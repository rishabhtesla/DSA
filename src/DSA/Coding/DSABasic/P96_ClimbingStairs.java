package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Count ways to reach step n using one- or two-step moves.
 * EXAMPLE: n=3 -> 3 ways: 1+1+1, 1+2, 2+1.
 * CONSTRAINTS: 1 <= n <= 45.
 * LOGIC / ALGORITHM:
 * 1. The ways recurrence is dp[n]=dp[n-1]+dp[n-2], stored in two variables.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input n=3 -> 3 ways: 1+1+1, 1+2, 2+1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
