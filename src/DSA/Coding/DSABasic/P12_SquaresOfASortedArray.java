package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P12: SQUARES OF A SORTED ARRAY
 *
 * Question
 * Return squares of a nondecreasing array in nondecreasing order.
 *
 * Example
 * [-4,-1,0,3,10] -> [0,1,9,16,100].
 *
 * Constraints
 * 1 <= n <= 10^4; input is sorted; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Compare absolute values at both ends and fill the output from right to left.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [-4,-1,0,3,10] -> [0,1,9,16,100].
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P12_SquaresOfASortedArray {
    public static int[] sortedSquares(int[] a) {
        int[] r = new int[a.length];
        int l = 0, h = a.length - 1, k = h;
        while (l <= h) {
            int x = Math.abs(a[l]), y = Math.abs(a[h]);
            r[k--] = x > y ? x * x : y * y;
            if (x > y) l++;
            else h--;
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sortedSquares(new int[]{-4, -1, 0, 3, 10})));
    }
}
