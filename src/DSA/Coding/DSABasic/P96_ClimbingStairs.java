package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P96: CLIMBING STAIRS
 *
 * Question
 * Count ways to reach step n using one- or two-step moves.
 *
 * Example
 * n=3 -> 3 ways: 1+1+1, 1+2, 2+1.
 *
 * Constraints
 * 1 <= n <= 45.
 *
 * Approach used by edSlash
 * 1. The ways recurrence is dp[n]=dp[n-1]+dp[n-2], stored in two variables.
 * 2. Keep only the previous states needed to calculate the next state instead of recomputing earlier answers.
 * 3. Update the state in the same order as the recurrence so each iteration represents one more input position.
 *
 * Dry run
 * Example from main: n=3 -> 3 ways: 1+1+1, 1+2, 2+1.
 * 1. Initialize the base state before processing the input.
 * 2. Calculate the next state from the previous state values.
 * 3. Shift the state variables and continue until the input is exhausted.
 * 4. Return the state that represents the requested answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P96_ClimbingStairs {
    public static int climbStairs(int n) {
        int a = 1, b = 1;
        for (int i = 0; i < n; i++) {
            int t = a + b;
            a = b;
            b = t;
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(3));
    }
}
