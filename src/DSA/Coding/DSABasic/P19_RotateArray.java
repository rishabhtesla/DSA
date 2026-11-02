package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P19: ROTATE ARRAY
 *
 * Question
 * Rotate an array right by k positions in-place.
 *
 * Example
 * [1,2,3,4,5,6,7], k=3 -> [5,6,7,1,2,3,4].
 *
 * Constraints
 * 1 <= n <= 10^5; k >= 0.
 *
 * Approach used by edSlash
 * 1. Reverse all elements, reverse the first k, then reverse the remainder.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [1,2,3,4,5,6,7], k=3 -> [5,6,7,1,2,3,4].
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P19_RotateArray {
    public static void rotate(int[] a, int k) {
        k %= a.length;
        rev(a, 0, a.length - 1);
        rev(a, 0, k - 1);
        rev(a, k, a.length - 1);
    }

    static void rev(int[] a, int l, int r) {
        while (l < r) {
            int t = a[l];
            a[l++] = a[r];
            a[r--] = t;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        rotate(a, 3);
        System.out.println(Arrays.toString(a));
    }
}
