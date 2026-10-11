package DSA.DP;

/**
 * ============================================================================
 * [06 / 30] - LONGEST COMMON SUBSEQUENCE (LeetCode 1143 / HackerRank "Common Child")
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given two strings text1 and text2, return the length of their longest common 
 * subsequence. A subsequence is a new string generated from the original string 
 * with some characters (can be none) deleted without changing the relative order.
 * If there is no common subsequence, return 0.
 *
 *
 * 2. RECURRENCE & DECISION LOGIC:
 * ----------------------------------------------------------------------------
 * Let dp[i][j] represent the LCS length of prefixes text1[0...i-1] and text2[0...j-1].
 *
 * Base Case:
 *   dp[0][j] = 0 (empty prefix of text1)
 *   dp[i][0] = 0 (empty prefix of text2)
 *
 * Transitions:
 *   If text1.charAt(i - 1) == text2.charAt(j - 1):
 *     dp[i][j] = 1 + dp[i - 1][j - 1]  (Match! Take top-left diagonal + 1)
 *   Else:
 *     dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) (Mismatch: take max of top or left)
 *
 *
 * 3. 1D SPACE OPTIMIZATION TRICK:
 * ----------------------------------------------------------------------------
 * Since row i only depends on row i-1, we only need a 1D array.
 * However, when characters match, we need dp[i - 1][j - 1] (the diagonal from 
 * the previous row before it gets overwritten).
 * We store `dp[j - 1]` in a variable `prevDiagonal` before updating `dp[j]`.
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * text1 = "abcde", text2 = "ace"
 *
 * Initial 1D dp: [0, 0, 0, 0] (representing empty text1 against "", "a", "ac", "ace")
 *
 * Pass i=1 ('a'):
 *   j=1 ('a'=='a') -> match! 1 + 0 = 1
 *   j=2 ('a'!='c') -> max(dp[2], dp[1]) = max(0, 1) = 1
 *   j=3 ('a'!='e') -> max(dp[3], dp[2]) = max(0, 1) = 1
 *   dp: [0, 1, 1, 1]
 *
 * Pass i=2 ('b'):
 *   No match with 'a', 'c', 'e'. dp carries forward max values:
 *   dp: [0, 1, 1, 1]
 *
 * Pass i=3 ('c'):
 *   j=1 ('c'!='a') -> max(1, 0) = 1
 *   j=2 ('c'=='c') -> match! 1 + prevDiagonal(1) = 2
 *   j=3 ('c'!='e') -> max(dp[3], dp[2]) = max(1, 2) = 2
 *   dp: [0, 1, 2, 2]
 *
 * Pass i=4 ('d'):
 *   No match -> dp remains: [0, 1, 2, 2]
 *
 * Pass i=5 ('e'):
 *   j=1 ('e'!='a') -> 1
 *   j=2 ('e'!='c') -> 2
 *   j=3 ('e'=='e') -> match! 1 + prevDiagonal(2) = 3
 *   dp: [0, 1, 2, 3]
 *
 * Result: dp[3] = 3 ("ace")
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(M * N) where M = text1.length(), N = text2.length().
 * - Space Complexity: O(N) using a single 1D array and a scalar variable.
 */
public class P06_LongestCommonSubsequence {

    public static int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // Space optimization: ensure n is smaller to minimize array allocation
        if (m < n) {
            return longestCommonSubsequence(text2, text1);
        }

        int[] dp = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            char c1 = text1.charAt(i - 1);
            int prevDiagonal = 0; // Tracks dp[i - 1][j - 1]

            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // Save dp[i - 1][j] before overwriting

                if (c1 == text2.charAt(j - 1)) {
                    dp[j] = 1 + prevDiagonal;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }

                prevDiagonal = temp;
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";
        System.out.println("P06 LCS Length: " + longestCommonSubsequence(text1, text2)); // Expected: 3
    }
}