package DSA.Coding.Hackerrank;

import java.util.Scanner;

/**
 * ============================================================================
 * 1. PROBLEM STATEMENT (HackerRank: "Repeated String")
 * ============================================================================
 * There is a string 's' of lowercase English letters that is repeated 
 * infinitely many times. Given an integer 'n', find and print the total number 
 * of occurrences of the letter 'a' in the first 'n' characters of the 
 * infinitely repeated string.
 *
 * Example 1:
 *   Input: s = "abcac", n = 10
 *   The repeated string is: "abcacabcac..."
 *   The first 10 characters are: "abcacabcac"
 *   Total occurrences of 'a': 4 (at indices 0, 3, 5, 8)
 *
 * Example 2:
 *   Input: s = "aba", n = 10
 *   The repeated string is: "abaabaabaa..."
 *   The first 10 characters are: "abaabaabaa"
 *   Total occurrences of 'a': 7
 *
 * Constraints:
 *   - 1 <= s.length() <= 100
 *   - 1 <= n <= 10^12
 *
 * ============================================================================
 * 2. LOGIC & MATHEMATICAL DERIVATION
 * ============================================================================
 * NAIVE APPROACH (Why it fails):
 *   Building the repeated string of length 'n' in memory causes an 
 *   OutOfMemoryError or Time Limit Exceeded (TLE) because 'n' can be up to 10^12.
 *   Even allocating 10^12 characters requires ~1 Terabyte of RAM.
 *
 * OPTIMAL APPROACH (O(|s|) Time, O(1) Space):
 *   Instead of expanding the string, we use integer division and modulo:
 *
 *   1. Count 'a' in one complete instance of string 's'.
 *      Let this count be: countA_Full
 *
 *   2. Find how many complete copies of 's' fit inside 'n':
 *      fullRepeats = n / s.length()
 *
 *   3. Find how many leftover characters remain:
 *      remainder = n % s.length()
 *
 *   4. Count 'a' in the prefix of 's' up to index (remainder - 1).
 *      Let this count be: countA_Remainder
 *
 *   5. Total 'a' = (fullRepeats * countA_Full) + countA_Remainder
 *
 *   Example with s = "aba", n = 10:
 *     - length = 3
 *     - countA_Full in "aba" = 2
 *     - fullRepeats = 10 / 3 = 3  ("aba aba aba" -> 3 * 2 = 6 'a's)
 *     - remainder   = 10 % 3 = 1  (prefix of length 1 is "a" -> 1 'a')
 *     - total = (3 * 2) + 1 = 7
 * ============================================================================
 */
public class RepeatedStringSolution {

    /**
     * Computes the number of occurrences of 'a' in the first 'n' characters.
     *
     * @param s The base string that repeats infinitely.
     * @param n The total prefix length to evaluate (up to 10^12).
     * @return Total count of the character 'a'.
     */
    public static long repeatedString(String s, long n) {
        long strLen = s.length();

        // Step 1: Count 'a' in the full base string
        long countInFullString = countOccurrencesOfA(s, strLen);

        // Optimization: If the string contains zero 'a's, return 0 immediately
        if (countInFullString == 0) {
            return 0;
        }

        // Step 2: Compute full string repetitions and the partial remainder length
        long fullRepeats = n / strLen;
        long remainderLength = n % strLen;

        // Step 3: Total 'a's contributed by the fully repeated strings
        long totalAs = fullRepeats * countInFullString;

        // Step 4: Add 'a's from the remaining prefix (if any)
        if (remainderLength > 0) {
            totalAs += countOccurrencesOfA(s, remainderLength);
        }

        return totalAs;
    }

    /**
     * Helper method to count occurrences of 'a' up to 'limit' characters of 's'.
     *
     * @param s     The string to inspect.
     * @param limit The number of characters from the start to check.
     * @return Number of times 'a' appears in s.substring(0, limit).
     */
    private static long countOccurrencesOfA(String s, long limit) {
        long count = 0;
        for (int i = 0; i < limit; i++) {
            if (s.charAt(i) == 'a') {
                count++;
            }
        }
        return count;
    }

    // Interactive Driver Method to demonstrate execution
    public static void main(String[] args) {
        // Built-in test cases
        System.out.println("--- Test Case 1 ---");
        String s1 = "aba";
        long n1 = 10L;
        System.out.println("Input: s = \"" + s1 + "\", n = " + n1);
        System.out.println("Output: " + repeatedString(s1, n1)); // Expected: 7

        System.out.println("\n--- Test Case 2 ---");
        String s2 = "a";
        long n2 = 1000000000000L; // 10^12
        System.out.println("Input: s = \"" + s2 + "\", n = " + n2);
        System.out.println("Output: " + repeatedString(s2, n2)); // Expected: 1000000000000

        System.out.println("\n--- Test Case 3 ---");
        String s3 = "ceeb";
        long n3 = 15L;
        System.out.println("Input: s = \"" + s3 + "\", n = " + n3);
        System.out.println("Output: " + repeatedString(s3, n3)); // Expected: 0

        // Custom user input
        System.out.println("\n--- Run Custom Test ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter string: ");
        String customStr = scanner.next();
        System.out.print("Enter n: ");
        long customN = scanner.nextLong();

        System.out.println("Occurrences of 'a': " + repeatedString(customStr, customN));
        scanner.close();
    }
}