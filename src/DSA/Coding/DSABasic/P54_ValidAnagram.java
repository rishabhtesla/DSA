package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Determine whether two strings are anagrams.
 * EXAMPLE: "anagram", "nagaram" -> true.
 * CONSTRAINTS: Strings have equal lengths and lowercase English letters.
 * LOGIC / ALGORITHM:
 * 1. Increment counts for the first string and decrement for the second.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "anagram", "nagaram" -> true.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P54_ValidAnagram {
    public static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        int[] f = new int[26];
        for (int i = 0; i < a.length(); i++) {
            f[a.charAt(i) - 'a']++;
            f[b.charAt(i) - 'a']--;
        }
        for (int x : f) if (x != 0) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram"));
    }
}
