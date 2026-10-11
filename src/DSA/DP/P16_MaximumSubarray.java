package DSA.DP;

/**
 * ============================================================================
 * [16 / 30] - MAXIMUM SUBARRAY (LeetCode 53 / Classic Kadane's Algorithm)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an integer array `nums`, find the contiguous subarray (containing at least 
 * one number) which has the largest sum and return its sum.
 *
 *
 * 2. RECURRENCE & DECISION LOGIC:
 * ----------------------------------------------------------------------------
 * A contiguous subarray ending at index `i` can either:
 *   Choice 1: Extend the optimal subarray ending at `i - 1` by appending `nums[i]`.
 *   Choice 2: Start a brand-new subarray beginning right at `nums[i]` (discarding 
 *             all previous elements because their cumulative sum was negative).
 *
 * State:
 *   dp[i] = maximum contiguous subarray sum ending strictly at index `i`.
 *
 * Recurrence:
 *   dp[i] = Math.max(nums[i], dp[i - 1] + nums[i])
 *
 * Global Answer:
 *   max(dp[0], dp[1], ..., dp[n - 1])
 *
 * Space Optimization:
 *   Since `dp[i]` only depends on `dp[i - 1]`, we replace the entire array with
 *   a single variable `currentMax`. Memory drops from O(n) to O(1).
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * Initial: currentMax = -2, globalMax = -2
 *
 * i=1 (val= 1): currentMax = max(1, -2 + 1) = 1  | globalMax = max(-2, 1) = 1
 * i=2 (val=-3): currentMax = max(-3, 1 - 3) = -2 | globalMax = max(1, -2) = 1
 * i=3 (val= 4): currentMax = max(4, -2 + 4) = 4  | globalMax = max(1, 4) = 4
 * i=4 (val=-1): currentMax = max(-1, 4 - 1) = 3  | globalMax = max(4, 3) = 4
 * i=5 (val= 2): currentMax = max(2, 3 + 2) = 5   | globalMax = max(4, 5) = 5
 * i=6 (val= 1): currentMax = max(1, 5 + 1) = 6   | globalMax = max(5, 6) = 6
 * i=7 (val=-5): currentMax = max(-5, 6 - 5) = 1  | globalMax = max(6, 1) = 6
 * i=8 (val= 4): currentMax = max(4, 1 + 4) = 5   | globalMax = max(6, 5) = 6
 *
 * Result: 6 (Subarray: [4, -1, 2, 1])
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n) - Single pass through the array.
 * - Space Complexity: O(1) - Constant auxiliary variables.
 */
public class P16_MaximumSubarray {

    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int currentMax = nums[0];
        int globalMax = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Either start a new subarray at nums[i] or extend the previous one
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            globalMax = Math.max(globalMax, currentMax);
        }

        return globalMax;
    }

    public static void main(String[] args) {
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("P16 Max Subarray Sum: " + maxSubArray(nums1)); // Expected: 6

        int[] nums2 = {-1, -2, -3};
        System.out.println("P16 All Negative:     " + maxSubArray(nums2)); // Expected: -1
    }
}