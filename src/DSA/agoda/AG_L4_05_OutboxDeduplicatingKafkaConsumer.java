package DSA.agoda;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

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
 * PROBLEM:
 *   Design and implement a strictly idempotent Kafka consumer (`InventoryBookingService`)
 *   that processes hotel room reservation events (`eventId`, `hotelId`, `roomsToDeduct`)
 *   under Kafka's "At-Least-Once" delivery guarantee without overselling inventory
 *   or losing downstream domain events.
 *
 *   Requirements:
 *     1. Atomic Inbox Deduplication + Business Mutation:
 *        - Recording `eventId` in the `processed_events` inbox table, deducting room
 *          inventory, and appending a downstream confirmation event into the `outbox`
 *          table MUST execute inside **one atomic local ACID transaction**.
 *        - If a retry or consumer-rebalance duplicate arrives with an already-committed
 *          `eventId`, it must be skipped idempotently and return the original outcome
 *          (or safely acknowledge the Kafka offset) without re-deducting rooms.
 *     2. Rollback Safety on Transient Failures:
 *        - If an unexpected runtime failure occurs mid-transaction before commit,
 *          NEITHER the inventory deduction NOR the `processed_events` inbox record
 *          may persist—otherwise subsequent Kafka retries would falsely see the
 *          failed event as "already processed" and drop the booking forever!
 *     3. Fine-Grained Partition Concurrency (No Global Lock Bottleneck):
 *        - Avoid a coarse `synchronized` method lock across all hotels; lock per
 *          `hotelId` (striped / per-entity lock) so bookings for `"hotel:bkk:501"`
 *          and `"hotel:tyo:808"` execute in parallel with zero contention.
 *     4. Transactional Outbox Relay:
 *        - Emit downstream events (e.g., `"BOOKING_CONFIRMED"` / `"BOOKING_REJECTED"`)
 *          into an atomic `outbox` log within the same transaction so a CDC/relay
 *          worker can publish to downstream Kafka topics without dual-write inconsistency.
 *
 * CONSTRAINTS & PRODUCTION EDGE CASES:
 *   - `roomsToDeduct > 0` (reject non-positive quantities).
 *   - Business Failure vs. Infrastructure Failure:
 *       * Business failure ("Insufficient inventory"): A valid deterministic outcome!
 *         Commit `eventId` in the inbox as `REJECTED_NO_INVENTORY` and write a
 *         `"BOOKING_REJECTED"` outbox event so retries don't spam downstream services.
 *       * Transient infrastructure failure (simulated DB exception before commit):
 *         Roll back BOTH `processedEvents` and `hotelInventory` so Kafka retries cleanly.
 *   - Inbox Retention / TTL Cleanup:
 *       * Store `processedAt` timestamp alongside `eventId` so a scheduled partition
 *         drop / TTL sweep can prune records older than the Kafka retention window (e.g., 7 days).
 *
 * EXAMPLES:
 *   Example 1 (Kafka Retry / Rebalance Duplicate):
 *     Initial inventory: `"hotel:bkk:501" -> 10 rooms`.
 *       - Event 1 (`evt_tx_90123`, deduct 2) -> Inbox empty -> deducts 2 (8 left),
 *         commits inbox + outbox `BOOKING_CONFIRMED` -> returns `SUCCESS`.
 *       - Event 2 (`evt_tx_90123`, deduct 2) [Duplicate!] -> Inbox hit -> skips deduction,
 *         inventory remains `8` (NOT `6`!) -> returns `DUPLICATE_IGNORED`.
 *
 *   Example 2 (Transient Crash Rollback & Recovery):
 *     Initial inventory: `"hotel:bkk:501" -> 8 rooms`.
 *       - Event 3 (`evt_tx_90124`, deduct 3) crashes before DB commit -> transaction rolls
 *         back completely (`evt_tx_90124` is NOT in inbox; rooms remain `8`).
 *       - Kafka redelivers `evt_tx_90124` -> succeeds cleanly -> rooms become `5`!
 *
 * ============================================================================
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 * ============================================================================
 *   - Why "Dual Writes" (Redis Set + Postgres DB, or DB Commit + Direct Kafka Produce) Fail:
 *       * Pattern A (Redis `SETNX` + Postgres): If Redis succeeds and Postgres crashes,
 *         retries see the key in Redis and silently drop the booking (data loss).
 *         If Postgres commits and Redis times out, retries double-deduct rooms (oversell!).
 *       * Pattern B (Postgres Commit -> `kafkaProducer.send()`): If the JVM crashes right
 *         after Postgres commits and before `kafkaProducer.send()` completes, the payment/email
 *         service never receives the booking event.
 *
 *   - The Staff-Level Solution (Transactional Inbox + Transactional Outbox):
 *
 *       [Kafka Topic] ---> [Consumer]
 *                             |
 *                             v  (Single Local ACID SQL Transaction)
 *                    +---------------------------------------------------+
 *                    | 1. INSERT INTO processed_events (event_id)        |
 *                    |      ON CONFLICT DO NOTHING; (0 rows => Duplicate)|
 *                    | 2. UPDATE hotels SET rooms = rooms - :qty         |
 *                    |      WHERE id = :hotelId AND rooms >= :qty;       |
 *                    | 3. INSERT INTO outbox_events (id, topic, payload) |
 *                    | COMMIT;                                           |
 *                    +---------------------------------------------------+
 *                             |
 *                             v  (Async CDC via Debezium / Log Tailer)
 *                    [Downstream Kafka Topic: booking-events]
 *
 *   - Zero distributed 2PC (Two-Phase Commit) needed—everything rides on a single
 *     database's local ACID guarantees!
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Per-Hotel Striped Locking (`ReentrantLock` per `hotelId`):
 *      - Simulates row-level database locking (`SELECT ... FOR UPDATE` / atomic `UPDATE`)
 *        on the target hotel row without blocking unrelated hotels.
 *
 *   2. Step A — Atomic Idempotency Gate (Transactional Inbox):
 *      - Check `processedEventStore.containsKey(eventId)`.
 *      - If present, this `eventId` was already committed in a prior transaction ->
 *        immediately return `ProcessingResult.DUPLICATE_IGNORED` (caller commits Kafka offset).
 *
 *   3. Step B — Business Invariant & Atomic State Mutation:
 *      - Read `currentRooms = hotelInventory.getOrDefault(hotelId, 0)`.
 *      - If `currentRooms >= roomsToDeduct`:
 *          * Compute `updatedRooms = currentRooms - roomsToDeduct`.
 *          * Prepare outbox payload: `"BOOKING_CONFIRMED"`.
 *      - Else:
 *          * Keep `updatedRooms = currentRooms` (deterministic business rejection).
 *          * Prepare outbox payload: `"BOOKING_REJECTED_INSUFFICIENT_ROOMS"`.
 *
 *   4. Step C — Simulated Atomic Commit (Inbox + Inventory + Outbox):
 *      - If a transient DB/network fault occurs before commit, throw exception BEFORE
 *        mutating `processedEventStore`, `hotelInventory`, or `outboxLog` (ensuring
 *        all-or-nothing atomicity so Kafka will safely redeliver!).
 *      - Otherwise, atomically commit all three state updates together:
 *          1. `hotelInventory.put(hotelId, updatedRooms)`
 *          2. `processedEventStore.put(eventId, new InboxRecord(eventId, outcome, Instant.now()))`
 *          3. `outboxLog.add(new OutboxEvent(eventId, outboxType, hotelId, roomsToDeduct))`
 *
 *   5. Step D — Offset Acknowledgment Contract:
 *      - Only commit the Kafka consumer offset AFTER `onMessageReceived` returns normally
 *        (whether `BOOKED`, `REJECTED_NO_INVENTORY`, or `DUPLICATE_IGNORED`).
 *      - Never commit the offset if an exception is thrown!
 *
 * ============================================================================
 * VISUAL DRY RUN: Initial Inventory("hotel:bkk:501") = 10
 * ============================================================================
 *   Msg # | Event ID     | Deduct | Inbox Hit? | DB Tx Outcome | Inventory | Outbox Appended          | Commit Offset?
 *   ------+--------------+--------+------------+---------------+-----------+--------------------------+---------------
 *     1   | evt_tx_90123 |   2    | No         | COMMITTED     | 10 -> 8   | BOOKING_CONFIRMED        | Yes
 *     2   | evt_tx_90123 |   2    | YES!       | SKIPPED (Dup) | 8 (safe!) | (none)                   | Yes
 *     3   | evt_tx_90124 |   3    | No         | ROLLBACK (Err)| 8 (safe!) | (none)                   | NO (Retry!)
 *     4   | evt_tx_90124 |   3    | No         | COMMITTED     | 8 -> 5    | BOOKING_CONFIRMED        | Yes
 *     5   | evt_tx_90125 |   9    | No         | COMMITTED     | 5 (insufficient) | BOOKING_REJECTED  | Yes
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(1) per message (amortized `ConcurrentHashMap` inbox lookup + per-hotel lock).
 *   - Space: O(H + E) where `H` is number of hotels and `E` is number of retained
 *            `eventId` records within the inbox TTL window (e.g., 7 days).
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would a non-idempotent consumer do during a Kafka consumer rebalance?
 *      (Re-process uncommitted partition messages from the last committed offset, deducting room
 *       inventory twice and overselling rooms!)
 *   2. What invariant must remain true after every message execution?
 *      (`processedEventStore` contains `eventId` IF AND ONLY IF its corresponding inventory
 *       update and outbox event were durably committed.)
 *   3. Why was putting `processedEventStore.putIfAbsent(...)` BEFORE a potentially failing DB
 *      operation dangerous without rollback?
 *      (In the original code, if `putIfAbsent` succeeded in memory/Redis and the subsequent DB
 *       inventory write crashed, the event would be permanently marked "processed" and lost!)
 *   4. Which edge cases change the control flow (duplicate `eventId`, insufficient inventory vs.
 *      transient DB exception, concurrent bookings on the SAME hotel vs. DIFFERENT hotels,
 *      invalid `roomsToDeduct <= 0`, inbox TTL expiry)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_L4_05_OutboxDeduplicatingKafkaConsumer {

    public enum ProcessingResult {
        BOOKED,
        REJECTED_NO_INVENTORY,
        DUPLICATE_IGNORED
    }

    public static final class InboxRecord {
        final String eventId;
        final ProcessingResult outcome;
        final Instant processedAt;

        InboxRecord(String eventId, ProcessingResult outcome, Instant processedAt) {
            this.eventId = eventId;
            this.outcome = outcome;
            this.processedAt = processedAt;
        }
    }

    public static final class OutboxEvent {
        final String eventId;
        final String eventType;
        final String hotelId;
        final int rooms;

        OutboxEvent(String eventId, String eventType, String hotelId, int rooms) {
            this.eventId = eventId;
            this.eventType = eventType;
            this.hotelId = hotelId;
            this.rooms = rooms;
        }

        @Override
        public String toString() {
            return "OutboxEvent{id='" + eventId + "', type='" + eventType +
                    "', hotel='" + hotelId + "', rooms=" + rooms + "}";
        }
    }

    public static class InventoryBookingService {
        // Transactional Inbox table: eventId -> committed InboxRecord
        private final ConcurrentHashMap<String, InboxRecord> processedEventStore = new ConcurrentHashMap<>();

        // Business Entity table: hotelId -> available room count
        private final ConcurrentHashMap<String, Integer> hotelInventory = new ConcurrentHashMap<>();

        // Transactional Outbox table: downstream events written atomically with business state
        private final List<OutboxEvent> outboxLog = new ArrayList<>();

        // Per-hotel row locks (avoids global synchronized bottleneck across unrelated hotels)
        private final ConcurrentHashMap<String, ReentrantLock> hotelLocks = new ConcurrentHashMap<>();

        public InventoryBookingService() {
            hotelInventory.put("hotel:bkk:501", 10); // 10 rooms initially available
        }

        /**
         * Processes a single Kafka booking command inside an atomic Inbox + Inventory + Outbox transaction.
         * Caller commits the Kafka consumer offset ONLY when this method returns without throwing.
         */
        public ProcessingResult onMessageReceived(
                String eventId, String hotelId, int roomsToDeduct, boolean simulateTransientDbFailure) {

            Objects.requireNonNull(eventId, "eventId must not be null");
            Objects.requireNonNull(hotelId, "hotelId must not be null");
            if (roomsToDeduct <= 0) {
                throw new IllegalArgumentException("roomsToDeduct must be positive: " + roomsToDeduct);
            }

            // Fast lock-free idempotency check before acquiring per-hotel row lock
            if (processedEventStore.containsKey(eventId)) {
                System.out.println("[WARN] Duplicate Kafka event detected: " + eventId + ". Skipping execution.");
                return ProcessingResult.DUPLICATE_IGNORED;
            }

            // Acquire fine-grained per-hotel lock (models DB row-level lock SELECT ... FOR UPDATE)
            ReentrantLock rowLock = hotelLocks.computeIfAbsent(hotelId, id -> new ReentrantLock());
            rowLock.lock();
            try {
                // Step 1: Double-check Inbox inside transaction boundary (INSERT ... ON CONFLICT DO NOTHING)
                if (processedEventStore.containsKey(eventId)) {
                    System.out.println("[WARN] Concurrent duplicate detected inside tx: " + eventId);
                    return ProcessingResult.DUPLICATE_IGNORED;
                }

                // Step 2: Evaluate business invariant (enough rooms available?)
                int currentRooms = hotelInventory.getOrDefault(hotelId, 0);
                final ProcessingResult outcome;
                final int nextRooms;
                final String outboxEventType;

                if (currentRooms >= roomsToDeduct) {
                    outcome = ProcessingResult.BOOKED;
                    nextRooms = currentRooms - roomsToDeduct;
                    outboxEventType = "BOOKING_CONFIRMED";
                } else {
                    outcome = ProcessingResult.REJECTED_NO_INVENTORY;
                    nextRooms = currentRooms;
                    outboxEventType = "BOOKING_REJECTED_INSUFFICIENT_ROOMS";
                }

                // Step 3: Simulate transient DB error BEFORE commit -> full rollback (nothing persisted!)
                if (simulateTransientDbFailure) {
                    throw new RuntimeException("Simulated DB connection drop before COMMIT for event: " + eventId);
                }

                // Step 4: Atomic Local ACID Commit (Business State + Inbox + Outbox together)
                hotelInventory.put(hotelId, nextRooms);
                processedEventStore.put(eventId, new InboxRecord(eventId, outcome, Instant.now()));
                synchronized (outboxLog) {
                    outboxLog.add(new OutboxEvent(eventId, outboxEventType, hotelId, roomsToDeduct));
                }

                System.out.println("[" + outcome + "] Event: " + eventId +
                        " | Hotel: " + hotelId + " | Remaining rooms: " + nextRooms);
                return outcome;
            } finally {
                rowLock.unlock();
            }
        }

        public int getAvailableRooms(String hotelId) {
            return hotelInventory.getOrDefault(hotelId, 0);
        }

        public List<OutboxEvent> getOutboxSnapshot() {
            synchronized (outboxLog) {
                return new ArrayList<>(outboxLog);
            }
        }
    }

    public static void main(String[] args) {
        InventoryBookingService service = new InventoryBookingService();

        // 1. Initial booking succeeds (10 -> 8 rooms)
        service.onMessageReceived("evt_tx_90123", "hotel:bkk:501", 2, false);

        // 2. Network glitch triggers Kafka retry / rebalance -> Duplicate arrives & is safely ignored!
        service.onMessageReceived("evt_tx_90123", "hotel:bkk:501", 2, false);

        // 3. Transient DB crash before commit -> full rollback; Kafka redelivery succeeds (8 -> 5 rooms)!
        try {
            service.onMessageReceived("evt_tx_90124", "hotel:bkk:501", 3, true);
        } catch (RuntimeException ex) {
            System.out.println("[ROLLBACK] " + ex.getMessage());
        }
        service.onMessageReceived("evt_tx_90124", "hotel:bkk:501", 3, false);

        System.out.println("\nFinal inventory count: " + service.getAvailableRooms("hotel:bkk:501") + " | Expected: 5");
        System.out.println("Outbox events emitted: " + service.getOutboxSnapshot());
    }
}