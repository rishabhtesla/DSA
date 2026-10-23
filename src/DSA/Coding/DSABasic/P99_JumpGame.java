package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Determine whether the last index is reachable using jump lengths.
 * EXAMPLE: [2,3,1,1,4] -> true.
 * CONSTRAINTS: 1 <= n <= 10^4; nums[i] >= 0.
 * LOGIC / ALGORITHM:
 * 1. Track the farthest reachable index and stop if an index lies beyond it.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [2,3,1,1,4] -> true.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P99_JumpGame {
    public static boolean canJump(int[] a) {
        int reach = 0;
        for (int i = 0; i < a.length && i <= reach; i++) reach = Math.max(reach, i + a[i]);
        return reach >= a.length - 1;
    }

    public static void main(String[] args) {
        System.out.println(canJump(new int[]{2, 3, 1, 1, 4}));
    }
}
