package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P07: ADD TO ARRAY FORM OF INTEGER
 *
 * Question
 * Add non-negative integer k to an integer represented by digit array.
 *
 * Example
 * [1,2,0,0]+34 -> [1,2,3,4].
 *
 * Constraints
 * 1 <= num.length <= 10^4; digits are 0..9; k >= 0.
 *
 * Approach used by edSlash
 * 1. Add from the least significant ends, carry through k, and prepend each resulting digit.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [1,2,0,0]+34 -> [1,2,3,4].
 *   Digits are processed from right to left:
 *
 *   Step | index used | digit added | carry before | output digit | carry after | result
 *   -----|------------|-------------|---------------|--------------|-------------|--------
 *     1  |      3     |      0      |      34       |      4       |      3      | [4]
 *     2  |      2     |      0      |       3       |      3       |      0      | [3,4]
 *     3  |      1     |      2      |       0       |      2       |      0      | [2,3,4]
 *     4  |      0     |      1      |       0       |      1       |      0      | [1,2,3,4]
 *
 *   The result is built from the front because the least significant digit
 *   is calculated first.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P07_AddToArrayFormOfInteger {
    public static List<Integer> addToArrayForm(int[] nums, int k) {
        LinkedList<Integer> r = new LinkedList<>();
        int i = nums.length - 1, carry = k;
        while (i >= 0 || carry > 0) {
            if (i >= 0) carry += nums[i--];
            r.addFirst(carry % 10);
            carry /= 10;
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(addToArrayForm(new int[]{1, 2, 0, 0}, 34));
    }
}
