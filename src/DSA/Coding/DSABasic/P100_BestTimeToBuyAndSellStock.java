package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P100: BEST TIME TO BUY AND SELL STOCK
 *
 * Question
 * Maximize profit from one buy followed by one sell.
 *
 * Example
 * [7,1,5,3,6,4] -> 5 (buy 1, sell 6).
 *
 * Constraints
 * 1 <= n <= 10^5; prices are non-negative.
 *
 * Approach used by edSlash
 * 1. Track the smallest price seen before today and maximize today’s price minus that minimum.
 * 2. Keep only the previous states needed to calculate the next state instead of recomputing earlier answers.
 * 3. Update the state in the same order as the recurrence so each iteration represents one more input position.
 *
 * Dry run
 *   Profit-state visual for [7,1,5,3,6,4]:
 *     minimum price starts at 7; profit = 0
 *     read 1 -> minimum price becomes 1
 *     read 5 -> profit = 5-1 = 4
 *     read 6 -> profit = 6-1 = 5, the best result
 *     return 5
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P100_BestTimeToBuyAndSellStock {
    public static int maxProfit(int[] nums) {
        int min = Integer.MAX_VALUE, ans = 0;
        for (int x : nums) {
            min = Math.min(min, x);
            ans = Math.max(ans, x - min);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }
}
