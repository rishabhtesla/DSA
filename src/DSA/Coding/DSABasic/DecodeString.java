package DSA.Coding.DSABasic;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given an encoded string 's', return its decoded string [00:00:30].
 * The encoding rule is: `k[encoded_string]`, where the `encoded_string` inside the square brackets 
 * is repeated exactly `k` times [00:00:43]. Note that `k` is guaranteed to be a positive integer [00:00:51].
 * You may assume that the input string is always valid; there are no extra white spaces, 
 * and square brackets are well-formed [00:00:59, 00:01:06].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "3[a]2[bc]" [00:01:30]
 * - Process: "3[a]" expands to "aaa". "2[bc]" expands to "bcbc" [00:01:46, 00:02:43].
 * - Result: "aaabcbc" [00:02:59]
 * 
 * Example 2: s = "3[a2[c]]" [00:01:54]
 * - Process:
 *   - Inner bracket first: "2[c]" expands to "cc" [00:02:07].
 *   - String becomes "3[acc]" [00:02:14].
 *   - Outer bracket next: "3[acc]" expands to "accaccacc" [00:02:23].
 * - Result: "accaccacc" [00:02:29]
 * 
 * Example 3: s = "3[ab]2[cd]" (Simulated inside the video explanation [00:31:23, 00:36:02])
 * - Process:
 *   - Use two stacks: `numStack` for multiplier numbers, `mainStack` for string fragments/brackets [00:07:07, 00:15:03].
 *   - Push `3` into `numStack`, push `[` and "ab" into `mainStack` [00:07:43, 00:32:20].
 *   - Push `2` into `numStack`, push `[` and "cd" into `mainStack` [00:32:35, 00:32:43].
 *   - Encounter `]`: pop "cd" and `[`, pop count `2` from `numStack` -> repeat "cd" 2 times -> "cdcd" [00:09:05, 00:33:57].
 *   - Push "cdcd" back to `mainStack` [00:09:50, 00:34:08].
 *   - Encounter outer `]`: pop accumulated string ("abcdcd") and `[`, pop count `3` from `numStack` [00:10:03, 00:34:39].
 *   - Repeat "abcdcd" 3 times -> "abcdcdabcdcdabcdcd" [00:10:29, 00:35:10].
 * - Result: "abcdcdabcdcdabcdcd" [00:36:02]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Two Stacks for Multipliers & Characters):
 * • Index Initialization: Pointer 'i' iterates through string `s` from index 0 to `s.length() - 1` [00:15:38].
 * • Condition Boundaries:
 *   - Digit (`0-9`): Accumulate multi-digit numbers via `num = num * 10 + (ch - '0')` [00:16:14, 00:18:07].
 *   - Not `]`: Push characters / opening brackets directly to `mainStack` [00:21:28].
 *   - `]`: Pop contents from `mainStack` up to `[`, repeat popped string by count from `numStack`, and push back [00:21:58, 00:26:24].
 * • Operational Steps:
 *   1. Create `Stack<Integer> numStack` and `Stack<String> mainStack` [00:15:03].
 *   2. Iterate characters: if digit, compute full integer value and push to `numStack` [00:16:14, 00:21:18].
 *   3. If non-closing bracket/char, push as String to `mainStack` [00:21:43].
 *   4. On closing bracket `]`, pop characters until `[`, pop matching `[` bracket [00:23:12, 00:24:08].
 *   5. Pop repeat multiplier count from `numStack` and multiply string using a `StringBuilder` [00:25:08, 00:25:57].
 *   6. Push multiplied string back onto `mainStack` [00:26:24].
 *   7. Reconstruct complete decoded string from `mainStack` [00:27:09].
 * • Time Complexity: O(n * k_max) - Where n is string length and k_max is the maximum repeat count.
 * • Space Complexity: O(n * k_max) - For stacks and string buffers storing intermediate/final results [00:06:34].
 * • LOGIC BEHIND THIS APPROACH:
 *   Nested brackets require inner expressions to be decoded before outer ones (LIFO order) [00:05:21, 00:06:15]. 
 *   Using two stacks isolates numbers from characters while maintaining nested context [00:07:07]. 
 *   Encountering a closing bracket triggers the expansion of the current innermost sub-string, which is then 
 *   pushed back to be processed by outer brackets [00:09:05, 00:09:50].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Two Stacks - s = "3[a2[c]]"):
 * Stacks setup: numStack = [], mainStack = [] [00:31:43]
 * i=0 ('3'): digit -> parse full num = 3, numStack.push(3) [00:32:14]
 * i=1 ('['): non-']' -> mainStack.push("[") [00:32:20]
 * i=2 ('a'): non-']' -> mainStack.push("a") [00:32:27]
 * i=3 ('2'): digit -> parse full num = 2, numStack.push(2) [00:32:35]
 * i=4 ('['): non-']' -> mainStack.push("[") [00:32:40]
 * i=5 ('c'): non-']' -> mainStack.push("c") [00:32:43]
 * i=6 (']'): closing bracket! [00:32:51]
 *            Pop until '[' -> sub = "c" [00:33:22]
 *            Pop '[' from mainStack [00:33:32]
 *            Pop count = 2 from numStack [00:33:40]
 *            Repeat "c" 2 times -> "cc" [00:33:57]
 *            mainStack.push("cc") [00:34:08] -> mainStack = ["[", "a", "cc"]
 * i=7 (']'): closing bracket! [00:34:23]
 *            Pop until '[' -> sub = "acc" [00:34:45]
 *            Pop '[' from mainStack [00:34:50]
 *            Pop count = 3 from numStack [00:34:57]
 *            Repeat "acc" 3 times -> "accaccacc" [00:35:10]
 *            mainStack.push("accaccacc") [00:35:25]
 * Result = "accaccacc" [00:36:02].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Recursive Sub-problem Strategy - s = "2[ab]"):
 * Recursion depth index ptr [0]:
 * - Parse number 2 -> recurse decodeString for "ab]"
 *   - Collect chars: "a", "b"
 *   - Encounter ']' -> return "ab"
 * - Repeat "ab" 2 times -> "abab"
 * Output = "abab".
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Character Classification Pipeline - s = "2[a]"):
 * Process input stream sequentially:
 * Stage 1: Map digits to multiplier stack -> [2]
 * Stage 2: Buffer characters into fragment list -> ["a"]
 * Stage 3: Reduce pipeline on ']' boundary by taking count 2 and joining list -> "aa"
 * Output = "aa".
 */
public class DecodeString {

    // APPROACH 1: Two Stacks for Multipliers & Characters (Anchor Strategy)
    public static String decodeStringOptimal(String s) {
        if (s == null || s.length() == 0) return "";

        Stack<Integer> numberStack = new Stack<>(); // Stack for repeat counts [00:15:03]
        Stack<String> mainStack = new Stack<>();    // Stack for strings & brackets [00:15:22]

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Handle multi-digit numbers (e.g. 13 -> 1, 3) [00:16:14, 00:18:07]
            if (ch >= '0' && ch <= '9') {
                int num = 0;
                while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    num = num * 10 + (s.charAt(i) - '0'); // Build multi-digit integer [00:18:17]
                    i++;
                }
                i--; // Adjust loop counter increment [00:21:02]
                numberStack.push(num); // Push parse result to number stack [00:21:18]
            } 
            // Push characters and opening brackets directly [00:21:28]
            else if (ch != ']') {
                mainStack.push(ch + ""); // Convert char to String and push [00:21:43]
            } 
            // Process closing bracket ']' [00:21:58]
            else {
                // Pop characters from mainStack until opening bracket '[' is reached [00:23:12]
                String str = "";
                while (!mainStack.peek().equals("[")) {
                    str = mainStack.pop() + str; // Prepend popped character [00:23:28]
                }

                mainStack.pop(); // Pop the opening bracket '[' [00:24:08]

                int count = numberStack.pop(); // Pop repeat multiplier from numberStack [00:25:08]

                // Repeat substring 'count' times [00:25:42]
                StringBuilder sb = new StringBuilder();
                while (count > 0) {
                    sb.append(str);
                    count--;
                }

                mainStack.push(sb.toString()); // Push expanded string back to mainStack [00:26:24]
            }
        }

        // Reconstruct complete result string from stack [00:27:09]
        StringBuilder answer = new StringBuilder();
        while (!mainStack.isEmpty()) {
            answer.insert(0, mainStack.pop()); // Prepend elements to preserve order [00:27:26]
        }

        return answer.toString();
    }

    // APPROACH 2: Recursive DFS Strategy
    // Uses implicit call stack to resolve nested bracket sub-problems naturally.
    private static int globalIndex = 0; // Class-level index tracker for recursive parsing

    public static String decodeStringRecursive(String s) {
        globalIndex = 0; // Reset index before execution
        return dfsDecode(s);
    }

    private static String dfsDecode(String s) {
        StringBuilder result = new StringBuilder();
        int num = 0;

        while (globalIndex < s.length()) {
            char ch = s.charAt(globalIndex);

            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            } else if (ch == '[') {
                globalIndex++; // Step inside brackets
                String nestedStr = dfsDecode(s); // Solve nested inner bracket sub-problem
                while (num > 0) {
                    result.append(nestedStr);
                    num--;
                }
            } else if (ch == ']') {
                return result.toString(); // Return completed sub-problem result
            } else {
                result.append(ch);
            }
            globalIndex++;
        }

        return result.toString();
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Simulating nested bracket expansion using stream reduction operations 
    // requires boxing characters into intermediate collections, adding memory overhead.
    public static String decodeStringStream(String s) {
        if (s == null || s.length() == 0) return "";

        Stack<Integer> counts = new Stack<>();
        Stack<StringBuilder> strings = new Stack<>();
        StringBuilder current = new StringBuilder();
        int k = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                k = k * 10 + (ch - '0');
            } else if (ch == '[') {
                counts.push(k);
                strings.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (ch == ']') {
                StringBuilder decoded = strings.pop();
                int repeatCount = counts.pop();
                String toAppend = current.toString();
                
                // Stream-based string replication
                String repeated = IntStream.range(0, repeatCount)
                        .mapToObj(i -> toAppend)
                        .collect(Collectors.joining());
                
                current = decoded.append(repeated);
            } else {
                current.append(ch);
            }
        }

        return current.toString();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Simple Brackets) ---
        String test1 = "3[a]2[bc]";
        String res1_1 = decodeStringOptimal(test1);
        String res1_2 = decodeStringRecursive(test1);
        String res1_3 = decodeStringStream(test1);

        System.out.println("Test Case 1: \"3[a]2[bc]\"");
        System.out.println("Approach 1 (Two Stacks) Result: " + res1_1);
        System.out.println("Approach 2 (Recursive)  Result: " + res1_2);
        System.out.println("Approach 3 (Stream API) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1.equals("aaabcbc") && res1_2.equals("aaabcbc") && res1_3.equals("aaabcbc") ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Nested Brackets) ---
        String test2 = "3[a2[c]]";
        String res2_1 = decodeStringOptimal(test2);
        String res2_2 = decodeStringRecursive(test2);
        String res2_3 = decodeStringStream(test2);

        System.out.println("Test Case 2: \"3[a2[c]]\"");
        System.out.println("Approach 1 (Two Stacks) Result: " + res2_1);
        System.out.println("Approach 2 (Recursive)  Result: " + res2_2);
        System.out.println("Approach 3 (Stream API) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1.equals("accaccacc") && res2_2.equals("accaccacc") && res2_3.equals("accaccacc") ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (From Video Explanation - Multi-digit Multiplier) ---
        String test3 = "2[a13[c]]";
        String res3_1 = decodeStringOptimal(test3);
        String res3_2 = decodeStringRecursive(test3);
        String res3_3 = decodeStringStream(test3);

        System.out.println("Test Case 3: \"2[a13[c]]\"");
        System.out.println("Approach 1 (Two Stacks) Result: " + res3_1);
        System.out.println("Approach 2 (Recursive)  Result: " + res3_2);
        System.out.println("Approach 3 (Stream API) Result: " + res3_3);
        boolean pass3 = res3_1.equals(res3_2) && res3_2.equals(res3_3);
        System.out.println("Verification: " + (pass3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}