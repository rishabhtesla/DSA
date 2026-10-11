package DSA.DP;

/**
 * ============================================================================
 * [11 / 30] - CLIMBING STAIRS (LeetCode 70 / HackerRank "Davis' Staircase" variant)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can climb either 1 or 2 steps.
 * In how many distinct ways can you climb to the top?
 *
 *
 * 2. RECURRENCE & DECISION LOGIC:
 * ----------------------------------------------------------------------------
 * To reach step `i`, you must arrive from either:
 *   1. Step `i - 1` (by taking a 1-step leap)
 *   2. Step `i - 2` (by taking a 2-step leap)
 *
 * State:
 *   dp[i] = total distinct ways to reach step i.
 *
 * Recurrence:
 *   dp[i] = dp[i - 1] + dp[i - 2]
 *
 * Base Cases:
 *   dp[1] = 1 (only {1})
 *   dp[2] = 2 (ways: {1, 1}, {2})
 *
 * Space Optimization:
 *   Since dp[i] only depends on the previous two values, we only need two variables:
 *   prev2 (represents i - 2) and prev1 (represents i - 1).
 *   Space drops from O(n) to O(1).
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * n = 5
 * Initial states: prev2 = 1 (step 1), prev1 = 2 (step 2)
 *
 * i = 3: current = prev1 + prev2 = 2 + 1 = 3 -> prev2 = 2, prev1 = 3
 * i = 4: current = prev1 + prev2 = 3 + 2 = 5 -> prev2 = 3, prev1 = 5
 * i = 5: current = prev1 + prev2 = 5 + 3 = 8 -> prev2 = 5, prev1 = 8
 *
 * Final Result: 8 distinct ways
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n) - Single linear pass.
 * - Space Complexity: O(1) - Constant auxiliary space.
 */
public class P11_ClimbingStairs {

    public static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int prev2 = 1; // ways to reach step 1
        int prev1 = 2; // ways to reach step 2

        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public static void main(String[] args) {
        int n1 = 3;
        System.out.println("P11 Ways to climb 3 stairs: " + climbStairs(n1)); // Expected: 3

        int n2 = 5;
        System.out.println("P11 Ways to climb 5 stairs: " + climbStairs(n2)); // Expected: 8
    }
}