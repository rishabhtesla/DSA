package DSA.Coding.Hackerrank;

import java.util.Scanner;

/**
 * ============================================================================
 * CRITICAL THINKING STUDY CLASS: Longest Common Subsequence (HackerRank "Common Child")
 * ============================================================================
 *
 * ----------------------------------------------------------------------------
 * STEP 1: UNDERSTAND THE REAL-WORLD ANALOGY
 * ----------------------------------------------------------------------------
 * Imagine you and a friend each have a shelf of numbered books in a row.
 * You want to see how many matching books you can both pick while:
 *   1. Preserving their left-to-right order.
 *   2. Skipping/throwing away any book that doesn't help match.
 *
 * You cannot swap books around. You can only keep or throw away.
 *
 * ----------------------------------------------------------------------------
 * STEP 2: THE "LAST CHARACTER" DECISION TREE
 * ----------------------------------------------------------------------------
 * Look at the ends of both strings: String A of length i, String B of length j.
 * There are only TWO possibilities for the last pair of characters:
 *
 *   CASE 1: The characters MATCH (A.last == B.last)
 *     - We MUST include this character! It contributes +1 to our score.
 *     - Now, we remove that character from both strings and solve the smaller
 *       problem: solve(i - 1, j - 1).
 *
 *   CASE 2: The characters DO NOT MATCH (A.last != B.last)
 *     - They cannot both be part of the same ending match.
 *     - We have a choice:
 *         a) Throw away A's last character and see what matches with B: solve(i - 1, j)
 *         b) Throw away B's last character and see what matches with A: solve(i, j - 1)
 *     - Since we want the LONGEST child, we take the maximum:
 *       max(solve(i - 1, j), solve(i, j - 1))
 *
 * ----------------------------------------------------------------------------
 * STEP 3: WHY WE USE A TABLE (DYNAMIC PROGRAMMING)
 * ----------------------------------------------------------------------------
 * If you write this recursively:
 *   solve("HARRY", "SALLY") branch out into:
 *     - solve("HARR", "SALLY") and solve("HARRY", "SALL")
 * Both of those branches will eventually ask: "What is solve('HARR', 'SALL')?"
 * Recomputing this over and over creates an exponential tree of 2^(N+M) steps!
 *
 * To fix this waste, we create a 2D grid where grid[i][j] stores the answer
 * to solve(i, j) so we compute each combination EXACTLY ONCE.
 *
 * ----------------------------------------------------------------------------
 * STEP 4: VISUAL DP TABLE DRY RUN (s1 = "CAT", s2 = "ACT")
 * ----------------------------------------------------------------------------
 *
 *         0(ø)   1('A')   2('C')   3('T')   <-- s2 (columns: j)
 *  0(ø)  [  0   |   0    |   0    |   0   ]
 *  1('C')[  0   |   0    |  (1)   |   1   ]  ('C' matches 'C' at col 2 -> 1 + dp[0][1] = 1)
 *  2('A')[  0   |  (1)   |   1    |   1   ]  ('A' matches 'A' at col 1 -> 1 + dp[1][0] = 1)
 *  3('T')[  0   |   1    |   1    |  (2)  ]  ('T' matches 'T' at col 3 -> 1 + dp[2][2] = 2)
 *   ^
 *  s1 (rows: i)
 *
 * Final Answer: dp[3][3] = 2 (The common letters are "CT" or "AT")
 * ============================================================================
 */
public class CriticalThinkingLCS {

    /**
     * Standard 2D DP Table (Best for building intuition)
     * Time Complexity:  O(M * N)
     * Space Complexity: O(M * N)
     */
    public static int longestCommonChild2D(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        // dp[i][j] stores the length of LCS for s1[0...i-1] and s2[0...j-1]
        int[][] dp = new int[m + 1][n + 1];

        // Fill row by row from top-left to bottom-right
        for (int i = 1; i <= m; i++) {
            char charFromS1 = s1.charAt(i - 1);

            for (int j = 1; j <= n; j++) {
                char charFromS2 = s2.charAt(j - 1);

                if (charFromS1 == charFromS2) {
                    // Match found: Take diagonal (result without these 2 chars) + 1
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // No match: Take the best outcome by ignoring either charFromS1 or charFromS2
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    /**
     * Memory-Optimized DP (Required when N and M are large, e.g., 5000)
     *
     * Notice: To calculate any cell in row 'i', we only ever look at:
     *   1. The cell directly above it: dp[i - 1][j]
     *   2. The cell diagonally above-left: dp[i - 1][j - 1]
     *   3. The cell directly to the left: dp[i][j - 1]
     *
     * We never look at row i-2, i-3, etc. Therefore, keeping all 5000 rows is wasted memory!
     * We only need two rows: 'prevRow' and 'currRow'.
     *
     * Time Complexity:  O(M * N)
     * Space Complexity: O(N)
     */
    public static int longestCommonChildOptimized(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] prevRow = new int[n + 1];
        int[] currRow = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            char charFromS1 = s1.charAt(i - 1);

            for (int j = 1; j <= n; j++) {
                char charFromS2 = s2.charAt(j - 1);

                if (charFromS1 == charFromS2) {
                    currRow[j] = 1 + prevRow[j - 1];
                } else {
                    currRow[j] = Math.max(currRow[j - 1], prevRow[j]);
                }
            }

            // Transfer currRow data to prevRow for the next turn
            System.arraycopy(currRow, 0, prevRow, 0, n + 1);
        }

        return currRow[n];
    }

    // Demonstrations and Tests
    public static void main(String[] args) {
        System.out.println("=== TEST RUN: Understand through Examples ===");

        // Example 1
        String s1 = "HARRY";
        String s2 = "SALLY";
        System.out.println("Input: s1 = \"" + s1 + "\", s2 = \"" + s2 + "\"");
        System.out.println("Common Child Length: " + longestCommonChildOptimized(s1, s2));
        System.out.println("Explanation: Matching letters in order are 'A' and 'Y' -> Length 2\n");

        // Example 2
        String s3 = "SHINCHAN";
        String s4 = "NOHARAAA";
        System.out.println("Input: s1 = \"" + s3 + "\", s2 = \"" + s4 + "\"");
        System.out.println("Common Child Length: " + longestCommonChildOptimized(s3, s4));
        System.out.println("Explanation: Matching letters in order are 'N', 'H', 'A' -> Length 3\n");

        // Interactive Testing
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Try your own inputs ---");
        System.out.print("Enter string 1: ");
        if (scanner.hasNext()) {
            String custom1 = scanner.next().toUpperCase();
            System.out.print("Enter string 2: ");
            String custom2 = scanner.next().toUpperCase();
            System.out.println("Length: " + longestCommonChildOptimized(custom1, custom2));
        }
        scanner.close();
    }
}