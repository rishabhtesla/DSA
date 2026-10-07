package DSA.agoda;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * [AG-04 / 06] - PIVOT ELEMENTS IN UNSORTED ARRAY (Agoda Classic OA & Live Round)
 * ============================================================================
 * 
 * PROBLEM:
 *   An element in an unsorted array arr is defined as a "Pivot Element" if:
 *   - It is strictly greater than ALL elements to its left, AND
 *   - It is strictly smaller than ALL elements to its right.
 *   Find all pivot elements and return them in order.
 *   Note: Boundary elements (first and last) need only satisfy the valid side.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Naive search: For each index i, scan all left and right elements -> O(n^2).
 *   - Prefix Max & Suffix Min Optimization:
 *     Instead of checking all elements individually:
 *     - An element `arr[i]` is greater than all left elements if: `arr[i] > prefixMax[i]`.
 *     - An element `arr[i]` is smaller than all right elements if: `arr[i] < suffixMin[i]`.
 *   - Two-Pass Linear Solution:
 *     Pass 1 (Right to Left): Precompute `suffixMin[i] = min(arr[i+1...n-1])`.
 *     Pass 2 (Left to Right): Track running `prefixMax = max(arr[0...i-1])`.
 *     If both conditions hold, `arr[i]` is a pivot element!
 *
 * COMPLEXITY:
 *   - Time:  O(n) - One backward pass to fill suffix array, one forward pass to collect pivots.
 *   - Space: O(n) - Suffix minimum lookup array.
 */
public class AG04_FindPivotElements {

    public static List<Integer> findPivots(int[] arr) {
        List<Integer> pivots = new ArrayList<>();
        if (arr == null || arr.length == 0) return pivots;

        int n = arr.length;
        int[] suffixMin = new int[n];

        // Suffix min of last element is infinity (no right elements exist)
        suffixMin[n - 1] = Integer.MAX_VALUE;
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], arr[i + 1]);
        }

        int prefixMax = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            boolean validLeft = (i == 0) || (arr[i] > prefixMax);
            boolean validRight = (i == n - 1) || (arr[i] < suffixMin[i]);

            if (validLeft && validRight) {
                pivots.add(arr[i]);
            }

            prefixMax = Math.max(prefixMax, arr[i]);
        }

        return pivots;
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 3, 1, 8, 9, 11, 10, 15};
        System.out.println("AG04 Output (Test 1): " + findPivots(arr1)); 
        // Expected: [8, 15] (8 has leftMax=3 and rightMin=9; 15 has leftMax=11 and no right elements)

        int[] arr2 = {5, 1, 4, 3, 6, 8, 10, 7, 9};
        System.out.println("AG04 Output (Test 2): " + findPivots(arr2)); 
        // Expected: [6]
    }
}