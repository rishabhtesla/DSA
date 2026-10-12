package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [68 / 73] - MIN Stack (LeetCode 155)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Design a stack that supports `push`, `pop`, `top`, and retrieving the minimum
 * element in **constant time O(1)**.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Auxiliary Stack Approach:
 *    - Maintain a second companion `minStack` where each entry stores the minimum value up to that height.
 *    - Valid and straightforward, but uses extra memory for a secondary stack.
 *
 * 2. Optimal Single Stack with Difference Encoding Approach (O(1) Time | O(n) Space):
 *    - **The Core Insight:** Instead of maintaining a separate stack, we can encode the difference between
 *      the incoming value and the current minimum directly into the primary stack (`val - min`).
 *    - **Push Logic:**
 *      - If the stack is empty, push `0L` and set `min = val`.
 *      - Otherwise, push the difference `(long) val - min`. If `val < min`, update `min = val`.
 *    - **Pop Logic:**
 *      - If the popped difference is negative (`diff < 0`), it indicates that a new minimum was introduced.
 *        We restore the previous minimum via `min = min - diff`.
 *    - **Top Logic:**
 *      - If the top difference is negative, the top element is the current `min`.
 *      - Otherwise, the actual value is `min + diff`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(1) constant time complexity for all operations (`push`, `pop`, `top`, `getMin`).
 * - Space: O(n) worst-case storage space to maintain the primary stack elements.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Operations: `push(-2)`, `push(0)`, `push(-3)`, `getMin()`, `pop()`, `top()`, `getMin()`
 * - Expected Outputs: `-3`, `0`, `-2`
 *
 * VISUAL DRY RUN:
 *   - Push `-2` -> stack has diff `0`, `min = -2`.
 *   - Push `0` -> difference `0 - (-2) = 2`, `min` remains `-2`.
 *   - Push `-3` -> difference `-3 - (-2) = -1` (negative, so new min), `min` updates to `-3`.
 *   - `getMin()` returns `-3`.
 *   - `pop()` pops `-1` (negative), restores previous `min = -3 - (-1) = -2`.
 *   - `top()` returns `0`, `getMin()` returns `-2`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why use `long` primitives for stack storage when the problem specifies standard `int` values?
 *    (Using `long` prevents potential integer overflow/underflow issues when calculating differences `val - min`
 *    if `val` and `min` reside near opposite ends of the 32-bit integer range.)
 * 2. How does difference encoding save space compared to a two-stack approach?
 *    (It eliminates the overhead of managing synchronized node allocations in a secondary collection
 *    while packing metadata seamlessly into existing stack elements.)
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
                // Store the difference between val and current min
                stack.push((long) val - min);
                if (val < min) {
                    min = val; // Record the new minimum
                }
            }
        }

        public void pop() {
            if (stack.isEmpty()) return;
            long diff = stack.pop();
            // If diff is negative, this element was a new minimum; restore the previous min
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