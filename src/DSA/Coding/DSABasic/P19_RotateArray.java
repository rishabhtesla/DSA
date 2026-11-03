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
 *   Reverse-sections visual for [1,2,3,4,5,6,7], k = 3:
 *     reverse whole array:      [7,6,5,4,3,2,1]
 *     reverse first 3 values:   [5,6,7,4,3,2,1]
 *     reverse remaining values:  [5,6,7,1,2,3,4]
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
