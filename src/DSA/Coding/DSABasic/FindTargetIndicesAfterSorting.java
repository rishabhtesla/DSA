package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given a 0-indexed integer array 'nums' and a target value 'target', return a list of the target indices 
 * of 'nums' after sorting 'nums' in non-decreasing order [00:00:18, 00:00:35]. The returned list must 
 * be sorted in increasing order. If there are no target indices, return an empty list.
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [1, 2, 5, 2, 3], target = 2 [00:00:35]
 * - Process: Sorting the array yields [1, 2, 2, 3, 5] [00:00:51]. 
 *   The target value 2 appears at index 1 and index 2 [00:01:05].
 * - Result: [1, 2]
 * 
 * Example 2: nums = [1, 2, 5, 2, 8, 7, 3, 3], target = 3 (Simulated inside the video explanation [00:06:59])
 * - Process:
 *   - Count elements strictly less than target (3): 1, 2, 2 -> `smallerCount` = 3 [00:07:54].
 *   - Count target duplicates: 3 appears twice -> `targetCount` = 2 [00:07:59].
 *   - In a sorted array, the elements smaller than target occupy the first 3 indices (0, 1, 2) [00:08:51].
 *   - The target values will strictly start at index `smallerCount` = 3, and repeat for `targetCount` = 2 iterations.
 *   - Pockets filled: index 3 and index 4 [00:08:59].
 * - Result: [3, 4]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Linear Counting Optimization):
 * • Index Initialization: Single loop index tracking sweeps across the elements of 'nums' linearly [00:03:49].
 * • Condition Boundaries: Two main counters are incremented: `smallerCount` tracks values < target, 
 *   and `targetCount` tracks values == target [00:03:59].
 * • Operational Steps:
 *   1. Initialize `smallerCount = 0` and `targetCount = 0` [00:03:31].
 *   2. Scan the array to populate both metrics [00:03:49].
 *   3. Loop `targetCount` times, adding `smallerCount++` to the results list sequentially [00:05:59].
 * • Time Complexity: O(n) - Single pass for tracking counters and an independent bound loop [00:01:43].
 * • Space Complexity: O(1) - Evaluated completely in-place ignoring output allocation memory.
 * • LOGIC BEHIND THIS APPROACH:
 *   Sorting an array forces all identical items to form a continuous block [00:03:39]. The starting position 
 *   of the block matches the total number of items strictly smaller than the target [00:01:59, 00:09:08]. By counting 
 *   smaller items and target frequencies, we determine the exact post-sort indexing range without sorting [00:02:12].
 * 
 * ---
 * VISUAL DRY RUN (nums = [1, 2, 5, 2, 8, 7, 3, 3], target = 3):
 * Map Construction Pass [00:07:54]:
 * Element 1 (< 3) -> smallerCount = 1
 * Element 2 (< 3) -> smallerCount = 2
 * Element 5 (> 3) -> Skip
 * Element 2 (< 3) -> smallerCount = 3
 * Element 8 (> 3) -> Skip
 * Element 7 (> 3) -> Skip
 * Element 3 (==3) -> targetCount = 1
 * Element 3 (==3) -> targetCount = 2
 * Result Generation Pass (targetCount = 2, smallerCount = 3) [00:08:09]:
 * Iteration 1: targetCount > 0 -> Add smallerCount (3) to list. smallerCount++ becomes 4. targetCount-- becomes 1 [00:08:18].
 * Iteration 2: targetCount > 0 -> Add smallerCount (4) to list. smallerCount++ becomes 5. targetCount-- becomes 0 [00:08:29].
 * Loop End. Returned List = [3, 4] [00:09:08].
 */
public class FindTargetIndicesAfterSorting {

    // APPROACH 1: Linear Counting Optimization (Anchor Strategy)
    public static List<Integer> targetIndicesCounting(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new ArrayList<>();

        int smallerCount = 0;
        int targetCount = 0;

        // Single pass logic to acquire relational counting metrics [00:03:49]
        for (int num : nums) {
            if (num == target) {
                targetCount++;
            } else if (num < target) {
                smallerCount++;
            }
        }

        List<Integer> result = new ArrayList<>();
        // Sequentially build the dynamic index array allocation [00:05:59]
        while (targetCount > 0) {
            result.add(smallerCount);
            smallerCount++;
            targetCount--;
        }
        return result;
    }

    // APPROACH 2: In-Place Library Sort & Double Binary Search Border Detection
    // Sorts the primitive array natively using quicksort steps in O(n log n). Then runs binary boundary loops 
    // to isolate the identical segment extremities, reducing iterative matching scan costs [00:01:15].
    public static List<Integer> targetIndicesBinarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new ArrayList<>();

        // In-place sorting operation [00:01:22]
        Arrays.sort(nums);

        int first = findBound(nums, target, true);
        if (first == -1) return new ArrayList<>(); // Target absent completely
        int last = findBound(nums, target, false);

        List<Integer> result = new ArrayList<>();
        for (int i = first; i <= last; i++) {
            result.add(i);
        }
        return result;
    }

    private static int findBound(int[] nums, int target, boolean findFirst) {
        int start = 0, end = nums.length - 1;
        int bound = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                bound = mid;
                if (findFirst) end = mid - 1;
                else start = mid + 1;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return bound;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Utilizing standard streaming configurations natively generates extra object boxes. 
    // While the filter-sort mapping remains clean, performance drops to O(n log n) with supplementary spatial footprint.
    public static List<Integer> targetIndicesStream(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new ArrayList<>();

        // Sort array in-place first to preserve mapping sequence contracts
        Arrays.sort(nums);

        return IntStream.range(0, nums.length)
                .filter(i -> nums[i] == target)
                .boxed()
                .collect(Collectors.toList());
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 ---
        int[] test1_base = {1, 2, 5, 2, 3};
        int[] t1_1 = test1_base.clone();
        int[] t1_2 = test1_base.clone();
        int[] t1_3 = test1_base.clone();
        
        List<Integer> res1_1 = targetIndicesCounting(t1_1, 2);
        List<Integer> res1_2 = targetIndicesBinarySearch(t1_2, 2);
        List<Integer> res1_3 = targetIndicesStream(t1_3, 2);

        System.out.println("Test Case 1: [1, 2, 5, 2, 3], target = 2");
        System.out.println("Approach 1 (Counting)   Result: " + res1_1);
        System.out.println("Approach 2 (Binary S)   Result: " + res1_2);
        System.out.println("Approach 3 (Stream API) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1.equals(List.of(1, 2)) && res1_2.equals(List.of(1, 2)) && res1_3.equals(List.of(1, 2)) ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2_base = {1, 2, 5, 2, 8, 7, 3, 3};
        int[] t2_1 = test2_base.clone();
        int[] t2_2 = test2_base.clone();
        int[] t2_3 = test2_base.clone();

        List<Integer> res2_1 = targetIndicesCounting(t2_1, 3);
        List<Integer> res2_2 = targetIndicesBinarySearch(t2_2, 3);
        List<Integer> res2_3 = targetIndicesStream(t2_3, 3);

        System.out.println("Test Case 2: [1, 2, 5, 2, 8, 7, 3, 3], target = 3");
        System.out.println("Approach 1 (Counting)   Result: " + res2_1);
        System.out.println("Approach 2 (Binary S)   Result: " + res2_2);
        System.out.println("Approach 3 (Stream API) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1.equals(List.of(3, 4)) && res2_2.equals(List.of(3, 4)) && res2_3.equals(List.of(3, 4)) ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}