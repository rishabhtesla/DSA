package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [15 / 24] - CANDY (LeetCode 135)
 * ============================================================================
 * 
 * PROBLEM:
 *   There are n children standing in a line with ratings[i]. Each child must receive
 *   at least 1 candy. Children with a higher rating than their immediate neighbors
 *   must get more candies than that neighbor. Return the minimum total candies needed.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Two separate directional dependencies:
 *     1. Condition L: If rating[i] > rating[i - 1], child i must have more candies than child i - 1.
 *     2. Condition R: If rating[i] > rating[i + 1], child i must have more candies than child i + 1.
 *   - Two-Pass Greedy Strategy:
 *     1. Initialize an array `candies` filled with 1.
 *     2. Left-to-Right pass: Satisfy Condition L.
 *        If ratings[i] > ratings[i - 1], set candies[i] = candies[i - 1] + 1.
 *     3. Right-to-Left pass: Satisfy Condition R without breaking Condition L.
 *        If ratings[i] > ratings[i + 1], set candies[i] = max(candies[i], candies[i + 1] + 1).
 *
 * COMPLEXITY:
 *   - Time:  O(n) - One pass left-to-right, one pass right-to-left.
 *   - Space: O(n) - Candies distribution array.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P15_Candy {

    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        // Pass 1: Enforce left neighbor constraint
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // Pass 2: Enforce right neighbor constraint
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        int totalCandies = 0;
        for (int count : candies) {
            totalCandies += count;
        }

        return totalCandies;
    }

    public static void main(String[] args) {
        int[] ratings = {1, 0, 2};
        System.out.println("P15 Output: " + candy(ratings));
        // Expected: 5 (candies: [2, 1, 2])
    }
}