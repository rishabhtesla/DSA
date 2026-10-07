package DSA.Hashmap;

/**
 * ============================================================================
 * [45 / 52] - ISOMORPHIC STRINGS (LeetCode 205)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given two strings s and t, determine if they are isomorphic.
 *   Two strings are isomorphic if the characters in s can be replaced to get t,
 *   preserving character order with a strict bijection (no two characters in s
 *   may map to the same character in t, and vice versa).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Bijection requires 1-to-1 two-way mapping.
 *   - Single Lookup via "Last Seen Position":
 *     Track the 1-based index where each character was last seen in both strings.
 *     If at any step `lastSeenS[s.charAt(i)] != lastSeenT[t.charAt(i)]`, their
 *     structural pattern deviates -> return false immediately.
 *   - Use 1-based indices (`i + 1`) because arrays default to 0 (unseen).
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass over the strings.
 *   - Space: O(1) - Two fixed ASCII tables of size 256.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P45_IsomorphicStrings {

    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] mapS = new int[256];
        int[] mapT = new int[256];

        for (int i = 0; i < s.length(); i++) {
            char cs = s.charAt(i);
            char ct = t.charAt(i);

            // Compare last seen positions
            if (mapS[cs] != mapT[ct]) {
                return false;
            }

            // Update to current index + 1
            mapS[cs] = i + 1;
            mapT[ct] = i + 1;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("P45 Output (egg, add):     " + isIsomorphic("egg", "add"));     // Expected: true
        System.out.println("P45 Output (foo, bar):     " + isIsomorphic("foo", "bar"));     // Expected: false
        System.out.println("P45 Output (paper, title): " + isIsomorphic("paper", "title")); // Expected: true
    }
}