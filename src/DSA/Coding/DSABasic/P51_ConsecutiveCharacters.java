package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P51: CONSECUTIVE CHARACTERS
 *
 * Question
 * Return the maximum run length of one repeated character.
 *
 * Example
 * "abbcccddddeeeeedcba" -> 5.
 *
 * Constraints
 * 1 <= s.length <= 500; lowercase letters.
 *
 * Approach used by edSlash
 * 1. Track current run when the character matches the previous one and update the maximum.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Run-length visual for "abbcccddddeeeeedcba":
 *     runs have lengths 1,2,3,4,5,1,1,1,1,1
 *     the longest run is "eeeee" with length 5
 *     return 5
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P51_ConsecutiveCharacters {
    public static int maxPower(String s) {
        int ans = 0, c = 0;
        char p = 0;
        for (char x : s.toCharArray()) {
            c = x == p ? c + 1 : 1;
            p = x;
            ans = Math.max(ans, c);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxPower("abbcccddddeeeeedcba"));
    }
}
