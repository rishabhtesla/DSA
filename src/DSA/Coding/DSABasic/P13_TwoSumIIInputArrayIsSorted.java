package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find 1-based indices of two sorted values adding to target.
 * EXAMPLE: [2,7,11,15], 9 -> [1,2].
 * CONSTRAINTS: 2 <= n <= 3*10^4; strictly increasing; exactly one pair.
 * LOGIC / ALGORITHM:
 * 1. Move the left pointer up when the sum is small and right down when it is large.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [2,7,11,15], 9 -> [1,2].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P13_TwoSumIIInputArrayIsSorted {
    public static int[] twoSum(int[] a, int t) {
        int l = 0, r = a.length - 1;
        while (l < r) {
            int s = a[l] + a[r];
            if (s == t) return new int[]{l + 1, r + 1};
            if (s < t) l++;
            else r--;
        }
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
    }
}
