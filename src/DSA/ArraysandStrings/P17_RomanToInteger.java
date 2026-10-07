package DSA.ArraysandStrings;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [17 / 24] - ROMAN TO INTEGER (LeetCode 13)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a Roman numeral s, convert it to an integer.
 *   Symbols: I=1, V=5, X=10, L=50, C=100, D=500, M=1000.
 *   Subtractive rule: If a smaller value appears before a larger value (e.g., IV=4, IX=9),
 *   it is subtracted rather than added.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Iterate from left to right through the string.
 *   - For each character at index i:
 *     - If `val(s[i]) < val(s[i + 1])`, it belongs to a subtractive pair -> subtract `val(s[i])`.
 *     - Otherwise, add `val(s[i])`.
 *   - Switch-case helper avoids HashMap lookup overhead in Java.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass over the string length (n <= 15).
 *   - Space: O(1) - Constant memory.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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