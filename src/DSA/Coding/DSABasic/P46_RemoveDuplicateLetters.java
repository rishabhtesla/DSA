package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P46: REMOVE DUPLICATE LETTERS
 *
 * Question
 * Remove repeated letters for the lexicographically smallest subsequence containing each letter once.
 *
 * Example
 * "cbacdcbc" -> "acdb".
 *
 * Constraints
 * 1 <= s.length <= 10^4; lowercase English letters.
 *
 * Approach used by edSlash
 * 1. Use remaining counts and a monotonic stack; pop a larger letter only if it appears later.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Monotonic-stack visual for "cbacdcbc":
 *     remove c when a smaller unused a appears -> keep a
 *     continue with b, c, d; repeated c is skipped
 *     final stack order is "acdb"
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
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
