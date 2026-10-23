package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the peak index of a mountain array.
 * EXAMPLE: [0,2,1,0] -> 1.
 * CONSTRAINTS: 3 <= n <= 10^5; values strictly rise then strictly fall.
 * LOGIC / ALGORITHM:
 * 1. If a[mid] < a[mid+1], climb right; otherwise keep the peak at or left of mid.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [0,2,1,0] -> 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P25_PeakIndexInMountainArray {
    public static int peakIndexInMountainArray(int[] a) {
        int l = 0, r = a.length - 1;
        while (l < r) {
            int m = (l + r) / 2;
            if (a[m] < a[m + 1]) l = m + 1;
            else r = m;
        }
        return l;
    }

    public static void main(String[] args) {
        System.out.println(peakIndexInMountainArray(new int[]{0, 2, 1, 0}));
    }
}
