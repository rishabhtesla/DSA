package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Rotate an array right by k positions in-place.
 * EXAMPLE: [1,2,3,4,5,6,7], k=3 -> [5,6,7,1,2,3,4].
 * CONSTRAINTS: 1 <= n <= 10^5; k >= 0.
 * LOGIC / ALGORITHM:
 * 1. Reverse all elements, reverse the first k, then reverse the remainder.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,2,3,4,5,6,7], k=3 -> [5,6,7,1,2,3,4].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P19_RotateArray {
    public static void rotate(int[] a, int k) {
        k %= a.length;
        rev(a, 0, a.length - 1);
        rev(a, 0, k - 1);
        rev(a, k, a.length - 1);
    }

    static void rev(int[] a, int l, int r) {
        while (l < r) {
            int t = a[l];
            a[l++] = a[r];
            a[r--] = t;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        rotate(a, 3);
        System.out.println(Arrays.toString(a));
    }
}
