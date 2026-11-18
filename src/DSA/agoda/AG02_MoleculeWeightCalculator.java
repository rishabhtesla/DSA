package DSA.agoda;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [AG-02 / 06] - MOLECULE WEIGHT CALCULATOR (Agoda HackerRank Favorite / LC 726 Variant)
 * ============================================================================
 *
 * PROBLEM:
 *   You are given a string `formula` representing a valid chemical compound and
 *   a fixed table of atomic weights:
 *     - Carbon   ('C') = 12
 *     - Hydrogen ('H') = 1
 *     - Oxygen   ('O') = 16
 *
 *   Calculate and return the total molecular weight of the compound according
 *   to the following rules:
 *     1. An uppercase letter represents a single atom.
 *     2. Parentheses '(' and ')' group sub-formulas together.
 *     3. An atom or a closing parenthesis ')' may be immediately followed by a
 *        positive integer (multi-digit possible, e.g., "12") acting as a multiplier.
 *     4. If no integer follows an atom or ')', its count defaults to 1.
 *
 * CONSTRAINTS:
 *   - 1 <= formula.length() <= 1000
 *   - `formula` consists only of 'C', 'H', 'O', digits '0'-'9', '(', and ')'.
 *   - `formula` is guaranteed to be valid and balanced.
 *   - Total molecular weight fits within a signed 32-bit integer.
 *
 * EXAMPLES:
 *   Example 1:
 *     Input:  formula = "CH4"
 *     Output: 16
 *     Explanation: C(12) + H(1) * 4 = 16
 *
 *   Example 2:
 *     Input:  formula = "H(CH4)2"
 *     Output: 33
 *     Explanation: H(1) + [ C(12) + H(1) * 4 ] * 2 = 1 + 16 * 2 = 33
 *
 *   Example 3:
 *     Input:  formula = "((CO)2O)3"
 *     Output: 216
 *     Explanation: [ [ C(12) + O(16) ] * 2 + O(16) ] * 3 = [ 28 * 2 + 16 ] * 3 = 72 * 3 = 216
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Data Structure Choice (Evaluation Stack):
 *      - We use a single `Deque<Integer>` stack to evaluate sub-expressions from
 *        inside-out without needing recursion or pre-matching parentheses indices.
 *      - Every positive integer on the stack represents an already-evaluated
 *        molecular weight chunk.
 *      - Since valid weights are strictly positive (> 0), we use `-1` as a
 *        sentinel marker ("scope wall") to represent an opening parenthesis '('.
 *
 *   2. Single-Pass Token Parsing (Pointer `i` from 0 to n - 1):
 *      - Notice we DO NOT increment `i` at the end of the outer `while` loop.
 *        Instead, each branch advances `i` over the exact characters it consumes.
 *
 *      - Case A: `formula.charAt(i) == '('`
 *          * A new nested group begins.
 *          * Push `-1` onto the stack to mark where this group starts, and do `i++`.
 *
 *      - Case B: `Character.isUpperCase(formula.charAt(i))` (An Atom: 'C', 'H', or 'O')
 *          * Look up the atom's base weight and advance `i++`.
 *          * Run an inner `while` loop to greedily read all consecutive digits
 *            (`count = count * 10 + digit`), advancing `i` for each digit.
 *          * If no digits were read (`count == 0`), default `count` to `1`.
 *          * Push `baseWeight * count` onto the stack.
 *
 *      - Case C: `formula.charAt(i) == ')'`
 *          * The current parenthetical group has ended. Advance `i++`.
 *          * Pop and accumulate (`innerGroupWeight += stack.pop()`) all evaluated
 *            weights from the top of the stack until we hit the nearest `-1` wall.
 *          * Pop and discard the `-1` sentinel wall itself.
 *          * Greedily read any consecutive digits immediately following ')' to get
 *            `multiplier` (defaulting to `1` if none exist).
 *          * Push the scaled group total `innerGroupWeight * multiplier` back onto
 *            the stack so outer scopes can treat it just like a single atom!
 *
 *   3. Final Aggregation:
 *      - Once the entire string is scanned, all parentheses have been resolved
 *        and collapsed. Pop and sum all remaining integers in the stack to get
 *        the final molecular weight.
 *
 * ============================================================================
 * VISUAL DRY RUN: formula = "H(CH4)2"
 * ============================================================================
 *   Index | Char | Action Taken                                | Stack (Bottom -> Top)
 *   ------+------+---------------------------------------------+-----------------------
 *     0   | 'H'  | Atom H(1), no digits -> count = 1. Push 1   | [ 1 ]
 *     1   | '('  | Open scope -> Push sentinel -1              | [ 1, -1 ]
 *     2   | 'C'  | Atom C(12), no digits -> count = 1. Push 12 | [ 1, -1, 12 ]
 *    3-4  | "H4" | Atom H(1), digits="4" -> push 1 * 4 = 4     | [ 1, -1, 12, 4 ]
 *    5-6  | ")2" | Close scope!                                |
 *         |      |   1. Pop until -1: sum = 4 + 12 = 16        | [ 1, -1 ]
 *         |      |   2. Pop sentinel -1                        | [ 1 ]
 *         |      |   3. Read digits="2" -> push 16 * 2 = 32    | [ 1, 32 ]
 *    END  |  -   | Sum everything remaining on stack: 1 + 32   | Result = 33
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(n) - Single forward pass; every element is pushed once and popped once.
 *   - Space: O(n) - Stack grows at most linearly with the length of the formula.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *      (Every entry on the stack is either a `-1` scope wall or a finalized positive weight.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (The inner digit-reading while-loops advance `i` directly, so the outer loop must NOT do `i++` unconditionally.)
 *   4. Which edge cases change the control flow (multi-digit numbers like "H12", adjacent parens "(CO)(CH4)", deeply nested "(((C)))")?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG02_MoleculeWeightCalculator {

    public static int calculateWeight(String formula) {
        // Step 1: Lookup table for base atomic weights
        Map<Character, Integer> atomicMass = new HashMap<>();
        atomicMass.put('C', 12);
        atomicMass.put('H', 1);
        atomicMass.put('O', 16);

        // Stack holds ONLY two things:
        //   1. `-1`          -> Sentinel wall marking an open '('
        //   2. Positive int  -> Already evaluated weight of an atom or collapsed (...) group
        Deque<Integer> stack = new ArrayDeque<>();
        int n = formula.length();
        int i = 0;

        while (i < n) {
            char ch = formula.charAt(i);

            // CASE 1: Open Parenthesis '(' -> Drop a boundary wall onto the stack
            if (ch == '(') {
                stack.push(-1);
                i++; // Consume '('
            }
            // CASE 2: Atom ('C', 'H', 'O') -> Evaluate [Atom + Optional Digits] and push weight
            else if (Character.isUpperCase(ch)) {
                int baseWeight = atomicMass.getOrDefault(ch, 0);
                i++; // Consume the atom letter

                // Parse full multi-digit number if present (e.g., "12" in "C12")
                int count = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    count = count * 10 + (formula.charAt(i) - '0');
                    i++; // Consume digit
                }

                // Default count is 1 if no digits followed the atom
                int effectiveCount = (count == 0) ? 1 : count;
                stack.push(baseWeight * effectiveCount);
            }
            // CASE 3: Close Parenthesis ')' -> Collapse current scope back to nearest '-1' wall
            else if (ch == ')') {
                i++; // Consume ')'

                // Sum all evaluated weights inside this parenthesis pair
                int innerGroupWeight = 0;
                while (!stack.isEmpty() && stack.peek() != -1) {
                    innerGroupWeight += stack.pop();
                }
                stack.pop(); // Remove the matching '(' sentinel wall (-1)

                // Parse full multi-digit multiplier after ')' if present (e.g., "2" in "(CH4)2")
                int multiplier = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    multiplier = multiplier * 10 + (formula.charAt(i) - '0');
                    i++; // Consume digit
                }

                // Default multiplier is 1 if no digits followed ')'
                int effectiveMultiplier = (multiplier == 0) ? 1 : multiplier;
                stack.push(innerGroupWeight * effectiveMultiplier);
            }
            // Defensive fallback for unexpected whitespace
            else {
                i++;
            }
        }

        // Step 2: All scopes are now flattened into positive numbers on the stack. Sum them up.
        int totalWeight = 0;
        while (!stack.isEmpty()) {
            totalWeight += stack.pop();
        }

        return totalWeight;
    }

    public static void main(String[] args) {
        System.out.println("Test 1 (CH4):       " + calculateWeight("CH4")       + " | Expected: 16");
        System.out.println("Test 2 (H(CH4)2):   " + calculateWeight("H(CH4)2")   + " | Expected: 33");
        System.out.println("Test 3 (((CO)2O)3): " + calculateWeight("((CO)2O)3") + " | Expected: 216");
        System.out.println("Test 4 (C12H22O11): " + calculateWeight("C12H22O11") + " | Expected: 342");
    }
}