package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [69 / 73] - EVALUATE REVERSE POLISH NOTATION (LeetCode 150)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation (RPN).
 * Valid operators are `+`, `-`, `*`, and `/`. Each operand may be an integer or another expression.
 * Note that division between two integers always truncates toward zero.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Recursive Evaluation Approach:
 *    - Parse the expression tree recursively or evaluate strings by searching for operators.
 *    - Inefficient: incurs repeated string parsing and high overhead compared to a linear stack pass.
 *
 * 2. Optimal LIFO Operand Stack Approach (O(n) Time | O(n) Space):
 *    - **The Core Insight:** Postfix (RPN) expressions inherently resolve operands before operators,
 *      matching the Last-In, First-Out (LIFO) behavior of a stack.
 *    - **Step 1:** Scan the tokens array from left to right.
 *    - **Step 2:** If the token is a number, parse it and push it onto the stack.
 *    - **Step 3:** If the token is an operator, pop the **second operand (`b`)** first,
 *      then pop the **first operand (`a`)**, compute the result of `a op b`, and push the result back onto the stack.
 *    - *Crucial Detail:* Order matters strictly for subtraction and division (`a - b` and `a / b`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass over the list of expression tokens.
 * - Space: O(n) - Stack stores operands proportional to the number of tokens.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Tokens: `["2", "1", "+", "3", "*"]`
 * - Expected Value: `9` (corresponding to `(2 + 1) * 3`)
 *
 * VISUAL DRY RUN (for `["2", "1", "+", "3", "*"]`):
 *   - Push `2` -> Stack: `[2]`
 *   - Push `1` -> Stack: `[2, 1]`
 *   - Operator `'+'` pops `1` (`b`) and `2` (`a`), computes `2 + 1 = 3`, and pushes `3` -> Stack: `[3]`
 *   - Push `3` -> Stack: `[3, 3]`
 *   - Operator `'*'` pops `3` (`b`) and `3` (`a`), computes `3 * 3 = 9`, and pushes `9` -> Stack: `[9]`
 *   - Returns final stack top: `9`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why must `b` be popped *before* `a` for non-commutative operations like `-` and `/`?
 *    (Because elements are popped from the top of the stack in reverse order of how they were pushed.
 *    The top element corresponds to the right-hand operand (`b`), and the element beneath it is the left-hand operand (`a`).)
 * 2. Does standard integer division in Java truncate toward zero automatically?
 *    (Yes, integer division (`/`) in Java truncates toward zero by default, matching the problem requirements.)
 */
public class P69_EvaluateReversePolishNotation {

    public static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {
            switch (token) {
                case "+":
                    // Addition is commutative; order of pops doesn't affect result
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-": {
                    // Order matters: pop b (right operand) first, then a (left operand)
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/": {
                    // Order matters: pop b (divisor) first, then a (dividend)
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                default:
                    // If token is a numeric operand, parse and push onto stack
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }

        // The final evaluated result remains at the top of the stack
        return stack.pop();
    }

    public static void main(String[] args) {
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        System.out.println("P69 Output (Test 1): " + evalRPN(tokens1)); // Expected: 9 ((2 + 1) * 3)

        String[] tokens2 = {"4", "13", "5", "/", "+"};
        System.out.println("P69 Output (Test 2): " + evalRPN(tokens2)); // Expected: 6 (4 + (13 / 5))
    }
}