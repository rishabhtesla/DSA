package DSA.agoda;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ============================================================================
 * [STAFF L4 - 05] EXACTLY-ONCE IDEMPOTENT KAFKA CONSUMER + TRANSACTIONAL OUTBOX
 * ============================================================================
 * 
 * JD TARGET:
 *   - "Distributed Systems & Streaming: Apache Kafka with strict consistency"
 *   - "High-throughput booking, search, and inventory updates"
 *   - "Resilient asynchronous messaging patterns"
 *
 * SCENARIO / SYSTEM CONTEXT:
 *   Kafka guarantees "At-Least-Once" delivery under network rebalancing or broker
 *   restarts. Duplicate inventory subtraction events (e.g., booking the same hotel room)
 *   can result in overselling inventory. Staff engineers must ensure business logic 
 *   is strictly idempotent.
 *
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 *   - Deduplication Strategies:
 *     1. Distributed Redis Set: Fast, but not atomic with database writes. Can lead to
 *        ghost state if Redis commits but Postgres rolls back.
 *     2. Transactional Outbox / Inbox Table:
 *        Store incoming message IDs inside the same SQL transaction as the business entity:
 *        `INSERT INTO processed_events (event_id) VALUES (?) ON CONFLICT DO NOTHING;`
 *        If insertion fails (row affected == 0), the event is a duplicate. Acknowledge 
 *        the offset and skip processing.
 *
 * COMPLEXITY:
 *   - Processing Time: O(1) database transactional constraint check.
 *   - Space: O(M) retention of event IDs for TTL window (e.g., 7 days).
 */
public class AG_L4_05_OutboxDeduplicatingKafkaConsumer {

    public static class InventoryBookingService {
        // Mock SQL In-Memory transactional state
        private final ConcurrentHashMap<String, Boolean> processedEventStore = new ConcurrentHashMap<>();
        private final ConcurrentHashMap<String, Integer> hotelInventory = new ConcurrentHashMap<>();

        public InventoryBookingService() {
            hotelInventory.put("hotel:bkk:501", 10); // 10 rooms available
        }

        /**
         * Simulates consumption of Kafka event payload
         */
        public synchronized boolean onMessageReceived(String eventId, String hotelId, int roomsToDeduct) {
            // Idempotency check: atomic insert in the same transaction
            if (processedEventStore.putIfAbsent(eventId, Boolean.TRUE) != null) {
                System.out.println("[WARN] Duplicate Kafka event detected: " + eventId + ". Skipping execution.");
                return false; // Acknowledge message to Kafka, do not execute
            }

            // Execute core business state change
            int currentRooms = hotelInventory.getOrDefault(hotelId, 0);
            if (currentRooms >= roomsToDeduct) {
                hotelInventory.put(hotelId, currentRooms - roomsToDeduct);
                System.out.println("[SUCCESS] Booked " + roomsToDeduct + " rooms. Remaining: " + (currentRooms - roomsToDeduct));
                return true;
            } else {
                System.out.println("[FAILED] Insufficient inventory for event: " + eventId);
                return false;
            }
        }

        public int getAvailableRooms(String hotelId) {
            return hotelInventory.getOrDefault(hotelId, 0);
        }
    }

    public static void main(String[] args) {
        InventoryBookingService service = new InventoryBookingService();

        // Kafka sends message successfully
        service.onMessageReceived("evt_tx_90123", "hotel:bkk:501", 2);

        // Network glitch triggers Kafka retry / consumer group rebalance -> Duplicate arrives!
        service.onMessageReceived("evt_tx_90123", "hotel:bkk:501", 2);

        System.out.println("Final inventory count: " + service.getAvailableRooms("hotel:bkk:501")); 
        // Expected: 8 (NOT 6!)
    }
}