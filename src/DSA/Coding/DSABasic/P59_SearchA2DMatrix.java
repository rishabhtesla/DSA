package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Search a row-wise sorted matrix whose rows are globally ordered.
 * EXAMPLE: [[1,3,5],[7,9,11]], target=9 -> true.
 * CONSTRAINTS: m,n >= 1; each row sorted and first value greater than prior row's last.
 * LOGIC / ALGORITHM:
 * 1. Treat the matrix as one sorted array and map flat index to row and column.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [[1,3,5],[7,9,11]], target=9 -> true.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
