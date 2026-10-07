package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * PROBLEM STATEMENT:
 * Implement a last-in-first-out (LIFO) stack using only two queues [00:00:17]. The implemented stack 
 * should support all the functions of a normal stack (push, top, pop, and empty) [00:00:36].
 * Implement the MyStack class:
 * - void push(int x) Pushes element x to the top of the stack [00:04:44].
 * - int pop() Removes the element on the top of the stack and returns it [00:06:53].
 * - int top() Returns the element on the top of the stack [00:06:58].
 * - boolean empty() Returns true if the stack is empty, false otherwise [00:07:04].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1:
 * MyStack myStack = new MyStack();
 * myStack.push(1);
 * myStack.push(2);
 * myStack.top();   // Returns 2
 * myStack.pop();   // Returns 2
 * myStack.empty(); // Returns false
 * 
 * Example 2: Data sequence = [10, 20, 30] (Simulated inside the video explanation [00:01:45, 00:13:08])
 * - Process:
 *   - Maintain two linear queues: a main buffer (`mainQueue`) and a transient swapping lane (`helperQueue`) [00:02:47].
 *   - push(10): Queue is empty. Enqueue 10 directly into `mainQueue` [00:03:05, 00:13:30].
 *   - push(20): 
 *     1. Evict existing items from `mainQueue` into `helperQueue`. `helperQueue` = [10] [00:03:23, 00:14:18].
 *     2. Append the new arrival into the empty `mainQueue`. `mainQueue` = [20] [00:03:44, 00:14:29].
 *     3. Return items from `helperQueue` back to `mainQueue`. `mainQueue` = [20, 10] [00:03:51, 00:14:39].
 *   - push(30):
 *     1. Evict items to helper lane. `helperQueue` = [20, 10] [00:05:09].
 *     2. Append arrival. `mainQueue` = [30] [00:05:25].
 *     3. Return helper queue items. `mainQueue` = [30, 20, 10] [00:05:33].
 *   - pop(): Dequeue from front of `mainQueue` directly, returning 30. Correctly mirrors LIFO nature [00:05:50].
 * - Result: Execution maps LIFO constraints safely while building over a FIFO underlying mechanism [00:00:58].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Two Queues Push-Costly Inversion):
 * • Index Initialization: Private instances track memory maps using `java.util.LinkedList` reference implementations [00:07:24, 00:08:08].
 * • Condition Boundaries: While pushing, evict elements to `helperQueue` until `mainQueue.isEmpty()` becomes true [00:09:56].
 * • Operational Steps:
 *   1. Establish encapsulated storage buffers: `mainQueue` and `helperQueue` [00:07:37].
 *   2. `push(x)`: Drain `mainQueue` completely into `helperQueue`. Enqueue `x` into `mainQueue`. Move everything back from `helperQueue` into `mainQueue` [00:08:42].
 *   3. `pop()`: Retrieve and remove the front element of `mainQueue` using `.remove()` [00:11:12].
 *   4. `top()`: Retrieve the front item using `.peek()` [00:11:27].
 *   5. `empty()`: Check if `mainQueue.isEmpty()` holds true [00:11:35].
 * • Time Complexity: O(n) for push, O(1) for pop/top/empty - Inverts processing boundaries onto insertion layers.
 * • Space Complexity: O(n) - Dual structural queue allocations match depth dimensions [00:01:54].
 * • LOGIC BEHIND THIS APPROACH:
 *   A linear queue outputs elements from the front (FIFO) [00:00:48]. To force a last-in-first-out flow (LIFO), we make sure 
 *   the newest element is always placed at the front of the line [00:00:38, 00:03:17]. By clearing out historical values 
 *   into a temporary lane and re-appending them behind the new arrival, we maintain the inverted stack sequence [00:04:30].
 * 
 * ---
 * VISUAL DRY RUN (Push sequence = [10, 20]):
 * State 1: push(10) -> mainQueue = [10], helperQueue = [] (Loop skips since main was initially empty) [00:13:30].
 * State 2: push(20) [00:14:10]:
 *   - Step A: mainQueue.remove() moves 10 to helperQueue. mainQueue = [], helperQueue = [10] [00:14:23].
 *   - Step B: Enqueue new value 20 to mainQueue. mainQueue = [20], helperQueue = [10] [00:14:29].
 *   - Step C: helperQueue.remove() moves 10 back. mainQueue = [20, 10], helperQueue = [] [00:14:39].
 * Verification: pop() evaluates front of `mainQueue`, evicting 20. Replicates LIFO behavior seamlessly [00:14:53].
 */
public class MyStack {

    private final Queue<Integer> mainQueue;
    private final Queue<Integer> helperQueue;

    // Class Constructor initializing primitive reference pools [00:08:03]
    public MyStack() {
        this.mainQueue = new LinkedList<>();
        this.helperQueue = new LinkedList<>();
    }

    // Push layer executing structural in-place sorting inversions [00:08:42]
    public void push(int x) {
        // Step 1: Shift all elements from mainQueue to helperQueue [00:09:56]
        while (!mainQueue.isEmpty()) {
            helperQueue.add(mainQueue.remove());
        }

        // Step 2: Enqueue the incoming element at the front of the stack structure [00:10:29]
        mainQueue.add(x);

        // Step 3: Return all elements back to mainQueue [00:10:51]
        while (!helperQueue.isEmpty()) {
            mainQueue.add(helperQueue.remove());
        }
    }

    // Pop layer evicting inverted stack boundaries [00:11:12]
    public int pop() {
        return mainQueue.remove();
    }

    // Top element tracking accessor [00:11:27]
    public int top() {
        return mainQueue.peek();
    }

    // Structural empty monitor [00:11:35]
    public boolean empty() {
        return mainQueue.isEmpty();
    }

    // APPROACH 2: Single Queue Rotation Strategy
    // Uses only one queue. When a new element is added, we count the historical elements and 
    // rotate them to the back of the queue, moving the newest element to the front in-place.
    public static class MyStackSingleQueue {
        private final Queue<Integer> queue = new LinkedList<>();

        public void push(int x) {
            int size = queue.size();
            queue.add(x);
            // Rotate all historical elements behind the newly arrived token boundary
            for (int i = 0; i < size; i++) {
                queue.add(queue.remove());
            }
        }

        public int pop() {
            return queue.remove();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Simulating stack indexing loops over standard pipeline conversions 
    // introduces performance overhead, bypassing constant execution limits while tracking temporary wrapper caches.
    public static class MyStackStreamVariant {
        private final List<Integer> internalStorageList = new ArrayList<>();

        public void push(int x) {
            internalStorageList.add(x);
        }

        public int pop() {
            return internalStorageList.remove(internalStorageList.size() - 1);
        }

        public int top() {
            return internalStorageList.get(internalStorageList.size() - 1);
        }

        public boolean empty() {
            // Evaluates structural boundaries using dynamic checking metrics
            return internalStorageList.stream().count() == 0;
        }
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST SUITE: APPROACH 1 (Push-Costly Dual Queues) ---
        System.out.println("Testing Approach 1: Synchronized Dual Queues (Costly Push)");
        MyStack app1 = new MyStack();
        app1.push(10);
        app1.push(20);
        app1.push(30);
        System.out.println("Current Top: " + app1.top()); // Expected: 30 [00:05:50]
        System.out.println("Popped Value: " + app1.pop()); // Expected: 30
        System.out.println("New Top: " + app1.top()); // Expected: 20 [00:14:53]
        boolean check1 = (app1.top() == 20 && !app1.empty());
        System.out.println("Approach 1 Status: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST SUITE: APPROACH 2 (Single Queue Rotation) ---
        System.out.println("Testing Approach 2: Single Queue Rotation");
        MyStackSingleQueue app2 = new MyStackSingleQueue();
        app2.push(10);
        app2.push(20);
        app2.push(30);
        System.out.println("Current Top: " + app2.top());
        System.out.println("Popped Value: " + app2.pop());
        System.out.println("New Top: " + app2.top());
        boolean check2 = (app2.top() == 20 && !app2.empty());
        System.out.println("Approach 2 Status: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST SUITE: APPROACH 3 (Stream Framework Wrapper) ---
        System.out.println("Testing Approach 3: Stream Pipeline Collection");
        MyStackStreamVariant app3 = new MyStackStreamVariant();
        app3.push(10);
        app3.push(20);
        app3.push(30);
        System.out.println("Current Top: " + app3.top());
        System.out.println("Popped Value: " + app3.pop());
        System.out.println("New Top: " + app3.top());
        boolean check3 = (app3.top() == 20 && !app3.empty());
        System.out.println("Approach 3 Status: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}