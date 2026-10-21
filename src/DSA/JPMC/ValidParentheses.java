package DSA.JPMC; /**
 * PROBLEM: Valid Parentheses
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', 
 * determine if the input string is valid.
 * Valid rules: Open brackets must be closed by the same type of brackets in the correct order.
 * 
 * EXAMPLE:
 * Input: s = "()[]{}" -> Output: true
 * Input: s = "([)]"   -> Output: false
 * 
 * LOGIC:
 * 1. Initialize an empty Stack.
 * 2. Loop through every character in the string.
 * 3. If it's an opening bracket, PUSH its corresponding closing bracket onto the stack.
 * 4. If it's a closing bracket, POP from the stack and check if it matches the current character.
 *    If the stack is empty (meaning no opening bracket existed) or the characters don't match, return false.
 * 5. At the end, if the stack is completely empty, it's valid.
 * 
 * DRY RUN: s = "{[]}"
 * - Char '{': push '}' to stack. Stack: ['}']
 * - Char '[': push ']' to stack. Stack: ['}', ']']
 * - Char ']': pop from stack (is ']'). Matches current char! Stack: ['}']
 * - Char '}': pop from stack (is '}'). Matches current char! Stack: empty.
 * - End of string, stack is empty -> true.
 * 
 * COMPLEXITY: Time: O(N) to traverse string. Space: O(N) worst case if all characters are opening brackets.
 */
import java.util.Stack;

/**
 * Learning supplement: repeatedly deleting pairs may be O(n^2); a stack scan
 * is O(n) time and O(n) space. Examples: {@code "()[]{}" -> true} and
 * {@code "(]" -> false}. Invariant: the stack contains unmatched opening
 * brackets for the processed prefix. A closer must match the top; leftovers
 * make the final result false. Ask what an empty stack means on a closer.
 */
public class ValidParentheses {
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                // If it's a closing bracket, the stack shouldn't be empty
                // and the top element should match the current character
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }
        // If stack is empty, all brackets were successfully closed
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("Is '()[]{}' valid? " + isValid("()[]{}")); // Output: true
        System.out.println("Is '([)]' valid? " + isValid("([)]"));     // Output: false
    }
}