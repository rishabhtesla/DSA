package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P23: SEARCH IN ROTATED SORTED ARRAY
 *
 * Question
 * Find target in a sorted array rotated once with distinct values.
 *
 * Example
 * [4,5,6,7,0,1,2], 0 -> 4.
 *
 * Constraints
 * 1 <= n <= 5000; values are distinct and sorted before rotation.
 *
 * Approach used by edSlash
 * 1. At each midpoint identify the sorted half, then test whether target lies inside it.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [4,5,6,7,0,1,2], 0 -> 4.
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P23_SearchInRotatedSortedArray {
    public static int search(int[] a, int t) {
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (a[m] == t) return m;
            if (a[l] <= a[m]) {
                if (a[l] <= t && t < a[m]) r = m - 1;
                else l = m + 1;
            } else {
                if (a[m] < t && t <= a[r]) l = m + 1;
                else r = m - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
    }
}
