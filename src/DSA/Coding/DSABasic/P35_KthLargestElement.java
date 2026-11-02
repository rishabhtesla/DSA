package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P35: KTH LARGEST ELEMENT
 *
 * Question
 * Return the kth largest element in an unsorted array.
 *
 * Example
 * [3,2,1,5,6,4], k=2 -> 5.
 *
 * Constraints
 * 1 <= k <= n <= 10^5; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Sort ascending and select index n-k.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [3,2,1,5,6,4], k=2 -> 5.
 * 1. Read the input values from left to right.
 * 2. Apply the decision described in the approach to the current value.
 * 3. Update the running answer and continue until all values are processed.
 * 4. Return the final answer produced after the last value.
 *
 * Complexity
 * O(n log n) time and O(n) auxiliary space.
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
