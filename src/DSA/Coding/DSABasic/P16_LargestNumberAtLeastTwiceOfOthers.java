package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P16: LARGEST NUMBER AT LEAST TWICE OF OTHERS
 *
 * Question
 * Return the index of the largest value if it is at least twice every other value.
 *
 * Example
 * [3,6,1,0] -> 1 because 6 >= 2*3.
 *
 * Constraints
 * 2 <= n <= 50; values are non-negative and fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Track the largest and runner-up values, then compare their ratio.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [3,6,1,0] -> 1 because 6 >= 2*3.
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P16_LargestNumberAtLeastTwiceOfOthers {
    public static int dominantIndex(int[] a) {
        int m = 0, s = -1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > a[m]) {
                s = m;
                m = i;
            } else if (s < 0 || a[i] > a[s]) s = i;
        }
        return s < 0 || a[m] >= 2 * a[s] ? m : -1;
    }

    public static void main(String[] args) {
        System.out.println(dominantIndex(new int[]{3, 6, 1, 0}));
    }
}
