package DSA.Hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * [48 / 52] - GROUP ANAGRAMS (LeetCode 49)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of strings `strs`, group the anagrams together.
 * You can return the answer in any order.
 * An anagram is a word or phrase formed by rearranging the letters of a different word,
 * typically using all the original letters exactly once.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Canonical Key Hashing Approach:
 *    - Two strings are anagrams if and only if they share the exact same canonical representation.
 *    - **Option A (Sorting):** Convert each string to a character array, sort it, and convert it back
 *      to a string to serve as the map key. (Takes O(K log K) per string, where K is length).
 *    - **Option B (Frequency Signature):** Count character frequencies into a delimited string signature
 *      (e.g., `#1#0#2...`), taking O(K) time.
 *    - *Interview Best Practice:* For typical interview constraints where word lengths are short (`K <= 100`),
 *      the sorting approach (`char[] -> Arrays.sort() -> String.valueOf()`) is clean, concise,
 *      and extremely fast.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(N * K log K) where `N` is `strs.length` and `K` is the maximum length of a string in `strs`.
 * - Space: O(N * K) to store all grouped lists and hash map key-value entries.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `strs = ["eat", "tea", "tan", "ate", "nat", "bat"]`
 *   -> `[["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]` (in any order)
 *
 * VISUAL DRY RUN (for `["eat", "tea", "tan"]`):
 *   - "eat": sorted chars are `['a', 'e', 't']` -> key: `"aet"`. Map: `{"aet" -> ["eat"]}`
 *   - "tea": sorted chars are `['a', 'e', 't']` -> key: `"aet"`. Map: `{"aet" -> ["eat", "tea"]}`
 *   - "tan": sorted chars are `['a', 'n', 't']` -> key: `"ant"`. Map: `{"aet" -> [...], "ant" -> ["tan"]}`
 *   - Returning `map.values()` aggregates the grouped lists.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why do we use `computeIfAbsent()` in Java instead of manual `containsKey()` checks?
 *    (It provides a concise, idiomatic way to initialize a new `ArrayList` only if the canonical key
 *    does not already exist in the map.)
 * 2. What edge cases are handled upfront?
 *    (Null or empty arrays return an empty list instantly, avoiding runtime exceptions.)
 */
public class P48_GroupAnagrams {

    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }

        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("P48 Output: " + groupAnagrams(strs));
        // Expected: [[eat, tea, ate], [bat], [tan, nat]] (groups in any order)
    }
}