package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P97: HOUSE ROBBER
 *
 * Question
 * Maximize stolen money without robbing adjacent houses.
 *
 * Example
 * [2,7,9,3,1] -> 12.
 *
 * Constraints
 * 1 <= n <= 100; amounts are non-negative.
 *
 * Approach used by edSlash
 * 1. For each house choose skip current or rob it plus best through two houses back.
 * 2. Keep only the previous states needed to calculate the next state instead of recomputing earlier answers.
 * 3. Update the state in the same order as the recurrence so each iteration represents one more input position.
 *
 * Dry run
 * Example from main: [2,7,9,3,1] -> 12.
 * 1. Initialize the base state before processing the input.
 * 2. Calculate the next state from the previous state values.
 * 3. Shift the state variables and continue until the input is exhausted.
 * 4. Return the state that represents the requested answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P97_HouseRobber {
    public static int rob(int[] nums) {
        int prev = 0, cur = 0;
        for (int x : nums) {
            int target = cur;
            cur = Math.max(cur, prev + x);
            prev = target;
        }
        return cur;
    }

    public static void main(String[] args) {
        System.out.println(rob(new int[]{2, 7, 9, 3, 1}));
    }
}
