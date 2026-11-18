package DSA.agoda;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ============================================================================
 * [STAFF L4 - 01] LOCK-FREE TOKEN BUCKET RATE LIMITER (High-Concurrency Engine)
 * ============================================================================
 *
 * JD TARGET:
 *   - "High-Concurrency Performance Tuning"
 *   - "Resiliency patterns (circuit breakers, rate limiting)"
 *   - "Concurrency/multithreading and memory management"
 *
 * PROBLEM:
 *   Design and implement a thread-safe, high-throughput Token Bucket Rate Limiter
 *   capable of throttling per-tenant / per-API-key traffic at hundreds of thousands
 *   of requests per second (RPS) without lock contention or background timer threads.
 *
 *   Requirements:
 *     1. Each tenant bucket has a maximum burst `capacity` and a continuous
 *        `refillRatePerSecond`.
 *     2. `tryConsume(tenantId, tokensRequested)` atomically checks whether enough
 *        tokens are available after lazy replenishment; if so, deducts them and
 *        returns `true`; otherwise leaves state unchanged and returns `false`.
 *     3. Must be completely lock-free (no `synchronized` or `ReentrantLock`) and
 *        zero-allocation on the hot path (`tryConsume`).
 *
 * CONSTRAINTS & PRODUCTION EDGE CASES:
 *   - `1 <= capacity <= 1_000_000` (fits comfortably in unsigned/positive 32 bits)
 *   - `0.001 <= refillRatePerSecond <= 1_000_000`
 *   - Monotonic Clock Requirement: Use `System.nanoTime()` (relative to bucket creation)
 *     instead of `System.currentTimeMillis()` so NTP clock jumps never freeze or
 *     corrupt token refills, AND a 32-bit millisecond offset never wraps around
 *     every ~49.7 days (`2^32 ms = 49.7 days`)!
 *   - Fractional Refill Precision: Advance `lastRefillTime` only by the exact time
 *     consumed by whole tokens generated (`tokensAdded / refillRatePerMs`), rather
 *     than resetting `lastRefillTime = now` on every sub-millisecond call (which
 *     otherwise starves low-rate buckets under high-frequency polling!).
 *
 * EXAMPLES:
 *   Example 1 (Burst & Refill):
 *     Bucket: capacity = 5 tokens, refillRate = 2 tokens/sec (1 token every 500 ms).
 *       - t = 0ms    : tryConsume(3) -> true  (remaining: 2)
 *       - t = 10ms   : tryConsume(2) -> true  (remaining: 0)
 *       - t = 20ms   : tryConsume(1) -> false (rejected; 0 available)
 *       - t = 1100ms : tryConsume(2) -> true  (elapsed 1100ms -> +2 tokens refilled -> 2 - 2 = 0)
 *
 * ============================================================================
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 * ============================================================================
 *   - Why Locks Fail at Staff/L4 Scale:
 *       * `synchronized` or `ReentrantLock` causes kernel-level thread parking and
 *         context switches under heavy contention, spiking tail latency (`p99`)
 *         from microseconds to tens of milliseconds.
 *   - Why Two `AtomicLong`s Fail:
 *       * Updating `tokens` and `lastRefillTime` in two separate `AtomicLong` fields
 *         is NOT atomic as a pair—two threads can interleave between updating tokens
 *         and updating timestamp, double-refilling the bucket!
 *   - The Staff-Level "AHA!" Moment (64-Bit Bit-Packed State + CAS Loop):
 *       * Pack BOTH `(availableTokens, lastRefillMs)` into a SINGLE 64-bit `AtomicLong`:
 *
 *           [  High 32 bits: availableTokens (0 .. 2^31-1)  |  Low 32 bits: elapsedMs since epoch  ]
 *            63                                           32 31                                   0
 *
 *       * Because the entire bucket state fits in one 64-bit CPU word, a single hardware
 *         `LOCK CMPXCHG` instruction (`state.compareAndSet`) updates BOTH tokens and
 *         timestamp atomically with zero heap allocations!
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Epoch-Relative Monotonic Time (`baseNanoTime`):
 *      - Record `baseNanoTime = System.nanoTime()` when the bucket is created.
 *      - Helper `nowMillis()` computes `(System.nanoTime() - baseNanoTime) / 1_000_000L`.
 *      - Benefits: Immune to wall-clock NTP backward jumps, and 32-bit relative ms
 *        lasts ~49.7 days of continuous bucket lifetime without wrap-around bugs.
 *
 *   2. Bit-Packing Helpers (`encode` / `decodeTokens` / `decodeTime`):
 *      - `encode(tokens, timeMs)` -> `(tokens << 32) | (timeMs & 0xFFFFFFFFL)`
 *      - `decodeTokens(state)`    -> `state >>> 32` (unsigned right shift!)
 *      - `decodeTime(state)`      -> `state & 0xFFFFFFFFL` (masks lower 32 bits)
 *
 *   3. Lock-Free CAS Loop (`tryConsume(int tokensRequested)`):
 *      - Loop (`while (true)`):
 *          a. Snapshot current 64-bit word: `long current = state.get()`.
 *          b. Unpack `currentTokens` and `lastRefillMs`.
 *          c. Read `long nowMs = nowMillis()`, compute `elapsedMs = Math.max(0L, nowMs - lastRefillMs)`.
 *          d. Lazy Refill Calculation:
 *               - `long tokensToAdd = (long) (elapsedMs * refillRatePerMs);`
 *               - `long refreshedTokens = Math.min(capacity, currentTokens + tokensToAdd);`
 *          e. Check Quota:
 *               - If `refreshedTokens < tokensRequested`, return `false` immediately
 *                 (no state mutation needed on rejection!).
 *          f. Compute Next State & Preserve Fractional Time Remainder:
 *               - `long nextTokens = refreshedTokens - tokensRequested;`
 *               - If `refreshedTokens == capacity`, snap `nextTimeMs = nowMs` (bucket is full,
 *                 discard excess idle time). Otherwise advance `lastRefillMs` only by the
 *                 exact ms consumed by `tokensToAdd` (`(long) (tokensToAdd / refillRatePerMs)`),
 *                 so sub-token elapsed milliseconds aren't lost under high-RPS traffic!
 *               - Pack `long nextState = encode(nextTokens, nextTimeMs)`.
 *          g. Atomic Commit:
 *               - `if (state.compareAndSet(current, nextState)) return true;`
 *               - If CAS fails, another thread won the race—spin and retry with fresh state!
 *
 *   4. Multi-Tenant Registry (`DistributedTokenBucketRateLimiter`):
 *      - Use `ConcurrentHashMap<String, TokenBucket>` with `computeIfAbsent` so
 *        different tenants/API keys never contend on each other's CAS loops.
 *
 * ============================================================================
 * VISUAL DRY RUN: capacity = 5, refillRate = 2 tokens/sec (0.002 tokens/ms)
 * ============================================================================
 *   Op # | Time (ms) | Pre-State (tokens, lastMs) | Elapsed | Refilled | Req | Post-State (tokens, lastMs) | Result
 *   -----+-----------+----------------------------+---------+----------+-----+-----------------------------+-------
 *   Init |     0     | (5, 0)                     |    -    |    -     |  -  | (5, 0)                      |   -
 *    1   |     0     | (5, 0)                     |   0 ms  |   +0     |  3  | (2, 0)                      |  true
 *    2   |    10     | (2, 0)                     |  10 ms  |   +0     |  2  | (0, 0)   [10ms preserved!]  |  true
 *    3   |    20     | (0, 0)                     |  20 ms  |   +0     |  1  | (0, 0)   [unchanged]        |  false
 *    4   |   1100    | (0, 0)                     | 1100 ms |   +2     |  2  | (0, 1000)[100ms preserved!] |  true
 *
 *   Notice how preserving the unconsumed 100ms remainder at Op #4 means the next
 *   token arrives in just 400ms (at t = 1500ms) instead of making the caller wait
 *   another full 500ms!
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(1) expected lock-free per request (single `AtomicLong` CAS; bounded
 *            spin retries only under simultaneous same-tenant collisions).
 *   - Space: O(K) where K is the number of active tenant keys (8 bytes of atomic
 *            state per bucket—zero GC pressure on `tryConsume`).
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force/naive solution do, and where does it bottleneck?
 *      (Global or per-key `synchronized` blocks cause thread blocking and context switches at high RPS;
 *       a background `ScheduledExecutorService` refilling K buckets every tick wastes O(K) CPU even on idle keys.)
 *   2. What invariant must remain true after every CAS transition?
 *      (`0 <= decodedTokens <= capacity`, and `lastRefillMs` never moves backward.)
 *   3. Why is each bit-operation/state update safe, and what subtle bugs would break it?
 *      - Using `>> 32` (signed shift) instead of `>>> 32` (unsigned shift) would sign-extend if bit 63
 *        were ever set.
 *      - Using raw `System.currentTimeMillis() & 0xFFFFFFFFL` directly wraps around every 49.7 days,
 *        causing `now - lastRefillTime` to jump by `+4.29 * 10^9 ms` and instantly refilling buckets!
 *        Using monotonic `System.nanoTime()` relative to `baseNanoTime` fixes both wrap-around and NTP drift.
 *   4. Which edge cases change the control flow (`tokensRequested <= 0`, `tokensRequested > capacity`,
 *      low-rate bucket `< 1 token/sec` polled every 1ms, burst idle period exceeding `capacity`,
 *      simultaneous CAS contention across threads)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_L4_01_DistributedTokenBucketRateLimiter {

    public static class TokenBucket {
        private final long capacity;
        private final double refillRatePerMs; // Tokens generated per millisecond
        private final long baseNanoTime;      // Monotonic epoch anchor for relative ms timestamps

        // Bit-packed 64-bit state:
        //   [ High 32 bits: availableTokens | Low 32 bits: relativeLastRefillMs ]
        private final AtomicLong state;

        public TokenBucket(long capacity, double refillRatePerSecond) {
            if (capacity <= 0 || capacity > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Capacity must be in [1, Integer.MAX_VALUE]");
            }
            if (refillRatePerSecond <= 0) {
                throw new IllegalArgumentException("Refill rate must be positive");
            }

            this.capacity = capacity;
            this.refillRatePerMs = refillRatePerSecond / 1000.0;
            this.baseNanoTime = System.nanoTime();

            // Initialize bucket at full capacity at relative timestamp 0ms
            this.state = new AtomicLong(encode(capacity, 0L));
        }

        public boolean tryConsume(int tokensRequested) {
            if (tokensRequested <= 0) return true;
            if (tokensRequested > capacity) return false; // Impossible even when bucket is full

            while (true) {
                long current = state.get();
                long currentTokens = decodeTokens(current);
                long lastRefillMs = decodeTime(current);
                long nowMs = currentRelativeMillis();

                // Step 1: Calculate lazy token replenishment since lastRefillMs
                long elapsedMs = Math.max(0L, nowMs - lastRefillMs);
                long tokensToAdd = (long) (elapsedMs * refillRatePerMs);
                long refreshedTokens = Math.min(capacity, currentTokens + tokensToAdd);

                // Step 2: Fast-fail without mutating state if insufficient tokens
                if (refreshedTokens < tokensRequested) {
                    return false;
                }

                // Step 3: Compute remaining tokens and preserve fractional millisecond remainder
                long nextTokens = refreshedTokens - tokensRequested;
                long nextRefillMs;
                if (refreshedTokens == capacity) {
                    // Bucket capped at max capacity -> discard excess idle time
                    nextRefillMs = nowMs;
                } else {
                    // Advance timestamp only by exact ms accounted for by `tokensToAdd`
                    long accountedMs = (long) (tokensToAdd / refillRatePerMs);
                    nextRefillMs = lastRefillMs + accountedMs;
                }

                long nextState = encode(nextTokens, nextRefillMs);

                // Step 4: Single-instruction atomic CAS commit
                if (state.compareAndSet(current, nextState)) {
                    return true;
                }
                // Lost CAS race to another concurrent thread -> retry immediately with fresh state
            }
        }

        private long currentRelativeMillis() {
            return (System.nanoTime() - baseNanoTime) / 1_000_000L;
        }

        // Pack (32-bit tokens, 32-bit relative ms) into a single 64-bit long
        private static long encode(long tokens, long relativeMs) {
            return (tokens << 32) | (relativeMs & 0xFFFFFFFFL);
        }

        private static long decodeTokens(long packedState) {
            return packedState >>> 32; // Unsigned right shift
        }

        private static long decodeTime(long packedState) {
            return packedState & 0xFFFFFFFFL; // Lower 32 bits
        }
    }

    // Per-Tenant Rate Limiter Manager
    private final ConcurrentHashMap<String, TokenBucket> tenantBuckets = new ConcurrentHashMap<>();
    private final long defaultCapacity;
    private final double defaultRefillRatePerSecond;

    public AG_L4_01_DistributedTokenBucketRateLimiter(long defaultCapacity, double defaultRefillRatePerSecond) {
        this.defaultCapacity = defaultCapacity;
        this.defaultRefillRatePerSecond = defaultRefillRatePerSecond;
    }

    public boolean allowRequest(String tenantId, int tokensRequested) {
        TokenBucket bucket = tenantBuckets.computeIfAbsent(
                tenantId,
                id -> new TokenBucket(defaultCapacity, defaultRefillRatePerSecond)
        );
        return bucket.tryConsume(tokensRequested);
    }

    public static void main(String[] args) throws InterruptedException {
        // Capacity: 5 tokens, Refill: 2 tokens/sec (1 token per 500ms)
        AG_L4_01_DistributedTokenBucketRateLimiter limiter =
                new AG_L4_01_DistributedTokenBucketRateLimiter(5, 2.0);

        String tenant = "partner-kayak-api";
        System.out.println("Consume 3: " + limiter.allowRequest(tenant, 3) + " | Expected: true (2 left)");
        System.out.println("Consume 2: " + limiter.allowRequest(tenant, 2) + " | Expected: true (0 left)");
        System.out.println("Consume 1: " + limiter.allowRequest(tenant, 1) + " | Expected: false (throttled)");

        Thread.sleep(1100); // Wait 1.1s -> refills 2 tokens (with 100ms fractional remainder preserved!)
        System.out.println("Consume 2 after 1.1s: " + limiter.allowRequest(tenant, 2) + " | Expected: true");
    }
}