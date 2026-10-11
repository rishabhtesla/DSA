package DSA.Coding.Agoda;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/*
 * ============================================================================
 * QUESTION 2: PALINDROME COUNT
 * ============================================================================
 * Problem Statement:
 * A palindrome is a string that reads the same forwards and backwards, such as
 * 121 or tacocat[cite: 5]. A substring is a continuous sequence of characters
 * within a string[cite: 5]. Given a string s, return how many unique substrings
 * of s are palindromes[cite: 5].
 *
 * Example:
 *   s = "mokkori"[cite: 5]
 *   Palindromic substrings: [m, o, k, k, o, r, i, kk, okko][cite: 5]
 *   Distinct set: {m, o, k, r, i, kk, okko} -> Count = 7[cite: 5]
 *
 * Constraints:
 *   - 1 <= |s| <= 5000[cite: 5]
 *   - Each character s[i] is in the range ascii[a-z][cite: 5]
 * ============================================================================
 */

class PalindromeResult {

    /*
     * ------------------------------------------------------------------------
     * LOGIC EXPLANATION:
     * ------------------------------------------------------------------------
     * 1. Palindrome Centers:
     *    Every palindrome expands outwards symmetrically from a center:
     *      - Odd-length: centered on a single character (indices i, i)
     *      - Even-length: centered between two characters (indices i, i + 1)
     *    A string of length n has 2n - 1 possible centers.
     *
     * 2. Two-Pointer Expansion:
     *    For each center, expand pointers `left` and `right` outward as long as
     *    left >= 0, right < n, and s.charAt(left) == s.charAt(right).
     *    Each valid expansion step represents a newly discovered palindrome.
     *
     * 3. Distinct Palindromes:
     *    Insert every valid palindromic substring into a `HashSet<String>`.
     *    The Set inherently discards duplicate occurrences of the same substring
     *    (e.g., repeating individual characters 'k' and 'o' in "mokkori").
     *
     * 4. Complexity:
     *    - Time Complexity: O(n^2) expansion steps.
     *    - Space Complexity: O(total unique palindromic characters). A string 
     *      of length n contains at most n distinct palindromic substrings.
     *
     * ------------------------------------------------------------------------
     * DRY RUN:
     * ------------------------------------------------------------------------
     * Input: s = "mokkori", n = 7[cite: 5]
     * Set: {}
     *
     * Index 0 ('m'):
     *   - Odd (0, 0): "m" matches -> Set: {"m"}
     *   - Even (0, 1): 'm' != 'o' -> stop
     *
     * Index 1 ('o'):
     *   - Odd (1, 1): "o" matches -> Set: {"m", "o"}
     *   - Even (1, 2): 'o' != 'k' -> stop
     *
     * Index 2 ('k'):
     *   - Odd (2, 2): "k" matches -> Set: {"m", "o", "k"}
     *   - Even (2, 3):
     *       Step 1: s[2]=='k' == s[3]=='k' -> "kk" matches -> Set: {"m", "o", "k", "kk"}
     *       Step 2: expand to (1, 4): s[1]=='o' == s[4]=='o' -> "okko" matches -> Set: {"m", "o", "k", "kk", "okko"}
     *       Step 3: expand to (0, 5): s[0]=='m' != s[5]=='r' -> stop
     *
     * Index 3 ('k'):
     *   - Odd (3, 3): "k" matches -> already in Set
     *   - Even (3, 4): 'k' != 'o' -> stop
     *
     * Index 4 ('o'):
     *   - Odd (4, 4): "o" matches -> already in Set
     *   - Even (4, 5): 'o' != 'r' -> stop
     *
     * Index 5 ('r'):
     *   - Odd (5, 5): "r" matches -> Set: {"m", "o", "k", "kk", "okko", "r"}
     *   - Even (5, 6): 'r' != 'i' -> stop
     *
     * Index 6 ('i'):
     *   - Odd (6, 6): "i" matches -> Set: {"m", "o", "k", "kk", "okko", "r", "i"}
     *   - Even: right pointer out of bounds
     *
     * Final Set elements: {"m", "o", "k", "r", "i", "kk", "okko"}[cite: 5]
     * Final Set size: 7[cite: 5]
     * Return: 7[cite: 5]
     * ------------------------------------------------------------------------
     */
    public static int palindrome(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int n = s.length();
        Set<String> uniquePalindromes = new HashSet<>();

        for (int i = 0; i < n; i++) {
            // Odd length expansion
            expand(s, i, i, uniquePalindromes);

            // Even length expansion
            expand(s, i, i + 1, uniquePalindromes);
        }

        return uniquePalindromes.size();
    }

    private static void expand(String s, int left, int right, Set<String> uniquePalindromes) {
        int n = s.length();
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            uniquePalindromes.add(s.substring(left, right + 1));
            left--;
            right++;
        }
    }
}

public class PalindromicSubstringsSolution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String outputPath = System.getenv("OUTPUT_PATH");
        BufferedWriter bufferedWriter;
        if (outputPath != null) {
            bufferedWriter = new BufferedWriter(new FileWriter(outputPath));
        } else {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        String s = bufferedReader.readLine();

        int result = PalindromeResult.palindrome(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}