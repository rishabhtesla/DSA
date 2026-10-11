package DSA.DP;

/**
 * ============================================================================
 * [08 / 30] - LONGEST PALINDROMIC SUBSEQUENCE (LeetCode 516)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given a string s, find the longest palindromic subsequence's length in s.
 * A subsequence is palindromic if it reads the same backward as forward.
 *
 *
 * 2. TWO METHODS TO SOLVE:
 * ----------------------------------------------------------------------------
 * Method A (Reduction to LCS):
 *   LPS(s) = LCS(s, reverse(s)).
 *   The longest common subsequence between a string and its reverse is 
 *   guaranteed to be the longest palindromic subsequence!
 *
 * Method B (Interval DP - More direct):
 *   Let dp[i][j] = length of LPS in substring s[i...j].
 *   Base Case:
 *     dp[i][i] = 1 (single character is always a palindrome of length 1).
 *   Transition:
 *     If s.charAt(i) == s.charAt(j):
 *       dp[i][j] = 2 + dp[i + 1][j - 1] (both endpoints match, add 2)
 *     Else:
 *       dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]) (drop left or drop right)
 *
 *
 * 3. 1D SPACE OPTIMIZATION FOR INTERVAL DP:
 * ----------------------------------------------------------------------------
 * Notice that dp[i][j] depends on:
 *   - dp[i + 1][j]     (cell below)
 *   - dp[i][j - 1]     (cell to the left)
 *   - dp[i + 1][j - 1] (bottom-left diagonal)
 *
 * By traversing `i` from bottom to top (n-1 down to 0) and `j` from `i + 1` up to `n - 1`,
 * we can collapse the grid into a single 1D array of size N!
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * s = "bbbab"
 * n = 5
 * Initial dp: [1, 1, 1, 1, 1] (every single character has length 1)
 *
 * i = 3 ('a'):
 *   j = 4 ('b'): 'a' != 'b' -> max(dp[4], dp[3]) = max(1, 1) = 1
 *
 * i = 2 ('b'):
 *   j = 3 ('a'): 'b' != 'a' -> max(dp[3], dp[2]) = max(1, 1) = 1
 *   j = 4 ('b'): 'b' == 'b' -> 2 + dp[3...3] = 2 + 1 = 3
 *
 * i = 1 ('b'):
 *   j = 2 ('b'): 'b' == 'b' -> 2 + 0 = 2
 *   j = 3 ('a'): 'b' != 'a' -> max(dp[3], dp[2]) = max(1, 2) = 2
 *   j = 4 ('b'): 'b' == 'b' -> 2 + dp[2...3] = 2 + 1 = 3
 *
 * i = 0 ('b'):
 *   j = 1 ('b'): 2
 *   j = 2 ('b'): 3
 *   j = 3 ('a'): 3
 *   j = 4 ('b'): 'b' == 'b' -> 2 + dp[1...3] = 2 + 2 = 4
 *
 * Result: dp[4] = 4 (Subsequence: "bbbb")
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N^2) where N = s.length().
 * - Space Complexity: O(N) using a single 1D array.
 */
public class P08_LongestPalindromicSubsequence {

    public static int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[] dp = new int[n];

        // Loop i from bottom to top
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1; // Base case: substring of length 1 is a palindrome of length 1
            int prevDiagonal = 0; // Tracks dp[i + 1][j - 1]

            for (int j = i + 1; j < n; j++) {
                int temp = dp[j]; // Save dp[i + 1][j]

                if (s.charAt(i) == s.charAt(j)) {
                    dp[j] = 2 + prevDiagonal;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }

                prevDiagonal = temp;
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        String s1 = "bbbab";
        System.out.println("P08 LPS of 'bbbab': " + longestPalindromeSubseq(s1)); // Expected: 4 ("bbbb")

        String s2 = "cbbd";
        System.out.println("P08 LPS of 'cbbd':  " + longestPalindromeSubseq(s2)); // Expected: 2 ("bb")
    }
}