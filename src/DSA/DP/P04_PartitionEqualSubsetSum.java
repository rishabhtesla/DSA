package DSA.DP;

/**
 * ============================================================================
 * [04 / 30] - PARTITION EQUAL SUBSET SUM (LeetCode 416)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an integer array `nums`, return true if you can partition the array into 
 * two subsets such that the sum of the elements in both subsets is equal.
 *
 *
 * 2. MATHEMATICAL REDUCTION TO 0/1 KNAPSACK:
 * ----------------------------------------------------------------------------
 * Let totalSum be the sum of all elements in `nums`.
 *
 * Observation 1:
 *   If totalSum is ODD, it is impossible to divide into two equal integer halves.
 *   Immediately return false!
 *
 * Observation 2:
 *   If totalSum is EVEN, the problem transforms to:
 *   "Can we find ANY subset of numbers that adds up exactly to target = totalSum / 2?"
 *   If one subset sums to target, the remaining elements automatically sum to target.
 *
 *
 * 3. RECURRENCE & 1D BOOLEAN KNAPSACK:
 * ----------------------------------------------------------------------------
 * State:
 *   dp[s] = true if a subset sum of value `s` is achievable.
 *
 * Base Case:
 *   dp[0] = true (sum 0 is always achievable with an empty set).
 *
 * Direction of Loop:
 *   Because each element in `nums` can be picked AT MOST ONCE (0/1 Knapsack),
 *   we must iterate the sum backwards: `s` from `target` down to `num`.
 *
 * Transition:
 *   dp[s] = dp[s] || dp[s - num]
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * nums = [1, 5, 11, 5]
 * Total sum = 22 (Even) -> target = 22 / 2 = 11.
 * Array size: target + 1 = 12
 * Initial dp: [T, F, F, F, F, F, F, F, F, F, F, F]
 *
 * Pass 1: num = 1 (s: 11 down to 1)
 *   dp[1] = dp[1] || dp[0] -> TRUE
 *   dp = [T, T, F, F, F, F, F, F, F, F, F, F]
 *
 * Pass 2: num = 5 (s: 11 down to 5)
 *   dp[6] = dp[6] || dp[1] -> TRUE
 *   dp[5] = dp[5] || dp[0] -> TRUE
 *   dp = [T, T, F, F, F, T, T, F, F, F, F, F]
 *
 * Pass 3: num = 11 (s: 11 down to 11)
 *   dp[11] = dp[11] || dp[0] -> TRUE!
 *   We hit dp[target] == true immediately!
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N * target) where target = sum / 2.
 * - Space Complexity: O(target) using 1D boolean array.
 */
public class P04_PartitionEqualSubsetSum {

    public static boolean canPartition(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // Odd sum cannot be partitioned into two equal integer halves
        if (totalSum % 2 != 0) {
            return false;
        }

        int target = totalSum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true; // Base case: sum 0 is always possible

        for (int num : nums) {
            // Traverse backwards so each number is used only once
            for (int s = target; s >= num; s--) {
                dp[s] = dp[s] || dp[s - num];
            }

            // Early exit optimization
            if (dp[target]) {
                return true;
            }
        }

        return dp[target];
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 5, 11, 5};
        System.out.println("P04 Can Partition [1, 5, 11, 5]: " + canPartition(nums1)); // Expected: true (Subsets: {11} and {1, 5, 5})

        int[] nums2 = {1, 2, 3, 5};
        System.out.println("P04 Can Partition [1, 2, 3, 5]:  " + canPartition(nums2)); // Expected: false
    }
}