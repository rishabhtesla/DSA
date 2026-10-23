package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return each position's product of all other values without division.
 * EXAMPLE: [1,2,3,4] -> [24,12,8,6].
 * CONSTRAINTS: 2 <= n <= 10^5; products fit 32-bit output; division is forbidden.
 * LOGIC / ALGORITHM:
 * 1. First store products left of each index, then multiply by a right-to-left suffix product.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,2,3,4] -> [24,12,8,6].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
