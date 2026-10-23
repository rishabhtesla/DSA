package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Maximize stolen money without robbing adjacent houses.
 * EXAMPLE: [2,7,9,3,1] -> 12.
 * CONSTRAINTS: 1 <= n <= 100; amounts are non-negative.
 * LOGIC / ALGORITHM:
 * 1. For each house choose skip current or rob it plus best through two houses back.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [2,7,9,3,1] -> 12.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P97_HouseRobber {
    public static int rob(int[] a) {
        int prev = 0, cur = 0;
        for (int x : a) {
            int t = cur;
            cur = Math.max(cur, prev + x);
            prev = t;
        }
        return cur;
    }

    public static void main(String[] args) {
        System.out.println(rob(new int[]{2, 7, 9, 3, 1}));
    }
}
