package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P47: REVERSE WORDS IN A STRING
 *
 * Question
 * Reverse word order while removing extra spaces.
 *
 * Example
 * "the sky is blue" -> "blue is sky the".
 *
 * Constraints
 * 1 <= s.length <= 10^4; words are non-empty.
 *
 * Approach used by edSlash
 * 1. Split on whitespace, reverse the word list, and join with single spaces.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Word-order visual for "the sky is blue":
 *     read words from right to left: blue, is, sky, the
 *     join them with one space
 *     result: "blue is sky the"
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P47_ReverseWordsInAString {
    public static String reverseWords(String s) {
        String[] p = s.trim().split("\s+");
        Collections.reverse(Arrays.asList(p));
        return String.join(" ", p);
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("the sky is blue"));
    }
}
