package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P99: JUMP GAME
 *
 * Question
 * Determine whether the last index is reachable using jump lengths.
 *
 * Example
 * [2,3,1,1,4] -> true.
 *
 * Constraints
 * 1 <= n <= 10^4; nums[i] >= 0.
 *
 * Approach used by edSlash
 * 1. Track the farthest reachable index and stop if an index lies beyond it.
 * 2. Keep only the previous states needed to calculate the next state instead of recomputing earlier answers.
 * 3. Update the state in the same order as the recurrence so each iteration represents one more input position.
 *
 * Dry run
 * Example from main: [2,3,1,1,4] -> true.
 * 1. Initialize the base state before processing the input.
 * 2. Calculate the next state from the previous state values.
 * 3. Shift the state variables and continue until the input is exhausted.
 * 4. Return the state that represents the requested answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P99_JumpGame {
    public static boolean canJump(int[] nums) {
        int reach = 0;
        for (int i = 0; i < nums.length && i <= reach; i++) reach = Math.max(reach, i + nums[i]);
        return reach >= nums.length - 1;
    }

    public static void main(String[] args) {
        System.out.println(canJump(new int[]{2, 3, 1, 1, 4}));
    }
}
