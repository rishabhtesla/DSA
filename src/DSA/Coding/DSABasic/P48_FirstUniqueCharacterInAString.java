package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the first index whose character occurs exactly once.
 * EXAMPLE: "leetcode" -> 0.
 * CONSTRAINTS: 1 <= s.length <= 10^5; lowercase English letters.
 * LOGIC / ALGORITHM:
 * 1. Count all letters, then scan left-to-right for the first count of one.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "leetcode" -> 0.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
