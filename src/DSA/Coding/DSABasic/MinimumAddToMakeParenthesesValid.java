package DSA.Coding.DSABasic;

import java.util.Stack;

/**
 * PROBLEM STATEMENT:
 * Given a string 's' containing parentheses '(' and ')' [00:00:15, 00:01:11].
 * A parentheses string is valid if and only if it is the empty string, or can be written as AB 
 * (concatenation of valid strings A and B) [00:00:35].
 * In one move, you can insert an opening or closing parenthesis at any position of the string [00:00:55, 00:01:18].
 * Return the minimum number of moves required to make 's' valid [00:01:30].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "())" [00:01:55]
 * - Process: Pair "()" matches. One closing parenthesis ')' remains unmatched. Need 1 open parenthesis '('.
 * - Result: 1
 * 
 * Example 2: s = "(((" [00:02:00, 00:02:40]
 * - Process: Three opening parentheses '(((' have no matching closing parentheses. Need 3 closing parentheses ')'.
 * - Result: 3
 * 
 * Example 3: s = "))((" (Simulated inside the video explanation [00:03:08, 00:06:20, 00:13:18])
 * - Process:
 *   - Simply subtracting counts `|2 - 2| = 0` is incorrect because relative position matters [00:03:17, 00:03:49].
 *   - The first two closing parentheses '))' arrive when stack is empty -> need 2 opening parentheses (count = 2) [00:06:29, 00:14:04].
 *   - The next two opening parentheses '((' are pushed onto stack -> need 2 closing parentheses (stack.size() = 2) [00:06:59, 00:14:40].
 *   - Total additions required = unmatched opens in stack + unmatched closes in count = 2 + 2 = 4 [00:03:45, 00:07:18, 00:15:18].
 * - Result: 4 [00:03:28, 00:15:22]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Stack for Open Parentheses & Balance Counter):
 * • Index Initialization: Pointer 'i' iterates through string characters from index 0 to s.length() - 1 [00:09:44].
 * • Condition Boundaries:
 *   - Character is '(': push to `stack` [00:10:04, 00:10:10].
 *   - Character is ')': if `!stack.isEmpty()` and top is '(', pop matching '(' [00:10:24, 00:11:05].
 *   - Else (stack is empty when ')' arrives): increment `count++` (missing open bracket) [00:11:24, 00:11:47].
 * • Operational Steps:
 *   1. Create character `Stack<Character>` and integer counter `count = 0` [00:09:09, 00:09:36].
 *   2. Iterate each character in string `s` [00:09:44].
 *   3. If character is '(', push onto stack [00:10:10].
 *   4. If character is ')' and stack contains unmatched '(', pop from stack [00:11:05].
 *   5. If character is ')' and stack is empty, increment `count` [00:11:47].
 *   6. After traversal, total insertions required = `stack.size() + count` [00:12:29].
 * • Time Complexity: O(n) - Single pass through the string [00:09:44].
 * • Space Complexity: O(n) auxiliary space - Stack stores unmatched open brackets [00:09:09].
 * • LOGIC BEHIND THIS APPROACH:
 *   Matching parentheses requires a Last-In-First-Out structure [00:03:00]. 
 *   An open bracket '(' can be matched by a subsequent closing bracket ')' [00:05:16]. 
 *   A closing bracket ')' that appears when no open brackets are available in the stack represents an unmatched closing bracket 
 *   that can never be matched by future characters, requiring a leading '(' insertion (`count++`) [00:06:29, 00:11:24]. 
 *   Any open brackets remaining in the stack at the end require trailing ')' insertions (`stack.size()`) [00:05:57, 00:07:14].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Stack & Balance Counter - s = "())(()"):
 * Initial: stack = [], count = 0 [00:13:30]
 * i=0 ('('): Open bracket -> stack.push('(') -> stack = ['('] [00:13:50]
 * i=1 (')'): Close bracket -> stack not empty -> pop '(' -> stack = [] [00:13:58]
 * i=2 (')'): Close bracket -> stack is empty! -> count++ -> count = 1 [00:14:04]
 * i=3 (')'): Close bracket -> stack is empty! -> count++ -> count = 2 [00:14:22]
 * i=4 ('('): Open bracket -> stack.push('(') -> stack = ['('] [00:14:40]
 * i=5 ('('): Open bracket -> stack.push('(') -> stack = ['(', '('] [00:14:45]
 * i=6 (')'): Close bracket -> stack not empty -> pop '(' -> stack = ['('] [00:14:58]
 * Loop End.
 * Result = stack.size() + count = 1 + 2 = 3.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Two Counters O(1) Space Optimization - s = ")(("):
 * Counters: openNeeded = 0, closeNeeded = 0
 * i=0 (')'): openNeeded = 0 == 0 -> openNeeded++ (1), closeNeeded stays 0
 * i=1 ('('): closeNeeded++ (1)
 * i=2 ('('): closeNeeded++ (2)
 * Total moves = openNeeded + closeNeeded = 1 + 2 = 3.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Reduction Pipeline - s = "())"):
 * Stream Stage 1: Map chars into balance array `[unmatchedOpen, unmatchedClose]`
 * Processing '(': [1, 0]
 * Processing ')': balance [0] (1) > 0 -> pop open -> [0, 0]
 * Processing ')': balance [0] (0) == 0 -> increment close -> [0, 1]
 * Final reduction sum = 0 + 1 = 1.
 * Output = 1.
 */
public class MinimumAddToMakeParenthesesValid {

    // APPROACH 1: Stack for Open Parentheses & Balance Counter (Anchor Strategy)
    public static int minAddToMakeValidOptimal(String s) {
        if (s == null || s.length() == 0) return 0;

        Stack<Character> stack = new Stack<>(); // Stack for open brackets [00:09:09]
        int count = 0; // Counter for unmatched closing brackets [00:09:36]

        // Traverse each character in string s [00:09:44]
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i); // Extract character [00:09:56]

            if (ch == '(') {
                stack.push(ch); // Push open bracket to stack [00:10:10]
            } 
            // Closing bracket with matching open bracket in stack [00:10:24]
            else if (!stack.isEmpty() && stack.peek() == '(') {
                stack.pop(); // Pop matched pair [00:11:05]
            } 
            // Unmatched closing bracket found [00:11:24]
            else {
                count++; // Needs an inserted opening bracket [00:11:47]
            }
        }

        // Total additions = unmatched open brackets (stack) + unmatched close brackets (count) [00:12:29]
        return stack.size() + count;
    }

    // APPROACH 2: Two Counters Strategy (O(1) Auxiliary Space Optimization)
    // Avoids heap allocations by replacing the stack with two primitive variables tracking 
    // needed open and needed closing brackets.
    public static int minAddToMakeValidCounters(String s) {
        if (s == null || s.length() == 0) return 0;

        int openNeeded = 0;  // Count of opening brackets '(' required
        int closeNeeded = 0; // Count of closing brackets ')' required

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                closeNeeded++; // An open bracket needs a matching closing bracket
            } else if (ch == ')') {
                if (closeNeeded > 0) {
                    closeNeeded--; // Match with an available open bracket
                } else {
                    openNeeded++;  // Unmatched closing bracket requires an open bracket
                }
            }
        }

        return openNeeded + closeNeeded;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Simulates stack balance using an array accumulator in stream reduction. 
    // Boxing operations introduce memory overhead compared to primitive counter iteration.
    public static int minAddToMakeValidStream(String s) {
        if (s == null || s.length() == 0) return 0;

        // acc[0] = closeNeeded (unmatched open brackets awaiting close), acc[1] = openNeeded (unmatched close)
        int[] result = s.chars()
                .mapToObj(c -> (char) c)
                .reduce(new int[]{0, 0}, (acc, ch) -> {
                    if (ch == '(') {
                        acc[0]++; // Expecting a matching close
                    } else if (ch == ')') {
                        if (acc[0] > 0) {
                            acc[0]--; // Match found
                        } else {
                            acc[1]++; // Unmatched close, need open
                        }
                    }
                    return acc;
                }, (a, b) -> new int[]{a[0] + b[0], a[1] + b[1]});

        return result[0] + result[1];
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Single Unmatched Close) ---
        String test1 = "())";
        int res1_1 = minAddToMakeValidOptimal(test1);
        int res1_2 = minAddToMakeValidCounters(test1);
        int res1_3 = minAddToMakeValidStream(test1);

        System.out.println("Test Case 1: \"())\"");
        System.out.println("Approach 1 (Stack + Counter) Result: " + res1_1);
        System.out.println("Approach 2 (Two Counters)   Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)     Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 1 && res1_2 == 1 && res1_3 == 1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (All Unmatched Opens) ---
        String test2 = "(((";
        int res2_1 = minAddToMakeValidOptimal(test2);
        int res2_2 = minAddToMakeValidCounters(test2);
        int res2_3 = minAddToMakeValidStream(test2);

        System.out.println("Test Case 2: \"(((\"");
        System.out.println("Approach 1 (Stack + Counter) Result: " + res2_1);
        System.out.println("Approach 2 (Two Counters)   Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)     Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 3 && res2_2 == 3 && res2_3 == 3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (From Video Board Explanation - Opposite Orientation) ---
        String test3 = "))((";
        int res3_1 = minAddToMakeValidOptimal(test3);
        int res3_2 = minAddToMakeValidCounters(test3);
        int res3_3 = minAddToMakeValidStream(test3);

        System.out.println("Test Case 3: \"))((\"");
        System.out.println("Approach 1 (Stack + Counter) Result: " + res3_1);
        System.out.println("Approach 2 (Two Counters)   Result: " + res3_2);
        System.out.println("Approach 3 (Stream API)     Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 == 4 && res3_2 == 4 && res3_3 == 4 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}