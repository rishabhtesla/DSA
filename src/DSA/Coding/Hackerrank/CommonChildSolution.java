package DSA.Coding.Hackerrank;

import java.util.Scanner;

/**
 * ============================================================================
 * PROBLEM BREAKDOWN: "COMMON CHILD" (HackerRank) / LCS (LeetCode 1143)
 * ============================================================================
 *
 * 1. WHAT DOES "CHILD STRING" ACTUALLY MEAN?
 * ----------------------------------------------------------------------------
 * A string S_child is a "child" of S_parent if you can create S_child by
 * DELETING 0 or more characters from S_parent WITHOUT rearranging the order.
 *
 * In standard Computer Science terminology:
 *   "Child String" == "Subsequence"
 *   "Longest Common Child" == "Longest Common Subsequence (LCS)"
 *
 * NOTE THE DIFFERENCE:
 *   - Substring   -> MUST be contiguous (e.g., "ARR" is a substring of "HARRY").
 *   - Subsequence -> Can have gaps (e.g., "AY" is formed by deleting 'H', 'R', 'R'
 *                    from "HARRY" -> order is strictly preserved).
 *
 *
 * 2. COMPREHENSIVE TEST CASES & INTUITION:
 * ----------------------------------------------------------------------------
 * Case 1: s1 = "HARRY", s2 = "SALLY"
 *   - Potential common letters: 'A', 'Y'
 *   - Relative order in s1: 'A' appears before 'Y'.
 *   - Relative order in s2: 'A' appears before 'Y'.
 *   - Valid common child: "AY" (Length = 2).
 *
 * Case 2: s1 = "SHINCHAN", s2 = "NOHARAAA"
 *   - Look for matching letters appearing in identical left-to-right order:
 *     s1: S  [H]  I  [N]  C  H  [A]  N
 *     s2: [N]  O  [H]  A  R  [A]  A  A  -> Order mismatch between 'N' and 'H'.
 *     Valid matching subsequence preserving order: "NHA" (Length = 3).
 *
 * Case 3: s1 = "ABCD", s2 = "DCBA"
 *   - Characters are reversed. You cannot pick more than 1 character while
 *     preserving order (e.g., picking 'A', 'B', 'C', or 'D').
 *   - Output: 1
 *
 * Case 4: s1 = "ABC", s2 = "DEF"
 *   - Completely disjoint sets of characters.
 *   - Output: 0
 *
 *
 * 3. DYNAMIC PROGRAMMING RECURRENCE & DECISION TREE:
 * ----------------------------------------------------------------------------
 * Let DP[i][j] = length of the Longest Common Child using prefixes:
 *                s1[0 ... i-1] (first i characters of s1)
 *                s2[0 ... j-1] (first j characters of s2)
 *
 * At any pair of characters (s1[i-1], s2[j-1]), there are two possible decisions:
 *
 * Case A: Characters MATCH (s1[i - 1] == s2[j - 1])
 *   - Both characters contribute to the common child!
 *   - Formula: DP[i][j] = 1 + DP[i - 1][j - 1]   (Diagonal top-left + 1)
 *
 * Case B: Characters MISMATCH (s1[i - 1] != s2[j - 1])
 *   - Both characters CANNOT match at this step. We take the best option between:
 *       1. Drop current char of s1: DP[i - 1][j] (look at the cell above)
 *       2. Drop current char of s2: DP[i][j - 1] (look at the cell to the left)
 *   - Formula: DP[i][j] = Math.max(DP[i - 1][j], DP[i][j - 1])
 *
 * Base Cases:
 *   - DP[0][j] = 0 (comparing empty string against prefix of s2)
 *   - DP[i][0] = 0 (comparing prefix of s1 against empty string)
 *
 *
 * 4. WHY MEMORY OPTIMIZATION IS CRITICAL:
 * ----------------------------------------------------------------------------
 * Constraints: |s1|, |s2| <= 5000.
 *
 * - 2D Table:
 *     A table of size 5001 x 5001 int values requires:
 *     5001 * 5001 * 4 bytes ≈ 100,040,000 bytes ≈ 95.4 MB.
 *     In competitive programming platforms like HackerRank, a 100 MB allocation
 *     frequently triggers Memory Limit Exceeded (MLE) or heavy Garbage Collection pauses.
 *
 * - 1D Rolling Rows:
 *     To calculate `currRow[j]`, we ONLY need:
 *       1. `prevRow[j - 1]` (top-left diagonal)
 *       2. `prevRow[j]`     (cell directly above)
 *       3. `currRow[j - 1]` (cell to the left)
 *     We only need TWO rows of size (N + 1):
 *     2 * 5001 * 4 bytes ≈ 40 KB (a 2500x memory reduction!).
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time:  O(M * N) -> Nested loop visiting each character pair once.
 * - Space: O(N)     -> Two 1D arrays of size (N + 1) for the optimized version.
 *          O(M * N) -> Full table for the textbook 2D version.
 */
public class CommonChildSolution {

    /**
     * Memory-Optimized DP Solution (Recommended for HackerRank & Production)
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
                    // Mismatch: max of left (currRow[j - 1]) and top (prevRow[j])
                    currRow[j] = Math.max(currRow[j - 1], prevRow[j]);
                }
            }

            // Copy currRow to prevRow for the next character of s1
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