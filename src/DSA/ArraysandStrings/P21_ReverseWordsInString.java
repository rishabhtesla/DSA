package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [21 / 24] - REVERSE WORDS IN A STRING (LeetCode 151)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an input string `s`, reverse the order of the words.
 * Words are defined as sequences of non-space characters separated by at least one space.
 * Return a string of the words in reverse order concatenated by a single space,
 * with no leading, trailing, or multiple spaces between them.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Regex-Based Approach:
 *    - `s.trim().split("\\s+")` followed by a reverse loop and joining.
 *    - Inefficient because it triggers heavy regex parsing, multiple temporary array
 *      allocations, and creates unnecessary string objects.
 *
 * 2. Optimal Backward Word Extraction Approach (O(n) Time | O(n) Space):
 *    - Since we want the words in reverse order, scanning the string **backwards** from right to left
 *      allows us to extract and append words directly in the correct final order.
 *    - **Step 1:** Start a pointer `i` at `s.length() - 1` and skip any trailing or intervening spaces backwards.
 *    - **Step 2:** Mark the end of a word at `end = i`.
 *    - **Step 3:** Continue scanning backwards until hitting a space or the beginning of the string to find `start = i + 1`.
 *    - **Step 4:** Extract the substring `s.substring(start, end + 1)` and append it to a `StringBuilder`,
 *      ensuring a single space separates words.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear backward pass over the characters of the string.
 * - Space: O(n) - `StringBuilder` required to store and return the final reordered string.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - "the sky is blue"     -> "blue is sky the"
 * - "  hello world  "     -> "world hello"
 * - "a good   example"    -> "example good a"
 *
 * VISUAL DRY RUN (for `"the sky is blue"`):
 *   - Start: `i = 14` (pointing at the last character 'e' of "blue")
 *   - Word 1: `end = 14`, backward scan stops at space before "blue" (`i = 9`, `start = 10`).
 *     - Substring extracted: `"blue"` -> Appended to `sb`.
 *   - Spaces skipped (`i` moves back past spaces to 's' of "is").
 *   - Word 2: `end = 8`, backward scan finds start at `i = 6` (`start = 7`).
 *     - Substring extracted: `"is"` -> Appended with space: `"blue is"`.
 *   - Word 3: extracts `"sky"` -> `"blue is sky"`
 *   - Word 4: extracts `"the"` -> `"blue is sky the"`
 *   - Returns: `"blue is sky the"`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we append a space *before* appending a word (when `sb.length() > 0`) instead of after?
 *    (Doing it before ensures we never leave a trailing space at the very end of our final result string.)
 * 2. How are multiple consecutive spaces (like `"a good   example"`) naturally handled?
 *    (The inner space-skipping loop effortlessly devours any number of consecutive spaces in O(1) time per batch.)
 */
public class P21_ReverseWordsInString {

    public static String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;

        while (i >= 0) {
            // Skip spaces backwards
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }
            if (i < 0) break;

            int end = i;

            // Move to start of the word
            while (i >= 0 && s.charAt(i) != ' ') {
                i--;
            }

            int start = i + 1;

            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(s, start, end + 1);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("P21 Output: \"" + reverseWords("the sky is blue") + "\"");
        // Expected: "blue is sky the"
        System.out.println("P21 Output: \"" + reverseWords("  hello world  ") + "\"");
        // Expected: "world hello"
        System.out.println("P21 Output: \"" + reverseWords("a good   example") + "\"");
        // Expected: "example good a"
    }
}