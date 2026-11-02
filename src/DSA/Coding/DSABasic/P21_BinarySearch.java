package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P21: BINARY SEARCH
 *
 * Question
 * Find target in a sorted array or return -1.
 *
 * Example
 * [-1,0,3,5,9,12], 9 -> 4.
 *
 * Constraints
 * 1 <= n <= 10^4; values are strictly increasing.
 *
 * Approach used by edSlash
 * 1. Compare target with the midpoint and discard the impossible half each iteration.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [-1,0,3,5,9,12], 9 -> 4.
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P21_BinarySearch {
    public static int search(int[] a, int t) {
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (a[m] == t) return m;
            if (a[m] < t) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
    }
}
