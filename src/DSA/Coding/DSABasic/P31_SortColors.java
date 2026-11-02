package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P31: SORT COLORS
 *
 * Question
 * Sort an array containing only 0, 1, and 2 in-place.
 *
 * Example
 * [2,0,2,1,1,0] -> [0,0,1,1,2,2].
 *
 * Constraints
 * n <= 300; values are only 0,1,2.
 *
 * Approach used by edSlash
 * 1. Dutch National Flag pointers maintain processed 0s, unknowns, and 2s.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [2,0,2,1,1,0] -> [0,0,1,1,2,2].
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P31_SortColors {
    public static void sortColors(int[] a) {
        int l = 0, m = 0, r = a.length - 1;
        while (m <= r) {
            if (a[m] == 0) {
                int t = a[l];
                a[l++] = a[m];
                a[m++] = t;
            } else if (a[m] == 2) {
                int t = a[m];
                a[m] = a[r];
                a[r--] = t;
            } else m++;
        }
    }

    public static void main(String[] args) {
        int[] a = {2, 0, 2, 1, 1, 0};
        sortColors(a);
        System.out.println(Arrays.toString(a));
    }
}
