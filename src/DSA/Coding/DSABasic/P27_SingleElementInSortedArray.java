package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find the only value appearing once when all others appear twice.
 * EXAMPLE: [1,1,2,3,3] -> 2.
 * CONSTRAINTS: 1 <= n <= 10^5; sorted; exactly one singleton.
 * LOGIC / ALGORITHM:
 * 1. Align midpoint to an even index; equal pairs before the singleton move left boundary past them.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,1,2,3,3] -> 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
