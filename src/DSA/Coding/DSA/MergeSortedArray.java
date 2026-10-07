package DSA.Coding.DSA; /**
 * =================================================================================================
 * QUICK SUMMARY
 * =================================================================================================
 * Merge two pre-sorted arrays (nums1 and nums2) into one single sorted array. 
 * The catch: You must do it in-place inside nums1 without using extra array memory. 
 * nums1 has extra empty trailing spaces (zeros) at the end to accommodate nums2.
 * 
 * =================================================================================================
 * FULL PROBLEM STATEMENT (LeetCode 88: Merge Sorted Array)
 * =================================================================================================
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers 
 * m and n, representing the number of elements in nums1 and nums2 respectively.
 * 
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 * 
 * The final sorted array should not be returned by the function, but instead be stored inside the 
 * array nums1. To accommodate this, nums1 has a length of m + n, where the first m elements 
 * denote the elements that should be merged, and the last n elements are set to 0 and should be ignored. 
 * nums2 has a length of n.
 * 
 * -------------------------------------------------------------------------------------------------
 * OFFICIAL LEETCODE EXAMPLES:
 * -------------------------------------------------------------------------------------------------
 * Example 1:
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 * Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
 * The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.
 * 
 * Example 2:
 * Input: nums1 = [1], m = 1, nums2 = [], n = 0
 * Output: [1]
 * 
 * Example 3:
 * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
 * Output: [1]
 * =================================================================================================
 * 
 * =================================================================================================
 * APPROACH OVERVIEW & COGNITIVE LOGIC
 * =================================================================================================
 * 1. Data Structure Used: 
 *    - In-place Array manipulation (Two-pointer technique). O(1) auxiliary space complexity.
 *    - O(m + n) Time Complexity because we iterate through both arrays exactly once.
 * 
 * 2. The Logic Behind the Approach:
 *    - Standard merging from index 0 requires shifting elements down, which is highly inefficient 
 *      and risks destroying elements we haven't checked yet.
 *    - Because the tail end of `nums1` is padded with dummy 0 values, we can safely build our array 
 *      from **Right to Left** (Back to Front) without overwriting active data.
 *    - We compare the largest available elements at the end of both valid boundaries (`m-1` and `n-1`)
 *      and drop the largest element directly into the absolute end of `nums1` (`m+n-1`).
 * =================================================================================================
 */

import java.util.Arrays;

public class MergeSortedArray {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // --- POINTER INITIALIZATION ---
        int i = m - 1;       // Pointer tracking the last valid active element in nums1
        int j = n - 1;       // Pointer tracking the last element in nums2
        int k = m + n - 1;   // Pointer tracking where to write the next largest element at the back of nums1

        // --- CORE MERGING LOGIC ---
        // We only loop as long as there are elements left in nums2 to merge.
        // If nums2 runs out first, whatever remains in nums1 is already sorted and in place!
        while (j >= 0) {
            
            /* 
             * EDGE CASE SAFETY CHECK (i >= 0): 
             * We must make sure nums1 hasn't run out of elements.
             * If nums1 is out of elements (i < 0) but nums2 still has elements (j >= 0),
             * it means all remaining elements in nums2 are smaller than everything else.
             * We skip the 'if' block and directly copy over nums2 elements via the 'else' block.
             */
            if (i >= 0 && nums1[i] > nums2[j]) {
                // If nums1's current element is larger, place it at the back position 'k'
                nums1[k] = nums1[i];
                i--; // Move the nums1 pointer to the left
            } else {
                // If nums2's current element is larger OR nums1 is exhausted, place nums2's element at 'k'
                nums1[k] = nums2[j];
                j--; // Move the nums2 pointer to the left
            }
            
            k--; // Move the writing placement index one step to the left
        }
    }

    public static void main(String[] args) {
        MergeSortedArray solver = new MergeSortedArray();

        System.out.println("--- 1. MERGE SORTED ARRAY EDGE CASES ---\n");

        // EDGE CASE A: nums1 has zero active elements (m = 0)
        // Explanation: Pointer 'i' starts at -1. Loop instantly skips the 'if' statement and copies nums2 directly.
        int[] nums1_A = {0, 0, 0};
        int m_A = 0;
        int[] nums2_A = {2, 5, 6};
        int n_A = 3;
        
        System.out.println("Edge Case A (m=0): nums1 has only padding, nums2 has all elements.");
        System.out.println("  INPUT  -> nums1: " + Arrays.toString(nums1_A) + ", m: " + m_A + ", nums2: " + Arrays.toString(nums2_A) + ", n: " + n_A);
        solver.merge(nums1_A, m_A, nums2_A, n_A);
        System.out.println("  OUTPUT -> nums1: " + Arrays.toString(nums1_A) + "\n");

        // EDGE CASE B: nums2 is completely empty (n = 0)
        // Explanation: Pointer 'j' starts at -1. The while loop condition 'j >= 0' evaluates to false immediately.
        int[] nums1_B = {1, 3, 5};
        int m_B = 3;
        int[] nums2_B = {};
        int n_B = 0;
        
        System.out.println("Edge Case B (n=0): nums2 is completely empty.");
        System.out.println("  INPUT  -> nums1: " + Arrays.toString(nums1_B) + ", m: " + m_B + ", nums2: " + Arrays.toString(nums2_B) + ", n: " + n_B);
        solver.merge(nums1_B, m_B, nums2_B, n_B);
        System.out.println("  OUTPUT -> nums1: " + Arrays.toString(nums1_B) + "\n");

        // EDGE CASE C: All elements of nums2 are strictly smaller than all elements of nums1
        // Explanation: Pointers track backwards. nums1 elements will constantly win, causing 'i' to drain to -1.
        // Once 'i' becomes -1, the code defaults to the 'else' block, safely wiping out remaining slots using nums2.
        int[] nums1_C = {4, 5, 6, 0, 0, 0};
        int m_C = 3;
        int[] nums2_C = {1, 2, 3};
        int n_C = 3;
        
        System.out.println("Edge Case C: All elements in nums2 are smaller than elements in nums1.");
        System.out.println("  INPUT  -> nums1: " + Arrays.toString(nums1_C) + ", m: " + m_C + ", nums2: " + Arrays.toString(nums2_C) + ", n: " + n_C);
        solver.merge(nums1_C, m_C, nums2_C, n_C);
        System.out.println("Result C: " + Arrays.toString(nums1_C) + "\n");
    }
}