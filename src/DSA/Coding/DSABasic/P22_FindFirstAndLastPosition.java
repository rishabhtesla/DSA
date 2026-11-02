package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P22: FIND FIRST AND LAST POSITION
 *
 * Question
 * Return the first and last index of target in a sorted array.
 *
 * Example
 * [5,7,7,8,8,10], 8 -> [3,4].
 *
 * Constraints
 * 0 <= n <= 10^5; sorted nondecreasing.
 *
 * Approach used by edSlash
 * 1. Run one lower-bound binary search for the first occurrence and one upper-bound search for the last.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [5,7,7,8,8,10], 8 -> [3,4].
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
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
