package DSA.agoda;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * ============================================================================
 * [STAFF L4 - 02] CACHE STAMPEDE MITIGATION / PROBABILISTIC EARLY EXPIRATION
 * ============================================================================
 * 
 * JD TARGET:
 *   - "Distributed Caching (Redis/Memcached)"
 *   - "Sub-second latencies across globally distributed travel applications"
 *   - "JVM memory management & connection pooling"
 *
 * SCENARIO / SYSTEM CONTEXT:
 *   High-demand hotel listings (e.g., during New Year's Eve in Bangkok) receive 
 *   tens of thousands of concurrent reads. When the cache key expires in Redis,
 *   all concurrent requests simultaneously miss and query PostgreSQL/MySQL.
 *   This causes connection pool exhaustion, DB crash, and cascading failure.
 *
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 *   - Pattern 1: Single-Flight / Mutex Locking (Locally via ConcurrentMap / Globally via Redis Redlock).
 *     Only 1 thread queries the DB; other threads wait for the computed result.
 *   - Pattern 2: Probabilistic Early Expiration (XFetch algorithm).
 *     Before the key hard-expires, compute:
 *     `-beta * delta * ln(random()) > (expiry - now)`
 *     If true, trigger asynchronous background refresh while continuing to serve 
 *     stale data to all other incoming requests.
 *
 * COMPLEXITY:
 *   - Time:  O(1) memory lookup + O(1) single-flight coordination.
 *   - Space: O(K) concurrent in-flight promises.
 */
public class AG_L4_02_CacheStampedeMutex {

    public static class SingleFlightCache<K, V> {
        private final ConcurrentMap<K, V> storage = new ConcurrentHashMap<>();
        private final ConcurrentMap<K, CompletableFuture<V>> inFlight = new ConcurrentHashMap<>();

        public V getOrCompute(K key, Supplier<V> dbLoader) {
            // Step 1: Fast cache hit
            V cached = storage.get(key);
            if (cached != null) {
                return cached;
            }

            // Step 2: Single-flight execution - exactly one thread computes
            CompletableFuture<V> future = inFlight.computeIfAbsent(key, k -> {
                CompletableFuture<V> promise = new CompletableFuture<>();
                // Asynchronously or synchronously compute via database
                ForkJoinPool.commonPool().execute(() -> {
                    try {
                        V value = dbLoader.get();
                        storage.put(k, value);
                        promise.complete(value);
                    } catch (Throwable t) {
                        promise.completeExceptionally(t);
                    } finally {
                        inFlight.remove(k); // Clean up coordination barrier
                    }
                });
                return promise;
            });

            try {
                return future.get(500, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                throw new RuntimeException("DB fetch timeout or failure", e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SingleFlightCache<String, String> hotelCache = new SingleFlightCache<>();
        CountDownLatch latch = new CountDownLatch(10);

        // Simulate 10 simultaneous threads querying an unpopulated hot key
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            new Thread(() -> {
                String hotelDetails = hotelCache.getOrCompute("hotel:bangkok:101", () -> {
                    System.out.println(">>> Executing expensive DB query by Thread: " + threadId);
                    try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                    return "Luxury River Suite, $180/night";
                });
                System.out.println("Thread " + threadId + " received: " + hotelDetails);
                latch.countDown();
            }).start();
        }

        latch.await();
        // Notice DB execution runs EXACTLY ONCE despite 10 concurrent requests!
    }
}