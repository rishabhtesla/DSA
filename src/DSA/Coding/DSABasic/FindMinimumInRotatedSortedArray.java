package DSA.Coding.DSABasic;

import java.util.Arrays;

/**
 * PROBLEM STATEMENT:
 * Given an integer array 'nums' of unique elements sorted in ascending order, which has been rotated between 
 * 1 and n times [00:00:48]. Find and return the minimum element of this array [00:00:25].
 * You must write an algorithm that runs in O(log n) time complexity [00:02:00].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [3, 4, 5, 1, 2]
 * - Process: The array is rotated. Looking at standard divisions, the anomaly point where the sorted order 
 *   breaks contains the boundary shift. 1 is preceded by 5, identifying 1 as the minimum element.
 * - Result: 1
 * 
 * Example 2: nums = [50, 60, 70, 80, 90, 100, 10, 20, 30, 40] (Simulated inside the video explanation [00:17:28])
 * - Process:
 *   - 'start' initializes to 0, 'end' to 9. Mid points to index 4 (value 90) [00:17:58].
 *   - Check sorted halves: nums[start](50) <= nums[mid](90) implies left is fully sorted [00:18:43]. 
 *     The absolute minimum element cannot reside within a clean ascending sequence; drop left half via `start = mid + 1 = 5` [00:19:03].
 *   - New mid points to index 7 (value 20). Left half [100, 10, 20] is unsorted (100 <= 20 is false) [00:19:58]. 
 *     Right half is sorted (20 <= 40). Drop right half via `end = mid - 1 = 6` [00:20:17].
 *   - New mid points to index 5 (value 100). Check conditions: `nums[mid](100) > nums[mid+1](10)`. 
 *     This explicitly exposes the trend-breaking inflection point. Return `nums[mid+1] = 10` [00:21:05].
 * - Result: 10
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Inflection Detection Binary Search):
 * • Index Initialization: Pointer 'start' begins at 0, 'end' begins at nums.length - 1 [00:10:37].
 * • Condition Boundaries: Main processing loop continues execution while `start <= end` [00:10:47].
 * • Operational Steps:
 *   1. Calculate middle position: `mid = start + (end - start) / 2` [00:10:54].
 *   2. If `nums[mid] > nums[mid + 1]`, then `mid + 1` is the minimum inflection element [00:13:57].
 *   3. If `nums[mid - 1] > nums[mid]`, then `mid` is the minimum inflection element [00:12:13].
 *   4. Greedily eliminate the sorted half: if `nums[start] <= nums[mid]`, left is sorted, move `start = mid + 1` [00:14:24]. 
 *      Otherwise, right is sorted, move `end = mid - 1` [00:15:21].
 * • Time Complexity: O(log n) - Halves the active search space with each boundary inspection [00:02:00].
 * • Space Complexity: O(1) - Evaluated completely in-place using localized scalar primitives.
 * • LOGIC BEHIND THIS APPROACH:
 *   A rotated sorted array contains a single inflection discontinuity point where an element is strictly smaller than its 
 *   predecessor [00:04:41]. By dividing the space, we locate this trend-breaking convergence point. Because the minimum element 
 *   disrupts the sorting pattern, it will always be found in the unsorted half of the array [00:07:53].
 * 
 * ---
 * VISUAL DRY RUN (nums = [50, 60, 70, 80, 90, 100, 10, 20, 30, 40]):
 * Initial: start = 0, end = 9, length = 10
 * Iteration 1: mid = 4 (value 90). nums[4](90) < nums[5](100) and nums[3](80) < nums[4](90). 
 *              Check sorted region: nums[0](50) <= nums[4](90) -> Left sorted. Shift right: start = 4 + 1 = 5 [00:19:03].
 * Iteration 2: start = 5, end = 9 -> mid = 7 (value 20). nums[7](20) < nums[8](30) and nums[6](10) < nums[7](20). 
 *              Check sorted region: nums[5](100) <= nums[7](20) is false -> Right sorted. Shift left: end = 7 - 1 = 6 [00:20:17].
 * Iteration 3: start = 5, end = 6 -> mid = 5 (value 100). 
 *              Condition check: `nums[5](100) > nums[6](10)` matches! Return `nums[6]` which equals 10 [00:21:05].
 */
public class FindMinimumInRotatedSortedArray {

    // APPROACH 1: Inflection Detection Binary Search (Anchor Strategy)
    public static int findMinOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        if (nums.length == 1) return nums[0]; // Base edge condition [00:03:08]
        
        // If the array is not rotated at all, the first element is the minimum [00:03:47]
        if (nums[0] < nums[nums.length - 1]) {
            return nums[0];
        }
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            
            // Check if mid element is the inflection pivot (e.g., [..., 100, 10, ...])
            if (mid < nums.length - 1 && nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }
            
            // Check if mid itself is the minimum boundary inflection point
            if (mid > 0 && nums[mid - 1] > nums[mid]) {
                return nums[mid];
            }
            
            // If the left segment is properly sorted, the minimum must be in the right segment
            if (nums[start] <= nums[mid]) {
                start = mid + 1;
            } 
            // Otherwise, the minimum must be in the left segment
            else {
                // Right side is sorted, contract right boundary downward
                end = mid - 1;
            }
        }
        return -1;
    }

    // APPROACH 2: Boundary Convergence Strategy
    // A variant that refrains from explicit tracking checks by continually contracting indices towards 
    // the absolute minimum point without explicit index boundaries overruns.
    public static int findMinConvergence(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start < end) {
            int mid = start + (end - start) / 2;
            
            // If mid value is greater than rightmost element, minimum is in the right partition
            if (nums[mid] > nums[end]) {
                start = mid + 1;
            } 
            // Otherwise, minimum is at mid or in the left partition
            else {
                end = mid;
            }
        }
        return nums[start];
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Stream allocations map linear reductions across primitive items. 
    // This degrades sorting operations from highly optimized logarithmic O(log n) down to linear O(n).
    public static int findMinStream(int[] nums) {
        if (nums == null || nums.length == 0) return -1;

        return Arrays.stream(nums)
                .min()
                .orElse(-1);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Classic Shift) ---
        int[] test1 = {3, 4, 5, 1, 2};
        int res1_1 = findMinOptimal(test1);
        int res1_2 = findMinConvergence(test1);
        int res1_3 = findMinStream(test1);

        System.out.println("Test Case 1: [3, 4, 5, 1, 2]");
        System.out.println("Approach 1 (Inflection BS)  Result: " + res1_1);
        System.out.println("Approach 2 (Convergence BS) Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)     Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 1 && res1_2 == 1 && res1_3 == 1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {50, 60, 70, 80, 90, 100, 10, 20, 30, 40};
        int res2_1 = findMinOptimal(test2);
        int res2_2 = findMinConvergence(test2);
        int res2_3 = findMinStream(test2);

        System.out.println("Test Case 2: [50, 60, 70, 80, 90, 100, 10, 20, 30, 40]");
        System.out.println("Approach 1 (Inflection BS)  Result: " + res2_1);
        System.out.println("Approach 2 (Convergence BS) Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)     Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 10 && res2_2 == 10 && res2_3 == 10 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}