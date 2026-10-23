package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find target in a sorted array or return -1.
 * EXAMPLE: [-1,0,3,5,9,12], 9 -> 4.
 * CONSTRAINTS: 1 <= n <= 10^4; values are strictly increasing.
 * LOGIC / ALGORITHM:
 * 1. Compare target with the midpoint and discard the impossible half each iteration.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [-1,0,3,5,9,12], 9 -> 4.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
