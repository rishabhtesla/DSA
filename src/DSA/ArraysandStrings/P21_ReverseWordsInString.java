package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [21 / 24] - REVERSE WORDS IN A STRING (LeetCode 151)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an input string s, reverse the order of the words. Words are separated
 *   by at least one space. Return a string of the words in reverse order concatenated
 *   by a single space, with no leading, trailing, or multiple spaces.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Direct Backward Word Extraction:
 *     Instead of tokenizing, trimming, reversing, and joining (which creates lots of temp strings):
 *     1. Start index `i = s.length() - 1`.
 *     2. Skip spaces backward to find the end of a word (`end = i`).
 *     3. Continue backward until finding a space or the start of the string (`start = i + 1`).
 *     4. Append `s.substring(start, end + 1)` and a single space separator.
 *     5. Trim the trailing space at the end.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single backward pass over the string.
 *   - Space: O(n) - StringBuilder for final result.
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