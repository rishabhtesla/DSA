package DSA.slidingwindow;

/**
 * ============================================================================
 * [38 / 38] - MINIMUM WINDOW SUBSTRING (LeetCode 76)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given two strings s and t of lengths m and n respectively, return the minimum
 *   window substring of s such that every character in t (including duplicates)
 *   is included in the window. If there is no such substring, return the empty string "".
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Two-Array Sliding Window with Match Counter:
 *     Use `int[128] target` for required counts from t, and `int[128] window` for counts in s.
 *     Track `formedChars`: the number of distinct characters that currently meet
 *     their required frequency threshold.
 *   - Algorithm:
 *     1. Count frequency of characters in t and find `requiredDistinct`.
 *     2. Expand `right`:
 *        - Increment `window[c]`.
 *        - If `target[c] > 0 && window[c] == target[c]`, increment `formedChars`.
 *     3. Shrink `left` while `formedChars == requiredDistinct`:
 *        - Record minimum window candidate: `right - left + 1`.
 *        - Decrement `window[s.charAt(left)]`.
 *        - If `target[c] > 0 && window[c] < target[c]`, decrement `formedChars`.
 *        - Advance `left++`.
 *
 * COMPLEXITY:
 *   - Time:  O(m + n) - Linear pass through string t to build counts, then each character
 *            in s is visited at most twice (by right pointer and left pointer).
 *   - Space: O(1) - Fixed ASCII frequency tables of size 128.
 */
public class P38_MinimumWindowSubstring {

    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        int[] target = new int[128];
        int requiredDistinct = 0;
        for (char c : t.toCharArray()) {
            if (target[c] == 0) requiredDistinct++;
            target[c]++;
        }

        int[] window = new int[128];
        int formedChars = 0;
        int left = 0;

        int minLen = Integer.MAX_VALUE;
        int minStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            window[rightChar]++;

            if (target[rightChar] > 0 && window[rightChar] == target[rightChar]) {
                formedChars++;
            }

            // Shrink window from the left while it remains valid
            while (formedChars == requiredDistinct) {
                int currentLen = right - left + 1;
                if (currentLen < minLen) {
                    minLen = currentLen;
                    minStart = left;
                }

                char leftChar = s.charAt(left);
                window[leftChar]--;
                if (target[leftChar] > 0 && window[leftChar] < target[leftChar]) {
                    formedChars--;
                }
                left++;
            }
        }

        return (minLen == Integer.MAX_VALUE) ? "" : s.substring(minStart, minStart + minLen);
    }

    public static void main(String[] args) {
        String s1 = "ADOBECODEBANC";
        String t1 = "ABC";
        System.out.println("P38 Output (Test 1): \"" + minWindow(s1, t1) + "\""); // Expected: "BANC"

        String s2 = "a";
        String t2 = "a";
        System.out.println("P38 Output (Test 2): \"" + minWindow(s2, t2) + "\""); // Expected: "a"

        String s3 = "a";
        String t3 = "aa";
        System.out.println("P38 Output (Test 3): \"" + minWindow(s3, t3) + "\""); // Expected: ""
    }
}