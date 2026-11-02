package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P32: LARGEST NUMBER
 *
 * Question
 * Arrange nonnegative integers to form the largest concatenated number.
 *
 * Example
 * [3,30,34,5,9] -> "9534330".
 *
 * Constraints
 * 1 <= n <= 100; each value is 0..10^9.
 *
 * Approach used by edSlash
 * 1. Sort strings by whether xy is larger than yx, then concatenate; collapse all-zero output.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [3,30,34,5,9] -> "9534330".
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P32_LargestNumber {
    public static String largestNumber(int[] nums) {
        String[] s = new String[nums.length];
        for (int i = 0; i < nums.length; i++) s[i] = "" + nums[i];
        Arrays.sort(s, (x, y) -> (y + x).compareTo(x + y));
        String r = String.join("", s);
        return r.charAt(0) == '0' ? "0" : r;
    }

    public static void main(String[] args) {
        System.out.println(largestNumber(new int[]{3, 30, 34, 5, 9}));
    }
}
