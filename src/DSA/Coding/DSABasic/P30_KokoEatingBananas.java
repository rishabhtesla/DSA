package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P30: KOKO EATING BANANAS
 *
 * Question
 * Find minimum integer eating speed that finishes all piles within h hours.
 *
 * Example
 * [3,6,7,11], h=8 -> 4.
 *
 * Constraints
 * 1 <= piles.length <= 10^4; 1 <= h <= 10^9.
 *
 * Approach used by edSlash
 * 1. Binary-search speed; compute required ceiling hours and keep feasible speeds.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [3,6,7,11], h=8 -> 4.
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P30_KokoEatingBananas {
    public static int minEatingSpeed(int[] piles, int hours) {
        int start = 1, end = 0;
        for (int x : piles) end = Math.max(end, x);
        while (start < end) {
            int mid = (start + end) / 2;
            long requiredHours = 0;
            for (int pile : piles) {
                requiredHours += (pile + mid - 1) / mid;
            }
            if (requiredHours <= hours) end = mid;
            else start = mid + 1;
        }
        return start;
    }

    public static void main(String[] args) {
        System.out.println(minEatingSpeed(new int[]{3, 6, 7, 11}, 8));
    }
}
