package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the transpose of a rectangular matrix.
 * EXAMPLE: [[1,2,3],[4,5,6]] -> [[1,4],[2,5],[3,6]].
 * CONSTRAINTS: 1 <= rows,cols <= 100; matrix is rectangular.
 * LOGIC / ALGORITHM:
 * 1. Create a cols-by-rows result and assign result[j][i]=matrix[i][j].
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [[1,2,3],[4,5,6]] -> [[1,4],[2,5],[3,6]].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
