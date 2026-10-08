package DSA.Matrix;

import java.util.Arrays;

/**
 * ============================================================================
 * [41 / 43] - ROTATE IMAGE (LeetCode 48)
 * ============================================================================
 * 
 * PROBLEM:
 *   You are given an n x n 2D matrix representing an image. Rotate the image
 *   by 90 degrees clockwise in-place (do NOT allocate another 2D matrix).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Linear algebra decomposition of a 90° clockwise rotation:
 *     Rotation by 90° clockwise = Transpose + Horizontal Reflection.
 *     1. Transpose the matrix: Swap `matrix[i][j]` with `matrix[j][i]` across main diagonal.
 *     2. Reverse each row: Swap `matrix[i][left]` with `matrix[i][right]`.
 *   - This avoids complex index-arithmetic 4-way corner rotations and is clean to write.
 *
 * COMPLEXITY:
 *   - Time:  O(n^2) - Transpose takes n^2 / 2 swaps; row reversal takes n^2 / 2 swaps.
 *   - Space: O(1) - Fully in-place swaps.
 *
 *
 * EXAMPLE:
 *   The first main matrix is [[1,2,3],[4,5,6],[7,8,9]]; expected rotation is
 *   [[7,4,1],[8,5,2],[9,6,3]].
 *
 * VISUAL DRY RUN:
 *   Transpose swaps (0,1):2<->4, (0,2):3<->7, (1,2):6<->8 -> [[1,4,7],[2,5,8],[3,6,9]].
 *   Reverse each row -> [7,4,1], [8,5,2], [9,6,3].
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P41_RotateImage {

    public static void rotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose matrix (swap rows with columns)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row horizontally
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