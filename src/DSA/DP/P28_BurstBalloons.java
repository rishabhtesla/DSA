package DSA.DP;

/**
 * ============================================================================
 * [28 / 30] - BURST BALLOONS (LeetCode 312)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with 
 * a number represented by an array `nums`.
 * You are asked to burst all the balloons.
 * If you burst balloon `i`, you will get:
 *   nums[i - 1] * nums[i] * nums[i + 1] coins.
 * If i - 1 or i + 1 goes out of bounds, treat the boundary value as 1.
 * Return the MAXIMUM coins you can collect by bursting the balloons wisely.
 *
 *
 * 2. THE "REVERSE THINKING" AHA! MOMENT:
 * ----------------------------------------------------------------------------
 * Why Forward Thinking Fails:
 *   If you burst a balloon first, its adjacent neighbors merge together!
 *   This introduces dynamic dependencies across subproblems, destroying optimal substructure.
 *
 * The Fix (Think of which balloon is burst LAST in an interval):
 *   Instead of asking "Which balloon do I burst first?", ask:
 *   "Which balloon `k` in interval [i...j] do I burst LAST?"
 *
 *   If balloon `k` is the LAST balloon burst in interval [i...j]:
 *   - All other balloons in [i...j] have ALREADY been burst.
 *   - Therefore, the remaining adjacent neighbors of `k` are strictly the 
 *     boundaries: `padded[i - 1]` and `padded[j + 1]`!
 *   - The subproblems [i ... k - 1] and [k + 1 ... j] are completely independent!
 *
 * Recurrence:
 *   dp[i][j] = max over all k in [i...j]:
 *     dp[i][k - 1] + dp[k + 1][j] + (padded[i - 1] * padded[k] * padded[j + 1])
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * nums = [3, 1, 5, 8]
 * Padded array: [1, 3, 1, 5, 8, 1]  (length = 6, valid indices 1 to 4)
 *
 * len = 1 (Single balloon burst last):
 *   dp[1][1] = 1 * 3 * 1 = 3
 *   dp[2][2] = 3 * 1 * 5 = 15
 *   dp[3][3] = 1 * 5 * 8 = 40
 *   dp[4][4] = 5 * 8 * 1 = 40
 *
 * Expanding lengths up to len = 4...
 * Optimal order: burst 1 first, then 5, then 3, then 8 last:
 *   Coins = (3*1*5) + (3*5*8) + (1*3*8) + (1*8*1) = 15 + 120 + 24 + 8 = 167
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N^3) - All intervals [i...j] with split k.
 * - Space Complexity: O(N^2) - 2D DP array.
 */
public class P28_BurstBalloons {

    public static int maxCoins(int[] nums) {
        int n = nums.length;

        // Create padded array with 1 at both boundaries
        int[] padded = new int[n + 2];
        padded[0] = 1;
        padded[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            padded[i + 1] = nums[i];
        }

        // dp[i][j] = max coins from bursting balloons within index range [i...j]
        int[][] dp = new int[n + 2][n + 2];

        // len is the length of the window of balloons
        for (int len = 1; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;

                // Pick balloon k to burst LAST in the range [i...j]
                for (int k = i; k <= j; k++) {
                    int coins = dp[i][k - 1] + dp[k + 1][j] 
                              + (padded[i - 1] * padded[k] * padded[j + 1]);
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }

        return dp[1][n];
    }

    public static void main(String[] args) {
        int[] nums1 = {3, 1, 5, 8};
        System.out.println("P28 Burst Balloons [3, 1, 5, 8]: " + maxCoins(nums1)); // Expected: 167

        int[] nums2 = {1, 5};
        System.out.println("P28 Burst Balloons [1, 5]:       " + maxCoins(nums2)); // Expected: 10
    }
}