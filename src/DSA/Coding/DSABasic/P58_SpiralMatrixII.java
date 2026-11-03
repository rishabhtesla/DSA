package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P58: SPIRAL MATRIX II
 *
 * Question
 * Generate an n by n matrix filled 1..n² in spiral order.
 *
 * Example
 * n=3 -> [[1,2,3],[8,9,4],[7,6,5]].
 *
 * Constraints
 * 1 <= n <= 20.
 *
 * Approach used by edSlash
 * 1. Maintain top,bottom,left,right boundaries and fill each side before shrinking it.
 * 2. Track the current row, column, or boundary so every cell is visited in the required order.
 * 3. Update the boundary or position after each value is placed or read, avoiding repeated work.
 *
 * Dry run
 *   Spiral-fill visual for n = 3:
 *     top row:      1 2 3
 *     right column:       4
 *     bottom row:   7 6 5
 *     left column and center: 8 9
 *     result: [[1,2,3],[8,9,4],[7,6,5]]
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P58_SpiralMatrixII {
    public static int[][] generateMatrix(int n) {
        int[][] a = new int[n][n];
        int target = 0, b = n - 1, l = 0, r = n - 1, v = 1;
        while (target <= b) {
            for (int j = l; j <= r; j++) a[target][j] = v++;
            target++;
            for (int i = target; i <= b; i++) a[i][r] = v++;
            r--;
            if (target <= b) {
                for (int j = r; j >= l; j--) a[b][j] = v++;
                b--;
            }
            if (l <= r) {
                for (int i = b; i >= target; i--) a[i][l] = v++;
                l++;
            }
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(generateMatrix(3)));
    }
}
