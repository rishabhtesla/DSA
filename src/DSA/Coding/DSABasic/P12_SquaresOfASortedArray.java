package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return squares of a nondecreasing array in nondecreasing order.
 * EXAMPLE: [-4,-1,0,3,10] -> [0,1,9,16,100].
 * CONSTRAINTS: 1 <= n <= 10^4; input is sorted; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Compare absolute values at both ends and fill the output from right to left.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [-4,-1,0,3,10] -> [0,1,9,16,100].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
