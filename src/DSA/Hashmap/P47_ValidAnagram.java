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