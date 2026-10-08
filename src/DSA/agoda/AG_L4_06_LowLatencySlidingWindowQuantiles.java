package DSA.agoda;

import java.util.Arrays;

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
 * SCENARIO / SYSTEM CONTEXT:
 *   Agoda SLAs mandate monitoring the P95, P99, and P99.9 latencies of internal
 *   microservices without running out of JVM heap or triggering GC stop-the-world
 *   pauses. Storing raw latency arrays creates excessive object allocation.
 *
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 *   - Naive method: Store every latency into an `ArrayList<Double>` and sort -> O(N log N)
 *     and massive memory churn.
 *   - HdrHistogram / Fixed Bucket Compression (O(1) Memory):
 *     Map raw latency (e.g., 0ms to 10,000ms) into logarithmically or statically
 *     spaced buckets.
 *     Computing P99 requires scanning the cumulative distribution frequency (CDF)
 *     across static buckets without allocating any new objects during runtime.
 *
 * COMPLEXITY:
 *   - Record Latency: O(1)
 *   - Compute Quantile (P99): O(Total Buckets) = O(1) fixed pass
 *   - Space: O(1) fixed primitive integer array
 */
public class AG_L4_06_LowLatencySlidingWindowQuantiles {

    public static class LowLatencyHistogram {
        // Pre-allocated buckets covering 0ms to 5000ms (1ms resolution)
        private final int[] buckets = new int[5001];
        private int overflowCount = 0;
        private long totalCount = 0;

        public synchronized void record(int latencyMs) {
            if (latencyMs < 0) return;
            if (latencyMs <= 5000) {
                buckets[latencyMs]++;
            } else {
                overflowCount++;
            }
            totalCount++;
        }

        public synchronized int getPercentile(double percentile) {
            if (totalCount == 0) return 0;

            long target = (long) Math.ceil((percentile / 100.0) * totalCount);
            long cumulative = 0;

            for (int i = 0; i <= 5000; i++) {
                cumulative += buckets[i];
                if (cumulative >= target) {
                    return i;
                }
            }
            return 5000; // In overflow region
        }
    }

    public static void main(String[] args) {
        LowLatencyHistogram histogram = new LowLatencyHistogram();

        // Simulate 100,000 sub-millisecond and low-latency API calls
        for (int i = 0; i < 99000; i++) {
            histogram.record(12); // 99% of requests take 12ms
        }
        // Tail latency spikes
        for (int i = 0; i < 900; i++) {
            histogram.record(150); // 0.9% take 150ms
        }
        for (int i = 0; i < 100; i++) {
            histogram.record(480); // 0.1% take 480ms
        }

        System.out.println("P50 Latency: " + histogram.getPercentile(50.0) + " ms");   // Expected: 12 ms
        System.out.println("P99 Latency: " + histogram.getPercentile(99.0) + " ms");   // Expected: 12 ms
        System.out.println("P99.9 Latency: " + histogram.getPercentile(99.9) + " ms"); // Expected: 150 ms
        System.out.println("P100 Latency: " + histogram.getPercentile(100.0) + " ms"); // Expected: 480 ms
    }
}