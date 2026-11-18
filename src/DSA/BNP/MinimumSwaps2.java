package DSA.BNP;

import java.util.*;

/**
 * ============================================================================
 * QUESTION 9: Minimum Swaps 2 (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * You are given an unordered array consisting of consecutive integers
 * [1, 2, 3, ..., n] without any duplicates. You are allowed to swap any two
 * elements. Find the minimum number of swaps required to sort the array in
 * ascending order.
 *
 * INPUT FORMAT:
 * - An integer array `arr` of size `n` containing a permutation of 1..n.
 *
 * CONSTRAINTS:
 * - 1 <= n <= 10^5
 * - 1 <= arr[i] <= n
 *
 * SAMPLE INPUT 0:
 *   arr = [4, 3, 1, 2]
 *
 * SAMPLE OUTPUT 0:
 *   3
 *
 * EXPLANATION 0:
 *   Given array: [4, 3, 1, 2]
 *   Swap 0 and 2 -> [1, 3, 4, 2]
 *   Swap 1 and 3 -> [1, 2, 4, 3]
 *   Swap 2 and 3 -> [1, 2, 3, 4]
 *   Minimum swaps required = 3.
 *
 * SAMPLE INPUT 1:
 *   arr = [2, 3, 4, 1, 5]
 *
 * SAMPLE OUTPUT 1:
 *   3
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (arr = [4, 3, 1, 2]):
 * ----------------------------------------------------------------------------
 * Since elements are strictly 1..n, in the sorted array, value `v` MUST reside
 * at index `v - 1` (i.e., arr[i] should equal i + 1).
 *
 * In-Place Greedy Cycle Placement:
 * Iterate `i` from 0 to n - 1:
 *   While arr[i] != i + 1:
 *     Swap arr[i] directly into its true target index `targetIdx = arr[i] - 1`.
 *     Increment `swaps`.
 *
 * Tracing arr = [4, 3, 1, 2]:
 * - i = 0:
 *     arr[0] is 4 (expected 1). Target index for 4 is 4 - 1 = 3.
 *     Swap arr[0] and arr[3] -> arr becomes [2, 3, 1, 4], swaps = 1.
 *     Still at i = 0: arr[0] is 2 (expected 1). Target index for 2 is 2 - 1 = 1.
 *     Swap arr[0] and arr[1] -> arr becomes [3, 2, 1, 4], swaps = 2.
 *     Still at i = 0: arr[0] is 3 (expected 1). Target index for 3 is 3 - 1 = 2.
 *     Swap arr[0] and arr[2] -> arr becomes [1, 2, 3, 4], swaps = 3.
 *     Now arr[0] == 1 -> exit while loop!
 * - i = 1: arr[1] == 2 -> already in place.
 * - i = 2: arr[2] == 3 -> already in place.
 * - i = 3: arr[3] == 4 -> already in place.
 *
 * Total swaps = 3.
 *
 * Why is this O(N)? Every swap places AT LEAST one number into its permanent
 * final home, so the inner while loop executes at most `n - 1` times total.
 *
 * Time Complexity:  O(N)
 * Space Complexity: O(1) in-place
 * ============================================================================
 */
public class MinimumSwaps2 {

    public static int minimumSwaps(int[] arr) {
        int swaps = 0;
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            // Keep swapping the current element to its true destination index (arr[i] - 1)
            // until the correct element (i + 1) lands at index `i`
            while (arr[i] != i + 1) {
                int targetIndex = arr[i] - 1;

                // Swap arr[i] with arr[targetIndex]
                int temp = arr[i];
                arr[i] = arr[targetIndex];
                arr[targetIndex] = temp;

                swaps++;
            }
        }

        return swaps;
    }

    public static void main(String[] args) {
        int[] sample0 = {4, 3, 1, 2};
        System.out.println("Sample 0 Input:  " + Arrays.toString(sample0));
        System.out.println("Sample 0 Output: " + minimumSwaps(sample0));
        // Expected: 3

        int[] sample1 = {2, 3, 4, 1, 5};
        System.out.println("\nSample 1 Input:  " + Arrays.toString(sample1));
        System.out.println("Sample 1 Output: " + minimumSwaps(sample1));
        // Expected: 3
    }
}