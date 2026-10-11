package DSA.DP;

import java.util.Arrays;

/**
 * ============================================================================
 * [24 / 30] - DUNGEON GAME (LeetCode 174)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * A knight starts at the top-left (0, 0) and must rescue the princess at the 
 * bottom-right corner (m - 1, n - 1). The knight moves only RIGHT or DOWN.
 * Each room has an integer value:
 *   - Negative numbers represent demons (drain HP).
 *   - Positive numbers represent magic orbs (restore HP).
 *   - 0 is an empty room.
 *
 * Constraint: If the knight's health points (HP) drop to 0 or below at ANY moment,
 * the knight dies immediately.
 * Return the KNIGHT'S MINIMUM INITIAL HP required to rescue the princess.
 *
 *
 * 2. WHY FORWARD DP FAILS ("THE BACKWARDS AHA! MOMENT"):
 * ----------------------------------------------------------------------------
 * If we start from top-left (0, 0) moving forward:
 *   Two competing factors fight each other:
 *     1. Current health accumulated so far.
 *     2. Minimum drop experienced along the way.
 *   A path with higher health now might hit a fatal drop later.
 *   This violates the "Optimal Substructure" property needed for forward DP!
 *
 * The Fix: WORK BACKWARDS from Princess (m - 1, n - 1) to Knight (0, 0)!
 *   State: dp[i][j] = MINIMUM HP the knight needs BEFORE ENTERING room (i, j)
 *                     in order to successfully survive to the end.
 *
 * Recurrence:
 *   From (i, j), the knight exits by stepping either RIGHT (i, j + 1) or DOWN (i + 1, j).
 *   The knight needs:
 *     minHPUponExit = Math.min(dp[i + 1][j], dp[i][j + 1])
 *
 *   Before entering room (i, j):
 *     neededHP = minHPUponExit - dungeon[i][j]
 *     dp[i][j] = Math.max(1, neededHP)
 *     *(If room gives huge positive health, neededHP might be <= 0, but the knight 
 *       must have at least 1 HP to stay alive upon entering).*
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * dungeon = [
 *   [-2,  -3,   3],
 *   [-5, -10,   1],
 *   [10,  30,  -5]
 * ]
 *
 * Destination cell (2, 2) has -5:
 *   Upon exiting, princess is rescued (needed HP after = 1).
 *   needed HP before entering: 1 - (-5) = 6. -> dp[2][2] = 6
 *
 * Cell (2, 1) has 30:
 *   Can only go right to (2, 2) which needs 6.
 *   needed: 6 - 30 = -24 -> clamped to 1. -> dp[2][1] = 1
 *
 * Cell (2, 0) has 10:
 *   Can only go right to (2, 1) which needs 1.
 *   needed: 1 - 10 = -9 -> clamped to 1. -> dp[2][0] = 1
 *
 * Working all the way back to (0, 0):
 *   dp[0][0] ends up as 7.
 *   (Knight starts with 7 HP -> loses 2 -> 5 HP -> steps right to -3 -> 2 HP 
 *    -> steps right to +3 -> 5 HP -> steps down to +1 -> 6 HP -> rescues princess at -5 -> 1 HP left!).
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(m * n)
 * - Space Complexity: O(n) using a single 1D array.
 */
public class P24_DungeonGame {

    public static int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;

        // dp array of size n + 1 (padded with infinity for out-of-bounds guards)
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);

        // Base case: to exit the princess's cell alive, knight needs at least 1 HP
        dp[n - 1] = 1;

        // Traverse backwards from bottom row to top row
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // Minimum HP needed upon exiting this cell (either going down or going right)
                int minHPToExit = Math.min(dp[j], dp[j + 1]);

                // Minimum HP needed before entering this cell
                dp[j] = Math.max(1, minHPToExit - dungeon[i][j]);
            }
            // Reset right sentinel for the next row above
            dp[n] = Integer.MAX_VALUE;
        }

        return dp[0];
    }

    public static void main(String[] args) {
        int[][] dungeon = {
            {-2,  -3,   3},
            {-5, -10,   1},
            {10,  30,  -5}
        };
        System.out.println("P24 Minimum Initial HP: " + calculateMinimumHP(dungeon)); // Expected: 7
    }
}