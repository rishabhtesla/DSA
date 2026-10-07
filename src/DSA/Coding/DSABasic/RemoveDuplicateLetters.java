package DSA.Coding.DSABasic;

import java.util.Stack;

/**
 * PROBLEM STATEMENT:
 * Given a string 's', remove duplicate letters so that every letter appears once and only once [00:01:15].
 * You must make sure your result is the lexicographically smallest among all possible results [00:01:22].
 * Note: This question is identical to LeetCode 1081 [00:00:29].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "bcabc" [00:01:08]
 * - Possible deduplicated options: "abc", "cab", "bca", "bac" [00:01:52].
 * - Lexicographically smallest option is "abc" [00:02:44].
 * - Result: "abc"
 * 
 * Example 2: s = "cbacdcbc" (Simulated inside the video explanation [00:04:30, 00:27:43])
 * - Process:
 *   - Record the last index occurrence of each character [00:08:19, 00:28:07]:
 *     'a': 2, 'b': 6, 'c': 7, 'd': 4 [00:09:44, 00:28:58].
 *   - Use a Stack to build the lexicographical order and a boolean array `visited` to track stack membership [00:11:50, 00:15:18].
 *   - i=0 ('c'): Stack = ['c'], visited['c'] = true [00:15:54, 00:30:17].
 *   - i=1 ('b'): 'b' < 'c' and last occurrence of 'c' (7) > 1. Pop 'c', visited['c'] = false. Push 'b', visited['b'] = true. Stack = ['b'] [00:16:12, 00:31:22].
 *   - i=2 ('a'): 'a' < 'b' and last occurrence of 'b' (6) > 2. Pop 'b', visited['b'] = false. Push 'a', visited['a'] = true. Stack = ['a'] [00:16:34, 00:32:57].
 *   - i=3 ('c'): 'c' > 'a'. Push 'c', visited['c'] = true. Stack = ['a', 'c'] [00:17:16, 00:33:58].
 *   - i=4 ('d'): 'd' > 'c'. Push 'd', visited['d'] = true. Stack = ['a', 'c', 'd'] [00:17:29, 00:34:56].
 *   - i=5 ('c'): 'c' is already visited (`visited['c'] == true`), so skip [00:17:44, 00:35:26].
 *   - i=6 ('b'): 'b' < 'd', but last occurrence of 'd' (4) < 6 ('d' won't appear again!). Do not pop 'd'. Push 'b', visited['b'] = true. Stack = ['a', 'c', 'd', 'b'] [00:17:58, 00:36:26].
 *   - i=7 ('c'): 'c' is already visited, so skip [00:18:11, 00:36:42].
 *   - Reconstruct result from stack: "acdb" [00:08:05, 00:37:09].
 * - Result: "acdb"
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Monotonic Stack with Frequency Tracking):
 * • Index Initialization: Array pointer 'i' iterates from 0 to s.length() - 1 [00:22:04].
 * • Condition Boundaries:
 *   - Skip if `visited[ch - 'a']` is true [00:22:35].
 *   - Pop stack while `!stack.isEmpty()`, `stack.peek() > ch`, and `lastIndex[stack.peek() - 'a'] > i` [00:23:15, 00:25:10].
 * • Operational Steps:
 *   1. Pre-calculate last appearance index for each character using an array of size 26 (`lastIndex`) [00:19:10].
 *   2. Iterate string characters. If character is already in stack, skip it [00:22:35].
 *   3. Pop characters from stack if they are larger than current character AND appear again later in the string [00:23:28, 00:24:09].
 *   4. Mark popped character as unvisited in `visited` array [00:24:43].
 *   5. Push current character to stack and mark as visited [00:25:26, 00:25:32].
 *   6. Convert stack to String, reverse, and return [00:26:07, 00:26:26].
 * • Time Complexity: O(n) - Each character is pushed and popped at most once [00:04:40].
 * • Space Complexity: O(1) auxiliary space - Fixed 26-element arrays for alphabet letters [00:08:29, 00:11:59].
 * • LOGIC BEHIND THIS APPROACH:
 *   To get the lexicographically smallest result, we want smaller characters as early as possible (greedy strategy) [00:02:44, 00:06:59]. 
 *   A monotonic increasing stack allows us to remove previously added larger characters when a smaller character arrives [00:15:18, 00:16:07]. 
 *   However, we can only safely discard a larger character if it appears again later in the string (verified by `lastIndex`) [00:07:53, 00:18:02].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Monotonic Stack - s = "cbacdcbc"):
 * lastIndex Array: a:2, b:6, c:7, d:4 [00:28:58]
 * Stack State History:
 * i=0 ('c'): stack = ['c'], visited = {c:true} [00:30:17]
 * i=1 ('b'): 'b' < 'c', lastIndex['c'] (7) > 1 -> pop 'c'. stack = ['b'], visited = {b:true} [00:31:22]
 * i=2 ('a'): 'a' < 'b', lastIndex['b'] (6) > 2 -> pop 'b'. stack = ['a'], visited = {a:true} [00:32:57]
 * i=3 ('c'): 'c' > 'a' -> stack = ['a', 'c'], visited = {a:true, c:true} [00:33:58]
 * i=4 ('d'): 'd' > 'c' -> stack = ['a', 'c', 'd'], visited = {a:true, c:true, d:true} [00:34:56]
 * i=5 ('c'): visited['c'] == true -> skip [00:35:26]
 * i=6 ('b'): 'b' < 'd', but lastIndex['d'] (4) < 6 -> CANNOT pop 'd'. stack = ['a', 'c', 'd', 'b'], visited = {a:true, b:true, c:true, d:true} [00:36:26]
 * i=7 ('c'): visited['c'] == true -> skip [00:36:42]
 * Pop Stack & Reverse [00:37:03]: 'b' -> 'd' -> 'c' -> 'a' -> Reverse = "acdb" [00:37:09].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Array-Based Stack Optimization - s = "cbacdcbc"):
 * Char Array Buffer (`sb`) acts as stack, `top` index pointer:
 * i=0 ('c'): sb[0] = 'c', top = 1
 * i=1 ('b'): 'b' < sb[0] ('c'), lastIndex['c'] (7) > 1 -> top-- (0). sb[0] = 'b', top = 1
 * i=2 ('a'): 'a' < sb[0] ('b'), lastIndex['b'] (6) > 2 -> top-- (0). sb[0] = 'a', top = 1
 * i=3 ('c'): sb[1] = 'c', top = 2
 * i=4 ('d'): sb[2] = 'd', top = 3
 * i=5 ('c'): visited['c'] == true -> skip
 * i=6 ('b'): 'b' < sb[2] ('d'), but lastIndex['d'] (4) < 6 -> stop pop. sb[3] = 'b', top = 4
 * i=7 ('c'): visited['c'] == true -> skip
 * Output = sb.substring(0, top) = "acdb".
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Frequency Pre-pass - s = "bcabc"):
 * Frequency Map via Stream: b:2, c:2, a:1
 * i=0 ('b'): stack = ['b'], freq['b'] = 1
 * i=1 ('c'): stack = ['b', 'c'], freq['c'] = 1
 * i=2 ('a'): 'a' < 'c', freq['c'] (1) > 0 -> pop 'c'. 'a' < 'b', freq['b'] (1) > 0 -> pop 'b'. stack = ['a'], freq['a'] = 0
 * i=3 ('b'): stack = ['a', 'b'], freq['b'] = 0
 * i=4 ('c'): stack = ['a', 'b', 'c'], freq['c'] = 0
 * Output = "abc".
 */
public class RemoveDuplicateLetters {

    // APPROACH 1: Monotonic Stack with Frequency Tracking (Anchor Strategy)
    public static String removeDuplicateLettersOptimal(String s) {
        if (s == null || s.length() == 0) return "";

        // Array to store the last occurrence index of each character [00:19:10]
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i; // Map char to 0-25 index [00:20:47]
        }

        // Boolean array to keep track of characters currently in stack [00:21:04]
        boolean[] present = new boolean[26];
        Stack<Character> stack = new Stack<>(); // Character stack [00:21:34]

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int index = ch - 'a';

            // If character is already present in stack, skip it [00:22:35]
            if (present[index]) continue;

            // Pop elements from stack if:
            // 1. Stack is not empty [00:23:15]
            // 2. Stack top character > current character (lexicographically larger) [00:23:28]
            // 3. Stack top character appears again later in string [00:24:03]
            while (!stack.isEmpty() 
                    && stack.peek() > ch 
                    && lastIndex[stack.peek() - 'a'] > i) {
                
                char popped = stack.pop(); // Remove character from stack [00:25:04]
                present[popped - 'a'] = false; // Mark popped character as unvisited [00:24:43]
            }

            stack.push(ch); // Push current character [00:25:26]
            present[index] = true; // Mark character as present in stack [00:25:32]
        }

        // Reconstruct string from stack [00:25:57]
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop()); // Pop yields reverse sequence [00:26:15]
        }

        return sb.reverse().toString(); // Reverse to get original stack order [00:26:26]
    }

    // APPROACH 2: Array-Based Stack Optimization (O(1) Memory Footprint)
    // Replaces Java's Stack object with a StringBuilder buffer to avoid object wrapping overhead.
    public static String removeDuplicateLettersArrayStack(String s) {
        if (s == null || s.length() == 0) return "";

        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }

        boolean[] visited = new boolean[26];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int idx = ch - 'a';

            if (visited[idx]) continue;

            while (sb.length() > 0 
                    && sb.charAt(sb.length() - 1) > ch 
                    && lastIndex[sb.charAt(sb.length() - 1) - 'a'] > i) {
                
                visited[sb.charAt(sb.length() - 1) - 'a'] = false;
                sb.deleteCharAt(sb.length() - 1);
            }

            sb.append(ch);
            visited[idx] = true;
        }

        return sb.toString();
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Pre-calculating character frequencies via IntStream introduces boxing overhead, 
    // but demonstrates a functional stream pipeline for counting remaining character occurrences.
    public static String removeDuplicateLettersStream(String s) {
        if (s == null || s.length() == 0) return "";

        // Build character frequency map using streams
        int[] countMap = new int[26];
        s.chars().forEach(ch -> countMap[ch - 'a']++);

        boolean[] inStack = new boolean[26];
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int idx = ch - 'a';

            countMap[idx]--; // Decrement remaining frequency count

            if (inStack[idx]) continue;

            while (!stack.isEmpty() 
                    && stack.peek() > ch 
                    && countMap[stack.peek() - 'a'] > 0) {
                
                char removed = stack.pop();
                inStack[removed - 'a'] = false;
            }

            stack.push(ch);
            inStack[idx] = true;
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.reverse().toString();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Layout) ---
        String test1 = "bcabc";
        String res1_1 = removeDuplicateLettersOptimal(test1);
        String res1_2 = removeDuplicateLettersArrayStack(test1);
        String res1_3 = removeDuplicateLettersStream(test1);

        System.out.println("Test Case 1: \"bcabc\"");
        System.out.println("Approach 1 (Monotonic Stack) Result: " + res1_1);
        System.out.println("Approach 2 (Array Buffer)   Result: " + res1_2);
        System.out.println("Approach 3 (Stream Pipeline) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1.equals("abc") && res1_2.equals("abc") && res1_3.equals("abc") ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        String test2 = "cbacdcbc";
        String res2_1 = removeDuplicateLettersOptimal(test2);
        String res2_2 = removeDuplicateLettersArrayStack(test2);
        String res2_3 = removeDuplicateLettersStream(test2);

        System.out.println("Test Case 2: \"cbacdcbc\"");
        System.out.println("Approach 1 (Monotonic Stack) Result: " + res2_1);
        System.out.println("Approach 2 (Array Buffer)   Result: " + res2_2);
        System.out.println("Approach 3 (Stream Pipeline) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1.equals("acdb") && res2_2.equals("acdb") && res2_3.equals("acdb") ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}