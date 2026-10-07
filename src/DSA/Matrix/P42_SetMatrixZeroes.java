package DSA.Matrix;

import java.util.Arrays;

/**
 * ============================================================================
 * [42 / 43] - SET MATRIX ZEROES (LeetCode 73)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an m x n integer matrix, if an element is 0, set its entire row and
 *   column to 0s in-place. Solve with O(1) auxiliary space.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Naive marker arrays: `boolean[] rowZero`, `boolean[] colZero` -> O(m + n) space.
 *   - O(1) Memory Trick (In-place First Row & Column Markers):
 *     Use the 0th row and 0th column of the matrix itself as our marker arrays!
 *     Because `matrix[0][0]` overlaps both the first row and column, use an
 *     isolated boolean `firstColHasZero` for column 0, and let `matrix[0][0]` track row 0.
 *   - Steps:
 *     1. Determine if col 0 has any zeros (`firstColHasZero`).
 *     2. Scan the rest of the matrix (from r=0, c=1). If `matrix[r][c] == 0`,
 *        mark `matrix[r][0] = 0` and `matrix[0][c] = 0`.
 *     3. Iterate bottom-up/inward (from r=1, c=1):
 *        If `matrix[r][0] == 0` or `matrix[0][c] == 0`, set `matrix[r][c] = 0`.
 *     4. Zero out row 0 if `matrix[0][0] == 0`.
 *     5. Zero out col 0 if `firstColHasZero == true`.
 *
 * COMPLEXITY:
 *   - Time:  O(m * n) - Two complete matrix sweeps.
 *   - Space: O(1) - Constant tracking variables.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P42_SetMatrixZeroes {

    public static void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean firstColHasZero = false;

        for (int r = 0; r < m; r++) {
            // Check if column 0 needs to be zeroed out
            if (matrix[r][0] == 0) {
                firstColHasZero = true;
            }

            // Use first row and column as flags for the rest of the cells
            for (int c = 1; c < n; c++) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }

        // Fill inner cells using row 0 and col 0 flags
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) {
                    matrix[r][c] = 0;
                }
            }
        }

        // Handle first row
        if (matrix[0][0] == 0) {
            for (int c = 0; c < n; c++) {
                matrix[0][c] = 0;
            }
        }

        // Handle first column
        if (firstColHasZero) {
            for (int r = 0; r < m; r++) {
                matrix[r][0] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        };
        setZeroes(matrix);
        System.out.println("P42 Output: " + Arrays.deepToString(matrix));
        // Expected: [[1, 0, 1], [0, 0, 0], [1, 0, 1]]
    }
}