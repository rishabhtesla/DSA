package DSA.Coding.Hackerrank;

import java.util.Scanner;

public class CommonChildSolver {

    /**
     * Easy explanation:
     * We compare the strings one character at a time.
     *
     * If the current characters match:
     *     dp[i][j] = dp[i - 1][j - 1] + 1
     * Because we found one more matching character.
     *
     * If they do not match:
     *     dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
     * Because we can skip a character from either string.
     *
     * This is the normal 2D Dynamic Programming solution.
     * Time: O(m * n)
     * Space: O(m * n)
     */
    public static int longestCommonSubsequence2D(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        // dp[i][j] = longest common subsequence length for s1[0..i-1] and s2[0..j-1]
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    /**
     * This version saves memory.
     *
     * We do not need the whole 2D table.
     * We only need the previous row and the current row.
     *
     * Why? Because every cell only depends on:
     * - the cell above
     * - the cell to the left
     * - the diagonal cell
     *
     * So we keep only 2 rows.
     * Time: O(m * n)
     * Space: O(n)
     */
    public static int longestCommonSubsequenceOptimized(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] previous = new int[n + 1];
        int[] current = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Match found: take the diagonal answer and add 1
                    current[j] = previous[j - 1] + 1;
                } else {
                    // No match: take the better option
                    current[j] = Math.max(current[j - 1], previous[j]);
                }
            }

            // Move current row to previous row for the next round
            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first string: ");
        String s1 = scanner.nextLine().trim();

        System.out.print("Enter second string: ");
        String s2 = scanner.nextLine().trim();

        int result = longestCommonSubsequenceOptimized(s1, s2);
        System.out.println("Longest common subsequence length: " + result);

        scanner.close();
    }
}
