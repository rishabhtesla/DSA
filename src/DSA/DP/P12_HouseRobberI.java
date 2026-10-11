package DSA.DP;

/**
 * ============================================================================
 * [12 / 30] - HOUSE ROBBER I (LeetCode 198 / HackerRank "Max Array Sum")
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * You are a professional robber planning to rob houses along a street. 
 * Each house has a certain amount of money stashed. Adjacent houses have security 
 * systems connected, and it will automatically contact the police if two adjacent 
 * houses were broken into on the same night.
 *
 * Given an integer array `nums` representing the money of each house, return the 
 * maximum amount of money you can rob tonight without alerting the police.
 *
 *
 * 2. RECURRENCE & BINARY CHOICE LOGIC:
 * ----------------------------------------------------------------------------
 * At each house `i`, you have exactly two choices:
 *
 * Choice 1: SKIP house i
 *   - Profit is whatever maximum we earned up to house `i - 1`: dp[i - 1]
 *
 * Choice 2: ROB house i
 *   - You gain `nums[i]`, but you cannot rob house `i - 1`.
 *   - Profit is: nums[i] + dp[i - 2]
 *
 * Recurrence:
 *   dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2])
 *
 * Space Optimization:
 *   We only need two variables:
 *     - `robPrev2`: max money robbed up to two houses ago (dp[i - 2])
 *     - `robPrev1`: max money robbed up to the previous house (dp[i - 1])
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * nums = [2, 7, 9, 3, 1]
 *
 * Initial:
 *   robPrev2 = 0
 *   robPrev1 = 0
 *
 * House 0 (val = 2):
 *   curr = max(robPrev1, 2 + robPrev2) = max(0, 2 + 0) = 2
 *   robPrev2 = 0, robPrev1 = 2
 *
 * House 1 (val = 7):
 *   curr = max(2, 7 + 0) = 7
 *   robPrev2 = 2, robPrev1 = 7
 *
 * House 2 (val = 9):
 *   curr = max(7, 9 + 2) = 11
 *   robPrev2 = 7, robPrev1 = 11
 *
 * House 3 (val = 3):
 *   curr = max(11, 3 + 7) = 11
 *   robPrev2 = 11, robPrev1 = 11
 *
 * House 4 (val = 1):
 *   curr = max(11, 1 + 11) = 12
 *   robPrev2 = 11, robPrev1 = 12
 *
 * Final Result: 12 (Rob houses 0, 2, and 4 -> 2 + 9 + 1 = 12)
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n) - Single pass over the array.
 * - Space Complexity: O(1) - Constant variables.
 */
public class P12_HouseRobberI {

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int robPrev2 = 0; // dp[i - 2]
        int robPrev1 = 0; // dp[i - 1]

        for (int num : nums) {
            int curr = Math.max(robPrev1, num + robPrev2);
            robPrev2 = robPrev1;
            robPrev1 = curr;
        }

        return robPrev1;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 1};
        System.out.println("P12 Max Robbed [1, 2, 3, 1]:    " + rob(nums1)); // Expected: 4 (1 + 3)

        int[] nums2 = {2, 7, 9, 3, 1};
        System.out.println("P12 Max Robbed [2, 7, 9, 3, 1]: " + rob(nums2)); // Expected: 12 (2 + 9 + 1)
    }
}