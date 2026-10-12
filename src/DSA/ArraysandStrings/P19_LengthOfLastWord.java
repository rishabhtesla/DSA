package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [19 / 24] - LENGTH OF LAST WORD (LeetCode 58)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a string `s` consisting of words and spaces, return the length of the
 * last word in the string.
 * A word is defined as a maximal substring consisting of non-space characters only.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Built-in Approach:
 *    - `s.trim().split(" ")` or regex-based splitting.
 *    - Inefficient because it scans the entire string, creates multiple intermediate string/array allocations,
 *      and handles unnecessary memory overhead.
 *
 * 2. Optimal Backward Scan Approach (O(n) Time | O(1) Space):
 *    - Since we only care about the *last* word, scanning from the right end of the string backwards
 *      is far more efficient than processing from the left.
 *    - **Step 1:** Start a pointer `p` at `s.length() - 1` and skip any trailing spaces backwards.
 *    - **Step 2:** Once we hit the first letter of the last word, continue scanning backwards and
 *      increment a `length` counter until we hit another space or reach the beginning of the string (`p < 0`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) worst-case (e.g., a single long word with no spaces), but effectively O(k)
 *           where `k` is the length of the last word plus trailing spaces.
 * - Space: O(1) - Only uses two integer trackers (`p` and `length`), requiring zero extra memory allocations.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - "Hello World"           -> 5 ("World")
 * - "   fly me   to the moon  " -> 4 ("moon")
 *
 * VISUAL DRY RUN (for "Hello World"):
 *   - Start: `p = 10` (pointing at 'd'), `length = 0`
 *   - Backward Scan for Word:
 *     - p = 10 ('d'): non-space -> `length = 1`, `p = 9`
 *     - p = 9 ('l'): non-space  -> `length = 2`, `p = 8`
 *     - p = 8 ('r'): non-space  -> `length = 3`, `p = 7`
 *     - p = 7 ('o'): non-space  -> `length = 4`, `p = 6`
 *     - p = 6 ('W'): non-space  -> `length = 5`, `p = 5`
 *     - p = 5 (' '): space      -> Loop terminates.
 *   - Returns: 5.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is scanning backwards superior to using built-in string functions in an interview setting?
 *    (It demonstrates an understanding of optimal memory usage and avoids unnecessary array/string allocations.)
 * 2. What edge cases are handled automatically?
 *    (Strings with excessive trailing spaces, single-word strings, or strings consisting entirely of spaces.)
 */
public class P19_LengthOfLastWord {

    public static int lengthOfLastWord(String s) {
        int p = s.length() - 1;

        // Skip trailing spaces
        while (p >= 0 && s.charAt(p) == ' ') {
            p--;
        }

        // Count length of last word
        int length = 0;
        while (p >= 0 && s.charAt(p) != ' ') {
            length++;
            p--;
        }

        return length;
    }

    public static void main(String[] args) {
        System.out.println("P19 Output (Hello World):         " + lengthOfLastWord("Hello World"));                 // Expected: 5
        System.out.println("P19 Output (   fly me to moon):  " + lengthOfLastWord("   fly me   to the moon  ")); // Expected: 4
    }
}