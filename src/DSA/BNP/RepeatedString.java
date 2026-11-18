package DSA.BNP;

/**
 * ============================================================================
 * QUESTION 8: Repeated String (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * There is a string `s` of lowercase English letters that is repeated infinitely
 * many times. Given an integer `n`, find and print the number of letter 'a's in
 * the first `n` letters of the infinite string.
 *
 * INPUT FORMAT:
 * - The first line contains a single string `s`.
 * - The second line contains a single long integer `n`.
 *
 * CONSTRAINTS:
 * - 1 <= s.length <= 100
 * - 1 <= n <= 10^12
 * - For 25% of test cases, n <= 10^6.
 *
 * SAMPLE INPUT 0:
 *   s = "aba"
 *   n = 10
 *
 * SAMPLE OUTPUT 0:
 *   7
 *
 * EXPLANATION 0:
 *   The first n = 10 letters of the infinite string are "abaabaabaa".
 *   Because there are 7 'a's, we return 7.
 *
 * SAMPLE INPUT 1:
 *   s = "a"
 *   n = 1000000000000
 *
 * SAMPLE OUTPUT 1:
 *   1000000000000
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (s = "aba", n = 10):
 * ----------------------------------------------------------------------------
 * Because `n` can be up to 10^12, building the repeated string causes an
 * OutOfMemoryError and Time Limit Exceeded. Use integer division and modulo!
 *
 * 1. Let len = s.length() = 3.
 * 2. Count occurrences of 'a' in ONE full copy of `s` ("aba"):
 *      countInSingle = 2 (indices 0 and 2).
 * 3. Find how many complete copies of `s` fit inside `n` characters:
 *      fullRepeats = n / len = 10 / 3 = 3 complete copies ("aba" + "aba" + "aba").
 *      Contribution from full copies = fullRepeats * countInSingle = 3 * 2 = 6.
 * 4. Find the leftover prefix length using modulo:
 *      remainder = n % len = 10 % 3 = 1 character (s.substring(0, 1) -> "a").
 * 5. Count 'a's in the first `remainder` (1) characters of `s`:
 *      index 0 is 'a' -> remainderCount = 1.
 * 6. Total = 6 + 1 = 7.
 *
 * Critical Edge Case: Use `long` everywhere; `int` overflows past 2.14 * 10^9.
 *
 * Time Complexity:  O(L) where L = s.length() (at most 100 operations)
 * Space Complexity: O(1)
 * ============================================================================
 */
public class RepeatedString {

    public static long repeatedString(String s, long n) {
        if (s == null || s.isEmpty() || n <= 0) return 0L;

        long len = s.length();
        long countInSingle = 0;

        // Step 1: Count 'a' in a single full instance of `s`
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == 'a') {
                countInSingle++;
            }
        }

        // Step 2: Calculate full repeats and remaining prefix length
        long fullRepeats = n / len;
        long remainder = n % len;

        long totalCount = fullRepeats * countInSingle;

        // Step 3: Count 'a' in the partial leftover prefix of length `remainder`
        for (int i = 0; i < remainder; i++) {
            if (s.charAt(i) == 'a') {
                totalCount++;
            }
        }

        return totalCount;
    }

    public static void main(String[] args) {
        System.out.println("Sample 0 (\"aba\", 10): " + repeatedString("aba", 10L));
        // Expected: 7

        System.out.println("Sample 1 (\"a\", 1000000000000): " + repeatedString("a", 1_000_000_000_000L));
        // Expected: 1000000000000
    }
}