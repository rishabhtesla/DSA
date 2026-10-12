package DSA.twopointers;

/**
 * ============================================================================
 * [31 / 34] - IS SUBSEQUENCE (LeetCode 392)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given two strings `s` and `t`, return `true` if `s` is a subsequence of `t`, or `false` otherwise.
 * A subsequence of a string is a new string formed from the original string by deleting
 * some (or none) characters without disturbing the relative positions of the remaining characters.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Combinatorial Approach:
 *    - Generate all possible subsequences of `t` and check if any match `s`.
 *    - Extremely inefficient ($O(2^n)$ time), leading to immediate Time Limit Exceeded (TLE).
 *
 * 2. Optimal Greedy Two-Pointer Scanning Approach (O(|t|) Time | O(1) Space):
 *    - **The Core Insight:** We can match characters greedily from left to right using two independent pointers:
 *      `i` for string `s` and `j` for string `t`.
 *    - **Step 1:** Iterate through string `t` using pointer `j`.
 *    - **Step 2:** If `s.charAt(i) == t.charAt(j)`, we have found the current required character of `s`,
 *      so advance pointer `i++`.
 *    - **Step 3:** Always increment `j++` to continue scanning through `t`.
 *    - **Step 4:** If pointer `i` reaches `s.length()`, it proves that all characters of `s` were found in their correct relative order.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(|t|) - At most a single linear pass through string `t` (where `|t|` is the length of `t`).
 * - Space: O(1) - Constant auxiliary space using only two integer pointers.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `s = "abc"`, `t = "ahbgdc"`
 * - Expected Result: `true`
 *
 * VISUAL DRY RUN (for `s = "abc"`, `t = "ahbgdc"`):
 *   - `i = 0` ('a'), `j` scans `t`: matches `'a'` at index 0 -> `i` becomes 1, `j` advances.
 *   - Skips `'h'`, `'b'`. Matches `'b'` at index 2 -> `i` becomes 2, `j` advances.
 *   - Skips `'g'`. Matches `'c'` at index 4 -> `i` becomes 3, loop terminates because `i == s.length()`.
 *   - Returns `true`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is a greedy approach optimal here?
 *    (Because order matters and no backtracking is required, matching each character as early as possible in `t`
 *    leaves the maximum remaining space for subsequent characters in `s`.)
 * 2. What happens if `s` is an empty string?
 *    (The loop terminates immediately with `i = 0 == s.length()`, correctly returning `true` since an empty string
 *    is always a valid subsequence of any string.)
 */
public class P31_IsSubsequence {

    public static boolean isSubsequence(String s, String t) {
        int i = 0; // Pointer for string s
        int j = 0; // Pointer for string t

        // Greedily match characters of s in t while maintaining relative order
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }

        // If pointer i reaches the end of s, all characters were successfully found in order
        return i == s.length();
    }

    public static void main(String[] args) {
        System.out.println("P31 Output (abc in ahbgdc): " + isSubsequence("abc", "ahbgdc")); // Expected: true
        System.out.println("P31 Output (axc in ahbgdc): " + isSubsequence("axc", "ahbgdc")); // Expected: false
    }
}