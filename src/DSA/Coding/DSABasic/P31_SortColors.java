package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Sort an array containing only 0, 1, and 2 in-place.
 * EXAMPLE: [2,0,2,1,1,0] -> [0,0,1,1,2,2].
 * CONSTRAINTS: n <= 300; values are only 0,1,2.
 * LOGIC / ALGORITHM:
 * 1. Dutch National Flag pointers maintain processed 0s, unknowns, and 2s.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [2,0,2,1,1,0] -> [0,0,1,1,2,2].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P31_SortColors {
    public static void sortColors(int[] a) {
        int l = 0, m = 0, r = a.length - 1;
        while (m <= r) {
            if (a[m] == 0) {
                int t = a[l];
                a[l++] = a[m];
                a[m++] = t;
            } else if (a[m] == 2) {
                int t = a[m];
                a[m] = a[r];
                a[r--] = t;
            } else m++;
        }
    }

    public static void main(String[] args) {
        int[] a = {2, 0, 2, 1, 1, 0};
        sortColors(a);
        System.out.println(Arrays.toString(a));
    }
}
