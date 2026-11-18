package DSA.BNP;

import java.util.*;

/**
 * ============================================================================
 * QUESTION 5: Balanced Brackets Validation (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * A bracket is considered to be any one of the following characters:
 * '(', ')', '{', '}', '[', or ']'.
 * Two brackets are considered to be a matched pair if an opening bracket
 * occurs to the left of a closing bracket of the exact same type.
 * Given a string `s` of brackets, determine whether the sequence of brackets
 * is balanced. Return "YES" if it is balanced; otherwise, return "NO".
 *
 * INPUT FORMAT:
 * A single string `s` consisting of bracket characters.
 *
 * CONSTRAINTS:
 * - 1 <= s.length <= 10^5
 * - Every character in `s` is one of '{', '}', '(', ')', '[', ']'.
 *
 * SAMPLE INPUT 0:
 *   s = "{[()]}"
 *
 * SAMPLE OUTPUT 0:
 *   YES
 *
 * SAMPLE INPUT 1:
 *   s = "{[(])}"
 *
 * SAMPLE OUTPUT 1:
 *   NO
 *
 * EXPLANATION 1:
 *   The string "{[(])}" is not balanced because the brackets enclosed by the
 *   matched pair '(' and ')' are not balanced: '[' is opened before '(',
 *   so ']' cannot close before ')' closes.
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (Sample 1: s = "{[(])}"):
 * ----------------------------------------------------------------------------
 * Use an ArrayDeque as a LIFO Stack. Whenever we see an OPENING bracket, push
 * its EXPECTED CLOSING bracket onto the stack. Whenever we see a CLOSING
 * bracket, pop the top of the stack and check if it matches!
 *
 * - char = '{' (opening): push '}' -> stack = ['}']
 * - char = '[' (opening): push ']' -> stack = ['}', ']']
 * - char = '(' (opening): push ')' -> stack = ['}', ']', ')']
 * - char = ']' (closing): stack is not empty; pop() gives ')' != ']' -> MISMATCH!
 *   Immediately return "NO".
 *
 * Dry Run for Sample 0: s = "{[()]}"
 * - '{' -> push '}' -> ['}']
 * - '[' -> push ']' -> ['}', ']']
 * - '(' -> push ')' -> ['}', ']', ')']
 * - ')' -> pop() == ')' -> matches! stack = ['}', ']']
 * - ']' -> pop() == ']' -> matches! stack = ['}']
 * - '}' -> pop() == '}' -> matches! stack = []
 * End of string: stack.isEmpty() is true -> return "YES".
 *
 * Time Complexity:  O(N)
 * Space Complexity: O(N) in worst case (all opening brackets)
 * ============================================================================
 */
public class BalancedBrackets {

    public static String isBalanced(String s) {
        if (s == null || s.length() % 2 != 0) {
            return "NO"; // Odd length strings can never be balanced
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            // Push the expected matching closing bracket for every opening bracket
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                // Closing bracket encountered: stack must not be empty and top must match
                if (stack.isEmpty() || stack.pop() != c) {
                    return "NO";
                }
            }
        }

        // Balanced only if no unclosed opening brackets remain on the stack
        return stack.isEmpty() ? "YES" : "NO";
    }

    public static void main(String[] args) {
        System.out.println("Sample 0 (\"{[()]}\"): " + isBalanced("{[()]}")); // Expected: YES
        System.out.println("Sample 1 (\"{[(])}\"): " + isBalanced("{[(])}")); // Expected: NO
        System.out.println("Sample 2 (\"{{[[(())]]}}\"): " + isBalanced("{{[[(())]]}}")); // Expected: YES
    }
}