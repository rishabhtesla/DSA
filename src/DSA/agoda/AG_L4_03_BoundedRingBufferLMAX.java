package DSA.agoda;

import java.util.concurrent.atomic.AtomicLong;

/**
 * ============================================================================
 * [STAFF L4 - 03] MECHANICAL SYMPATHY: FALSE SHARING & LOCK-FREE RING BUFFER
 * ============================================================================
 * 
 * JD TARGET:
 *   - "Identify and resolve system bottlenecks across the JVM, CPU cache locality"
 *   - "Streaming messaging backbones handling high-throughput booking"
 *
 * SCENARIO / SYSTEM CONTEXT:
 *   Agoda processes millions of price tick updates per second across global flights.
 *   Standard Java `ArrayBlockingQueue` relies on lock contention (`ReentrantLock`)
 *   and cache-line bouncing (False Sharing) on CPU L1/L2/L3 caches.
 *
 * ARCHITECTURAL TRADEOFF & INTERVIEW INTUITION:
 *   - False Sharing Problem:
 *     CPUs read memory in 64-byte Cache Lines. If the Producer write index and
 *     Consumer read index share the same 64-byte line, Core A's write invalidates
 *     Core B's cache line, causing memory bus stalls.
 *   - Solution:
 *     Use `@jdk.internal.vm.annotation.Contended` or long-padding (7 unused longs = 56 bytes)
 *     to isolate pointers onto separate cache lines.
 *   - Power-of-Two Masking:
 *     Replace modulo `index % capacity` with bitwise AND `index & (capacity - 1)`.
 *
 * COMPLEXITY:
 *   - Time:  Sub-microsecond O(1) latency per event.
 *   - Space: O(Capacity) fixed contiguous array.
 */
public class AG_L4_03_BoundedRingBufferLMAX {

    // Cache line padding to prevent false sharing (64 bytes = 8 longs)
    static class PaddedAtomicLong extends AtomicLong {
        public volatile long p1, p2, p3, p4, p5, p6, p7; // 56 bytes of padding
        public PaddedAtomicLong(long initialValue) {
            super(initialValue);
        }
    }

    public static class LockFreeRingBuffer<T> {
        private final Object[] entries;
        private final int mask;

        private final PaddedAtomicLong writeCursor = new PaddedAtomicLong(0);
        private final PaddedAtomicLong readCursor = new PaddedAtomicLong(0);

        public LockFreeRingBuffer(int powerOfTwoCapacity) {
            if (Integer.bitCount(powerOfTwoCapacity) != 1) {
                throw new IllegalArgumentException("Capacity must be a power of two");
            }
            this.entries = new Object[powerOfTwoCapacity];
            this.mask = powerOfTwoCapacity - 1;
        }

        public boolean offer(T item) {
            long currentWrite = writeCursor.get();
            long currentRead = readCursor.get();

            // Check if buffer is full
            if (currentWrite - currentRead >= entries.length) {
                return false; // Backpressure signal to producer
            }

            if (writeCursor.compareAndSet(currentWrite, currentWrite + 1)) {
                int slot = (int) (currentWrite & mask);
                entries[slot] = item;
                return true;
            }
            return false;
        }

        @SuppressWarnings("unchecked")
        public T poll() {
            long currentRead = readCursor.get();
            long currentWrite = writeCursor.get();

            if (currentRead >= currentWrite) {
                return null; // Buffer empty
            }

            if (readCursor.compareAndSet(currentRead, currentRead + 1)) {
                int slot = (int) (currentRead & mask);
                T item = (T) entries[slot];
                entries[slot] = null; // Prevent memory leak for GC
                return item;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        LockFreeRingBuffer<String> ringBuffer = new LockFreeRingBuffer<>(1024);
        System.out.println("Enqueued: " + ringBuffer.offer("FlightPriceUpdate: $450"));
        System.out.println("Dequeued: " + ringBuffer.poll());
        System.out.println("Empty poll: " + ringBuffer.poll());
    }
}