package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given a sorted array of distinct integers 'nums' and a target value, return the index if the target is found.
 * If not, return the index where it would be if it were inserted in order [00:00:16].
 * You must write an algorithm with O(log n) runtime complexity [00:02:29].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [1, 3, 5, 6], target = 5
 * - Process: 5 is present at index 2.
 * - Result: 2 [00:00:52]
 * 
 * Example 2: nums = [1, 3, 5, 6], target = 2 (Simulated inside the video explanation [00:08:16])
 * - Process:
 *   - 'start' initializes at 0, 'end' at 3. Mid = 1 (val=3). 
 *   - 2 < 3, so 'end' shifts to `mid - 1 = 0` [00:09:47].
 *   - Next iteration: Start=0, End=0. Mid = 0 (val=1). 2 > 1, so 'start' shifts to `mid + 1 = 1` [00:09:59].
 *   - Loop terminates (start=1, end=0). The target 2 should be inserted at index 1.
 * - Result: 1 [00:10:24]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Lower Bound Binary Search):
 * • Index Initialization: Pointer 'start' begins at 0, 'end' begins at nums.length - 1 [00:10:30].
 * • Condition Boundaries: The binary loop continues while `start <= end` [00:10:48].
 * • Operational Steps:
 *   1. Calculate the mid position: `mid = start + (end - start) / 2` [00:10:59].
 *   2. If `nums[mid] == target`, return `mid` index [00:11:06].
 *   3. If `target > nums[mid]`, target belongs in the right side; move `start = mid + 1` [00:11:26].
 *   4. If `target < nums[mid]`, target belongs in the left side; move `end = mid - 1` [00:11:39].
 *   5. If not found, 'start' will naturally point to the insertion index [00:07:59].
 * • Time Complexity: O(log n) - Halves the search space every iteration [00:02:29].
 * • Space Complexity: O(1) - Evaluates operations entirely in-place with minimal extra overhead.
 * • LOGIC BEHIND THIS APPROACH:
 *   This is a classic "Lower Bound" binary search problem. When the target isn't found, the 'start' pointer 
 *   inevitably crosses the 'end' pointer and rests at the smallest index where `nums[index] > target`, 
 *   which is precisely the correct insertion point [00:07:53].
 * 
 * ---
 * VISUAL DRY RUN (nums = [1, 3, 5, 6], target = 2):
 * Initial: start=0, end=3, target=2
 * Iteration 1: mid = 1 (val=3). 2 < 3 -> end = 1 - 1 = 0.
 * Iteration 2: mid = 0 (val=1). 2 > 1 -> start = 0 + 1 = 1.
 * Loop Termination: start=1, end=0 (start > end). Return start = 1.
 */
public class SearchInsertPosition {

    // APPROACH 1: Lower Bound Binary Search (Anchor Strategy)
    public static int searchInsertOptimal(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

    // APPROACH 2: Linear Search (Brute Force fallback)
    // Acceptable for very small arrays, but explicitly bypassed by the problem requirements 
    // for O(log n) complexity. Provided here as a structural design alternative.
    public static int searchInsertLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: This implementation maps the problem to a stream-based finding approach.
    // While clean, it forces O(n) scan complexity due to the nature of filter-based finding, 
    // sacrificing the logarithmic performance requirement of the original problem.
    public static int searchInsertStream(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;

        return IntStream.range(0, nums.length)
                .filter(i -> nums[i] >= target)
                .findFirst()
                .orElse(nums.length);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Target Present) ---
        int[] test1 = {1, 3, 5, 6};
        int res1_1 = searchInsertOptimal(test1, 5);
        int res1_2 = searchInsertLinear(test1, 5);
        int res1_3 = searchInsertStream(test1, 5);

        System.out.println("Test Case 1: [1, 3, 5, 6], target = 5");
        System.out.println("Approach 1 (Optimal) Result: " + res1_1);
        System.out.println("Approach 2 (Linear)  Result: " + res1_2);
        System.out.println("Approach 3 (Streams) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 2 && res1_2 == 2 && res1_3 == 2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Insertion Required) ---
        int[] test2 = {1, 3, 5, 6};
        int res2_1 = searchInsertOptimal(test2, 2);
        int res2_2 = searchInsertLinear(test2, 2);
        int res2_3 = searchInsertStream(test2, 2);

        System.out.println("Test Case 2: [1, 3, 5, 6], target = 2");
        System.out.println("Approach 1 (Optimal) Result: " + res2_1);
        System.out.println("Approach 2 (Linear)  Result: " + res2_2);
        System.out.println("Approach 3 (Streams) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 1 && res2_2 == 1 && res2_3 == 1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Append required at end) ---
        int[] test3 = {1, 3, 5, 6};
        int res3_1 = searchInsertOptimal(test3, 7);
        int res3_2 = searchInsertLinear(test3, 7);
        int res3_3 = searchInsertStream(test3, 7);

        System.out.println("Test Case 3: [1, 3, 5, 6], target = 7");
        System.out.println("Approach 1 (Optimal) Result: " + res3_1);
        System.out.println("Approach 2 (Linear)  Result: " + res3_2);
        System.out.println("Approach 3 (Streams) Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 == 4 && res3_2 == 4 && res3_3 == 4 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}