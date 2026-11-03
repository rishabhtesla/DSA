package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P57: TRANSPOSE MATRIX
 *
 * Question
 * Return the transpose of a rectangular matrix.
 *
 * Example
 * [[1,2,3],[4,5,6]] -> [[1,4],[2,5],[3,6]].
 *
 * Constraints
 * 1 <= rows,cols <= 100; matrix is rectangular.
 *
 * Approach used by edSlash
 * 1. Create a cols-by-rows result and assign result[j][i]=matrix[i][j].
 * 2. Track the current row, column, or boundary so every cell is visited in the required order.
 * 3. Update the boundary or position after each value is placed or read, avoiding repeated work.
 *
 * Dry run
 *   Transpose visual:
 *     row [1,2,3] becomes column [1,2,3]
 *     row [4,5,6] becomes column [4,5,6]
 *     result rows are [1,4], [2,5], [3,6]
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P57_TransposeMatrix {
    public static int[][] transpose(int[][] a) {
        int[][] r = new int[a[0].length][a.length];
        for (int i = 0; i < a.length; i++) for (int j = 0; j < a[0].length; j++) r[j][i] = a[i][j];
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(transpose(new int[][]{{1, 2, 3}, {4, 5, 6}})));
    }
}
