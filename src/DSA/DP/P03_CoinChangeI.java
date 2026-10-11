package DSA.DP;

import java.util.Arrays;

/**
 * ============================================================================
 * [03 / 30] - COIN CHANGE I (LeetCode 322)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an integer array `coins` representing coin denominations and an integer 
 * `amount`, return the FEWEST NUMBER OF COINS needed to make up that amount.
 * If that amount of money cannot be made up by any combination, return -1.
 * You may assume you have an infinite number of each kind of coin.
 *
 *
 * 2. RECURRENCE & DECISION LOGIC:
 * ----------------------------------------------------------------------------
 * Instead of counting combinations, we minimize the count of items used.
 *
 * State:
 *   dp[a] = minimum number of coins needed to form amount `a`.
 *
 * Base Case:
 *   dp[0] = 0 (0 coins required to form sum 0).
 *   All other dp[a] = amount + 1 (sentinel value representing infinity).
 *   *Why not Integer.MAX_VALUE?*
 *   Adding 1 to Integer.MAX_VALUE causes integer overflow into negative numbers.
 *   Using `amount + 1` is safe because the maximum possible answer is `amount` 
 *   (using all 1-cent coins).
 *
 * Transition:
 *   For every target `a` from 1 to `amount`:
 *     For each `coin` in coins:
 *       If coin <= a:
 *         dp[a] = Math.min(dp[a], 1 + dp[a - coin])
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * coins = [1, 2, 5], amount = 11
 *
 * dp initialized: [0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12]
 *
 * Target a = 1:  min(12, 1 + dp[0]) = 1               -> dp[1] = 1
 * Target a = 2:  coin 1 -> 1+dp[1]=2; coin 2 -> 1+dp[0]=1 -> dp[2] = 1
 * Target a = 3:  coin 1 -> 1+dp[2]=2; coin 2 -> 1+dp[1]=2 -> dp[3] = 2
 * Target a = 5:  coin 5 -> 1+dp[0]=1                  -> dp[5] = 1
 * Target a = 6:  coin 5 -> 1+dp[1]=2                  -> dp[6] = 2
 * Target a = 10: coin 5 -> 1+dp[5]=2                  -> dp[10] = 2
 * Target a = 11: coin 5 -> 1+dp[6]=3; coin 1 -> 1+dp[10]=3 -> dp[11] = 3
 *
 * Coins used for 11: {5, 5, 1} -> 3 coins.
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N * amount) where N = number of coin denominations.
 * - Space Complexity: O(amount) using a single 1D array.
 */
public class P03_CoinChangeI {

    public static int coinChange(int[] coins, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;

        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0; // 0 coins needed for sum 0

        for (int a = 1; a <= amount; a++) {
            for (int coin : coins) {
                if (coin <= a) {
                    dp[a] = Math.min(dp[a], 1 + dp[a - coin]);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println("P03 Minimum Coins for 11: " + coinChange(coins, amount)); // Expected: 3

        int[] impossibleCoins = {2};
        int impossibleAmount = 3;
        System.out.println("P03 Minimum Coins for 3:  " + coinChange(impossibleCoins, impossibleAmount)); // Expected: -1
    }
}