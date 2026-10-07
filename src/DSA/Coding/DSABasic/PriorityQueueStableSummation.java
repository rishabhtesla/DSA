package DSA.Coding.DSABasic;

import java.util.PriorityQueue;

/**
 * =========================================================================
 *                      CLASS: PriorityQueueStableSummation
 * =========================================================================
 * PURPOSE: 
 * This class processes a dataset of values and priorities. It securely extracts 
 * the top 'K' highest-priority elements to compute their total sum. 
 * If two elements share identical priorities, it relies on a "stable sort" 
 * mechanism ensuring the element that entered the dataset first is picked first.
 *
 * =========================================================================
 *                           APPROACH LOGIC
 * =========================================================================
 * 1. OBJECT WRAPPING:
 *    We wrap each item's data (value, priority, and original index) into a 
 *    custom 'Element' class. Storing the index ('insertOrder') is crucial 
 *    because standard Java PriorityQueues do not guarantee stable sorting 
 *    for elements with identical keys.
 *
 * 2. CUSTOM COMPARATOR HIERARCHY (The Core Strategy):
 *    We pass a custom lambda comparator to the PriorityQueue that operates 
 *    on a two-tier sorting system:
 *    - Tier 1 (Primary): Max-Heap on 'priority'. We compare `b.priority` 
 *      against `a.priority`. Higher priorities gravitate to the top.
 *    - Tier 2 (Tie-breaker): Min-Heap on 'insertOrder'. If priorities match,
 *      we compare `a.insertOrder` against `b.insertOrder`. The element that 
 *      arrived earlier (smaller index) gravitates to the top.
 *
 * 3. PROCESSING & SUMMATION:
 *    - Step 1: Push all elements into the queue. The heap auto-arranges them.
 *    - Step 2: Extract (poll) elements up to 'numk' times. 
 *    - Safety Guard: The extraction loop condition checks `!pq.isEmpty()` 
 *      to prevent `NullPointerException` errors if 'numk' exceeds array size.
 *
 * =========================================================================
 *                         COMPLEXITY ANALYSIS
 * =========================================================================
 * - Time Complexity: O(N log N) to insert all items into the priority queue, 
 *   and O(K log N) to poll K elements. Total Time: O((N + K) log N).
 * - Space Complexity: O(N) to store the elements inside the Priority Queue.
 * =========================================================================
 */
public class PriorityQueueStableSummation {
    
    static class Element {
        int value;
        int priority;
        int insertOrder;

        public Element(int value, int priority, int insertOrder) {
            this.value = value;
            this.priority = priority;
            this.insertOrder = insertOrder;
        }

        @Override
        public String toString() {
            return String.format("[Val: %d, Priority: %d, Order: %d]", value, priority, insertOrder);
        }
    }

    public static void processData(int n, int numk, int[] x, int[] y) {
        // Priority Queue with specific custom tie-breaking sorting logic
        PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> {
            // Tier 1: Max-Heap on Priority
            if (b.priority != a.priority) {
                return Integer.compare(b.priority, a.priority); 
            }
            // Tier 2: Min-Heap on Insertion Order (Stable tie-breaking)
            return Integer.compare(a.insertOrder, b.insertOrder); 
        });

        System.out.println("--- DRY RUN START ---");
        System.out.println("Step 1: Populating Priority Queue");
        for (int i = 0; i < n; i++) {
            Element el = new Element(x[i], y[i], i);
            pq.add(el);
            System.out.println("  -> Inserted: " + el);
        }

        System.out.println("\nStep 2: Polling up to " + numk + " elements from Queue");
        long sum = 0;
        
        for (int i = 0; i < numk && !pq.isEmpty(); i++) {
            Element pulled = pq.poll();
            sum += pulled.value;
            System.out.println("  -> Polled element " + (i + 1) + ": " + pulled + " | Running Sum: " + sum);
        }

        System.out.println("\nFinal Sum Result: " + sum);
        System.out.println("--- DRY RUN END ---\n");
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("TEST CASE 1: Standard sorting & tie-breaking");
        System.out.println("=================================================");
        /*
           n = 4 elements, k = 2
           Index 0: (10, 5)
           Index 1: (20, 10)  <- Same priority as index 2, but came first
           Index 2: (30, 10)  
           Index 3: (40, 2)
           
           Expected Selection Order: Index 1 first, then Index 2.
           Expected Sum: 20 + 30 = 50
        */
        int n1 = 4;
        int k1 = 2;
        int[] x1 = {10, 20, 30, 40};
        int[] y1 = {5, 10, 10, 2};
        processData(n1, k1, x1, y1);

        System.out.println("=================================================");
        System.out.println("TEST CASE 2: K is greater than array size (K > N)");
        System.out.println("=================================================");
        /*
           n = 3 elements, k = 5
           Elements: (50, 1), (60, 3), (70, 2)
           
           Expected Selection Order: Safe extraction of all available items.
           Expected Sum: 60 + 70 + 50 = 180
        */
        int n2 = 3;
        int k2 = 5;
        int[] x2 = {50, 60, 70};
        int[] y2 = {1, 3, 2};
        processData(n2, k2, x2, y2);
    }
}