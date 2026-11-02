package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P59: SEARCH A2D MATRIX
 *
 * Question
 * Search a row-wise sorted matrix whose rows are globally ordered.
 *
 * Example
 * [[1,3,5],[7,9,11]], target=9 -> true.
 *
 * Constraints
 * m,n >= 1; each row sorted and first value greater than prior row's last.
 *
 * Approach used by edSlash
 * 1. Treat the matrix as one sorted array and map flat index to row and column.
 * 2. Track the current row, column, or boundary so every cell is visited in the required order.
 * 3. Update the boundary or position after each value is placed or read, avoiding repeated work.
 *
 * Dry run
 * Example from main: [[1,3,5],[7,9,11]], target=9 -> true.
 * 1. Start at the first valid row, column, or boundary.
 * 2. Read or write the current cell and move in the required direction.
 * 3. Shrink or update the boundary after completing a row, column, or rotation.
 * 4. Stop after every cell has been processed.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P59_SearchA2DMatrix {
    public static boolean searchMatrix(int[][] a, int t) {
        int m = a.length, n = a[0].length, l = 0, r = m * n - 1;
        while (l <= r) {
            int x = (l + r) / 2, v = a[x / n][x % n];
            if (v == t) return true;
            if (v < t) l = x + 1;
            else r = x - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(searchMatrix(new int[][]{{1, 3, 5}, {7, 9, 11}}, 9));
    }
}
