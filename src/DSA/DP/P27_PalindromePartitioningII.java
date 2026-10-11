package DSA.DP;

import java.util.Arrays;

/**
 * ============================================================================
 * [27 / 30] - PALINDROME PARTITIONING II (LeetCode 132)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given a string `s`, partition `s` such that every substring of the partition 
 * is a palindrome.
 * Return the MINIMUM CUTS needed for a palindrome partitioning of `s`.
 *
 * Example:
 *   s = "aab" -> Can be partitioned as ["aa", "b"] with 1 cut. Output: 1.
 *
 *
 * 2. RECURRENCE & PRECOMPUTATION STRATEGY:
 * ----------------------------------------------------------------------------
 * Step 1: Precompute Palindrome Table in O(N^2)
 *   Let isPal[i][j] = true if substring s[i...j] is a palindrome.
 *   isPal[i][j] is true if:
 *     (s.charAt(i) == s.charAt(j)) AND (j - i <= 2 || isPal[i + 1][j - 1])
 *
 * Step 2: 1D Minimum Cuts DP
 *   Let dp[i] = minimum cuts needed for prefix s[0...i].
 *
 *   Transition:
 *     If s[0...i] is already a palindrome:
 *       dp[i] = 0 (No cuts needed!)
 *     Otherwise, try all possible cut points `j` (1 <= j <= i):
 *       If s[j...i] is a palindrome, we can cut right before `j`:
 *       dp[i] = min(dp[i], 1 + dp[j - 1])
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * s = "aab"
 *
 * Step 1: Palindromes
 *   "a" (0..0) = T, "aa" (0..1) = T, "aab" (0..2) = F
 *   "a" (1..1) = T, "ab" (1..2) = F
 *   "b" (2..2) = T
 *
 * Step 2: 1D Cuts DP
 *   i = 0 ("a"):
 *     isPal[0][0] is true -> dp[0] = 0
 *
 *   i = 1 ("aa"):
 *     isPal[0][1] is true -> dp[1] = 0
 *
 *   i = 2 ("aab"):
 *     isPal[0][2] is false.
 *     j = 1: s[1..2] = "ab" (not pal)
 *     j = 2: s[2..2] = "b" (is pal) -> 1 + dp[1] = 1 + 0 = 1
 *     dp[2] = 1
 *
 * Result: dp[2] = 1 cut
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N^2) - O(N^2) palindrome matrix + O(N^2) 1D transitions.
 * - Space Complexity: O(N^2) - Boolean table for palindrome checks.
 */
public class P27_PalindromePartitioningII {

    public static int minCut(String s) {
        int n = s.length();
        if (n <= 1) return 0;

        // Step 1: Precompute isPal table
        boolean[][] isPal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || isPal[i + 1][j - 1])) {
                    isPal[i][j] = true;
                }
            }
        }

        // Step 2: Compute min cuts for each prefix
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            if (isPal[0][i]) {
                dp[i] = 0; // Entire prefix is a palindrome
            } else {
                dp[i] = i; // Max cuts is cutting every single character (i cuts)
                for (int j = 1; j <= i; j++) {
                    if (isPal[j][i]) {
                        dp[i] = Math.min(dp[i], 1 + dp[j - 1]);
                    }
                }
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        System.out.println("P27 Min Cuts 'aab':   " + minCut("aab"));   // Expected: 1 (["aa", "b"])
        System.out.println("P27 Min Cuts 'a':     " + minCut("a"));     // Expected: 0
        System.out.println("P27 Min Cuts 'ababb': " + minCut("ababb")); // Expected: 1 (["aba", "bb"])
    }
}