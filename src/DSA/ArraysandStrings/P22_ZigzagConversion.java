package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [22 / 24] - ZIGZAG CONVERSION (LeetCode 6)
 * ============================================================================
 * 
 * PROBLEM:
 *   The string "PAYPALISHIRING" is written in a zigzag pattern on a given number
 *   of rows like this:
 *   P   A   H   N
 *   A P L S I I G
 *   Y   I   R
 *   And then read line by line: "PAHNAPLSIIGYIR". Return this converted string.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Simulate row traversal with a bounce:
 *     Maintain an array of `StringBuilder`—one for each row.
 *     Keep track of `currentRow` and a direction step `dir` (+1 going down, -1 going up).
 *     When `currentRow == 0`, switch direction to +1 (downwards).
 *     When `currentRow == numRows - 1`, switch direction to -1 (upwards).
 *   - Edge Case: If `numRows == 1` or `numRows >= s.length()`, zigzag does not change the order.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Visits each character in s exactly once.
 *   - Space: O(n) - StringBuilders storing characters for each row.
 *
 *
 * EXAMPLE:
 *   The first main call converts "PAYPALISHIRING" with numRows=3 and expects "PAHNAPLSIIGYIR".
 *
 * VISUAL DRY RUN:
 *   Row pointer/direction visits rows 0,1,2,1 repeatedly. Appending characters gives
 *   row0="PAHN", row1="APLSIIG", row2="YIR"; concatenate rows -> "PAHNAPLSIIGYIR".
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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