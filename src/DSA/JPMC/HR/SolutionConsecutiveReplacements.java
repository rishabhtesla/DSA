package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * ============================================================================
 * HACKERRANK QUESTION 3: Consecutive Character Replacement
 * ============================================================================
 *
 * Problem Statement:
 * Given a string s containing lowercase English letters, determine the minimum
 * number of character replacements required such that no two adjacent characters
 * in the string are identical.
 *
 * You may replace any character with any lowercase English letter.
 *
 * Input Format for Custom Testing:
 * - Line 1: String s
 *
 * Constraints:
 * - 1 <= s.length() <= 10^5
 * - s contains only lowercase English letters.
 *
 * Example:
 * Input:
 *   baaabbb
 * Output:
 *   2
 * Explanation:
 * - Streak of 'a's: "aaa" has length 3 -> floor(3 / 2) = 1 replacement (e.g. "aba").
 * - Streak of 'b's: "bbb" has length 3 -> floor(3 / 2) = 1 replacement (e.g. "bab").
 * - Total replacements = 1 + 1 = 2.
 *
 * ============================================================================
 * LOGIC & APPROACH (Run-Length Grouping):
 * ============================================================================
 * 1. Group consecutive identical characters into runs of length L.
 * 2. To eliminate all adjacent duplicates within a block of identical characters
 *    of length L, the minimum replacements needed is Math.floor(L / 2).
 *    - Length 2 ("aa") -> 1 replacement ("ax")
 *    - Length 3 ("aaa") -> 1 replacement ("axa")
 *    - Length 4 ("aaaa") -> 2 replacements ("axax")
 *    - Length 5 ("aaaaa") -> 2 replacements ("axaxa")
 * 3. Because the alphabet contains 26 characters, you can always pick an
 *    intermediate replacement character distinct from both left and right neighbors.
 * 4. Sum L / 2 across all distinct runs.
 *
 * Complexity:
 * - Time: O(n) single pass
 * - Space: O(1) auxiliary space
 *
 * ============================================================================
 * DRY RUN (s = "baaabbb"):
 * ============================================================================
 * i=0: 'b' -> runLength=1. i advances to 1.
 * i=1: 'a' -> matches i=2 ('a'), i=3 ('a'). runLength=3.
 *      replacements += 3 / 2 = 1.
 * i=4: 'b' -> matches i=5 ('b'), i=6 ('b'). runLength=3.
 *      replacements += 3 / 2 = 1.
 * Total replacements = 2.
 * ============================================================================
 */
public class SolutionConsecutiveReplacements {

    public static int minReplacements(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        int replacements = 0;
        int i = 0;
        int n = s.length();

        while (i < n) {
            int runLength = 1;
            while (i + 1 < n && s.charAt(i) == s.charAt(i + 1)) {
                runLength++;
                i++;
            }
            replacements += (runLength / 2);
            i++;
        }

        return replacements;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = bufferedReader.readLine();
        if (line != null) {
            System.out.println(minReplacements(line.trim()));
        }
        bufferedReader.close();
    }
}