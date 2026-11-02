package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P44: BOATS TO SAVE PEOPLE
 *
 * Question
 * Use boats carrying at most two people to minimize boat count.
 *
 * Example
 * [3,2,2,1], limit=3 -> 3.
 *
 * Constraints
 * 1 <= n <= 5*10^4; each weight <= limit.
 *
 * Approach used by edSlash
 * 1. Sort weights; pair lightest with heaviest when they fit, otherwise send heaviest alone.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [3,2,2,1], limit=3 -> 3.
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P44_BoatsToSavePeople {
    public static int numRescueBoats(int[] nums, int lim) {
        Arrays.sort(nums);
        int l = 0, r = nums.length - 1, c = 0;
        while (l <= r) {
            if (nums[l] + nums[r] <= lim) l++;
            r--;
            c++;
        }
        return c;
    }

    public static void main(String[] args) {
        System.out.println(numRescueBoats(new int[]{3, 2, 2, 1}, 3));
    }
}
