package DSA.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * [40 / 43] - SPIRAL MATRIX (LeetCode 54)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an m x n matrix, return all elements of the matrix in spiral order
 *   (clockwise starting from the top-left corner).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - 4-Boundary Inward Shrink Pattern:
 *     Define boundaries: `top = 0`, `bottom = m - 1`, `left = 0`, `right = n - 1`.
 *     Execute 4 directional sweeps per cycle:
 *     1. Move Right: (top, left -> right), then `top++`.
 *     2. Move Down:  (top -> bottom, right), then `right--`.
 *     3. Move Left:  (bottom, right -> left), then `bottom--`.
 *        *Check `top <= bottom` before sweeping to avoid duplicate row processing.*
 *     4. Move Up:    (bottom -> top, left), then `left++`.
 *        *Check `left <= right` before sweeping to avoid duplicate column processing.*
 *     Loop terminates when boundaries cross (`top > bottom || left > right`).
 *
 * COMPLEXITY:
 *   - Time:  O(m * n) - Each cell is visited exactly once.
 *   - Space: O(1) - Auxiliary space (excluding the output list).
 *
 *
 * EXAMPLE:
 *   The first main matrix is [[1,2,3],[4,5,6],[7,8,9]], expecting [1,2,3,6,9,8,7,4,5].
 *
 * VISUAL DRY RUN:
 *   Boundaries top=0,bottom=2,left=0,right=2: traverse top [1,2,3], right [6,9],
 *   bottom [8,7], left upward [4]. Narrow to the center and append [5]; output order is
 *   [1,2,3,6,9,8,7,4,5].
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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
            // 1. Traverse Right
            for (int c = left; c <= right; c++) {
                result.add(matrix[top][c]);
            }
            top++;

            // 2. Traverse Down
            for (int r = top; r <= bottom; r++) {
                result.add(matrix[r][right]);
            }
            right--;

            // 3. Traverse Left (guard against single remaining row)
            if (top <= bottom) {
                for (int c = right; c >= left; c--) {
                    result.add(matrix[bottom][c]);
                }
                bottom--;
            }

            // 4. Traverse Up (guard against single remaining column)
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