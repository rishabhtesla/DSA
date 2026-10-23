package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the larger count of positive and negative values, ignoring zero.
 * EXAMPLE: [-2,-1,-1,1,2,3] -> 3.
 * CONSTRAINTS: 1 <= n <= 2000; values are sorted and fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Count signs and return their maximum.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [-2,-1,-1,1,2,3] -> 3.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P33_MaximumCountPositiveAndNegative {
    public static int maximumCount(int[] a) {
        int p = 0, n = 0;
        for (int x : a) {
            if (x > 0) p++;
            if (x < 0) n++;
        }
        return Math.max(p, n);
    }

    public static void main(String[] args) {
        System.out.println(maximumCount(new int[]{-2, -1, -1, 1, 2, 3}));
    }
}
