package DSA.DP;

import java.util.Arrays;

/**
 * ============================================================================
 * [02 / 30] - COIN CHANGE II (LeetCode 518 / HackerRank "The Coin Change Problem")
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an integer array `coins` representing coins of different denominations 
 * and an integer `amount` representing a total sum:
 * Return the NUMBER OF DISTINCT COMBINATIONS that make up that amount.
 * You may assume an infinite number of each kind of coin.
 *
 * NOTE: Combinations vs Permutations:
 *   [1, 2] and [2, 1] represent the SAME combination. We must not double-count!
 *
 *
 * 2. RECURRENCE & THE "OUTER LOOP" AHA! MOMENT:
 * ----------------------------------------------------------------------------
 * Why coin loop outside, amount loop inside?
 *   - If we process coins one by one in the outer loop, every combination is 
 *     formed in a sorted order of coin selection (e.g., all 1s first, then all 2s).
 *     This strictly avoids permutation duplicates like {1, 2} vs {2, 1}.
 *
 * Why forward traversal (amount = coin -> target)?
 *   - Unlike 0/1 Knapsack where each item is used AT MOST ONCE (requiring backward scan),
 *     here each coin can be REUSED infinitely.
 *   - Traversing forward allows `dp[amount]` to build upon the updated `dp[amount - coin]`,
 *     which already accounts for using this same coin multiple times.
 *
 * Recurrence:
 *   dp[amount] = dp[amount] + dp[amount - coin]
 *
 * Base Case:
 *   dp[0] = 1 (There is exactly 1 way to form an amount of 0: pick empty set of coins).
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * coins = [1, 2, 5], amount = 5
 * Initial dp: [1, 0, 0, 0, 0, 0]
 *
 * Outer Loop 1 (coin = 1):
 *   dp[1] += dp[0] -> 1
 *   dp[2] += dp[1] -> 1
 *   dp[3] += dp[2] -> 1
 *   dp[4] += dp[3] -> 1
 *   dp[5] += dp[4] -> 1
 *   dp after coin 1: [1, 1, 1, 1, 1, 1]
 *
 * Outer Loop 2 (coin = 2):
 *   dp[2] += dp[0] -> 1 + 1 = 2   ({1,1}, {2})
 *   dp[3] += dp[1] -> 1 + 1 = 2   ({1,1,1}, {1,2})
 *   dp[4] += dp[2] -> 1 + 2 = 3   ({1,1,1,1}, {1,1,2}, {2,2})
 *   dp[5] += dp[3] -> 1 + 2 = 3   ({1,1,1,1,1}, {1,1,1,2}, {1,2,2})
 *   dp after coin 2: [1, 1, 2, 2, 3, 3]
 *
 * Outer Loop 3 (coin = 5):
 *   dp[5] += dp[0] -> 3 + 1 = 4   (previous 3 ways + {5})
 *   dp after coin 5: [1, 1, 2, 2, 3, 4]
 *
 * Final Result: dp[5] = 4
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N * amount) where N = number of coin types.
 * - Space Complexity: O(amount) using a single 1D array.
 */
public class P02_CoinChangeII {

    public static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1; // 1 way to make 0

        for (int coin : coins) {
            for (int a = coin; a <= amount; a++) {
                dp[a] += dp[a - coin];
            }
        }

        return dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 5;
        System.out.println("P02 Distinct Combinations: " + change(amount, coins)); // Expected: 4
    }
}