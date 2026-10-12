package DSA.Hashmap;

/**
 * ============================================================================
 * [44 / 52] - RANSOM NOTE (LeetCode 383)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given two strings `ransomNote` and `magazine`, return `true` if `ransomNote` can be
 * constructed by using the letters from `magazine`, and `false` otherwise.
 * Each letter in `magazine` can only be used once in `ransomNote`.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / HashMap Approach:
 *    - Using `HashMap<Character, Integer>` to count frequencies.
 *    - Valid, but incurs object allocation overhead and hash computation costs.
 *
 * 2. Optimal Array Frequency Table (O(m + n) Time | O(1) Space):
 *    - Since the inputs consist solely of lowercase English letters (`'a'` through `'z'`),
 *      a primitive integer array of size 26 vastly outperforms a `HashMap`.
 *    - **Step 1:** Early exit if `ransomNote.length() > magazine.length()` (it's physically impossible to construct).
 *    - **Step 2:** Count the character frequencies of `magazine` in the frequency array.
 *    - **Step 3:** Iterate through `ransomNote`, decrementing the respective character counts.
 *      If any count drops below zero, `magazine` lacks sufficient letters, so return `false` immediately.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(m + n) where `m` is the length of `ransomNote` and `n` is the length of `magazine`.
 * - Space: O(1) - Fixed integer array of size 26 requires constant auxiliary memory.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `ransomNote = "a"`, `magazine = "b"`   -> `false`
 * - `ransomNote = "aa"`, `magazine = "ab"`  -> `false`
 * - `ransomNote = "aa"`, `magazine = "aab"` -> `true`
 *
 * VISUAL DRY RUN (for `ransomNote = "a"`, `magazine = "b"`):
 *   - Length check: `1 > 1` (false).
 *   - Count frequency of magazine (`"b"`): `counts['b' - 'a']` becomes 1.
 *   - Scan ransomNote (`"a"`):
 *     - index for `'a'` (`counts[0]`) is decremented from 0 to -1.
 *     - `counts[0] < 0` triggers an immediate return of `false`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is an integer array of size 26 preferred over a `HashMap` in this context?
 *    (It eliminates object wrapping overhead, hash collision handling, and memory allocations,
 *    yielding maximum execution speed.)
 * 2. What edge cases are handled upfront?
 *    (When the ransom note is longer than the magazine, the length check short-circuits instantly.)
 */
public class P44_RansomNote {

    public static boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        int[] counts = new int[26];

        for (int i = 0; i < magazine.length(); i++) {
            counts[magazine.charAt(i) - 'a']++;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            int idx = ransomNote.charAt(i) - 'a';
            counts[idx]--;
            if (counts[idx] < 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("P44 Output (a, b):   " + canConstruct("a", "b"));     // Expected: false
        System.out.println("P44 Output (aa, ab):  " + canConstruct("aa", "ab"));   // Expected: false
        System.out.println("P44 Output (aa, aab): " + canConstruct("aa", "aab"));  // Expected: true
    }
}