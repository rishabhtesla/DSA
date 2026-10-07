package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * A peak element is an element that is strictly greater than its neighbors [00:00:15]. 
 * Given a 0-indexed integer array 'nums', find a peak element, and return its index [00:00:42]. 
 * If the array contains multiple peaks, return the index of any of the peaks [00:00:47].
 * You may imagine that nums[-1] = nums[n] = -Infinity [00:01:10].
 * You must write an algorithm that runs in O(log n) time complexity [00:02:20].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [1, 2, 3, 1] [00:01:22]
 * - Process: Element 3 at index 2 is strictly greater than both its left neighbor 2 and right neighbor 1 [00:02:00].
 * - Result: 2
 * 
 * Example 2: nums = [1, 2, 1, 3, 5, 6, 4] (Simulated inside the video explanation [00:03:31])
 * - Process:
 *   - Edge cases verify endpoints first. Index 0 and index 6 are not lone peak anchors [00:20:35].
 *   - 'start' initializes to 1, 'end' to 5 (length-2 bounds check to bypass boundary checks inside the loop) [00:20:53].
 *   - Mid points to index 3 (value 3) [00:21:28]. Left neighbor is 1, right neighbor is 5. 
 *     Since `nums[mid] < nums[mid + 1]` (3 < 5), a peak is guaranteed to exist on the right slope [00:21:58]. 
 *     Shift right: `start = mid + 1 = 4` [00:22:09].
 *   - New mid points to index 4 (value 5). `nums[4] < nums[5]` (5 < 6). Shift right: `start = mid + 1 = 5` [00:23:03].
 *   - Pointers converge at index 5 (value 6). Value 6 > 5 (left) and 6 > 4 (right). Peak located [00:23:19]!
 * - Result: 5 (Index 1 matching value 2 is also a valid alternative peak) [00:23:32].
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Bounded Boundary Slope Binary Search):
 * • Index Initialization: Handles single elements or standalone endpoint peaks via upfront edge statements [00:12:34]. 
 *   Internal binary search 'start' begins at 1, 'end' begins at nums.length - 2 [00:15:21].
 * • Condition Boundaries: The binary loop remains active while `start <= end` [00:16:12].
 * • Operational Steps:
 *   1. Compute current center point: `mid = start + (end - start) / 2` [00:16:19].
 *   2. If `nums[mid] > nums[mid - 1]` AND `nums[mid] > nums[mid + 1]`, return `mid` as the confirmed peak [00:16:28].
 *   3. If `nums[mid] < nums[mid + 1]`, the right neighbor ascends. Greedily climb the right slope via `start = mid + 1` [00:17:16].
 *   4. Otherwise, climb the left slope by adjusting the right bound via `end = mid - 1` [00:17:47].
 * • Time Complexity: O(log n) - Continuously eliminates half the lookup region by identifying local slope paths [00:02:20].
 * • Space Complexity: O(1) - Primitive state monitors evaluated completely in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   An unsorted array can still be traversed logarithmically using binary search indicators [00:03:16]. 
 *   By comparing a middle element with its immediate right neighbor, we identify the direction of an upward slope. 
 *   Since the array endpoints terminate at negative infinity, following an upward slope guarantees encountering a peak [00:11:39].
 * 
 * ---
 * VISUAL DRY RUN (nums = [1, 2, 1, 3, 5, 6, 4]):
 * Initial: start = 1, end = 5, length = 7
 * Iteration 1: mid = 3 (value 3). nums[3]=3 > nums[2]=1 (true), but nums[3]=3 > nums[4]=5 is false. 
 *              Slope analysis: nums[3](3) < nums[4](5) -> Right neighbor climbs. Shift start = 3 + 1 = 4 [00:22:09].
 * Iteration 2: start = 4, end = 5 -> mid = 4 (value 5). nums[4]=5 > nums[3]=3 (true), but nums[4]=5 > nums[5]=6 is false.
 *              Slope analysis: nums[4](5) < nums[5](6) -> Right neighbor climbs. Shift start = 4 + 1 = 5 [00:23:03].
 * Iteration 3: start = 5, end = 5 -> mid = 5 (value 6). 
 *              Condition check: `nums[5](6) > nums[4](5)` AND `nums[5](6) > nums[6](4)`. Both true! Return index 5 [00:23:19].
 */
public class FindPeakElement {

    // APPROACH 1: Bounded Boundary Slope Binary Search (Anchor Strategy)
    public static int findPeakElementOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        if (nums.length == 1) return 0; // Solo element boundary rule [00:12:43]
        
        // Upfront processing for extreme perimeter indices to simplify internal loop checks [00:12:59]
        if (nums[0] > nums[1]) return 0;
        if (nums[nums.length - 1] > nums[nums.length - 2]) return nums.length - 1;
        
        // Contract bounds inward since indices 0 and length-1 are fully addressed above [00:15:21]
        int start = 1;
        int end = nums.length - 2;
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            
            // Check if the current element is strictly greater than both neighbors [00:16:28]
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            }
            
            // If the right neighbor is larger, an upward slope exists towards the right [00:17:16]
            if (nums[mid] < nums[mid + 1]) {
                start = mid + 1;
            } 
            // Otherwise, an upward slope must exist towards the left
            else {
                end = mid - 1;
            }
        }
        return -1;
    }

    // APPROACH 2: Unbounded Convergence Search Variant
    // A concise binary search layout that maintains an active scanning range until the start and end pointers 
    // converge directly on a peak element index.
    public static int findPeakElementConvergence(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start < end) {
            int mid = start + (end - start) / 2;
            
            // If descending slope is encountered, the peak resides at mid or to its left
            if (nums[mid] > nums[mid + 1]) {
                end = mid;
            } 
            // If ascending slope is encountered, the peak must reside strictly to the right
            else {
                start = mid + 1;
            }
        }
        return start;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Resolving peak elements via full linear stream scans transforms the algorithm 
    // from an optimized logarithmic O(log n) performance down to a strict sequential linear O(n) space/time layer.
    public static int findPeakElementStream(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        if (nums.length == 1) return 0;

        return IntStream.range(0, nums.length)
                .filter(i -> {
                    boolean leftOk = (i == 0) || (nums[i] > nums[i - 1]);
                    boolean rightOk = (i == nums.length - 1) || (nums[i] > nums[i + 1]);
                    return leftOk && rightOk;
                })
                .findFirst()
                .orElse(-1);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Single Peak) ---
        int[] test1 = {1, 2, 3, 1};
        int res1_1 = findPeakElementOptimal(test1);
        int res1_2 = findPeakElementConvergence(test1);
        int res1_3 = findPeakElementStream(test1);

        System.out.println("Test Case 1: [1, 2, 3, 1]");
        System.out.println("Approach 1 (Slope BS)       Result Index: " + res1_1);
        System.out.println("Approach 2 (Convergence BS) Result Index: " + res1_2);
        System.out.println("Approach 3 (Stream Scan)    Result Index: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 2 && res1_2 == 2 && res1_3 == 2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation - Multiple Peaks) ---
        int[] test2 = {1, 2, 1, 3, 5, 6, 4};
        int res2_1 = findPeakElementOptimal(test2);
        int res2_2 = findPeakElementConvergence(test2);
        int res2_3 = findPeakElementStream(test2);

        System.out.println("Test Case 2: [1, 2, 1, 3, 5, 6, 4]");
        System.out.println("Approach 1 (Slope BS)       Result Index: " + res2_1);
        System.out.println("Approach 2 (Convergence BS) Result Index: " + res2_2);
        System.out.println("Approach 3 (Stream Scan)    Result Index: " + res2_3);
        // Both indices 1 (value 2) and 5 (value 6) qualify as valid peak positions.
        boolean isValidResult = (res2_1 == 1 || res2_1 == 5) && (res2_2 == 1 || res2_2 == 5) && (res2_3 == 1 || res2_3 == 5);
        System.out.println("Verification: " + (isValidResult ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}