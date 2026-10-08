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
 * SCENARIO / SYSTEM CONTEXT:
 *   Agoda's search and pricing engines receive hundreds of thousands of RPS.
 *   Upstream scrapers, bots, and partner aggregators (like Kayak/Google Flights)
 *   must be throttled per tenant/API-key without introducing lock contention.
 *
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 *   - Naive Synchronized / Lock approach:
 *     Using `synchronized` or `ReentrantLock` causes context switching and OS-level 
 *     thread parks, degrading latency from sub-millisecond to tens of milliseconds 
 *     under thousands of concurrent threads.
 *   - Lock-Free CAS Approach:
 *     Pack state (available tokens & last refill timestamp) into atomic storage or
 *     use Compare-And-Swap (`AtomicLong` / Bit packing).
 *   - Lazy Refill:
 *     Never run a background thread to refill tokens (O(N) background overhead).
 *     Refill lazily at request time using the delta: `elapsedTime * refillRate`.
 *
 * COMPLEXITY:
 *   - Time:  O(1) amortized lock-free per request.
 *   - Space: O(K) where K is active tenant keys.
 */
public class AG_L4_01_DistributedTokenBucketRateLimiter {

    public static class TokenBucket {
        private final long capacity;
        private final double refillRatePerMs; // Tokens added per millisecond

        // Bit-packed state: High 32 bits = available tokens, Low 32 bits = timestamp (ms)
        // Or clean concurrent reference structure using AtomicLong for CAS:
        private final AtomicLong state;

        public TokenBucket(long capacity, double refillRatePerSecond) {
            this.capacity = capacity;
            this.refillRatePerMs = refillRatePerSecond / 1000.0;
            long now = System.currentTimeMillis();
            // Initial state: full capacity and current timestamp
            this.state = new AtomicLong(encode(capacity, now));
        }

        public boolean tryConsume(int tokensRequested) {
            while (true) {
                long current = state.get();
                long currentTokens = decodeTokens(current);
                long lastRefillTime = decodeTime(current);
                long now = System.currentTimeMillis();

                // Compute lazy replenishment
                long elapsed = Math.max(0, now - lastRefillTime);
                long refreshedTokens = Math.min(capacity, currentTokens + (long) (elapsed * refillRatePerMs));

                if (refreshedTokens < tokensRequested) {
                    return false; // Rate limit exceeded
                }

                long nextTokens = refreshedTokens - tokensRequested;
                long nextState = encode(nextTokens, now);

                // CAS ensures atomic state transition without locks
                if (state.compareAndSet(current, nextState)) {
                    return true;
                }
                // Contention retry: Thread lost CAS race, retry immediately
            }
        }

        // Pack (32-bit tokens, 32-bit delta ms) into single 64-bit long
        private static long encode(long tokens, long timestampMs) {
            return (tokens << 32) | (timestampMs & 0xFFFFFFFFL);
        }

        private static long decodeTokens(long state) {
            return state >>> 32;
        }

        private static long decodeTime(long state) {
            return state & 0xFFFFFFFFL;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Capacity: 5 tokens, Refill: 2 tokens/sec
        TokenBucket bucket = new TokenBucket(5, 2);

        System.out.println("Consume 3: " + bucket.tryConsume(3)); // true (2 remaining)
        System.out.println("Consume 2: " + bucket.tryConsume(2)); // true (0 remaining)
        System.out.println("Consume 1: " + bucket.tryConsume(1)); // false (exceeded)

        Thread.sleep(1100); // Wait for refill (~2 tokens generated)
        System.out.println("Consume 2 after 1s: " + bucket.tryConsume(2)); // true
    }
}