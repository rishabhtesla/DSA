package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Arrange nonnegative integers to form the largest concatenated number.
 * EXAMPLE: [3,30,34,5,9] -> "9534330".
 * CONSTRAINTS: 1 <= n <= 100; each value is 0..10^9.
 * LOGIC / ALGORITHM:
 * 1. Sort strings by whether xy is larger than yx, then concatenate; collapse all-zero output.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [3,30,34,5,9] -> "9534330".; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P32_LargestNumber {
    public static String largestNumber(int[] a) {
        String[] s = new String[a.length];
        for (int i = 0; i < a.length; i++) s[i] = "" + a[i];
        Arrays.sort(s, (x, y) -> (y + x).compareTo(x + y));
        String r = String.join("", s);
        return r.charAt(0) == '0' ? "0" : r;
    }

    public static void main(String[] args) {
        System.out.println(largestNumber(new int[]{3, 30, 34, 5, 9}));
    }
}
