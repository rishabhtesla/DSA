package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P18: TRAPPING RAIN WATER
 *
 * Question
 * Compute rainwater trapped between elevation bars.
 *
 * Example
 * [0,1,0,2,1,0,1,3,2,1,2,1] -> 6.
 *
 * Constraints
 * 0 <= n <= 2*10^4; heights are non-negative.
 *
 * Approach used by edSlash
 * 1. Two pointers maintain left/right maximum walls and add water on the side with the lower current wall.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [0,1,0,2,1,0,1,3,2,1,2,1] -> 6.
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P18_TrappingRainWater {
    public static int trap(int[] nums) {
        int l = 0, r = nums.length - 1, lm = 0, rm = 0, ans = 0;
        while (l < r) {
            if (nums[l] < nums[r]) {
                lm = Math.max(lm, nums[l]);
                ans += lm - nums[l++];
            } else {
                rm = Math.max(rm, nums[r]);
                ans += rm - nums[r--];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    }
}
