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
 *   Clockwise-rotation visual for [[1,2],[3,4]]:
 *     transpose -> [[1,3],[2,4]]
 *     reverse each row -> [[3,1],[4,2]]
 *     return the rotated matrix
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P60_RotateImage {
    public static void rotate(int[][] a) {
        int n = a.length;
        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++) {
                int target = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = target;
            }
        for (int[] r : a)
            for (int l = 0, h = n - 1; l < h; l++, h--) {
                int target = r[l];
                r[l] = r[h];
                r[h] = target;
            }
    }

    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        rotate(a);
        System.out.println(Arrays.deepToString(a));
    }
}
