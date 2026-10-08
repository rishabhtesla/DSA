package DSA.twopointers;

/**
 * ============================================================================
 * [31 / 34] - IS SUBSEQUENCE (LeetCode 392)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
 *   A subsequence of a string is a new string formed from the original string by deleting
 *   some (or none) characters without disturbing the relative positions of the remaining characters.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Greedy Two-Pointer Scanning:
 *     Pointer `i` for `s`, pointer `j` for `t`.
 *     At every character `t.charAt(j)`:
 *       If `s.charAt(i) == t.charAt(j)`, we found the next needed character of `s`!
 *       Increment `i`.
 *     Always increment `j`.
 *     If `i == s.length()`, all characters of `s` were found in relative order.
 *
 * COMPLEXITY:
 *   - Time:  O(len(t)) - At most one full pass through string t.
 *   - Space: O(1) - Two indices only.
 *
 *
 * EXAMPLE:
 *   The first main call asks whether "abc" is a subsequence of "ahbgdc"; expected true.
 *
 * VISUAL DRY RUN:
 *   s pointer j=0 scans t: a matches -> j=1; h,b are skipped; b matches -> j=2;
 *   g is skipped; c matches -> j=3==s.length(). Return true.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P31_IsSubsequence {

    public static boolean isSubsequence(String s, String t) {
        int i = 0; // Pointer for s
        int j = 0; // Pointer for t

        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }

        return i == s.length();
    }

    public static void main(String[] args) {
        System.out.println("P31 Output (abc in ahbgdc): " + isSubsequence("abc", "ahbgdc")); // Expected: true
        System.out.println("P31 Output (axc in ahbgdc): " + isSubsequence("axc", "ahbgdc")); // Expected: false
    }
}