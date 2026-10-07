package DSA.Hashmap;

/**
 * ============================================================================
 * [44 / 52] - RANSOM NOTE (LeetCode 383)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given two strings ransomNote and magazine, return true if ransomNote can be
 *   constructed by using the letters from magazine, or false otherwise.
 *   Each letter in magazine can only be used once in ransomNote.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Character Frequency Table:
 *     Since inputs contain only lowercase English letters ('a' through 'z'),
 *     an integer array of size 26 outperforms `HashMap<Character, Integer>`.
 *   - Algorithm:
 *     1. If `ransomNote.length() > magazine.length()`, return false immediately.
 *     2. Count frequencies of each character in `magazine`.
 *     3. Decrement frequencies while scanning `ransomNote`.
 *     4. If count drops below 0 for any character, magazine lacks sufficient letters.
 *
 * COMPLEXITY:
 *   - Time:  O(m + n) where m = ransomNote.length(), n = magazine.length().
 *   - Space: O(1) - Fixed integer array of size 26.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P44_RansomNote {

    public static boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        int[] counts = new int[26];

        for (int i = 0; i < magazine.length(); i++) {
            counts[magazine.charAt(i) - 'a']++;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            int idx = ransomNote.charAt(i) - 'a';
            counts[idx]--;
            if (counts[idx] < 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("P44 Output (a, b):   " + canConstruct("a", "b"));     // Expected: false
        System.out.println("P44 Output (aa, ab):  " + canConstruct("aa", "ab"));   // Expected: false
        System.out.println("P44 Output (aa, aab): " + canConstruct("aa", "aab"));  // Expected: true
    }
}