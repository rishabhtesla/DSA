package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P53: VALID PARENTHESES
 *
 * Question
 * Check whether brackets are correctly nested and matched.
 *
 * Example
 * "()[]{}" -> true; "([)]" -> false.
 *
 * Constraints
 * 1 <= s.length <= 10^4; only bracket characters.
 *
 * Approach used by edSlash
 * 1. Push opening brackets and require the matching opener for each closing bracket.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 * Example from main: "()[]{}" -> true; "([)]" -> false.
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P53_ValidParentheses {
    public static boolean isValid(String s) {
        Deque<Character> q = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') q.push(c);
            else {
                if (q.isEmpty()) return false;
                char o = q.pop();
                if ((c == ')' && o != '(') || (c == ']' && o != '[') || (c == '}' && o != '{')) return false;
            }
        }
        return q.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("()[]{}"));
    }
}
