package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Remove all occurrences of val in-place and return the new length.
 * EXAMPLE: [3,2,2,3], val=3 -> length 2, prefix [2,2].
 * CONSTRAINTS: 0 <= length <= 100; order of retained values need not change.
 * LOGIC / ALGORITHM:
 * 1. Write each non-target value at the next compact position.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [3,2,2,3], val=3 -> length 2, prefix [2,2].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P11_RemoveElement {
    public static int removeElement(int[] a, int v) {
        int k = 0;
        for (int x : a) if (x != v) a[k++] = x;
        return k;
    }

    public static void main(String[] args) {
        int[] a = {3, 2, 2, 3};
        System.out.println(removeElement(a, 3));
    }
}
