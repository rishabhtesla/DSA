package DSA.DP;

import java.util.Arrays;

/**
 * ============================================================================
 * [20 / 30] - LONGEST BITONIC SUBSEQUENCE (GeeksforGeeks / Standard Assessment)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an array of positive integers `nums`, find the length of the longest 
 * bitonic subsequence.
 * A subsequence is called BITONIC if it first strictly increases, and after a 
 * peak element, strictly decreases.
 *   - Purely increasing or purely decreasing subsequences are also valid bitonic sequences.
 *
 *
 * 2. RECURRENCE & PEAK DECOMPOSITION:
 * ----------------------------------------------------------------------------
 * Every bitonic sequence has a "peak" element at some index `i`.
 * To maximize the total length around peak `i`:
 *   Total length = (LIS ending at i) + (LDS starting at i) - 1
 *   *(We subtract 1 because the peak element `nums[i]` is counted in both parts).*
 *
 * Dual Array DP:
 *   1. `inc[i]`: Length of the longest strictly increasing subsequence ending at `i`.
 *      - Fill from LEFT to RIGHT:
 *        inc[i] = 1 + max(inc[j]) for all j < i with nums[j] < nums[i]
 *
 *   2. `dec[i]`: Length of the longest strictly decreasing subsequence starting at `i` 
 *      (which is equivalent to LIS ending at `i` from right to left).
 *      - Fill from RIGHT to LEFT:
 *        dec[i] = 1 + max(dec[j]) for all j > i with nums[j] < nums[i]
 *
 * Global Answer:
 *   max(inc[i] + dec[i] - 1) for all 0 <= i < n
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * nums = [1, 11, 2, 10, 4, 5, 2, 1]
 *
 * Left-to-Right LIS (`inc`):
 *   i=0 (1):  1
 *   i=1 (11): 2
 *   i=2 (2):  2
 *   i=3 (10): 3
 *   i=4 (4):  3
 *   i=5 (5):  4  ([1, 2, 4, 5])
 *   i=6 (2):  2
 *   i=7 (1):  1
 *   inc = [1, 2, 2, 3, 3, 4, 2, 1]
 *
 * Right-to-Left LDS (`dec`):
 *   i=7 (1):  1
 *   i=6 (2):  2  ([2, 1])
 *   i=5 (5):  3  ([5, 2, 1])
 *   i=4 (4):  3  ([4, 2, 1])
 *   i=3 (10): 4  ([10, 5, 2, 1])
 *   i=2 (2):  2
 *   i=1 (11): 5  ([11, 10, 5, 2, 1])
 *   i=0 (1):  1
 *   dec = [1, 5, 2, 4, 3, 3, 2, 1]
 *
 * Combining at each peak `i` (inc[i] + dec[i] - 1):
 *   i=1: 2 + 5 - 1 = 6  (Sequence: [1, 11, 10, 5, 2, 1])
 *   i=3: 3 + 4 - 1 = 6  (Sequence: [1, 2, 10, 5, 2, 1])
 *   i=5: 4 + 3 - 1 = 6  (Sequence: [1, 2, 4, 5, 2, 1])
 *
 * Maximum Bitonic Length = 6
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n^2) - Two O(n^2) DP passes.
 * - Space Complexity: O(n) - Two auxiliary arrays `inc` and `dec`.
 */
public class P20_LongestBitonicSubsequence {

    public static int longestBitonicSequence(int[] nums) {
        int n = nums.length;
        if (n <= 1) return n;

        int[] inc = new int[n];
        int[] dec = new int[n];
        Arrays.fill(inc, 1);
        Arrays.fill(dec, 1);

        // Step 1: Compute LIS from left to right
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    inc[i] = Math.max(inc[i], 1 + inc[j]);
                }
            }
        }

        // Step 2: Compute LDS from right to left
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (nums[j] < nums[i]) {
                    dec[i] = Math.max(dec[i], 1 + dec[j]);
                }
            }
        }

        // Step 3: Find peak that maximizes (inc[i] + dec[i] - 1)
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, inc[i] + dec[i] - 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 11, 2, 10, 4, 5, 2, 1};
        System.out.println("P20 Longest Bitonic Subsequence: " + longestBitonicSequence(nums1)); // Expected: 6

        int[] nums2 = {12, 11, 40, 5, 3, 1};
        System.out.println("P20 Longest Bitonic Subsequence: " + longestBitonicSequence(nums2)); // Expected: 5 (12 -> 40 -> 5 -> 3 -> 1)
    }
}