package DSA.JPMC;

/**
 * PROBLEM: Best Time to Buy and Sell Stock
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing 
 * a different day in the future to sell that stock.
 * 
 * EXAMPLE:
 * Input: prices = [7, 1, 5, 3, 6, 4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * 
 * LOGIC:
 * We want to find the maximum difference between two numbers (prices[sell] - prices[buy]) 
 * where the sell day is AFTER the buy day.
 * As we iterate through the array, we keep track of the lowest price seen so far.
 * If we find a higher price, we calculate the profit and update the max profit.
 * 
 * DRY RUN: prices = [7, 1, 5, 3, 6, 4]
 * - Day 1 (7): minPrice = 7, maxProfit = 0
 * - Day 2 (1): minPrice = 1, maxProfit = 0 (1-7 is negative)
 * - Day 3 (5): minPrice = 1, profit = 5-1 = 4. maxProfit = 4
 * - Day 4 (3): minPrice = 1, profit = 3-1 = 2. maxProfit = 4
 * - Day 5 (6): minPrice = 1, profit = 6-1 = 5. maxProfit = 5
 * - Day 6 (4): minPrice = 1, profit = 4-1 = 3. maxProfit = 5
 * Result = 5.
 * 
 * COMPLEXITY: Time: O(N), Space: O(1)
 *
 * ADDITIONAL LEARNING NOTES:
 * Buying and selling every pair is O(n^2); tracking the lowest price so far
 * gives O(n) time and O(1) space. Examples: [7,1,5,3,6,4] -> 5 and
 * [7,6,4,3,1] -> 0. Invariant: after day i, minPrice is the smallest
 * earlier price and bestProfit is the best completed trade. On the main
 * sequence, minPrice becomes 1, then selling at 5/6 updates best to 5.
 * Questions: why can the sell day never precede the buy day, and why is a
 * negative profit replaced by zero?
 */
public class BuySellStock {
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            // Update the minimum price encountered so far
            if (price < minPrice) {
                minPrice = price;
            } 
            // If the current price yields a better profit, update maxProfit
            else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("Maximum Profit: " + maxProfit(prices)); // Output: 5
    }
}