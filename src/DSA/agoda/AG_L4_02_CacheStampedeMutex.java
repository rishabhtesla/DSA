package DSA.agoda;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * ============================================================================
 * [STAFF L4 - 02] CACHE STAMPEDE MITIGATION / SINGLE-FLIGHT & XFETCH (PER)
 * ============================================================================
 *
 * JD TARGET:
 *   - "Distributed Caching (Redis/Memcached)"
 *   - "Sub-second latencies across globally distributed travel applications"
 *   - "JVM memory management & connection pooling"
 *
 * PROBLEM:
 *   Design and implement a thread-safe, stampede-proof cache layer (`SingleFlightCache<K, V>`)
 *   for hot inventory keys (e.g., high-demand hotel listings during peak travel dates).
 *
 *   Requirements:
 *     1. Fast-Path Hit: Serve cached, non-expired entries in `O(1)` without locking
 *        or hitting the backing database (`dbLoader`).
 *     2. Single-Flight Request Coalescing (Thundering Herd Protection): If `N` threads
 *        simultaneously miss on the same key, **at most ONE** database query may be
 *        executed while the remaining `N - 1` callers share its `CompletableFuture`.
 *     3. Probabilistic Early Expiration (XFetch / Stale-While-Revalidate): Before a
 *        hot key hard-expires, probabilistically trigger a non-blocking background
 *        refresh on a single thread while continuing to serve the slightly stale
 *        cached value to all other callers with zero tail-latency spike!
 *     4. Failure Isolation: If `dbLoader` throws an exception or times out, clean up
 *        the `inFlight` barrier so subsequent callers can retry instead of getting
 *        permanently stuck on a failed future.
 *
 * CONSTRAINTS & PRODUCTION EDGE CASES:
 *   - Must avoid `ForkJoinPool.commonPool()` for blocking JDBC/network DB I/O
 *     (blocking commonPool threads starves parallel streams and JVM async tasks!).
 *   - Must guard `inFlight.remove(key, promise)` using the 2-argument conditional
 *     remove so a slow/timed-out task never accidentally deletes a *newer* in-flight
 *     request's promise!
 *   - Handle `InterruptedException` properly by restoring `Thread.currentThread().interrupt()`.
 *
 * EXAMPLES:
 *   Example 1 (Cold-Key Thundering Herd):
 *     10 concurrent threads call `getOrCompute("hotel:bangkok:101", dbLoader, ttlMs=5000)`.
 *       - Thread 0 wins `inFlight.computeIfAbsent(...)` and launches 1 DB query (100ms).
 *       - Threads 1..9 join the exact same `CompletableFuture<V>` and wait.
 *       - At t = 100ms, `storage` is populated and all 10 threads receive `"Luxury River Suite, $180/night"`.
 *       - Total DB queries executed = 1 (instead of 10).
 *
 *   Example 2 (XFetch Proactive Background Refresh):
 *     Key `"hotel:bangkok:101"` has 400ms remaining before expiry, and `delta = 100ms`.
 *       - Thread A triggers `-beta * delta * ln(random()) > remainingTtl` -> returns `true`!
 *       - Thread A kicks off an async background refresh via `inFlight.computeIfAbsent(...)`
 *         AND immediately returns the existing cached value (`0ms` latency!).
 *       - Threads B..Z continue reading the cached value with zero blocking while Thread A
 *         silently refreshes the entry before hard expiry ever occurs.
 *
 * ============================================================================
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 * ============================================================================
 *   - Why Naive Cache-Aside Crashes PostgreSQL/MySQL:
 *       * Under 10,000 RPS on a hot hotel ID, a TTL expiry causes 10,000 simultaneous
 *         cache misses -> 10,000 concurrent DB queries -> HikariCP connection pool
 *         exhaustion (`ConnectionTimeout`) -> cascading 503 outage across services.
 *
 *   - Layer 1: Single-Flight Request Coalescing (`ConcurrentHashMap` + `CompletableFuture`)
 *       * Collapses `N` concurrent misses for key `K` into **1** backend flight.
 *       * Why `computeIfAbsent` + `CompletableFuture`?
 *           - `ConcurrentHashMap.computeIfAbsent` guarantees the mapping function runs
 *             atomically once per key.
 *           - By immediately offloading `dbLoader.get()` onto a dedicated I/O executor
 *             inside `computeIfAbsent`, we NEVER hold the `ConcurrentHashMap` bucket
 *             lock during slow network I/O!
 *
 *   - Layer 2: Probabilistic Early Expiration (XFetch — VLDB 2015 Paper)
 *       * Single-flight still forces callers to wait `delta` ms (e.g., 100ms) when a key
 *         hard-expires. XFetch eliminates even that latency spike:
 *           `now - (delta * beta * ln(rand(0, 1))) >= expiryTime`
 *         where:
 *           - `delta` = time taken to recompute the value (`computeDurationMs`).
 *           - `beta`  = tuning factor (`1.0` default; `> 1.0` refreshes earlier).
 *           - `-ln(rand)` = exponential distribution (concentrates refresh probability
 *             right before `expiryTime`, proportional to traffic rate and `delta`).
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Cache Entry Wrapper (`CacheEntry<V>`):
 *      - Stores `value`, `expiryTimeMs` (hard deadline), and `recomputeDeltaMs`
 *        (measured DB execution time used by XFetch).
 *
 *   2. Fast Path & XFetch Early Refresh Check:
 *      - Read `CacheEntry<V> entry = storage.get(key)`.
 *      - If `entry != null && !entry.isHardExpired(now)`:
 *          * Evaluate XFetch formula:
 *              `double xfetchWindow = entry.recomputeDeltaMs * beta * -Math.log(ThreadLocalRandom.current().nextDouble());`
 *              `if (now + xfetchWindow >= entry.expiryTimeMs)` -> trigger non-blocking
 *              `triggerAsyncRefresh(key, dbLoader, ttlMs)` (coalesced via `inFlight`!).
 *          * Immediately return `entry.value` (`O(1)` latency, zero waiting!).
 *
 *   3. Cold / Hard-Expired Path (Single-Flight Barrier):
 *      - Call `CompletableFuture<V> future = triggerAsyncRefresh(key, dbLoader, ttlMs)`.
 *      - Inside `triggerAsyncRefresh`:
 *          * `inFlight.computeIfAbsent(key, k -> { ... })` creates a `CompletableFuture<V> promise`
 *            and submits the DB fetch to a dedicated `ioExecutor` (never `commonPool`).
 *          * Worker measures `start = System.nanoTime()`, runs `dbLoader.get()`, computes
 *            `deltaMs`, writes `new CacheEntry<>(val, now + ttlMs, deltaMs)` to `storage`,
 *            and completes `promise.complete(val)`.
 *          * In `finally`: calls `inFlight.remove(k, promise)` (2-arg CAS remove!) so
 *            only this exact promise is cleaned up.
 *      - Caller awaits `future.get(timeoutMs, TimeUnit.MILLISECONDS)` and returns the result.
 *
 * ============================================================================
 * VISUAL DRY RUN: 10 Concurrent Threads Missing on "hotel:bangkok:101"
 * ============================================================================
 *   Time  | Thread(s)   | `storage.get` | `inFlight` Map State          | Action Taken
 *   ------+-------------+---------------+-------------------------------+----------------------------------
 *    0ms  | Thread 0    | null (MISS)   | {} -> {key: Future#1}         | Wins computeIfAbsent -> submits DB task
 *    0ms  | Threads 1-9 | null (MISS)   | {key: Future#1} (exists!)     | Joins Future#1 -> blocks on .get(500ms)
 *   50ms  | IO-Worker   | null          | {key: Future#1}               | Executing dbLoader.get() (100ms query)
 *   100ms | IO-Worker   | CacheEntry    | {key: Future#1} -> {}         | Writes storage, completes Future#1, removes
 *   100ms | Threads 0-9 | CacheEntry    | {}                            | All 10 threads wake up & return value!
 *   105ms | Thread 10   | HIT!          | {}                            | Returns value in O(1) (~0ms)
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:
 *       * Cache Hit:  `O(1)` (`ConcurrentHashMap.get` + `ThreadLocalRandom`).
 *       * Cache Miss: `O(1)` coordination overhead + 1 shared `dbLoader` execution.
 *   - Space:
 *       * `O(K + M)` where `K` is cached keys in `storage` and `M` (`M <= K`) is
 *         active keys currently in `inFlight`.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the naive solution do, and why does it fail at scale?
 *      (Naive cache-aside fires 10,000 parallel DB queries on TTL expiry; naive `synchronized(this)`
 *       serializes unrelated hotel keys behind one global lock.)
 *   2. What invariant must remain true after every state transition?
 *      (At any instant, `inFlight` holds at most ONE active `CompletableFuture` per key, and
 *       every `inFlight` entry is guaranteed to be removed in a `finally` block.)
 *   3. Why is each concurrency primitive safe, and what subtle bugs would break it?
 *      - `inFlight.remove(k, promise)` (2-arg) vs `inFlight.remove(k)` (1-arg): If a task stalls
 *        past `timeoutMs` and a new thread starts `Future#2`, the stale task's `finally` block
 *        using 1-arg `remove(k)` would delete `Future#2`! 2-arg `remove(k, promise)` prevents this.
 *      - Never run blocking JDBC calls on `ForkJoinPool.commonPool()`; inject a dedicated I/O pool.
 *   4. Which edge cases change the control flow (`dbLoader` throws RuntimeException, `dbLoader`
 *      returns `null`, caller times out at `500ms`, `InterruptedException`, XFetch background refresh
 *      colliding with a concurrent cold miss)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_L4_02_CacheStampedeMutex {

    public static class SingleFlightCache<K, V> {

        private static final class CacheEntry<V> {
            final V value;
            final long expiryTimeMs;
            final long recomputeDeltaMs;

            CacheEntry(V value, long expiryTimeMs, long recomputeDeltaMs) {
                this.value = value;
                this.expiryTimeMs = expiryTimeMs;
                this.recomputeDeltaMs = Math.max(1L, recomputeDeltaMs);
            }

            boolean isHardExpired(long nowMs) {
                return nowMs >= expiryTimeMs;
            }
        }

        private final ConcurrentMap<K, CacheEntry<V>> storage = new ConcurrentHashMap<>();
        private final ConcurrentMap<K, CompletableFuture<V>> inFlight = new ConcurrentHashMap<>();
        private final ExecutorService ioExecutor;
        private final long defaultTtlMs;
        private final long fetchTimeoutMs;
        private final double xfetchBeta;

        public SingleFlightCache(ExecutorService ioExecutor, long defaultTtlMs, long fetchTimeoutMs, double xfetchBeta) {
            this.ioExecutor = ioExecutor;
            this.defaultTtlMs = defaultTtlMs;
            this.fetchTimeoutMs = fetchTimeoutMs;
            this.xfetchBeta = xfetchBeta;
        }

        public V getOrCompute(K key, Supplier<V> dbLoader) {
            long nowMs = System.currentTimeMillis();
            CacheEntry<V> entry = storage.get(key);

            // Step 1: Fast cache hit (non-expired)
            if (entry != null && !entry.isHardExpired(nowMs)) {
                // Step 1b: Probabilistic Early Expiration (XFetch)
                // Proactively refresh in the background before hard TTL expires so callers never block
                double rand = ThreadLocalRandom.current().nextDouble(); // (0.0, 1.0)
                double earlyRefreshLeadMs = -xfetchBeta * entry.recomputeDeltaMs * Math.log(rand);

                if (nowMs + earlyRefreshLeadMs >= entry.expiryTimeMs) {
                    // Fire-and-forget coalesced refresh; current caller still gets instant 0ms response!
                    triggerAsyncRefresh(key, dbLoader);
                }
                return entry.value;
            }

            // Step 2: Cache miss or hard-expired -> Single-flight request coalescing
            CompletableFuture<V> future = triggerAsyncRefresh(key, dbLoader);

            try {
                return future.get(fetchTimeoutMs, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while awaiting cache load for key: " + key, ie);
            } catch (TimeoutException te) {
                throw new RuntimeException("Timeout after " + fetchTimeoutMs + "ms loading key: " + key, te);
            } catch (ExecutionException ee) {
                throw new RuntimeException("Backing loader failed for key: " + key, ee.getCause());
            }
        }

        // Ensures at most ONE background DB flight runs per key at any given moment
        private CompletableFuture<V> triggerAsyncRefresh(K key, Supplier<V> dbLoader) {
            return inFlight.computeIfAbsent(key, k -> {
                CompletableFuture<V> promise = new CompletableFuture<>();

                // Offload blocking DB call immediately so ConcurrentHashMap bucket lock is never held
                ioExecutor.execute(() -> {
                    long startNs = System.nanoTime();
                    try {
                        V freshValue = dbLoader.get();
                        long elapsedMs = (System.nanoTime() - startNs) / 1_000_000L;
                        long expiryMs = System.currentTimeMillis() + defaultTtlMs;

                        if (freshValue != null) {
                            storage.put(k, new CacheEntry<>(freshValue, expiryMs, elapsedMs));
                        }
                        promise.complete(freshValue);
                    } catch (Throwable t) {
                        promise.completeExceptionally(t);
                    } finally {
                        // 2-arg remove ensures we only clean up THIS exact promise instance
                        inFlight.remove(k, promise);
                    }
                });

                return promise;
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService dbPool = Executors.newFixedThreadPool(4);
        SingleFlightCache<String, String> hotelCache =
                new SingleFlightCache<>(dbPool, 5_000L, 500L, 1.0);

        int concurrentUsers = 10;
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(concurrentUsers);

        // Simulate 10 simultaneous requests hitting an unpopulated hot hotel key at the exact same instant
        for (int i = 0; i < concurrentUsers; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    startGate.await(); // Release all 10 threads simultaneously
                    String hotelDetails = hotelCache.getOrCompute("hotel:bangkok:101", () -> {
                        System.out.println(">>> [DB HIT] Executing expensive query by Thread: " + threadId);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ignored) {
                            Thread.currentThread().interrupt();
                        }
                        return "Luxury River Suite, $180/night";
                    });
                    System.out.println("Thread " + threadId + " received: " + hotelDetails);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    doneLatch.countDown();
                }
            }).start();
        }

        startGate.countDown(); // Fire all 10 requests together!
        doneLatch.await();
        dbPool.shutdown();
    }
}