package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find the minimum in a rotated sorted array of distinct values.
 * EXAMPLE: [3,4,5,1,2] -> 1.
 * CONSTRAINTS: 1 <= n <= 5000; values are distinct.
 * LOGIC / ALGORITHM:
 * 1. Compare midpoint with the right endpoint to decide whether the minimum is right of mid or at/before it.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [3,4,5,1,2] -> 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P24_FindMinimumInRotatedSortedArray {
    public static int findMin(int[] a) {
        int l = 0, r = a.length - 1;
        while (l < r) {
            int m = (l + r) / 2;
            if (a[m] > a[r]) l = m + 1;
            else r = m;
        }
        return a[l];
    }

    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3, 4, 5, 1, 2}));
    }
}
