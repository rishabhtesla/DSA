package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return indices containing target after sorting the array.
 * EXAMPLE: [1,2,5,2,3], target 2 -> [1,2].
 * CONSTRAINTS: 1 <= n <= 100; values are 1..100.
 * LOGIC / ALGORITHM:
 * 1. Sort, then collect every index equal to target.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,2,5,2,3], target 2 -> [1,2].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P28_FindTargetIndicesAfterSortingArray {
    public static List<Integer> targetIndices(int[] a, int t) {
        Arrays.sort(a);
        List<Integer> r = new ArrayList<>();
        for (int i = 0; i < a.length; i++) if (a[i] == t) r.add(i);
        return r;
    }

    public static void main(String[] args) {
        System.out.println(targetIndices(new int[]{1, 2, 5, 2, 3}, 2));
    }
}
