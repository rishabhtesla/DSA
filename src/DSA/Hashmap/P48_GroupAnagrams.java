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
 * PROBLEM:
 *   Given an array of strings strs, group the anagrams together. You can return
 *   the answer in any order.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Canonical Key Hashing:
 *     Two strings are anagrams if and only if they produce the same canonical key.
 *   - Option A: Sort characters of each string -> O(K log K) per string.
 *   - Option B: Count frequencies into a 26-char signature (e.g., "#1#0#2...") -> O(K) per string.
 *     In practice with short strings (lengths <= 100), `char[] -> Arrays.sort() -> String.valueOf()`
 *     is clean, fast, and cache-friendly.
 *   - Group using `Map<String, List<String>>`.
 *
 * COMPLEXITY:
 *   - Time:  O(N * K log K) where N = strs.length and K = max word length.
 *   - Space: O(N * K) to store the grouped lists and map entries.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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