package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * There is an integer array 'nums' sorted in ascending order with distinct values [00:00:29]. 
 * Prior to being passed to your function, 'nums' is possibly rotated at an unknown pivot index 
 * such that the resulting array becomes [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]].
 * Given the array 'nums' after the rotation and an integer 'target', return the index of 'target' if it is in 'nums', 
 * or -1 if it is not in 'nums' [00:01:12]. You must write an algorithm with O(log n) runtime complexity [00:03:35].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [4, 5, 6, 7, 0, 1, 2], target = 0 [00:03:51]
 * - Process: The array is split around a mid element. By identifying which half is strictly sorted 
 *   and checking boundaries, we find target 0 at index 4 [00:04:08].
 * - Result: 4
 * 
 * Example 2: nums = [50, 60, 70, 80, 90, 100, 10, 20, 30, 40], target = 30 (Simulated inside the video explanation [00:19:28])
 * - Process:
 *   - 'start' initializes to 0 (50), 'end' to 9 (40) [00:19:58]. Mid points to index 4 (value 90) [00:20:13].
 *   - Check sorted half: nums[start](50) <= nums[mid](90) -> Left side is sorted [00:20:28].
 *   - Target 30 does not fall within left boundaries (50 <= 30 <= 90 is false) [00:20:48]. 
 *     Hence, drop left half: `start = mid + 1 = 5` [00:21:03].
 *   - New mid points to index 7 (value 20). Right half is sorted (20 <= 40). 
 *     Target 30 falls in right bounds (20 <= 30 <= 40). Drop left: `start = mid + 1 = 8` [00:22:22].
 *   - New mid points to index 8 (value 30). Match found [00:23:03]!
 * - Result: 8
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Rotated Modified Binary Search):
 * • Index Initialization: Pointer 'start' begins at 0, 'end' begins at nums.length - 1 [00:13:37].
 * • Condition Boundaries: Loop runs while `start <= end` [00:13:58].
 * • Operational Steps:
 *   1. Calculate the middle index: `mid = start + (end - start) / 2` [00:14:07].
 *   2. If `nums[mid] == target`, return `mid` index immediately [00:14:24].
 *   3. Determine which half is fully sorted: if `nums[start] <= nums[mid]`, left is sorted [00:14:52], else right is sorted [00:15:07].
 *   4. Check if the target is within the sorted range to contract boundaries via `start` or `end` modifications [00:15:29].
 * • Time Complexity: O(log n) - Discards half of the remaining search space with each comparison iteration [00:03:35].
 * • Space Complexity: O(1) - Constant tracking registers evaluated purely in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   A rotated sorted array split in half always yields at least one completely sorted sub-segment [00:06:24]. 
 *   By inspecting that sorted sub-segment's endpoints, we definitively know if the target lies inside it. 
 *   If it does, we narrow the search space to that half; otherwise, we confidently search the remaining half [00:08:35].
 * 
 * ---
 * VISUAL DRY RUN (nums = [50, 60, 70, 80, 90, 100, 10, 20, 30, 40], target = 30):
 * Initial: start = 0, end = 9, target = 30
 * Iteration 1: mid = 4 (value 90). nums[0](50) <= nums[4](90) -> Left sorted. Target 30 inside [50, 90]? No. start = mid + 1 = 5.
 * Iteration 2: mid = 7 (value 20). nums[5](100) <= nums[7](20) -> False. Right sorted. Target 30 inside [20, 40]? Yes. start = mid + 1 = 8.
 * Iteration 3: mid = 8 (value 30). nums[8] == target (30). Match found! Return mid index 8.
 */
public class SearchInRotatedSortedArray {

    // APPROACH 1: Rotated Modified Binary Search (Anchor Strategy)
    public static int searchRotatedOptimal(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            // Determine if the left segment is sorted
            if (nums[start] <= nums[mid]) {
                // Verify if target falls within the sorted left boundaries
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } 
            // Otherwise, the right segment must be sorted
            else {
                // Verify if target falls within the sorted right boundaries
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    // APPROACH 2: Find Pivot and Double Binary Search Variant
    // Locates the point of structural discontinuity (the smallest element index), then performs 
    // a standard binary search on the appropriate remaining sorted subarray segment.
    public static int searchRotatedPivotBased(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        
        int n = nums.length;
        int pivot = findPivotIndex(nums);
        
        // If the array is not rotated at all, perform a standard array-wide binary search
        if (pivot == 0) {
            return standardBinarySearch(nums, target, 0, n - 1);
        }
        
        if (target >= nums[0]) {
            return standardBinarySearch(nums, target, 0, pivot - 1);
        }
        return standardBinarySearch(nums, target, pivot, n - 1);
    }

    private static int findPivotIndex(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > nums[end]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    private static int standardBinarySearch(int[] nums, int target, int start, int end) {
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) return mid;
            if (target > nums[mid]) start = mid + 1;
            else end = mid - 1;
        }
        return -1;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Relies on matching transformations inside dynamic collectors. This forces a complete 
    // O(n) structural scan over all array partitions instead of scaling gracefully down to logarithmic log(n) boundaries.
    public static int searchRotatedStream(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        return IntStream.range(0, nums.length)
                .filter(i -> nums[i] == target)
                .findFirst()
                .orElse(-1);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Classic Shift) ---
        int[] test1 = {4, 5, 6, 7, 0, 1, 2};
        int res1_1 = searchRotatedOptimal(test1, 0);
        int res1_2 = searchRotatedPivotBased(test1, 0);
        int res1_3 = searchRotatedStream(test1, 0);

        System.out.println("Test Case 1: [4, 5, 6, 7, 0, 1, 2], target = 0");
        System.out.println("Approach 1 (Optimal Rotated BS) Result: " + res1_1);
        System.out.println("Approach 2 (Pivot + Double BS)  Result: " + res1_2);
        System.out.println("Approach 3 (Functional Stream)  Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 4 && res1_2 == 4 && res1_3 == 4 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {50, 60, 70, 80, 90, 100, 10, 20, 30, 40};
        int res2_1 = searchRotatedOptimal(test2, 30);
        int res2_2 = searchRotatedPivotBased(test2, 30);
        int res2_3 = searchRotatedStream(test2, 30);

        System.out.println("Test Case 2: [50, 60, 70, 80, 90, 100, 10, 20, 30, 40], target = 30");
        System.out.println("Approach 1 (Optimal Rotated BS) Result: " + res2_1);
        System.out.println("Approach 2 (Pivot + Double BS)  Result: " + res2_2);
        System.out.println("Approach 3 (Functional Stream)  Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 8 && res2_2 == 8 && res2_3 == 8 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}