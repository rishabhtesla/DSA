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
 * Example from main: "leetcode" -> 0.
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
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
