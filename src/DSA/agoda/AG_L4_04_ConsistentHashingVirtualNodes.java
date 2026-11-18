package DSA.agoda;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ============================================================================
 * [STAFF L4 - 04] CONSISTENT HASHING WITH VIRTUAL NODES (Stateful Sharding)
 * ============================================================================
 *
 * JD TARGET:
 *   - "Distributed Architecture: Redis/Memcached cluster routing"
 *   - "Architecting, designing, and operating massive-scale distributed systems"
 *   - "Minimal data movement under dynamic scaling"
 *
 * PROBLEM:
 *   Design and implement a thread-safe, low-variance Consistent Hash Ring router
 *   (`ConsistentHashRouter<T>`) to partition stateful cache keys (e.g., hotel room
 *   locks, session state, pricing shards) across a dynamic cluster of `N` nodes.
 *
 *   Requirements:
 *     1. Uniform Load Distribution: Map each physical node to `V` Virtual Nodes
 *        (replicas) across a 32-bit unsigned ring `[0, 2^32 - 1]` so standard
 *        deviation of key distribution stays minimal (`< 5-10%` with `V = 150`).
 *     2. Clockwise Ring Routing (`getRoute(key)`): Hash the key onto the ring and
 *        route it to the first virtual node encountered moving clockwise
 *        (`ceilingEntry(hash)`), wrapping around to `firstEntry()` if past the max hash.
 *     3. Minimal Key Relocation on Topology Change: Adding or removing a physical
 *        node must relocate only `~1 / N` of keys (those owned by the affected node's
 *        clockwise arcs), leaving all other `~(N - 1) / N` keys untouched.
 *     4. Thread Safety Under Concurrent Reads & Topology Updates:
 *        - `MessageDigest` is NOT thread-safe! Sharing a single instance across
 *          reader threads without `ThreadLocal` corrupts digests under concurrency.
 *        - Protect ring mutations (`addNode` / `removeNode`) and lookups (`getRoute`)
 *          with a `ReentrantReadWriteLock` (or Copy-On-Write immutable `TreeMap` snapshot)
 *          so concurrent `getRoute` calls never hit `ConcurrentModificationException`
 *          or corrupt `TreeMap` Red-Black tree pointers during rebalancing!
 *
 * CONSTRAINTS & PRODUCTION EDGE CASES:
 *   - `numberOfReplicas >= 1` (typically `100` to `250` per physical node).
 *   - Empty ring (`N == 0`): `getRoute(key)` returns `null` cleanly instead of throwing `NPE`.
 *   - Hash collision on virtual node placement: Guard `removeNode` with `ring.remove(hash, node)`
 *     (2-arg conditional remove!) so removing Node A never accidentally deletes Node B's
 *     virtual node if two vnode hashes happen to collide on the 32-bit ring!
 *   - Replication Factor (`getPreferenceList(key, replicationFactor)`): Production storage
 *     engines (Dynamo / Cassandra / Agoda session replicas) walk clockwise collecting
 *     `R` *distinct physical* nodes so replicas never land on two vnodes of the same server.
 *
 * EXAMPLES:
 *   Example 1 (Minimal Key Movement on Node Failover):
 *     Cluster: ["redis-node-1", "redis-node-2", "redis-node-3"] with V = 150 vnodes each.
 *       - "hotel:1001" -> "redis-node-1"
 *       - "hotel:2045" -> "redis-node-2"
 *       - "hotel:9999" -> "redis-node-3"
 *     Event: "redis-node-2" crashes -> `router.removeNode("redis-node-2")`.
 *       - Keys owned by `redis-node-1` and `redis-node-3` DO NOT MOVE (~67% cache hit rate preserved!).
 *       - Only keys owned by `redis-node-2` ("hotel:2045") seamlessly shift clockwise to their
 *         next surviving neighbor on the ring.
 *
 * ============================================================================
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 * ============================================================================
 *   - Why Modulo Hashing (`hash(key) % N`) Fails in Production:
 *       * When `N` changes from `10` to `9` (1 node crashes), `hash(key) % 10 != hash(key) % 9`
 *         for **~90% of all keys**, triggering a massive database stampede across every shard.
 *
 *   - Why Raw Consistent Hashing (Without VNodes) Skews:
 *       * Placing only `N = 3` points on `[0, 2^32 - 1]` leaves wildly uneven arc lengths
 *         (one node might own 65% of the ring and hot-spot under flash-sale traffic).
 *       * Splitting each server into `V = 150` virtual tokens interleaves nodes finely
 *         around the circle, shrinking load variance from `O(1)` down to `O(1 / sqrt(V))`.
 *
 *   - Ring Geometry Visualization:
 *
 *                         0 / 2^32 (Wrap-around)
 *                            . - ~ ~ ~ - .
 *                        .-'    [VN_B1]    '-.
 *                      .'                     '.
 *                [VN_C2]        RING           [VN_A1]  <-- key "hotel:1001" lands here,
 *                    |        [0..2^32-1]        |          walks clockwise -> hits VN_A1!
 *                [VN_A2]                       [VN_C1]
 *                      '.                     .'
 *                        '-.    [VN_B2]    .-'
 *                            ' - . _ . - '
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Thread-Local MD5 Digest (`ThreadLocal<MessageDigest>`):
 *      - `MessageDigest.getInstance("MD5")` maintains internal mutable byte buffers.
 *      - Wrapping it in `ThreadLocal.withInitial(...)` avoids both `Synchronized` lock
 *        contention on `hash()` AND multi-threaded digest corruption.
 *      - Compute 32-bit unsigned ring coordinate `[0, 2^32 - 1]` from the first 4 bytes
 *        of the 128-bit MD5 digest (`(b3 << 24) | (b2 << 16) | (b1 << 8) | b0` masked with `0xFF`).
 *
 *   2. Adding a Physical Node (`addNode(T node)`):
 *      - Acquire `writeLock`.
 *      - For `i = 0` to `numberOfReplicas - 1`:
 *          * Compute `long vnodeHash = hash(node.toString() + "-VN-" + i)`.
 *          * Insert `ring.put(vnodeHash, node)` into the Red-Black `TreeMap`.
 *      - Release `writeLock` in `finally`.
 *
 *   3. Removing a Physical Node (`removeNode(T node)`):
 *      - Acquire `writeLock`.
 *      - For `i = 0` to `numberOfReplicas - 1`:
 *          * Compute `long vnodeHash = hash(node.toString() + "-VN-" + i)`.
 *          * Call 2-argument `ring.remove(vnodeHash, node)` so we only remove entries
 *            actually owned by `node` (safe against 32-bit hash collisions).
 *      - Release `writeLock` in `finally`.
 *
 *   4. Routing a Key (`getRoute(String key)`):
 *      - Acquire `readLock` (allows unlimited concurrent readers in parallel).
 *      - If `ring.isEmpty()`, return `null`.
 *      - Compute `long keyHash = hash(key)`.
 *      - Query `Map.Entry<Long, T> entry = ring.ceilingEntry(keyHash)` (`O(log(N * V))`):
 *          * Returns the smallest ring position `>= keyHash`.
 *          * If `entry == null` (`keyHash` is larger than every vnode hash on the ring),
 *            wrap clockwise past `2^32 - 1` back to `ring.firstEntry()`.
 *      - Return `entry.getValue()`.
 *
 *   5. Replica Preference List (`getReplicas(String key, int replicationFactor)`):
 *      - Walk clockwise from `keyHash` using `ring.tailMap(keyHash, true)` followed by
 *        `ring.headMap(keyHash, false)`, collecting distinct physical nodes into a
 *        `LinkedHashSet<T>` until `replicationFactor` physical servers are gathered.
 *
 * ============================================================================
 * VISUAL DRY RUN: 2 Nodes (A, B), V = 2 Virtual Nodes Each, 32-Bit Ring [0 .. 4.29B]
 * ============================================================================
 *   Step 1: Populate Ring (`TreeMap<Long, String>`)
 *     - hash("A-VN-0") = 500_000_000   ->  500M : Node A
 *     - hash("B-VN-0") = 1_400_000_000 -> 1400M : Node B
 *     - hash("A-VN-1") = 2_800_000_000 -> 2800M : Node A
 *     - hash("B-VN-1") = 3_900_000_000 -> 3900M : Node B
 *
 *   Step 2: Route Keys Clockwise (`ceilingEntry(keyHash)`)
 *     Key          | keyHash       | ceilingEntry(keyHash) | Wrap-Around? | Assigned Node
 *     -------------+---------------+-----------------------+--------------+--------------
 *     "hotel:1001" |   920_000_000 | 1400M -> Node B       | No           | Node B
 *     "hotel:2045" | 2_800_000_000 | 2800M -> Node A (exact| No           | Node A
 *     "hotel:9999" | 4_150_000_000 | null (> 3900M!)       | Yes -> 500M  | Node A
 *
 *   Step 3: Failover Node B (`removeNode("Node B")` removes 1400M and 3900M)
 *     - "hotel:1001" (920M) -> ceilingEntry(920M) is now 2800M -> Node A (migrated!)
 *     - "hotel:2045" (2800M)-> ceilingEntry(2800M) is STILL 2800M -> Node A (untouched!)
 *     - "hotel:9999" (4150M)-> wraps to 500M -> STILL Node A (untouched!)
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:
 *       * `addNode` / `removeNode`: `O(V log(N * V))` to insert/delete `V` vnodes in `TreeMap`.
 *       * `getRoute(key)`:          `O(log(N * V))` Red-Black tree ceiling lookup + `O(1)` MD5 hash.
 *       * `getReplicas(key, R)`:    `O(log(N * V) + R * V)` worst case (typically `O(log(N * V) + R)`).
 *   - Space:
 *       * `O(N * V)` entries stored in the `NavigableMap` ring.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would `hash(key) % N` do on scale-out/failover, and why is it catastrophic?
 *      (Rehashes `~(N - 1)/N` keys—90% on a 10-node cluster—causing simultaneous cache misses and DB overload.)
 *   2. What invariant must remain true after every topology mutation?
 *      (Every active physical node has up to `V` deterministic virtual positions in `ring`, and
 *       no removed node leaves stale vnode pointers behind.)
 *   3. Why were the concurrency fixes (`ThreadLocal<MessageDigest>` + `ReadWriteLock` + 2-arg `remove`) needed?
 *      - In the original code, `md` was a single instance field with NO synchronization on `getRoute()`!
 *        Two concurrent threads calling `getRoute()` simultaneously would call `md.reset()` and
 *        `md.digest()` concurrently, corrupting MD5 state or throwing `ArrayIndexOutOfBoundsException`!
 *      - Likewise, `getRoute()` read `ring` without a read lock while `addNode()` was `synchronized`,
 *        allowing concurrent `TreeMap` structural modifications to loop infinitely or throw `NPE`.
 *   4. Which edge cases change the control flow (empty ring `N == 0`, key hash greater than all
 *      vnode hashes wrapping to `firstEntry()`, exact hash match `ceilingEntry`, 32-bit vnode hash
 *      collision between two distinct nodes, `replicationFactor > N`)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_L4_04_ConsistentHashingVirtualNodes {

    public static class ConsistentHashRouter<T> {
        private final int numberOfReplicas;
        private final NavigableMap<Long, T> ring = new TreeMap<>();
        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

        // Thread-safe per-thread MD5 instance (avoids lock contention AND digest corruption!)
        private static final ThreadLocal<MessageDigest> MD5_DIGEST = ThreadLocal.withInitial(() -> {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("MD5 algorithm unavailable in JVM", e);
            }
        });

        public ConsistentHashRouter(int numberOfReplicas, Collection<T> nodes) {
            if (numberOfReplicas <= 0) {
                throw new IllegalArgumentException("numberOfReplicas must be positive");
            }
            this.numberOfReplicas = numberOfReplicas;
            if (nodes != null) {
                for (T node : nodes) {
                    addNode(node);
                }
            }
        }

        public void addNode(T node) {
            Objects.requireNonNull(node, "Node cannot be null");
            rwLock.writeLock().lock();
            try {
                for (int i = 0; i < numberOfReplicas; i++) {
                    long hash = hash(node.toString() + "-VN-" + i);
                    ring.put(hash, node);
                }
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        public void removeNode(T node) {
            if (node == null) return;
            rwLock.writeLock().lock();
            try {
                for (int i = 0; i < numberOfReplicas; i++) {
                    long hash = hash(node.toString() + "-VN-" + i);
                    // 2-arg remove prevents deleting another node's vnode on a rare 32-bit hash collision
                    ring.remove(hash, node);
                }
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        public T getRoute(String key) {
            Objects.requireNonNull(key, "Routing key cannot be null");
            rwLock.readLock().lock();
            try {
                if (ring.isEmpty()) {
                    return null;
                }

                long hash = hash(key);
                // Locate nearest virtual node clockwise (smallest ring position >= key hash)
                Map.Entry<Long, T> entry = ring.ceilingEntry(hash);
                if (entry == null) {
                    // Wrap clockwise past 2^32 - 1 back to the beginning of the ring
                    return ring.firstEntry().getValue();
                }
                return entry.getValue();
            } finally {
                rwLock.readLock().unlock();
            }
        }

        // Staff-Level Extension: Walk clockwise to collect R distinct physical replica nodes
        public List<T> getReplicas(String key, int replicationFactor) {
            Objects.requireNonNull(key, "Routing key cannot be null");
            if (replicationFactor <= 0) return Collections.emptyList();

            rwLock.readLock().lock();
            try {
                if (ring.isEmpty()) return Collections.emptyList();

                Set<T> distinctNodes = new LinkedHashSet<>(replicationFactor);
                long hash = hash(key);

                // 1. Walk clockwise from key's hash to the end of the ring
                for (T node : ring.tailMap(hash, true).values()) {
                    distinctNodes.add(node);
                    if (distinctNodes.size() == replicationFactor) {
                        return new ArrayList<>(distinctNodes);
                    }
                }

                // 2. Wrap around from 0 up to key's hash if more distinct physical nodes are needed
                for (T node : ring.headMap(hash, false).values()) {
                    distinctNodes.add(node);
                    if (distinctNodes.size() == replicationFactor) {
                        break;
                    }
                }

                return new ArrayList<>(distinctNodes);
            } finally {
                rwLock.readLock().unlock();
            }
        }

        // Unsigned 32-bit hash in [0, 2^32 - 1] derived from thread-local MD5
        private static long hash(String key) {
            MessageDigest md = MD5_DIGEST.get();
            md.reset();
            byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));
            return ((long) (digest[3] & 0xFF) << 24)
                    | ((long) (digest[2] & 0xFF) << 16)
                    | ((long) (digest[1] & 0xFF) << 8)
                    | ((long) (digest[0] & 0xFF));
        }
    }

    public static void main(String[] args) {
        List<String> nodes = Arrays.asList(
                "redis-node-1.agoda.internal",
                "redis-node-2.agoda.internal",
                "redis-node-3.agoda.internal"
        );

        ConsistentHashRouter<String> router = new ConsistentHashRouter<>(150, nodes);

        String k1 = "hotel:1001";
        String k2 = "hotel:2045";
        String k3 = "hotel:9999";

        System.out.println("Initial Routes:");
        System.out.println("  " + k1 + " -> " + router.getRoute(k1) + " | Replicas(2): " + router.getReplicas(k1, 2));
        System.out.println("  " + k2 + " -> " + router.getRoute(k2));
        System.out.println("  " + k3 + " -> " + router.getRoute(k3));

        // Simulate failover of node 2
        System.out.println("\nSimulating Failover: removing redis-node-2.agoda.internal...");
        router.removeNode("redis-node-2.agoda.internal");

        System.out.println("Post-Failover Routes (only keys on node-2 migrate):");
        System.out.println("  " + k1 + " -> " + router.getRoute(k1));
        System.out.println("  " + k2 + " -> " + router.getRoute(k2));
        System.out.println("  " + k3 + " -> " + router.getRoute(k3));
    }
}