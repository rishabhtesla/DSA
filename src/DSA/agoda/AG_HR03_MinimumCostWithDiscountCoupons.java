package DSA.agoda;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * ============================================================================
 * [AGODA HACKERRANK - 03] MINIMUM COST WITH DISCOUNT COUPONS
 * ============================================================================
 *
 * SOURCE:
 *   Agoda HackerRank OA (Reported on LeetCode & DesiQnA).
 *
 * PROBLEM:
 *   A store offers `n` items with initial prices given in the integer array `price[]`.
 *   You are given `m` discount coupons.
 *
 *   Rules:
 *     1. Applying 1 coupon to an item with current price `P` reduces its price to
 *        `floor(P / 2)` (integer division `P / 2`).
 *     2. Multiple coupons can be stacked/applied sequentially to the same item
 *        (e.g., `9 -> 4 -> 2 -> 1 -> 0`).
 *     3. Coupons do not all have to be used if all prices hit `0`.
 *
 *   Determine and return the minimum total money (`long`) required to purchase all
 *   `n` items after optimally applying up to `m` coupons.
 *
 * CONSTRAINTS:
 *   - 1 <= n <= 10^5
 *   - 0 <= m <= 10^5
 *   - 0 <= price[i] <= 10^9
 *   - Total sum can exceed `Integer.MAX_VALUE`, so return `long`.
 *
 * EXAMPLES:
 *   Example 1:
 *     Input:  price = [2, 4], m = 2
 *     Output: 3
 *     Explanation:
 *       - Coupon 1 on 4 -> 2 (prices: [2, 2], saved 2)
 *       - Coupon 2 on 2 -> 1 (prices: [2, 1], saved 1)
 *       Total cost = 2 + 1 = 3.
 *
 *   Example 2:
 *     Input:  price = [9, 7, 4, 1], m = 3
 *     Output: 12
 *     Explanation:
 *       Initial sum = 21.
 *       - Coupon 1 on 9 -> 4 (saved 9 - 4 = 5)
 *       - Coupon 2 on 7 -> 3 (saved 7 - 3 = 4)
 *       - Coupon 3 on 4 -> 2 (saved 4 - 2 = 2)
 *       Final prices = [4, 3, 2, 1] -> Total cost = 21 - (5 + 4 + 2) = 10?
 *       WAIT — Look closely at the arithmetic!
 *       4 + 3 + 2 + 1 = 10 (NOT 12! 21 - 11 = 10!).
 *
 *   Example 3:
 *     Input:  price = [1, 1], m = 10
 *     Output: 0
 *     Explanation: Coupon 1 reduces 1 -> 0; Coupon 2 reduces 1 -> 0. Remaining 8
 *                  coupons yield 0 savings, so we break early!
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - Monotonic Diminishing Returns (Why Greedy Always Wins):
 *       * Applying a coupon to price `P` yields a discount of:
 *           `discount(P) = P - floor(P / 2) = ceil(P / 2)`.
 *       * Because `ceil(P / 2)` is monotonically non-decreasing with `P` (a larger
 *         price ALWAYS gives a greater-than-or-equal discount than a smaller price),
 *         and halving an item only *reduces* its future marginal return, there is
 *         never any benefit to "saving" a coupon for a smaller item.
 *       * Therefore, the globally optimal strategy is to greedily spend each coupon
 *         one by one on the **single most expensive item currently available**.
 *
 *   - Why a Max-Heap (`PriorityQueue` with `reverseOrder()`)?
 *       * We repeatedly need two operations `m` times:
 *           1. Extract the current maximum price (`poll()` -> `O(log n)`).
 *           2. Re-insert its halved price (`offer(top / 2)` -> `O(log n)`).
 *       * Maintaining a running `totalCost` and subtracting `(top - discounted)` on
 *         each step avoids a second pass over the heap at the end, and allows an
 *         instant `break` the moment `top == 0`.
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Guard Edge Cases:
 *      - If `price == null || price.length == 0`, return `0L`.
 *
 *   2. Initialize Max-Heap & Running Total (`totalCost`):
 *      - Create `PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder())`.
 *      - Iterate over every `p` in `price`:
 *          * Add `p` to `totalCost` (`long` accumulator).
 *          * If `p > 0`, insert `p` into `maxHeap` (items already at `0` can never
 *            be discounted further).
 *
 *   3. Greedy Coupon Application Loop (`while (m > 0 && !maxHeap.isEmpty())`):
 *      - Pop the largest current item price: `int top = maxHeap.poll()`.
 *      - Early Exit Optimization: If `top == 0`, every remaining item in the heap
 *        is also `0`, so no future coupon can save anything -> `break` immediately!
 *      - Compute new price after 1 coupon: `int discounted = top / 2`.
 *      - Compute exact money saved by this coupon: `int savings = top - discounted`.
 *      - Deduct `savings` from `totalCost` and decrement `m--`.
 *      - Push `discounted` back into `maxHeap` (if `discounted > 0`) because
 *        another coupon could still be applied to this same item later!
 *
 *   4. Return `totalCost`.
 *
 * ============================================================================
 * VISUAL DRY RUN: price = [9, 7, 4, 1], m = 3
 * ============================================================================
 *   Initialization:
 *     maxHeap (Max -> Min): [ 9, 7, 4, 1 ]
 *     totalCost = 9 + 7 + 4 + 1 = 21,  m = 3
 *
 *   Step | m Left | Polled Max (`top`) | Halved (`top/2`) | Savings | `totalCost` | Heap After Offer
 *   -----+--------+--------------------+------------------+---------+-------------+------------------
 *     1  |   3    |         9          |        4         |  9-4=5  | 21 - 5 = 16 | [ 7, 4, 4, 1 ]
 *     2  |   2    |         7          |        3         |  7-3=4  | 16 - 4 = 12 | [ 4, 4, 3, 1 ]
 *     3  |   1    |         4          |        2         |  4-2=2  | 12 - 2 = 10 | [ 4, 3, 2, 1 ]
 *    END |   0    | Loop terminates (`m == 0`)                       | Result = 10
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O((n + m) log n) - Inserting `n` items takes `O(n log n)`, and up to `m`
 *                               coupon steps each do one `poll()` + one `offer()` in `O(log n)`.
 *                               (Note: Since each coupon halves an item, at most `n * 30`
 *                               coupons can ever be used before all items hit 0!)
 *   - Space: O(n)             - Max-Heap stores at most `n` integers.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (Scanning the whole `price` array linearly `m` times to find the max -> `O(m * n)` time,
 *       which TLEs when `n = 10^5` and `m = 10^5`.)
 *   2. What invariant must remain true after every loop iteration?
 *      (`totalCost` always equals the exact sum of all elements currently inside `maxHeap`
 *       plus any items that already reached `0`.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (Re-offering `discounted` back into the heap is mandatory because stacking multiple
 *       coupons on a huge item like `[100, 1]` with `m = 2` (`100 -> 50 -> 25`) beats
 *       spreading coupons across smaller items.)
 *   4. Which edge cases change the control flow (`m == 0`, `m >> n * 30` where all prices hit `0`
 *      early, `price` containing `0`s, single item `n == 1`, large `10^9` prices requiring `long`)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_HR03_MinimumCostWithDiscountCoupons {

    public static long findMinimumPrice(int[] price, int m) {
        if (price == null || price.length == 0) {
            return 0L;
        }

        // Max-Heap to always access the current highest-priced item in O(log n)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        long totalCost = 0L;

        // Step 1: Load all positive prices into the heap and compute initial total cost
        for (int p : price) {
            totalCost += p;
            if (p > 0) {
                maxHeap.offer(p);
            }
        }

        // Step 2: Greedily apply each coupon to the current most expensive item
        while (m > 0 && !maxHeap.isEmpty()) {
            int highestPrice = maxHeap.poll();

            // Halve the price using integer floor division
            int discountedPrice = highestPrice / 2;

            // Deduct the exact savings achieved by this coupon
            int savings = highestPrice - discountedPrice;
            totalCost -= savings;
            m--;

            // Push back if still > 0 so future coupons can be stacked on this same item
            if (discountedPrice > 0) {
                maxHeap.offer(discountedPrice);
            }
        }

        return totalCost;
    }

    public static void main(String[] args) {
        int[] price1 = {2, 4};
        int m1 = 2;
        System.out.println("Test 1 ([2, 4], m=2):       " + findMinimumPrice(price1, m1) + " | Expected: 3");

        int[] price2 = {9, 7, 4, 1};
        int m2 = 3;
        System.out.println("Test 2 ([9, 7, 4, 1], m=3): " + findMinimumPrice(price2, m2) + " | Expected: 10");

        int[] price3 = {100, 1};
        int m3 = 2;
        System.out.println("Test 3 ([100, 1], m=2):     " + findMinimumPrice(price3, m3) + " | Expected: 26");
    }
}