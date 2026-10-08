package DSA.Hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [46 / 52] - WORD PATTERN (LeetCode 290)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a pattern and a string s, find if s follows the same pattern.
 *   Follow means a full match, such that there is a bijection between a letter
 *   in pattern and a non-empty word in s.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Split `s` into words array. If `words.length != pattern.length()`, return false.
 *   - To ensure a strict bijection:
 *     1. Map `char -> word`
 *     2. Map `word -> char` (or use a `Set<String>` for taken words)
 *   - Alternatively, Java's `map.put(key, val)` returns the PREVIOUS value associated
 *     with that key. Storing indices in two maps and checking return values gives
 *     an elegant symmetry check.
 *
 * COMPLEXITY:
 *   - Time:  O(n) where n is total characters in s (splitting and hashing words).
 *   - Space: O(w) where w is the number of unique words/characters.
 *
 *
 * EXAMPLE:
 *   The first main input is pattern="abba", s="dog cat cat dog"; expected result is true.
 *
 * VISUAL DRY RUN:
 *   Pairs are a/dog (map both ways), b/cat (map both ways), b/cat (consistent),
 *   a/dog (consistent). Both maps end as a<->dog,b<->cat; return true.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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