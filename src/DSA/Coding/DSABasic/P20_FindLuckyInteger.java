package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P20: FIND LUCKY INTEGER
 *
 * Question
 * Return the largest integer whose frequency equals its value.
 *
 * Example
 * [2,2,3,4] -> 2.
 *
 * Constraints
 * 1 <= n <= 500; values are 1..500.
 *
 * Approach used by edSlash
 * 1. Count frequencies, then inspect candidate values from 500 down to 1.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [2,2,3,4] -> 2.
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P20_FindLuckyInteger {
    public static int findLucky(int[] nums) {
        int[] f = new int[501];
        for (int x : nums) f[x]++;
        for (int i = 500; i > 0; i--) if (f[i] == i) return i;
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(findLucky(new int[]{2, 2, 3, 4}));
    }
}
