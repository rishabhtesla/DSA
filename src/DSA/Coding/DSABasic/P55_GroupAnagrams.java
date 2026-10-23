package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Group strings that contain the same letters with the same multiplicities.
 * EXAMPLE: ["eat","tea","tan","ate","nat","bat"] -> groups [eat,tea,ate], [tan,nat], [bat].
 * CONSTRAINTS: 1 <= total characters <= 10^4; lowercase English letters.
 * LOGIC / ALGORITHM:
 * 1. Sort each word's characters as a canonical key and collect words by that key.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n log n) time and O(n) auxiliary space.
 * DRY RUN: Main input ["eat","tea","tan","ate","nat","bat"] -> groups [eat,tea,ate], [tan,nat], [bat].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
