package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [15 / 24] - CANDY (LeetCode 135)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * There are `n` children standing in a line with given ratings in an array `ratings`.
 * You are giving candies to these children subjected to the following requirements:
 * 1. Each child must have at least one candy.
 * 2. Children with a higher rating than their immediate neighbors get more candies than those neighbors.
 *
 * Return the minimum number of candies you need to distribute to have all the children fulfill these requirements.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. The Core Conflict:
 *    - Each child's candy count depends on *both* their left neighbor and their right neighbor simultaneously.
 *    - Trying to satisfy both directions in a single forward pass is impossible because changing a child's
 *      candy count can invalidate the constraint for their neighbor.
 *
 * 2. Two-Pass Greedy Strategy (Decoupled Constraints):
 *    - Instead of solving both at once, we break it into two independent directional passes:
 *      - **Left-to-Right Pass:** Enforces the left neighbor constraint. If `ratings[i] > ratings[i - 1]`,
 *        make sure child `i` has more than child `i - 1`.
 *      - **Right-to-Left Pass:** Enforces the right neighbor constraint. If `ratings[i] > ratings[i + 1]`,
 *        make sure child `i` has more than child `i + 1`.
 *
 * 3. The "Aha!" Math Insight:
 *    - During the right-to-left pass, we use `Math.max(candies[i], candies[i + 1] + 1)`.
 *    - This ensures we **never overwrite or violate** the progress made by the left-to-right pass, while still
 *      satisfying the right-hand constraint.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Exactly two linear passes over the array plus one final pass to sum up.
 * - Space: O(n) - Extra `candies` array of size `n` to store each child's allocation.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE: ratings = [1, 0, 2] -> Minimum candies = 5
 *
 * VISUAL DRY RUN:
 *   Initial Array: `ratings = [1, 0, 2]`
 *   Initial Candies (all given 1): `[1, 1, 1]`
 *
 *   Step 1: Left-to-Right Pass (i from 1 to 2)
 *   - i = 1: ratings[1] (0) is NOT > ratings[0] (1) -> `candies[1]` remains 1.
 *   - i = 2: ratings[2] (2) > ratings[1] (0) -> `candies[2] = candies[1] + 1 = 1 + 1 = 2`.
 *   - State after Pass 1: `[1, 1, 2]`
 *
 *   Step 2: Right-to-Left Pass (i from n-2 down to 0)
 *   - i = 1: ratings[1] (0) is NOT > ratings[2] (2) -> `candies[1]` remains 1.
 *   - i = 0: ratings[0] (1) > ratings[1] (0) ->
 *     `candies[0] = Math.max(candies[0], candies[1] + 1) = Math.max(1, 1 + 1) = 2`.
 *   - Final State after Pass 2: `[2, 1, 2]`
 *
 *   Summing up: `2 + 1 + 2 = 5`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we need `Math.max()` in the second pass instead of a simple assignment?
 *    (Because assigning directly would erase the valid candy count established by the left pass,
 *    potentially violating the left neighbor constraint.)
 * 2. What happens if all ratings are identical (e.g., [2, 2, 2])?
 *    (Both loops find no elements greater than their neighbors, leaving the array as [1, 1, 1],
 *    which correctly assigns 1 candy to each child.)
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