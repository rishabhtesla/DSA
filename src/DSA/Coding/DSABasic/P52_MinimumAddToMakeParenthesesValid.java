package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P52: MINIMUM ADD TO MAKE PARENTHESES VALID
 *
 * Question
 * Find minimum parentheses insertions needed to make a string valid.
 *
 * Example
 * "()))((" -> 4.
 *
 * Constraints
 * 1 <= s.length <= 1000; characters are '(' or ')'.
 *
 * Approach used by edSlash
 * 1. Track unmatched opens; unmatched closes require immediate opens; remaining opens need closes.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Parenthesis-balance visual for "()))(":
 *     balance changes: 1 -> 0 -> 1 -> 0 -> -1 -> 0
 *     one closing parenthesis has no matching opening parenthesis
 *     one opening parenthesis remains unmatched at the end
 *     additions needed = 1 + 1 = 2
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
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
