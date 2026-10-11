package DSA.DP;

/**
 * ============================================================================
 * [13 / 30] - HOUSE ROBBER II (LeetCode 213)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * You are a professional robber planning to rob houses along a street. 
 * All houses at this place are arranged IN A CIRCLE. 
 * That means the first house is the neighbor of the last one. 
 *
 * Meanwhile, adjacent houses have a security system connected: if two adjacent 
 * houses are broken into on the same night, the alarm will trigger.
 *
 * Given an integer array `nums` representing the amount of money of each house, 
 * return the maximum amount of money you can rob tonight without alerting the police.
 *
 *
 * 2. THE CIRCULAR "AHA!" REDUCTION:
 * ----------------------------------------------------------------------------
 * Because House 0 and House (n - 1) are adjacent, they CANNOT both be robbed.
 * This naturally splits into TWO mutually exclusive linear subproblems:
 *
 * Case 1: Rob houses in range [0 ... n - 2]
 *   - We may rob House 0, but we explicitly EXCLUDE the last house (n - 1).
 *
 * Case 2: Rob houses in range [1 ... n - 1]
 *   - We may rob House (n - 1), but we explicitly EXCLUDE the first house (0).
 *
 * Overall Answer:
 *   max(robLinear(nums, 0, n - 2), robLinear(nums, 1, n - 1))
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * nums = [2, 3, 2] (length = 3)
 *
 * Subproblem 1: Range [0 ... 1] -> subarray [2, 3]
 *   - House 0: 2
 *   - House 1: max(2, 3) = 3
 *   - Result 1 = 3
 *
 * Subproblem 2: Range [1 ... 2] -> subarray [3, 2]
 *   - House 1: 3
 *   - House 2: max(3, 2) = 3
 *   - Result 2 = 3
 *
 * Global Answer: max(3, 3) = 3 (Robbing house 0 and 2 together is illegal).
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n) - Two linear passes of House Robber I.
 * - Space Complexity: O(1) - Constant variables.
 */
public class P13_HouseRobberII {

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        if (n == 1) return nums[0];
        if (n == 2) return Math.max(nums[0], nums[1]);

        // Take the max of:
        // 1. Robbing from first house to second-to-last house
        // 2. Robbing from second house to last house
        return Math.max(robRange(nums, 0, n - 2), robRange(nums, 1, n - 1));
    }

    private static int robRange(int[] nums, int start, int end) {
        int robPrev2 = 0;
        int robPrev1 = 0;

        for (int i = start; i <= end; i++) {
            int curr = Math.max(robPrev1, nums[i] + robPrev2);
            robPrev2 = robPrev1;
            robPrev1 = curr;
        }

        return robPrev1;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 2};
        System.out.println("P13 Circular Rob [2, 3, 2]:    " + rob(nums1)); // Expected: 3

        int[] nums2 = {1, 2, 3, 1};
        System.out.println("P13 Circular Rob [1, 2, 3, 1]: " + rob(nums2)); // Expected: 4 (House 1 and 3 -> 1 + 3)
    }
}