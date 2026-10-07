package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [20 / 24] - LONGEST COMMON PREFIX (LeetCode 14)
 * ============================================================================
 * 
 * PROBLEM:
 *   Write a function to find the longest common prefix string amongst an array of
 *   strings. If there is no common prefix, return an empty string "".
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Horizontal Scanning: Compare prefix of (str[0], str[1]), then with str[2], etc.
 *   - Vertical Scanning (Better early-exit in practice):
 *     Compare characters column by column across all strings.
 *     At column `col`, take `char c = strs[0].charAt(col)`:
 *     Check every other string `strs[i]`. If `col == strs[i].length()` or
 *     `strs[i].charAt(col) != c`, the common prefix stops immediately before `col`.
 *     Return `strs[0].substring(0, col)`.
 *
 * COMPLEXITY:
 *   - Time:  O(S) where S is the sum of characters across all strings.
 *            Best case O(minLen * n) if mismatch occurs early.
 *   - Space: O(1) - Constant auxiliary space.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P20_LongestCommonPrefix {

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        String base = strs[0];
        for (int col = 0; col < base.length(); col++) {
            char c = base.charAt(col);
            for (int i = 1; i < strs.length; i++) {
                // Out of bounds on strs[i] or character mismatch found
                if (col == strs[i].length() || strs[i].charAt(col) != c) {
                    return base.substring(0, col);
                }
            }
        }

        return base;
    }

    public static void main(String[] args) {
        String[] strs1 = {"flower", "flow", "flight"};
        String[] strs2 = {"dog", "racecar", "car"};
        System.out.println("P20 Output (Test 1): \"" + longestCommonPrefix(strs1) + "\""); // Expected: "fl"
        System.out.println("P20 Output (Test 2): \"" + longestCommonPrefix(strs2) + "\""); // Expected: ""
    }
}