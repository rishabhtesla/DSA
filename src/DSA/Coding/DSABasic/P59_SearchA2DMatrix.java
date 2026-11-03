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
 *   Matrix-search visual for target 9:
 *     start at matrix[0][0] = 1 and move through the sorted row/column order
 *     values 1,3,5,7 are smaller than 9, so continue
 *     reach 9 -> return true
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P59_SearchA2DMatrix {
    public static boolean searchMatrix(int[][] a, int target) {
        int m = a.length, n = a[0].length, l = 0, r = m * n - 1;
        while (l <= r) {
            int x = (l + r) / 2, v = a[x / n][x % n];
            if (v == target) return true;
            if (v < target) l = x + 1;
            else r = x - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(searchMatrix(new int[][]{{1, 3, 5}, {7, 9, 11}}, 9));
    }
}
