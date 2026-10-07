package DSA.slidingwindow;

import java.util.Arrays;

/**
 * ============================================================================
 * [36 / 38] - LONGEST SUBSTRING WITHOUT REPEATING CHARACTERS (LeetCode 3)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a string s, find the length of the longest substring without duplicate characters.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Sliding Window with Last-Seen Index Lookup:
 *     Instead of checking frequencies with a Set and advancing `left` one character at a time,
 *     track the LAST SEEN index of each character directly in an integer array `lastSeen[128]`.
 *   - When encountering character `c` at index `right`:
 *     - If `c` was seen inside our current window (`lastSeen[c] >= left`), jump `left`
 *       directly to `lastSeen[c] + 1`. This skips all characters before the duplicate in O(1).
 *     - Update `lastSeen[c] = right`.
 *     - Maximize `maxLength = Math.max(maxLength, right - left + 1)`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass over string s.
 *   - Space: O(1) - Fixed ASCII direct-address table of size 128.
 */
public class P36_LongestSubstringWithoutRepeatingCharacters {

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;

        int[] lastSeen = new int[128];
        Arrays.fill(lastSeen, -1);

        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If character seen inside current active window, jump left past its prior position
            if (lastSeen[c] >= left) {
                left = lastSeen[c] + 1;
            }

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