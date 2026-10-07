package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * PROBLEM STATEMENT:
 * Given an input string 's', reverse the order of the words [00:00:21].
 * A word is defined as a sequence of non-space characters. The words in 's' will be separated 
 * by at least one space [00:00:26].
 * Return a string of the words in reverse order concatenated by a single space [00:00:30].
 * Note that 's' may contain leading or trailing spaces or multiple spaces between two words [00:00:35]. 
 * The returned string should only have a single space separating the words. Do not include any extra spaces [00:00:46].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "the sky is blue" [00:01:13]
 * - Process: Split words -> ["the", "sky", "is", "blue"]. Reverse order -> ["blue", "is", "sky", "the"].
 * - Result: "blue is sky the"
 * 
 * Example 2: s = "  hello world  " (Simulated inside the video explanation [00:01:11, 00:01:41])
 * - Process:
 *   - Input contains leading and trailing spaces: "  hello world  " [00:00:35].
 *   - Split by regex `\s+` (one or more spaces) to extract words: ["hello", "world"] [00:02:48, 00:03:12].
 *   - Traverse array backward from index `length - 1` down to 0: append "world ", then append "hello " [00:03:35, 00:05:22].
 *   - Trim trailing space created by concatenation step using `.trim()` [00:03:49, 00:06:26].
 * - Result: "world hello"
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Regex Split with Reverse Concat & Trim):
 * • Index Initialization: Array index variable 'i' starts at `arr.length - 1` and decrements down to 0 [00:05:04].
 * • Condition Boundaries: Loop continues while `i >= 0` [00:05:07].
 * • Operational Steps:
 *   1. Split the string using regex `s.split("\\s+")` to separate words by one or more whitespace characters [00:04:12].
 *   2. Instantiate a `StringBuilder` buffer for efficient string accumulation [00:04:46].
 *   3. Iterate backward through the array of words, appending `arr[i] + " "` to the buffer [00:05:16].
 *   4. Convert the buffer to a string and call `.trim()` to remove any trailing space added during concatenation [00:06:06, 00:06:26].
 * • Time Complexity: O(n) - Single pass for regex splitting and another linear pass for string reconstruction.
 * • Space Complexity: O(n) - Memory allocated for the split word array and `StringBuilder` buffer [00:03:29].
 * • LOGIC BEHIND THIS APPROACH:
 *   Strings in Java are immutable [00:04:34]. Using `StringBuilder` avoids generating intermediate string objects during 
 *   concatenation [00:04:46]. Splitting on `\\s+` automatically handles variable amounts of whitespace between words [00:02:48, 00:03:01]. 
 *   Traversing the resulting array backward places the words in reverse order, and `.trim()` cleanly removes any extra trailing space [00:05:36].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Regex Split - s = "  a good   example  "):
 * Step 1: s.trim().split("\\s+") -> arr = ["a", "good", "example"] [00:03:12]
 * StringBuilder State (sb = ""):
 * i = 2 (arr[2] = "example"): sb.append("example ") -> sb = "example " [00:05:16]
 * i = 1 (arr[1] = "good")   : sb.append("good ")    -> sb = "example good "
 * i = 0 (arr[0] = "a")      : sb.append("a ")       -> sb = "example good a "
 * Step 2: Convert to String -> "example good a "
 * Step 3: Apply .trim() -> "example good a" [00:06:26].
 * Output = "example good a".
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Two-Pointer Character Scanning - s = "the sky"):
 * Scan backward starting at index i = 6 ('y'):
 * - Find end of word at index 6 ('y')
 * - Move left until space is found -> start of word at index 4 ('s')
 * - Append substring(4, 7) -> "sky "
 * - Move left past spaces -> find end of next word at index 2 ('e')
 * - Move left until boundary -> start of word at index 0 ('t')
 * - Append substring(0, 3) -> "sky the "
 * - Trim output -> "sky the".
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Pipeline - s = "the sky is blue"):
 * Step 1: Split using regex `\s+` -> Stream["the", "sky", "is", "blue"]
 * Step 2: Collect into list -> ["the", "sky", "is", "blue"]
 * Step 3: Collections.reverse() -> ["blue", "is", "sky", "the"]
 * Step 4: String.join(" ", list) -> "blue is sky the"
 * Output = "blue is sky the".
 */
public class ReverseWordsInString {

    // APPROACH 1: Regex Split with Reverse Concat & Trim (Anchor Strategy)
    public static String reverseWordsOptimal(String s) {
        if (s == null || s.length() == 0) return "";

        // Split by one or more whitespace characters [00:04:12]
        String[] words = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();

        // Traverse words in reverse order [00:05:04]
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]).append(" "); // Append word followed by space delimiter [00:05:16]
        }

        // Convert to string and trim trailing extra space [00:06:26]
        return sb.toString().trim();
    }

    // APPROACH 2: Two-Pointer In-Place Character Scanning (O(1) Auxiliary Heap Optimization)
    // Avoids regex array allocations by scanning the string backward character by character 
    // to identify word boundaries, appending each word to a StringBuilder buffer directly.
    public static String reverseWordsTwoPointer(String s) {
        if (s == null || s.length() == 0) return "";

        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;

        while (i >= 0) {
            // Skip trailing spaces for current word block
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }
            if (i < 0) break;

            int wordEnd = i;

            // Find start index of current word block
            while (i >= 0 && s.charAt(i) != ' ') {
                i--;
            }

            // Extract word and append to result buffer
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(s.substring(i + 1, wordEnd + 1));
        }

        return sb.toString();
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Converting split words into list collections and running Stream joiners 
    // adds object wrapping overhead, but provides a clean, single-expression functional pipeline.
    public static String reverseWordsStream(String s) {
        if (s == null || s.length() == 0) return "";

        List<String> wordsList = Arrays.asList(s.trim().split("\\s+"));
        Collections.reverse(wordsList);

        return String.join(" ", wordsList);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Layout) ---
        String test1 = "the sky is blue";
        String res1_1 = reverseWordsOptimal(test1);
        String res1_2 = reverseWordsTwoPointer(test1);
        String res1_3 = reverseWordsStream(test1);

        System.out.println("Test Case 1: \"the sky is blue\"");
        System.out.println("Approach 1 (Regex Split)  Result: \"" + res1_1 + "\"");
        System.out.println("Approach 2 (Two Pointer)  Result: \"" + res1_2 + "\"");
        System.out.println("Approach 3 (Stream API)   Result: \"" + res1_3 + "\"");
        System.out.println("Verification: " + (res1_1.equals("blue is sky the") && res1_2.equals("blue is sky the") && res1_3.equals("blue is sky the") ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Leading/Trailing/Multiple Spaces) ---
        String test2 = "  a good   example  ";
        String res2_1 = reverseWordsOptimal(test2);
        String res2_2 = reverseWordsTwoPointer(test2);
        String res2_3 = reverseWordsStream(test2);

        System.out.println("Test Case 2: \"  a good   example  \"");
        System.out.println("Approach 1 (Regex Split)  Result: \"" + res2_1 + "\"");
        System.out.println("Approach 2 (Two Pointer)  Result: \"" + res2_2 + "\"");
        System.out.println("Approach 3 (Stream API)   Result: \"" + res2_3 + "\"");
        System.out.println("Verification: " + (res2_1.equals("example good a") && res2_2.equals("example good a") && res2_3.equals("example good a") ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}