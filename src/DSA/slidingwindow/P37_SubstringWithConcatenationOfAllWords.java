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
 * PROBLEM STATEMENT:
 * You are given a string `s` and an array of strings `words`. All words are of the
 * **EXACT SAME LENGTH**. Return the starting indices of all substrings in `s` that are
 * a concatenation of each word in `words` exactly once and without any intervening characters.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach:
 *    - Check every substring of length `totalLen` in `s`, slice it into word chunks,
 *      and verify frequency matches using hash maps.
 *    - Inefficient: re-parses and re-hashes overlapping windows repeatedly, leading to a high time complexity.
 *
 * 2. Optimal Grouped Sliding Window by Offset Approach (O(wordLen * (n / wordLen)) = O(n) Time | O(k) Space):
 *    - **The Core Insight:** Because all words have a fixed length (`wordLen`), any valid concatenated window
 *      must align with one of `wordLen` starting offsets (`offset = 0, 1, ..., wordLen - 1`).
 *    - **Step 1:** Build a target frequency map (`targetCount`) for the words array.
 *    - **Step 2:** For each fixed modular `offset`, sweep through the string using a chunked sliding window
 *      that jumps forward by `wordLen` increments.
 *    - **Step 3:** Maintain a `seenCount` frequency map. If a word is valid and within target counts, increment matches.
 *      If a word exceeds its allowed frequency, shrink the window from the left by `wordLen` chunks.
 *      If an invalid word is encountered, completely reset the window and restart after the invalid chunk.
 *    - **Step 4:** Record the `left` index whenever `matched == numWords`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(wordLen * (n / wordLen)) = O(n) total linear string passes and hash map lookups.
 * - Space: O(k) where $k$ is the total number of unique words stored in the frequency maps.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `s = "barfoothefoobarman"`, `words = ["foo", "bar"]`
 * - Expected Starting Indices: `[0, 9]`
 *
 * VISUAL DRY RUN (for `offset = 0`):
 *   - Target counts: `{"foo": 1, "bar": 1}`. `wordLen = 3`, `numWords = 2`.
 *   - Reads `"bar"`, then `"foo"`. Both match target counts. `matched == 2` -> records index `0`.
 *   - Next chunk is `"the"` (invalid word) -> clears window and resets `left` to follow it.
 *   - Later reads `"foo"`, then `"bar"` starting at index `9` -> records index `9`.
 *   - Returns `[0, 9]`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we need to loop over `offset` from `0` to `wordLen - 1`?
 *    (Because words can start at any character index from 0 up to `wordLen - 1`, iterating through each possible
 *    modular offset ensures we don't miss valid concatenated substrings that don't begin at index 0.)
 * 2. Why is resetting the window upon encountering an invalid word safe and efficient?
 *    (An invalid word breaks any contiguous concatenation sequence entirely. Resetting the window past the invalid chunk
 *    prevents redundant scanning over blocks that can never form a valid combination.)
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

        // Build target frequency map for words
        Map<String, Integer> targetCount = new HashMap<>();
        for (String w : words) {
            targetCount.put(w, targetCount.getOrDefault(w, 0) + 1);
        }

        // Run the chunked sliding window for each possible modular offset
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

                    // If a word occurs more times than required, contract the window from the left
                    while (seenCount.get(sub) > targetCount.get(sub)) {
                        String leftWord = s.substring(left, left + wordLen);
                        seenCount.put(leftWord, seenCount.get(leftWord) - 1);
                        matched--;
                        left += wordLen;
                    }

                    // If all words match the target counts, record the starting left index
                    if (matched == numWords) {
                        result.add(left);
                    }
                } else {
                    // Invalid word encountered: reset window state completely
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