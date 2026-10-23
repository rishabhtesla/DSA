package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find minimum parentheses insertions needed to make a string valid.
 * EXAMPLE: "()))((" -> 4.
 * CONSTRAINTS: 1 <= s.length <= 1000; characters are '(' or ')'.
 * LOGIC / ALGORITHM:
 * 1. Track unmatched opens; unmatched closes require immediate opens; remaining opens need closes.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "()))((" -> 4.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P52_MinimumAddToMakeParenthesesValid {
    public static int minAddToMakeValid(String s) {
        int open = 0, add = 0;
        for (char c : s.toCharArray())
            if (c == '(') open++;
            else if (open > 0) open--;
            else add++;
        return add + open;
    }

    public static void main(String[] args) {
        System.out.println(minAddToMakeValid("()))(("));
    }
}
