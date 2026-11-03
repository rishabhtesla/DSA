package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P09: MEDIAN OF TWO SORTED ARRAYS
 *
 * Question
 * Find the median of two sorted arrays as if they were merged.
 *
 * Example
 * [1,3] and [2] -> 2.0.
 *
 * Constraints
 * Total length is 1..2000; arrays are individually sorted; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Copy and sort the combined values, then select the middle or average of the two middles.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [1,3] and [2] -> 2.0.
 *   Visual transformation:
 *
 *   firstArray       = [1, 3]
 *   secondArray      = [2]
 *   copied combined  = [1, 3, 2]
 *   sorted combined  = [1, 2, 3]
 *   length           = 3
 *   middle index     = 3 / 2 = 1
 *   median           = combined[1] = 2.0
 *
 * Complexity
 * O(n log n) time and O(n) auxiliary space.
 */
public class P09_MedianOfTwoSortedArrays {
    public static double findMedianSortedArrays(int[] firstArray, int[] secondArray) {
        int[] c = new int[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, c, 0, firstArray.length);
        System.arraycopy(secondArray, 0, c, firstArray.length, secondArray.length);
        Arrays.sort(c);
        int n = c.length;
        return n % 2 == 1 ? c[n / 2] : (c[n / 2 - 1] + c[n / 2]) / 2.0;
    }

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
    }
}
