package DSA.slidingwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * [37 / 38] - SUBSTRING WITH CONCATENATION OF ALL WORDS (LeetCode 30)
 * ============================================================================
 * 
 * PROBLEM:
 *   You are given a string s and an array of strings words. All words are of the
 *   EXACT SAME LENGTH. Return the starting indices of all substrings in s that are
 *   a concatenation of each word in words exactly once and without intervening characters.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Let:
 *     - `wordLen = words[0].length()`
 *     - `numWords = words.length`
 *     - `totalLen = wordLen * numWords`
 *   - Grouped Sliding Window by Offset (Aha!):
 *     Because words have fixed length `wordLen`, any valid concatenated window must align
 *     with one of `wordLen` starting offsets: `offset = 0, 1, ..., wordLen - 1`.
 *     For each fixed offset:
 *     - Move a chunked sliding window jumping by `wordLen` steps.
 *     - Maintain word frequencies seen in current window with `seenMap` and track `wordsMatched`.
 *     - If word is invalid or seen too many times, slide `left` forward by chunks of `wordLen`
 *       until valid again.
 *     - If `wordsMatched == numWords`, add `left` to answer.
 *
 * COMPLEXITY:
 *   - Time:  O(wordLen * (n / wordLen)) = O(n) total word comparisons/hash operations.
 *   - Space: O(k) where k is total unique words stored in frequency maps.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P37_SubstringWithConcatenationOfAllWords {

    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0) return result;

        int wordLen = words[0].length();
        int numWords = words.length;
        int totalLen = wordLen * numWords;
        int sLen = s.length();

        if (sLen < totalLen) return result;

        Map<String, Integer> targetCount = new HashMap<>();
        for (String w : words) {
            targetCount.put(w, targetCount.getOrDefault(w, 0) + 1);
        }

        // Run sliding window for each modular offset from 0 to wordLen - 1
        for (int offset = 0; offset < wordLen; offset++) {
            int left = offset;
            int right = offset;
            Map<String, Integer> seenCount = new HashMap<>();
            int matched = 0;

            while (right + wordLen <= sLen) {
                String sub = s.substring(right, right + wordLen);
                right += wordLen;

                if (targetCount.containsKey(sub)) {
                    seenCount.put(sub, seenCount.getOrDefault(sub, 0) + 1);
                    matched++;

                    // If word occurs more times than required, shrink from left
                    while (seenCount.get(sub) > targetCount.get(sub)) {
                        String leftWord = s.substring(left, left + wordLen);
                        seenCount.put(leftWord, seenCount.get(leftWord) - 1);
                        matched--;
                        left += wordLen;
                    }

                    // Found a valid concatenated substring
                    if (matched == numWords) {
                        result.add(left);
                    }
                } else {
                    // Invalid word: reset window to point right after this invalid chunk
                    seenCount.clear();
                    matched = 0;
                    left = right;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String s1 = "barfoothefoobarman";
        String[] words1 = {"foo", "bar"};
        System.out.println("P37 Output (Test 1): " + findSubstring(s1, words1)); 
        // Expected: [0, 9]

        String s2 = "wordgoodgoodgoodbestword";
        String[] words2 = {"word", "good", "best", "word"};
        System.out.println("P37 Output (Test 2): " + findSubstring(s2, words2)); 
        // Expected: []
    }
}