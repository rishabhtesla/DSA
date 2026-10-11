package DSA.DP;

import java.util.Arrays;

/**
 * ============================================================================
 * [25 / 30] - CHERRY PICKUP (LeetCode 741)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * You are given an n x n grid representing a field of cherries, where:
 *   - 0 is an empty cell.
 *   - 1 is a cell with a cherry that can be collected.
 *   - -1 is a thorn that blocks movement.
 *
 * Objective:
 *   1. Start at (0, 0) and reach (n - 1, n - 1) by moving only RIGHT and DOWN.
 *   2. Collect cherries along the way (picked cherries become 0).
 *   3. Return from (n - 1, n - 1) back to (0, 0) by moving only LEFT and UP.
 *   Find the MAXIMUM cherries collected. If no valid round trip exists, return 0.
 *
 *
 * 2. THE SIMULTANEOUS WALKING "AHA!" MOMENT:
 * ----------------------------------------------------------------------------
 * Why walking forward then backward fails:
 *   Greedily picking cherries on the way forward can starve the return trip.
 *
 * The Equivalent Model:
 *   Walking from (n - 1, n - 1) back to (0, 0) is IDENTICAL to TWO PEOPLE 
 *   STARTING AT (0, 0) TOGETHER and walking to (n - 1, n - 1) at the SAME PACE!
 *
 * The Step Coordinate Invariant:
 *   Let both walkers take `step` moves in total:
 *   For Walker 1 at (r1, c1): r1 + c1 = step  =>  c1 = step - r1
 *   For Walker 2 at (r2, c2): r2 + c2 = step  =>  c2 = step - r2
 *
 *   Because `c1` and `c2` are completely derived from `step`, our state only needs 
 *   THREE dimensions: (step, r1, r2) rather than 4 dimensions!
 *
 * Cherry Collection:
 *   If r1 == r2, both walkers are on the EXACT SAME CELL:
 *     add grid[r1][c1] ONCE (cannot double-count the same cherry).
 *   If r1 != r2:
 *     add grid[r1][c1] + grid[r2][c2].
 *
 * Transitions:
 *   At each step, each walker can independently move RIGHT or DOWN (4 total transitions):
 *     1. Walker 1 Down, Walker 2 Down: (r1 - 1, r2 - 1)
 *     2. Walker 1 Down, Walker 2 Right: (r1 - 1, r2)
 *     3. Walker 1 Right, Walker 2 Down: (r1, r2 - 1)
 *     4. Walker 1 Right, Walker 2 Right: (r1, r2)
 *
 *
 * 3. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n^3) - Total steps = 2n - 1; r1 and r2 take values from 0 to n - 1.
 * - Space Complexity: O(n^2) - Collapsing step dimension into rolling 2D DP.
 */
public class P25_CherryPickup {

    public static int cherryPickup(int[][] grid) {
        int n = grid.length;
        int maxSteps = 2 * n - 2;

        // dp[r1][r2] stores max cherries collected when Walker 1 is at r1 and Walker 2 is at r2
        int[][] dp = new int[n][n];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // Base case: starting point
        dp[0][0] = grid[0][0];

        // Total steps taken from 1 to 2n - 2
        for (int step = 1; step <= maxSteps; step++) {
            int[][] nextDp = new int[n][n];
            for (int[] row : nextDp) {
                Arrays.fill(row, -1);
            }

            // Valid row bounds for current step
            int minR = Math.max(0, step - (n - 1));
            int maxR = Math.min(n - 1, step);

            for (int r1 = minR; r1 <= maxR; r1++) {
                int c1 = step - r1;
                if (grid[r1][c1] == -1) continue; // Thorns block Walker 1

                for (int r2 = minR; r2 <= maxR; r2++) {
                    int c2 = step - r2;
                    if (grid[r2][c2] == -1) continue; // Thorns block Walker 2

                    // Find max cherries from the 4 previous states at (step - 1)
                    int maxPrev = -1;
                    for (int dr1 = 0; dr1 <= 1; dr1++) {
                        for (int dr2 = 0; dr2 <= 1; dr2++) {
                            int prevR1 = r1 - dr1;
                            int prevR2 = r2 - dr2;

                            if (prevR1 >= 0 && prevR2 >= 0 && dp[prevR1][prevR2] != -1) {
                                maxPrev = Math.max(maxPrev, dp[prevR1][prevR2]);
                            }
                        }
                    }

                    if (maxPrev == -1) continue; // Unreachable from previous step

                    // Collect cherries: if at same cell, count once; otherwise count both
                    int cherries = (r1 == r2) ? grid[r1][c1] : grid[r1][c1] + grid[r2][c2];
                    nextDp[r1][r2] = maxPrev + cherries;
                }
            }

            dp = nextDp;
        }

        return Math.max(0, dp[n - 1][n - 1]);
    }

    public static void main(String[] args) {
        int[][] grid1 = {
            {0,  1, -1},
            {1,  0, -1},
            {1,  1,  1}
        };
        System.out.println("P25 Cherry Pickup: " + cherryPickup(grid1)); // Expected: 5

        int[][] grid2 = {
            {1,  1, -1},
            {1, -1,  1},
            {-1, 1,  1}
        };
        System.out.println("P25 Blocked Grid:  " + cherryPickup(grid2)); // Expected: 0
    }
}