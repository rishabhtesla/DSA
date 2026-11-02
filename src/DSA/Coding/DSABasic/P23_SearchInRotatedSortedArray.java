package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P23: SEARCH IN ROTATED SORTED ARRAY
 *
 * Question
 * Find target in a sorted array rotated once with distinct values.
 *
 * Example
 * [4,5,6,7,0,1,2], 0 -> 4.
 *
 * Constraints
 * 1 <= n <= 5000; values are distinct and sorted before rotation.
 *
 * Approach used by edSlash
 * 1. At each midpoint identify the sorted half, then test whether target lies inside it.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [4,5,6,7,0,1,2], 0 -> 4.
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P23_SearchInRotatedSortedArray {
    public static int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] == target) return mid;
            if (nums[start] <= nums[mid]) {
                if (nums[start] <= target && target < nums[mid]) end = mid - 1;
                else start = mid + 1;
            } else {
                if (nums[mid] < target && target <= nums[end]) start = mid + 1;
                else end = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
    }
}
