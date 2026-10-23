package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the index of the largest value if it is at least twice every other value.
 * EXAMPLE: [3,6,1,0] -> 1 because 6 >= 2*3.
 * CONSTRAINTS: 2 <= n <= 50; values are non-negative and fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Track the largest and runner-up values, then compare their ratio.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [3,6,1,0] -> 1 because 6 >= 2*3.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P16_LargestNumberAtLeastTwiceOfOthers {
    public static int dominantIndex(int[] a) {
        int m = 0, s = -1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > a[m]) {
                s = m;
                m = i;
            } else if (s < 0 || a[i] > a[s]) s = i;
        }
        return s < 0 || a[m] >= 2 * a[s] ? m : -1;
    }

    public static void main(String[] args) {
        System.out.println(dominantIndex(new int[]{3, 6, 1, 0}));
    }
}
