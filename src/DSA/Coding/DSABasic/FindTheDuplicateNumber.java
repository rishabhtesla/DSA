package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * PROBLEM STATEMENT:
 * Given an array of integers 'nums' containing 'n + 1' integers where each integer is in the range [1, n] inclusive.
 * There is only one repeated integer in 'nums', return this repeated integer [00:00:40].
 * You must solve the problem without modifying the array 'nums' and uses only constant extra space [00:06:34].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [1, 3, 4, 2, 2]
 * - Process: Elements are within the valid index boundaries. Sifting across values identifies 2 as appearing twice.
 * - Result: 2
 * 
 * Example 2: nums = [3, 1, 3, 4, 2] (Simulated inside the video explanation [00:05:43, 00:12:34])
 * - Process:
 *   - Use array values themselves as matching index locations by jumping via absolute magnitudes [00:04:34].
 *   - index 0 (val 3): Go to index 3 and negate its element: `nums[3]` (4) becomes -4 [00:05:59].
 *   - index 1 (val 1): Go to index 1 and negate its element: `nums[1]` (1) becomes -1 [00:06:06].
 *   - index 2 (val 3): Go to index 3. `nums[3]` is already negative (-4) [00:06:16].
 *   - This indicates that index 3 was visited before, mapping 3 as the duplicate number [00:06:21].
 *   - To adhere to the non-modification constraint, restore the array elements back to positive values before returning [00:06:44, 00:11:05].
 * - Result: 3 [00:14:02]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Negative Index Marking with In-Place Restoration):
 * • Index Initialization: Single pointer loop 'i' increments sequentially from 0 up to nums.length - 1 [00:07:00].
 * • Condition Boundaries: Extract the absolute value of the element to avoid out-of-bounds metrics: `element = Math.abs(nums[i])` [00:07:39].
 * • Operational Steps:
 *   1. Iterate over the array elements sequentially [00:07:00].
 *   2. For each element, fetch its absolute magnitude to determine the target tracking index [00:07:39].
 *   3. If `nums[element] > 0`, negate the value at that index to mark it as visited [00:07:55, 00:08:00].
 *   4. If `nums[element] < 0`, the duplicate number is found. Store it and break the loop [00:08:10, 00:10:59].
 *   5. Run a secondary loop to restore all negated numbers back to positive before returning the answer [00:11:05, 00:11:21].
 * • Time Complexity: O(n) - Two independent single-pass linear scans through the array dataset.
 * • Space Complexity: O(1) - Constant auxiliary tracking variables modified completely in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   Since the elements are strictly bounded within the range `[1, n]`, each value can be mapped to a valid array index [00:03:17]. 
 *   Using the array itself as a state registry by flipping elements to negative values removes the need for extra memory [00:03:35]. 
 *   The first number that attempts to map to an already negative value reveals itself as the duplicate [00:04:05, 00:06:16].
 * 
 * ---
 * VISUAL DRY RUN (nums = [3, 1, 3, 4, 2]):
 * Initial State: nums = [3, 1, 3, 4, 2], answer = 0 [00:12:43]
 * i = 0: element = abs(nums[0]) = 3. `nums[3]` = 4 > 0 -> negate: `nums[3]` becomes -4. Array: [3, 1, 3, -4, 2] [00:12:59].
 * i = 1: element = abs(nums[1]) = 1. `nums[1]` = 1 > 0 -> negate: `nums[1]` becomes -1. Array: [3, -1, 3, -4, 2] [00:13:13].
 * i = 2: element = abs(nums[2]) = 3. `nums[3]` = -4 < 0 -> Match found! answer = 3, trigger break [00:13:34].
 * Restoration Loop [00:11:05]: Re-iterate to run `nums[i] = Math.abs(nums[i])`. Array restored back to [3, 1, 3, 4, 2].
 * Final returned answer = 3 [00:14:02].
 */
public class FindTheDuplicateNumber {

    // APPROACH 1: Negative Index Marking with In-Place Restoration (Anchor Strategy)
    public static int findDuplicateMarking(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int answer = 0;
        
        // Linear tracking pass using values as internal index flags [00:07:00]
        for (int i = 0; i < nums.length; i++) {
            int element = Math.abs(nums[i]); // Fetch pure magnitude to handle negative lookups safely [00:07:39]
            
            // If the element at the target index is positive, flag it as visited [00:07:55]
            if (nums[element] > 0) {
                nums[element] = -nums[element];
            } else {
                // Parity mismatch maps an already visited location, indicating a duplicate element [00:08:10]
                answer = element;
                break; // Terminate loop mutations immediately [00:10:59]
            }
        }
        
        // Restoration loop to maintain the array's original values [00:06:44, 00:11:05]
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Math.abs(nums[i]);
        }
        
        return answer;
    }

    // APPROACH 2: Floyd's Tortoise and Hare Cycle Detection Algorithm
    // Treats the array values as pointer nodes to form a linked list structure. Since there is a duplicate number, 
    // a cycle is guaranteed to form. This approach requires zero array modifications or extra space.
    public static int findDuplicateCycleDetection(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int tortoise = nums[0];
        int hare = nums[0];
        
        // Phase 1: Locate the intersection point inside the cycle loop
        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while (tortoise != hare);
        
        // Phase 2: Find the entrance to the cycle loop (the duplicate value)
        tortoise = nums[0];
        while (tortoise != hare) {
            tortoise = nums[tortoise];
            hare = nums[hare];
        }
        
        return tortoise;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Relies on matching distinct values inside intermediate collection maps. 
    // This transitions space utilization metrics up to linear O(n) dimensions, adding object-boxing overhead.
    public static int findDuplicateStream(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> uniqueSet = new HashSet<>();
        return Arrays.stream(nums)
                .boxed()
                .filter(num -> !uniqueSet.add(num)) // Filters out the first element that fails to add (the duplicate)
                .findFirst()
                .orElse(0);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Layout) ---
        int[] test1 = {1, 3, 4, 2, 2};
        int res1_1 = findDuplicateMarking(test1.clone());
        int res1_2 = findDuplicateCycleDetection(test1);
        int res1_3 = findDuplicateStream(test1);

        System.out.println("Test Case 1: [1, 3, 4, 2, 2]");
        System.out.println("Approach 1 (Index Marking)  Result: " + res1_1);
        System.out.println("Approach 2 (Cycle Detect)   Result: " + res1_2);
        System.out.println("Approach 3 (Stream Pipeline) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 2 && res1_2 == 2 && res1_3 == 2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {3, 1, 3, 4, 2};
        int res2_1 = findDuplicateMarking(test2.clone());
        int res2_2 = findDuplicateCycleDetection(test2);
        int res2_3 = findDuplicateStream(test2);

        System.out.println("Test Case 2: [3, 1, 3, 4, 2]");
        System.out.println("Approach 1 (Index Marking)  Result: " + res2_1);
        System.out.println("Approach 2 (Cycle Detect)   Result: " + res2_2);
        System.out.println("Approach 3 (Stream Pipeline) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 3 && res2_2 == 3 && res2_3 == 3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}