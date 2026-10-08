package DSA.agoda;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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
 * SCENARIO / SYSTEM CONTEXT:
 *   Agoda shards hotel inventory locks and session storage across tens of cache
 *   nodes. When nodes crash or scale up during flash sales, standard `hash(key) % N`
 *   rehashes ~90% of all keys, wiping out cache hit ratios and dropping performance.
 *
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 *   - Consistent Hashing Ring:
 *     Arranges keys and server nodes on a virtual 32-bit integer ring $[0, 2^{32}-1]$.
 *     When a server is added/removed, only $1/N$ of keys are relocated.
 *   - Virtual Nodes (VNodes):
 *     Physical servers are mapped to $V$ virtual positions (e.g., 100-250 vnodes).
 *     This avoids "hot-spotting" and uneven data distribution caused by standard hash skew.
 *
 * COMPLEXITY:
 *   - Node lookup: O(log(N * V)) using Red-Black Tree (`TreeMap.tailMap`).
 *   - Space: O(N * V) where N is server count, V is virtual nodes count.
 */
public class AG_L4_04_ConsistentHashingVirtualNodes {

    public static class ConsistentHashRouter<T> {
        private final int numberOfReplicas;
        private final NavigableMap<Long, T> ring = new TreeMap<>();
        private final MessageDigest md;

        public ConsistentHashRouter(int numberOfReplicas, Collection<T> nodes) {
            this.numberOfReplicas = numberOfReplicas;
            try {
                this.md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            for (T node : nodes) {
                addNode(node);
            }
        }

        public synchronized void addNode(T node) {
            for (int i = 0; i < numberOfReplicas; i++) {
                long hash = hash(node.toString() + "-VN-" + i);
                ring.put(hash, node);
            }
        }

        public synchronized void removeNode(T node) {
            for (int i = 0; i < numberOfReplicas; i++) {
                long hash = hash(node.toString() + "-VN-" + i);
                ring.remove(hash);
            }
        }

        public T getRoute(String key) {
            if (ring.isEmpty()) return null;

            long hash = hash(key);
            // Locate nearest node clockwise on the ring
            Map.Entry<Long, T> entry = ring.ceilingEntry(hash);
            if (entry == null) {
                // Wrap around to beginning of ring
                return ring.firstEntry().getValue();
            }
            return entry.getValue();
        }

        private long hash(String key) {
            md.reset();
            byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));
            // 32-bit truncation from MD5 bytes
            return ((long) (digest[3] & 0xFF) << 24)
                 | ((long) (digest[2] & 0xFF) << 16)
                 | ((long) (digest[1] & 0xFF) << 8)
                 | ((long) (digest[0] & 0xFF));
        }
    }

    public static void main(String[] args) {
        List<String> nodes = Arrays.asList("redis-node-1.agoda.internal", 
                                           "redis-node-2.agoda.internal", 
                                           "redis-node-3.agoda.internal");

        ConsistentHashRouter<String> router = new ConsistentHashRouter<>(150, nodes);

        System.out.println("Hotel 1001 mapped to: " + router.getRoute("hotel:1001"));
        System.out.println("Hotel 2045 mapped to: " + router.getRoute("hotel:2045"));
        System.out.println("Hotel 9999 mapped to: " + router.getRoute("hotel:9999"));

        // Failover node 2
        router.removeNode("redis-node-2.agoda.internal");
        System.out.println("Hotel 1001 after failover: " + router.getRoute("hotel:1001"));
    }
}