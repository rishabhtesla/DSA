package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Reverse word order while removing extra spaces.
 * EXAMPLE: "the sky is blue" -> "blue is sky the".
 * CONSTRAINTS: 1 <= s.length <= 10^4; words are non-empty.
 * LOGIC / ALGORITHM:
 * 1. Split on whitespace, reverse the word list, and join with single spaces.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "the sky is blue" -> "blue is sky the".; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
