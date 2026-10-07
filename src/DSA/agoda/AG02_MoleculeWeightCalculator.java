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
 *   Given a chemical formula as a string (e.g., "CH4", "H(CH4)2", "((CH)2O)3") 
 *   and predefined atomic weights (C = 12, H = 1, O = 16), calculate the total 
 *   molecular weight of the compound.
 *   - An uppercase letter represents an atom.
 *   - Parentheses '(' and ')' group elements and can be followed by a multiplier.
 *   - If no integer follows an atom or parenthesis, its count defaults to 1.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Classic recursive parsing problem that maps naturally to an Evaluation Stack.
 *   - Push sentinel `-1` to mark the beginning of an open parenthesis '('.
 *   - When reading an Atom:
 *     Parse its name, read its following digits (if any), and push (atomWeight * count).
 *   - When reading ')':
 *     Pop all computed weights from the stack until hitting the sentinel `-1`.
 *     Sum them up into `innerWeight`.
 *     Read the multiplier integer immediately following ')' (defaults to 1).
 *     Push (innerWeight * multiplier) back onto the stack!
 *   - Final step: Sum all values remaining in the stack.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single forward scan; each character is pushed and popped at most twice.
 *   - Space: O(n) - Stack depth bounded by parentheses and atom count.
 */
public class AG02_MoleculeWeightCalculator {

    public static int calculateWeight(String formula) {
        Map<Character, Integer> atomicMass = new HashMap<>();
        atomicMass.put('C', 12);
        atomicMass.put('H', 1);
        atomicMass.put('O', 16);

        Deque<Integer> stack = new ArrayDeque<>();
        int n = formula.length();
        int i = 0;

        while (i < n) {
            char ch = formula.charAt(i);

            if (ch == '(') {
                stack.push(-1); // Sentinel marker for scope boundary
                i++;
            } else if (Character.isUpperCase(ch)) {
                int weight = atomicMass.getOrDefault(ch, 0);
                i++;

                // Read optional count digits after atom
                int count = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    count = count * 10 + (formula.charAt(i) - '0');
                    i++;
                }
                stack.push(weight * (count == 0 ? 1 : count));
            } else if (ch == ')') {
                i++;

                // Pop and aggregate everything within this parenthetical group
                int groupSum = 0;
                while (!stack.isEmpty() && stack.peek() != -1) {
                    groupSum += stack.pop();
                }
                stack.pop(); // Pop sentinel -1

                // Read optional multiplier digits after ')'
                int multiplier = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    multiplier = multiplier * 10 + (formula.charAt(i) - '0');
                    i++;
                }
                stack.push(groupSum * (multiplier == 0 ? 1 : multiplier));
            } else {
                i++;
            }
        }

        int totalWeight = 0;
        while (!stack.isEmpty()) {
            totalWeight += stack.pop();
        }

        return totalWeight;
    }

    public static void main(String[] args) {
        System.out.println("AG02 Output (CH4):     " + calculateWeight("CH4"));       // Expected: 16 (12 + 1*4)
        System.out.println("AG02 Output (H(CH4)2): " + calculateWeight("H(CH4)2"));   // Expected: 33 (1 + 16*2)
        System.out.println("AG02 Output ((CO)2):   " + calculateWeight("(CO)2"));     // Expected: 56 ((12+16)*2)
    }
}