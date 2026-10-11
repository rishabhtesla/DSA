package DSA.DP;

/**
 * ============================================================================
 * [05 / 30] - TARGET SUM (LeetCode 494)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an integer array `nums` and an integer `target`.
 * You want to build an expression out of nums by adding one of the symbols 
 * '+' and '-' before each integer in nums and then concatenate all integers.
 *
 * Return the number of different expressions that evaluate to `target`.
 *
 *
 * 2. THE MATHEMATICAL "AHA!" REDUCTION:
 * ----------------------------------------------------------------------------
 * Assigning '+' or '-' partitions the array into two subsets:
 *   P = subset of numbers given a '+'
 *   N = subset of numbers given a '-'
 *
 * By definition:
 *   (1) sum(P) - sum(N) = target
 *   (2) sum(P) + sum(N) = totalSum
 *
 * Add equation (1) and (2) together:
 *   sum(P) - sum(N) + sum(P) + sum(N) = target + totalSum
 *   2 * sum(P) = target + totalSum
 *   sum(P) = (target + totalSum) / 2
 *
 * This reduces the problem to:
 *   "Find the NUMBER OF SUBSETS whose sum equals: subsetTarget = (target + totalSum) / 2"
 *
 * INVALID CASES (Immediate Return 0):
 *   1. If Math.abs(target) > totalSum: Impossible to reach target.
 *   2. If (target + totalSum) is ODD: sum(P) must be an integer, so impossible.
 *
 *
 * 3. RECURRENCE & 1D COUNTING KNAPSACK:
 * ----------------------------------------------------------------------------
 * State:
 *   dp[s] = number of subsets that sum to `s`.
 *
 * Base Case:
 *   dp[0] = 1 (1 way to sum to 0 using an empty subset).
 *
 * Backward Traversal (0/1 Knapsack):
 *   dp[s] = dp[s] + dp[s - num]
 *   *Note: s iterates down to `num`, but can stop at 0 if num is 0.*
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * nums = [1, 1, 1, 1, 1], target = 3
 * totalSum = 5
 * (target + totalSum) = 3 + 5 = 8 (Even)
 * subsetTarget = 8 / 2 = 4
 *
 * dp array of size 5: [1, 0, 0, 0, 0]
 *
 * For each '1' in nums (repeated 5 times):
 *   Num 1: dp[4]+=dp[3], dp[3]+=dp[2], dp[2]+=dp[1], dp[1]+=dp[0] -> [1, 1, 0, 0, 0]
 *   Num 2: [1, 2, 1, 0, 0]
 *   Num 3: [1, 3, 3, 1, 0]
 *   Num 4: [1, 4, 6, 4, 1]
 *   Num 5: [1, 5, 10, 10, 5]
 *
 * Result: dp[4] = 5
 * (There are 5 ways to pick four +1s and one -1 to equal 3).
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N * subsetTarget) where subsetTarget = (target + totalSum) / 2.
 * - Space Complexity: O(subsetTarget) using 1D count array.
 */
public class P05_TargetSum {

    public static int findTargetSumWays(int[] nums, int target) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // Validity Checks
        if (Math.abs(target) > totalSum) {
            return 0;
        }
        if ((target + totalSum) % 2 != 0) {
            return 0;
        }

        int subsetTarget = (target + totalSum) / 2;
        int[] dp = new int[subsetTarget + 1];
        dp[0] = 1; // 1 way to form sum 0

        for (int num : nums) {
            // Must traverse backwards to ensure 0/1 knapsack property
            for (int s = subsetTarget; s >= num; s--) {
                dp[s] += dp[s - num];
            }
        }

        return dp[subsetTarget];
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;
        System.out.println("P05 Target Sum Ways: " + findTargetSumWays(nums, target)); // Expected: 5

        int[] nums2 = {1};
        int target2 = 1;
        System.out.println("P05 Target Sum Ways: " + findTargetSumWays(nums2, target2)); // Expected: 1
    }
}