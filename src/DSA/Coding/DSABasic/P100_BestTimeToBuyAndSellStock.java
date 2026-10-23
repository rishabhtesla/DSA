package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Maximize profit from one buy followed by one sell.
 * EXAMPLE: [7,1,5,3,6,4] -> 5 (buy 1, sell 6).
 * CONSTRAINTS: 1 <= n <= 10^5; prices are non-negative.
 * LOGIC / ALGORITHM:
 * 1. Track the smallest price seen before today and maximize today’s price minus that minimum.
 * 2. For each price, update the minimum prefix price before computing the best profit ending today.
 * 3. Return the largest profit; leaving ans at zero handles prices that never rise.
 * INVARIANT: Before processing today, min is the lowest earlier price and ans is the best profit in the processed prefix.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [7,1,5,3,6,4] -> 5 (buy 1, sell 6).; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P100_BestTimeToBuyAndSellStock {
    public static int maxProfit(int[] a) {
        int min = Integer.MAX_VALUE, ans = 0;
        for (int x : a) {
            min = Math.min(min, x);
            ans = Math.max(ans, x - min);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }
}
