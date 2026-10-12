package DSA.slidingwindow;

import java.util.Arrays;

/**
 * ============================================================================
 * [36 / 38] - LONGEST SUBSTRING WITHOUT REPEATING CHARACTERS (LeetCode 3)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a string `s`, find the length of the longest substring without duplicate characters.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / HashSet Approach:
 *    - Use a HashSet to track characters in the current window and shrink `left` one step at a time
 *      whenever a duplicate is encountered.
 *    - Valid, but can take up to $2n$ steps as `left` creeps forward incrementally.
 *
 * 2. Optimal Sliding Window with Last-Seen Index Lookup (O(n) Time | O(1) Space):
 *    - **The Core Insight:** Instead of advancing `left` incrementally, we can track the **last seen index**
 *      of each character directly using an integer lookup array (`lastSeen[128]` initialized to `-1`).
 *    - **Step 1:** When encountering character `c` at index `right`, check if it was seen *inside* our current window
 *      (`lastSeen[c] >= left`).
 *    - **Step 2:** If it is inside the window, jump `left` **directly** to `lastSeen[c] + 1` in O(1) time,
 *      effectively skipping all characters before the duplicate in one leap.
 *    - **Step 3:** Update `lastSeen[c] = right` and maximize the window length: `maxLength = Math.max(maxLength, right - left + 1)`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass over string `s`, with `left` pointer jumping forward monotonically.
 * - Space: O(1) - Fixed ASCII direct-address lookup table of size 128 requires constant auxiliary space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - String: `"abcabcbb"`
 * - Expected Longest Length: `3` (corresponding to substring `"abc"`)
 *
 * VISUAL DRY RUN (for `"abcabcbb"`):
 *   - Window expands: `a`, `b`, `c` (length = 3).
 *   - At next `'a'` (`right = 3`), `lastSeen['a']` is `0`, which is `>= left` (`0`).
 *   - `left` jumps directly to `0 + 1 = 1`. Window becomes `"bca"`.
 *   - Process continues similarly; peak maximum length remains `3`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why must we check `lastSeen[c] >= left` instead of just checking if `lastSeen[c] != -1`?
 *    (Because a character might have appeared earlier in the string *outside* our current active window
 *    (to the left of `left`). If we didn't check the boundary, an old character index would incorrectly pull
 *    `left` backwards, breaking the active window invariant.)
 * 2. Why is an array of size 128 sufficient for this problem?
 *    (Standard ASCII characters span values from 0 to 127, making a fixed 128-element integer array
 *    faster and more memory-efficient than a generic HashMap wrapper.)
 */
public class P36_LongestSubstringWithoutRepeatingCharacters {

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;

        // Direct-address table to store the last seen index of each ASCII character
        int[] lastSeen = new int[128];
        Arrays.fill(lastSeen, -1);

        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If the character was seen inside the current window, jump left past its prior position
            if (lastSeen[c] >= left) {
                left = lastSeen[c] + 1;
            }

            // Update the last seen index for the character and record the max window length
            lastSeen[c] = right;
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println("P36 Output (abcabcbb): " + lengthOfLongestSubstring("abcabcbb")); // Expected: 3 ("abc")
        System.out.println("P36 Output (bbbbb):    " + lengthOfLongestSubstring("bbbbb"));    // Expected: 1 ("b")
        System.out.println("P36 Output (pwwkew):   " + lengthOfLongestSubstring("pwwkew"));   // Expected: 3 ("wke")
    }
}