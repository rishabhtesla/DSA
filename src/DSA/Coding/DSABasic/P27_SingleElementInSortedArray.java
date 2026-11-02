package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P27: SINGLE ELEMENT IN SORTED ARRAY
 *
 * Question
 * Find the only value appearing once when all others appear twice.
 *
 * Example
 * [1,1,2,3,3] -> 2.
 *
 * Constraints
 * 1 <= n <= 10^5; sorted; exactly one singleton.
 *
 * Approach used by edSlash
 * 1. Align midpoint to an even index; equal pairs before the singleton move left boundary past them.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [1,1,2,3,3] -> 2.
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P27_SingleElementInSortedArray {
    public static int singleNonDuplicate(int[] a) {
        int l = 0, r = a.length - 1;
        while (l < r) {
            int m = (l + r) / 2;
            if ((m & 1) == 1) m--;
            if (a[m] == a[m + 1]) l = m + 2;
            else r = m;
        }
        return a[l];
    }

    public static void main(String[] args) {
        System.out.println(singleNonDuplicate(new int[]{1, 1, 2, 3, 3}));
    }
}
