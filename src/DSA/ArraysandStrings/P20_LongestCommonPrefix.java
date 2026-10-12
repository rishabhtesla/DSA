package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [20 / 24] - LONGEST COMMON PREFIX (LeetCode 14)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string `""`.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Horizontal Scanning Approach:
 *    - Find the common prefix of `strs[0]` and `strs[1]`, then take that result and
 *      find the common prefix with `strs[2]`, and so on.
 *
 * 2. Optimal Vertical Scanning Approach (O(S) Time | O(1) Space):
 *    - Instead of reducing pairwise strings horizontally, compare characters **column by column**
 *      across all strings simultaneously.
 *    - **The Rule:** Pick the first string (`strs[0]`) as our base. For each character column `col`,
 *      check if every other string `strs[i]` matches `strs[0].charAt(col)` at that exact index.
 *    - **Early Exit:** The moment we find a string shorter than `col` or a character mismatch,
 *      we stop immediately and return the substring from index `0` up to `col`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(S) in the worst case, where `S` is the sum of all characters across all strings in the array.
 *           In practice, it has an optimal best-case running time of O(minLen * n) when a mismatch occurs early.
 * - Space: O(1) - Constant auxiliary space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - ["flower", "flow", "flight"] -> "fl"
 * - ["dog", "racecar", "car"]    -> ""
 *
 * VISUAL DRY RUN (for Test 1: `["flower", "flow", "flight"]`):
 *   - Base string: `"flower"`
 *   - `col = 0`: character is `'f'`
 *     - `strs[1]` ('flow') at col 0 is `'f'` (Match)
 *     - `strs[2]` ('flight') at col 0 is `'f'` (Match)
 *   - `col = 1`: character is `'l'`
 *     - `strs[1]` at col 1 is `'l'` (Match)
 *     - `strs[2]` at col 1 is `'l'` (Match)
 *   - `col = 2`: character is `'o'`
 *     - `strs[1]` at col 2 is `'o'` (Match)
 *     - `strs[2]` at col 2 is `'i'` -> **Mismatch!**
 *   - Loop terminates immediately, returns `base.substring(0, 2)` -> `"fl"`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is vertical scanning often preferred over horizontal scanning in interviews?
 *    (It allows for immediate early-exit on the very first mismatch without processing redundant characters further down the array.)
 * 2. What edge cases are handled automatically?
 *    (Empty array inputs, arrays with a single string, or strings with an empty string `""` element.)
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