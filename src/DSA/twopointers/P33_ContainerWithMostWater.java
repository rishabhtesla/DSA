package DSA.twopointers;

/**
 * ============================================================================
 * [33 / 34] - CONTAINER WITH MOST WATER (LeetCode 11)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer array height of length n, find two lines that together with
 *   the x-axis form a container, such that the container contains the most water.
 *   Return the maximum amount of water a container can store.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - `area = width * min(height[left], height[right])` where `width = right - left`.
 *   - Start with maximum width: `left = 0`, `right = n - 1`.
 *   - Which pointer do we move?
 *     Moving either pointer reduces the `width` by 1.
 *     If we move the taller line, the new area can NEVER exceed the current area
 *     because the bottleneck (`min` height) was already the shorter line, and width just shrank!
 *     The ONLY way to potentially find a larger area is to discard the shorter line
 *     in hopes of finding a significantly taller one.
 *   - Rule: Move whichever pointer is strictly shorter.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Left and right converge inward in a single scan.
 *   - Space: O(1) - Constant memory.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P33_ContainerWithMostWater {

    public static int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxWater = 0;

        while (left < right) {
            int width = right - left;
            int currentArea = width * Math.min(height[left], height[right]);
            maxWater = Math.max(maxWater, currentArea);

            // Shift the bottleneck pointer inward
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