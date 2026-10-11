package DSA.DP;

/**
 * ============================================================================
 * [22 / 30] - UNIQUE PATHS II (LeetCode 63)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * You are given an m x n integer array `obstacleGrid`. There is a robot initially 
 * at the top-left corner (grid[0][0]) attempting to reach (grid[m - 1][n - 1]).
 * An obstacle is marked as 1, and an empty space is marked as 0.
 * The robot can only move DOWN or RIGHT and cannot step through obstacles.
 * Return the number of possible unique paths to reach the bottom-right corner.
 *
 *
 * 2. RECURRENCE & OBSTACLE LOGIC:
 * ----------------------------------------------------------------------------
 * If a cell contains an obstacle (`grid[i][j] == 1`), no path can pass through it:
 *   dp[j] = 0
 *
 * Otherwise:
 *   dp[j] = dp[j] (from top) + dp[j - 1] (from left)
 *
 * Base Case:
 *   - If the starting cell has an obstacle (`grid[0][0] == 1`), immediately return 0.
 *   - Otherwise, `dp[0] = 1`.
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * obstacleGrid = [
 *   [0, 0, 0],
 *   [0, 1, 0],
 *   [0, 0, 0]
 * ]
 *
 * Initialize dp of size 3: [1, 0, 0]
 *
 * Row 0:
 *   j=0: grid[0][0]=0 -> dp[0] stays 1
 *   j=1: grid[0][1]=0 -> dp[1] += dp[0] = 1
 *   j=2: grid[0][2]=0 -> dp[2] += dp[1] = 1
 *   dp: [1, 1, 1]
 *
 * Row 1:
 *   j=0: grid[1][0]=0 -> dp[0] stays 1
 *   j=1: grid[1][1]=1 (Obstacle!) -> dp[1] = 0
 *   j=2: grid[1][2]=0 -> dp[2] = dp[2] + dp[1] = 1 + 0 = 1
 *   dp: [1, 0, 1]
 *
 * Row 2:
 *   j=0: grid[2][0]=0 -> dp[0] stays 1
 *   j=1: grid[2][1]=0 -> dp[1] = dp[1] + dp[0] = 0 + 1 = 1
 *   j=2: grid[2][2]=0 -> dp[2] = dp[2] + dp[1] = 1 + 1 = 2
 *   dp: [1, 1, 2]
 *
 * Final Result: dp[2] = 2
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(m * n)
 * - Space Complexity: O(n) using a single 1D array.
 */
public class P22_UniquePathsII {

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        // If start or end is blocked, no path is possible
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }

        int[] dp = new int[n];
        dp[0] = 1; // Base case: 1 way to stand at start

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    // Obstacle blocks all paths through this cell
                    dp[j] = 0;
                } else if (j > 0) {
                    // Accumulate paths from left (dp[j - 1]) onto top (dp[j])
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        int[][] grid1 = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };
        System.out.println("P22 Unique Paths with Obstacles: " + uniquePathsWithObstacles(grid1)); // Expected: 2

        int[][] grid2 = {
            {0, 1},
            {0, 0}
        };
        System.out.println("P22 Unique Paths with Obstacles: " + uniquePathsWithObstacles(grid2)); // Expected: 1
    }
}