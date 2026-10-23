package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the largest integer whose frequency equals its value.
 * EXAMPLE: [2,2,3,4] -> 2.
 * CONSTRAINTS: 1 <= n <= 500; values are 1..500.
 * LOGIC / ALGORITHM:
 * 1. Count frequencies, then inspect candidate values from 500 down to 1.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [2,2,3,4] -> 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P20_FindLuckyInteger {
    public static int findLucky(int[] a) {
        int[] f = new int[501];
        for (int x : a) f[x]++;
        for (int i = 500; i > 0; i--) if (f[i] == i) return i;
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(findLucky(new int[]{2, 2, 3, 4}));
    }
}
