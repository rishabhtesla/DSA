package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P56: MATRIX DIAGONAL SUM
 *
 * Question
 * Sum both diagonals of a square matrix without double-counting the center.
 *
 * Example
 * [[1,2,3],[4,5,6],[7,8,9]] -> 25.
 *
 * Constraints
 * 1 <= n <= 100; matrix is n by n.
 *
 * Approach used by edSlash
 * 1. Add a[i][i] and a[i][n-1-i], then subtract the center once for odd n.
 * 2. Track the current row, column, or boundary so every cell is visited in the required order.
 * 3. Update the boundary or position after each value is placed or read, avoiding repeated work.
 *
 * Dry run
 *   Diagonal visual for the 3x3 matrix:
 *     main diagonal: 1 + 5 + 9 = 15
 *     secondary diagonal: 3 + 5 + 7 = 15
 *     subtract the center 5 once because it was counted twice
 *     total = 15 + 15 - 5 = 25
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P56_MatrixDiagonalSum {
    public static int diagonalSum(int[][] a) {
        int n = a.length, s = 0;
        for (int i = 0; i < n; i++) s += a[i][i] + a[i][n - 1 - i];
        if (n % 2 == 1) s -= a[n / 2][n / 2];
        return s;
    }

    public static void main(String[] args) {
        System.out.println(diagonalSum(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
    }
}
