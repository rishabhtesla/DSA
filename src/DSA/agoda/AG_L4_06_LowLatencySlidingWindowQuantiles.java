package DSA.agoda;

import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.LongAdder;

/**
 * ============================================================================
 * [STAFF L4 - 06] P99 LATENCY SLIDING WINDOW PROFILER (Memory & Observability)
 * ============================================================================
 *
 * JD TARGET:
 *   - "Monitoring/observability (Prometheus, Grafana, ELK)"
 *   - "Identify and resolve system bottlenecks across network I/O and JVM"
 *   - "Sub-second latencies across global pricing engines"
 *
 * PROBLEM:
 *   Design and implement a zero-allocation, thread-safe latency quantile profiler
 *   (`LowLatencyHistogram` + `SlidingWindowHistogram`) capable of computing P50,
 *   P95, P99, and P99.9 tail latencies at hundreds of thousands of requests per second
 *   (RPS) without heap exhaustion or GC stop-the-world pauses.
 *
 *   Requirements:
 *     1. Bounded O(1) Memory Footprint: Pre-allocate fixed primitive bucket counters
 *        (`0ms` to `5000ms` at `1ms` resolution, plus an overflow counter for `> 5000ms`)
 *        so recording samples produces **zero heap allocations** on the hot path.
 *     2. Lock-Free Concurrent Recording (`record(int latencyMs)`):
 *        - Avoid `synchronized` on `record()`—putting a global lock on the metrics
 *          recorder turns your observability library into the very bottleneck causing
 *          P99 latency spikes!
 *        - Use `AtomicLongArray` (or striped `LongAdder`s) for wait-free atomic increments.
 *     3. Sliding Time-Window Support (Why Static Cumulative Histograms Go Stale!):
 *        - A static all-time histogram becomes blind to new outages after millions of
 *          healthy requests accumulate ("denominator inertia").
 *        - Maintain a ring of `W` fixed time-slice buckets (e.g., `6` slices x `10s` = `60s`
 *          sliding window) so stale traffic rolls off automatically without locks or GC!
 *     4. Accurate Quantile Rank Calculation (`getPercentile(double percentile)`):
 *        - Compute target rank `ceil((percentile / 100.0) * totalCount)` and scan the
 *          Cumulative Distribution Function (CDF) across buckets `[0 .. 5000]`.
 *
 * CONSTRAINTS & PRODUCTION EDGE CASES:
 *   - `0.0 <= percentile <= 100.0` (validate range; `percentile == 0.0` returns min recorded
 *     or `0`; `percentile == 100.0` returns exact max bucket).
 *   - Negative latencies (`latencyMs < 0`, e.g., from non-monotonic clocks) are safely clamped
 *     or ignored rather than throwing `ArrayIndexOutOfBoundsException`.
 *   - Overflow values (`latencyMs > 5000`) increment `overflowCount` and still contribute to
 *     `totalCount` so tail percentiles accurately report `5001` (sentinel for `> 5000ms`).
 *   - 32-bit `int` overflow: Use `long` (`AtomicLongArray` / `LongAdder`) instead of `int[]`
 *     because at 50k RPS, a single popular bucket (`12ms`) overflows `Integer.MAX_VALUE`
 *     (`2.14 * 10^9`) in less than 12 hours!
 *
 * EXAMPLES:
 *   Example 1 (100,000 API Requests):
 *     - 99,000 requests @ 12 ms   (99.0%)
 *     -    900 requests @ 150 ms  ( 0.9%)
 *     -    100 requests @ 480 ms  ( 0.1%)
 *     Total = 100,000:
 *       - P50.0  -> target rank = ceil(0.500 * 100,000) =  50,000 -> 12 ms
 *       - P99.0  -> target rank = ceil(0.990 * 100,000) =  99,000 -> 12 ms
 *       - P99.9  -> target rank = ceil(0.999 * 100,000) =  99,900 -> 150 ms
 *       - P100.0 -> target rank = ceil(1.000 * 100,000) = 100,000 -> 480 ms
 *
 * ============================================================================
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 * ============================================================================
 *   - Why Naive `ArrayList<Integer>` + `Collections.sort` Fails in Production:
 *       * Boxing `int -> Integer` allocates 16 bytes per request (~1.6 MB/sec at 100k RPS),
 *         triggering frequent Young-Gen GC pauses that artificially inflate P99 latency
 *         (the "Observer Effect").
 *       * Sorting `N` samples takes `O(N log N)` time and `O(N)` memory.
 *
 *   - Fixed-Bucket Histogram vs. HdrHistogram vs. t-digest:
 *       * **Fixed Linear Buckets (`0..5000ms`)**: `O(1)` array index `buckets[latencyMs]`,
 *         ~40 KB total memory (`5001 * 8 bytes` fits completely inside CPU L1/L2 cache!),
 *         and exact `1ms` precision across the entire SLA range.
 *       * **HdrHistogram (Gil Tene)**: Uses logarithmic mantissa/exponent sub-buckets to
 *         cover `1 microsecond` to `1 hour` with constant relative error (`0.1%`) in ~30 KB.
 *       * **Sliding Window Ring (`W` slices)**: Combines fixed histograms with a circular
 *         array of time slices (like Prometheus / Resilience4j `SlidingTimeWindowMetrics`),
 *         resetting expired slices lazily as time advances so P99 reflects *current* health.
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Lock-Free Histogram (`LowLatencyHistogram`):
 *      - `AtomicLongArray buckets = new AtomicLongArray(MAX_LATENCY_MS + 1)` (`5001` slots = ~40 KB).
 *      - `LongAdder overflowCount = new LongAdder()` (for latencies `> 5000ms`).
 *      - `LongAdder totalCount = new LongAdder()` (avoids single-cache-line CAS contention
 *        on the global counter when all threads increment simultaneously!).
 *
 *   2. Hot-Path Recording (`record(int latencyMs)` — Zero Allocation, Lock-Free):
 *      - If `latencyMs < 0`, return immediately.
 *      - If `latencyMs <= MAX_LATENCY_MS`:
 *          `buckets.incrementAndGet(latencyMs);`
 *      - Else:
 *          `overflowCount.increment();`
 *      - `totalCount.increment();`
 *
 *   3. CDF Quantile Scan (`getPercentile(double percentile)`):
 *      - Validate `0.0 <= percentile <= 100.0`.
 *      - Snapshot `long total = totalCount.sum()`. If `total == 0`, return `0`.
 *      - Compute target 1-based rank:
 *          `long targetRank = (percentile == 0.0) ? 1L : (long) Math.ceil((percentile / 100.0) * total);`
 *      - Walk buckets `i = 0` to `MAX_LATENCY_MS`:
 *          * `cumulative += buckets.get(i);`
 *          * The first bucket `i` where `cumulative >= targetRank` is our quantile! Return `i`.
 *      - If `targetRank` falls past bucket `5000` (inside `overflowCount`), return `MAX_LATENCY_MS + 1` (`5001`).
 *
 *   4. Sliding Time-Window Wrapper (`SlidingWindowHistogram`):
 *      - Maintains `numSlices` circular `LowLatencyHistogram` slices, each covering `sliceDurationMs`.
 *      - On `record(latencyMs)`: computes `currentSliceIdx = (nowMs / sliceDurationMs) % numSlices`.
 *        If the slice's `epochId != (nowMs / sliceDurationMs)`, atomically claims and resets
 *        that slice before recording.
 *      - On `getPercentile(p)`: aggregates counts across all active (non-expired) slices!
 *
 * ============================================================================
 * VISUAL DRY RUN: 100,000 Requests (99,000 @ 12ms, 900 @ 150ms, 100 @ 480ms)
 * ============================================================================
 *   Bucket Index (ms) | Bucket Count | Cumulative Count | Percentiles Resolved Here
 *   ------------------+--------------+------------------+---------------------------------------
 *        0 .. 11      |      0       |        0         | -
 *           12        |    99,000    |      99,000      | P50 (rank 50,000) & P99 (rank 99,000)
 *       13 .. 149     |      0       |      99,000      | -
 *          150        |     900      |      99,900      | P99.9 (rank 99,900)
 *      151 .. 479     |      0       |      99,900      | -
 *          480        |     100      |     100,000      | P100  (rank 100,000)
 *     5001 (overflow) |      0       |     100,000      | -
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:
 *       * `record(latencyMs)`:      `O(1)` lock-free atomic increment (~10-20 ns, 0 bytes allocated).
 *       * `getPercentile(p)`:       `O(B)` where `B = 5001` fixed buckets (`~3 microseconds` scan).
 *   - Space:
 *       * `O(B)` fixed primitive memory (`5001 * 8 bytes ≈ 40 KB` per histogram slice).
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would storing raw samples do to JVM tail latency?
 *      (Creates massive object churn -> triggers GC stop-the-world pauses -> your profiler
 *       itself causes the exact P99 latency spikes you are trying to measure!)
 *   2. What invariant must remain true during CDF scanning?
 *      (`sum(buckets[0..5000]) + overflowCount == totalCount`, and `cumulative` is monotonically
 *       non-decreasing so the first index where `cumulative >= targetRank` is unique.)
 *   3. Why were `synchronized` and `int[]` replaced with `AtomicLongArray` + `LongAdder`?
 *      - `synchronized` on `record()` serializes every request thread through a single monitor lock!
 *      - `int[]` overflows at `2.14 * 10^9` samples (< 12 hours at 50k RPS), turning counts negative
 *        and corrupting percentile math!
 *   4. Which edge cases change the control flow (`totalCount == 0`, `percentile == 0.0` vs `100.0`,
 *      negative `latencyMs < 0`, overflow `latencyMs > 5000`, stale window slices with zero traffic)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_L4_06_LowLatencySlidingWindowQuantiles {

    public static class LowLatencyHistogram {
        public static final int MAX_TRACKED_MS = 5000;

        // 5001 * 8 bytes ≈ 40 KB fixed primitive memory (fits inside CPU L2 cache)
        private final AtomicLongArray buckets = new AtomicLongArray(MAX_TRACKED_MS + 1);
        private final LongAdder overflowCount = new LongAdder();
        private final LongAdder totalCount = new LongAdder();

        // Hot-path method: Lock-free, wait-free on LongAdder, zero heap allocations
        public void record(int latencyMs) {
            if (latencyMs < 0) {
                return; // Ignore invalid negative clock readings
            }
            if (latencyMs <= MAX_TRACKED_MS) {
                buckets.incrementAndGet(latencyMs);
            } else {
                overflowCount.increment();
            }
            totalCount.increment();
        }

        // Cold-path reader (scraped every 10-15s by Prometheus)
        public int getPercentile(double percentile) {
            if (percentile < 0.0 || percentile > 100.0) {
                throw new IllegalArgumentException("Percentile must be in [0.0, 100.0], got: " + percentile);
            }

            long total = totalCount.sum();
            if (total == 0L) {
                return 0;
            }

            // 1-based target rank in [1, total]
            long targetRank = (percentile == 0.0)
                    ? 1L
                    : (long) Math.ceil((percentile / 100.0) * total);

            long cumulative = 0L;
            for (int ms = 0; ms <= MAX_TRACKED_MS; ms++) {
                cumulative += buckets.get(ms);
                if (cumulative >= targetRank) {
                    return ms;
                }
            }

            // Target rank lies in the overflow region (> 5000 ms)
            return MAX_TRACKED_MS + 1;
        }

        public void reset() {
            for (int ms = 0; ms <= MAX_TRACKED_MS; ms++) {
                buckets.set(ms, 0L);
            }
            overflowCount.reset();
            totalCount.reset();
        }

        long getBucketCount(int ms) {
            return buckets.get(ms);
        }

        long getOverflowCount() {
            return overflowCount.sum();
        }
    }

    /**
     * Sliding Time-Window Profiler (e.g., 6 slices x 10s = 60s rolling P99 window).
     * Prevents "denominator inertia" so fresh outages immediately move P99!
     */
    public static class SlidingWindowHistogram {
        private final LowLatencyHistogram[] slices;
        private final long[] sliceEpochs;
        private final int numSlices;
        private final long sliceDurationMs;

        public SlidingWindowHistogram(int numSlices, long sliceDurationMs) {
            this.numSlices = numSlices;
            this.sliceDurationMs = sliceDurationMs;
            this.slices = new LowLatencyHistogram[numSlices];
            this.sliceEpochs = new long[numSlices];
            for (int i = 0; i < numSlices; i++) {
                this.slices[i] = new LowLatencyHistogram();
                this.sliceEpochs[i] = -1L;
            }
        }

        public void record(int latencyMs) {
            long currentEpoch = System.currentTimeMillis() / sliceDurationMs;
            int idx = (int) (currentEpoch % numSlices);

            // Lazily rotate/clear stale slice when time advances into a new epoch
            if (sliceEpochs[idx] != currentEpoch) {
                synchronized (slices[idx]) {
                    if (sliceEpochs[idx] != currentEpoch) {
                        slices[idx].reset();
                        sliceEpochs[idx] = currentEpoch;
                    }
                }
            }
            slices[idx].record(latencyMs);
        }

        public int getSlidingPercentile(double percentile) {
            long currentEpoch = System.currentTimeMillis() / sliceDurationMs;
            long minValidEpoch = currentEpoch - numSlices + 1;

            long[] mergedBuckets = new long[LowLatencyHistogram.MAX_TRACKED_MS + 1];
            long mergedTotal = 0L;

            for (int i = 0; i < numSlices; i++) {
                if (sliceEpochs[i] >= minValidEpoch) {
                    for (int ms = 0; ms <= LowLatencyHistogram.MAX_TRACKED_MS; ms++) {
                        long c = slices[i].getBucketCount(ms);
                        mergedBuckets[ms] += c;
                        mergedTotal += c;
                    }
                    mergedTotal += slices[i].getOverflowCount();
                }
            }

            if (mergedTotal == 0L) return 0;

            long targetRank = (percentile == 0.0)
                    ? 1L
                    : (long) Math.ceil((percentile / 100.0) * mergedTotal);

            long cumulative = 0L;
            for (int ms = 0; ms <= LowLatencyHistogram.MAX_TRACKED_MS; ms++) {
                cumulative += mergedBuckets[ms];
                if (cumulative >= targetRank) {
                    return ms;
                }
            }
            return LowLatencyHistogram.MAX_TRACKED_MS + 1;
        }
    }

    public static void main(String[] args) {
        LowLatencyHistogram histogram = new LowLatencyHistogram();

        // Simulate 100,000 API calls
        for (int i = 0; i < 99_000; i++) {
            histogram.record(12);  // 99.0% take 12ms
        }
        for (int i = 0; i < 900; i++) {
            histogram.record(150); //  0.9% take 150ms
        }
        for (int i = 0; i < 100; i++) {
            histogram.record(480); //  0.1% take 480ms
        }

        System.out.println("P50 Latency:   " + histogram.getPercentile(50.0)  + " ms | Expected: 12 ms");
        System.out.println("P99 Latency:   " + histogram.getPercentile(99.0)  + " ms | Expected: 12 ms");
        System.out.println("P99.9 Latency: " + histogram.getPercentile(99.9)  + " ms | Expected: 150 ms");
        System.out.println("P100 Latency:  " + histogram.getPercentile(100.0) + " ms | Expected: 480 ms");
    }
}