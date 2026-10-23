package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Add non-negative integer k to an integer represented by digit array.
 * EXAMPLE: [1,2,0,0]+34 -> [1,2,3,4].
 * CONSTRAINTS: 1 <= num.length <= 10^4; digits are 0..9; k >= 0.
 * LOGIC / ALGORITHM:
 * 1. Add from the least significant ends, carry through k, and prepend each resulting digit.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,2,0,0]+34 -> [1,2,3,4].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P07_AddToArrayFormOfInteger {
    public static List<Integer> addToArrayForm(int[] a, int k) {
        LinkedList<Integer> r = new LinkedList<>();
        int i = a.length - 1, carry = k;
        while (i >= 0 || carry > 0) {
            if (i >= 0) carry += a[i--];
            r.addFirst(carry % 10);
            carry /= 10;
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(addToArrayForm(new int[]{1, 2, 0, 0}, 34));
    }
}
