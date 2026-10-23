package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Determine whether characters can be rearranged so all words become identical.
 * EXAMPLE: ["abc","aabc","bc"] -> true.
 * CONSTRAINTS: 1 <= words.length <= 100; lowercase letters.
 * LOGIC / ALGORITHM:
 * 1. Total frequency of every character must be divisible by the number of words.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input ["abc","aabc","bc"] -> true.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P50_RedistributeCharactersToMakeAllStringsEqual {
    public static boolean makeEqual(String[] w) {
        int[] f = new int[26];
        for (String s : w) for (char c : s.toCharArray()) f[c - 'a']++;
        for (int x : f) if (x % w.length != 0) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(makeEqual(new String[]{"abc", "aabc", "bc"}));
    }
}
