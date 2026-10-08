package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [18 / 24] - INTEGER TO ROMAN (LeetCode 12)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer num between 1 and 3999, convert it to a Roman numeral.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Greedy value subtraction:
 *     Roman numerals operate on a base of fixed symbols plus 6 subtraction pairs:
 *     1000 (M), 900 (CM), 500 (D), 400 (CD), 100 (C), 90 (XC), 50 (L), 40 (XL),
 *     10 (X), 9 (IX), 5 (V), 4 (IV), 1 (I).
 *   - If we store values and symbols in descending order:
 *     Iterate through values; while `num >= value[i]`, append `symbol[i]` and subtract `value[i]`.
 *
 * COMPLEXITY:
 *   - Time:  O(1) - The range is bounded (num < 4000), total loop iterations <= 15.
 *   - Space: O(1) - Fixed symbol arrays and bounded StringBuilder output.
 *
 *
 * EXAMPLE:
 *   The first main call converts 3749 and expects "MMMDCCXLIX".
 *
 * VISUAL DRY RUN:
 *   Greedy value/symbol picks consume the number: 3000 -> "MMM", 700 -> "DCC",
 *   40 -> "XL", and 9 -> "IX". Concatenating the selected symbols returns
 *   "MMMDCCXLIX".
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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