package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [19 / 24] - LENGTH OF LAST WORD (LeetCode 58)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a string s consisting of words and spaces, return the length of the
 *   last word in the string. A word is a maximal substring consisting of non-space
 *   characters only.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Naive: `s.trim().split(" ")` creates extra array allocations and scans entire string.
 *   - Optimal Backward Scan:
 *     1. Start pointer `p = s.length() - 1`.
 *     2. Skip any trailing whitespace backwards until hitting a letter.
 *     3. Count characters backwards until encountering the next space or start of string.
 *
 * COMPLEXITY:
 *   - Time:  O(n) worst case (O(k) where k is distance from end to start of last word).
 *   - Space: O(1) - Two integer variables, zero memory allocations.
 */
public class P19_LengthOfLastWord {

    public static int lengthOfLastWord(String s) {
        int p = s.length() - 1;

        // Skip trailing spaces
        while (p >= 0 && s.charAt(p) == ' ') {
            p--;
        }

        // Count length of last word
        int length = 0;
        while (p >= 0 && s.charAt(p) != ' ') {
            length++;
            p--;
        }

        return length;
    }

    public static void main(String[] args) {
        System.out.println("P19 Output (Hello World):      " + lengthOfLastWord("Hello World"));                 // Expected: 5
        System.out.println("P19 Output (   fly me   moon  ):  " + lengthOfLastWord("   fly me   to the moon  ")); // Expected: 4
    }
}