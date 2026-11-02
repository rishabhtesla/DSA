package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P33: MAXIMUM COUNT POSITIVE AND NEGATIVE
 *
 * Question
 * Return the larger count of positive and negative values, ignoring zero.
 *
 * Example
 * [-2,-1,-1,1,2,3] -> 3.
 *
 * Constraints
 * 1 <= n <= 2000; values are sorted and fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Count signs and return their maximum.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [-2,-1,-1,1,2,3] -> 3.
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P33_MaximumCountPositiveAndNegative {
    public static int maximumCount(int[] a) {
        int p = 0, n = 0;
        for (int x : a) {
            if (x > 0) p++;
            if (x < 0) n++;
        }
        return Math.max(p, n);
    }

    public static void main(String[] args) {
        System.out.println(maximumCount(new int[]{-2, -1, -1, 1, 2, 3}));
    }
}
