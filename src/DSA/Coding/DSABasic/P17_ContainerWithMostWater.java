package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P17: CONTAINER WITH MOST WATER
 *
 * Question
 * Choose two vertical lines that hold the greatest water area.
 *
 * Example
 * [1,8,6,2,5,4,8,3,7] -> 49.
 *
 * Constraints
 * 2 <= n <= 10^5; heights are non-negative.
 *
 * Approach used by edSlash
 * 1. Start at both ends; discard the shorter wall because moving the taller wall cannot improve the limiting height.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [1,8,6,2,5,4,8,3,7] -> 49.
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P17_ContainerWithMostWater {
    public static int maxArea(int[] a) {
        int l = 0, r = a.length - 1, ans = 0;
        while (l < r) {
            ans = Math.max(ans, Math.min(a[l], a[r]) * (r - l));
            if (a[l] < a[r]) l++;
            else r--;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }
}
