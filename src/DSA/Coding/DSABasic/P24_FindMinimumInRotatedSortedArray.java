package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P24: FIND MINIMUM IN ROTATED SORTED ARRAY
 *
 * Question
 * Find the minimum in a rotated sorted array of distinct values.
 *
 * Example
 * [3,4,5,1,2] -> 1.
 *
 * Constraints
 * 1 <= n <= 5000; values are distinct.
 *
 * Approach used by edSlash
 * 1. Compare midpoint with the right endpoint to decide whether the minimum is right of mid or at/before it.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [3,4,5,1,2] -> 1.
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P24_FindMinimumInRotatedSortedArray {
    public static int findMin(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (nums[mid] > nums[end]) start = mid + 1;
            else end = mid;
        }
        return nums[start];
    }

    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3, 4, 5, 1, 2}));
    }
}
