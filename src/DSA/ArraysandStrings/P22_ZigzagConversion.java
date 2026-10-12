package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [22 / 24] - ZIGZAG CONVERSION (LeetCode 6)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * The string `"PAYPALISHIRING"` is written in a zigzag pattern on a given number
 * of rows like this (example with numRows = 3):
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 *
 * And then read line by line: `"PAHNAPLSIIGYIR"`.
 * Write a function that takes a string and returns this converted string.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Mathematical Index Calculation Approach:
 *    - Calculate character positions based on cycle lengths (`2 * numRows - 2`).
 *    - Fast, but prone to boundary off-by-one errors and harder to write cleanly.
 *
 * 2. Optimal Simulation with Direction Bounce (O(n) Time | O(n) Space):
 *    - Instead of complex math formulas, we can **simulate** the visual writing process.
 *    - Maintain an array of `StringBuilder` objects—one for each row (`numRows`).
 *    - Keep track of `currentRow` and a direction step `dir` (`+1` going down, `-1` going up).
 *    - **The Rule:** As we iterate through each character of the string, append it to `rows[currentRow]`.
 *      When we hit the top (`currentRow == 0`) or bottom (`currentRow == numRows - 1`) row,
 *      we flip the direction `dir = -dir`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single pass visiting each character in string `s` exactly once.
 * - Space: O(n) - Array of `StringBuilder` objects storing the characters for each row (totaling `n` characters).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `"PAYPALISHIRING"`, numRows = 3 -> `"PAHNAPLSIIGYIR"`
 * - `"PAYPALISHIRING"`, numRows = 4 -> `"PINALSIGYAHRPI"`
 *
 * VISUAL DRY RUN (for `numRows = 3`):
 *   - Start: `currentRow = 0`, `dir = -1` (flips to +1 on first step)
 *   - Character 'P': added to row 0. Direction flips to +1. `currentRow` becomes 1.
 *   - Character 'A': added to row 1. `currentRow` becomes 2.
 *   - Character 'Y': added to row 2. Hits bottom (`numRows - 1`). Direction flips to -1. `currentRow` becomes 1.
 *   - Character 'P': added to row 1. `currentRow` becomes 0.
 *   - Character 'A': added to row 0. Hits top (`0`). Direction flips to +1. `currentRow` becomes 1.
 *   - ... continues until all characters are distributed across rows.
 *   - Concatenating row 0, row 1, and row 2 yields `"PAHNAPLSIIGYIR"`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we include an early check for `numRows == 1` or `numRows >= s.length()`?
 *    (Because with 1 row or more rows than characters, the zigzag pattern degenerates into a flat line,
 *    so returning `s` immediately prevents unnecessary computation and array overhead.)
 * 2. How does the direction flip work seamlessly at boundaries?
 *    (Checking `currentRow == 0 || currentRow == numRows - 1` right after appending ensures
 *    the bounce happens precisely at the outer boundary rows before the row pointer updates.)
 */
public class P22_ZigzagConversion {

    public static String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) {
            return s;
        }

        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }

        int currentRow = 0;
        int dir = -1; // Will flip to +1 on first step

        for (char c : s.toCharArray()) {
            rows[currentRow].append(c);

            // Bounce direction when hitting top or bottom row
            if (currentRow == 0 || currentRow == numRows - 1) {
                dir = -dir;
            }
            currentRow += dir;
        }

        // Combine all rows
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("P22 Output (numRows=3): " + convert("PAYPALISHIRING", 3)); // Expected: "PAHNAPLSIIGYIR"
        System.out.println("P22 Output (numRows=4): " + convert("PAYPALISHIRING", 4)); // Expected: "PINALSIGYAHRPI"
    }
}