package DSA.HashMaps;

/**
 * ============================================================================
 * [39 / 43] - VALID SUDOKU (LeetCode 36)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be
 * validated according to the following rules:
 * 1. Each row must contain the digits 1-9 without repetition.
 * 2. Each column must contain the digits 1-9 without repetition.
 * 3. Each of the nine 3 x 3 sub-boxes must contain the digits 1-9 without repetition.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Multiple Pass Approach:
 *    - Scan rows, columns, and sub-boxes in separate loops using hash sets or string keys.
 *    - Valid, but requires multiple passes over the board or redundant lookups.
 *
 * 2. Optimal Single-Pass Boolean Tracking Approach (O(1) Time | O(1) Space):
 *    - **The Core Insight:** We can validate rows, columns, and 3x3 sub-boxes simultaneously
 *      in a **single pass** using fixed boolean tracking arrays or bitmasks.
 *    - **Sub-Box Mapping Formula:** Any cell coordinate `(r, c)` maps directly into a 3x3 sub-box index
 *      from `0` to `8` using: `boxIndex = (r / 3) * 3 + (c / 3)`.
 *    - **Step 1:** Maintain tracking arrays `rows[9][9]`, `cols[9][9]`, and `boxes[9][9]`.
 *    - **Step 2:** For each filled cell, convert character `ch` to a 0-indexed digit via `digit = ch - '1'`.
 *    - **Step 3:** Check if `rows[r][digit] || cols[c][digit] || boxes[boxIndex][digit]` is already true.
 *      If so, a duplicate exists -> return `false`. Otherwise, mark all three as `true`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(1) - The board size is strictly fixed at 9x9 = 81 cells, making time complexity constant.
 * - Space: O(1) - Fixed boolean arrays of size 9x9 require constant auxiliary memory.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Validates standard filled cells on a partial or complete 9x9 Sudoku board -> returns `true`.
 *
 * VISUAL DRY RUN:
 *   - Scan row-major. For cell `(0, 0) = '5'`: `digit = 4`, `boxIndex = 0`.
 *   - Checks tracking flags: all are `false`, so it marks `rows[0][4]`, `cols[0][4]`, and `boxes[0][4]` as `true`.
 *   - Subsequent cells are checked similarly; any collision triggers an immediate `false` return.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does the sub-box mapping formula `(r / 3) * 3 + (c / 3)` correctly identify 3x3 boxes?
 *    (Integer division by 3 groups rows 0-2 into block row 0, rows 3-5 into block row 1, etc.
 *    Multiplying by 3 and adding `c / 3` maps the 9 sub-boxes in standard reading order from 0 to 8.)
 * 2. Why is this problem considered O(1) time and space complexity?
 *    (Because the board dimensions are invariant (always 9x9), the number of operations and memory allocations
 *    never scale with an arbitrary input size $n$.)
 */
public class P39_ValidSudoku {

    public static boolean isValidSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char ch = board[r][c];
                if (ch == '.') continue;

                int digit = ch - '1'; // Map characters '1'-'9' to indices 0-8
                int boxIndex = (r / 3) * 3 + (c / 3);

                // Check if the digit already exists in the current row, column, or sub-box
                if (rows[r][digit] || cols[c][digit] || boxes[boxIndex][digit]) {
                    return false;
                }

                // Mark the digit as seen in all three constraints
                rows[r][digit] = true;
                cols[c][digit] = true;
                boxes[boxIndex][digit] = true;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };
        System.out.println("P39 Output: " + isValidSudoku(board)); // Expected: true
    }
}