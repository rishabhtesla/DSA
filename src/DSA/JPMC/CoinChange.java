package DSA.JPMC; /**
 * PROBLEM: Coin Change
 * You are given an integer array coins representing coins of different denominations 
 * and an integer amount representing a total amount of money.
 * Return the fewest number of coins that you need to make up that amount. 
 * If that amount of money cannot be made up, return -1.
 * 
 * EXAMPLE:
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 * 
 * LOGIC:
 * We use an array `dp` where dp[i] represents the minimum coins needed to make amount `i`.
 * We initialize the array with a dummy max value (amount + 1).
 * Base case: dp[0] = 0 (0 coins needed to make amount 0).
 * For each coin, and for every amount from the coin's value up to the target amount, 
 * we check if using this coin results in fewer total coins than we previously calculated.
 * Formula: dp[i] = min(dp[i], dp[i - coin] + 1)
 * 
 * DRY RUN: coins = [1, 2], amount = 3
 * dp array initialized to: [0, 4, 4, 4] (size is amount + 1, max is 4)
 * - Coin = 1:
 *   dp[1] = min(dp[1], dp[1-1] + 1) -> min(4, 0+1) = 1
 *   dp[2] = min(dp[2], dp[2-1] + 1) -> min(4, 1+1) = 2
 *   dp[3] = min(dp[3], dp[3-1] + 1) -> min(4, 2+1) = 3
 * - Coin = 2:
 *   dp[2] = min(dp[2], dp[2-2] + 1) -> min(2, 0+1) = 1
 *   dp[3] = min(dp[3], dp[3-2] + 1) -> min(3, 1+1) = 2
 * Final dp array: [0, 1, 1, 2]. dp[3] is 2 (which is 2+1).
 * 
 * COMPLEXITY: Time: O(amount * N) where N is number of coins. Space: O(amount).
 */
import java.util.Arrays;

public class CoinChange {
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        
        // Initialize with a value strictly greater than any possible answer (amount + 1)
        Arrays.fill(dp, amount + 1);
        dp[0] = 0; // 0 coins needed for amount 0
        
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        
        // If the target amount is still amount + 1, it means it's unreachable
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println("Minimum coins required: " + coinChange(coins, amount)); // Output: 3
    }
}