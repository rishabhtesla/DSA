package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find the median of two sorted arrays as if they were merged.
 * EXAMPLE: [1,3] and [2] -> 2.0.
 * CONSTRAINTS: Total length is 1..2000; arrays are individually sorted; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Copy and sort the combined values, then select the middle or average of the two middles.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n log n) time and O(n) auxiliary space.
 * DRY RUN: Main input [1,3] and [2] -> 2.0.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P09_MedianOfTwoSortedArrays {
    public static double findMedianSortedArrays(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        Arrays.sort(c);
        int n = c.length;
        return n % 2 == 1 ? c[n / 2] : (c[n / 2 - 1] + c[n / 2]) / 2.0;
    }

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
    }
}
