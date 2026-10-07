package DSA.Coding.DSABasic;

public class TribonacciMasterRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    The Tribonacci sequence Tn is defined as follows: 
    T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.
    Given n, return the value of Tn.

    APPROACH 1: Four-Variable Shifting Iteration (As shown in video)
    - Base Case Check: If `n == 0`, return 0. If `n == 1` or `n == 2`, return 1 [00:02:52].
    - Maintain state variables: `firstTerm = 0`, `secondTerm = 1`, `thirdTerm = 1`.
    - Run an inclusive loop from `1` up to `n` [00:03:50].
    - In each cycle, compute `fourthTerm = firstTerm + secondTerm + thirdTerm` [00:04:00].
    - Shift all state labels leftwards: `firstTerm` takes `secondTerm`, `secondTerm` takes 
      `thirdTerm`, and `thirdTerm` takes `fourthTerm` [00:04:21].
    - Upon loop exit, `firstTerm` correctly holds the calculated target item value.

    VISUAL DRY RUN (Four-Variable Shifting for n = 4):
    ----------------------------------------------------------------------------
    - Initial: firstTerm = 0, secondTerm = 1, thirdTerm = 1
    - i = 1:
        fourthTerm = 0 + 1 + 1 = 2                                   [00:06:17]
        firstTerm = 1, secondTerm = 1, thirdTerm = 2                 [00:06:41]
    - i = 2:
        fourthTerm = 1 + 1 + 2 = 4                                   [00:06:52]
        firstTerm = 1, secondTerm = 2, thirdTerm = 4                 [00:07:07]
    - i = 3:
        fourthTerm = 1 + 2 + 4 = 7                                   [00:07:14]
        firstTerm = 2, secondTerm = 4, thirdTerm = 7                 [00:07:28]
    - i = 4:
        fourthTerm = 2 + 4 + 7 = 13                                  [00:07:36]
        firstTerm = 4, secondTerm = 7, thirdTerm = 13                [00:07:44]
    - Loop Ends. Returns firstTerm = 4                               [00:07:52]

    TIME COMPLEXITY: O(N) - Single loop iterating linearly up to N operations.
    SPACE COMPLEXITY: O(1) - Constant tracking registers without auxiliary growth.
    ================================================================================
    */
    public static int tribonacciVideo(int n) {
        // Base edge case checking [00:02:52]
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;

        int firstTerm = 0;
        int secondTerm = 1;
        int thirdTerm = 1;

        // Slide state variables up to n [00:03:50]
        for (int i = 1; i <= n; i++) {
            int fourthTerm = firstTerm + secondTerm + thirdTerm;
            firstTerm = secondTerm;
            secondTerm = thirdTerm;
            thirdTerm = fourthTerm;
        }

        return firstTerm;
    }

    /*
    ================================================================================
    APPROACH 2: Standard Bottom-Up Tabulation (Dynamic Programming)
    - Directly builds an explicit table array representing T[i] from 3 to N.
    - More readable for mapping standard mathematical notations.
    - TIME COMPLEXITY: O(N)
    - SPACE COMPLEXITY: O(N) - Table storage of size n + 1.
    ================================================================================
    */
    public static int tribonacciTabulation(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }

        return dp[n];
    }

    /*
    ================================================================================
    APPROACH 3: Pure Recursion (Brute Force Baseline)
    - Evaluates structural branches via functional stack directly.
    - TIME COMPLEXITY: O(3^N) - Massive overhead, highly inefficient for larger N.
    - SPACE COMPLEXITY: O(N) - Maximum recursion stack call depth.
    ================================================================================
    */
    public static int tribonacciRecursive(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        
        return tribonacciRecursive(n - 1) + tribonacciRecursive(n - 2) + tribonacciRecursive(n - 3);
    }

    private static void verifyAllApproaches(int n, int expected) {
        System.out.println("Testing for n = " + n + " (Expected: " + expected + ")");
        System.out.println("1. Video Shifting Strategy: " + tribonacciVideo(n));
        System.out.println("2. DP Table Tabulation    : " + tribonacciTabulation(n));
        if (n <= 10) { // Bound evaluation check for exponential recursion limit
            System.out.println("3. Pure Stack Recursion   : " + tribonacciRecursive(n));
        } else {
            System.out.println("3. Pure Stack Recursion   : SKIPPED (Avoid call stack crash)");
        }
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 1137: N-TH TRIBONACCI NUMBER ===\n");

        verifyAllApproaches(0, 0);   // Base Edge Case
        verifyAllApproaches(2, 1);   // Boundary Case 
        verifyAllApproaches(4, 4);   // Target Case evaluated in video [00:08:17]
        verifyAllApproaches(7, 24);  // Mid-Range Target Check
    }
}