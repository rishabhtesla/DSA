package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find the contiguous subarray with maximum sum.
 * EXAMPLE: [-2,1,-3,4,-1,2,1,-5,4] -> 6 from [4,-1,2,1].
 * CONSTRAINTS: 1 <= n <= 10^5; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Kadane's recurrence keeps the best subarray ending at the current element.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [-2,1,-3,4,-1,2,1,-5,4] -> 6 from [4,-1,2,1].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P98_MaximumSubarray {
    public static int maxSubArray(int[] a) {
        int best = a[0], cur = 0;
        for (int x : a) {
            cur = Math.max(x, cur + x);
            best = Math.max(best, cur);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
