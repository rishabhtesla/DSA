package DSA.twopointers;

/**
 * ============================================================================
 * [33 / 34] - CONTAINER WITH MOST WATER (LeetCode 11)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an integer array `height` of length `n`, find two lines that together with
 * the x-axis form a container, such that the container contains the most water.
 * Return the maximum amount of water a container can store.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach:
 *    - Check all possible pairs of lines to calculate every potential container area.
 *    - Inefficient: takes $O(n^2)$ time, resulting in TLE for large arrays.
 *
 * 2. Optimal Opposite-Ends Greedy Two Pointers Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** The area of any container is defined by `width * min(height[left], height[right])`.
 *      If we start with maximum width (`left = 0`, `right = n - 1`), moving either pointer inward reduces the width by 1.
 *    - If we move the *taller* line, the new area can **never** exceed the current maximum because the bottleneck
 *      (the `min` height) was already constrained by the shorter line, and the width has now shrunk.
 *    - Therefore, the **only** way to potentially find a larger area is to discard the shorter line in hopes of finding
 *      a taller one. Thus, we always move whichever pointer is strictly shorter inward.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Left and right pointers converge inward in a single linear scan.
 * - Space: O(1) - Constant auxiliary pointer space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `height = [1, 8, 6, 2, 5, 4, 8, 3, 7]`
 * - Expected Maximum Area: `49` (formed between height 8 at index 1 and height 7 at index 8)
 *
 * VISUAL DRY RUN (for `[1, 8, 6, 2, 5, 4, 8, 3, 7]`):
 *   - Start with `left = 0` (1) and `right = 8` (7) -> area = `min(1, 7) * 8 = 8`. Move `left++` (shorter side).
 *   - `left = 1` (8) and `right = 8` (7) -> area = `min(8, 7) * 7 = 49`. Max updates to 49. Move `right--` (shorter side).
 *   - Continue shrinking the shorter side; no subsequent combination exceeds 49. Returns `49`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does moving the taller pointer inward guarantee we won't miss a better solution?
 *    (Because the container's height is strictly limited by the shorter of the two lines. Moving the taller line
 *    keeps or worsens the bottleneck while strictly decreasing the width, meaning the area can only shrink.)
 * 2. What happens if both lines have equal heights?
 *    (Moving either pointer is safe because neither can improve the bottleneck alone, though moving either
 *    maintains forward progress through the array.)
 */
public class P33_ContainerWithMostWater {

    public static int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;

        int left = 0;
        int right = height.length - 1;
        int maxWater = 0;

        while (left < right) {
            int width = right - left;
            int currentArea = width * Math.min(height[left], height[right]);
            maxWater = Math.max(maxWater, currentArea);

            // Greedily shift the shorter line inward to search for a taller bottleneck
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxWater;
    }

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("P33 Output: " + maxArea(heights));
        // Expected: 49 (between height 8 at idx 1 and height 7 at idx 8)
    }
}