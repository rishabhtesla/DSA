package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Corrupted Ledger Node Propagation / Rotting Oranges
 * ============================================================================
 *
 * Problem Statement:
 * You are given an m x n grid representing a distributed cluster of nodes:
 *   - 0: Empty node
 *   - 1: Healthy uncorrupted node
 *   - 2: Corrupted/infected node
 *
 * Every minute, any healthy node (1) that is 4-directionally adjacent to a
 * corrupted node (2) becomes corrupted.
 *
 * Return the minimum minutes that must elapse until no node is healthy.
 * If this is impossible, return -1.
 *
 * Input Format for Custom Testing:
 * - Line 1: Two integers m and n.
 * - Next m lines: n space-separated integers representing the grid rows.
 *
 * Constraints:
 * - 1 <= m, n <= 100
 * - grid[i][j] is 0, 1, or 2.
 * ============================================================================
 */
public class SolutionRottingNodes {

    public static int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        int healthyCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) {
                    queue.offer(new int[]{r, c});
                } else if (grid[r][c] == 1) {
                    healthyCount++;
                }
            }
        }

        if (healthyCount == 0) return 0;

        int minutes = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty() && healthyCount > 0) {
            int size = queue.size();
            minutes++;

            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                for (int[] dir : dirs) {
                    int nr = curr[0] + dir[0];
                    int nc = curr[1] + dir[1];

                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        healthyCount--;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }

        return healthyCount == 0 ? minutes : -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String line = bufferedReader.readLine();
        if (line == null || line.trim().isEmpty()) return;

        String[] dims = line.trim().split("\\s+");
        int m = Integer.parseInt(dims[0]);
        int n = Integer.parseInt(dims[1]);

        int[][] grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            String[] rowTokens = bufferedReader.readLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(rowTokens[j]);
            }
        }

        System.out.println(orangesRotting(grid));
        bufferedReader.close();
    }
}