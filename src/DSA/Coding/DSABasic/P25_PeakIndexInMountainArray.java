package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P25: PEAK INDEX IN MOUNTAIN ARRAY
 *
 * Question
 * Return the peak index of a mountain array.
 *
 * Example
 * [0,2,1,0] -> 1.
 *
 * Constraints
 * 3 <= n <= 10^5; values strictly rise then strictly fall.
 *
 * Approach used by edSlash
 * 1. If a[mid] < a[mid+1], climb right; otherwise keep the peak at or left of mid.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 *   Peak-search visual for [0,2,1,0]:
 *     mid=1 -> 2 > 1, so the peak is at mid or to its left
 *     move end to 1; start=end=1
 *     return peak index 1
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P25_PeakIndexInMountainArray {
    public static int peakIndexInMountainArray(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (nums[mid] < nums[mid + 1]) start = mid + 1;
            else end = mid;
        }
        return start;
    }

    public static void main(String[] args) {
        System.out.println(peakIndexInMountainArray(new int[]{0, 2, 1, 0}));
    }
}
