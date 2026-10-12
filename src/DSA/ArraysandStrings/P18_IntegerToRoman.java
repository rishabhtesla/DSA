package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [18 / 24] - INTEGER TO ROMAN (LeetCode 12)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an integer `num` (where 1 <= num <= 3999), convert it to a Roman numeral string.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Greedy Value Subtraction Approach (O(1) Time | O(1) Space):
 *    - Roman numerals are fundamentally additive, with special tokens introduced for
 *      subtractive pairs (like 4 = IV, 9 = IX, 40 = XL, 90 = XC, 400 = CD, 900 = CM).
 *    - If we pre-define all standard values and their corresponding Roman string symbols
 *      in **descending order** (from 1000 down to 1), we can apply a greedy strategy.
 *    - **The Rule:** For each value-symbol pair, keep subtracting the value from `num`
 *      and appending the symbol as long as `num >= value`.
 *    - Because values are checked largest-first, this naturally yields the shortest valid
 *      Roman numeral representation without needing complex state tracking.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(1) - The maximum input is 3999, so the outer and inner loops have a strictly
 *           bounded maximum number of iterations (at most ~15 appends total).
 * - Space: O(1) - Fixed lookup arrays and a StringBuilder output of bounded length (at most 15 chars).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - 3749 -> "MMMDCCXLIX" (3000 = MMM, 700 = DCC, 40 = XL, 9 = IX)
 * - 58   -> "LVIII"      (50 = L, 8 = VIII)
 * - 1994 -> "MCMXCIV"    (1000 = M, 900 = CM, 90 = XC, 4 = IV)
 *
 * VISUAL DRY RUN (for `num = 58`):
 *   - i = 0 (1000): 58 >= 1000 (false)
 *   - ... skips down ...
 *   - i = 6 (50): 58 >= 50 (true) -> `num` becomes 8, appends "L" (`sb = "L"`)
 *   - i = 7 (40): 8 >= 40 (false)
 *   - ... skips down ...
 *   - i = 10 (5): 8 >= 5 (true) -> `num` becomes 3, appends "V" (`sb = "LV"`)
 *   - i = 11 (4): 3 >= 4 (false)
 *   - i = 12 (1): 3 >= 1 (true) -> `num` becomes 2, appends "I" (`sb = "LVI"`)
 *                 3 >= 1 (true) -> `num` becomes 1, appends "I" (`sb = "LVII"`)
 *                 3 >= 1 (true) -> `num` becomes 0, appends "I" (`sb = "LVIII"`)
 *   - Loop terminates (`num == 0`), returns "LVIII".
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why must the `VALUES` and `SYMBOLS` arrays include subtraction pairs (like 900, 400, etc.)?
 *    (Without them, a greedy algorithm would try to build 900 using 500 + 100 + 100 + 100 (DCCC),
 *    which violates standard Roman numeral notation where 900 must be written as CM.)
 * 2. Why is the time complexity considered O(1) rather than O(n)?
 *    (Because the input space is strictly capped at 3999. The number of loops executed
 *    does not scale infinitely with an arbitrary input size `n`.)
 */
public class P18_IntegerToRoman {

    private static final int[] VALUES = {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };

    private static final String[] SYMBOLS = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };

    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < VALUES.length && num > 0; i++) {
            while (num >= VALUES[i]) {
                num -= VALUES[i];
                sb.append(SYMBOLS[i]);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("P18 Output (3749): " + intToRoman(3749)); // Expected: "MMMDCCXLIX"
        System.out.println("P18 Output (58):   " + intToRoman(58));   // Expected: "LVIII"
        System.out.println("P18 Output (1994): " + intToRoman(1994)); // Expected: "MCMXCIV"
    }
}