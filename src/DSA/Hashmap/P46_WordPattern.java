package DSA.Hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [46 / 52] - WORD PATTERN (LeetCode 290)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a `pattern` and a string `s`, find if `s` follows the same pattern.
 * Here "follow" means a full match, such that there is a strict bijection (1-to-1 mapping)
 * between a letter in `pattern` and a non-empty word in `s` (i.e., no two letters map
 * to the same word, and no two words map to the same letter).
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Single-Map Approach:
 *    - Mapping `char -> word` without tracking reverse mappings. Fails because two different
 *      characters could erroneously map to the same word (e.g., pattern="abba", s="dog dog dog dog").
 *
 * 2. Optimal Dual-Map Approach (O(n) Time | O(w) Space):
 *    - **Step 1:** Split string `s` by spaces into a `words` array. If `pattern.length() != words.length`,
 *      return `false` immediately.
 *    - **Step 2:** Maintain two hash maps (`charToWord` and `wordToChar`) to enforce a **mutual bijection**.
 *    - **Step 3:** For each index, check if character `c` maps to a different word, or if word `w`
 *      maps to a different character. If either violation occurs, return `false`. Otherwise, record both mappings.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) where `n` is the total length of characters processed during splitting and hashing words.
 * - Space: O(w) where `w` is the number of unique words/characters stored in the lookup maps.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `pattern = "abba"`, `s = "dog cat cat dog"`  -> `true`
 * - `pattern = "abba"`, `s = "dog cat cat fish"` -> `false`
 * - `pattern = "aaaa"`, `s = "dog cat cat dog"`  -> `false`
 *
 * VISUAL DRY RUN (for `pattern = "abba"`, `s = "dog cat cat dog"`):
 *   - Length check: 4 == 4 (passes).
 *   - i = 0: c = 'a', w = "dog". Both maps are empty -> puts 'a'-> "dog" and "dog"->'a'.
 *   - i = 1: c = 'b', w = "cat". Both maps are empty -> puts 'b'-> "cat" and "cat"->'b'.
 *   - i = 2: c = 'b', w = "cat". Matches existing mappings ('b'-> "cat" and "cat"->'b').
 *   - i = 3: c = 'a', w = "dog". Matches existing mappings ('a'-> "dog" and "dog"->'a').
 *   - Returns `true`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why are two hash maps required instead of just one?
 *    (Two maps are strictly necessary to enforce a bidirectional bijection. A single map from character to word
 *    prevents a character from pointing to multiple words, but doesn't prevent multiple distinct characters
 *    from pointing to the exact same word.)
 * 2. What edge cases are handled automatically?
 *    (Length mismatches between the pattern string length and the split word array length are caught upfront.)
 */
public class P46_WordPattern {

    public static boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) {
            return false;
        }

        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String w = words[i];

            if (charToWord.containsKey(c) && !charToWord.get(c).equals(w)) {
                return false;
            }
            if (wordToChar.containsKey(w) && wordToChar.get(w) != c) {
                return false;
            }

            charToWord.put(c, w);
            wordToChar.put(w, c);
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("P46 Output (abba, dog cat cat dog):  " + wordPattern("abba", "dog cat cat dog"));  // Expected: true
        System.out.println("P46 Output (abba, dog cat cat fish): " + wordPattern("abba", "dog cat cat fish")); // Expected: false
        System.out.println("P46 Output (aaaa, dog cat cat dog):  " + wordPattern("aaaa", "dog cat cat dog"));  // Expected: false
    }
}