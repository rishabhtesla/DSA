package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given an array of integers 'nums' sorted in non-decreasing order, find the starting and ending position 
 * of a given 'target' value [00:00:17]. If 'target' is not found in the array, return [-1, -1] [00:01:06]. 
 * You must write an algorithm with O(log n) runtime complexity [00:01:12].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [5, 7, 7, 8, 8, 10], target = 8 [00:01:27]
 * - Process: Target 8 spans across index 3 and index 4 [00:01:38].
 * - Result: [3, 4]
 * 
 * Example 2: nums = [10, 20, 20, 30, 30, 30, 40, 40, 50], target = 30 (Simulated inside the video explanation [00:02:45])
 * - Process:
 *   - To find the FIRST position: When `nums[mid] == 30`, lock index as a potential answer and continue 
 *     searching greedily in the LEFT search space (`end = mid - 1`) [00:05:08]. First match occurs at index 3.
 *   - To find the LAST position: When `nums[mid] == 30`, lock index as a potential answer and continue 
 *     searching greedily in the RIGHT search space (`start = mid + 1`) [00:14:23]. Last match occurs at index 5.
 * - Result: [3, 5]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Double Binary Search):
 * • Index Initialization: Run two distinct binary searches. Pointers 'start' begins at 0, 'end' begins at nums.length - 1 [00:07:29].
 * • Condition Boundaries: Loops continue while `start <= end` [00:07:47].
 * • Operational Steps:
 *   1. Calculate the mid index: `mid = start + (end - start) / 2` [00:07:59].
 *   2. For First Occurrence: If `nums[mid] == target`, cache `ans = mid` and discard the right side via `end = mid - 1` [00:08:24].
 *   3. For Last Occurrence: If `nums[mid] == target`, cache `ans = mid` and discard the left side via `start = mid + 1` [00:14:12].
 *   4. Standard binary search shifts apply for inequality conditions (`target > nums[mid]` or `target < nums[mid]`) [00:08:44].
 * • Time Complexity: O(log n) - Composed of two independent sequential O(log n) passes [00:01:12].
 * • Space Complexity: O(1) - Primitive local indexing parameters evaluated completely in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   Standard binary searches terminate early upon matching any random target element index. To locate precise sequence borders, 
 *   we cache the matching position as a current optimal state and step outwards until boundary conditions break, 
 *   narrowing down the exact duplicate limits [00:12:03].
 * 
 * ---
 * VISUAL DRY RUN (nums = [10, 20, 20, 30, 30, 30, 40, 40, 50], target = 30):
 * [First Position Pass]
 * Step 1: start=0, end=8 -> mid=4 (value 30) -> Match! ans=4. Shrink space left: end = 4-1 = 3 [00:05:08].
 * Step 2: start=0, end=3 -> mid=1 (value 20) -> 30 > 20. Shift right space: start = 1+1 = 2 [00:05:52].
 * Step 3: start=2, end=3 -> mid=2 (value 20) -> 30 > 20. Shift right space: start = 2+1 = 3 [00:06:17].
 * Step 4: start=3, end=3 -> mid=3 (value 30) -> Match! ans=3. Shrink space left: end = 3-1 = 2 [00:06:39].
 * Terminated (start > end). First Position = 3.
 */
public class FirstAndLastPositionSortedArray {

    // APPROACH 1: Double Binary Search (Anchor Strategy)
    public static int[] searchRangeOptimal(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        if (nums == null || nums.length == 0) return result;
        
        result[0] = findFirstOccurrence(nums, target);
        result[1] = findLastOccurrence(nums, target);
        
        return result;
    }

    private static int findFirstOccurrence(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        int ans = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                ans = mid;
                end = mid - 1; // Greedily pull boundary leftward
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return ans;
    }

    private static int findLastOccurrence(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        int ans = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                ans = mid;
                start = mid + 1; // Greedily push boundary rightward
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return ans;
    }

    // APPROACH 2: Single Core Binary Search with Local Scan Offset
    // Runs one standard binary search to find any valid pivot match. From that index, it scans outwards 
    // linearly to define extreme ends. Worst-case scenario matches O(n) if all elements duplicate the target.
    public static int[] searchRangePivotScan(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[]{-1, -1};
        
        int start = 0, end = nums.length - 1;
        int pivotIndex = -1;
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                pivotIndex = mid;
                break;
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        
        if (pivotIndex == -1) return new int[]{-1, -1};
        
        int first = pivotIndex;
        while (first > 0 && nums[first - 1] == target) {
            first--;
        }
        
        int last = pivotIndex;
        while (last < nums.length - 1 && nums[last + 1] == target) {
            last++;
        }
        
        return new int[]{first, last};
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Utilizing standard filtering operations forces complete structural scanning, 
    // dropping time efficiency from O(log n) down to linear O(n) times while generating boxing overhead.
    public static int[] searchRangeStream(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[]{-1, -1};

        List<Integer> matchingIndices = IntStream.range(0, nums.length)
                .filter(i -> nums[i] == target)
                .boxed()
                .collect(Collectors.toList());

        if (matchingIndices.isEmpty()) {
            return new int[]{-1, -1};
        }
        
        return new int[]{matchingIndices.get(0), matchingIndices.get(matchingIndices.size() - 1)};
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Duplicates) ---
        int[] test1 = {5, 7, 7, 8, 8, 10};
        int[] res1_1 = searchRangeOptimal(test1, 8);
        int[] res1_2 = searchRangePivotScan(test1, 8);
        int[] res1_3 = searchRangeStream(test1, 8);

        System.out.println("Test Case 1: [5, 7, 7, 8, 8, 10], target = 8");
        System.out.println("Approach 1 (Double BS)  Result: " + Arrays.toString(res1_1));
        System.out.println("Approach 2 (Pivot Scan) Result: " + Arrays.toString(res1_2));
        System.out.println("Approach 3 (Stream API) Result: " + Arrays.toString(res1_3));
        System.out.println("Verification: " + (res1_1[0] == 3 && res1_1[1] == 4 && res1_2[0] == 3 && res1_2[1] == 4 && res1_3[0] == 3 && res1_3[1] == 4 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {10, 20, 20, 30, 30, 30, 40, 40, 50};
        int[] res2_1 = searchRangeOptimal(test2, 30);
        int[] res2_2 = searchRangePivotScan(test2, 30);
        int[] res2_3 = searchRangeStream(test2, 30);

        System.out.println("Test Case 2: [10, 20, 20, 30, 30, 30, 40, 40, 50], target = 30");
        System.out.println("Approach 1 (Double BS)  Result: " + Arrays.toString(res2_1));
        System.out.println("Approach 2 (Pivot Scan) Result: " + Arrays.toString(res2_2));
        System.out.println("Approach 3 (Stream API) Result: " + Arrays.toString(res2_3));
        System.out.println("Verification: " + (res2_1[0] == 3 && res2_1[1] == 5 && res2_2[0] == 3 && res2_2[1] == 5 && res2_3[0] == 3 && res2_3[1] == 5 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}