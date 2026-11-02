package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P26: SEARCH INSERT POSITION
 *
 * Question
 * Return target's index or the position where it belongs in sorted order.
 *
 * Example
 * [1,3,5,6], 2 -> 1.
 *
 * Constraints
 * 1 <= n <= 10^4; values sorted and distinct.
 *
 * Approach used by edSlash
 * 1. Start with start at the first index and end at the last index.
 * 2. Check the middle value. Return immediately when it equals target.
 * 3. If target is larger, search the right half; otherwise search the left half.
 * 4. When the loop ends, start is the correct insertion position.
 *
 * Dry run
 * Example from main: [1,3,5,6], 2 -> 1.
 * 1. For [1, 3, 5, 6] and target 2, start = 0 and end = 3.
 * 2. mid = 1, and nums[mid] = 3. Target 2 is smaller, so end becomes 0.
 * 3. mid = 0, and nums[mid] = 1. Target 2 is larger, so start becomes 1.
 * 4. The loop ends because start is greater than end. Return start = 1.
 *
 * Complexity
 * O(log n) time and O(1) auxiliary space.
 */
public class P26_SearchInsertPosition {
    public static int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return start;
    }

    public static void main(String[] args) {
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5));
    }
}
