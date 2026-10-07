package DSA.Coding.DSABasic;

public class PowerOfTwoRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given an integer `n`, return true if it is a power of two. Otherwise, return false.
    An integer `n` is a power of two if there exists an integer `x` such that n == 2^x.

    APPROACH 1: Iterative Division (As shown in video)
    - If `n <= 0`, it cannot be a power of two (False).
    - Repeatedly divide `n` by 2 as long as `n % 2 == 0`.
    - If after divisions, `n` becomes 1, it is a power of two.

    APPROACH 2: Bit Manipulation (Optimized)
    - Powers of two in binary always have exactly one '1' bit (e.g., 4 is 100, 8 is 1000).
    - If you subtract 1 from a power of two, all bits flip (e.g., 4-1 = 3, which is 011).
    - The bitwise AND of `n` and `n-1` will always be 0 if `n` is a power of two.
    - Logic: `(n > 0) && ((n & (n - 1)) == 0)`

    VISUAL DRY RUN (Iterative Approach):
    ----------------------------------------------------------------------------
    Example: n = 16
    - 16 % 2 == 0 -> n = 8
    - 8 % 2 == 0  -> n = 4
    - 4 % 2 == 0  -> n = 2
    - 2 % 2 == 0  -> n = 1
    - Loop ends. n == 1, returns True.

    TIME COMPLEXITY: 
    - Iterative: O(log N)
    - Bit Manipulation: O(1)
    SPACE COMPLEXITY: O(1)
    ================================================================================
    */

    // Approach 1: Iterative
    public static boolean isPowerOfTwoIterative(int n) {
        if (n <= 0) return false;
        while (n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }

    // Approach 2: Bitwise (Optimized)
    public static boolean isPowerOfTwoBitwise(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    private static void printResult(int n) {
        System.out.println("Input: " + n);
        System.out.println("Iterative Result: " + isPowerOfTwoIterative(n));
        System.out.println("Bitwise Result  : " + isPowerOfTwoBitwise(n));
        System.out.println("---------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 231: POWER OF TWO ===\n");
        
        printResult(16);   // True
        printResult(3);    // False
        printResult(1);    // True (2^0)
        printResult(0);    // False
        printResult(-16);  // False
    }
}