package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find minimum integer eating speed that finishes all piles within h hours.
 * EXAMPLE: [3,6,7,11], h=8 -> 4.
 * CONSTRAINTS: 1 <= piles.length <= 10^4; 1 <= h <= 10^9.
 * LOGIC / ALGORITHM:
 * 1. Binary-search speed; compute required ceiling hours and keep feasible speeds.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [3,6,7,11], h=8 -> 4.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P30_KokoEatingBananas {
    public static int minEatingSpeed(int[] p, int h) {
        int l = 1, r = 0;
        for (int x : p) r = Math.max(r, x);
        while (l < r) {
            int m = (l + r) / 2;
            long hours = 0;
            for (int x : p) hours += (x + m - 1) / m;
            if (hours <= h) r = m;
            else l = m + 1;
        }
        return l;
    }

    public static void main(String[] args) {
        System.out.println(minEatingSpeed(new int[]{3, 6, 7, 11}, 8));
    }
}
