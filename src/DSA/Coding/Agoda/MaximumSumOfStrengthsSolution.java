package DSA.Coding.Agoda;

import java.util.Arrays;
import java.util.List;

/*
 * ============================================================================
 * QUESTION 1: Maximum Sum of Strengths
 * ============================================================================
 * Given an array of n integers, you can perform the following operation any 
 * number of times:
 *   1. Choose any index i (0 <= i < n - 1) and swap arr[i] and arr[i + 1].
 *   2. Each element can be swapped at most once during the process.
 * 
 * The strength of an index i is defined as arr[i] * (i + 1) using 0-based indexing.
 * Return the maximum possible total sum of strengths after optimal swaps:
 *        sum(arr[i] * (i + 1)) for i = 0 to n - 1
 * 
 * Constraints:
 *   - 1 <= n <= 10^5
 *   - 1 <= arr[i] <= 10^5
 * 
 * ============================================================================
 * APPROACH & LOGIC
 * ============================================================================
 * 1. Crucial Rule: "Each element can be swapped at most once."
 *    This implies you can only choose to swap DISJOINT adjacent pairs.
 *    For instance, if you swap (arr[0], arr[1]), you CANNOT swap (arr[1], arr[2]).
 * 
 * 2. Dynamic Programming Formulation:
 *    At each position i, you have two choices:
 *      - Choice A: Do not swap arr[i] with arr[i - 1].
 *        Contribution added: arr[i] * (i + 1)
 *        The previous element arr[i - 1] could either have been swapped with arr[i - 2]
 *        or kept unswapped.
 *      - Choice B: Swap arr[i] with arr[i - 1].
 *        This requires arr[i - 1] to NOT have been swapped with arr[i - 2].
 *        Swapping them exchanges their multipliers:
 *          arr[i - 1] gets multiplier (i + 1)
 *          arr[i]     gets multiplier i
 * 
 * 3. Space-Optimized DP:
 *    Maintain two values for the prefix ending at the current index:
 *      - prev0: Max sum where the current element is NOT swapped with its left neighbor.
 *      - prev1: Max sum where the current element IS swapped with its left neighbor.
 * 
 *    Time Complexity: O(n)
 *    Space Complexity: O(1)
 * 
 * ============================================================================
 * DRY RUN: arr = [2, 1, 4, 3], n = 4
 * ============================================================================
 * - Base at index 0:
 *     prev0 = 2 * 1 = 2
 *     prev1 = -infinity
 * 
 * - Step at index 1: (val = 1, prev_val = 2)
 *     next0 = max(2, -inf) + 1 * 2 = 4
 *     next1 = 1 * 1 + 2 * 2 = 5   (swap arr[0] and arr[1])
 *     State: prev0 = 4, prev1 = 5
 * 
 * - Step at index 2: (val = 4, prev_val = 1, mult_prev = 2, mult_cur = 3)
 *     next0 = max(4, 5) + 4 * 3 = 5 + 12 = 17
 *     next1 = prev0 - (1 * 2) + (4 * 2) + (1 * 3) = 4 - 2 + 8 + 3 = 13
 *     State: prev0 = 17, prev1 = 13
 * 
 * - Step at index 3: (val = 3, prev_val = 4, mult_prev = 3, mult_cur = 4)
 *     next0 = max(17, 13) + 3 * 4 = 17 + 12 = 29
 *     next1 = prev0 - (4 * 3) + (3 * 3) + (4 * 4) = 17 - 12 + 9 + 16 = 30
 *     State: prev0 = 29, prev1 = 30
 * 
 * Result = max(29, 30) = 30 (Swaps: [0, 1] and [2, 3] -> final arr = [1, 2, 3, 4])
 * ============================================================================
 */

public class MaximumSumOfStrengthsSolution {

    public static long getMaximumSumOfStrengths(List<Integer> arr) {
        int n = arr.size();
        if (n == 0) return 0L;
        if (n == 1) return (long) arr.get(0);

        // State tracking:
        // prev0: max sum of prefix where the current element is NOT swapped with its left neighbor
        // prev1: max sum of prefix where the current element IS swapped with its left neighbor
        long prev0 = (long) arr.get(0) * 1;
        long prev1 = Long.MIN_VALUE;

        // Base case for index 1
        long cur0 = Math.max(prev0, prev1) + (long) arr.get(1) * 2;
        long cur1 = (long) arr.get(1) * 1 + (long) arr.get(0) * 2;

        prev0 = cur0;
        prev1 = cur1;

        // Iterate through index 2 to n - 1
        for (int i = 2; i < n; i++) {
            long val_i = arr.get(i);
            long val_prev = arr.get(i - 1);
            long mult_i = i + 1;
            long mult_prev = i;

            // Choice 1: Don't swap arr[i] with arr[i - 1]
            long next0 = Math.max(prev0, prev1) + (val_i * mult_i);

            // Choice 2: Swap arr[i] with arr[i - 1] (arr[i - 1] must NOT have been swapped)
            long next1 = prev0 - (val_prev * mult_prev) + (val_i * mult_prev) + (val_prev * mult_i);

            prev0 = next0;
            prev1 = next1;
        }

        return Math.max(prev0, prev1);
    }

    public static void main(String[] args) {
        List<Integer> sample1 = Arrays.asList(2, 1, 4, 3);
        System.out.println("Output for [2, 1, 4, 3]: " + getMaximumSumOfStrengths(sample1)); // Expected: 30

        List<Integer> sample2 = Arrays.asList(1, 9, 7, 3, 2);
        System.out.println("Output for [1, 9, 7, 3, 2]: " + getMaximumSumOfStrengths(sample2)); // Expected: 65
    }
}