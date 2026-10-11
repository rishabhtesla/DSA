package DSA.DP;

/**
 * ============================================================================
 * [23 / 30] - MINIMUM PATH SUM (LeetCode 64)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an m x n grid filled with non-negative numbers, find a path from top left 
 * to bottom right which minimizes the sum of all numbers along its path.
 * You can only move either DOWN or RIGHT at any point in time.
 *
 *
 * 2. RECURRENCE & 1D IN-PLACE/ARRAY LOGIC:
 * ----------------------------------------------------------------------------
 * Let dp[j] = minimum path sum to reach the current cell in column `j`.
 *
 * Transition:
 *   To reach cell (i, j), we must arrive from either (i - 1, j) or (i, j - 1).
 *   We choose the minimum cost path:
 *     - If i == 0 and j == 0: dp[0] = grid[0][0]
 *     - If i == 0: dp[j] = dp[j - 1] + grid[0][j] (can only come from the left)
 *     - If j == 0: dp[0] = dp[0] + grid[i][0]     (can only come from above)
 *     - Otherwise: dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1])
 *                          where dp[j] is top, and dp[j - 1] is left.
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * grid = [
 *   [1, 3, 1],
 *   [1, 5, 1],
 *   [4, 2, 1]
 * ]
 *
 * Initial Row 0:
 *   dp[0] = 1
 *   dp[1] = dp[0] + 3 = 4
 *   dp[2] = dp[1] + 1 = 5
 *   dp = [1, 4, 5]
 *
 * Row 1:
 *   j=0: dp[0] = dp[0] + 1 = 1 + 1 = 2
 *   j=1: dp[1] = 5 + min(dp[1]=4, dp[0]=2) = 5 + 2 = 7
 *   j=2: dp[2] = 1 + min(dp[2]=5, dp[1]=7) = 1 + 5 = 6
 *   dp = [2, 7, 6]
 *
 * Row 2:
 *   j=0: dp[0] = dp[0] + 4 = 2 + 4 = 6
 *   j=1: dp[1] = 2 + min(dp[1]=7, dp[0]=6) = 2 + 6 = 8
 *   j=2: dp[2] = 1 + min(dp[2]=6, dp[1]=8) = 1 + 6 = 7
 *   dp = [6, 8, 7]
 *
 * Final Result: dp[2] = 7 (Path: 1 -> 3 -> 1 -> 1 -> 1 = 7)
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(m * n)
 * - Space Complexity: O(n) using a single 1D array.
 */
public class P23_MinimumPathSum {

    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] dp = new int[n];
        dp[0] = grid[0][0];

        // Initialize row 0 (can only move right)
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        // Process remaining rows
        for (int i = 1; i < m; i++) {
            dp[0] += grid[i][0]; // First column (can only move down)

            for (int j = 1; j < n; j++) {
                // Min of coming from top (dp[j]) or left (dp[j - 1])
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        int[][] grid1 = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        System.out.println("P23 Min Path Sum: " + minPathSum(grid1)); // Expected: 7

        int[][] grid2 = {
            {1, 2, 3},
            {4, 5, 6}
        };
        System.out.println("P23 Min Path Sum: " + minPathSum(grid2)); // Expected: 12 (1 -> 2 -> 3 -> 6)
    }
}