package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [07 / 24] - BEST TIME TO BUY AND SELL STOCK (LeetCode 121)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array prices[] where prices[i] is the price on the ith day,
 *   find the maximum profit from buying on one day and selling on a future day.
 *   If no profit is possible, return 0.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Naive: Check all pairs (i, j) with j > i -> O(n^2).
 *   - Optimal (One Pass): As you scan each day, ask:
 *     "If I sell today, what is the best past day I could have bought on?"
 *   - Track the minimum price seen so far (`minPrice`).
 *   - Profit if sold today is `price - minPrice`. Track the maximum profit encountered.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass through the prices array.
 *   - Space: O(1) - Only two scalar tracking variables.
 *
 *
 * EXAMPLE:
 *   Input: prices = [7, 1, 5, 3, 6, 4]
 *   Output: 5
 *
 * VISUAL DRY RUN:
 *   price | minPrice | profit if sold today | maxProfit
 *   ------+----------+----------------------+----------
 *     7   |    7     |          -           |    0
 *     1   |    1     |          -           |    0
 *     5   |    1     |          4           |    4
 *     3   |    1     |          2           |    4
 *     6   |    1     |          5           |    5
 *     4   |    1     |          3           |    5
 *   The best decision is buy at 1 and sell at 6, giving profit 5.
 *
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P07_BestTimeToBuyAndSellStock {

    public static int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price; // Record new historical low buy price
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice);
            }
        }

        return maxProfit;
    }

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("P07 Output: " + maxProfit(prices));
        // Expected: 5 (Buy at 1, Sell at 6)
    }
}