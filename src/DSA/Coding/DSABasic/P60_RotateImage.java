package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Rotate an n by n matrix 90 degrees clockwise in-place.
 * EXAMPLE: [[1,2],[3,4]] -> [[3,1],[4,2]].
 * CONSTRAINTS: 1 <= n <= 20; matrix is square.
 * LOGIC / ALGORITHM:
 * 1. Transpose across the diagonal, then reverse every row.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [[1,2],[3,4]] -> [[3,1],[4,2]].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
