package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the maximum run length of one repeated character.
 * EXAMPLE: "abbcccddddeeeeedcba" -> 5.
 * CONSTRAINTS: 1 <= s.length <= 500; lowercase letters.
 * LOGIC / ALGORITHM:
 * 1. Track current run when the character matches the previous one and update the maximum.
 * 2. Scan once, updating frequencies or a stack while preserving the required character order or balance.
 * 3. Return the constructed string or final validity test.
 * INVARIANT: The processed character prefix remains valid, with counts and ordering sufficient to complete the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input "abbcccddddeeeeedcba" -> 5.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
