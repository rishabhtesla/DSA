package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P54: VALID ANAGRAM
 *
 * Question
 * Determine whether two strings are anagrams.
 *
 * Example
 * "anagram", "nagaram" -> true.
 *
 * Constraints
 * Strings have equal lengths and lowercase English letters.
 *
 * Approach used by edSlash
 * 1. Increment counts for the first string and decrement for the second.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 * Example from main: "anagram", "nagaram" -> true.
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
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
