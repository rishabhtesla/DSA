package DSA.DP;

/**
 * ============================================================================
 * [07 / 30] - EDIT DISTANCE (LeetCode 72)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given two strings word1 and word2, return the minimum number of operations 
 * required to convert word1 to word2.
 * You have the following three operations permitted on a word:
 *   - Insert a character
 *   - Delete a character
 *   - Replace a character
 *
 *
 * 2. RECURRENCE & THREE-WAY CHOICE LOGIC:
 * ----------------------------------------------------------------------------
 * Let dp[i][j] = minimum operations to convert word1[0...i-1] to word2[0...j-1].
 *
 * Base Cases:
 *   dp[0][j] = j (converting empty word1 to word2 of length j takes j inserts)
 *   dp[i][0] = i (converting word1 of length i to empty word2 takes i deletes)
 *
 * Transitions:
 *   If word1.charAt(i - 1) == word2.charAt(j - 1):
 *     dp[i][j] = dp[i - 1][j - 1]  (No operation needed, carry diagonal forward)
 *   Else:
 *     dp[i][j] = 1 + min of:
 *       1. dp[i][j - 1]      -> INSERT (add word2's character into word1)
 *       2. dp[i - 1][j]      -> DELETE (remove word1's character)
 *       3. dp[i - 1][j - 1]  -> REPLACE (replace word1's char with word2's char)
 *
 *
 * 3. 1D SPACE OPTIMIZATION:
 * ----------------------------------------------------------------------------
 * We maintain a 1D array of size N + 1.
 * Like LCS, we use a `prevDiagonal` variable to remember dp[i - 1][j - 1]
 * before `dp[j]` is replaced.
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * word1 = "horse", word2 = "ros"
 *
 * Base row (i = 0): dp = [0, 1, 2, 3]
 *
 * Row 1 ('h'):
 *   j=1 ('h'!='r') -> 1 + min(dp[1]=1, dp[0]=0, diag=0) = 1
 *   j=2 ('h'!='o') -> 1 + min(dp[2]=2, dp[1]=1, diag=1) = 2
 *   j=3 ('h'!='s') -> 1 + min(dp[3]=3, dp[2]=2, diag=2) = 3
 *   dp: [1, 1, 2, 3]
 *
 * Row 2 ('o'):
 *   j=1 ('o'!='r') -> 1 + min(1, 2, 1) = 2
 *   j=2 ('o'=='o') -> diag = 1
 *   j=3 ('o'!='s') -> 1 + min(3, 1, 2) = 2
 *   dp: [2, 2, 1, 2]
 *
 * ... continuing down to row 5 ('e'):
 * Final row becomes: [5, 3, 2, 3] -> Answer = 3
 * Operations: "horse" -> "rorse" (replace 'h' with 'r') -> "rose" (remove 'r') -> "ros" (remove 'e').
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(M * N)
 * - Space Complexity: O(N) using a single 1D array.
 */
public class P07_EditDistance {

    public static int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[] dp = new int[n + 1];

        // Base case: transforming empty word1 to word2 requires j insertions
        for (int j = 0; j <= n; j++) {
            dp[j] = j;
        }

        for (int i = 1; i <= m; i++) {
            int prevDiagonal = dp[0]; // Stores dp[i - 1][0]
            dp[0] = i;                // Base case: transforming word1[0...i-1] to empty word2 requires i deletions

            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // Save dp[i - 1][j]

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[j] = prevDiagonal;
                } else {
                    int insertOp  = dp[j - 1];
                    int deleteOp  = dp[j];
                    int replaceOp = prevDiagonal;

                    dp[j] = 1 + Math.min(replaceOp, Math.min(insertOp, deleteOp));
                }

                prevDiagonal = temp;
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        String word1 = "horse";
        String word2 = "ros";
        System.out.println("P07 Edit Distance: " + minDistance(word1, word2)); // Expected: 3

        String w1 = "intention";
        String w2 = "execution";
        System.out.println("P07 Edit Distance: " + minDistance(w1, w2)); // Expected: 5
    }
}