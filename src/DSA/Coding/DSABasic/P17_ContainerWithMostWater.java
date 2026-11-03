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
 *   Two-pointer visual for [1,8,6,2,5,4,8,3,7]:
 *     start at heights 1 and 7 -> area = 8
 *     move the shorter side and keep the best area
 *     the best pair is height 8 at index 1 and height 7 at index 8
 *     width = 7, area = 7*7 = 49
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P17_ContainerWithMostWater {
    public static int maxArea(int[] nums) {
        int l = 0, r = nums.length - 1, ans = 0;
        while (l < r) {
            ans = Math.max(ans, Math.min(nums[l], nums[r]) * (r - l));
            if (nums[l] < nums[r]) l++;
            else r--;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }
}
