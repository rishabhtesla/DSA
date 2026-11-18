package DSA.BNP;

import java.util.*;

/**
 * ============================================================================
 * QUESTION 10: Special String Again (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * A string is said to be a "special string" if either of two conditions is met:
 *   1. All of the characters are the same, e.g., "aaa".
 *   2. All characters except the middle one are the same, e.g., "aadaa".
 * A special substring is any substring of a string which meets one of those
 * criteria. Given a string `s`, determine how many special substrings can be
 * formed from it.
 *
 * INPUT FORMAT:
 * - An integer `n`, the length of string `s`
 * - A string `s`
 *
 * CONSTRAINTS:
 * - 1 <= n <= 10^6
 * - All characters in `s` are lowercase English letters 'a'-'z'.
 *
 * SAMPLE INPUT 0:
 *   n = 7, s = "abcbaba"
 *
 * SAMPLE OUTPUT 0:
 *   10
 *
 * EXPLANATION 0:
 *   The 10 special substrings of "abcbaba" are:
 *   - 7 single characters: "a", "b", "c", "b", "a", "b", "a" (Rule 1)
 *   - 3 middle-pivot substrings: "bcb", "bab", "aba" (Rule 2)
 *   Total = 10.
 *
 * SAMPLE INPUT 1:
 *   n = 4, s = "aaaa"
 *
 * SAMPLE OUTPUT 1:
 *   10
 *
 * EXPLANATION 1:
 *   "a" (4), "aa" (3), "aaa" (2), "aaaa" (1) -> 4 + 3 + 2 + 1 = 10.
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (s = "abcbaba"):
 * ----------------------------------------------------------------------------
 * Because n <= 10^6, O(N^2) substring generation will time out.
 * Compress consecutive identical characters into Run-Length Pairs (char, count):
 *
 * Step 1: Build compressed list for "abcbaba":
 *   [('a', 1), ('b', 1), ('c', 1), ('b', 1), ('a', 1), ('b', 1), ('a', 1)]
 *   (For "aaaa", this would compress to [('a', 4)]).
 *
 * Step 2: Count Rule 1 substrings (all characters identical):
 *   A run of `k` identical characters forms k * (k + 1) / 2 special substrings.
 *   Each of the 7 runs has count = 1 -> 7 * (1 * 2 / 2) = 7.
 *
 * Step 3: Count Rule 2 substrings (same outer char around a single pivot):
 *   Look at every triple of adjacent runs (runs[i-1], runs[i], runs[i+1]):
 *   If middle run has count == 1 AND runs[i-1].ch == runs[i+1].ch:
 *     It forms min(runs[i-1].count, runs[i+1].count) valid substrings!
 *   Checking triples in "abcbaba":
 *   - ('a',1), ('b',1), ('c',1) -> 'a' != 'c' (0)
 *   - ('b',1), ('c',1), ('b',1) -> middle count==1 & 'b'=='b' -> +min(1,1) = +1 ("bcb")
 *   - ('c',1), ('b',1), ('a',1) -> 'c' != 'a' (0)
 *   - ('b',1), ('a',1), ('b',1) -> middle count==1 & 'b'=='b' -> +min(1,1) = +1 ("bab")
 *   - ('a',1), ('b',1), ('a',1) -> middle count==1 & 'a'=='a' -> +min(1,1) = +1 ("aba")
 *
 * Total = 7 + 1 + 1 + 1 = 10.
 *
 * Time Complexity:  O(N) single pass
 * Space Complexity: O(N) for run-length list
 * ============================================================================
 */
public class SpecialStringAgain {

    // Helper class representing a contiguous run of identical characters
    static class CharRun {
        char ch;
        long count;

        CharRun(char ch, long count) {
            this.ch = ch;
            this.count = count;
        }
    }

    public static long substrCount(int n, String s) {
        if (s == null || n == 0) return 0L;

        // Step 1: Run-Length Encode the string into contiguous character groups
        List<CharRun> runs = new ArrayList<>();
        int i = 0;
        while (i < n) {
            char current = s.charAt(i);
            long runLength = 0;
            while (i < n && s.charAt(i) == current) {
                runLength++;
                i++;
            }
            runs.add(new CharRun(current, runLength));
        }

        long totalSpecial = 0;

        // Step 2: Rule 1 - All characters identical within each single run
        for (CharRun run : runs) {
            totalSpecial += (run.count * (run.count + 1)) / 2;
        }

        // Step 3: Rule 2 - Single middle pivot character flanked by identical outer runs
        for (int j = 1; j < runs.size() - 1; j++) {
            CharRun prev = runs.get(j - 1);
            CharRun mid = runs.get(j);
            CharRun next = runs.get(j + 1);

            if (mid.count == 1 && prev.ch == next.ch) {
                totalSpecial += Math.min(prev.count, next.count);
            }
        }

        return totalSpecial;
    }

    public static void main(String[] args) {
        String sample0 = "abcbaba";
        System.out.println("Sample 0 (\"abcbaba\"): " + substrCount(sample0.length(), sample0));
        // Expected: 10

        String sample1 = "aaaa";
        System.out.println("Sample 1 (\"aaaa\"):    " + substrCount(sample1.length(), sample1));
        // Expected: 10

        String sample2 = "asasd";
        System.out.println("Sample 2 (\"asasd\"):   " + substrCount(sample2.length(), sample2));
        // Expected: 7 ("a", "s", "a", "s", "d", "asa", "sas")
    }
}