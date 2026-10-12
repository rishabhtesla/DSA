package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [70 / 73] - BASIC CALCULATOR (LeetCode 224)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a string `s` representing a valid expression containing `'('`, `')'`, `'+'`, `'-'`,
 * non-negative integers, and spaces, implement a basic calculator to evaluate it.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Shunting-Yard or Recursion Approach:
 *    - Convert infix expression to postfix (RPN) or use recursive descent parsing.
 *    - Valid, but introduces heavy overhead since this specific calculator only contains `+`, `-`, and parentheses
 *      (meaning all operations have equal precedence).
 *
 * 2. Optimal Single-Pass State Stack Approach (O(n) Time | O(n) Space):
 *    - **The Core Insight:** Parentheses act as sign distributors. For example, `-(a + b - c)` expands to `-a - b + c`.
 *    - We can evaluate the expression on the fly with a single pass using three state variables: `result`, `sign` (+1 or -1), and `num`.
 *    - **Digits:** Accumulate multi-digit numbers via `num = num * 10 + (c - '0')`.
 *    - **Operators (`+`, `-`):** Commit the accumulated number to `result`, update `sign`, and reset `num`.
 *    - **Opening Parenthesis (`(`):** Push the current accumulated `result` and active `sign` onto the stack,
 *      then reset `result = 0` and `sign = 1` to evaluate the inner sub-expression.
 *    - **Closing Parenthesis (`)`):** Commit the final number, multiply by the popped parent `sign`, and add the popped prior `result`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass through the characters of string `s`.
 * - Space: O(n) - Stack depth is bounded by the maximum nesting level of parentheses.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Expression: `"(1 + (4 + 5 + 2) - 3) + (6 + 8)"`
 * - Expected Value: `23`
 *
 * VISUAL DRY RUN (for `"1 + 1"`):
 *   - Read `'1'` -> `num = 1`.
 *   - Read `'+'` -> commits `result += 1 * 1` (result = 1), sets `sign = 1`, `num = 0`.
 *   - Read `'1'` -> `num = 1`.
 *   - End of loop commits remaining `sign * num` (`+1 * 1 = 1`), total result = `2`. Returns `2`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why don't we need operator precedence handling (like multiplication/division) for this problem?
 *    (Because LeetCode 224 restricts operations strictly to addition, subtraction, and parentheses,
 *    all operators share the exact same precedence, allowing left-to-right evaluation.)
 * 2. What purpose do the stacked `sign` and `result` values serve when entering a parenthesis block?
 *    (They act as a snapshot checkpoint, preserving the outer context so that when the sub-expression inside
 *    the parentheses finishes evaluating, it can be correctly integrated back into the running total.)
 */
public class P70_BasicCalculator {

    public static int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        int num = 0;
        int sign = 1; // Tracks current operation sign (+1 for addition, -1 for subtraction)

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                // Build multi-digit numbers incrementally
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
                // Push current result and active sign onto stack as a state checkpoint
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * num;
                num = 0;
                result *= stack.pop(); // Multiply by the sign that preceded the parenthesis block
                result += stack.pop(); // Add the accumulated result from before the parenthesis block
            }
        }

        // Add any remaining trailing number to the final result
        result += sign * num;
        return result;
    }

    public static void main(String[] args) {
        System.out.println("P70 Output (1 + 1):           " + calculate("1 + 1"));             // Expected: 2
        System.out.println("P70 Output ( 2-1 + 2 ):       " + calculate(" 2-1 + 2 "));         // Expected: 3
        System.out.println("P70 Output ((1+(4+5+2)-3)+(6+8)): " + calculate("(1+(4+5+2)-3)+(6+8)")); // Expected: 23
    }
}