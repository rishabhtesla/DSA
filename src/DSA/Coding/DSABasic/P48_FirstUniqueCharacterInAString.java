package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P48: FIRST UNIQUE CHARACTER IN A STRING
 *
 * Question
 * Return the first index whose character occurs exactly once.
 *
 * Example
 * "leetcode" -> 0.
 *
 * Constraints
 * 1 <= s.length <= 10^5; lowercase English letters.
 *
 * Approach used by edSlash
 * 1. Count all letters, then scan left-to-right for the first count of one.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Frequency visual for "leetcode":
 *     count each character; l, e, t, c, o, d occur once and e occurs twice
 *     scan from left: l is the first character with count 1
 *     return index 0
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P48_FirstUniqueCharacterInAString {
    public static int firstUniqChar(String s) {
        int[] f = new int[26];
        for (char c : s.toCharArray()) f[c - 'a']++;
        for (int i = 0; i < s.length(); i++) if (f[s.charAt(i) - 'a'] == 1) return i;
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(firstUniqChar("leetcode"));
    }
}
