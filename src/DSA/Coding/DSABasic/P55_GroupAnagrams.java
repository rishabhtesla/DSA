package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P55: GROUP ANAGRAMS
 *
 * Question
 * Group strings that contain the same letters with the same multiplicities.
 *
 * Example
 * ["eat","tea","tan","ate","nat","bat"] -> groups [eat,tea,ate], [tan,nat], [bat].
 *
 * Constraints
 * 1 <= total characters <= 10^4; lowercase English letters.
 *
 * Approach used by edSlash
 * 1. Sort each word's characters as a canonical key and collect words by that key.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 * Example from main: ["eat","tea","tan","ate","nat","bat"] -> groups [eat,tea,ate], [tan,nat], [bat].
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
 *
 * Complexity
 * O(n log n) time and O(n) auxiliary space.
 */
public class P55_GroupAnagrams {
    public static List<List<String>> groupAnagrams(String[] a) {
        Map<String, List<String>> m = new HashMap<>();
        for (String s : a) {
            char[] c = s.toCharArray();
            Arrays.sort(c);
            m.computeIfAbsent(new String(c), k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(m.values());
    }

    public static void main(String[] args) {
        System.out.println(groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
    }
}
