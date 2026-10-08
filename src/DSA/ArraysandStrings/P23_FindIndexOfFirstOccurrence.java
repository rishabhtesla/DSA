package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [23 / 24] - FIND THE INDEX OF FIRST OCCURRENCE IN STRING (LeetCode 28 / KMP)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given two strings needle and haystack, return the index of the first
 *   occurrence of needle in haystack, or -1 if needle is not part of haystack.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Brute Force: Slide needle along haystack and check match -> O(N * M) time.
 *   - Knuth-Morris-Pratt (KMP) Algorithm -> O(N + M) time:
 *     When a mismatch occurs at needle[j], we know needle[0..j-1] already matched
 *     haystack[i-j..i-1].
 *     Instead of rewinding haystack pointer `i`, we use a precomputed LPS array
 *     (Longest Proper Prefix which is also Suffix).
 *     `lps[k]` tells us the longest matching prefix length for needle[0..k].
 *     On mismatch, reset `j = lps[j - 1]` without backtracking `i`.
 *
 * COMPLEXITY:
 *   - Time:  O(N + M) where N = haystack.length(), M = needle.length().
 *   - Space: O(M) for the LPS array.
 *
 *
 * EXAMPLE:
 *   The first main input is haystack="sadbutsad", needle="sad"; expected index is 0.
 *
 * VISUAL DRY RUN:
 *   KMP builds lps=[0,0,0]. At text index 0, window s-a-d matches pattern indices
 *   0,1,2; j reaches 3, so return i-j+1 = 0 without scanning the later "sad".
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P23_FindIndexOfFirstOccurrence {

    public static int strStr(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        if (haystack.length() < needle.length()) return -1;

        int[] lps = buildLPS(needle);
        int i = 0; // Pointer in haystack
        int j = 0; // Pointer in needle

        while (i < haystack.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == needle.length()) {
                    return i - j; // Match found at starting index i - j
                }
            } else {
                if (j > 0) {
                    j = lps[j - 1]; // Fallback in needle using LPS
                } else {
                    i++;
                }
            }
        }

        return -1;
    }

    // Precomputes the Longest Prefix Suffix (LPS) table
    private static int[] buildLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length > 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    public static void main(String[] args) {
        System.out.println("P23 Output (sadbutsad, sad): " + strStr("sadbutsad", "sad")); // Expected: 0
        System.out.println("P23 Output (leetcode, leeto):  " + strStr("leetcode", "leeto")); // Expected: -1
    }
}