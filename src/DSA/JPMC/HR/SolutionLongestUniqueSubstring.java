package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * ============================================================================
 * HACKERRANK QUESTION 13: Longest Unique Token Sequence
 * ============================================================================
 *
 * Problem Statement:
 * A security authorization gateway inspects incoming payload signatures encoded
 * as a string s. To prevent duplicate token injection, find the length of the
 * longest substring that contains no repeating characters.
 *
 * Input Format for Custom Testing:
 * - Line 1: String s.
 *
 * Constraints:
 * - 0 <= s.length() <= 10^5
 * - s consists of English letters, digits, symbols, and spaces (ASCII range 0 to 127).
 *
 * Example 1:
 * Input:
 *   abcabcbb
 * Output:
 *   3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input:
 *   bbbbb
 * Output:
 *   1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input:
 *   pwwkew
 * Output:
 *   3
 * Explanation: The answer is "wke", with the length of 3. Notice "pwke" is a subsequence, not a substring.
 *
 * ============================================================================
 * LOGIC & APPROACH (Optimized Sliding Window with Direct Address Array):
 * ============================================================================
 * 1. Data Structure:
 *    Since input characters fall within standard ASCII (0 to 127), use an
 *    integer array `lastSeen` of size 128 initialized to -1. This avoids
 *    HashMap hashing and boxing overhead.
 *
 * 2. Sliding Window:
 *    - Maintain a left pointer `left = 0`.
 *    - Expand the right pointer `right` from 0 to n - 1.
 *    - When encountering character c = s.charAt(right):
 *        If c was previously seen at index `lastSeen[c]` and `lastSeen[c] >= left`,
 *        a duplicate exists within the active window. Jump `left` forward to:
 *            left = lastSeen[c] + 1
 *    - Update `lastSeen[c] = right`.
 *    - Record the window size: `maxLength = max(maxLength, right - left + 1)`.
 *
 * Complexity:
 * - Time: O(n) single pass, each character visited once.
 * - Space: O(1) auxiliary space (fixed array of size 128).
 *
 * ============================================================================
 * DRY RUN (s = "pwwkew"):
 * ============================================================================
 * Initial: left = 0, maxLength = 0, lastSeen[*] = -1
 *
 * right = 0, ch = 'p': lastSeen['p'] = -1 -> left remains 0.
 *                      lastSeen['p'] = 0, window = [0..0] ("p"), max = 1
 * right = 1, ch = 'w': lastSeen['w'] = -1 -> left remains 0.
 *                      lastSeen['w'] = 1, window = [0..1] ("pw"), max = 2
 * right = 2, ch = 'w': lastSeen['w'] = 1 >= left(0) -> Duplicate!
 *                      left = lastSeen['w'] + 1 = 2
 *                      lastSeen['w'] = 2, window = [2..2] ("w"), max = 2
 * right = 3, ch = 'k': lastSeen['k'] = -1 -> left remains 2.
 *                      lastSeen['k'] = 3, window = [2..3] ("wk"), max = 2
 * right = 4, ch = 'e': lastSeen['e'] = -1 -> left remains 2.
 *                      lastSeen['e'] = 4, window = [2..4] ("wke"), max = 3
 * right = 5, ch = 'w': lastSeen['w'] = 2 >= left(2) -> Duplicate!
 *                      left = lastSeen['w'] + 1 = 3
 *                      lastSeen['w'] = 5, window = [3..5] ("kew"), max = 3
 *
 * Final Result: 3
 * ============================================================================
 */
public class SolutionLongestUniqueSubstring {

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int[] lastSeen = new int[128];
        Arrays.fill(lastSeen, -1);

        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);

            if (lastSeen[ch] >= left) {
                left = lastSeen[ch] + 1;
            }

            lastSeen[ch] = right;
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = bufferedReader.readLine();

        if (line == null) {
            System.out.println(0);
        } else {
            System.out.println(lengthOfLongestSubstring(line));
        }

        bufferedReader.close();
    }
}