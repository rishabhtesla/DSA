package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P19: ROTATE ARRAY
 *
 * Question
 * Rotate an array right by k positions in-place.
 *
 * Example
 * [1,2,3,4,5,6,7], k=3 -> [5,6,7,1,2,3,4].
 *
 * Constraints
 * 1 <= n <= 10^5; k >= 0.
 *
 * Approach used by edSlash
 * 1. Reverse all elements, reverse the first k, then reverse the remainder.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [1,2,3,4,5,6,7], k=3 -> [5,6,7,1,2,3,4].
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P19_RotateArray {
    public static void rotate(int[] nums, int rotationCount) {
        rotationCount %= nums.length;
        rev(nums, 0, nums.length - 1);
        rev(nums, 0, rotationCount - 1);
        rev(nums, rotationCount, nums.length - 1);
    }

    static void rev(int[] nums, int left, int right) {
        while (left < right) {
            int t = nums[left];
            nums[left++] = nums[right];
            nums[right--] = t;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        rotate(nums, 3);
        System.out.println(Arrays.toString(nums));
    }
}
