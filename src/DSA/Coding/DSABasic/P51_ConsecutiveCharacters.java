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
 * Example from main: "abbcccddddeeeeedcba" -> 5.
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
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
