package DSA.Coding.Hackerrank;

import java.util.Scanner;

/**
 * ============================================================================
 * 1. PROBLEM STATEMENT (HackerRank: "Common Child")
 * ============================================================================
 * A string is said to be a child of another string if it can be formed by 
 * deleting 0 or more characters from the other string. Letters cannot be 
 * rearranged.
 *
 * Given two strings of equal length, what is the length of the longest string 
 * that can be constructed such that it is a child of both?
 *
 * In computer science, this is identical to the classic:
 * "Longest Common Subsequence (LCS)" problem.
 *
 * Example 1:
 *   s1 = "HARRY"
 *   s2 = "SALLY"
 *   Common children of both strings: "AY" (length 2).
 *   Output: 2
 *
 * Example 2:
 *   s1 = "AA"
 *   s2 = "BB"
 *   Output: 0 (No common characters)
 *
 * Example 3:
 *   s1 = "SHINCHAN"
 *   s2 = "NOHARAAA"
 *   Longest common child: "NHA" (length 3).
 *   Output: 3
 *
 * Constraints:
 *   - 1 <= |s1|, |s2| <= 5000
 *   - All characters are uppercase English letters (A-Z).
 *
 * ============================================================================
 * 2. LOGIC & DYNAMIC PROGRAMMING RECURRENCE
 * ============================================================================
 * Let DP[i][j] represent the length of the Longest Common Child of prefixes
 * s1[0...i-1] and s2[0...j-1].
 *
 * Base Case:
 *   DP[0][j] = 0 (empty prefix of s1)
 *   DP[i][0] = 0 (empty prefix of s2)
 *
 * Transitions:
 *   - If s1.charAt(i - 1) == s2.charAt(j - 1):
 *       DP[i][j] = 1 + DP[i - 1][j - 1]
 *       (Characters match: extend the subsequence by 1)
 *
 *   - Else:
 *       DP[i][j] = max(DP[i - 1][j], DP[i][j - 1])
 *       (Skip current char of s1 OR skip current char of s2)
 *
 * ----------------------------------------------------------------------------
 * MEMORY OPTIMIZATION (Crucial for HackerRank 5000 x 5000 Constraint):
 * ----------------------------------------------------------------------------
 * A standard 5000 x 5000 int array requires:
 *   5000 * 5000 * 4 bytes ≈ 100 MB, which risks Memory Limit Exceeded in Java.
 *
 * Since row 'i' depends only on the previous row 'i - 1', we only need TWO rows:
 *   - prevRow[]
 *   - currRow[]
 * Space drops from O(M * N) to O(N) (5000 * 4 bytes ≈ 20 KB).
 * ============================================================================
 */
public class CommonChildSolution {

    /**
     * Memory-Optimized DP Solution (Recommended for HackerRank)
     * Time Complexity:  O(M * N) where M = s1.length(), N = s2.length()
     * Space Complexity: O(N) using only two 1D rows
     */
    public static int commonChild(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] prevRow = new int[n + 1];
        int[] currRow = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            char c1 = s1.charAt(i - 1);

            for (int j = 1; j <= n; j++) {
                char c2 = s2.charAt(j - 1);

                if (c1 == c2) {
                    // Match: diagonal value + 1
                    currRow[j] = 1 + prevRow[j - 1];
                } else {
                    // Mismatch: max of left and top
                    currRow[j] = Math.max(currRow[j - 1], prevRow[j]);
                }
            }

            // Copy currRow to prevRow for the next iteration
            System.arraycopy(currRow, 0, prevRow, 0, n + 1);
        }

        return currRow[n];
    }

    /**
     * Standard 2D Table DP Solution (Classic textbook LCS representation)
     * Time Complexity:  O(M * N)
     * Space Complexity: O(M * N)
     */
    public static int commonChild2D(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
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

    public static void main(String[] args) {

        /*
         * ====================================================================
         * TEST CASE 1 & DRY RUN: commonChild2D()
         * ====================================================================
         * Input: s1 = "HARRY", s2 = "SALLY"
         * Grid Dimensions: (5 + 1) x (5 + 1) = 6x6 Matrix initialized to 0
         *
         * Grid Setup:
         *          j:     0( )  1(S)  2(A)  3(L)  4(L)  5(Y)
         *   i=0( ):     [  0,    0,    0,    0,    0,    0  ]
         *
         * Row-by-Row Execution:
         *
         * i=1 ('H'):
         *   j=1 ('H' != 'S') -> max(dp[0][1], dp[1][0]) = max(0, 0) = 0
         *   j=2 ('H' != 'A') -> max(dp[0][2], dp[1][1]) = max(0, 0) = 0
         *   j=3 ('H' != 'L') -> max(dp[0][3], dp[1][2]) = max(0, 0) = 0
         *   j=4 ('H' != 'L') -> max(dp[0][4], dp[1][3]) = max(0, 0) = 0
         *   j=5 ('H' != 'Y') -> max(dp[0][5], dp[1][4]) = max(0, 0) = 0
         *   Row 1: [0, 0, 0, 0, 0, 0]
         *
         * i=2 ('A'):
         *   j=1 ('A' != 'S') -> max(dp[1][1], dp[2][0]) = max(0, 0) = 0
         *   j=2 ('A' == 'A') -> MATCH! 1 + dp[1][1] = 1 + 0 = 1
         *   j=3 ('A' != 'L') -> max(dp[1][3], dp[2][2]) = max(0, 1) = 1
         *   j=4 ('A' != 'L') -> max(dp[1][4], dp[2][3]) = max(0, 1) = 1
         *   j=5 ('A' != 'Y') -> max(dp[1][5], dp[2][4]) = max(0, 1) = 1
         *   Row 2: [0, 0, 1, 1, 1, 1]
         *
         * i=3 ('R'):
         *   'R' is not in "SALLY", so every cell carries forward max(top, left).
         *   Row 3: [0, 0, 1, 1, 1, 1]
         *
         * i=4 ('R'):
         *   'R' is not in "SALLY", carries forward previous values.
         *   Row 4: [0, 0, 1, 1, 1, 1]
         *
         * i=5 ('Y'):
         *   j=1 ('Y' != 'S') -> max(dp[4][1], dp[5][0]) = max(0, 0) = 0
         *   j=2 ('Y' != 'A') -> max(dp[4][2], dp[5][1]) = max(1, 0) = 1
         *   j=3 ('Y' != 'L') -> max(dp[4][3], dp[5][2]) = max(1, 1) = 1
         *   j=4 ('Y' != 'L') -> max(dp[4][4], dp[5][3]) = max(1, 1) = 1
         *   j=5 ('Y' == 'Y') -> MATCH! 1 + dp[4][4] = 1 + 1 = 2
         *   Row 5: [0, 0, 1, 1, 1, 2]
         *
         * Result: dp[5][5] = 2 (The common child is "AY")
         */
        System.out.println("=== TEST CASE 1: commonChild2D ===");
        String s1 = "HARRY";
        String s2 = "SALLY";
        int result2D = commonChild2D(s1, s2);
        System.out.println("Input: s1 = \"" + s1 + "\", s2 = \"" + s2 + "\"");
        System.out.println("Output length: " + result2D + " (Expected: 2 -> \"AY\")\n");

        /*
         * ====================================================================
         * TEST CASE 2 & DRY RUN: commonChild() [1D Array Space-Optimized]
         * ====================================================================
         * Input: s1 = "SHIN", s2 = "NOHA"
         * Array length: n + 1 = 5
         *
         * Initial State:
         *   prevRow = [0, 0, 0, 0, 0]
         *   currRow = [0, 0, 0, 0, 0]
         *
         * Pass 1: i = 1, c1 = 'S' (comparing 'S' with "NOHA")
         *   j=1 ('S' != 'N') -> max(curr[0], prev[1]) = max(0, 0) = 0
         *   j=2 ('S' != 'O') -> max(curr[1], prev[2]) = max(0, 0) = 0
         *   j=3 ('S' != 'H') -> max(curr[2], prev[3]) = max(0, 0) = 0
         *   j=4 ('S' != 'A') -> max(curr[3], prev[4]) = max(0, 0) = 0
         *   currRow becomes: [0, 0, 0, 0, 0]
         *   Copy to prevRow: [0, 0, 0, 0, 0]
         *
         * Pass 2: i = 2, c1 = 'H' (comparing 'H' with "NOHA")
         *   j=1 ('H' != 'N') -> max(curr[0], prev[1]) = max(0, 0) = 0
         *   j=2 ('H' != 'O') -> max(curr[1], prev[2]) = max(0, 0) = 0
         *   j=3 ('H' == 'H') -> MATCH! 1 + prevRow[2] = 1 + 0 = 1
         *   j=4 ('H' != 'A') -> max(curr[3], prev[4]) = max(1, 0) = 1
         *   currRow becomes: [0, 0, 0, 1, 1]
         *   Copy to prevRow: [0, 0, 0, 1, 1]
         *
         * Pass 3: i = 3, c1 = 'I' (comparing 'I' with "NOHA")
         *   No matches found, cells propagate maximums from left and above:
         *   currRow becomes: [0, 0, 0, 1, 1]
         *   Copy to prevRow: [0, 0, 0, 1, 1]
         *
         * Pass 4: i = 4, c1 = 'N' (comparing 'N' with "NOHA")
         *   j=1 ('N' == 'N') -> MATCH! 1 + prevRow[0] = 1 + 0 = 1
         *   j=2 ('N' != 'O') -> max(curr[1], prev[2]) = max(1, 0) = 1
         *   j=3 ('N' != 'H') -> max(curr[2], prev[3]) = max(1, 1) = 1
         *   j=4 ('N' != 'A') -> max(curr[3], prev[4]) = max(1, 1) = 1
         *   currRow becomes: [0, 1, 1, 1, 1]
         *
         * Result: currRow[4] = 1 (The common child is "H" or "N")
         */
        System.out.println("=== TEST CASE 2: commonChild (Memory Optimized) ===");
        String s3 = "SHIN";
        String s4 = "NOHA";
        int resultOptimized = commonChild(s3, s4);
        System.out.println("Input: s1 = \"" + s3 + "\", s2 = \"" + s4 + "\"");
        System.out.println("Output length: " + resultOptimized + " (Expected: 1 -> \"H\" or \"N\")\n");

        // Custom Console Run
        System.out.println("--- Run Custom Test ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter string 1: ");
        if (scanner.hasNext()) {
            String custom1 = scanner.next();
            System.out.print("Enter string 2: ");
            String custom2 = scanner.next();
            System.out.println("Result: " + commonChild(custom1, custom2));
        }
        scanner.close();
    }
}