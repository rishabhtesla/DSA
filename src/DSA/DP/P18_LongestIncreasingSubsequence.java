package DSA.DP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * [18 / 30] - LONGEST INCREASING SUBSEQUENCE (LeetCode 300)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an integer array `nums`, return the length of the longest strictly 
 * increasing subsequence.
 *
 *
 * 2. TWO APPROACHES:
 * ----------------------------------------------------------------------------
 * Approach A (Standard DP): O(n^2) time, O(n) space
 *   - dp[i] = length of LIS ending at index i.
 *   - dp[i] = 1 + max(dp[j]) for all j < i where nums[j] < nums[i].
 *
 * Approach B (Patience Sorting + Binary Search): O(n log n) time, O(n) space
 *   - Maintain an array/list `tails` where `tails[k]` stores the SMALLEST tail 
 *     element among all increasing subsequences of length `k + 1` found so far.
 *   - For each number `x` in `nums`:
 *       - If `x` is greater than all elements in `tails`: append `x` (LIS increases by 1).
 *       - Else: binary search the first element in `tails` that is >= `x`, and 
 *         replace it with `x` (this allows future numbers to have a lower bar to beat!).
 *   - The size of `tails` at the end is the length of the LIS.
 *
 *
 * 3. DRY RUN TRACE (Patience Sort):
 * ----------------------------------------------------------------------------
 * nums = [10, 9, 2, 5, 3, 7, 101, 18]
 *
 * x = 10: tails = [10]
 * x = 9:  replace 10 -> tails = [9]
 * x = 2:  replace 9  -> tails = [2]
 * x = 5:  5 > 2      -> tails = [2, 5]
 * x = 3:  replace 5  -> tails = [2, 3]  (Subsequence {2, 3} has a better tail than {2, 5})
 * x = 7:  7 > 3      -> tails = [2, 3, 7]
 * x = 101: 101 > 7   -> tails = [2, 3, 7, 101]
 * x = 18: replace 101-> tails = [2, 3, 7, 18]
 *
 * Result: tails.size() = 4 (e.g., [2, 3, 7, 18])
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n log n) - Binary search (log n) for each of the n elements.
 * - Space Complexity: O(n) - Auxiliary `tails` array.
 */
public class P18_LongestIncreasingSubsequence {

    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        List<Integer> tails = new ArrayList<>();

        for (int x : nums) {
            // Binary search: find index of first element >= x
            int idx = Collections.binarySearch(tails, x);

            // If not found, binarySearch returns (-(insertion point) - 1)
            if (idx < 0) {
                idx = -(idx + 1);
            }

            // If x is larger than all elements, append it
            if (idx == tails.size()) {
                tails.add(x);
            } else {
                // Otherwise, replace the existing element to keep tails as small as possible
                tails.set(idx, x);
            }
        }

        return tails.size();
    }

    public static void main(String[] args) {
        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("P18 LIS Length: " + lengthOfLIS(nums1)); // Expected: 4

        int[] nums2 = {0, 1, 0, 3, 2, 3};
        System.out.println("P18 LIS Length: " + lengthOfLIS(nums2)); // Expected: 4
    }
}