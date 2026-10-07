package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * PROBLEM STATEMENT:
 * Given two strings 's' and 't', return true if 't' is an anagram of 's', and false otherwise [00:00:43].
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, 
 * typically using all the original letters exactly once [00:00:54].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "anagram", t = "nagaram" [00:01:38]
 * - Process:
 *   - Character frequencies in s ("anagram"): 'a': 3, 'n': 1, 'g': 1, 'r': 1, 'm': 1 [00:03:06].
 *   - Iterating through t ("nagaram"): decrementing frequency of each character matched [00:03:29, 00:13:01].
 *   - All character frequencies match and reduce to zero, leaving an empty frequency table [00:04:30, 00:14:03].
 * - Result: true [00:02:08, 00:14:08]
 * 
 * Example 2: s = "rat", t = "car" [00:01:15, 00:05:12]
 * - Process:
 *   - s ("rat") character frequencies: 'r': 1, 'a': 1, 't': 1.
 *   - t ("car"): 'c' is not found in the frequency table of s [00:01:21, 00:05:29].
 * - Result: false [00:01:25, 00:05:45]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - HashMap Frequency Tracking & Reduction):
 * • Index Initialization: Loop 'i' iterates through string `s` (0 to s.length() - 1), then loop 'i' iterates through string `t` (0 to t.length() - 1) [00:06:53, 00:08:59].
 * • Condition Boundaries:
 *   - Fast-pass check: if `s.length() != t.length()`, return false immediately.
 *   - In `t` traversal: if `!map.containsKey(ch)`, return false immediately [00:09:45].
 *   - Frequency decrement: if `map.get(ch) == 1`, remove key (`map.remove(ch)`) [00:10:06]; else decrement count by 1 (`map.put(ch, count - 1)`) [00:10:20].
 * • Operational Steps:
 *   1. Create `HashMap<Character, Integer> hm = new HashMap<>()` [00:06:09].
 *   2. Iterate string `s`: populate `hm` with character frequency counts [00:06:53, 00:07:48].
 *   3. Iterate string `t`: check if character exists in `hm` [00:08:59, 00:09:30].
 *   4. If char missing, return false [00:09:45]. If present, decrement count or remove entry if count reaches 1 [00:10:06, 00:10:20].
 *   5. After second loop, check if `hm.isEmpty()`. Return true if empty, else false [00:10:42].
 * • Time Complexity: O(n) - Two linear passes through strings of length n.
 * • Space Complexity: O(k) auxiliary space - Where k is the number of distinct characters (k <= 26 for lowercase English letters) [00:06:09].
 * • LOGIC BEHIND THIS APPROACH:
 *   Two strings are anagrams if and only if they contain the exact same set of characters with identical frequencies [00:00:54]. 
 *   Populating a hash map with character counts from the first string and consuming them with characters from the second string 
 *   verifies frequency equality in linear time [00:02:59, 00:03:29].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: HashMap Frequency Tracking - s = "rat", t = "car"):
 * Phase 1 (Build HashMap for "rat"):
 * i=0 ('r'): map = {'r': 1}
 * i=1 ('a'): map = {'r': 1, 'a': 1}
 * i=2 ('t'): map = {'r': 1, 'a': 1, 't': 1} [00:05:12]
 * 
 * Phase 2 (Consume with "car"):
 * i=0 ('c'): map.containsKey('c') == false -> Mismatch found! Return false [00:05:29].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Fixed 26-Element Array Optimization - s = "rat", t = "car"):
 * Frequency Array (size 26):
 * Pass 1 ("rat"): count['r'-'a']++, count['a'-'a']++, count['t'-'a']++
 * Pass 2 ("car"): count['c'-'a']-- -> count['c'-'a'] becomes -1 (< 0) -> Return false.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Sorting Character Arrays - s = "rat", t = "car"):
 * Step 1: Convert to char arrays -> sArr = ['r','a','t'], tArr = ['c','a','r']
 * Step 2: Arrays.sort() -> sArr = ['a','r','t'], tArr = ['a','c','r']
 * Step 3: Compare arrays -> 'r' != 'c' at index 1 -> Return false.
 */
public class ValidAnagram {

    // APPROACH 1: HashMap Frequency Tracking & Reduction (Anchor Strategy)
    public static boolean isAnagramOptimal(String s, String t) {
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false; // Early length check

        Map<Character, Integer> hm = new HashMap<>(); // HashMap for character frequencies [00:06:09]

        // Phase 1: Populate character counts from string s [00:06:53]
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i); // Extract character [00:07:10]
            if (hm.containsKey(ch)) {
                hm.put(ch, hm.get(ch) + 1); // Increment count [00:07:34]
            } else {
                hm.put(ch, 1); // Initial entry [00:07:48]
            }
        }

        // Phase 2: Consume character counts using string t [00:08:59]
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i); // Extract character [00:09:05]

            // If character does not exist in s, t cannot be an anagram [00:09:45]
            if (!hm.containsKey(ch)) {
                return false;
            }

            // If count is 1, remove key entirely [00:10:06]
            if (hm.get(ch) == 1) {
                hm.remove(ch);
            } 
            // Decrement frequency count [00:10:20]
            else {
                hm.put(ch, hm.get(ch) - 1);
            }
        }

        // String t is a valid anagram if all frequencies reduced to 0 (empty map) [00:10:42]
        return hm.isEmpty();
    }

    // APPROACH 2: Fixed 26-Element Array Strategy (O(1) Space Optimization)
    // Replaces HashMap with a primitive integer frequency array of size 26.
    // Extremely fast and eliminates object hashing/boxing overhead.
    public static boolean isAnagramArray(String s, String t) {
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;

        int[] count = new int[26];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }

        for (int c : count) {
            if (c != 0) return false;
        }

        return true;
    }

    // APPROACH 3: Sorting Character Arrays Strategy
    // Converts both strings to character arrays, sorts them, and checks for direct equality.
    // Time Complexity: O(n log n) due to quicksort pass.
    public static boolean isAnagramSorting(String s, String t) {
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;

        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();

        Arrays.sort(sArray);
        Arrays.sort(tArray);

        return Arrays.equals(sArray, tArray);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Valid Anagram Pair) ---
        String s1 = "anagram";
        String t1 = "nagaram";
        boolean res1_1 = isAnagramOptimal(s1, t1);
        boolean res1_2 = isAnagramArray(s1, t1);
        boolean res1_3 = isAnagramSorting(s1, t1);

        System.out.println("Test Case 1: s = \"anagram\", t = \"nagaram\"");
        System.out.println("Approach 1 (HashMap)  Result: " + res1_1);
        System.out.println("Approach 2 (Freq Array) Result: " + res1_2);
        System.out.println("Approach 3 (Sorting)   Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 && res1_2 && res1_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Invalid Anagram Pair) ---
        String s2 = "rat";
        String t2 = "car";
        boolean res2_1 = isAnagramOptimal(s2, t2);
        boolean res2_2 = isAnagramArray(s2, t2);
        boolean res2_3 = isAnagramSorting(s2, t2);

        System.out.println("Test Case 2: s = \"rat\", t = \"car\"");
        System.out.println("Approach 1 (HashMap)  Result: " + res2_1);
        System.out.println("Approach 2 (Freq Array) Result: " + res2_2);
        System.out.println("Approach 3 (Sorting)   Result: " + res2_3);
        System.out.println("Verification: " + (!res2_1 && !res2_2 && !res2_3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}