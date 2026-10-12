package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [23 / 24] - FIND THE INDEX OF FIRST OCCURRENCE IN STRING (LeetCode 28 / KMP)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given two strings `needle` and `haystack`, return the index of the first
 * occurrence of `needle` in `haystack`, or `-1` if `needle` is not part of `haystack`.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach (O(N * M) Time):
 *    - Slide `needle` along `haystack` one character at a time, checking matching sequences.
 *    - Inefficient because it repeatedly rescans characters in `haystack` upon a mismatch.
 *
 * 2. Optimal Knuth-Morris-Pratt (KMP) Algorithm (O(N + M) Time | O(M) Space):
 *    - **The Core Insight:** When a mismatch occurs at `needle[j]`, we already know that
 *      the preceding segment `needle[0..j-1]` successfully matched `haystack`.
 *    - Instead of rewinding the haystack pointer `i` back and repeating work, we use a
 *      precomputed **LPS (Longest Proper Prefix which is also Suffix)** table.
 *    - `lps[k]` tells us the length of the longest matching prefix of the pattern that is
 *      also a suffix up to index `k`. On a mismatch, we can shift our pattern safely by
 *      jumping `j = lps[j - 1]` without ever moving `i` backward!
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(N + M) where `N` is haystack length and `M` is needle length.
 *           (Building LPS takes O(M); searching takes O(N) with zero backtracking of `i`).
 * - Space: O(M) - Auxiliary storage for the `lps` integer array of size `M`.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `haystack = "sadbutsad"`, `needle = "sad"` -> 0
 * - `haystack = "leetcode"`, `needle = "leeto"`     -> -1
 *
 * VISUAL DRY RUN (for `"sadbutsad"` / `"sad"`):
 *   - `lps` table for `"sad"` is `[0, 0, 0]` (no proper prefixes match suffixes).
 *   - `i` scans haystack while `j` tracks needle.
 *   - At `i = 0, 1, 2`, characters 's', 'a', 'd' match seamlessly (`j` reaches 3).
 *   - `j == needle.length()` is triggered -> returns `i - j` = `3 - 3 = 0` immediately,
 *     bypassing the second occurrence of "sad".
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is the haystack pointer `i` never decremented during a mismatch in KMP?
 *    (Because the LPS table mathematically guarantees that the skipped characters already match
 *    a valid prefix of the needle, making re-checking them redundant.)
 * 2. What edge cases are handled upfront?
 *    (An empty `needle` returns 0 instantly per problem constraints, and a `haystack` shorter
 *    than `needle` returns -1 immediately.)
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