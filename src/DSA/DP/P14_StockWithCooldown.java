package DSA.DP;

/**
 * ============================================================================
 * [14 / 30] - STOCK WITH COOLDOWN (LeetCode 309)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * You are given an array `prices` where `prices[i]` is the price of a given 
 * stock on the i-th day.
 * Find the maximum profit you can achieve. You may complete as many transactions 
 * as you like with the following restrictions:
 *   - After you sell your stock, you cannot buy stock on the next day (cooldown of 1 day).
 *   - You may not engage in multiple transactions simultaneously (must sell before buying again).
 *
 *
 * 2. FINITE STATE MACHINE (FSM) ARCHITECTURE:
 * ----------------------------------------------------------------------------
 * On any given day, you can be in one of three states:
 *
 * State 0: `hold` (Holding 1 share of stock)
 *   - Arrived by: Continuing to hold from yesterday, OR buying today (which requires 
 *     having been in `rest` yesterday due to cooldown).
 *   - Transition: hold[i] = max(hold[i - 1], rest[i - 1] - prices[i])
 *
 * State 1: `sold` (Just sold stock today; triggers mandatory cooldown tomorrow)
 *   - Arrived by: Selling the stock that was held yesterday.
 *   - Transition: sold[i] = hold[i - 1] + prices[i]
 *
 * State 2: `rest` (Free to buy; can stay idle or just completed cooldown)
 *   - Arrived by: Continuing to rest from yesterday, OR entering rest from yesterday's `sold` state.
 *   - Transition: rest[i] = max(rest[i - 1], sold[i - 1])
 *
 *
 * 3. BASE CASES & INITIALIZATION:
 * ----------------------------------------------------------------------------
 * Day 0:
 *   hold = -prices[0] (we bought on day 0)
 *   sold = 0          (cannot have sold yet on day 0)
 *   rest = 0          (idle with 0 profit)
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * prices = [1, 2, 3, 0, 2]
 *
 * Day 0 (price = 1):
 *   hold = -1, sold = 0, rest = 0
 *
 * Day 1 (price = 2):
 *   nextHold = max(-1, rest(0) - 2) = -1
 *   nextSold = hold(-1) + 2 = 1
 *   nextRest = max(0, sold(0)) = 0
 *   -> hold = -1, sold = 1, rest = 0
 *
 * Day 2 (price = 3):
 *   nextHold = max(-1, 0 - 3) = -1
 *   nextSold = -1 + 3 = 2
 *   nextRest = max(0, 1) = 1
 *   -> hold = -1, sold = 2, rest = 1
 *
 * Day 3 (price = 0):
 *   nextHold = max(-1, rest(1) - 0) = 1  (Bought at 0 using profit from cooldown!)
 *   nextSold = -1 + 0 = -1
 *   nextRest = max(1, 2) = 2
 *   -> hold = 1, sold = -1, rest = 2
 *
 * Day 4 (price = 2):
 *   nextHold = max(1, 2 - 2) = 1
 *   nextSold = hold(1) + 2 = 3
 *   nextRest = max(2, -1) = 2
 *   -> hold = 1, sold = 3, rest = 2
 *
 * Global Max Profit: max(sold, rest) = max(3, 2) = 3
 * (Buy day 0 at 1 -> Sell day 1 at 2 [profit 1] -> Cooldown day 2 -> Buy day 3 at 0 -> Sell day 4 at 2 [profit 2] -> Total = 3)
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n) - Single pass over price array.
 * - Space Complexity: O(1) - Three scalar state variables.
 */
public class P14_StockWithCooldown {

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int hold = -prices[0]; // Holding stock
        int sold = 0;          // Just sold today
        int rest = 0;          // In cooldown / idle

        for (int i = 1; i < prices.length; i++) {
            int prevHold = hold;
            int prevSold = sold;
            int prevRest = rest;

            // Update states
            hold = Math.max(prevHold, prevRest - prices[i]);
            sold = prevHold + prices[i];
            rest = Math.max(prevRest, prevSold);
        }

        // Optimal final state is either just sold or resting (never holding stock at the end)
        return Math.max(sold, rest);
    }

    public static void main(String[] args) {
        int[] prices1 = {1, 2, 3, 0, 2};
        System.out.println("P14 Stock Profit [1, 2, 3, 0, 2]: " + maxProfit(prices1)); // Expected: 3

        int[] prices2 = {1};
        System.out.println("P14 Stock Profit [1]:             " + maxProfit(prices2)); // Expected: 0
    }
}