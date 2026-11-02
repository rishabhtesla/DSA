package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P14: PRODUCT OF ARRAY EXCEPT SELF
 *
 * Question
 * Return each position's product of all other values without division.
 *
 * Example
 * [1,2,3,4] -> [24,12,8,6].
 *
 * Constraints
 * 2 <= n <= 10^5; products fit 32-bit output; division is forbidden.
 *
 * Approach used by edSlash
 * 1. First store products left of each index, then multiply by a right-to-left suffix product.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [1,2,3,4] -> [24,12,8,6].
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P14_ProductOfArrayExceptSelf {
    public static int[] productExceptSelf(int[] a) {
        int[] r = new int[a.length];
        Arrays.fill(r, 1);
        int p = 1;
        for (int i = 0; i < a.length; i++) {
            r[i] = p;
            p *= a[i];
        }
        p = 1;
        for (int i = a.length - 1; i >= 0; i--) {
            r[i] *= p;
            p *= a[i];
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(productExceptSelf(new int[]{1, 2, 3, 4})));
    }
}
