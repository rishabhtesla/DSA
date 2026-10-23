package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Generate an n by n matrix filled 1..n² in spiral order.
 * EXAMPLE: n=3 -> [[1,2,3],[8,9,4],[7,6,5]].
 * CONSTRAINTS: 1 <= n <= 20.
 * LOGIC / ALGORITHM:
 * 1. Maintain top,bottom,left,right boundaries and fill each side before shrinking it.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input n=3 -> [[1,2,3],[8,9,4],[7,6,5]].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
