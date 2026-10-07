package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [01 / 24] - MERGE SORTED ARRAY (LeetCode 88)
 * ============================================================================
 * 
 * PROBLEM:
 *   You are given two integer arrays, nums1 and nums2, sorted in non-decreasing
 *   order, and two integers m and n, representing the number of valid elements
 *   in nums1 and nums2 respectively.
 *   nums1 has a total capacity of m + n, where the last n positions are padded with 0.
 *   Merge nums2 into nums1 in-place such that nums1 becomes a single sorted array.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Naive: Insert nums2 at nums1[m..m+n-1] and sort -> O((m+n) log(m+n)). Fails in-place spirit.
 *   - Two pointers from front: If we insert from index 0, we must shift elements or use 
 *     an extra buffer -> O(m) space.
 *   - Aha! Moment: The free space sits at the BACK of nums1 (indices m to m+n-1).
 *     If we compare elements backwards from largest to smallest, the largest element
 *     goes to index (m + n - 1). We will NEVER overwrite an unread value in nums1
 *     because p1 only retreats, leaving untouched elements safely behind it.
 *
 * COMPLEXITY:
 *   - Time:  O(m + n) - Each element is placed exactly once.
 *   - Space: O(1)     - In-place modification with zero extra memory.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P01_MergeSortedArray {

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;          // Pointer to last active item in nums1
        int p2 = n - 1;          // Pointer to last item in nums2
        int write = m + n - 1;   // Pointer to back of nums1

        // Loop as long as nums2 has items to merge
        while (p2 >= 0) {
            // If nums1 has elements remaining AND its value is larger
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