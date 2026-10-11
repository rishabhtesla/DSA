package DSA.DP;

/**
 * ============================================================================
 * [10 / 30] - WILDCARD MATCHING (LeetCode 44)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an input string `s` and a pattern `p`, implement wildcard pattern 
 * matching with support for '?' and '*' where:
 *   - '?' Matches any single character.
 *   - '*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the ENTIRE input string (not partial).
 *
 *
 * 2. RECURRENCE & BRANCHING LOGIC:
 * ----------------------------------------------------------------------------
 * Let dp[i][j] = boolean indicating if s[0...i-1] matches pattern p[0...j-1].
 *
 * Base Cases:
 *   dp[0][0] = true (empty string matches empty pattern).
 *   dp[i][0] = false for i > 0 (non-empty string cannot match empty pattern).
 *   dp[0][j] = dp[0][j - 1] if p.charAt(j - 1) == '*' (pattern of only '*' matches empty string).
 *
 * Transitions:
 *   Case 1: Characters match directly, OR pattern has '?':
 *     dp[i][j] = dp[i - 1][j - 1] (Take diagonal)
 *
 *   Case 2: Pattern has '*':
 *     The '*' wildcard can match:
 *       a) EMPTY sequence: dp[i][j - 1] (ignore the '*', look left)
 *       b) ONE OR MORE characters: dp[i - 1][j] (consume current char of s, keep '*' active)
 *     Formula:
 *       dp[i][j] = dp[i][j - 1] || dp[i - 1][j]
 *
 *   Case 3: Mismatch:
 *     dp[i][j] = false
 *
 *
 * 3. 1D SPACE OPTIMIZATION:
 * ----------------------------------------------------------------------------
 * Row i only depends on row i - 1:
 *   - diagonal: dp[i - 1][j - 1] (stored in `prevDiagonal`)
 *   - top: dp[i - 1][j] (current value in `dp[j]` before update)
 *   - left: dp[i][j - 1] (already updated `dp[j - 1]`)
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * s = "adceb", p = "*a*b"
 *
 * Base Row (i = 0, empty string):
 *   p[0]='*': dp[1] = true
 *   p[1]='a': dp[2] = false
 *   p[2]='*': dp[3] = dp[2] = false
 *   p[3]='b': dp[4] = false
 *   dp: [T, T, F, F, F]
 *
 * Row 1 ('a'):
 *   j=1 ('*') -> dp[0] || dp[1] = F || T = T
 *   j=2 ('a'=='a') -> prevDiagonal(T) = T
 *   j=3 ('*') -> dp[2] || dp[3] = T || F = T
 *   j=4 ('b'!='a') -> F
 *   dp: [F, T, T, T, F]
 *
 * ... and so on until last row ('b') where '*' and 'b' successfully align to true.
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(M * N) where M = s.length(), N = p.length().
 * - Space Complexity: O(N) using a single 1D array.
 */
public class P10_WildcardMatching {

    public static boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[] dp = new boolean[n + 1];
        dp[0] = true; // Base case: empty string matches empty pattern

        // Initialize base row: leading '*' characters can match empty string
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[j] = dp[j - 1];
            } else {
                break;
            }
        }

        for (int i = 1; i <= m; i++) {
            char sc = s.charAt(i - 1);
            boolean prevDiagonal = dp[0]; // Stores dp[i - 1][0]
            dp[0] = false;                // Non-empty string cannot match empty pattern

            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                boolean temp = dp[j];     // Save dp[i - 1][j] before overwriting

                if (pc == '*') {
                    // Match empty sequence (dp[j - 1]) OR match one character (dp[j])
                    dp[j] = dp[j - 1] || dp[j];
                } else if (pc == '?' || pc == sc) {
                    // Characters match or '?' wildcard
                    dp[j] = prevDiagonal;
                } else {
                    dp[j] = false;
                }

                prevDiagonal = temp;
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println("P10 Match 'aa' with 'a':     " + isMatch("aa", "a"));     // Expected: false
        System.out.println("P10 Match 'aa' with '*':     " + isMatch("aa", "*"));     // Expected: true
        System.out.println("P10 Match 'cb' with '?a':    " + isMatch("cb", "?a"));    // Expected: false
        System.out.println("P10 Match 'adceb' with '*a*b': " + isMatch("adceb", "*a*b")); // Expected: true
    }
}