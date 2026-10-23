package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find target in a sorted array rotated once with distinct values.
 * EXAMPLE: [4,5,6,7,0,1,2], 0 -> 4.
 * CONSTRAINTS: 1 <= n <= 5000; values are distinct and sorted before rotation.
 * LOGIC / ALGORITHM:
 * 1. At each midpoint identify the sorted half, then test whether target lies inside it.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [4,5,6,7,0,1,2], 0 -> 4.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
