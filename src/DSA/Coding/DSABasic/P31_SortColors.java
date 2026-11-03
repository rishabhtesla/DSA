package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P31: SORT COLORS
 *
 * Question
 * Sort an array containing only 0, 1, and 2 in-place.
 *
 * Example
 * [2,0,2,1,1,0] -> [0,0,1,1,2,2].
 *
 * Constraints
 * n <= 300; values are only 0,1,2.
 *
 * Approach used by edSlash
 * 1. Dutch National Flag pointers maintain processed 0s, unknowns, and 2s.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 *   Dutch National Flag visual for [2,0,2,1,1,0]:
 *     low marks the next 0 position, mid scans, high marks the next 2 position
 *     move 0 to the low region and 2 to the high region
 *     leave 1 in the middle region
 *     final array: [0,0,1,1,2,2]
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P31_SortColors {
    public static void sortColors(int[] nums) {
        int l = 0, m = 0, r = nums.length - 1;
        while (m <= r) {
            if (nums[m] == 0) {
                int target = nums[l];
                nums[l++] = nums[m];
                nums[m++] = target;
            } else if (nums[m] == 2) {
                int target = nums[m];
                nums[m] = nums[r];
                nums[r--] = target;
            } else m++;
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 0, 2, 1, 1, 0};
        sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }
}
