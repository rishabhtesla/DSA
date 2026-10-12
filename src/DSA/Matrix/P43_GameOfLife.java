package DSA.Matrix;

import java.util.Arrays;

/**
 * ============================================================================
 * [43 / 43] - GAME OF LIFE (LeetCode 289)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * According to Wikipedia's article: "Conway's Game of Life is a zero-player game..."
 * Given a board of `m x n` integers representing cells where each cell is live (`1`) or dead (`0`),
 * update the board to its next state simultaneously according to the 4 classic rules:
 * 1. Live cell with < 2 live neighbors dies (underpopulation).
 * 2. Live cell with 2 or 3 live neighbors lives.
 * 3. Live cell with > 3 live neighbors dies (overpopulation).
 * 4. Dead cell with exactly 3 live neighbors becomes live (reproduction).
 * **Constraint:** Update the board **in-place** without creating a copy matrix.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Copy Matrix Approach:
 *    - Allocate a duplicate `m x n` matrix to compute next states without affecting current neighbor counts.
 *    - Valid, but violates the O(1) auxiliary space requirement.
 *
 * 2. Optimal Two-Bit State Encoding Approach (O(m * n) Time | O(1) Space):
 *    - **The Core Insight:** Since cell states update *simultaneously*, mutating a cell directly would
 *      ruin neighbor calculations for neighboring cells. We can solve this by encoding **two states**
 *      (Next State & Current State) into the same integer using 2 bits:
 *      - `0`: Dead -> Dead
 *      - `1`: Live -> Live
 *      - `2`: Live -> Dead
 *      - `3`: Dead -> Live
 *    - **Step 1:** Scan every cell, count its live neighbors (interpreting both `1` and `2` as previously alive),
 *      and assign encoded states `2` or `3` where transitions occur.
 *    - **Step 2:** Run a final decoding pass to convert all `2`s to `0`s and `3`s to `1`s.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(m * n) - Two linear passes over the grid (one for neighbor checking/encoding, one for decoding).
 * - Space: O(1) - In-place integer state encoding requires zero auxiliary matrix allocations.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Board:
 *   [0, 1, 0]
 *   [0, 0, 1]
 *   [1, 1, 1]
 *   [0, 0, 0]
 * - Expected Next State:
 *   [0, 0, 0]
 *   [1, 0, 1]
 *   [0, 1, 1]
 *   [0, 1, 0]
 *
 * VISUAL DRY RUN:
 *   - Cell `(0, 1)` has 1 live neighbor -> dies underpopulation (encoded as state `2`).
 *   - Cell `(1, 0)` has 3 live neighbors -> reproduces (encoded as state `3`).
 *   - After the first pass, temporary states preserve original neighbor data.
 *   - The final pass decodes `2 -> 0` and `3 -> 1`, yielding the correct next board state.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is direct mutation impossible without state encoding?
 *    (Because updating a cell's value from `1` to `0` prematurely would cause subsequent neighbor checks
 *    to read an incorrect lower live count, cascading into calculation errors.)
 * 2. How do we distinguish between a cell that is *currently* alive versus one that *was* alive?
 *    (By checking `board[nr][nc] == 1 || board[nr][nc] == 2`, since both states signify that the cell
 *    was alive in the current generation.)
 */
public class P43_GameOfLife {

    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
    };

    public static void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        // Step 1: Compute next states and encode them using state values 2 and 3
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                int liveNeighbors = countLiveNeighbors(board, r, c, m, n);

                if (board[r][c] == 1) {
                    // Rule 1 & 3: Live cell dies due to underpopulation or overpopulation
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        board[r][c] = 2; // Transition: Live -> Dead
                    }
                } else {
                    // Rule 4: Dead cell becomes live due to reproduction
                    if (liveNeighbors == 3) {
                        board[r][c] = 3; // Transition: Dead -> Live
                    }
                }
            }
        }

        // Step 2: Final pass to decode intermediate states into final 0 or 1
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (board[r][c] == 2) {
                    board[r][c] = 0;
                } else if (board[r][c] == 3) {
                    board[r][c] = 1;
                }
            }
        }
    }

    private static int countLiveNeighbors(int[][] board, int r, int c, int m, int n) {
        int live = 0;
        for (int[] d : DIRECTIONS) {
            int nr = r + d[0];
            int nc = c + d[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                // 1 means currently alive; 2 means was alive but will die in next state
                if (board[nr][nc] == 1 || board[nr][nc] == 2) {
                    live++;
                }
            }
        }
        return live;
    }

    public static void main(String[] args) {
        int[][] board = {
                {0, 1, 0},
                {0, 0, 1},
                {1, 1, 1},
                {0, 0, 0}
        };
        gameOfLife(board);
        System.out.println("P43 Output: " + Arrays.deepToString(board));
        // Expected: [[0, 0, 0], [1, 0, 1], [0, 1, 1], [0, 1, 0]]
    }
}