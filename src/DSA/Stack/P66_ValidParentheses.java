package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [66 / 73] - VALID PARENTHESES (LeetCode 20)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`,
 * determine if the input string is valid.
 * An input string is valid if open brackets are closed by the same type of brackets and in the correct order.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / String Replacement Approach:
 *    - Repeatedly replace pairs like `"()"`, `"{}"`, and `"[]"` with empty strings until no more replacements can be made.
 *    - Inefficient: takes $O(n^2)$ or worse due to string allocation overhead on each iteration.
 *
 * 2. Optimal LIFO Stack with Expected Closer Push Approach (O(n) Time | O(n) Space):
 *    - **The Core Insight:** The most recently opened bracket must be the first one to close, which maps
 *      perfectly to a Last-In, First-Out (LIFO) stack data structure.
 *    - **Clean Trick:** Instead of pushing opening brackets and evaluating mapping lookups upon closing,
 *      **push the expected closing bracket** directly onto the stack whenever an opening bracket is encountered:
 *      - Encountering `'('` -> push `')'`
 *      - Encountering `'{'` -> push `'}'`
 *      - Encountering `'['` -> push `']'`
 *    - When encountering any closing bracket, check `stack.isEmpty() || stack.pop() != c`.
 *      This avoids verbose nested `if/switch` checks during the pop phase.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass over the characters of string `s`.
 * - Space: O(n) - Stack stores unmatched expected closing brackets (at worst, $O(n)$ if all characters are openers).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - String: `"()[]{}"`
 * - Expected Result: `true`
 *
 * VISUAL DRY RUN (for `"()[]{}"`):
 *   - Character `'('` pushes `')'` onto the stack -> Stack: `[')']`.
 *   - Character `')'` pops stack and matches `'j'` -> Stack: `[]`.
 *   - Characters `'['`, `']'`, `'{'`, `'}'` process similarly.
 *   - At the end, the stack is empty and every closer matched successfully -> returns `true`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is checking `(s.length() & 1) == 1` an effective initial optimization?
 *    (A valid sequence of matched parentheses must always have an even length.
 *    If the string length is odd, it can never form complete pairs, so we can return `false` instantly.)
 * 2. Why use `ArrayDeque` instead of `Stack` in Java?
 *    ( `ArrayDeque` is a more modern, efficient, and faster implementation of a LIFO stack than the legacy
 *    synchronized `Stack` class.)
 */
public class P66_ValidParentheses {

    public static boolean isValid(String s) {
        // Fast-fail: odd length strings can never have fully matched pairs
        if (s == null || (s.length() & 1) == 1) return false;

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Push the expected closing counterpart onto the stack for opening brackets
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                // For closing characters, verify stack is non-empty and top matches current char
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }

        // The string is valid only if all opening brackets have been successfully matched and popped
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("P66 Output ( ()[]{} ): " + isValid("()[]{}")); // Expected: true
        System.out.println("P66 Output ( (] ):     " + isValid("(]"));     // Expected: false
        System.out.println("P66 Output ( ([]) ):   " + isValid("([])"));   // Expected: true
    }
}