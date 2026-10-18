package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION 14: Minimum Settlement Denominations
 * ============================================================================
 *
 * Problem Statement:
 * You are given an integer array coins representing cash settlement denominations
 * and an integer amount representing a target balance to settle.
 *
 * Return the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins,
 * return -1.
 *
 * You may assume that you have an infinite number of each kind of coin.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the number of coin denominations.
 * - Next n lines: Each line contains coins[i].
 * - Last line: An integer amount.
 *
 * Constraints:
 * - 1 <= coins.length <= 12
 * - 1 <= coins[i] <= 2^31 - 1
 * - 0 <= amount <= 10^4
 *
 * Example 1:
 * Input:
 *   3
 *   1
 *   2
 *   5
 *   11
 * Output:
 *   3
 * Explanation: 11 = 5 + 5 + 1 (3 coins total)
 *
 * Example 2:
 * Input:
 *   1
 *   2
 *   3
 * Output:
 *   -1
 *
 * Example 3:
 * Input:
 *   1
 *   1
 *   0
 * Output:
 *   0
 *
 * ============================================================================
 * LOGIC & APPROACH (Bottom-Up 1D DP):
 * ============================================================================
 * 1. State Definition:
 *    Let dp[x] = minimum number of coins needed to make amount x.
 *
 * 2. Base Cases:
 *    - dp[0] = 0 (0 coins required to form 0 amount).
 *    - dp[1...amount] = infinity (represented as amount + 1, since the maximum
 *      possible coins is amount using all 1-cent coins).
 *
 * 3. Transitions:
 *    For each amount x from 1 to target amount:
 *      For each coin in coins:
 *        if x - coin >= 0:
 *          dp[x] = min(dp[x], dp[x - coin] + 1)
 *
 * 4. Result:
 *    If dp[amount] > amount, the target is unreachable -> return -1.
 *    Otherwise -> return dp[amount].
 *
 * Complexity:
 * - Time: O(amount * n)
 * - Space: O(amount)
 *
 * ============================================================================
 * DRY RUN (coins = [1, 2, 5], amount = 6):
 * ============================================================================
 * Initialize dp array of size 7 with sentinel 7:
 * dp = [0, 7, 7, 7, 7, 7, 7]
 *
 * x = 1: coin 1 -> dp[1] = min(7, dp[0] + 1) = 1
 * x = 2: coin 1 -> dp[2] = min(7, dp[1] + 1) = 2
 *        coin 2 -> dp[2] = min(2, dp[0] + 1) = 1
 * x = 3: coin 1 -> dp[3] = min(7, dp[2] + 1) = 2
 *        coin 2 -> dp[3] = min(2, dp[1] + 1) = 2
 * x = 4: coin 1 -> dp[4] = min(7, dp[3] + 1) = 3
 *        coin 2 -> dp[4] = min(3, dp[2] + 1) = 2
 * x = 5: coin 1 -> dp[5] = min(7, dp[4] + 1) = 3
 *        coin 2 -> dp[5] = min(3, dp[3] + 1) = 3
 *        coin 5 -> dp[5] = min(3, dp[0] + 1) = 1
 * x = 6: coin 1 -> dp[6] = min(7, dp[5] + 1) = 2
 *        coin 2 -> dp[6] = min(2, dp[4] + 1) = 3 -> 2
 *        coin 5 -> dp[6] = min(2, dp[1] + 1) = 2
 *
 * Final dp[6] = 2 (coins: 5 + 1)
 * Output: 2
 * ============================================================================
 */
public class SolutionCoinChange {

    public static int coinChange(List<Integer> coins, int amount) {
        if (amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }

        int maxSentinel = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, maxSentinel);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != maxSentinel) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Integer> coins = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            coins.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        int amount = Integer.parseInt(bufferedReader.readLine().trim());

        int result = coinChange(coins, amount);
        System.out.println(result);

        bufferedReader.close();
    }
}