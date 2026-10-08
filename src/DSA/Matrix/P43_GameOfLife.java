package DSA.Matrix;

import java.util.Arrays;

/**
 * ============================================================================
 * [43 / 43] - GAME OF LIFE (LeetCode 289)
 * ============================================================================
 * 
 * PROBLEM:
 *   Conway's Game of Life on an m x n board. State updates simultaneously:
 *   1. Live cell with < 2 live neighbors dies (underpopulation).
 *   2. Live cell with 2 or 3 live neighbors lives.
 *   3. Live cell with > 3 live neighbors dies (overpopulation).
 *   4. Dead cell with exactly 3 live neighbors becomes live (reproduction).
 *   Update the board in-place without creating a copy.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - The challenge is that cell states update SIMULTANEOUSLY, so mutating a cell
 *     directly ruins neighbor calculations for surrounding cells.
 *   - Two-Bit State Encoding (or 4 State Flags):
 *     Store (Next State, Current State) in the same cell using 2 bits:
 *     - State 0: 0 -> 0 (dead to dead)
 *     - State 1: 1 -> 1 (live to live)
 *     - State 2: 1 -> 0 (live to dead)
 *     - State 3: 0 -> 1 (dead to live)
 *   - Reading previous state: `(board[r][c] == 1 || board[r][c] == 2)` means was alive.
 *   - Applying next state: `board[r][c] %= 2` (or `board[r][c] >> 1` if using bit shift).
 *
 * COMPLEXITY:
 *   - Time:  O(m * n) - Check 8 neighbors for every cell, then a second pass to decode.
 *   - Space: O(1) - In-place integer status encodings.
 *
 *
 * EXAMPLE:
 *   The first main board is [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]; expected next board is
 *   [[0,0,0],[1,0,1],[0,1,1],[0,1,0]].
 *
 * VISUAL DRY RUN:
 *   Count neighbors using encoded states: (0,1) has 1 -> dies (2), (1,0) has 3 -> born
 *   (3), and the middle cells update similarly without losing old values. Decode 2->0 and
 *   3->1 after the pass, yielding the expected board.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                int liveNeighbors = countLiveNeighbors(board, r, c, m, n);

                // Rule 1, 2, 3: Cell currently alive
                if (board[r][c] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        board[r][c] = 2; // Transition: Live -> Dead
                    }
                    // Else stays 1 (Live -> Live)
                } 
                // Rule 4: Cell currently dead
                else {
                    if (liveNeighbors == 3) {
                        board[r][c] = 3; // Transition: Dead -> Live
                    }
                }
            }
        }

        // Final Pass: Decode intermediate states into final 0 or 1
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
                // 1 means currently alive; 2 means was alive but will die
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
        /* Expected:
         * [[0, 0, 0],
         *  [1, 0, 1],
         *  [0, 1, 1],
         *  [0, 1, 0]]
         */
    }
}