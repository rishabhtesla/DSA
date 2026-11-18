package DSA.agoda;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * ============================================================================
 * [STAFF L4 - 03] MECHANICAL SYMPATHY: FALSE SHARING & LOCK-FREE RING BUFFER
 * ============================================================================
 *
 * JD TARGET:
 *   - "Identify and resolve system bottlenecks across the JVM, CPU cache locality"
 *   - "Streaming messaging backbones handling high-throughput booking"
 *
 * PROBLEM:
 *   Design and implement a bounded, lock-free Single-Producer Single-Consumer (SPSC)
 *   Ring Buffer (`LockFreeRingBuffer<T>`) capable of passing millions of real-time
 *   price tick events per second between threads with sub-microsecond latency.
 *
 *   Requirements:
 *     1. Bounded Power-of-Two Capacity: Reject non-power-of-two capacities so slot
 *        indexing uses fast bitwise masking (`sequence & (capacity - 1)`) instead
 *        of expensive integer division (`%`).
 *     2. Cache-Line Padding (Mechanical Sympathy): Isolate `writeCursor` and
 *        `readCursor` onto separate 64-byte CPU L1 cache lines to eliminate
 *        False Sharing (MESI cache-invalidation storms across CPU cores).
 *     3. Safe Publication & Memory Visibility: Guarantee that a consumer never
 *        observes a half-published or `null` slot after the producer advances the
 *        write cursor, and null out consumed slots promptly to avoid GC memory leaks.
 *     4. Backpressure Signaling: `offer(item)` returns `false` when full; `poll()`
 *        returns `null` when empty.
 *
 * CONSTRAINTS & PRODUCTION EDGE CASES:
 *   - Capacity `N` MUST be a power of two (`N >= 2`, `Integer.bitCount(N) == 1`).
 *   - Null elements are disallowed (`Objects.requireNonNull(item)`) because `null`
 *     denotes an empty/unpublished slot in the ring.
 *   - Critical Concurrency Bug Fixed (Publication Ordering!):
 *       * In the original `offer()`, `writeCursor` was incremented via CAS *BEFORE*
 *         writing `entries[slot] = item`! That creates a fatal race window where
 *         the Consumer sees `readCursor < writeCursor`, reads `entries[slot]` BEFORE
 *         the Producer finishes storing `item`, and returns `null` (losing the event!).
 *       * Fix: In SPSC, write `entries.lazySet(slot, item)` FIRST, and publish
 *         `writeCursor.lazySet(currentWrite + 1)` SECOND (release-store semantics).
 *         Likewise in `poll()`, read `item` FIRST, null out `entries.lazySet(slot, null)`,
 *         and advance `readCursor.lazySet(currentRead + 1)` LAST!
 *
 * EXAMPLES:
 *   Example 1 (Basic Enqueue, Dequeue & Empty Backpressure):
 *     Buffer capacity = 4 (`mask = 3 = 0b0011`):
 *       - offer("Tick-1") -> writes slot (0 & 3) = 0, writeCursor becomes 1 -> true
 *       - offer("Tick-2") -> writes slot (1 & 3) = 1, writeCursor becomes 2 -> true
 *       - poll()          -> reads  slot (0 & 3) = 0, readCursor  becomes 1 -> "Tick-1"
 *       - poll()          -> reads  slot (1 & 3) = 1, readCursor  becomes 2 -> "Tick-2"
 *       - poll()          -> readCursor (2) == writeCursor (2) (empty)      -> null
 *
 *   Example 2 (Wrap-Around & Full Backpressure):
 *     Buffer capacity = 2 (`mask = 1`):
 *       - offer("A") -> true  (write=1, read=0)
 *       - offer("B") -> true  (write=2, read=0 -> `write - read == 2 == capacity` -> FULL!)
 *       - offer("C") -> false (backpressure applied; producer drops or retries)
 *       - poll()     -> "A"   (write=2, read=1 -> slot 0 freed!)
 *       - offer("C") -> true  (writes slot `2 & 1 = 0`, write=3, read=1)
 *
 * ============================================================================
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 * ============================================================================
 *   - Why `ArrayBlockingQueue` Bottlenecks at Millions of Ticks/Sec:
 *       1. Lock Contention: Every `offer` and `poll` acquires a single `ReentrantLock`,
 *          forcing OS kernel futex waits and context switches when threads collide.
 *       2. False Sharing: In standard queues, `putIndex` and `takeIndex` sit adjacent
 *          in heap memory—inside the **same 64-byte L1 cache line**:
 *
 *            +----------------------- 64-Byte CPU L1 Cache Line -----------------------+
 *            | [Object Header: 12B] | [writeCursor: 8B] | [readCursor: 8B] | [36B ...] |
 *            +-------------------------------------------------------------------------+
 *                   ^ Core 0 (Producer) writes here         ^ Core 1 (Consumer) writes here
 *                   ==> Hardware MESI protocol invalidates Core 1's entire cache line!
 *
 *       3. Solution — Cache-Line Padding (64-Byte Isolation):
 *          Pad `PaddedAtomicLong` with 7 dummy `long`s (7 * 8 = 56 bytes) before and
 *          after the 8-byte value so `writeCursor` and `readCursor` NEVER share a
 *          64-byte cache line, eliminating cross-core cache-coherency stalls.
 *
 *   - Why `lazySet` (Release Store) Beats Full `volatile` Write (`set` / `CAS`) in SPSC:
 *       * A full `volatile` write (`LOCK XCHG` on x86) flushes the CPU Store Buffer
 *         immediately (~10-20 ns stall).
 *       * In SPSC, only ONE thread ever writes `writeCursor`, and only ONE thread ever
 *         writes `readCursor`—so we don't even need `compareAndSet`! Using `lazySet`
 *         (Store-Release barrier) guarantees all prior array writes (`entries[slot] = item`)
 *         become visible BEFORE the updated cursor becomes visible, at nearly plain-write speed!
 *
 *   - Why Power-of-Two Capacity?
 *       * Integer modulo `seq % capacity` compiles to an `IDIV` CPU instruction (~20-40 cycles).
 *       * When `capacity = 2^k`, `seq & (capacity - 1)` compiles to a single-cycle `AND`
 *         instruction (~1 cycle) AND handles monotonic `long` sequence numbers cleanly.
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Cache-Line Padded Cursor (`PaddedAtomicLong`):
 *      - Includes pre-padding (`p1..p7`) + `AtomicLong` value + post-padding (`p8..p14`)
 *        so adjacent fields on the heap can never share a 64-byte cache line with it.
 *      - Also caches the opposite cursor locally (`cachedReadCursor` on Producer,
 *        `cachedWriteCursor` on Consumer) so neither core even reads the other core's
 *        cache line unless the local cached bound looks full/empty! (Classic LMAX Disruptor trick.)
 *
 *   2. Producer Enqueue (`offer(T item)`):
 *      - Reject `null` items (`Objects.requireNonNull(item)`).
 *      - Read `currentWrite = writeCursor.get()` (producer-owned).
 *      - Compute wrap boundary: `wrapPoint = currentWrite - capacity`.
 *      - Fast-path full check: If `cachedReadCursor <= wrapPoint`:
 *          * Refresh `cachedReadCursor = readCursor.get()`.
 *          * If still `cachedReadCursor <= wrapPoint`, buffer is genuinely FULL -> return `false`.
 *      - Compute ring index: `int slot = (int) (currentWrite & mask)`.
 *      - **Step A (Store Payload First):** `entries.lazySet(slot, item)`.
 *      - **Step B (Publish Cursor Second):** `writeCursor.lazySet(currentWrite + 1)`.
 *      - Return `true`.
 *
 *   3. Consumer Dequeue (`poll()`):
 *      - Read `currentRead = readCursor.get()` (consumer-owned).
 *      - Fast-path empty check: If `currentRead >= cachedWriteCursor`:
 *          * Refresh `cachedWriteCursor = writeCursor.get()`.
 *          * If still `currentRead >= cachedWriteCursor`, buffer is genuinely EMPTY -> return `null`.
 *      - Compute ring index: `int slot = (int) (currentRead & mask)`.
 *      - **Step A (Read Payload First):** `T item = entries.get(slot)`.
 *      - **Step B (Clear Slot for GC):** `entries.lazySet(slot, null)`.
 *      - **Step C (Advance Cursor Last):** `readCursor.lazySet(currentRead + 1)`.
 *      - Return `item`.
 *
 * ============================================================================
 * VISUAL DRY RUN: Capacity = 4 (mask = 3 = 0b11)
 * ============================================================================
 *   Step | Op          | writeCursor | readCursor | Slot (`seq & 3`) | Ring Array State       | Return
 *   -----+-------------+-------------+------------+------------------+------------------------+---------
 *    0   | Init        |      0      |     0      |        -         | [ null, null, null, null ] | -
 *    1   | offer("T1") |   0 -> 1    |     0      |   0 & 3 = 0      | [ "T1", null, null, null ] | true
 *    2   | offer("T2") |   1 -> 2    |     0      |   1 & 3 = 1      | [ "T1", "T2", null, null ] | true
 *    3   | offer("T3") |   2 -> 3    |     0      |   2 & 3 = 2      | [ "T1", "T2", "T3", null ] | true
 *    4   | offer("T4") |   3 -> 4    |     0      |   3 & 3 = 3      | [ "T1", "T2", "T3", "T4" ] | true
 *    5   | offer("T5") |      4      |     0      | 4 - 0 >= 4 FULL! | [ "T1", "T2", "T3", "T4" ] | false
 *    6   | poll()      |      4      |  0 -> 1    |   0 & 3 = 0      | [ null, "T2", "T3", "T4" ] | "T1"
 *    7   | offer("T5") |   4 -> 5    |     1      |   4 & 3 = 0      | [ "T5", "T2", "T3", "T4" ] | true
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(1) wait-free (zero locks, zero CAS spin loops in SPSC, ~5-15 ns per op).
 *   - Space: O(Capacity) fixed pre-allocated `AtomicReferenceArray` (zero runtime GC allocation).
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would a naive lock-based queue do, and where does it bottleneck?
 *      (`ArrayBlockingQueue` locks a single `ReentrantLock` on both ends, causing thread contention
 *       and L1 cache-line ping-pong between producer and consumer cores.)
 *   2. What invariant must remain true after every operation?
 *      (`0 <= writeCursor - readCursor <= capacity`, and every slot in `[readCursor, writeCursor - 1]`
 *       holds a non-null published item while all other slots are `null`.)
 *   3. Why is the publication order (`entries[slot] = item` BEFORE `writeCursor + 1`) mandatory?
 *      (Advancing `writeCursor` before storing `item` exposes an uninitialized slot to the consumer
 *       thread, causing lost messages or reading stale references from a previous lap!)
 *   4. Which edge cases change the control flow (non-power-of-two capacity, `null` item insertion,
 *      buffer completely full backpressure, buffer completely empty poll, 32-bit `int` overflow
 *      of sequence numbers -> prevented by using 64-bit `long` cursors)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_L4_03_BoundedRingBufferLMAX {

    // Cache-line isolated AtomicLong (surrounded by 56-byte pre/post padding to prevent False Sharing)
    static final class PaddedAtomicLong extends AtomicLong {
        @SuppressWarnings("unused")
        public volatile long p1, p2, p3, p4, p5, p6, p7; // 56 bytes pre-padding

        public PaddedAtomicLong(long initialValue) {
            super(initialValue);
        }

        @SuppressWarnings("unused")
        public volatile long p8, p9, p10, p11, p12, p13, p14; // 56 bytes post-padding
    }

    /**
     * Wait-free, cache-line padded Single-Producer Single-Consumer (SPSC) Ring Buffer
     * inspired by LMAX Disruptor / JCTools SpscArrayQueue.
     */
    public static class LockFreeRingBuffer<T> {
        private final AtomicReferenceArray<T> entries;
        private final int capacity;
        private final int mask;

        // Producer-owned state (isolated on its own cache line)
        private final PaddedAtomicLong writeCursor = new PaddedAtomicLong(0L);
        private long cachedReadCursor = 0L; // Producer-local cache of readCursor to avoid cross-core reads

        // Consumer-owned state (isolated on its own cache line)
        private final PaddedAtomicLong readCursor = new PaddedAtomicLong(0L);
        private long cachedWriteCursor = 0L; // Consumer-local cache of writeCursor to avoid cross-core reads

        public LockFreeRingBuffer(int powerOfTwoCapacity) {
            if (powerOfTwoCapacity <= 0 || Integer.bitCount(powerOfTwoCapacity) != 1) {
                throw new IllegalArgumentException("Capacity must be a positive power of two, got: " + powerOfTwoCapacity);
            }
            this.capacity = powerOfTwoCapacity;
            this.mask = powerOfTwoCapacity - 1;
            this.entries = new AtomicReferenceArray<>(powerOfTwoCapacity);
        }

        // Called exclusively by the single Producer thread
        public boolean offer(T item) {
            if (item == null) {
                throw new NullPointerException("Null items are not permitted in LockFreeRingBuffer");
            }

            final long currentWrite = writeCursor.get();
            final long wrapPoint = currentWrite - capacity;

            // Step 1: Check cached consumer progress first to avoid cross-core cache-line read
            if (cachedReadCursor <= wrapPoint) {
                cachedReadCursor = readCursor.get();
                if (cachedReadCursor <= wrapPoint) {
                    return false; // Buffer is full -> apply backpressure
                }
            }

            // Step 2: Store item into ring slot BEFORE advancing writeCursor (Release publication!)
            final int slot = (int) (currentWrite & mask);
            entries.lazySet(slot, item);

            // Step 3: Publish updated writeCursor so Consumer sees both the new index AND the item
            writeCursor.lazySet(currentWrite + 1);
            return true;
        }

        // Called exclusively by the single Consumer thread
        public T poll() {
            final long currentRead = readCursor.get();

            // Step 1: Check cached producer progress first to avoid cross-core cache-line read
            if (currentRead >= cachedWriteCursor) {
                cachedWriteCursor = writeCursor.get();
                if (currentRead >= cachedWriteCursor) {
                    return null; // Buffer is empty
                }
            }

            // Step 2: Read item BEFORE advancing readCursor so Producer cannot overwrite this slot yet!
            final int slot = (int) (currentRead & mask);
            final T item = entries.get(slot);

            // Step 3: Null out slot immediately so GC can reclaim processed event objects
            entries.lazySet(slot, null);

            // Step 4: Publish updated readCursor to free this slot for Producer wrap-around
            readCursor.lazySet(currentRead + 1);
            return item;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockFreeRingBuffer<String> ringBuffer = new LockFreeRingBuffer<>(4);

        System.out.println("Offer 1: " + ringBuffer.offer("FlightPriceUpdate: BKK->TYO $450") + " | Expected: true");
        System.out.println("Offer 2: " + ringBuffer.offer("FlightPriceUpdate: SIN->LHR $820") + " | Expected: true");
        System.out.println("Poll  1: " + ringBuffer.poll()                                    + " | Expected: BKK->TYO $450");
        System.out.println("Poll  2: " + ringBuffer.poll()                                    + " | Expected: SIN->LHR $820");
        System.out.println("Poll  3: " + ringBuffer.poll()                                    + " | Expected: null (empty)");
    }
}