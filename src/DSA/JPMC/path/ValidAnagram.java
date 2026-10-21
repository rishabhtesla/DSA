package DSA.JPMC.path;

/**
 * PROBLEM: Valid Anagram
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 * 
 * EXAMPLE:
 * Input: s = "anagram", t = "nagaram" -> Output: true
 * Input: s = "rat", t = "car"         -> Output: false
 * 
 * LOGIC:
 * 1. If lengths are different, they cannot be anagrams.
 * 2. Create an integer array of size 26 (to represent the lowercase English alphabet).
 * 3. Iterate through the strings. For string 's', INCREMENT the count at the character's index.
 *    For string 't', DECREMENT the count at the character's index.
 *    (e.g., char 'a' - 'a' = index 0. char 'z' - 'a' = index 25).
 * 4. At the end, if they are perfect anagrams, every index in the array will be exactly 0.
 * 
 * DRY RUN: s="aba", t="baa"
 * - i=0: s='a' (arr[0]++), t='b' (arr[1]--). Array: [1, -1, 0...]
 * - i=1: s='b' (arr[1]++), t='a' (arr[0]--). Array: [0, 0, 0...]
 * - i=2: s='a' (arr[0]++), t='a' (arr[0]--). Array: [0, 0, 0...]
 * All zeroes -> True.
 * 
 * COMPLEXITY: Time: O(N) where N is length of string. Space: O(1) (array size is fixed at 26).
 */
/**
 * Learning supplement: sorting both strings costs O(n log n); frequency
 * counting is O(n) time and O(alphabet) space. Examples:
 * {@code "anagram","nagaram" -> true} and {@code "rat","car" -> false}.
 * Invariant: counts are balanced for the processed characters. Dry run adds
 * counts for anagram and subtracts counts for nagaram, leaving all zero.
 * Ask how differing lengths can be rejected immediately.
 */
public class ValidAnagram {
    public static boolean isAnagram(String s, String t) {
        // If lengths don't match, they can't be anagrams
        if (s.length() != t.length()) {
            return false;
        }

        // Frequency array for 26 lowercase English letters
        int[] charCounts = new int[26];

        // Increment for characters in 's', decrement for characters in 't'
        for (int i = 0; i < s.length(); i++) {
            charCounts[s.charAt(i) - 'a']++;
            charCounts[t.charAt(i) - 'a']--;
        }

        // Check if any count is non-zero
        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("Is anagram? " + isAnagram("anagram", "nagaram")); // true
        System.out.println("Is anagram? " + isAnagram("rat", "car"));         // false
    }
}