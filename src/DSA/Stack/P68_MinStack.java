package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [68 / 73] - MIN STACK (LeetCode 155)
 * ============================================================================
 * 
 * PROBLEM:
 *   Design a stack that supports push, pop, top, and retrieving the minimum
 *   element in constant time O(1).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Two Stacks vs Single Interleaved Stack:
 *     Approach 1: Auxiliary `minStack` where each entry stores the min up to that height.
 *     Approach 2 (Single Stack with Encoded Previous Min):
 *     When a new minimum `val <= min` arrives, push the OLD `min` first onto the stack,
 *     then update `min = val` and push `val`.
 *     When popping: if popped element == `min`, pop once more to restore the previous `min`.
 *     This avoids a second stack while retaining clean O(1) operations.
 *
 * COMPLEXITY:
 *   - Time:  O(1) for push, pop, top, and getMin.
 *   - Space: O(n) worst case.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P68_MinStack {

    static class MinStack {
        private final Deque<Long> stack;
        private long min;

        public MinStack() {
            stack = new ArrayDeque<>();
        }

        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = val;
            } else {
                // Store difference: val - min
                stack.push((long) val - min);
                if (val < min) {
                    min = val; // Record new minimum
                }
            }
        }

        public void pop() {
            if (stack.isEmpty()) return;
            long diff = stack.pop();
            // If diff < 0, this element was a new minimum; restore previous min
            if (diff < 0) {
                min = min - diff;
            }
        }

        public int top() {
            long diff = stack.peek();
            if (diff < 0) {
                return (int) min;
            }
            return (int) (min + diff);
        }

        public int getMin() {
            return (int) min;
        }
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("getMin: " + minStack.getMin()); // Expected: -3
        minStack.pop();
        System.out.println("top:    " + minStack.top());    // Expected: 0
        System.out.println("getMin: " + minStack.getMin()); // Expected: -2
    }
}