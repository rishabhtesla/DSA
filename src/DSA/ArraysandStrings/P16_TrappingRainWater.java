package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [16 / 24] - TRAPPING RAIN WATER (LeetCode 42)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given `n` non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it can trap after raining.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Brute Force O(n^2):
 *    - For every element, scan all the way to the left to find `maxLeft` and
 *      all the way to the right to find `maxRight`.
 *    - Water at index `i` = `min(maxLeft, maxRight) - height[i]`. Too slow.
 *
 * 2. Prefix & Suffix Arrays O(n) Time | O(n) Space:
 *    - Precompute two arrays: one storing left maximums, one storing right maximums.
 *    - Solves the problem in a single linear pass after precomputation, but requires extra space.
 *
 * 3. Optimal Two-Pointer Approach (O(n) Time | O(1) Extra Space):
 *    - The amount of water trapped at any bar is strictly limited by the **shorter** of the
 *      tallest bars to its left and right (`min(leftMax, rightMax)`).
 *    - We use two pointers (`left` and `right`) starting at both ends, along with `leftMax` and `rightMax`.
 *    - **The Core Rule:** If `height[left] < height[right]`, we are guaranteed that the water level at
 *      `left` is bounded by `leftMax` (because `rightMax` is at least `height[right]`, which is taller).
 *      Therefore, we can safely calculate water for `left` using `leftMax`, update `leftMax`, and increment `left`.
 *      Otherwise, we symmetrically process the `right` side.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single pass where left and right pointers converge toward the center.
 * - Space: O(1) - Only a few tracking variables (`left`, `right`, `leftMax`, `rightMax`).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE: height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1] -> Expected Water: 6
 *
 * VISUAL DRY RUN:
 *   - Start: `left = 0, right = 11, leftMax = 0, rightMax = 0, totalWater = 0`
 *   - `height[0] (0) < height[11] (1)` -> Process left:
 *     - `height[0] >= leftMax` (0 >= 0) -> `leftMax = 0`
 *     - `left` advances to 1.
 *   - As pointers converge, whenever the active side's height is less than its counterpart,
 *     we check against its respective running max. If the bar is lower than `max`, it traps
 *     `max - height[bar]` water.
 *   - Running accumulations add up step-by-step: `1 + 1 + 1 + 2 + 1 = 6`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is it safe to process the side with the smaller height, ignoring the other side's full depth?
 *    (Because water containment is bottlenecked by the *shorter* boundary. If `leftMax < rightMax`,
 *    even if there's an infinitely tall wall far to the right, the left wall (`leftMax`) is guaranteed
 *    to be the strict limiting factor for the current left pointer.)
 * 2. What happens if the input array has fewer than 3 elements?
 *    (The `while` loop won't accumulate water because pointers will cross or meet immediately, correctly returning 0.)
 */
public class P16_TrappingRainWater {

    public static int trap(int[] height) {
        if (height == null || height.length == 0) return 0;

        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    totalWater += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    totalWater += rightMax - height[right];
                }
                right--;
            }
        }

        return totalWater;
    }

    public static void main(String[] args) {
        int[] heights = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("P16 Output: " + trap(heights));
        // Expected: 6
    }
}