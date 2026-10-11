package DSA.DP;

/**
 * ============================================================================
 * [09 / 30] - DISTINCT SUBSEQUENCES (LeetCode 115)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given two strings s and t, return the number of distinct subsequences of s 
 * which equals t.
 *
 * Example: s = "rabbbit", t = "rabbit" -> Output: 3
 * The three distinct ways to form "rabbit" correspond to removing each of the three 'b's.
 *
 *
 * 2. RECURRENCE & DECISION LOGIC:
 * ----------------------------------------------------------------------------
 * Let dp[i][j] = number of distinct subsequences of s[0...i-1] that match t[0...j-1].
 *
 * Base Cases:
 *   dp[i][0] = 1 (An empty string t can always be formed by deleting all characters of s).
 *   dp[0][j] = 0 for j > 0 (A non-empty string t cannot be formed from empty string s).
 *
 * Transitions:
 *   For every character pair s[i - 1] and t[j - 1]:
 *   
 *   Option 1: We can ALWAYS choose to SKIP s[i - 1] and see how many times t[0...j-1]
 *             was already formed by s[0...i-2]:
 *             dp[i - 1][j]
 *
 *   Option 2: If s.charAt(i - 1) == t.charAt(j - 1), we have the ADDITIONAL choice 
 *             to USE s[i - 1] to match t[j - 1]:
 *             dp[i - 1][j - 1]
 *
 *   Formula:
 *     If s.charAt(i - 1) == t.charAt(j - 1):
 *       dp[i][j] = dp[i - 1][j] + dp[i - 1][j - 1]
 *     Else:
 *       dp[i][j] = dp[i - 1][j]
 *
 *
 * 3. 1D SPACE OPTIMIZATION (BACKWARD SCAN):
 * ----------------------------------------------------------------------------
 * Notice that dp[j] depends on:
 *   - dp[j]     (from the previous row: skip current character)
 *   - dp[j - 1] (from the previous row: match current character)
 *
 * Just like 0/1 Knapsack, if we scan `j` BACKWARDS (from target length down to 1),
 * `dp[j - 1]` still contains the value from row `i - 1` and has not been overwritten yet!
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * s = "rabbbit", t = "rabbit"
 * Initial 1D dp of size 7: [1, 0, 0, 0, 0, 0, 0]  (dp[0] is always 1)
 *
 * 'r' matches t[0]='r':
 *   dp[1] += dp[0] -> dp = [1, 1, 0, 0, 0, 0, 0]
 * 'a' matches t[1]='a':
 *   dp[2] += dp[1] -> dp = [1, 1, 1, 0, 0, 0, 0]
 * 'b' (first) matches t[2]='b':
 *   dp[3] += dp[2] -> dp = [1, 1, 1, 1, 0, 0, 0]
 * 'b' (second) matches t[3]='b' and t[2]='b':
 *   dp[4] += dp[3] -> dp[4] = 0 + 1 = 1
 *   dp[3] += dp[2] -> dp[3] = 1 + 1 = 2
 *   dp = [1, 1, 1, 2, 1, 0, 0]
 * 'b' (third) matches t[3]='b' and t[2]='b':
 *   dp[4] += dp[3] -> dp[4] = 1 + 2 = 3
 *   dp[3] += dp[2] -> dp[3] = 2 + 1 = 3
 *   dp = [1, 1, 1, 3, 3, 0, 0]
 * 'i' matches t[4]='i':
 *   dp[5] += dp[4] -> dp[5] = 0 + 3 = 3
 * 't' matches t[5]='t':
 *   dp[6] += dp[5] -> dp[6] = 0 + 3 = 3
 *
 * Result: dp[6] = 3
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(M * N) where M = s.length(), N = t.length().
 * - Space Complexity: O(N) using a single 1D array.
 */
public class P09_DistinctSubsequences {

    public static int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();

        // Edge case: if t is longer than s, it cannot be a subsequence
        if (m < n) return 0;

        int[] dp = new int[n + 1];
        dp[0] = 1; // Base case: empty string t can always be formed (1 way)

        for (int i = 0; i < m; i++) {
            char sc = s.charAt(i);

            // Traverse backwards from n down to 1 (0/1 knapsack pattern)
            for (int j = n; j >= 1; j--) {
                if (sc == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        String s1 = "rabbbit", t1 = "rabbit";
        System.out.println("P09 Distinct Subsequences: " + numDistinct(s1, t1)); // Expected: 3

        String s2 = "babgbag", t2 = "bag";
        System.out.println("P09 Distinct Subsequences: " + numDistinct(s2, t2)); // Expected: 5
    }
}