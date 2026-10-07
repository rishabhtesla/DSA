package DSA.Coding.DSABasic;

public class FibonacciNumberRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    The Fibonacci numbers, commonly denoted F(n) form a sequence, called the 
    Fibonacci sequence, such that each number is the sum of the two preceding ones, 
    starting from 0 and 1. That is:
    F(0) = 0, F(1) = 1
    F(n) = F(n - 1) + F(n - 2), for n > 1.
    Given n, calculate F(n).

    APPROACH 1: Variable Shifting Iteration (As shown in video)
    - Handle edge base cases: If `n == 0` return 0, if `n == 1` return 1 [00:03:12].
    - Maintain tracking variables: `firstTerm = 0` and `secondTerm = 1`.
    - Run a loop from `1` to `n`. In each loop item, compute `thirdTerm = firstTerm + secondTerm` [00:04:30].
    - Shift the pointers: `firstTerm` takes the value of `secondTerm`, and `secondTerm` takes the value of `thirdTerm` [00:04:39].
    - By managing the shifting this way, the `firstTerm` variable contains the target $n^{th}$ value upon termination.

    VISUAL DRY RUN (Iterative Approach):
    ----------------------------------------------------------------------------
    Example: n = 4 (Targeting 4th Fibonacci item) [00:05:42]
    - Initial: firstTerm = 0, secondTerm = 1
    - Loop i = 1: 
        thirdTerm = 0 + 1 = 1                                        [00:06:10]
        firstTerm = secondTerm -> 1                                  [00:06:17]
        secondTerm = thirdTerm -> 1                                  [00:06:27]
    - Loop i = 2: 
        thirdTerm = 1 + 1 = 2                                        [00:06:44]
        firstTerm = secondTerm -> 1                                  [00:06:50]
        secondTerm = thirdTerm -> 2                                  [00:06:55]
    - Loop i = 3: 
        thirdTerm = 1 + 2 = 3                                        [00:07:02]
        firstTerm = secondTerm -> 2                                  [00:07:07]
        secondTerm = thirdTerm -> 3                                  [00:07:08]
    - Loop i = 4: 
        thirdTerm = 2 + 3 = 5                                        [00:07:14]
        firstTerm = secondTerm -> 3                                  [00:07:21]
        secondTerm = thirdTerm -> 5                                  [00:07:28]
    - Terminate: Returns firstTerm = 3                               [00:07:38]

    TIME COMPLEXITY: O(N) - Loop runs linearly up to N operations.
    SPACE COMPLEXITY: O(1) - Handled purely with basic state variables without memory overhead.
    ================================================================================
    */

    // Approach 1: Variable Shifting Iteration (Video Implementation)
    public static int fib(int n) {
        // Base edge case checking [00:03:12]
        if (n == 0) return 0;
        if (n == 1) return 1;

        int firstTerm = 0;
        int secondTerm = 1;

        // Loop runs from 1 to n to slide value states [00:03:50]
        for (int i = 1; i <= n; i++) {
            int thirdTerm = firstTerm + secondTerm;
            firstTerm = secondTerm;
            secondTerm = thirdTerm;
        }

        return firstTerm;
    }

    /*
    ================================================================================
    APPROACH 2: Standard Space-Optimized Dynamic Programming Loop
    - Often written similarly, but standard DP counts forward relative to index position 
      (from 2 to n) where the final evaluation state naturally resides at the `b` pointer.
    ================================================================================
    */
    public static int fibStandardDP(int n) {
        if (n <= 1) return n;
        
        int a = 0; // Represents F(i-2)
        int b = 1; // Represents F(i-1)
        
        for (int i = 2; i <= n; i++) {
            int current = a + b;
            a = b;
            b = current;
        }
        return b;
    }

    private static void printTestResult(int n, int expected) {
        int res1 = fib(n);
        int res2 = fibStandardDP(n);
        System.out.println("Input 'n'      : " + n);
        System.out.println("Expected Output: " + expected);
        System.out.println("Video Approach : " + res1);
        System.out.println("DP Approach    : " + res2);
        System.out.println("Status         : " + (res1 == expected && res2 == expected ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 509: FIBONACCI NUMBER ===\n");

        // Case 1: Minimum boundary edge case
        printTestResult(0, 0);

        // Case 2: Standard basic state case
        printTestResult(1, 1);

        // Case 3: Target case simulated in dry run [00:07:45]
        printTestResult(4, 3);

        // Case 4: Higher range verification
        printTestResult(6, 8);
    }
}