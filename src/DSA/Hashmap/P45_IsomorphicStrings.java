package DSA.Hashmap;

/**
 * ============================================================================
 * [45 / 52] - ISOMORPHIC STRINGS (LeetCode 205)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given two strings `s` and `t`, determine if they are isomorphic.
 * Two strings are isomorphic if the characters in `s` can be replaced to get `t`,
 * preserving character order with a strict bijection (no two characters in `s`
 * may map to the same character in `t`, and vice versa).
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Dual HashMaps / Arrays Approach:
 *    - Maintain two separate maps to ensure a two-way 1-to-1 mapping relationship.
 *
 * 2. Optimal "Last Seen Position" Mapping (O(n) Time | O(1) Space):
 *    - **The Core Insight:** Instead of tracking character strings or object mappings,
 *      we can compare the **last seen index** of characters in both strings.
 *    - If two characters correspond to each other under a valid bijection, they must have
 *      appeared at the exact same relative positions previously.
 *    - **Implementation trick:** Use 1-based indexing (`i + 1`) in two arrays of size 256
 *      (to cover all ASCII characters). Since uninitialized array elements default to `0`,
 *      using `i + 1` correctly distinguishes between "unseen" characters and a character
 *      last seen at index `0`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single pass over the characters of strings `s` and `t`.
 * - Space: O(1) - Two fixed ASCII frequency/position lookup tables of size 256.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `s = "egg"`, `t = "add"`     -> `true`
 * - `s = "foo"`, `t = "bar"`     -> `false`
 * - `s = "paper"`, `t = "title"` -> `true`
 *
 * VISUAL DRY RUN (for `s = "egg"`, `t = "add"`):
 *   - i = 0: cs = 'e', ct = 'a'. Both mapS['e'] and mapT['a'] are 0. Equal!
 *     -> Updates `mapS['e'] = 1`, `mapT['a'] = 1`.
 *   - i = 1: cs = 'g', ct = 'd'. Both mapS['g'] and mapT['d'] are 0. Equal!
 *     -> Updates `mapS['g'] = 2`, `mapT['d'] = 2`.
 *   - i = 2: cs = 'g', ct = 'd'. `mapS['g']` is 2, `mapT['d']` is 2. Equal!
 *     -> Updates `mapS['g'] = 3`, `mapT['d'] = 3`.
 *   - Returns `true`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we use `i + 1` instead of `i` for tracking last-seen positions?
 *    (Because integer arrays default to 0. If we used `i`, a character first seen at index 0
 *    would look indistinguishable from an uninitialized character slot, breaking the check.)
 * 2. What edge cases are handled upfront?
 *    (Strings of differing lengths are short-circuited instantly by the length check.)
 */
public class P45_IsomorphicStrings {

    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] mapS = new int[256];
        int[] mapT = new int[256];

        for (int i = 0; i < s.length(); i++) {
            char cs = s.charAt(i);
            char ct = t.charAt(i);

            // Compare last seen positions
            if (mapS[cs] != mapT[ct]) {
                return false;
            }

            // Update to current index + 1
            mapS[cs] = i + 1;
            mapT[ct] = i + 1;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("P45 Output (egg, add):     " + isIsomorphic("egg", "add"));     // Expected: true
        System.out.println("P45 Output (foo, bar):     " + isIsomorphic("foo", "bar"));     // Expected: false
        System.out.println("P45 Output (paper, title): " + isIsomorphic("paper", "title")); // Expected: true
    }
}