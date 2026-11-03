package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P21: BINARY SEARCH
 *
 * Question
 * Find target in a sorted array or return -1.
 *
 * Example
 * [-1,0,3,5,9,12], 9 -> 4.
 *
 * Constraints
 * 1 <= n <= 10^4; values are strictly increasing.
 *
 * Approach used by edSlash
 * 1. Compare target with the midpoint and discard the impossible half each iteration.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 *   Binary-search table for [-1,0,3,5,9,12], target = 9:
 *     start=0, end=5, mid=2 -> 3 < 9, move start to 3
 *     start=3, end=5, mid=4 -> nums[4]=9, return 4
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P21_BinarySearch {
    public static int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) start = mid + 1;
            else end = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
    }
}
