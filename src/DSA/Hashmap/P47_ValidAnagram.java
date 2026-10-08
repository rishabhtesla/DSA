package DSA.Hashmap;

/**
 * ============================================================================
 * [47 / 52] - VALID ANAGRAM (LeetCode 242)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 *   An anagram is a word formed by rearranging the letters of another word.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Equal lengths is a prerequisite: if `s.length() != t.length()`, return false.
 *   - Single-Pass Frequency Array:
 *     Increment count for `s.charAt(i)` and decrement for `t.charAt(i)` simultaneously.
 *     If they are anagrams, every bucket in `int[26]` must end at exactly 0.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single linear scan.
 *   - Space: O(1) - Fixed integer array of size 26.
 *
 *
 * EXAMPLE:
 *   The first main strings are "anagram" and "nagaram"; expected result is true.
 *
 * VISUAL DRY RUN:
 *   Increment counts for a,n,a,g,r,a,m, then decrement while reading n,a,g,a,r,a,m.
 *   Every count stays non-negative and finishes at zero, so the strings are anagrams.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P47_ValidAnagram {

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] counts = new int[26];

        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }

        for (int c : counts) {
            if (c != 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("P47 Output (anagram, nagaram): " + isAnagram("anagram", "nagaram")); // Expected: true
        System.out.println("P47 Output (rat, car):         " + isAnagram("rat", "car"));         // Expected: false
    }
}