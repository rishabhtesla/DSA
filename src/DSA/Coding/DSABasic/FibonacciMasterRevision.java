package DSA.Coding.DSABasic;

import java.util.Arrays;

public class FibonacciMasterRevision {

    /*
    ================================================================================
    APPROACH 3: Pure Recursion (Brute Force)
    - Directly mirrors the recurrence relation: F(n) = F(n-1) + F(n-2).
    - It builds a deep binary recursion tree.
    - TIME COMPLEXITY: O(2^N) - Exponential growth as it recalculates subproblems repeatedly.
    - SPACE COMPLEXITY: O(N) - Maximum depth of the recursive call stack.
    
    VISUAL RECURSION TREE (n = 4):
                         F(4)
                       /      \
                    F(3)       F(2)
                   /   \       /   \
                 F(2)  F(1)  F(1)  F(0)
                 /  \
               F(1) F(0)
    ================================================================================
    */
    public static int fibRecursive(int n) {
        // Base Cases
        if (n == 0) return 0;
        if (n == 1) return 1;
        
        // Recursive Leap
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    /*
    ================================================================================
    APPROACH 4: Top-Down Dynamic Programming (Memoization)
    - Optimizes pure recursion by tracking subproblem values in a cache array (`memo`).
    - Before solving `F(n)`, it checks the cache. If it was already calculated, 
      it returns it instantly in O(1) time, cutting off redundant tree branches.
    - TIME COMPLEXITY: O(N) - Each state from 0 to N is calculated exactly once.
    - SPACE COMPLEXITY: O(N) - For the tracking array storage + recursive stack space.
    ================================================================================
    */
    public static int fibMemoization(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1); // Initialize cache elements as empty (-1)
        return memoizeWorker(n, memo);
    }

    private static int memoizeWorker(int n, int[] memo) {
        if (n <= 1) return n;
        
        // Return value immediately if it exists in the cache
        if (memo[n] != -1) return memo[n];
        
        // Save the computation result into cache before returning
        memo[n] = memoizeWorker(n - 1, memo) + memoizeWorker(n - 2, memo);
        return memo[n];
    }

    /*
    ================================================================================
    APPROACH 5: Matrix Exponentiation (Highly Optimized)
    - We use the mathematical matrix property:
      | F(n+1)  F(n)   |   =   | 1  1 |^n
      | F(n)    F(n-1) |       | 1  0 |
    - By utilizing Binary Exponentiation (Divide & Conquer), we can raise the transformation 
      matrix to the power of `n` in logarithmic steps rather than calculating linearly.
    - TIME COMPLEXITY: O(log N) - The binary shifting loop halves `n` at every step.
    - SPACE COMPLEXITY: O(1) - Uses a static 2x2 matrix footprint.
    ================================================================================
    */
    public static int fibMatrixExponentiation(int n) {
        if (n <= 1) return n;
        
        int[][] transformationMatrix = {{1, 1}, {1, 0}};
        int[][] resultMatrix = {{1, 0}, {0, 1}}; // Identity Matrix acting as 1
        
        int power = n - 1;
        while (power > 0) {
            // If power target index is odd, multiply directly into our result tracking unit
            if ((power & 1) == 1) {
                resultMatrix = multiplyMatrices(resultMatrix, transformationMatrix);
            }
            // Square the core transformation base matrix and right-shift power index values
            transformationMatrix = multiplyMatrices(transformationMatrix, transformationMatrix);
            power >>= 1;
        }
        
        // The top-left corner cell yields the value of F(n)
        return resultMatrix[0][0];
    }

    // Helper tool to handle 2x2 matrix multiplications
    private static int[][] multiplyMatrices(int[][] A, int[][] B) {
        int[][] C = new int[2][2];
        C[0][0] = A[0][0] * B[0][0] + A[0][1] * B[1][0];
        C[0][1] = A[0][0] * B[0][1] + A[0][1] * B[1][1];
        C[1][0] = A[1][0] * B[0][0] + A[1][1] * B[1][0];
        C[1][1] = A[1][0] * B[0][1] + A[1][1] * B[1][1];
        return C;
    }

    /*
    ================================================================================
    APPROACH 1 & 2 (From previous turns for structure completeness)
    ================================================================================
    */
    public static int fibIterativeVideo(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int firstTerm = 0, secondTerm = 1;
        for (int i = 1; i <= n; i++) {
            int thirdTerm = firstTerm + secondTerm;
            firstTerm = secondTerm;
            secondTerm = thirdTerm;
        }
        return firstTerm;
    }

    public static int fibIterativeStandardDP(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int current = a + b;
            a = b;
            b = current;
        }
        return b;
    }

    private static void verifyAllApproaches(int n, int expected) {
        System.out.println("Testing for n = " + n + " (Expected: " + expected + ")");
        System.out.println("1. Video Iterative     : " + fibIterativeVideo(n));
        System.out.println("2. Standard DP Loop    : " + fibIterativeStandardDP(n));
        System.out.println("3. Pure Recursive      : " + fibRecursive(n));
        System.out.println("4. Memoized Top-Down   : " + fibMemoization(n));
        System.out.println("5. Matrix Exponent     : " + fibMatrixExponentiation(n));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== COMPREHENSIVE FIBONACCI ALGORITHM BENCHMARK ===\n");
        
        verifyAllApproaches(0, 0);   // Base Edge Case
        verifyAllApproaches(1, 1);   // Base Case
        verifyAllApproaches(4, 3);   // Standard Low Target Range
        verifyAllApproaches(9, 34);  // Mid-Range Target Check
    }
}