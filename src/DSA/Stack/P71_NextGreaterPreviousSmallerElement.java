package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * ============================================================================
 * [71 / 73] - NEXT GREATER & PREVIOUS SMALLER ELEMENT (Monotonic Stack Foundation)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array nums:
 *   1. Find the Next Greater Element (NGE) for each position (index or -1).
 *   2. Find the Previous Smaller Element (PSE) for each position (index or -1).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Monotonic Stack Concept:
 *     A stack that maintains elements in either monotonically increasing or decreasing order.
 *   - Next Greater Element (Decreasing Stack from Right to Left):
 *     Traverse right-to-left. Pop all elements `<= nums[i]` because `nums[i]` is closer
 *     and larger, completely shadowing them for any future elements to the left.
 *     Top of stack is now the next greater element!
 *   - Previous Smaller Element (Increasing Stack from Left to Right):
 *     Traverse left-to-right. Pop all elements `>= nums[i]`. Top of stack is the
 *     previous smaller element.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Each element is pushed and popped at most once.
 *   - Space: O(n) - Monotonic stack auxiliary memory.
 */
public class P71_NextGreaterPreviousSmallerElement {

    // Returns array of values representing Next Greater Element (-1 if none)
    public static int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int[] nge = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // Monotonic decreasing stack

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            nge[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }

        return nge;
    }

    // Returns array of indices representing Previous Smaller Element (-1 if none)
    public static int[] previousSmallerIndex(int[] nums) {
        int n = nums.length;
        int[] pse = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // Monotonic increasing stack of indices

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }
            pse[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        return pse;
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 2, 10, 8};
        System.out.println("Nums:                 " + Arrays.toString(nums));
        System.out.println("Next Greater:         " + Arrays.toString(nextGreaterElement(nums)));
        // Expected NGE: [5, 10, 10, -1, -1]
        System.out.println("Prev Smaller (Index): " + Arrays.toString(previousSmallerIndex(nums)));
        // Expected PSE indices: [-1, 0, -1, 2, 2]
    }
}