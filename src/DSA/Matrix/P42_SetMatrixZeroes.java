package DSA.Matrix;

import java.util.Arrays;

/**
 * ============================================================================
 * [42 / 43] - SET MATRIX ZEROES (LeetCode 73)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an `m x n` integer matrix, if an element is `0`, set its entire row and
 * column to `0`s **in-place**. Solve with **O(1) auxiliary space**.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Marker Arrays Approach:
 *    - Allocate separate `boolean[] rowZero` and `boolean[] colZero` arrays.
 *    - Valid, but requires `O(m + n)` auxiliary space, violating the strict O(1) space requirement.
 *
 * 2. Optimal In-Place First Row & Column Markers Approach (O(m * n) Time | O(1) Space):
 *    - **The Core Insight:** We can use the matrix's **very first row and first column** themselves
 *      as our marker storage arrays, eliminating extra memory allocations entirely.
 *    - Because `matrix[0][0]` overlaps both the first row and first column flags, we introduce an
 *      isolated boolean variable (`firstColHasZero`) to track column 0, and let `matrix[0][0]` track row 0.
 *    - **Step 1:** Determine if column 0 contains any zeros (`firstColHasZero`).
 *    - **Step 2:** Scan the rest of the matrix (from `r = 0`, `c = 1`). If `matrix[r][c] == 0`,
 *      mark `matrix[r][0] = 0` and `matrix[0][c] = 0`.
 *    - **Step 3:** Iterate bottom-up/inward (from `r = 1`, `c = 1`). If `matrix[r][0] == 0` or `matrix[0][c] == 0`,
 *      set `matrix[r][c] = 0`.
 *    - **Step 4:** Zero out row 0 if `matrix[0][0] == 0`.
 *    - **Step 5:** Zero out column 0 if `firstColHasZero == true`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(m * n) - Two complete linear passes across the matrix cells.
 * - Space: O(1) - Constant auxiliary space using a single boolean tracking flag.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Matrix:
 *   [1, 1, 1]
 *   [1, 0, 1]
 *   [1, 1, 1]
 * - Result:
 *   [1, 0, 1]
 *   [0, 0, 0]
 *   [1, 0, 1]
 *
 * VISUAL DRY RUN:
 *   - The zero at `(1, 1)` flags row `matrix[1][0] = 0` and column `matrix[0][1] = 0`.
 *   - Inner cell pass zeros out row 1 and column 1.
 *   - First row/column final handlers process flags to produce the correct transformed matrix.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we need the separate `firstColHasZero` boolean flag?
 *    (Because `matrix[0][0]` is shared as a flag for *both* row 0 and column 0, we cannot use it
 *    unambiguously for both without losing state. `firstColHasZero` protects column 0's flag state.)
 * 2. Why must we update the inner cells from top-left to bottom-right, but handle row 0 and col 0 last?
 *    (Updating inner cells first relies on the flags in row 0 and col 0. If we modified the marker headers first,
 *    we would corrupt the flags needed for the rest of the matrix.)
 */
public class P42_SetMatrixZeroes {

    public static void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean firstColHasZero = false;

        // Step 1: Scan matrix and use first row/col as marker flags
        for (int r = 0; r < m; r++) {
            if (matrix[r][0] == 0) {
                firstColHasZero = true;
            }

            for (int c = 1; c < n; c++) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }

        // Step 2: Fill inner cells (from r=1, c=1) using the row and column flags
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) {
                    matrix[r][c] = 0;
                }
            }
        }

        // Step 3: Handle the first row based on matrix[0][0]
        if (matrix[0][0] == 0) {
            for (int c = 0; c < n; c++) {
                matrix[0][c] = 0;
            }
        }

        // Step 4: Handle the first column based on firstColHasZero flag
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