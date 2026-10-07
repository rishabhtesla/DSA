package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [08 / 24] - BEST TIME TO BUY AND SELL STOCK II (LeetCode 122)
 * ============================================================================
 * 
 * PROBLEM:
 *   You may complete as many transactions as you like (buy one and sell one
 *   multiple times). You cannot hold multiple shares at once. Find max profit.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Greedy Peak-Valley Strategy:
 *     Any continuous price climb from Day A to Day C can be broken into daily gains:
 *     (prices[C] - prices[A]) = (prices[C] - prices[B]) + (prices[B] - prices[A]).
 *   - Therefore, simply capture every positive daily slope:
 *     If prices[i] > prices[i - 1], accumulate (prices[i] - prices[i - 1]).
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single linear iteration.
 *   - Space: O(1) - Constant auxiliary memory.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P08_BestTimeToBuyAndSellStockII {

    public static int maxProfit(int[] prices) {
        int totalProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                totalProfit += prices[i] - prices[i - 1];
            }
        }

        return totalProfit;
    }

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("P08 Output: " + maxProfit(prices));
        // Expected: 7 ((5 - 1) + (6 - 3))
    }
}