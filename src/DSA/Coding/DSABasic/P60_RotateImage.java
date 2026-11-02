package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P60: ROTATE IMAGE
 *
 * Question
 * Rotate an n by n matrix 90 degrees clockwise in-place.
 *
 * Example
 * [[1,2],[3,4]] -> [[3,1],[4,2]].
 *
 * Constraints
 * 1 <= n <= 20; matrix is square.
 *
 * Approach used by edSlash
 * 1. Transpose across the diagonal, then reverse every row.
 * 2. Track the current row, column, or boundary so every cell is visited in the required order.
 * 3. Update the boundary or position after each value is placed or read, avoiding repeated work.
 *
 * Dry run
 * Example from main: [[1,2],[3,4]] -> [[3,1],[4,2]].
 * 1. Start at the first valid row, column, or boundary.
 * 2. Read or write the current cell and move in the required direction.
 * 3. Shrink or update the boundary after completing a row, column, or rotation.
 * 4. Stop after every cell has been processed.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P60_RotateImage {
    public static void rotate(int[][] a) {
        int n = a.length;
        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++) {
                int t = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = t;
            }
        for (int[] r : a)
            for (int l = 0, h = n - 1; l < h; l++, h--) {
                int t = r[l];
                r[l] = r[h];
                r[h] = t;
            }
    }

    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        rotate(a);
        System.out.println(Arrays.deepToString(a));
    }
}
