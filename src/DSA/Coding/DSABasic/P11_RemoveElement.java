package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P11: REMOVE ELEMENT
 *
 * Question
 * Remove all occurrences of val in-place and return the new length.
 *
 * Example
 * [3,2,2,3], val=3 -> length 2, prefix [2,2].
 *
 * Constraints
 * 0 <= length <= 100; order of retained values need not change.
 *
 * Approach used by edSlash
 * 1. Write each non-target value at the next compact position.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [3,2,2,3], val=3 -> length 2, prefix [2,2].
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P11_RemoveElement {
    public static int removeElement(int[] nums, int v) {
        int k = 0;
        for (int x : nums) if (x != v) nums[k++] = x;
        return k;
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 2, 3};
        System.out.println(removeElement(nums, 3));
    }
}
