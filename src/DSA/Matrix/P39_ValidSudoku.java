/**
 * ============================================================================
 * [39 / 43] - VALID SUDOKU (LeetCode 36)
 * ============================================================================
 *
 * PROBLEM:
 *   Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be
 *   validated according to the following rules:
 *   1. Each row must contain the digits 1-9 without repetition.
 *   2. Each column must contain the digits 1-9 without repetition.
 *   3. Each of the nine 3 x 3 sub-boxes must contain the digits 1-9 without repetition.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Instead of 3 separate passes or string-based hash sets, use boolean arrays or bitmasks.
 *   - Sub-box mapping formula:
 *     `boxIndex = (row / 3) * 3 + (col / 3)` maps any (row, col) cell directly into [0..8].
 *   - Tracking:
 *     `boolean[9][9] rows`, `boolean[9][9] cols`, `boolean[9][9] boxes`.
 *     For digit `d = char - '1'`:
 *     If `rows[r][d] || cols[c][d] || boxes[b][d]` is already true -> invalid board!
 *     Otherwise, mark all three as true.
 *
 * COMPLEXITY:
 *   - Time:  O(1) - Constant 81 cells checked.
 *   - Space: O(1) - Fixed boolean arrays of size 9x9.
 *
 *
 * EXAMPLE:
 *   The first main board begins row 0 with 5,3,.,.,7,... and the complete board is the
 *   standard valid Sudoku from main; expected result is true.
 *
 * VISUAL DRY RUN:
 *   Scan row-major, mapping each digit to row/column/3x3-box flags: (0,0)=5 marks box0,
 *   (0,1)=3, (0,4)=7, then row 1 marks 6,1,9,5 in distinct flags. Every filled cell
 *   is new in all three sets; scan ends without a collision -> true.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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

                int digit = ch - '1'; // Map '1'-'9' to 0-8
                int boxIndex = (r / 3) * 3 + (c / 3);

                // Check for duplicates
                if (rows[r][digit] || cols[c][digit] || boxes[boxIndex][digit]) {
                    return false;
                }

                // Mark seen
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