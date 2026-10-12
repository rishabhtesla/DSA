package DSA.Hashmap;

/**
 * ============================================================================
 * [47 / 52] - VALID ANAGRAM (LeetCode 242)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.
 * An anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Sorting Approach (O(n log n) Time | O(n) Space):
 *    - Convert both strings to char arrays, sort them, and check for equality.
 *    - Inefficient due to sorting overhead (`O(n log n)`).
 *
 * 2. Optimal Single-Pass Frequency Array (O(n) Time | O(1) Space):
 *    - **Step 1:** Early exit if lengths differ (`s.length() != t.length()`).
 *    - **Step 2:** Use a fixed integer array of size 26 (since inputs contain only lowercase English letters).
 *      Instead of two separate loops, we **increment** counts for characters in `s` and **decrement**
 *      counts for characters in `t` within a single unified loop.
 *    - **Step 3:** Verify that every bucket in the frequency array ends at exactly `0`.
 *      If any bucket is non-zero, the strings are not anagrams.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear scan of length `n` through both strings.
 * - Space: O(1) - Fixed integer array of size 26 requires constant auxiliary memory.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `s = "anagram"`, `t = "nagaram"` -> `true`
 * - `s = "rat"`, `t = "car"`         -> `false`
 *
 * VISUAL DRY RUN (for `s = "rat"`, `t = "car"`):
 *   - Length check: 3 == 3 (passes).
 *   - i = 0: counts['r' - 'a']++ (1), counts['c' - 'a']-- (-1)
 *   - i = 1: counts['a' - 'a']++ (1), counts['a' - 'a']-- (0)
 *   - i = 2: counts['t' - 'a']++ (1), counts['r' - 'a']-- (0)
 *   - Final array check: bucket for 't' has value 1 (non-zero) -> returns `false`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is a single-pass loop that increments and decrements simultaneously better than two separate frequency loops?
 *    (It halves the number of loop iterations and allows us to use a single array to track net balance.)
 * 2. What edge cases are handled automatically?
 *    (Strings with different lengths are short-circuited immediately before any counting begins.)
 */
public class P47_ValidAnagram {

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] counts = new int[26];

        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }

        for (int c : counts) {
            if (c != 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("P47 Output (anagram, nagaram): " + isAnagram("anagram", "nagaram")); // Expected: true
        System.out.println("P47 Output (rat, car):         " + isAnagram("rat", "car"));         // Expected: false
    }
}