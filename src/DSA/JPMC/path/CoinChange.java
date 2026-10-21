package DSA.JPMC.path; /**
 * PROBLEM: Coin Change
 * You are given an integer array coins and an integer amount. Return the fewest number 
 * of coins that you need to make up that amount. If it cannot be made up, return -1.
 * 
 * EXAMPLE: Input: coins = [1,2,5], amount = 11 -> Output: 3 (5 + 5 + 1)
 * 
 * LOGIC:
 * Use a DP array where dp[i] stores the minimum coins needed to make amount 'i'.
 * Initialize the array with an impossibly high number (amount + 1).
 * Base case: dp[0] = 0 (0 coins for amount 0).
 * For each coin, iterate through all amounts from the coin's value up to the target amount,
 * checking if using this coin yields a smaller total coin count.
 * 
 * COMPLEXITY: Time: O(amount * N), Space: O(amount)
 */
import java.util.Arrays;

/**
 * Learning supplement: brute-force coin-choice recursion is exponential;
 * bottom-up DP is O(amount * numberOfCoins) time and O(amount) space.
 * Examples: {@code [1,2,5],11 -> 3} and {@code [2],3 -> -1}. Invariant:
 * dp[a] is the fewest coins currently known for amount a. For amount 3 with
 * coins 1 and 2, dp becomes [0,1,1,2]. Ask why the loop allows repeated use
 * of a denomination and what sentinel means unreachable.
 */
public class CoinChange {
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // Use amount + 1 as "infinity"
        dp[0] = 0;
        
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        System.out.println("Min coins: " + coinChange(new int[]{1, 2, 5}, 11)); // 3
    }
}