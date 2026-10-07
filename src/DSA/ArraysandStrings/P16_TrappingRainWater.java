package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [16 / 24] - TRAPPING RAIN WATER (LeetCode 42)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given n non-negative integers representing an elevation map where width
 *   of each bar is 1, compute how much water it can trap after raining.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Water trapped at index i is determined strictly by:
 *     water[i] = max(0, min(maxLeft, maxRight) - height[i]).
 *   - Prefix/Suffix Max arrays take O(n) time and O(n) space.
 *   - Optimal Two-Pointer approach (O(1) Space):
 *     Use two pointers `left = 0` and `right = n - 1` along with `leftMax` and `rightMax`.
 *     At any step:
 *     - If `leftMax < rightMax`, the bottleneck for the `left` bar is guaranteed to be
 *       `leftMax` (because even if there's a higher wall further right, `rightMax` already exceeds it).
 *       Thus, water trapped at `left` = `leftMax - height[left]`. Advance `left`.
 *     - Otherwise, the bottleneck for `right` is `rightMax`. Advance `right`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass where left and right converge.
 *   - Space: O(1) - Pointers and maximum height trackers only.
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