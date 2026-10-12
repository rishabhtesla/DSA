package DSA.slidingwindow;

/**
 * ============================================================================
 * [35 / 38] - MINIMUM SIZE SUBARRAY SUM (LeetCode 209)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of positive integers `nums` and a positive integer `target`, return
 * the minimal length of a subarray whose sum is greater than or equal to `target`.
 * If there is no such subarray, return `0` instead.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach:
 *    - Check all possible subarrays using nested loops to compute their sums.
 *    - Inefficient: takes $O(n^2)$ or $O(n^3)$ time, which will TLE (Time Limit Exceeded) for large inputs.
 *
 * 2. Optimal Variable-Size Sliding Window (Shrinkable Window) Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** Because all numbers in `nums` are **positive**, adding elements monotonically
 *      increases the window sum, and removing elements from the left monotonically decreases it.
 *    - **Step 1 (Expand):** Expand the window to the right by adding `nums[right]` to a running `currentSum`.
 *    - **Step 2 (Contract):** As long as `currentSum >= target`, update the minimum length found so far
 *      (`minLength = Math.min(minLength, right - left + 1)`), and then shrink the window from the left
 *      (`currentSum -= nums[left++]`) to find the tightest possible valid subarray.
 *    - **Step 3:** Because each element is added once and removed at most once, total operations are strictly linear.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Both the `right` and `left` pointers traverse the array at most once.
 * - Space: O(1) - Constant auxiliary tracking space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `target = 7`, `nums = [2, 3, 1, 2, 4, 3]`
 * - Expected Minimum Length: `2` (corresponding to subarray `[4, 3]`)
 *
 * VISUAL DRY RUN:
 *   - Expand `right` pointer, sums accumulate: `2`, `5`, `6`, `8`.
 *   - At `right = 3`, `currentSum >= target` (8 >= 7). Update length to 4. Shrink from left (`left` moves to 1), sum becomes 6.
 *   - Continue expanding and shrinking: when window hits `[4, 3]`, sum is 7, length is 2 (new minimum).
 *   - Final minimum length returned is `2`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does this sliding window approach require all numbers to be positive?
 *    (If negative numbers were allowed, adding an element could *decrease* the sum, meaning expanding the window
 *    would no longer guarantee a monotonic increase, breaking the shrinkable sliding window invariant.)
 * 2. What edge cases are handled correctly?
 *    (If no subarray sums up to or exceeds the target, `minLength` remains `Integer.MAX_VALUE`,
 *    triggering the ternary operator to return `0` cleanly.)
 */
public class P35_MinimumSizeSubarraySum {

    public static int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;

        // Expand the window to the right
        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];

            // Contract the window from the left while the sum meets or exceeds the target
            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                currentSum -= nums[left];
                left++;
            }
        }

        // Return 0 if no valid subarray was found
        return (minLength == Integer.MAX_VALUE) ? 0 : minLength;
    }

    public static void main(String[] args) {
        int target1 = 7;
        int[] nums1 = {2, 3, 1, 2, 4, 3};
        System.out.println("P35 Output (Test 1): " + minSubArrayLen(target1, nums1)); // Expected: 2 ([4, 3])

        int target2 = 4;
        int[] nums2 = {1, 4, 4};
        System.out.println("P35 Output (Test 2): " + minSubArrayLen(target2, nums2)); // Expected: 1 ([4])

        int target3 = 11;
        int[] nums3 = {1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println("P35 Output (Test 3): " + minSubArrayLen(target3, nums3)); // Expected: 0
    }
}