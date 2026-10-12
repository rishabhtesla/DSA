package DSA.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * [40 / 43] - SPIRAL MATRIX (LeetCode 54)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an `m x n` matrix, return all elements of the matrix in spiral order
 * (clockwise starting from the top-left corner).
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Visited-Set Approach:
 *    - Track visited cells using a boolean matrix of the same size and change direction when hitting bounds or visited cells.
 *    - Valid, but requires extra space and conditional branch checks.
 *
 * 2. Optimal 4-Boundary Inward Shrink Pattern (O(m * n) Time | O(1) Auxiliary Space):
 *    - **The Core Insight:** Instead of tracking visited cells, define explicit dynamic boundaries
 *      that shrink inward after every directional sweep: `top`, `bottom`, `left`, and `right`.
 *    - **Step 1:** Sweep `top` row from left to right, then increment `top++`.
 *    - **Step 2:** Sweep `right` column from top to bottom, then decrement `right--`.
 *    - **Step 3:** Sweep `bottom` row from right to left (guarded by `top <= bottom`), then decrement `bottom--`.
 *    - **Step 4:** Sweep `left` column from bottom to top (guarded by `left <= right`), then increment `left++`.
 *    - **Step 5:** Terminate when the boundaries cross (`top > bottom || left > right`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(m * n) - Every single cell in the matrix is visited exactly once.
 * - Space: O(1) - Auxiliary space (excluding the output list required to store the results).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Matrix:
 *   [1, 2, 3]
 *   [4, 5, 6]
 *   [7, 8, 9]
 * - Result: `[1, 2, 3, 6, 9, 8, 7, 4, 5]`
 *
 * VISUAL DRY RUN:
 *   - Initial boundaries: `top = 0, bottom = 2, left = 0, right = 2`.
 *   - Traverse top row: adds `[1, 2, 3]`, `top` becomes 1.
 *   - Traverse right column: adds `[6, 9]`, `right` becomes 1.
 *   - Traverse bottom row: adds `[8, 7]`, `bottom` becomes 1.
 *   - Traverse left column upward: adds `[4]`, `left` becomes 1.
 *   - Shrinks to center cell `[5]`, appends it, and loop terminates.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why are the inner checks (`top <= bottom` and `left <= right`) mandatory before Steps 3 and 4?
 *    (They prevent duplicate row or column processing when the matrix has an odd number of rows or columns
 *    and the boundaries collapse onto a single remaining line segment.)
 * 2. How does the boundary shrinking pattern handle rectangular matrices (e.g., 1xN or Mx1)?
 *    (The boundary guards and increments/decrements naturally terminate the loop as soon as any single dimension
 *    is fully traversed, without requiring special-case branching.)
 */
public class P40_SpiralMatrix {

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1. Traverse Right along the top boundary
            for (int c = left; c <= right; c++) {
                result.add(matrix[top][c]);
            }
            top++;

            // 2. Traverse Down along the right boundary
            for (int r = top; r <= bottom; r++) {
                result.add(matrix[r][right]);
            }
            right--;

            // 3. Traverse Left along the bottom boundary (guard against single remaining row)
            if (top <= bottom) {
                for (int c = right; c >= left; c--) {
                    result.add(matrix[bottom][c]);
                }
                bottom--;
            }

            // 4. Traverse Up along the left boundary (guard against single remaining column)
            if (left <= right) {
                for (int r = bottom; r >= top; r--) {
                    result.add(matrix[r][left]);
                }
                left++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println("P40 Output: " + spiralOrder(matrix));
        // Expected: [1, 2, 3, 6, 9, 8, 7, 4, 5]
    }
}