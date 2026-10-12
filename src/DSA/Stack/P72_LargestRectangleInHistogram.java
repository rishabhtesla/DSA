package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [72 / 73] - LARGEST RECTANGLE IN HISTOGRAM (LeetCode 84)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of integers `heights` representing the histogram's bar heights
 * where the width of each bar is `1`, return the area of the largest rectangle in the histogram.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach:
 *    - For every bar, expand outwards to the left and right until encountering a shorter bar to find its max width.
 *    - Inefficient: takes $O(n^2)$ time, which will result in TLE for large test cases.
 *
 * 2. Optimal Monotonic Increasing Stack Approach (O(n) Time | O(n) Space):
 *    - **The Core Insight:** For each bar `i`, what is the largest rectangle where `heights[i]` acts as the bottleneck (height)?
 *      It extends left to the first bar strictly shorter than `heights[i]` (Previous Smaller Element / PSE)
 *      and right to the first bar strictly shorter (Next Smaller Element / NSE).
 *    - **Width Formula:** `width = NSE_index - PSE_index - 1`.
 *    - **Step 1:** Maintain a monotonic increasing stack storing bar indices.
 *    - **Step 2:** When a bar smaller than the stack's top arrives (`heights[i] < heights[stack.peek()]`),
 *      pop the stack: the popped bar's right boundary is `i` (NSE), and its left boundary is the new stack top (PSE).
 *    - **Step 3 (Sentinel Trick):** Append a dummy height of `0` at index `n` to automatically flush and evaluate
 *      all remaining bars left in the stack at the end of the loop.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Each bar index is pushed and popped from the stack at most once.
 * - Space: O(n) - Monotonic stack storage for indices.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Heights: `[2, 1, 5, 6, 2, 3]`
 * - Expected Largest Area: `10` (formed by bars of height `5` and `6` with widths spanning indices `[2, 3]`)
 *
 * VISUAL DRY RUN (for `[2, 1, 5, 6, 2, 3]`):
 *   - Stack tracks increasing heights. When height `2` at index `4` arrives:
 *     - Pops `6` (width 1, area = 6 * 1 = 6).
 *     - Pops `5` (width 2, area = 5 * 2 = 10).
 *     - Pushes index `4`.
 *   - Sentinel `0` at the end flushes remaining bars. Max area recorded remains `10`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is appending a height of `0` at index `n` (the sentinel trick) so powerful?
 *    (Without a sentinel, any bars left in the stack at the end of the array would require an extra cleanup loop
 *    to compute their areas. The sentinel forces a final flush by being smaller than all positive heights.)
 * 2. How does the stack compute the left boundary (PSE) when it becomes empty after a pop?
 *    (If the stack is empty after popping, it means there is no smaller element to the left,
 *    so the rectangle extends all the way to index `0` (`width = i`).)
 */
public class P72_LargestRectangleInHistogram {

    public static int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) return 0;

        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        // Iterate up to n; treat heights[n] as 0 to automatically flush remaining bars in the stack
        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            // If current bar is shorter than the stack's top bar, pop and calculate max area
            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                // If stack is empty, width spans from index 0 to i; otherwise between stack.peek() and i
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