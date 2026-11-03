package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P34: PLUS ONE
 *
 * Question
 * Add one to a nonnegative integer represented by digits.
 *
 * Example
 * [1,2,9] -> [1,3,0].
 *
 * Constraints
 * 1 <= digits.length <= 100; digits are 0..9 and no leading zero except zero.
 *
 * Approach used by edSlash
 * 1. Process from right to left; stop when a digit does not overflow, otherwise create a leading 1.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 *   Right-to-left carry visual for [1,2,9]:
 *     9 + 1 = 10 -> write 0, carry 1
 *     2 + 1 = 3  -> write 3, carry 0
 *     1 remains unchanged
 *     result: [1,3,0]
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P34_PlusOne {
    public static int[] plusOne(int[] nums) {
        for (int i = nums.length - 1; i >= 0; i--) if (nums[i]++ < 9) return nums;
        int[] r = new int[nums.length + 1];
        r[0] = 1;
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(plusOne(new int[]{1, 2, 9})));
    }
}
