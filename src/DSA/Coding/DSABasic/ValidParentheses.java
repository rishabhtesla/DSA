package DSA.Coding.DSABasic;

import java.util.Stack;

/**
 * PROBLEM STATEMENT:
 * Given a string 's' containing just the characters '(', ')', '{', '}', '[' and ']', 
 * determine if the input string is valid [00:00:27].
 * An input string is valid if:
 * 1. Open brackets must be closed by the same type of brackets [00:00:47].
 * 2. Open brackets must be closed in the correct order [00:00:53].
 * 3. Every close bracket has a corresponding open bracket of the same type [00:00:59].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "()" [00:01:05]
 * - Process: Open bracket '(' matched by immediate close bracket ')' [00:01:11].
 * - Result: true
 * 
 * Example 2: s = "()[]{}" [00:01:17]
 * - Process: Pairs "()", "[]", "{}" are matched and closed correctly in order [00:01:21].
 * - Result: true
 * 
 * Example 3: s = "([)]" (Simulated inside the video explanation [00:02:18])
 * - Process:
 *   - Push '(' to stack [00:05:13].
 *   - Push '[' to stack [00:05:18].
 *   - Encounter ')': stack peak is '['. Expecting '(' to match ')'. Mismatch! [00:02:26, 00:05:29].
 * - Result: false [00:02:30, 00:05:38]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Explicit Character Stack Validation):
 * • Index Initialization: Pointer 'i' iterates through string characters from 0 to s.length() - 1 [00:08:42].
 * • Condition Boundaries:
 *   - Opening brackets ('(', '{', '['): Push to stack [00:09:12, 00:09:37].
 *   - Closing brackets (')', '}', ']'): Stack must not be empty AND top of stack must match corresponding open bracket [00:10:04, 00:10:30].
 *   - Any other condition: return false immediately [00:12:04].
 * • Operational Steps:
 *   1. Create `Stack<Character> stack = new Stack<>()` [00:08:29].
 *   2. Iterate each character in `s` [00:08:42].
 *   3. If character is an opening bracket, push it onto stack [00:09:37].
 *   4. If character is a closing bracket:
 *      - Verify stack is non-empty (`!stack.isEmpty()`) [00:10:04].
 *      - Verify stack top matches matching opening bracket [00:10:30].
 *      - If matched, pop from stack [00:11:12]; else return false [00:12:04].
 *   5. After loop, check if stack is empty (`stack.isEmpty()`). If empty return true; else false [00:12:29].
 * • Time Complexity: O(n) - Single pass through the string [00:08:42].
 * • Space Complexity: O(n) auxiliary space - Stack stores at most n/2 bracket elements [00:03:00].
 * • LOGIC BEHIND THIS APPROACH:
 *   Nested expressions follow a Last-In-First-Out (LIFO) structure [00:03:16]. 
 *   The most recently opened bracket must be the first one closed [00:02:26]. 
 *   A stack tracks unresolved open brackets in exact LIFO order, guaranteeing proper nesting and type matching [00:03:22].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Explicit Character Stack - s = "([{}])"):
 * Initial: stack = [] [00:13:51]
 * i=0 ('('): Open bracket -> stack.push('(') -> stack = ['('] [00:13:59]
 * i=1 ('['): Open bracket -> stack.push('[') -> stack = ['(', '['] [00:14:04]
 * i=2 ('{'): Open bracket -> stack.push('{') -> stack = ['(', '[', '{'] [00:14:16]
 * i=3 ('}'): Close bracket -> stack not empty & top is '{' -> stack.pop() -> stack = ['(', '['] [00:15:04]
 * i=4 (']'): Close bracket -> stack not empty & top is '[' -> stack.pop() -> stack = ['('] [00:15:26]
 * i=5 (')'): Close bracket -> stack not empty & top is '(' -> stack.pop() -> stack = [] [00:16:05]
 * Post-Loop: stack.isEmpty() == true -> Return true [00:16:14].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Map Lookup & Closing-Push Optimization - s = "()[]{}"):
 * Stack pushes expected CLOSING bracket instead:
 * i=0 ('('): push ')' -> stack = [')']
 * i=1 (')'): pop top (matches ')') -> stack = []
 * i=2 ('['): push ']' -> stack = [']']
 * i=3 (']'): pop top (matches ']') -> stack = []
 * i=4 ('{'): push '}' -> stack = ['}']
 * i=5 ('}'): pop top (matches '}') -> stack = []
 * Return stack.isEmpty() == true.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Character Classification Pipeline - s = "([)]"):
 * Stream Stage 1: Convert string chars into sequence.
 * Stream Stage 2: Reduce into Character Stack:
 *   - Push '(', Push '['
 *   - Encounter ')': stack.peek() = '[' != '('. Pipeline flags validation error.
 * Return false.
 */
public class ValidParentheses {

    // APPROACH 1: Explicit Character Stack Validation (Anchor Strategy)
    public static boolean isValidOptimal(String s) {
        if (s == null || s.length() == 0) return true;

        Stack<Character> stack = new Stack<>(); // Stack to store open brackets [00:08:29]

        // Traverse string character by character [00:08:42]
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i); // Extract character [00:09:01]

            // Push opening brackets onto stack [00:09:12]
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch); // Direct push for open brackets [00:09:37]
            } 
            // Process square closing bracket [00:10:20]
            else if (ch == ']' && !stack.isEmpty() && stack.peek() == '[') {
                stack.pop(); // Matching pair found [00:11:12]
            } 
            // Process parenthesis closing bracket [00:11:29]
            else if (ch == ')' && !stack.isEmpty() && stack.peek() == '(') {
                stack.pop(); // Matching pair found [00:11:45]
            } 
            // Process curly closing bracket [00:11:56]
            else if (ch == '}' && !stack.isEmpty() && stack.peek() == '{') {
                stack.pop(); // Matching pair found [00:12:04]
            } 
            // Return false if closing bracket has no match or stack is empty [00:12:04]
            else {
                return false; // Mismatched or unexpected bracket [00:12:14]
            }
        }

        // Return true if all brackets were closed and popped successfully [00:12:29]
        return stack.isEmpty();
    }

    // APPROACH 2: Map Lookup & Expected-Closing Push Strategy
    // Clean optimization that pushes the EXPECTED CLOSING bracket onto the stack 
    // whenever an opening bracket is encountered.
    public static boolean isValidMap(String s) {
        if (s == null || s.length() == 0) return true;

        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                stack.push(')');
            } else if (ch == '{') {
                stack.push('}');
            } else if (ch == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || stack.pop() != ch) {
                return false;
            }
        }

        return stack.isEmpty();
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Boxing primitive chars into Character object streams 
    // adds object creation overhead, but demonstrates functional stack evaluation.
    public static boolean isValidStream(String s) {
        if (s == null || s.length() == 0) return true;

        Stack<Character> stack = new Stack<>();

        boolean isValid = s.chars()
                .mapToObj(c -> (char) c)
                .allMatch(ch -> {
                    if (ch == '(' || ch == '{' || ch == '[') {
                        stack.push(ch);
                        return true;
                    }
                    if (stack.isEmpty()) return false;
                    char top = stack.pop();
                    return (ch == ')' && top == '(') ||
                           (ch == '}' && top == '{') ||
                           (ch == ']' && top == '[');
                });

        return isValid && stack.isEmpty();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Valid Simple Brackets) ---
        String test1 = "()";
        boolean res1_1 = isValidOptimal(test1);
        boolean res1_2 = isValidMap(test1);
        boolean res1_3 = isValidStream(test1);

        System.out.println("Test Case 1: \"()\"");
        System.out.println("Approach 1 (Explicit Stack) Result: " + res1_1);
        System.out.println("Approach 2 (Expected-Close) Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 && res1_2 && res1_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Valid Multiple Pairs) ---
        String test2 = "()[]{}";
        boolean res2_1 = isValidOptimal(test2);
        boolean res2_2 = isValidMap(test2);
        boolean res2_3 = isValidStream(test2);

        System.out.println("Test Case 2: \"()[]{}\"");
        System.out.println("Approach 1 (Explicit Stack) Result: " + res2_1);
        System.out.println("Approach 2 (Expected-Close) Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 && res2_2 && res2_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Invalid Nested Bracket Sequence) ---
        String test3 = "([)]";
        boolean res3_1 = isValidOptimal(test3);
        boolean res3_2 = isValidMap(test3);
        boolean res3_3 = isValidStream(test3);

        System.out.println("Test Case 3: \"([)]\"");
        System.out.println("Approach 1 (Explicit Stack) Result: " + res3_1);
        System.out.println("Approach 2 (Expected-Close) Result: " + res3_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res3_3);
        System.out.println("Verification: " + (!res3_1 && !res3_2 && !res3_3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}