package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P27: SINGLE ELEMENT IN SORTED ARRAY
 *
 * Question
 * Find the only value appearing once when all others appear twice.
 *
 * Example
 * [1,1,2,3,3] -> 2.
 *
 * Constraints
 * 1 <= n <= 10^5; sorted; exactly one singleton.
 *
 * Approach used by edSlash
 * 1. Align midpoint to an even index; equal pairs before the singleton move left boundary past them.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [1,1,2,3,3] -> 2.
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P27_SingleElementInSortedArray {
    public static int singleNonDuplicate(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if ((mid & 1) == 1) mid--;
            if (nums[mid] == nums[mid + 1]) start = mid + 2;
            else end = mid;
        }
        return nums[start];
    }

    public static void main(String[] args) {
        System.out.println(singleNonDuplicate(new int[]{1, 1, 2, 3, 3}));
    }
}
