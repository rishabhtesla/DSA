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
 *   A store offers n items with prices given in `price[]`. You have `m` discount coupons.
 *   Applying 1 coupon to an item reduces its price from P to floor(P / 2).
 *   You can apply multiple coupons to the same item.
 *   Determine the minimum total money needed to purchase all n items.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Greedy Reduction:
 *     Each coupon applied to price P reduces total cost by `P - floor(P / 2) = ceil(P / 2)`.
 *     To maximize savings on every single coupon, greedily apply the coupon to the 
 *     CURRENT HIGHEST PRICE item in the array.
 *   - Max-Heap (PriorityQueue):
 *     1. Insert all prices into a Max-Heap.
 *     2. Repeat `m` times (or until top element is 0):
 *        Pop max price `P`, halve it `floor(P / 2)`, and push it back into the heap.
 *     3. Sum all remaining elements in the heap.
 *
 * COMPLEXITY:
 *   - Time:  O(n log n + m log n) - Heapify and m extraction/insertion cycles.
 *   - Space: O(n)                 - Max-Heap storage.
 */
public class AG_HR03_MinimumCostWithDiscountCoupons {

    public static long findMinimumPrice(int[] price, int m) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        long totalCost = 0;

        for (int p : price) {
            maxHeap.offer(p);
            totalCost += p;
        }

        // Apply coupons greedily to largest available price
        while (m > 0 && !maxHeap.isEmpty()) {
            int top = maxHeap.poll();
            if (top == 0) break; // Further coupons yield 0 discount

            int discounted = top / 2;
            totalCost -= (top - discounted); // Subtract the savings

            maxHeap.offer(discounted);
            m--;
        }

        return totalCost;
    }

    public static void main(String[] args) {
        int[] price1 = {2, 4};
        int m1 = 2;
        System.out.println("AG_HR03 Output (Test 1): " + findMinimumPrice(price1, m1)); 
        // Expected: 3 (4 -> 2 -> 1, total: 2 + 1 = 3)

        int[] price2 = {9, 7, 4, 1};
        int m2 = 3;
        System.out.println("AG_HR03 Output (Test 2): " + findMinimumPrice(price2, m2)); 
        // 9 -> 4 (save 5), 7 -> 3 (save 4), 4 -> 2 (save 2). Total: 4 + 3 + 4 + 1 = 12
    }
}