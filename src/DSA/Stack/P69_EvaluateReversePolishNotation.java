package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * [69 / 73] - EVALUATE REVERSE POLISH NOTATION (LeetCode 150)
 * ============================================================================
 * 
 * PROBLEM:
 *   Evaluate the value of an arithmetic expression in Reverse Polish Notation (RPN).
 *   Valid operators are '+', '-', '*', and '/'. Division truncates toward zero.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Postfix expressions inherently resolve operands before operators.
 *   - Stack Algorithm:
 *     1. Scan tokens left to right.
 *     2. If token is a number -> push onto stack.
 *     3. If token is an operator -> pop `b` (second operand), then pop `a` (first operand).
 *        Compute `result = a op b`, and push `result` back onto stack.
 *     *Order matters for subtraction and division: `a - b` and `a / b`!*
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass over the token list.
 *   - Space: O(n) - Stack stores operands.
 *
 *
 * EXAMPLE:
 *   The first main tokens are ["2","1","+","3","*"]; expected value is 9.
 *
 * VISUAL DRY RUN:
 *   Push 2 -> [2], push 1 -> [2,1]; '+' pops 1,2 and pushes 3 -> [3]; push 3 -> [3,3];
 *   '*' pops 3,3 and pushes 9. Final stack top is 9.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P69_EvaluateReversePolishNotation {

    public static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                default:
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        System.out.println("P69 Output (Test 1): " + evalRPN(tokens1)); // Expected: 9 ((2 + 1) * 3)

        String[] tokens2 = {"4", "13", "5", "/", "+"};
        System.out.println("P69 Output (Test 2): " + evalRPN(tokens2)); // Expected: 6 (4 + (13 / 5))
    }
}