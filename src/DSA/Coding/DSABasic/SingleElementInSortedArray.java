package DSA.Coding.DSABasic;

import java.util.Arrays;

/**
 * PROBLEM STATEMENT:
 * You are given a sorted array consisting of only integers where every element appears exactly twice, 
 * except for one element which appears exactly once [00:00:41]. Return the single element that appears only once [00:00:55].
 * Your solution must run in O(log n) time complexity and O(1) space complexity [00:01:59].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [1, 1, 2, 3, 3, 4, 4, 8, 8] [00:01:05]
 * - Process: Pairs are matched sequentially. 2 has no matching twin boundary.
 * - Result: 2
 * 
 * Example 2: nums = [10, 10, 20, 30, 30, 40, 40] (Simulated inside the video explanation [00:19:58])
 * - Process:
 *   - Upfront boundary evaluations scan array index perimeters to avoid out-of-bounds metrics inside the loop [00:20:17].
 *   - 'start' initializes to 0, 'end' to 6. Mid points to index 3 (value 30) [00:20:37].
 *   - Mid index 3 is an odd value. In a normal duplicated prefix, an odd index should match its left neighbor 
 *     (`nums[mid-1] == nums[mid]`) [00:20:43]. Here, `nums[2]` (20) != `nums[3]` (30). 
 *     This reveals that the structural disruption occurred to the left. 
 *     Contract right bound: `end = mid - 1 = 2` [00:21:18].
 *   - New mid points to index 1 (value 10). Index 1 is odd. Left neighbor `nums[0]` is 10. 
 *     `nums[0] == nums[1]` is true. Prefix is valid [00:21:47]. Shift right: `start = mid + 1 = 2` [00:22:08].
 *   - New mid points to index 2 (value 20). Check isolation conditions: `nums[2] != nums[1]` and `nums[2] != nums[3]`. 
 *     Single element identified [00:22:21]!
 * - Result: 20
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Even-Odd Index Alignment Binary Search):
 * • Index Initialization: Handle small boundary edge cases early [00:12:07]. Internal binary search 
 *   pointers 'start' begins at 0, 'end' begins at nums.length - 1 [00:13:37].
 * • Condition Boundaries: Loop remains active while `start <= end` [00:13:51].
 * • Operational Steps:
 *   1. Calculate the center pointer: `mid = start + (end - start) / 2` [00:13:58].
 *   2. Check isolation status: if `nums[mid] != nums[mid-1]` and `nums[mid] != nums[mid+1]`, return `nums[mid]` [00:14:28].
 *   3. If `mid` index is odd: a correct setup requires `nums[mid] == nums[mid - 1]`. If true, move `start = mid + 1`, else `end = mid - 1` [00:15:10].
 *   4. If `mid` index is even: a correct setup requires `nums[mid] == nums[mid + 1]`. If true, move `start = mid + 1`, else `end = mid - 1` [00:18:09].
 * • Time Complexity: O(log n) - Discards half of the remaining lookup space per validation step [00:01:59].
 * • Space Complexity: O(1) - Primitive variable tracking executed completely in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   In a clean sorted array containing only double duplicates, every unique pair must begin on an even index and terminate 
 *   on an odd index [00:04:37]. The unique single element disrupts this even-odd parity chain [00:05:17]. By examining the 
 *   mid-point index along with its duplicates, we can determine if the single anomaly lies to the left or right [00:11:40].
 * 
 * ---
 * VISUAL DRY RUN (nums = [10, 10, 20, 30, 30, 40, 40]):
 * Initial: start = 0, end = 6, length = 7
 * Iteration 1: mid = 3 (value 30). Index 3 is ODD. Check left: nums[2]=20 != nums[3]=30. 
 *              Parity mismatch indicates distortion on the left. Contract right bound: end = 3 - 1 = 2 [00:21:18].
 * Iteration 2: start = 0, end = 2 -> mid = 1 (value 10). Index 1 is ODD. Check left: nums[0]=10 == nums[1]=10. 
 *              Parity matches. The unique element lies to the right. Shift left bound: start = 1 + 1 = 2 [00:22:08].
 * Iteration 3: start = 2, end = 2 -> mid = 2 (value 20). 
 *              Condition check: `nums[2](20) != nums[1](10)` AND `nums[2](20) != nums[3](30)`. Match! Return value 20 [00:22:21].
 */
public class SingleElementInSortedArray {

    // APPROACH 1: Even-Odd Index Alignment Binary Search (Anchor Strategy)
    public static int singleNonDuplicateOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        if (nums.length == 1) return nums[0]; // Solo boundary condition [00:12:07]
        
        // Upfront evaluations for external array edges to avoid boundary check crashes [00:12:27]
        if (nums[0] != nums[1]) return nums[0];
        if (nums[nums.length - 1] != nums[nums.length - 2]) return nums[nums.length - 1];
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            
            // Confirm isolation pattern match [00:14:28]
            if (nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]) {
                return nums[mid];
            }
            
            // Scenario A: Middle index represents an ODD register boundary [00:14:52]
            if (mid % 2 == 1) {
                // In a normal state, odd indices align with their left companion duplicates
                if (nums[mid] == nums[mid - 1]) {
                    start = mid + 1; // Left partition is uniform; single element lies right
                } else {
                    end = mid - 1; // Parity is broken; single element lies left
                }
            } 
            // Scenario B: Middle index represents an EVEN register boundary [00:17:28]
            else {
                // In a normal state, even indices align with their right companion duplicates
                if (nums[mid] == nums[mid + 1]) {
                    start = mid + 1; // Left partition is uniform; single element lies right
                } else {
                    end = mid - 1; // Parity is broken; single element lies left
                }
            }
        }
        return -1;
    }

    // APPROACH 2: Bitwise XOR Structural Optimization
    // Bypasses explicit conditional branches for even and odd index values. By executing a bitwise XOR 
    // on index `mid ^ 1`, even indices check `mid + 1` and odd indices check `mid - 1` automatically.
    public static int singleNonDuplicateXOR(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        
        int start = 0;
        int end = nums.length - 1;
        
        // Use strict boundary limit contraction
        while (start < end) {
            int mid = start + (end - start) / 2;
            
            // If mid is even, mid^1 is mid+1. If mid is odd, mid^1 is mid-1.
            if (nums[mid] == nums[mid ^ 1]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return nums[start];
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Relies on bitwise XOR reduction layers over an explicit stream configuration. 
    // This scales down search efficiency from logarithmic O(log n) down to a traditional sequential linear O(n).
    public static int singleNonDuplicateStream(int[] nums) {
        if (nums == null || nums.length == 0) return -1;

        return Arrays.stream(nums)
                .reduce(0, (accumulator, element) -> accumulator ^ element);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Parity Normal Shift) ---
        int[] test1 = {1, 1, 2, 3, 3, 4, 4, 8, 8};
        int res1_1 = singleNonDuplicateOptimal(test1);
        int res1_2 = singleNonDuplicateXOR(test1);
        int res1_3 = singleNonDuplicateStream(test1);

        System.out.println("Test Case 1: [1, 1, 2, 3, 3, 4, 4, 8, 8]");
        System.out.println("Approach 1 (Even-Odd BS) Result: " + res1_1);
        System.out.println("Approach 2 (Bitwise XOR) Result: " + res1_2);
        System.out.println("Approach 3 (Stream XOR)  Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 2 && res1_2 == 2 && res1_3 == 2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {10, 10, 20, 30, 30, 40, 40};
        int res2_1 = singleNonDuplicateOptimal(test2);
        int res2_2 = singleNonDuplicateXOR(test2);
        int res2_3 = singleNonDuplicateStream(test2);

        System.out.println("Test Case 2: [10, 10, 20, 30, 30, 40, 40]");
        System.out.println("Approach 1 (Even-Odd BS) Result: " + res2_1);
        System.out.println("Approach 2 (Bitwise XOR) Result: " + res2_2);
        System.out.println("Approach 3 (Stream XOR)  Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 20 && res2_2 == 20 && res2_3 == 20 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}