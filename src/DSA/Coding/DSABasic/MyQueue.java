package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * PROBLEM STATEMENT:
 * Implement a first-in-first-out (FIFO) queue using only two stacks [00:00:17]. The implemented queue 
 * should support all the functions of a normal queue (push, peek, pop, and empty) [00:00:55].
 * Implement the MyQueue class:
 * - void push(int x) Pushes element x to the back of the queue [00:04:44].
 * - int pop() Removes the element from the front of the queue and returns it [00:06:34].
 * - int peek() Returns the element at the front of the queue [00:06:34].
 * - boolean empty() Returns true if the queue is empty, false otherwise [00:12:05].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1:
 * MyQueue myQueue = new MyQueue();
 * myQueue.push(1);
 * myQueue.push(2);
 * myQueue.peek();  // Returns 1
 * myQueue.pop();   // Returns 1
 * myQueue.empty(); // Returns false
 * 
 * Example 2: Data sequence = [10, 20, 30] (Simulated inside the video explanation [00:02:20, 00:13:57])
 * - Process:
 *   - Maintain two parallel stacks: a main buffer (`mainStack`) and a temporary swap lane (`helperStack`) [00:03:38].
 *   - push(10): Stack is empty. Push 10 directly into `mainStack` [00:03:51, 00:14:06].
 *   - push(20): 
 *     1. Drain existing elements from `mainStack` into `helperStack`. `helperStack` = [10] [00:04:30, 00:14:48].
 *     2. Push the new element into the empty `mainStack`. `mainStack` = [20] [00:04:43, 00:15:10].
 *     3. Pop elements from `helperStack` back to `mainStack`. `mainStack` = [10, 20] (where 10 is at the top) [00:04:59, 00:15:15].
 *   - push(30):
 *     1. Drain elements to helper lane. `helperStack` = [20, 10] [00:05:33].
 *     2. Push arrival into main stack. `mainStack` = [30] [00:05:49].
 *     3. Return items from helper lane back to main stack. `mainStack` = [10, 20, 30] (10 is at the top) [00:05:59].
 *   - pop(): Pop directly from the top of `mainStack`, returning 10. Correctly preserves FIFO order [00:06:13].
 * - Result: Replicates FIFO boundaries over LIFO underlying stacks [00:00:38].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Two Stacks Push-Costly Inversion):
 * • Index Initialization: Private class variables maintain internal state using standard `java.util.Stack` buffers [00:07:04, 00:08:29].
 * • Condition Boundaries: During push operations, pop from `mainStack` until `mainStack.isEmpty()` is true [00:10:13].
 * • Operational Steps:
 *   1. Establish encapsulated storage components: `mainStack` and `helperStack` [00:07:37].
 *   2. `push(x)`: Pop all elements out of `mainStack` into `helperStack`. Push `x` onto `mainStack`. Pop everything from `helperStack` back onto `mainStack` [00:08:47].
 *   3. `pop()`: Dequeue by popping from the top of `mainStack` via `.pop()` [00:11:38].
 *   4. `peek()`: Access the front element using `.peek()` [00:11:47].
 *   5. `empty()`: Check if `mainStack.isEmpty()` holds true [00:12:05].
 * • Time Complexity: O(n) for push, O(1) for pop/peek/empty - Shifts processing overhead onto insertion layers.
 * • Space Complexity: O(n) - Dual structural stack buffers scale with overall depth [00:00:43].
 * • LOGIC BEHIND THIS APPROACH:
 *   A standard stack operates via Last-In-First-Out (LIFO) semantics [00:00:37]. To mirror First-In-First-Out (FIFO) behavior, 
 *   the oldest element must always sit on top of the stack [00:01:06, 00:02:56]. By temporarily offloading elements to a helper 
 *   lane, inserting the new arrival at the bottom, and reloading the historical elements on top, we maintain the correct queue order [00:06:49].
 * 
 * ---
 * VISUAL DRY RUN (Push sequence = [10, 20]):
 * State 1: push(10) -> mainStack = [10], helperStack = [] (Loop is skipped as main was initially blank) [00:14:06].
 * State 2: push(20) [00:14:42]:
 *   - Step A: mainStack.pop() moves 10 to helperStack. mainStack = [], helperStack = [10] [00:14:48].
 *   - Step B: Push new value 20 to mainStack. mainStack = [20], helperStack = [10] [00:15:10].
 *   - Step C: helperStack.pop() moves 10 back. mainStack = [10, 20] (top is 10), helperStack = [] [00:15:15].
 * Verification: pop() checks top of `mainStack`, evicting 10. Replicates FIFO behavior seamlessly [00:15:32].
 */
public class MyQueue {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> helperStack;

    // Class Constructor initializing internal storage containers [00:08:23]
    public MyQueue() {
        this.mainStack = new Stack<>();
        this.helperStack = new Stack<>();
    }

    // Push execution loop performing structural sorting inversion [00:08:47]
    public void push(int x) {
        // Step 1: Push all elements out of mainStack into helperStack [00:10:13]
        while (!mainStack.isEmpty()) {
            helperStack.push(mainStack.pop());
        }

        // Step 2: Push the new element onto the bottom of the mainStack structure [00:10:52]
        mainStack.push(x);

        // Step 3: Pop all elements from helperStack back onto mainStack [00:11:12]
        while (!helperStack.isEmpty()) {
            mainStack.push(helperStack.pop());
        }
    }

    // Pop execution layer evicting inverted queue boundaries [00:11:38]
    public int pop() {
        return mainStack.pop();
    }

    // Front element tracking accessor [00:11:47]
    public int peek() {
        return mainStack.peek();
    }

    // Structural empty monitor [00:12:05]
    public boolean empty() {
        return mainStack.isEmpty();
    }

    // APPROACH 2: Synchronized Amortized O(1) Lazy Evaluation (Pop-Costly Variant)
    // Minimizes push overhead to O(1) by appending elements directly to an 'input' stack.
    // Inversions are performed lazily on-demand inside pop/peek steps only when the 'output' stack runs dry.
    public static class MyQueueLazyAmortized {
        private final Stack<Integer> input = new Stack<>();
        private final Stack<Integer> output = new Stack<>();

        public void push(int x) {
            input.push(x);
        }

        public int pop() {
            shiftElements();
            return output.pop();
        }

        public int peek() {
            shiftElements();
            return output.peek();
        }

        public boolean empty() {
            return input.isEmpty() && output.isEmpty();
        }

        private void shiftElements() {
            if (output.isEmpty()) {
                while (!input.isEmpty()) {
                    output.push(input.pop());
                }
            }
        }
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Forcing stream allocations over linear transformations introduces 
    // performance delays, bypassing constant time targets to populate reference collection boundaries.
    public static class MyQueueStreamVariant {
        private final List<Integer> internalDataList = new ArrayList<>();

        public void push(int x) {
            internalDataList.add(x);
        }

        public int pop() {
            return internalDataList.remove(0);
        }

        public int peek() {
            return internalDataList.get(0);
        }

        public boolean empty() {
            // Evaluates structural boundaries using dynamic checking metrics
            return internalDataList.stream().count() == 0;
        }
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST SUITE: APPROACH 1 (Push-Costly Dual Stacks) ---
        System.out.println("Testing Approach 1: Synchronized Dual Stacks (Costly Push)");
        MyQueue app1 = new MyQueue();
        app1.push(10);
        app1.push(20);
        app1.push(30);
        System.out.println("Current Peek: " + app1.peek()); // Expected: 10 [00:06:34]
        System.out.println("Popped Value: " + app1.pop()); // Expected: 10
        System.out.println("New Peek: " + app1.peek()); // Expected: 20 [00:15:32]
        boolean check1 = (app1.peek() == 20 && !app1.empty());
        System.out.println("Approach 1 Status: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST SUITE: APPROACH 2 (Lazy Amortized Variant) ---
        System.out.println("Testing Approach 2: Lazy Amortized Stacks Shift");
        MyQueueLazyAmortized app2 = new MyQueueLazyAmortized();
        app2.push(10);
        app2.push(20);
        app2.push(30);
        System.out.println("Current Peek: " + app2.peek());
        System.out.println("Popped Value: " + app2.pop());
        System.out.println("New Peek: " + app2.peek());
        boolean check2 = (app2.peek() == 20 && !app2.empty());
        System.out.println("Approach 2 Status: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST SUITE: APPROACH 3 (Stream Framework Wrapper) ---
        System.out.println("Testing Approach 3: Stream Pipeline Collection");
        MyQueueStreamVariant app3 = new MyQueueStreamVariant();
        app3.push(10);
        app3.push(20);
        app3.push(30);
        System.out.println("Current Peek: " + app3.peek());
        System.out.println("Popped Value: " + app3.pop());
        System.out.println("New Peek: " + app3.peek());
        boolean check3 = (app3.peek() == 20 && !app3.empty());
        System.out.println("Approach 3 Status: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}