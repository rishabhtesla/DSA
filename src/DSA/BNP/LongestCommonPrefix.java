package DSA.BNP;

import java.util.*;

/**
 * ============================================================================
 * QUESTION 4: Longest Common Prefix (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * Write a function to find the longest common prefix string amongst an array
 * of strings `strs`. If there is no common prefix, return an empty string "".
 *
 * INPUT FORMAT:
 * An array of strings `strs` of length N.
 *
 * CONSTRAINTS:
 * - 1 <= strs.length <= 200
 * - 0 <= strs[i].length <= 200
 * - strs[i] consists of only lowercase English letters (if non-empty).
 *
 * SAMPLE INPUT 0:
 *   strs = ["flower", "flow", "flight"]
 *
 * SAMPLE OUTPUT 0:
 *   "fl"
 *
 * EXPLANATION 0:
 *   All three strings share the starting characters 'f' and 'l'. At index 2,
 *   "flower" and "flow" have 'o', while "flight" has 'i' -> mismatch!
 *   Therefore, the longest common prefix is "fl".
 *
 * SAMPLE INPUT 1:
 *   strs = ["dog", "racecar", "car"]
 *
 * SAMPLE OUTPUT 1:
 *   ""
 *
 * EXPLANATION 1:
 *   There is no common prefix among the input strings at index 0.
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (strs = ["flower", "flow", "flight"]):
 * ----------------------------------------------------------------------------
 * Approach: Vertical Scanning (Column-by-Column comparison)
 * Use strs[0] ("flower") as the reference string. Iterate character index `i`
 * from 0 to strs[0].length() - 1, and compare strs[0].charAt(i) against the
 * character at index `i` of every other string strs[j] (j = 1 .. N-1).
 *
 * - i = 0, ch = 'f' (from "flower"):
 *     j = 1 ("flow"):   i < 4 and 'f' == 'f' -> match
 *     j = 2 ("flight"): i < 6 and 'f' == 'f' -> match
 *
 * - i = 1, ch = 'l' (from "flower"):
 *     j = 1 ("flow"):   i < 4 and 'l' == 'l' -> match
 *     j = 2 ("flight"): i < 6 and 'l' == 'l' -> match
 *
 * - i = 2, ch = 'o' (from "flower"):
 *     j = 1 ("flow"):   i < 4 and 'o' == 'o' -> match
 *     j = 2 ("flight"): i < 6 and 'i' != 'o' -> MISMATCH!
 *     Immediately return strs[0].substring(0, 2) -> "fl".
 *
 * Edge Cases Handled:
 * 1. Null or empty array -> returns ""
 * 2. Shorter string later in array (e.g., ["flower", "flow"]) -> when i == 4,
 *    `i == strs[1].length()` triggers immediate return of "flow" without
 *    throwing StringIndexOutOfBoundsException.
 *
 * Time Complexity:  O(S) where S is the sum of all characters across all strings
 * Space Complexity: O(1) auxiliary space
 * ============================================================================
 */
public class LongestCommonPrefix {

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // Scan each character column `i` of the first string
        for (int i = 0; i < strs[0].length(); i++) {
            char currentChar = strs[0].charAt(i);

            // Compare this character with every other string at the same index `i`
            for (int j = 1; j < strs.length; j++) {
                // Stop if string `j` has ended OR has a different character at index `i`
                if (i == strs[j].length() || strs[j].charAt(i) != currentChar) {
                    return strs[0].substring(0, i);
                }
            }
        }

        // Entire first string is a common prefix across all strings
        return strs[0];
    }

    public static void main(String[] args) {
        String[] sample0 = {"flower", "flow", "flight"};
        System.out.println("Sample 0 Input:  " + Arrays.toString(sample0));
        System.out.println("Sample 0 Output: \"" + longestCommonPrefix(sample0) + "\"");
        // Expected: "fl"

        String[] sample1 = {"dog", "racecar", "car"};
        System.out.println("\nSample 1 Input:  " + Arrays.toString(sample1));
        System.out.println("Sample 1 Output: \"" + longestCommonPrefix(sample1) + "\"");
        // Expected: ""
    }
}