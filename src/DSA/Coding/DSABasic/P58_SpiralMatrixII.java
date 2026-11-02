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
 * Example from main: n=3 -> [[1,2,3],[8,9,4],[7,6,5]].
 * 1. Start at the first valid row, column, or boundary.
 * 2. Read or write the current cell and move in the required direction.
 * 3. Shrink or update the boundary after completing a row, column, or rotation.
 * 4. Stop after every cell has been processed.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P58_SpiralMatrixII {
    public static int[][] generateMatrix(int n) {
        int[][] a = new int[n][n];
        int t = 0, b = n - 1, l = 0, r = n - 1, v = 1;
        while (t <= b) {
            for (int j = l; j <= r; j++) a[t][j] = v++;
            t++;
            for (int i = t; i <= b; i++) a[i][r] = v++;
            r--;
            if (t <= b) {
                for (int j = r; j >= l; j--) a[b][j] = v++;
                b--;
            }
            if (l <= r) {
                for (int i = b; i >= t; i--) a[i][l] = v++;
                l++;
            }
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(generateMatrix(3)));
    }
}
