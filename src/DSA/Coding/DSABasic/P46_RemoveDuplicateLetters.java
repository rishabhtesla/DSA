package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Remove repeated letters for the lexicographically smallest subsequence containing each letter once.
 * EXAMPLE: "cbacdcbc" -> "acdb".
 * CONSTRAINTS: 1 <= s.length <= 10^4; lowercase English letters.
 * LOGIC / ALGORITHM:
 * 1. Use remaining counts and a monotonic stack; pop a larger letter only if it appears later.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "cbacdcbc" -> "acdb".; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P46_RemoveDuplicateLetters {
    public static String removeDuplicateLetters(String s) {
        int[] f = new int[26];
        for (char c : s.toCharArray()) f[c - 'a']++;
        boolean[] u = new boolean[26];
        Deque<Character> q = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            int i = c - 'a';
            f[i]--;
            if (u[i]) continue;
            while (!q.isEmpty() && q.peek() > c && f[q.peek() - 'a'] > 0) u[q.pop() - 'a'] = false;
            q.push(c);
            u[i] = true;
        }
        StringBuilder b = new StringBuilder();
        for (char c : q) b.append(c);
        return b.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicateLetters("cbacdcbc"));
    }
}
