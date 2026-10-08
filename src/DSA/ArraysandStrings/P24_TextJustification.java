package DSA.ArraysandStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * [24 / 24] - TEXT JUSTIFICATION (LeetCode 68)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array of strings words and a width maxWidth, format the text such
 *   that each line has exactly maxWidth characters and is fully (left and right)
 *   justified.
 *   - Pack as many words as possible per line.
 *   - Pad extra spaces between words as evenly as possible. If uneven, distribute
 *     extra spaces starting from the left.
 *   - The last line and lines with only 1 word must be left-justified (no extra
 *     spaces between words, pad spaces at the very end).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Greedy line grouping:
 *     Keep adding words to the line as long as:
 *     `currentWordsLength + wordCount - 1 + nextWord.length() <= maxWidth`.
 *   - Justification Rules:
 *     1. If last line OR line has only 1 word:
 *        Join words with a single space, then pad right with spaces to fill `maxWidth`.
 *     2. Regular line:
 *        `totalSpaces = maxWidth - wordsLength`.
 *        `gaps = wordCount - 1`.
 *        `spacesPerGap = totalSpaces / gaps`.
 *        `extraSpaces = totalSpaces % gaps` (the first `extraSpaces` gaps get +1 space).
 *
 * COMPLEXITY:
 *   - Time:  O(N) total characters processed where N is total length of all words.
 *   - Space: O(N) to construct the justified output lines.
 *
 *
 * EXAMPLE:
 *   The first main words are [This,is,an,example,of,text,justification.] with maxWidth=16.
 *   Expected lines are "This    is    an", "example  of text", and "justification.  ".
 *
 * VISUAL DRY RUN:
 *   Pack words while length<=16: [This,is,an] (10 letters, 2 gaps) gets 6 extra spaces,
 *   split 3/3 -> "This    is    an". Next [example,of,text] gets 2 extra spaces, split
 *   1/1 -> "example  of text". The final word is left-justified and padded -> output above.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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