package DSA.BNP;

import java.util.*;

/**
 * ============================================================================
 * QUESTION 7: Sherlock and Anagrams (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * Two strings are anagrams of each other if the letters of one string can be
 * rearranged to form the other string. Given a string `s`, find the number of
 * pairs of substrings of the string that are anagrams of each other.
 *
 * INPUT FORMAT:
 * A single string `s` consisting of lowercase English letters.
 *
 * CONSTRAINTS:
 * - 2 <= s.length <= 100
 * - s contains only lowercase ASCII characters 'a'-'z'.
 *
 * SAMPLE INPUT 0:
 *   s = "abba"
 *
 * SAMPLE OUTPUT 0:
 *   4
 *
 * EXPLANATION 0:
 *   The list of all anagrammatic pairs in "abba" is:
 *   1. [s[0..0], s[3..3]] -> ("a", "a")
 *   2. [s[1..1], s[2..2]] -> ("b", "b")
 *   3. [s[0..1], s[2..3]] -> ("ab", "ba")
 *   4. [s[0..2], s[1..3]] -> ("abb", "bba")
 *   Total pairs = 4.
 *
 * SAMPLE INPUT 1:
 *   s = "abcd"
 *
 * SAMPLE OUTPUT 1:
 *   0
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (s = "abba"):
 * ----------------------------------------------------------------------------
 * Two substrings are anagrams if and only if they have the EXACT SAME character
 * frequency signature (or identical sorted characters).
 *
 * 1. Generate all substrings s[i..j] (0 <= i <= j < N).
 * 2. Compute a canonical 26-char frequency signature for each substring and
 *    store its count in a HashMap<String, Integer>.
 *
 * Tracing frequencies for s = "abba":
 * - Length 1 substrings:
 *     "a" (idx 0), "a" (idx 3) -> signature for 'a': count = 2
 *     "b" (idx 1), "b" (idx 2) -> signature for 'b': count = 2
 * - Length 2 substrings:
 *     "ab" (0..1), "ba" (2..3) -> signature for 'a':1,'b':1 -> count = 2
 *     "bb" (1..2)              -> signature for 'b':2       -> count = 1
 * - Length 3 substrings:
 *     "abb" (0..2), "bba" (1..3) -> signature for 'a':1,'b':2 -> count = 2
 * - Length 4 substring:
 *     "abba" (0..3)              -> signature for 'a':2,'b':2 -> count = 1
 *
 * 3. If a signature appears `k` times, the number of unordered pairs we can
 *    form from those `k` substrings is k * (k - 1) / 2:
 *      - 'a' (k=2):   2 * 1 / 2 = 1 pair
 *      - 'b' (k=2):   2 * 1 / 2 = 1 pair
 *      - 'ab' (k=2):  2 * 1 / 2 = 1 pair
 *      - 'abb' (k=2): 2 * 1 / 2 = 1 pair
 *    Total = 1 + 1 + 1 + 1 = 4 pairs.
 *
 * Time Complexity:  O(N^2) using sliding frequency updates per start index `i`
 * Space Complexity: O(N^2) to store substring signatures in the map
 * ============================================================================
 */
public class SherlockAndAnagrams {

    public static int sherlockAndAnagrams(String s) {
        Map<String, Integer> signatureCounts = new HashMap<>();
        int n = s.length();

        // Fix starting index `i` of substring
        for (int i = 0; i < n; i++) {
            int[] freq = new int[26]; // Reset frequency array for each new start `i`

            // Extend ending index `j` from `i` to `n - 1`
            for (int j = i; j < n; j++) {
                freq[s.charAt(j) - 'a']++;

                // Convert int[26] frequency table into a unique immutable map key
                String signature = Arrays.toString(freq);
                signatureCounts.put(signature, signatureCounts.getOrDefault(signature, 0) + 1);
            }
        }

        // Sum nC2 = count * (count - 1) / 2 for every anagram group
        int totalPairs = 0;
        for (int count : signatureCounts.values()) {
            if (count > 1) {
                totalPairs += (count * (count - 1)) / 2;
            }
        }

        return totalPairs;
    }

    public static void main(String[] args) {
        System.out.println("Sample 0 (\"abba\"): " + sherlockAndAnagrams("abba")); // Expected: 4
        System.out.println("Sample 1 (\"abcd\"): " + sherlockAndAnagrams("abcd")); // Expected: 0
        System.out.println("Sample 2 (\"kkkk\"): " + sherlockAndAnagrams("kkkk")); // Expected: 10
    }
}