package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Sum both diagonals of a square matrix without double-counting the center.
 * EXAMPLE: [[1,2,3],[4,5,6],[7,8,9]] -> 25.
 * CONSTRAINTS: 1 <= n <= 100; matrix is n by n.
 * LOGIC / ALGORITHM:
 * 1. Add a[i][i] and a[i][n-1-i], then subtract the center once for odd n.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [[1,2,3],[4,5,6],[7,8,9]] -> 25.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
