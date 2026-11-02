package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P98: MAXIMUM SUBARRAY
 *
 * Question
 * Find the contiguous subarray with maximum sum.
 *
 * Example
 * [-2,1,-3,4,-1,2,1,-5,4] -> 6 from [4,-1,2,1].
 *
 * Constraints
 * 1 <= n <= 10^5; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Kadane's recurrence keeps the best subarray ending at the current element.
 * 2. Keep only the previous states needed to calculate the next state instead of recomputing earlier answers.
 * 3. Update the state in the same order as the recurrence so each iteration represents one more input position.
 *
 * Dry run
 * Example from main: [-2,1,-3,4,-1,2,1,-5,4] -> 6 from [4,-1,2,1].
 * 1. Initialize the base state before processing the input.
 * 2. Calculate the next state from the previous state values.
 * 3. Shift the state variables and continue until the input is exhausted.
 * 4. Return the state that represents the requested answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P98_MaximumSubarray {
    public static int maxSubArray(int[] a) {
        int best = a[0], cur = 0;
        for (int x : a) {
            cur = Math.max(x, cur + x);
            best = Math.max(best, cur);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
