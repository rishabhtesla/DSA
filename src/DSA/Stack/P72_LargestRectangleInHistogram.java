package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [72 / 73] - LARGEST RECTANGLE IN HISTOGRAM (LeetCode 84)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array of integers heights representing the histogram's bar height
 *   where the width of each bar is 1, return the area of the largest rectangle
 *   in the histogram.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - For each bar `i`, what is the largest rectangle where `heights[i]` is the bottleneck?
 *     It extends left until the first bar strictly shorter than `heights[i]` (PSE),
 *     and right until the first bar strictly shorter than `heights[i]` (NSE).
 *     `width = NSE_index - PSE_index - 1`.
 *   - Single-Pass Monotonic Stack (Increasing):
 *     Store bar indices in strictly increasing order of height.
 *     When `heights[i] < heights[stack.peek()]`:
 *     - The popped bar's right boundary is `i`.
 *     - The popped bar's left boundary is the new top of stack.
 *     - Area = `poppedHeight * (i - stack.peek() - 1)`.
 *   - Append a dummy height of 0 at index `n` to automatically flush all remaining bars!
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Each index is pushed and popped at most once.
 *   - Space: O(n) - Monotonic stack of indices.
 */
public class P72_LargestRectangleInHistogram {

    public static int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) return 0;

        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        // Iterate up to n; treat heights[n] as 0 to flush remaining bars in stack
        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] heights1 = {2, 1, 5, 6, 2, 3};
        System.out.println("P72 Output (Test 1): " + largestRectangleArea(heights1)); // Expected: 10 (bars [5, 6] of height 5)

        int[] heights2 = {2, 4};
        System.out.println("P72 Output (Test 2): " + largestRectangleArea(heights2)); // Expected: 4
    }
}