package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given a square matrix 'mat', return the sum of the matrix diagonals [00:00:19, 00:00:24].
 * Only include the sum of the elements on the primary diagonal and all the elements on the 
 * secondary diagonal that are not part of the primary diagonal [00:00:38, 00:00:43].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: mat = [[1,2,3],
 *                   [4,5,6],
 *                   [7,8,9]] [00:01:00, 00:01:16]
 * - Primary diagonal: mat[0][0]=1, mat[1][1]=5, mat[2][2]=9 (Indices where i == j) [00:02:23].
 * - Secondary diagonal: mat[0][2]=3, mat[1][1]=5, mat[2][0]=7 (Indices where i + j == n - 1) [00:02:34, 00:03:17].
 * - Notice that mat[1][1]=5 is in both diagonals; it must only be counted ONCE [00:00:43, 00:01:00].
 * - Sum = 1 + 5 + 9 + 3 + 7 = 25 [00:01:10, 00:08:47].
 * - Result: 25
 * 
 * Example 2: mat = [[1,1,1,1],
 *                   [1,1,1,1],
 *                   [1,1,1,1],
 *                   [1,1,1,1]]
 * - Primary diagonal elements = 1 + 1 + 1 + 1 = 4.
 * - Secondary diagonal elements = 1 + 1 + 1 + 1 = 4.
 * - Since n = 4 (even size), no center element is shared.
 * - Sum = 4 + 4 = 8.
 * - Result: 8
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Full 2D Grid Traversal with Conditional Inclusion):
 * • Index Initialization: Outer loop 'i' runs 0 to n-1 (rows), inner loop 'j' runs 0 to n-1 (cols) [00:03:52, 00:04:22].
 * • Condition Boundaries:
 *   - Primary Diagonal match: `i == j` [00:02:23, 00:04:29].
 *   - Secondary Diagonal match: `i + j == n - 1` [00:03:17, 00:04:48].
 *   - Logical OR combined condition: `if (i == j || i + j == n - 1)` [00:04:29, 00:04:48].
 * • Operational Steps:
 *   1. Store matrix dimension `n = mat.length` [00:03:59].
 *   2. Iterate over all matrix elements using nested loops `i` and `j` [00:03:52, 00:04:22].
 *   3. Check if element `mat[i][j]` belongs to primary (`i == j`) or secondary (`i + j == n - 1`) diagonal [00:04:29, 00:04:48].
 *   4. If condition holds true, accumulate into `sum += mat[i][j]` [00:05:05].
 *   5. Return accumulated `sum` [00:05:12].
 * • Time Complexity: O(n^2) - Scans all n x n entries in the 2D grid matrix.
 * • Space Complexity: O(1) auxiliary space - Modifies scalar tracking variable directly.
 * • LOGIC BEHIND THIS APPROACH:
 *   Primary diagonal cells satisfy `i == j` [00:02:23]. 
 *   Secondary diagonal cells satisfy `i + j == n - 1` [00:03:17]. 
 *   Using `if (i == j || i + j == n - 1)` automatically includes elements on either diagonal while preventing double-counting 
 *   the central element where both conditions match simultaneously [00:04:29, 00:05:05].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Full Grid Traversal - mat = [[1,2,3],[4,5,6],[7,8,9]], n = 3, n-1 = 2):
 * Initial: sum = 0 [00:06:00]
 * i=0, j=0: i == j (0==0) -> match primary! sum += mat[0][0] (1) -> sum = 1 [00:06:15]
 * i=0, j=1: 0!=1 and 0+1!=2 -> skip [00:06:29]
 * i=0, j=2: 0+2 == 2 -> match secondary! sum += mat[0][2] (3) -> sum = 4 [00:06:47]
 * i=1, j=0: 1!=0 and 1+0!=2 -> skip [00:07:11]
 * i=1, j=1: i == j (1==1) -> match primary! sum += mat[1][1] (5) -> sum = 9 [00:07:27]
 * i=1, j=2: 1!=2 and 1+2!=2 -> skip [00:07:38]
 * i=2, j=0: 2+0 == 2 -> match secondary! sum += mat[2][0] (7) -> sum = 16 [00:08:16]
 * i=2, j=1: 2!=1 and 2+1!=2 -> skip [00:08:29]
 * i=2, j=2: i == j (2==2) -> match primary! sum += mat[2][2] (9) -> sum = 25 [00:08:39]
 * Loop End. Return sum = 25 [00:08:47].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Single Pass Pointer Iteration - mat = [[1,2,3],[4,5,6],[7,8,9]], n = 3):
 * Loop i from 0 to 2:
 * i=0: sum += mat[0][0] (1) + mat[0][3-1-0] (mat[0][2] = 3) -> sum = 4
 * i=1: sum += mat[1][1] (5) + mat[1][3-1-1] (mat[1][1] = 5) -> sum = 14 (added 5 twice)
 * i=2: sum += mat[2][2] (9) + mat[2][3-1-2] (mat[2][0] = 7) -> sum = 30
 * Post-Loop Check: n is odd (3 % 2 == 1) -> subtract center element mat[1][1] (5):
 * sum = 30 - 5 = 25.
 * Output = 25.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Pipeline - mat = [[1,2,3],[4,5,6],[7,8,9]], n = 3):
 * IntStream range 0..2:
 * i=0 -> mat[0][0] (1) + mat[0][2] (3) = 4
 * i=1 -> mat[1][1] (5) + (1 == 2 ? mat[1][1] : 0) = 5
 * i=2 -> mat[2][2] (9) + mat[2][0] (7) = 16
 * Sum components = 4 + 5 + 16 = 25.
 * Output = 25.
 */
public class MatrixDiagonalSum {

    // APPROACH 1: Full 2D Grid Traversal with Conditional Inclusion (Anchor Strategy)
    public static int diagonalSumOptimal(int[][] mat) {
        if (mat == null || mat.length == 0) return 0;

        int sum = 0; // Accumulator for diagonal elements [00:03:46]
        int n = mat.length; // Matrix dimension (n x n) [00:03:59]

        // Outer loop for rows [00:03:52]
        for (int i = 0; i < n; i++) {
            // Inner loop for columns [00:04:22]
            for (int j = 0; j < n; j++) {
                // Primary diagonal condition: i == j [00:04:29]
                // Secondary diagonal condition: i + j == n - 1 [00:04:48]
                if (i == j || i + j == n - 1) {
                    sum += mat[i][j]; // Accumulate element [00:05:05]
                }
            }
        }

        return sum; // Return calculated sum [00:05:12]
    }

    // APPROACH 2: Single Pass Linear Iteration (O(n) Optimal Speed Strategy)
    // Directly accesses primary `mat[i][i]` and secondary `mat[i][n - 1 - i]` elements in a single O(n) loop.
    // If n is odd, the middle element is added twice, so we subtract `mat[n/2][n/2]` once at the end.
    public static int diagonalSumLinear(int[][] mat) {
        if (mat == null || mat.length == 0) return 0;

        int sum = 0;
        int n = mat.length;

        for (int i = 0; i < n; i++) {
            sum += mat[i][i];            // Primary diagonal element
            sum += mat[i][n - 1 - i];    // Secondary diagonal element
        }

        // If size is odd, subtract central overlapping element once
        if (n % 2 == 1) {
            sum -= mat[n / 2][n / 2];
        }

        return sum;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Stream processing provides a functional one-liner pipeline 
    // but adds internal iterator boxing overhead compared to primitive loops.
    public static int diagonalSumStream(int[][] mat) {
        if (mat == null || mat.length == 0) return 0;

        int n = mat.length;

        return IntStream.range(0, n)
                .map(i -> mat[i][i] + (i != n - 1 - i ? mat[i][n - 1 - i] : 0))
                .sum();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (3x3 Odd Matrix with Central Overlap) ---
        int[][] test1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int res1_1 = diagonalSumOptimal(test1);
        int res1_2 = diagonalSumLinear(test1);
        int res1_3 = diagonalSumStream(test1);

        System.out.println("Test Case 1: 3x3 Grid [[1,2,3],[4,5,6],[7,8,9]]");
        System.out.println("Approach 1 (Full Traversal) Result: " + res1_1);
        System.out.println("Approach 2 (Linear O(n))   Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 25 && res1_2 == 25 && res1_3 == 25 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (4x4 Even Matrix without Central Overlap) ---
        int[][] test2 = {
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
        };
        int res2_1 = diagonalSumOptimal(test2);
        int res2_2 = diagonalSumLinear(test2);
        int res2_3 = diagonalSumStream(test2);

        System.out.println("Test Case 2: 4x4 Grid of 1s");
        System.out.println("Approach 1 (Full Traversal) Result: " + res2_1);
        System.out.println("Approach 2 (Linear O(n))   Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 8 && res2_2 == 8 && res2_3 == 8 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (1x1 Matrix Single Element) ---
        int[][] test3 = {{5}};
        int res3_1 = diagonalSumOptimal(test3);
        int res3_2 = diagonalSumLinear(test3);
        int res3_3 = diagonalSumStream(test3);

        System.out.println("Test Case 3: 1x1 Grid [[5]]");
        System.out.println("Approach 1 (Full Traversal) Result: " + res3_1);
        System.out.println("Approach 2 (Linear O(n))   Result: " + res3_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 == 5 && res3_2 == 5 && res3_3 == 5 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}