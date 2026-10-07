package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [66 / 73] - VALID PARENTHESES (LeetCode 20)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
 *   determine if the input string is valid.
 *   Open brackets must be closed by the same type of brackets and in correct order.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - LIFO (Last-In, First-Out) Matching:
 *     The most recently opened bracket must be the first one to close.
 *   - Clean Trick (Push expected closing bracket):
 *     When encountering '(', push ')' onto the stack.
 *     When encountering '{', push '}'.
 *     When encountering '[', push ']'.
 *     When encountering any closing bracket, check `stack.isEmpty() || stack.pop() != c`.
 *     This avoids verbose nested if/switch checks during the pop phase.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass through the string.
 *   - Space: O(n) - Stack stores unmatched brackets.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P66_ValidParentheses {

    public static boolean isValid(String s) {
        if (s == null || (s.length() & 1) == 1) return false;

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("P66 Output ( ()[]{} ): " + isValid("()[]{}")); // Expected: true
        System.out.println("P66 Output ( (] ):     " + isValid("(]"));     // Expected: false
        System.out.println("P66 Output ( ([]) ):   " + isValid("([])"));   // Expected: true
    }
}