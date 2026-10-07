package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given a 2D integer array 'matrix', return the transpose of matrix [00:00:23].
 * The transpose of a matrix is the matrix flipped over its main diagonal, switching 
 * the matrix's row and column indices [00:00:30, 00:00:40].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: matrix = [[1, 2, 3],
 *                      [4, 5, 6],
 *                      [7, 8, 9]] [00:00:50, 00:01:40]
 * - Square matrix (3x3): rows become columns and columns become rows [00:00:43].
 * - Row 0 [1, 2, 3] -> Column 0 [1, 2, 3]
 * - Row 1 [4, 5, 6] -> Column 1 [4, 5, 6]
 * - Row 2 [7, 8, 9] -> Column 2 [7, 8, 9]
 * - Result: [[1, 4, 7],
 *            [2, 5, 8],
 *            [3, 6, 9]] [00:02:15, 00:03:56]
 * 
 * Example 2: matrix = [[1, 2],
 *                      [3, 4],
 *                      [5, 6],
 *                      [7, 8]] (Simulated inside the video explanation [00:05:43, 00:11:39])
 * - Rectangular matrix (4 rows, 2 cols) [00:05:49]:
 *   - Dimensions flip: transposed matrix will have 2 rows and 4 cols (`ans[cols][rows]`) [00:05:58, 00:08:50].
 *   - Mapping: `ans[j][i] = matrix[i][j]` [00:07:55, 00:09:29].
 *   - Row 0 [1, 2] -> Column 0 [1, 2]
 *   - Row 1 [3, 4] -> Column 1 [3, 4]
 *   - Row 2 [5, 6] -> Column 2 [5, 6]
 *   - Row 3 [7, 8] -> Column 3 [7, 8]
 * - Result: [[1, 3, 5, 7],
 *            [2, 4, 6, 8]] [00:06:23, 00:11:39]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Direct Index-Inverted Transpose Matrix Allocation):
 * • Index Initialization:
 *   - Extract row count `row = matrix.length` and column count `col = matrix[0].length` [00:08:29, 00:08:38].
 *   - Outer loop 'i' scans original rows from 0 to `row - 1` [00:08:59].
 *   - Inner loop 'j' scans original columns from 0 to `col - 1` [00:09:11].
 * • Condition Boundaries:
 *   - Matrix dimension transformation: `ans = new int[col][row]` [00:08:50].
 *   - Index mapping assignment: `ans[j][i] = matrix[i][j]` [00:07:55, 00:09:29].
 * • Operational Steps:
 *   1. Measure input dimensions: `row = matrix.length`, `col = matrix[0].length` [00:08:29, 00:08:38].
 *   2. Instantiate transpose matrix `ans` with swapped dimensions `[col][row]` [00:08:50].
 *   3. Iterate over input matrix using nested loops `i` and `j` [00:08:59, 00:09:11].
 *   4. Assign transposed coordinates: `ans[j][i] = matrix[i][j]` [00:09:29].
 *   5. Return new transposed matrix `ans` [00:09:42].
 * • Time Complexity: O(R * C) - Visits each element of the input matrix exactly once.
 * • Space Complexity: O(R * C) - Space allocated for the output transpose matrix result.
 * • LOGIC BEHIND THIS APPROACH:
 *   Transposing flips matrix entries along the primary diagonal [00:00:30, 00:00:50]. 
 *   An element at position `(i, j)` in the original matrix moves to position `(j, i)` in the transpose [00:04:16, 00:07:55]. 
 *   For non-square (R != C) matrices, output dimensions must swap from `R x C` to `C x R` to avoid index out of bounds exceptions [00:05:58, 00:08:50].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Direct Index-Inverted Transpose - matrix = [[1, 2], [3, 4]], R=2, C=2):
 * Input matrix (2x2):
 * (0,0)=1, (0,1)=2
 * (1,0)=3, (1,1)=4
 * Output ans matrix (2x2):
 * i=0, j=0: ans[0][0] = matrix[0][0] (1) -> ans = [[1, _], [_, _]] [00:10:41]
 * i=0, j=1: ans[1][0] = matrix[0][1] (2) -> ans = [[1, _], [2, _]] [00:10:59]
 * i=1, j=0: ans[0][1] = matrix[1][0] (3) -> ans = [[1, 3], [2, _]] [00:11:16]
 * i=1, j=1: ans[1][1] = matrix[1][1] (4) -> ans = [[1, 3], [2, 4]] [00:11:33]
 * Result = [[1, 3], [2, 4]] [00:11:39].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Square In-Place Dynamic Allocation - matrix = [[1,2,3],[4,5,6],[7,8,9]]):
 * Detect square matrix (R == C == 3):
 * - Swap elements above main diagonal (i < j):
 *   i=0, j=1: swap matrix[0][1] (2) and matrix[1][0] (4)
 *   i=0, j=2: swap matrix[0][2] (3) and matrix[2][0] (7)
 *   i=1, j=2: swap matrix[1][2] (6) and matrix[2][1] (8)
 * Result = [[1, 4, 7], [2, 5, 8], [3, 6, 9]].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream IntStream Pipeline - matrix = [[1, 2], [3, 4], [5, 6]]):
 * Row count = 3, Col count = 2
 * Stream IntStream range(0, col = 2):
 * j=0 -> map to IntStream range(0, row = 3) -> [matrix[0][0], matrix[1][0], matrix[2][0]] -> [1, 3, 5]
 * j=1 -> map to IntStream range(0, row = 3) -> [matrix[0][1], matrix[1][1], matrix[2][1]] -> [2, 4, 6]
 * Result = [[1, 3, 5], [2, 4, 6]].
 */
public class TransposeMatrix {

    // APPROACH 1: Direct Index-Inverted Transpose Matrix Allocation (Anchor Strategy)
    public static int[][] transposeOptimal(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return new int[0][0];

        int row = matrix.length;       // Number of rows in original matrix [00:08:29]
        int col = matrix[0].length;    // Number of columns in original matrix [00:08:38]

        // Output matrix has inverted dimensions (col x row) [00:08:50]
        int[][] ans = new int[col][row];

        // Iterate through rows of original matrix [00:08:59]
        for (int i = 0; i < row; i++) {
            // Iterate through columns of original matrix [00:09:11]
            for (int j = 0; j < col; j++) {
                // Invert row and column indices for transpose [00:07:55, 00:09:29]
                ans[j][i] = matrix[i][j];
            }
        }

        return ans; // Return transposed matrix [00:09:42]
    }

    // APPROACH 2: Square In-Place Dynamic Strategy (Handles Rectangular & Square Matrices)
    // For square matrices (R == C), transposes in-place without extra space by swapping `matrix[i][j]` 
    // and `matrix[j][i]`. For rectangular matrices, falls back to new array allocation.
    public static int[][] transposeInPlaceOrNew(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return new int[0][0];

        int row = matrix.length;
        int col = matrix[0].length;

        // If matrix is square, perform in-place swapping above the diagonal
        if (row == col) {
            for (int i = 0; i < row; i++) {
                for (int j = i + 1; j < col; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
            return matrix;
        }

        // Rectangular matrix fallback
        int[][] ans = new int[col][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[j][i] = matrix[i][j];
            }
        }
        return ans;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Uses IntStream range mapping to project columns to rows functionally. 
    // IntStream boxing introduces allocation cost, but demonstrates functional matrix operations.
    public static int[][] transposeStream(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return new int[0][0];

        int row = matrix.length;
        int col = matrix[0].length;

        return IntStream.range(0, col)
                .mapToObj(j -> IntStream.range(0, row)
                        .map(i -> matrix[i][j])
                        .toArray())
                .toArray(int[][]::new);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Square 3x3 Matrix) ---
        int[][] test1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int[][] res1_1 = transposeOptimal(test1);
        int[][] res1_2 = transposeInPlaceOrNew(cloneMatrix(test1));
        int[][] res1_3 = transposeStream(test1);

        System.out.println("Test Case 1: Square 3x3 Grid");
        System.out.println("Approach 1 (Direct Index) Result: " + Arrays.deepToString(res1_1));
        System.out.println("Approach 2 (In-Place Swap) Result: " + Arrays.deepToString(res1_2));
        System.out.println("Approach 3 (Stream API)   Result: " + Arrays.deepToString(res1_3));
        boolean check1 = Arrays.deepEquals(res1_1, res1_2) && Arrays.deepEquals(res1_2, res1_3);
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Rectangular 4x2 Matrix from Video Explanation) ---
        int[][] test2 = {
            {1, 2},
            {3, 4},
            {5, 6},
            {7, 8}
        };
        int[][] res2_1 = transposeOptimal(test2);
        int[][] res2_2 = transposeInPlaceOrNew(cloneMatrix(test2));
        int[][] res2_3 = transposeStream(test2);

        System.out.println("Test Case 2: Rectangular 4x2 Grid");
        System.out.println("Approach 1 (Direct Index) Result: " + Arrays.deepToString(res2_1));
        System.out.println("Approach 2 (In-Place/New) Result: " + Arrays.deepToString(res2_2));
        System.out.println("Approach 3 (Stream API)   Result: " + Arrays.deepToString(res2_3));
        boolean check2 = Arrays.deepEquals(res2_1, res2_2) && Arrays.deepEquals(res2_2, res2_3);
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }

    // Helper method to deep clone 2D arrays for testing
    private static int[][] cloneMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }
}