package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the first and last index of target in a sorted array.
 * EXAMPLE: [5,7,7,8,8,10], 8 -> [3,4].
 * CONSTRAINTS: 0 <= n <= 10^5; sorted nondecreasing.
 * LOGIC / ALGORITHM:
 * 1. Run one lower-bound binary search for the first occurrence and one upper-bound search for the last.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [5,7,7,8,8,10], 8 -> [3,4].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P22_FindFirstAndLastPosition {
    public static int[] searchRange(int[] a, int t) {
        int l = 0, r = a.length - 1, first = -1, last = -1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (a[m] >= t) {
                if (a[m] == t) first = m;
                r = m - 1;
            } else l = m + 1;
        }
        l = 0;
        r = a.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (a[m] <= t) {
                if (a[m] == t) last = m;
                l = m + 1;
            } else r = m - 1;
        }
        return new int[]{first, last};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
    }
}
