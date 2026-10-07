package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [70 / 73] - BASIC CALCULATOR (LeetCode 224)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a string s representing a valid expression containing '(', ')', '+', '-',
 *   non-negative integers, and spaces, implement a basic calculator to evaluate it.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Since only '+' and '-' exist, all operations have equal precedence!
 *   - Parentheses are sign distributors:
 *     -(a + b - c) expands to -a - b + c.
 *   - Single-Pass State Stack:
 *     Maintain `result`, `sign` (+1 or -1), and `num`.
 *     - If digit: accumulate `num = num * 10 + (c - '0')`.
 *     - If '+': `result += sign * num`, set `sign = 1`, `num = 0`.
 *     - If '-': `result += sign * num`, set `sign = -1`, `num = 0`.
 *     - If '(': Push current `result` and `sign` onto stack, reset `result = 0`, `sign = 1`.
 *     - If ')': `result += sign * num`, then multiply by popped sign, add popped previous result.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass through string s.
 *   - Space: O(n) - Stack depth bounded by nesting of parentheses.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P70_BasicCalculator {

    public static int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        int num = 0;
        int sign = 1; // +1 or -1

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '+') {
                result += sign * num;
                num = 0;
                sign = 1;
            } else if (c == '-') {
                result += sign * num;
                num = 0;
                sign = -1;
            } else if (c == '(') {
                // Save state prior to parentheses block
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * num;
                num = 0;
                result *= stack.pop(); // Pop sign before parenthesis
                result += stack.pop(); // Pop result before parenthesis
            }
        }

        result += sign * num;
        return result;
    }

    public static void main(String[] args) {
        System.out.println("P70 Output (1 + 1):           " + calculate("1 + 1"));             // Expected: 2
        System.out.println("P70 Output ( 2-1 + 2 ):       " + calculate(" 2-1 + 2 "));         // Expected: 3
        System.out.println("P70 Output ((1+(4+5+2)-3)+(6+8)): " + calculate("(1+(4+5+2)-3)+(6+8)")); // Expected: 23
    }
}