package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P50: REDISTRIBUTE CHARACTERS TO MAKE ALL STRINGS EQUAL
 *
 * Question
 * Determine whether characters can be rearranged so all words become identical.
 *
 * Example
 * ["abc","aabc","bc"] -> true.
 *
 * Constraints
 * 1 <= words.length <= 100; lowercase letters.
 *
 * Approach used by edSlash
 * 1. Total frequency of every character must be divisible by the number of words.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Character-count visual for ["abc","aabc","bc"]:
 *     total counts: a=3, b=3, c=3
 *     there are 3 strings, and each count is divisible by 3
 *     every character can be evenly redistributed -> true
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P50_RedistributeCharactersToMakeAllStringsEqual {
    public static boolean makeEqual(String[] words) {
        int[] f = new int[26];
        for (String s : words) for (char c : s.toCharArray()) f[c - 'a']++;
        for (int x : f) if (x % words.length != 0) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(makeEqual(new String[]{"abc", "aabc", "bc"}));
    }
}
