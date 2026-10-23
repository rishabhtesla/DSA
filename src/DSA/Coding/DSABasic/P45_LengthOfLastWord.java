package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the length of the final word in a space-separated string.
 * EXAMPLE: "Hello World" -> 5.
 * CONSTRAINTS: 1 <= s.length <= 10^4; words contain letters and spaces.
 * LOGIC / ALGORITHM:
 * 1. Trim trailing spaces, split words, and measure the final token.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "Hello World" -> 5.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P45_LengthOfLastWord {
    public static int lengthOfLastWord(String s) {
        String[] p = s.trim().split("\s+");
        return p[p.length - 1].length();
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("Hello World"));
    }
}
