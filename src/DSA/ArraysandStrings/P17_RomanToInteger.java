package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [17 / 24] - ROMAN TO INTEGER (LeetCode 13)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a Roman numeral string `s`, convert it to an integer.
 * Roman numerals are represented by seven different symbols:
 * I=1, V=5, X=10, L=50, C=100, D=500, M=1000.
 *
 * Subtractive Rule: Usually, Roman numerals are written largest to smallest from left to right.
 * However, the numeral for subtraction uses specific pairs where a smaller value appears before
 * a larger value (e.g., IV = 4, IX = 9, XL = 40, XC = 90, CD = 400, CM = 900).
 * In these cases, the smaller value is subtracted from the larger value rather than added.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / String Replacement Approach:
 *    - Search and replace subtractive pairs ("IV" -> "IIII", etc.) then sum up.
 *    - Inefficient due to multiple string scanning passes.
 *
 * 2. Optimal Left-to-Right Single Pass (O(n) Time | O(1) Space):
 *    - Instead of looking ahead to group pairs, we can evaluate character by character from left to right.
 *    - **The Rule:** Compare the value of the current symbol `s[i]` with the next symbol `s[i + 1]`.
 *      - If `current < next`, it is a subtractive combination -> **subtract** `current`.
 *      - Otherwise, it is an additive combination -> **add** `current`.
 *    - **Optimization:** Using a `switch-case` helper method instead of a `HashMap` avoids
 *      object allocation and hash lookup overhead, maximizing runtime performance.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single pass over the string length (`n <= 15` per constraints).
 * - Space: O(1) - Constant auxiliary space for variables.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - "III" -> 3
 * - "LVIII" -> 58 (L = 50, V = 5, III = 3)
 * - "MCMXCIV" -> 1994 (M = 1000, CM = 900, XC = 90, IV = 4)
 *
 * VISUAL DRY RUN (for "III"):
 *   - Start: `total = 0`, `n = 3`
 *   - i = 0: 'I' (1). Next is 'I' (1). 1 < 1 is false -> `total += 1` (total = 1)
 *   - i = 1: 'I' (1). Next is 'I' (1). 1 < 1 is false -> `total += 1` (total = 2)
 *   - i = 2: 'I' (1). No next character (`i < n - 1` is false) -> `total += 1` (total = 3)
 *   - Returns: 3.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does the left-to-right subtractive rule work correctly without breaking the math?
 *    (Because if a smaller value precedes a larger one, subtracting it now correctly cancels out
 *    the contribution when we eventually add the larger value in the next iteration.)
 * 2. What edge cases are handled automatically?
 *    (Single-character strings like "I" skip the lookahead bounds check (`i < n - 1`) and add correctly.)
 */
public class P17_RomanToInteger {

    private static int getValue(char ch) {
        switch (ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    public static int romanToInt(String s) {
        int total = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int current = getValue(s.charAt(i));

            // If current is less than the next symbol, subtract it
            if (i < n - 1 && current < getValue(s.charAt(i + 1))) {
                total -= current;
            } else {
                total += current;
            }
        }

        return total;
    }

    public static void main(String[] args) {
        System.out.println("P17 Output (III):     " + romanToInt("III"));     // Expected: 3
        System.out.println("P17 Output (LVIII):   " + romanToInt("LVIII"));   // Expected: 58
        System.out.println("P17 Output (MCMXCIV): " + romanToInt("MCMXCIV")); // Expected: 1994
    }
}