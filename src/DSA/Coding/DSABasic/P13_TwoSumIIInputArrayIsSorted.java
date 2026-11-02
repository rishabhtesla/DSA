package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P13: TWO SUM II INPUT ARRAY IS SORTED
 *
 * Question
 * Find 1-based indices of two sorted values adding to target.
 *
 * Example
 * [2,7,11,15], 9 -> [1,2].
 *
 * Constraints
 * 2 <= n <= 3*10^4; strictly increasing; exactly one pair.
 *
 * Approach used by edSlash
 * 1. Move the left pointer up when the sum is small and right down when it is large.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [2,7,11,15], 9 -> [1,2].
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P13_TwoSumIIInputArrayIsSorted {
    public static int[] twoSum(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int s = nums[l] + nums[r];
            if (s == target) return new int[]{l + 1, r + 1};
            if (s < target) l++;
            else r--;
        }
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
    }
}
