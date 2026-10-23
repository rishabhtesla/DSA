package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Check whether brackets are correctly nested and matched.
 * EXAMPLE: "()[]{}" -> true; "([)]" -> false.
 * CONSTRAINTS: 1 <= s.length <= 10^4; only bracket characters.
 * LOGIC / ALGORITHM:
 * 1. Push opening brackets and require the matching opener for each closing bracket.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "()[]{}" -> true; "([)]" -> false.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
