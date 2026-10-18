package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * ============================================================================
 * HACKERRANK QUESTION 2: Break a Palindrome
 * ============================================================================
 *
 * Problem Statement:
 * Given a palindromic string of lowercase English letters, replace exactly ONE
 * character with any lowercase English letter such that the resulting string:
 *   1. Is NOT a palindrome.
 *   2. Is lexicographically as small as possible.
 *
 * If it is impossible to satisfy these conditions (e.g., single-character string),
 * return the string "IMPOSSIBLE".
 *
 * Input Format for Custom Testing:
 * - Line 1: A string palindromeStr.
 *
 * Constraints:
 * - 1 <= palindromeStr.length() <= 10^5
 * - palindromeStr consists only of lowercase English letters and is guaranteed
 *   to be a palindrome initially.
 *
 * Example 1:
 * Input:
 *   abccba
 * Output:
 *   aaccba
 *
 * Example 2:
 * Input:
 *   a
 * Output:
 *   IMPOSSIBLE
 *
 * Example 3:
 * Input:
 *   aba
 * Output:
 *   abb
 *
 * ============================================================================
 * LOGIC & APPROACH (Greedy Replacement):
 * ============================================================================
 * 1. Corner Case:
 *    - Any string of length <= 1 cannot break palindromic symmetry by changing
 *      one character (it remains length 1, trivially a palindrome). Return "IMPOSSIBLE".
 *
 * 2. Lexicographically Smallest Goal:
 *    - Changing the earliest possible non-'a' character in the first half (0 to n/2 - 1)
 *      to 'a' guarantees the smallest lexicographical outcome.
 *    - Note: Never modify the middle element of an odd-length string (e.g., 'b' in "aba").
 *      Replacing the middle element from 'b' to 'a' leaves the string as "aaa",
 *      which is STILL a palindrome!
 *
 * 3. All 'a's in the first half (e.g., "aaaa" or "aabaa"):
 *    - If all characters in the first half are already 'a', the only way to make it
 *      non-palindromic with minimal lexicographical penalty is to change the very
 *      last character to 'b'.
 *
 * Complexity:
 * - Time: O(n)
 * - Space: O(n) to construct the result character array.
 *
 * ============================================================================
 * DRY RUN (palindromeStr = "aba"):
 * ============================================================================
 * n = 3, n / 2 = 1.
 * Loop index i = 0:
 * - chars[0] is 'a'. It is already 'a', continue.
 * Loop terminates.
 * Fallback: change last character (chars[2]) to 'b'.
 * Result: "abb".
 * "abb" is not a palindrome and is lexicographically minimal.
 * ============================================================================
 */
public class SolutionBreakPalindrome {

    public static String breakPalindrome(String palindromeStr) {
        if (palindromeStr == null || palindromeStr.length() <= 1) {
            return "IMPOSSIBLE";
        }

        char[] chars = palindromeStr.toCharArray();
        int n = chars.length;

        for (int i = 0; i < n / 2; i++) {
            if (chars[i] != 'a') {
                chars[i] = 'a';
                return new String(chars);
            }
        }

        chars[n - 1] = 'b';
        return new String(chars);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = bufferedReader.readLine();
        if (line != null) {
            System.out.println(breakPalindrome(line.trim()));
        }
        bufferedReader.close();
    }
}