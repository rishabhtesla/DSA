package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the kth largest element in an unsorted array.
 * EXAMPLE: [3,2,1,5,6,4], k=2 -> 5.
 * CONSTRAINTS: 1 <= k <= n <= 10^5; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Sort ascending and select index n-k.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n log n) time and O(n) auxiliary space.
 * DRY RUN: Main input [3,2,1,5,6,4], k=2 -> 5.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P35_KthLargestElement {
    public static int findKthLargest(int[] a, int k) {
        Arrays.sort(a);
        return a[a.length - k];
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    }
}
