package DSA.Coding.DSABasic;

import java.util.Arrays;

public class MedianSortedArraysRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, 
    return the median of the two sorted arrays.
    The overall run time complexity should be O(log (m+n)).

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: nums1 = [1, 3], nums2 = [2] [00:01:44]
    - Merged array: [1, 2, 3] (Odd Length = 3).
    - Median is the exact middle element: 2.
    - Output: 2.00000

    Example B: nums1 = [1, 2], nums2 = [3, 4] [00:02:05]
    - Merged array: [1, 2, 3, 4] (Even Length = 4).
    - Median is the average of the two middle elements: (2 + 3) / 2 = 2.5.
    - Output: 2.50000

    LOGIC BEHIND APPROACH 1 (Two-Pointer Merge Strategy):
    ----------------------------------------------------------------------------
    1. Virtual Combined Array: We allocate an auxiliary space array `ans` of size `m + n` [00:09:14].
    2. Boundary Clamping via Max Value: When tracing indices using `p1` and `p2`, if one pointer 
       crosses out of its array bounds, reading it directly causes an IndexOutOfBoundsException [00:07:46]. 
       We intercept this using a ternary guard condition: if an array is exhausted, we treat its current value 
       as `Integer.MAX_VALUE` [00:10:50]. Because we are always selecting the smaller element, this large mock value 
       naturally forces the algorithm to drain all remaining items from the active array.
    3. Median Index Derivation:
       - If the total length is **Odd**, the exact middle element lies at `length / 2` [00:21:22].
       - If the total length is **Even**, the median is the midpoint of elements at `(length / 2)` and `(length / 2) - 1` [00:20:06].
    4. Type-Cast Guarding: In Java, executing `int / int` drops decimal remainders (e.g., `13 / 2 = 6`). 
       We explicitly prepend `(double)` to preserve floating-point accuracy during midpoint division [00:22:43].

    VISUAL DRY RUN (Two-Pointer Strategy for nums1 = [1, 3], nums2 = [2]):
    ----------------------------------------------------------------------------
    - Initial: p1 = 0, p2 = 0, p3 = 0, ans = [_, _, _]
    - Cycle 1: val1 = nums1[0] = 1, val2 = nums2[0] = 2.
               val1 (1) < val2 (2) -> ans[0] = 1. p1 becomes 1, p3 becomes 1.
    - Cycle 2: val1 = nums1[1] = 3, val2 = nums2[0] = 2.
               val2 (2) < val1 (3) -> ans[1] = 2. p2 becomes 1 (out of bounds), p3 becomes 2.
    - Cycle 3: val1 = nums1[1] = 3, val2 = Integer.MAX_VALUE (exhausted).
               val1 (3) < val2 -> ans[2] = 3. p1 becomes 2 (out of bounds), p3 becomes 3.
    - Total Length = 3 (Odd). Median index = 3 / 2 = 1. ans[1] = 2. Returns 2.0.

    TIME COMPLEXITY: O(M + N) - Linearly maps and reads all items across both inputs.
    SPACE COMPLEXITY: O(M + N) - Allocates an explicit table array to store merged outputs.
    ================================================================================
    */

    // Helper method to merge two sorted arrays seamlessly (Video Logic) [00:08:48]
    public static int[] mergeArrays(int[] arr1, int[] arr2) {
        int[] ans = new int[arr1.length + arr2.length];
        int p1 = 0, p2 = 0, p3 = 0;

        // Loop runs continuously until both structural elements are exhausted [00:09:53]
        while (p1 < arr1.length || p2 < arr2.length) {
            // Ternary conditions guard bounds by substituting exhausted arrays with MAX_VALUE [00:10:34]
            int val1 = (p1 < arr1.length) ? arr1[p1] : Integer.MAX_VALUE;
            int val2 = (p2 < arr2.length) ? arr2[p2] : Integer.MAX_VALUE;

            if (val1 < val2) {
                ans[p3] = val1;
                p1++;
            } else {
                ans[p3] = val2;
                p2++;
            }
            p3++;
        }
        return ans;
    }

    // Approach 1: Two-Pointer Merge Simulation (Video Strategy)
    public static double findMedianSortedArraysMerge(int[] nums1, int[] nums2) {
        int[] merged = mergeArrays(nums1, nums2);
        int totalLen = merged.length;

        // Even length condition check [00:22:02]
        if (totalLen % 2 == 0) {
            int mid1 = merged[totalLen / 2];
            int mid2 = merged[(totalLen / 2) - 1];
            return (double) (mid1 + mid2) / 2.0; // Type-cast to double to preserve decimals [00:22:43]
        } else {
            // Odd length condition [00:22:58]
            return (double) merged[totalLen / 2];
        }
    }

    /*
    ================================================================================
    APPROACH 2: Optimized Binary Search / Partitioning (Ideal Solution)
    - Instead of merging the arrays, we find a valid cut point (partition) that divides 
      both arrays into two halves such that the left half contains the smaller elements 
      and the right half contains the larger elements.
    - We binary search on the smaller array to find the correct partition index `i`.
    - TIME COMPLEXITY: O(log(min(M, N))) - Highly efficient logarithmic performance.
    - SPACE COMPLEXITY: O(1) - Uses constant pointers without creating a new array.
    ================================================================================
    */
    public static double findMedianSortedArraysBinarySearch(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array to minimize binary search range
        if (nums1.length > nums2.length) {
            return findMedianSortedArraysBinarySearch(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int low = 0, high = m;

        while (low <= high) {
            int partitionX = (low + high) / 2;
            int partitionY = (m + n + 1) / 2 - partitionX;

            // If partitionX is 0 it means nothing is on left side of nums1. Use -INF
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            // If partitionX is equal to length of nums1, nothing is on right side. Use +INF
            int minRightX = (partitionX == m) ? Integer.MAX_VALUE : nums1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == n) ? Integer.MAX_VALUE : nums2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                // Correct partition found
                if ((m + n) % 2 == 0) {
                    return ((double) Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
                } else {
                    return (double) Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) {
                // Move towards left in nums1
                high = partitionX - 1;
            } else {
                // Move towards right in nums1
                low = partitionX + 1;
            }
        }
        return 0.0;
    }

    private static void verifyBothApproaches(int[] nums1, int[] nums2, double expected) {
        System.out.println("Nums1 Array: " + Arrays.toString(nums1) + " | Nums2 Array: " + Arrays.toString(nums2));
        System.out.println("Expected Median : " + expected);
        System.out.println("1. Video Merge  : " + findMedianSortedArraysMerge(nums1, nums2));
        System.out.println("2. Binary Search: " + findMedianSortedArraysBinarySearch(nums1, nums2));
        System.out.println("Status          : " + 
            (findMedianSortedArraysMerge(nums1, nums2) == expected && 
             findMedianSortedArraysBinarySearch(nums1, nums2) == expected ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 4: MEDIAN OF TWO SORTED ARRAYS ===\n");

        // Case 1: Odd combined length simulation [00:01:44]
        verifyBothApproaches(new int[]{1, 3}, new int[]{2}, 2.0);

        // Case 2: Even combined length simulation [00:02:05]
        verifyBothApproaches(new int[]{1, 2}, new int[]{3, 4}, 2.5);

        // Case 3: Unequal sized array configuration
        verifyBothApproaches(new int[]{0, 0}, new int[]{0, 0}, 0.0);

        // Case 4: One empty array boundary check
        verifyBothApproaches(new int[]{}, new int[]{1}, 1.0);
    }
}