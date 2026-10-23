package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return target's index or the position where it belongs in sorted order.
 * EXAMPLE: [1,3,5,6], 2 -> 1.
 * CONSTRAINTS: 1 <= n <= 10^4; values sorted and distinct.
 * LOGIC / ALGORITHM:
 * 1. Use a lower-bound binary search for the first value not less than target.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,3,5,6], 2 -> 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P26_SearchInsertPosition {
    public static int searchInsert(int[] a, int t) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (a[m] < t) l = m + 1;
            else r = m;
        }
        return l;
    }

    public static void main(String[] args) {
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5));
    }
}
