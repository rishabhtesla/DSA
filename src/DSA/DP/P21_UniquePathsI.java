package DSA.DP;

import java.util.Arrays;

/**
 * ============================================================================
 * [21 / 30] - UNIQUE PATHS I (LeetCode 62)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * A robot is located at the top-left corner of an m x n grid (grid[0][0]).
 * The robot tries to reach the bottom-right corner (grid[m - 1][n - 1]).
 * The robot can only move either DOWN or RIGHT at any point in time.
 * Given the two integers m and n, return the number of possible unique paths.
 *
 *
 * 2. RECURRENCE & 1D SPACE OPTIMIZATION:
 * ----------------------------------------------------------------------------
 * Let dp[i][j] = number of unique paths to reach cell (i, j).
 * To arrive at (i, j), the robot must come from:
 *   - Cell above: (i - 1, j)
 *   - Cell to the left: (i, j - 1)
 *
 * Recurrence:
 *   dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
 *
 * Base Case:
 *   dp[0][j] = 1 (only 1 way to reach cells in the first row: keep moving right)
 *   dp[i][0] = 1 (only 1 way to reach cells in the first column: keep moving down)
 *
 * 1D Space Optimization:
 *   Notice `dp[i][j]` only needs the current row's left neighbor `dp[j - 1]`
 *   and the previous row's value at the same column `dp[j]`.
 *   Formula on a single 1D array:
 *     dp[j] = dp[j] + dp[j - 1]
 *   Memory drops from O(m * n) to O(n).
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * m = 3, n = 7
 * Initial 1D array of size 7: [1, 1, 1, 1, 1, 1, 1] (Row 0)
 *
 * Row 1 (i = 1):
 *   j=1: dp[1] = dp[1] + dp[0] = 1 + 1 = 2
 *   j=2: dp[2] = 1 + 2 = 3
 *   j=3: dp[3] = 1 + 3 = 4
 *   j=4: dp[4] = 1 + 4 = 5
 *   j=5: dp[5] = 1 + 5 = 6
 *   j=6: dp[6] = 1 + 6 = 7
 *   dp becomes: [1, 2, 3, 4, 5, 6, 7]
 *
 * Row 2 (i = 2):
 *   j=1: dp[1] = 2 + 1 = 3
 *   j=2: dp[2] = 3 + 3 = 6
 *   j=3: dp[3] = 4 + 6 = 10
 *   j=4: dp[4] = 5 + 10 = 15
 *   j=5: dp[5] = 6 + 15 = 21
 *   j=6: dp[6] = 7 + 21 = 28
 *   dp becomes: [1, 3, 6, 10, 15, 21, 28]
 *
 * Final Result: dp[6] = 28
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(m * n) - Visited every cell once.
 * - Space Complexity: O(n) - Single 1D array of size n.
 */
public class P21_UniquePathsI {

    public static int uniquePaths(int m, int n) {
        // Space optimization: allocate array of size min(m, n)
        if (m < n) {
            return uniquePaths(n, m);
        }

        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Row 0 has exactly 1 path to each cell

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        System.out.println("P21 Unique Paths (3 x 7): " + uniquePaths(3, 7)); // Expected: 28
        System.out.println("P21 Unique Paths (3 x 2): " + uniquePaths(3, 2)); // Expected: 3
    }
}