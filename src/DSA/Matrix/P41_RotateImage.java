package DSA.Matrix;

import java.util.Arrays;

/**
 * ============================================================================
 * [41 / 43] - ROTATE IMAGE (LeetCode 48)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * You are given an `n x n` 2D matrix representing an image. Rotate the image
 * by 90 degrees clockwise **in-place** (do NOT allocate another 2D matrix).
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Extra Space Approach:
 *    - Allocate a new `n x n` matrix and map indices using `matrix_new[j][n - 1 - i] = matrix[i][j]`.
 *    - Valid, but violates the strict O(1) auxiliary space constraint.
 *
 * 2. Optimal Linear Algebra Decomposition Approach (O(n^2) Time | O(1) Space):
 *    - **The Core Insight:** A 90-degree clockwise rotation of a matrix is mathematically equivalent to
 *      performing a **Transpose** followed by a **Horizontal Reflection (Row Reversal)**.
 *    - **Step 1 (Transpose):** Swap `matrix[i][j]` with `matrix[j][i]` across the main diagonal (only for `j > i`).
 *    - **Step 2 (Horizontal Reflection):** Reverse each individual row by swapping elements from left to right.
 *    - This completely avoids complex four-way index-arithmetic corner rotations while remaining clean and bug-free.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n^2) - Transposing takes $n(n-1)/2$ swaps, and row reversal takes $n^2/2$ swaps, resulting in quadratic time.
 * - Space: O(1) - All swaps are performed completely in-place with zero extra memory allocations.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Matrix:
 *   [1, 2, 3]
 *   [4, 5, 6]
 *   [7, 8, 9]
 * - Expected Rotation:
 *   [7, 4, 1]
 *   [8, 5, 2]
 *   [9, 6, 3]
 *
 * VISUAL DRY RUN:
 *   - Initial: Transpose swaps elements across the main diagonal (e.g., `2` and `4` swap, `3` and `7` swap).
 *     Matrix becomes: `[[1, 4, 7], [2, 5, 8], [3, 6, 9]]`.
 *   - Row Reversal: Reverses each row horizontally (`[1, 4, 7]` becomes `[7, 4, 1]`, etc.).
 *   - Final Rotated Result: `[[7, 4, 1], [8, 5, 2], [9, 6, 3]]`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does `j` start at `i + 1` during the transpose step?
 *    (Starting at `i + 1` ensures we only swap elements across the main diagonal once.
 *    If `j` started at `0`, we would swap elements twice, effectively undoing the transpose.)
 * 2. Why is this matrix transformation preferred over direct 4-way corner element rotation?
 *    (It simplifies the pointer logic into two standard, well-understood operations (transpose + reflection),
 *    greatly reducing the risk of off-by-one errors or index out-of-bounds bugs under interview pressure.)
 */
public class P41_RotateImage {

    public static void rotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose the matrix (swap rows with columns across the main diagonal)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row horizontally to complete the 90° clockwise rotation
        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        rotate(matrix);
        System.out.println("P41 Output: " + Arrays.deepToString(matrix));
        // Expected: [[7, 4, 1], [8, 5, 2], [9, 6, 3]]
    }
}