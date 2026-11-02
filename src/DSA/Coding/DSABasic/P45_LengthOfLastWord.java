package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P45: LENGTH OF LAST WORD
 *
 * Question
 * Return the length of the final word in a space-separated string.
 *
 * Example
 * "Hello World" -> 5.
 *
 * Constraints
 * 1 <= s.length <= 10^4; words contain letters and spaces.
 *
 * Approach used by edSlash
 * 1. Trim trailing spaces, split words, and measure the final token.
 * 2. Keep only the previous states needed to calculate the next state instead of recomputing earlier answers.
 * 3. Update the state in the same order as the recurrence so each iteration represents one more input position.
 *
 * Dry run
 * Example from main: "Hello World" -> 5.
 * 1. Initialize the base state before processing the input.
 * 2. Calculate the next state from the previous state values.
 * 3. Shift the state variables and continue until the input is exhausted.
 * 4. Return the state that represents the requested answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P45_LengthOfLastWord {
    public static int lengthOfLastWord(String s) {
        String[] p = s.trim().split("\s+");
        return p[p.length - 1].length();
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("Hello World"));
    }
}
