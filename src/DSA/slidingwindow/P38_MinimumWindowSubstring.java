package DSA.slidingwindow;

/**
 * ============================================================================
 * [38 / 38] - MINIMUM WINDOW SUBSTRING (LeetCode 76)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given two strings `s` and `t` of lengths `m` and `n` respectively, return the minimum
 * window substring of `s` such that every character in `t` (including duplicates)
 * is included in the window. If there is no such substring, return the empty string `""`.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach:
 *    - Check all possible substrings of `s` and verify if each contains all required character frequencies of `t`.
 *    - Inefficient: takes $O(m^2)$ or $O(m^3)$ time, resulting in Time Limit Exceeded (TLE).
 *
 * 2. Optimal Two-Array Sliding Window with Match Counter (O(m + n) Time | O(1) Space):
 *    - **The Core Insight:** Instead of checking validity from scratch on every window, we can dynamically
 *      track character frequencies using ASCII tables and a `formedChars` match counter.
 *    - **Step 1:** Populate `target[128]` with the required character counts from `t` and count how many
 *      `requiredDistinct` characters exist.
 *    - **Step 2 (Expand):** Expand the `right` pointer, incrementing `window[rightChar]`. If the current character's
 *      frequency matches its target requirement, increment `formedChars`.
 *    - **Step 3 (Contract):** Whenever `formedChars == requiredDistinct`, the window is valid. Record the minimum length candidate,
 *      then shrink from the `left` pointer while decrementing counts until the window breaks validity.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(m + n) - Linear pass to build counts for `t`, followed by `s` where each character is visited at most twice
 *            (once by `right`, once by `left`).
 * - Space: O(1) - Fixed ASCII frequency tables of size 128 require constant auxiliary space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `s = "ADOBECODEBANC"`, `t = "ABC"`
 * - Expected Minimum Window: `"BANC"`
 *
 * VISUAL DRY RUN (for `s = "ADOBECODEBANC"`, `t = "ABC"`):
 *   - Expand `right` until substring `"ADOBEC"` contains all targets (`A`, `B`, `C`). `formedChars == requiredDistinct` (3).
 *   - Shrink `left` to remove redundant prefix elements (`A`, `D`, `O`), refining the window to `"DOBEC"`.
 *   - Continue expanding and contracting; later, window `"BANC"` satisfies all conditions with length 4,
 *     which is smaller than previous valid windows. Returns `"BANC"`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we track `requiredDistinct` instead of matching total string lengths?
 *    (Tracking the count of *distinct* characters whose frequencies have been completely fulfilled prevents
 *    premature or delayed contraction when dealing with duplicate characters in `t`.)
 * 2. What edge cases are handled automatically?
 *    (If `s` is shorter than `t`, or if no valid window exists, `minLen` remains `Integer.MAX_VALUE`
 *    and the function correctly returns an empty string `""`.)
 */
public class P38_MinimumWindowSubstring {

    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // Build target frequency array for string t
        int[] target = new int[128];
        int requiredDistinct = 0;
        for (char c : t.toCharArray()) {
            if (target[c] == 0) {
                requiredDistinct++;
            }
            target[c]++;
        }

        int[] window = new int[128];
        int formedChars = 0;
        int left = 0;

        int minLen = Integer.MAX_VALUE;
        int minStart = 0;

        // Expand window to the right
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            window[rightChar]++;

            if (target[rightChar] > 0 && window[rightChar] == target[rightChar]) {
                formedChars++;
            }

            // Contract window from the left while it satisfies all criteria
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

        // Return empty string if no valid window was found, otherwise extract the substring
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