package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * [01 / 24] - MERGE SORTED ARRAY (LeetCode 88)
 *
 * PROBLEM:
 * Given sorted integer arrays `nums1` and `nums2`, merge `nums2` into `nums1`
 * in non-decreasing order. `nums1` has length `m + n`; its first `m` values
 * are valid and its final `n` positions are empty buffer space. Modify nums1
 * in-place and return nothing.
 *
 * CONSTRAINTS:
 * - 0 <= m, n <= 200
 * - 1 <= m + n <= 200
 * - nums1 and nums2 are sorted in non-decreasing order.
 *
 * EXAMPLES:
 * - nums1=[1,2,3,0,0,0], m=3, nums2=[2,5,6], n=3 -> [1,2,2,3,5,6]
 * - nums1=[1], m=1, nums2=[], n=0 -> [1]
 * - nums1=[0], m=0, nums2=[1], n=1 -> [1]
 *
 * BRUTE FORCE:
 * Copy nums2 into the empty part of nums1, then sort all m+n values.
 * Time: O((m+n) log(m+n)); extra space depends on the sorting implementation.
 *
 * MAIN LOGIC:
 * Compare the largest unread values from the ends of both arrays. Write the
 * larger value at `write = m+n-1`, then move that source pointer backwards.
 * Filling from right to left protects unread nums1 values from being overwritten.
 * Invariant: every position after `write` is already final and sorted.
 * Time: O(m+n); extra space: O(1).
 *
 * VISUAL DRY RUN:
 * nums1=[1,2,3,0,0,0], nums2=[2,5,6]; p1=2, p2=2, write=5
 * - 3 vs 6 -> write 6 at index 5; p2=1, write=4
 *   nums1=[1,2,3,0,0,6]
 * - 3 vs 5 -> write 5 at index 4; p2=0, write=3
 *   nums1=[1,2,3,0,5,6]
 * - 3 vs 2 -> write 3 at index 3; p1=1, write=2
 *   nums1=[1,2,3,3,5,6]
 * - 2 vs 2 -> write nums2's 2 at index 2; p2=-1, write=1
 *   nums1=[1,2,2,3,5,6]
 * nums2 is exhausted; nums1's remaining [1,2] is already correctly placed.
 *
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is writing from the back safer than writing from the front?
 * 2. What exactly is finalized after each iteration?
 * 3. Why can the algorithm stop when nums2 is exhausted?
 */
public class P01_MergeSortedArray {

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;          // Pointer to last active item in nums1
        int p2 = n - 1;          // Pointer to last item in nums2
        int write = m + n - 1;   // Pointer to back of nums1

        // The suffix after write is finalized after every iteration.
        while (p2 >= 0) {
            // Compare the largest unread values and place the larger one at write.
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[write] = nums1[p1];
                p1--;
            } else {
                nums1[write] = nums2[p2];
                p2--;
            }
            write--;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        merge(nums1, 3, nums2, 3);
        System.out.println("P01 Output: " + Arrays.toString(nums1));
        // Expected: [1, 2, 2, 3, 5, 6]
    }
}