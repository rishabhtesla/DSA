package DSA.ArraysandStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * [24 / 24] - TEXT JUSTIFICATION (LeetCode 68)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of strings `words` and a width `maxWidth`, format the text such
 * that each line has exactly `maxWidth` characters and is fully (left and right) justified.
 *
 * Rules:
 * 1. Pack as many words as possible per line (greedy approach).
 * 2. Pad extra spaces between words as evenly as possible. If the number of spaces
 *    does not divide evenly, distribute extra spaces starting from the left gaps.
 * 3. The last line and lines with only 1 word must be left-justified (no extra spaces
 *    between words, with padding spaces added at the very end).
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Greedy Line Grouping Approach (O(N) Time | O(N) Space):
 *    - **Step 1:** Pack words into the current line as long as the cumulative length satisfies:
 *      `currentWordsLength + wordCount - 1 + nextWord.length() <= maxWidth`.
 *    - **Step 2:** Handle formatting based on two distinct control flows:
 *      - **Case 1 (Last Line or Single Word):** Join words with a single space, then pad right with spaces
 *        until length equals `maxWidth`.
 *      - **Case 2 (Fully Justified Line):**
 *        - Calculate `totalSpaces = maxWidth - lineLetters`.
 *        - Calculate `gaps = wordCount - 1`.
 *        - Determine `baseSpaces = totalSpaces / gaps` and `extraSpaces = totalSpaces % gaps`.
 *        - Distribute `baseSpaces + 1` to the first `extraSpaces` gaps, and `baseSpaces` to the remaining gaps.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(N) - Processes every character across all words linearly.
 * - Space: O(N) - Storage for the resulting list of justified strings and line builders.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE: `words = ["This", "is", "an", "example", "of", "text", "justification."]`, `maxWidth = 16`
 *
 * VISUAL DRY RUN:
 *   - Line 1: `["This", "is", "an"]` (10 letters, 2 gaps). Total spaces = 6. Split as 3 and 3 -> `"This    is    an"`
 *   - Line 2: `["example", "of", "text"]` (14 letters, 2 gaps). Total spaces = 2. Split as 1 and 1 -> `"example  of text"`
 *   - Line 3: `["justification."]`: Left-justified and padded right -> `"justification.  "`
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we check `j == n` alongside `wordCount == 1` for left justification?
 *    (Because per the problem description, the final line of text must always be left-justified,
 *    regardless of how many words it contains.)
 * 2. How does the modulo operation (`extraSpaces = totalSpaces % gaps`) handle uneven space distribution?
 *    (It ensures that any remainder spaces are assigned progressively to the leftmost gaps first.)
 */
public class P24_TextJustification {

    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int i = 0;
        int n = words.length;

        while (i < n) {
            int j = i;
            int lineLetters = 0;

            // Greedily find how many words fit in current line with at least 1 space between them
            while (j < n && lineLetters + words[j].length() + (j - i) <= maxWidth) {
                lineLetters += words[j].length();
                j++;
            }

            int wordCount = j - i;
            StringBuilder line = new StringBuilder();

            // Case 1: Last line OR line with only 1 word -> Left justified
            if (j == n || wordCount == 1) {
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) line.append(' ');
                }
                while (line.length() < maxWidth) {
                    line.append(' ');
                }
            }
            // Case 2: Fully justified line
            else {
                int totalSpaces = maxWidth - lineLetters;
                int gaps = wordCount - 1;
                int baseSpaces = totalSpaces / gaps;
                int extraSpaces = totalSpaces % gaps;

                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) {
                        int spacesToApply = baseSpaces + (k - i < extraSpaces ? 1 : 0);
                        for (int s = 0; s < spacesToApply; s++) {
                            line.append(' ');
                        }
                    }
                }
            }

            result.add(line.toString());
            i = j; // Move to next line
        }

        return result;
    }

    public static void main(String[] args) {
        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        List<String> justified = fullJustify(words, 16);
        System.out.println("P24 Output:");
        for (String line : justified) {
            System.out.println("\"" + line + "\"");
        }
        /* Expected:
         * "This    is    an"
         * "example  of text"
         * "justification.  "
         */
    }
}