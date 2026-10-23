package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Add one to a nonnegative integer represented by digits.
 * EXAMPLE: [1,2,9] -> [1,3,0].
 * CONSTRAINTS: 1 <= digits.length <= 100; digits are 0..9 and no leading zero except zero.
 * LOGIC / ALGORITHM:
 * 1. Process from right to left; stop when a digit does not overflow, otherwise create a leading 1.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,2,9] -> [1,3,0].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P34_PlusOne {
    public static int[] plusOne(int[] a) {
        for (int i = a.length - 1; i >= 0; i--) if (a[i]++ < 9) return a;
        int[] r = new int[a.length + 1];
        r[0] = 1;
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(plusOne(new int[]{1, 2, 9})));
    }
}
