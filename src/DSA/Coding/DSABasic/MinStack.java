package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * PROBLEM STATEMENT:
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time [00:00:26].
 * Implement the MinStack class:
 * - MinStack() initializes the stack object [00:00:51].
 * - void push(int val) pushes the element val onto the stack [00:08:10].
 * - void pop() removes the element on the top of the stack [00:10:58].
 * - int top() gets the top element of the stack [00:11:59].
 * - int getMin() retrieves the minimum element in the stack [00:12:15].
 * You must implement a solution with O(1) time complexity for each function [00:00:43].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1:
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // Returns -3
 * minStack.pop();
 * minStack.top();    // Returns 0
 * minStack.getMin(); // Returns -2
 * 
 * Example 2: Data sequence = [10, 20, 5, 10, 40, 30] (Simulated inside the video explanation [00:04:53, 00:13:34])
 * - Process:
 *   - Maintain two parallel stacks: a main data stack (`mainStack`) and a tracking minimum stack (`minStack`) [00:04:42].
 *   - push(10): Stack is empty. Push 10 to both `mainStack` and `minStack` [00:05:04, 00:13:51].
 *   - push(20): 20 > minStack.peek() (10). Push 20 only to `mainStack` [00:05:10, 00:14:07].
 *   - push(5): 5 <= minStack.peek() (10). Push 5 to both `mainStack` and `minStack` [00:05:25, 00:14:37].
 *   - push(10): 10 > minStack.peek() (5). Push 10 only to `mainStack` [00:14:56].
 *   - getMin(): Returns `minStack.peek()` = 5 [00:05:39, 00:14:51].
 *   - pop() executes twice: Removes 30 and 40 from `mainStack`. Neither equals `minStack.peek()` (5), so `minStack` remains unchanged [00:05:53, 00:15:54].
 *   - pop() executes again: Removes 10 from `mainStack`. Does not equal 5.
 *   - pop() executes again: Removes 5 from `mainStack`. Since 5 == `minStack.peek()` (5), pop from `minStack` as well [00:06:07, 00:16:35].
 *   - getMin(): Returns `minStack.peek()` = 10 [00:06:16, 00:16:47].
 * - Result: System operates correctly in O(1) throughout mutations [00:00:43].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Synchronized Dual Stacks):
 * • Index Initialization: Private auxiliary storage allocations track mutations via internal `java.util.Stack` class structures [00:07:04].
 * • Condition Boundaries: Upon pushing, write to `minStack` if empty OR if `val <= minStack.peek()` [00:08:16, 00:10:47]. 
 *   Upon popping, pop from `minStack` if the main evicted element equals `minStack.peek()` [00:11:39].
 * • Operational Steps:
 *   1. Define two encapsulated stack fields: `mainStack` and `minStack` [00:07:09].
 *   2. `push(val)`: Always append to `mainStack`. If `val <= minStack.peek()`, append to `minStack` as well [00:10:25].
 *   3. `pop()`: Retrieve the top item from `mainStack`. If it matches `minStack.peek()`, pop from `minStack` too [00:11:18].
 *   4. `top()`: Call `mainStack.peek()` natively [00:11:59].
 *   5. `getMin()`: Call `minStack.peek()` natively [00:12:15].
 * • Time Complexity: O(1) for all operations - Avoids explicit iteration loops completely [00:00:43].
 * • Space Complexity: O(n) - Auxiliary space used by the tracking stack structure [00:06:11].
 * • LOGIC BEHIND THIS APPROACH:
 *   A single min-scalar variable fails when the absolute minimum element is popped from the stack, as historical 
 *   context about previous minimum states is lost [00:03:55]. By leveraging a secondary tracking stack, we record 
 *   the local minimum value for each stack depth boundary. This ensures that historical minimum thresholds are cleanly 
 *   restored as the main stack contracts [00:04:42].
 * 
 * ---
 * VISUAL DRY RUN (Push sequence = [10, 20, 5]):
 * State 1: push(10) -> mainStack = [10], minStack = [10] (Initial blank boundary check matched) [00:13:51].
 * State 2: push(20) -> mainStack = [10, 20]. Condition (20 <= 10) is false -> minStack remains [10] [00:14:07].
 * State 3: push(5)  -> mainStack = [10, 20, 5]. Condition (5 <= 10) is true -> minStack becomes [10, 5] [00:14:37].
 * Evaluation: getMin() evaluates `minStack.peek()`, returning 5 [00:14:51].
 * State 4: pop() -> mainStack removes 5. Since (5 == minStack.peek()) matches, pop from minStack too. 
 *          mainStack = [10, 20], minStack = [10] [00:16:35].
 * Evaluation: getMin() evaluates `minStack.peek()`, returning 10 [00:16:47].
 */
public class MinStack {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    // Class Constructor initializing primitive fields [00:00:51, 00:07:45]
    public MinStack() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    // Push execution logic mapping state adjustments [00:08:10]
    public void push(int val) {
        mainStack.push(val);
        // Add to minStack if empty or if val is smaller than or equal to current minimum [00:10:47]
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    // Pop execution logic mapping state adjustments [00:10:58]
    public void pop() {
        if (mainStack.isEmpty()) return;
        
        int removedValue = mainStack.pop();
        // If the removed element matches the current minimum, pop it from minStack too [00:11:39]
        if (removedValue == minStack.peek()) {
            minStack.pop();
        }
    }

    // Top accessor implementation [00:11:59]
    public int top() {
        return mainStack.peek();
    }

    // Constant time retrieval of minimum element [00:12:15]
    public int getMin() {
        return minStack.peek();
    }

    // APPROACH 2: Single Stack with Value-Minimum Node Pairs
    // An alternative structural layout storing nodes containing both the value and the historical minimum at that point.
    // Removes synchronization dependencies across independent tracking collections.
    public static class MinStackNodePair {
        private static class Node {
            int val;
            int currentMin;
            Node next;
            
            Node(int val, int currentMin, Node next) {
                this.val = val;
                this.currentMin = currentMin;
                this.next = next;
            }
        }
        
        private Node head;
        
        public void push(int val) {
            if (head == null) {
                head = new Node(val, val, null);
            } else {
                head = new Node(val, Math.min(val, head.currentMin), head);
            }
        }
        
        public void pop() {
            if (head != null) {
                head = head.next;
            }
        }
        
        public int top() {
            return head != null ? head.val : -1;
        }
        
        public int getMin() {
            return head != null ? head.currentMin : -1;
        }
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Forcing stream allocations over linear reduction checks violates the O(1) constant runtime contract.
    // Provided here strictly to show alternative streaming layout mechanics across intermediate storage snapshots.
    public static class MinStackStreamVariant {
        private final List<Integer> internalList = new ArrayList<>();
        
        public void push(int val) {
            internalList.add(val);
        }
        
        public void pop() {
            if (!internalList.isEmpty()) {
                internalList.remove(internalList.size() - 1);
            }
        }
        
        public int top() {
            return internalList.isEmpty() ? -1 : internalList.get(internalList.size() - 1);
        }
        
        // Runtime Trade-off: Stream collection requires O(n) scanning time, violating O(1) requirements
        public int getMin() {
            return internalList.stream()
                    .min(Integer::compare)
                    .orElse(-1);
        }
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST SUITE: APPROACH 1 (Dual Stacks - Anchor Strategy) ---
        System.out.println("Testing Approach 1: Synchronized Dual Stacks");
        MinStack app1 = new MinStack();
        app1.push(10);
        app1.push(20);
        app1.push(5);
        System.out.println("Current Top: " + app1.top() + " | Current Min: " + app1.getMin()); // Expected: Top=5, Min=5
        app1.pop();
        System.out.println("After Pop -> Current Top: " + app1.top() + " | Current Min: " + app1.getMin()); // Expected: Top=20, Min=10
        boolean check1 = (app1.top() == 20 && app1.getMin() == 10);
        System.out.println("Approach 1 Status: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST SUITE: APPROACH 2 (Linked Node Pairs) ---
        System.out.println("Testing Approach 2: Linked Node Pairs Class");
        MinStackNodePair app2 = new MinStackNodePair();
        app2.push(10);
        app2.push(20);
        app2.push(5);
        System.out.println("Current Top: " + app2.top() + " | Current Min: " + app2.getMin());
        app2.pop();
        System.out.println("After Pop -> Current Top: " + app2.top() + " | Current Min: " + app2.getMin());
        boolean check2 = (app2.top() == 20 && app2.getMin() == 10);
        System.out.println("Approach 2 Status: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST SUITE: APPROACH 3 (Stream Reduction Tracking) ---
        System.out.println("Testing Approach 3: Stream Pipeline Collection");
        MinStackStreamVariant app3 = new MinStackStreamVariant();
        app3.push(10);
        app3.push(20);
        app3.push(5);
        System.out.println("Current Top: " + app3.top() + " | Current Min: " + app3.getMin());
        app3.pop();
        System.out.println("After Pop -> Current Top: " + app3.top() + " | Current Min: " + app3.getMin());
        boolean check3 = (app3.top() == 20 && app3.getMin() == 10);
        System.out.println("Approach 3 Status: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}