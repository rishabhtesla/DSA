package DSA.DP;

import java.util.Arrays;

/**
 * ============================================================================
 * [26 / 30] - MATRIX CHAIN MULTIPLICATION (Classic Benchmark / GeeksforGeeks)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given a sequence of matrices, find the most efficient way to multiply them 
 * together. The problem is NOT actually to perform the multiplications, but merely 
 * to decide the order of parenthesizations that MINIMIZES the total number of 
 * scalar multiplications.
 *
 * Input:
 *   An array `arr[]` where matrix `i` has dimension arr[i - 1] x arr[i].
 *   For N matrices, `arr[]` has length N + 1.
 *
 * Example:
 *   arr = [10, 20, 30, 40, 30] represents 4 matrices:
 *   A1: 10x20,  A2: 20x30,  A3: 30x40,  A4: 40x30
 *
 *
 * 2. RECURRENCE & INTERVAL PARTITION LOGIC:
 * ----------------------------------------------------------------------------
 * State:
 *   dp[i][j] = minimum scalar multiplications needed to compute A[i]...A[j].
 *
 * Base Case:
 *   dp[i][i] = 0 (a single matrix requires 0 scalar multiplications).
 *
 * Partition Choice (Split at index k):
 *   To compute product A[i...j], we can place the final set of parentheses 
 *   between matrix `k` and matrix `k + 1` for any k from i to j - 1:
 *     (A[i]...A[k]) * (A[k+1]...A[j])
 *
 *   Cost of this parenthesization:
 *     cost = dp[i][k] + dp[k + 1][j] + (arr[i - 1] * arr[k] * arr[j])
 *
 * Recurrence:
 *   dp[i][j] = min { dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j] }
 *              for all i <= k < j
 *
 * Loop Ordering (Interval Length):
 *   We must compute smaller intervals first!
 *   Outer loop: length of chain `len` from 2 to N.
 *   Inner loop: start index `i` from 1 to N - len + 1, end index `j = i + len - 1`.
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * arr = [40, 20, 30, 10, 30] -> 4 matrices: A1(40x20), A2(20x30), A3(30x10), A4(10x30)
 *
 * len = 2 (chains of 2 matrices):
 *   dp[1][2] = A1 * A2 = 40 * 20 * 30 = 24,000
 *   dp[2][3] = A2 * A3 = 20 * 30 * 10 = 6,000
 *   dp[3][4] = A3 * A4 = 30 * 10 * 30 = 9,000
 *
 * len = 3 (chains of 3 matrices):
 *   dp[1][3]: min of:
 *     k=1: dp[1][1] + dp[2][3] + 40*20*10 = 0 + 6000 + 8000 = 14,000  (Winner!)
 *     k=2: dp[1][2] + dp[3][3] + 40*30*10 = 24000 + 0 + 12000 = 36,000
 *     -> dp[1][3] = 14,000
 *
 *   dp[2][4]: min of:
 *     k=2: dp[2][2] + dp[3][4] + 20*30*30 = 0 + 9000 + 18000 = 27,000
 *     k=3: dp[2][3] + dp[4][4] + 20*10*30 = 6000 + 0 + 6000 = 12,000  (Winner!)
 *     -> dp[2][4] = 12,000
 *
 * len = 4 (full chain A1...A4):
 *   dp[1][4]: min over k in {1, 2, 3}:
 *     k=1: dp[1][1] + dp[2][4] + 40*20*30 = 0 + 12000 + 24000 = 36,000
 *     k=2: dp[1][2] + dp[3][4] + 40*30*30 = 24000 + 9000 + 36000 = 69,000
 *     k=3: dp[1][3] + dp[4][4] + 40*10*30 = 14000 + 0 + 12000 = 26,000  (Winner!)
 *
 * Result: dp[1][4] = 26,000
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N^3) - Three nested loops (chain length, starting index, partition k).
 * - Space Complexity: O(N^2) - 2D DP table.
 */
public class P26_MatrixChainMultiplication {

    public static int matrixMultiplication(int[] arr) {
        int n = arr.length - 1; // Number of matrices
        int[][] dp = new int[n + 1][n + 1];

        // Base case: chains of length 1 have cost 0 (already 0 by default)

        // len is chain length (from 2 up to n matrices)
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;

                // Try every possible split point k
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + (arr[i - 1] * arr[k] * arr[j]);
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[1][n];
    }

    public static void main(String[] args) {
        int[] arr1 = {40, 20, 30, 10, 30};
        System.out.println("P26 Min Multiplications: " + matrixMultiplication(arr1)); // Expected: 26000

        int[] arr2 = {10, 20, 30, 40, 30};
        System.out.println("P26 Min Multiplications: " + matrixMultiplication(arr2)); // Expected: 30000
    }
}