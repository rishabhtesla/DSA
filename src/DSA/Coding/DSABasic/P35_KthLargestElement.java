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
 *   Sorted-order visual for [3,2,1,5,6,4], k = 2:
 *     sort values -> [1,2,3,4,5,6]
 *     the 2nd largest value is at index length-k = 4
 *     return sorted[4] = 5
 *
 * Complexity
 * O(n log n) time and O(n) auxiliary space.
 */
public class P35_KthLargestElement {
    public static int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    }
}
