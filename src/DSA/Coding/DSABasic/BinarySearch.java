package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given an array of integers 'nums' which is sorted in ascending order, and an integer 'target', 
 * write a function to search 'target' in 'nums' [00:00:54]. If 'target' exists, then return its index. 
 * Otherwise, return -1 [00:01:12]. You must write an algorithm with O(log n) runtime complexity [00:01:41].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [-1, 0, 3, 5, 9, 12], target = 9
 * - Process: Target 9 is compared against structural middle values. It matches index 4.
 * - Result: 4
 * 
 * Example 2: nums = [10, 20, 30, 40, 60], target = 70 (Simulated inside the video explanation [00:12:29])
 * - Process:
 *   - 'start' initializes to 0, 'end' to 4. Mid points to index 2 (value 30) [00:12:44].
 *   - 70 > 30, so 'start' shifts to mid + 1 = 3 [00:12:58].
 *   - New mid points to index 3 (value 40). 70 > 40, so 'start' shifts to mid + 1 = 4 [00:13:13].
 *   - New mid points to index 4 (value 60). 70 > 60, so 'start' shifts to mid + 1 = 5 [00:13:45].
 *   - The condition 'start <= end' (5 <= 4) fails, terminating the lookup loop [00:14:08].
 * - Result: -1
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Iterative Divide and Conquer):
 * • Index Initialization: Pointer 'start' begins at 0, 'end' begins at nums.length - 1 [00:08:25].
 * • Condition Boundaries: The verification loop continues execution as long as 'start <= end' [00:08:39].
 * • Operational Steps:
 *   1. Calculate the mid position: `mid = start + (end - start) / 2` [00:08:49].
 *   2. If `nums[mid] == target`, return `mid` index immediately [00:09:07].
 *   3. If `target > nums[mid]`, target lies in right partition; set `start = mid + 1` [00:09:20].
 *   4. If `target < nums[mid]`, target lies in left partition; set `end = mid - 1` [00:09:40].
 * • Time Complexity: O(log n) - Cuts the active searching space in half at each iteration step [00:01:41].
 * • Space Complexity: O(1) - Constant tracking registers modified purely in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   A pre-sorted data structure guarantees ordered distribution properties [00:00:30]. By testing the absolute 
 *   median element, we determine whether the target value can reside in either side. This allows us to discard 
 *   half the remaining search space with each comparison, bringing superior logarithmic scaling [00:14:41].
 * 
 * ---
 * VISUAL DRY RUN (nums = [10, 20, 30, 40, 60], target = 20):
 * Initial: start = 0, end = 4, target = 20 [00:10:59]
 * Step 1: mid = (0 + 4) / 2 = 2. nums[2] = 30. 20 < 30 -> Adjust right limit: end = mid - 1 = 1 [00:11:15].
 * Step 2: mid = (0 + 1) / 2 = 0. nums[0] = 10. 20 > 10 -> Adjust left limit: start = mid + 1 = 1 [00:11:46].
 * Step 3: mid = (1 + 1) / 2 = 1. nums[1] = 20. Match found! Return mid index 1 [00:12:20].
 */
public class BinarySearch {

    // APPROACH 1: Iterative Divide and Conquer (Anchor Strategy)
    public static int searchIterative(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start <= end) {
            // Safe mid derivation formula to avoid possible integer boundary overflow conditions
            int mid = start + (end - start) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    // APPROACH 2: Classical Tail-Recursive Structural Optimization
    // Mimics structural function call frame tracking to partition boundaries down the call stack.
    public static int searchRecursive(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        return executeBinarySearch(nums, target, 0, nums.length - 1);
    }

    private static int executeBinarySearch(int[] nums, int target, int start, int end) {
        if (start > end) return -1;
        
        int mid = start + (end - start) / 2;
        
        if (nums[mid] == target) return mid;
        
        if (target > nums[mid]) {
            return executeBinarySearch(nums, target, mid + 1, end);
        } else {
            return executeBinarySearch(nums, target, start, mid - 1);
        }
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Relies on stream indices scanning maps to execute searches. 
    // This wrapper pattern degrades time scaling from optimal log(n) back down to linear O(n) search times.
    public static int searchStream(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        return IntStream.range(0, nums.length)
                .filter(i -> nums[i] == target)
                .findFirst()
                .orElse(-1);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Target Exists) ---
        int[] test1 = {10, 20, 30, 40, 60};
        int res1_1 = searchIterative(test1, 20);
        int res1_2 = searchRecursive(test1, 20);
        int res1_3 = searchStream(test1, 20);

        System.out.println("Test Case 1: [10, 20, 30, 40, 60], target = 20");
        System.out.println("Approach 1 (Iterative) Result: " + res1_1);
        System.out.println("Approach 2 (Recursive) Result: " + res1_2);
        System.out.println("Approach 3 (Stream API) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 1 && res1_2 == 1 && res1_3 == 1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Target Absent Beyond Right Bound) ---
        int[] test2 = {10, 20, 30, 40, 60};
        int res2_1 = searchIterative(test2, 70);
        int res2_2 = searchRecursive(test2, 70);
        int res2_3 = searchStream(test2, 70);

        System.out.println("Test Case 2: [10, 20, 30, 40, 60], target = 70");
        System.out.println("Approach 1 (Iterative) Result: " + res2_1);
        System.out.println("Approach 2 (Recursive) Result: " + res2_2);
        System.out.println("Approach 3 (Stream API) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == -1 && res2_2 == -1 && res2_3 == -1 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}