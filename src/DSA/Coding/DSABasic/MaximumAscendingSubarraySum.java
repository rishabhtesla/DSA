package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given an array of positive integers 'nums', return the maximum possible sum of an ascending sub-array.
 * A sub-array is defined as a contiguous sequence of elements in an array [00:00:25].
 * A sub-array [nums[l], nums[l+1], ..., nums[r-1], nums[r]] is ascending if for all i where l <= i < r, 
 * nums[i] < nums[i+1]. Note that a sub-array of size 1 is considered ascending [00:03:44].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [10, 20, 30, 5, 10, 50] [00:00:46]
 * - Process: There are two main ascending sub-arrays:
 *   - [10, 20, 30] -> Sum = 10 + 20 + 30 = 60.
 *   - [5, 10, 50]  -> Sum = 5 + 10 + 50 = 65.
 * - Comparing both sums, 65 is larger.
 * - Result: 65
 * 
 * Example 2: nums = [10, 20, 30, 10, 50, 80] (Simulated inside the video explanation [00:09:07])
 * - Process:
 *   - The first sub-array is [10, 20, 30] -> Sum = 60 [00:10:18].
 *   - At index 3, the element breaks the ascending order (10 is not > 30), triggering an evaluation update. 
 *     'maxSum' locks in 60 [00:10:29].
 *   - The next ascending sub-array starts from index 3: [10, 50, 80] -> Sum = 10 + 50 + 80 = 140 [00:11:20].
 *   - After processing terminates, a final edge comparison sets the maximum value to 140 [00:11:38].
 * - Result: 140
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Iterative Accumulation & Reset):
 * • Index Initialization: Single pointer loop 'i' steps from index 1 up to nums.length - 1 [00:06:59].
 * • Condition Boundaries: If `nums[i] > nums[i - 1]`, add to the current running sum sequence [00:07:16]. 
 *   Otherwise, update the global maximum accumulator state and reset the sequence baseline [00:07:47].
 * • Operational Steps:
 *   1. Initialize `currentSum = nums[0]` and `maxSum = nums[0]` [00:06:44].
 *   2. Iterate from `i = 1` through the array [00:06:59].
 *   3. If descending/equal sequence break: compute `maxSum = max(maxSum, currentSum)`, then overwrite `currentSum = nums[i]` [00:07:52].
 *   4. Post-loop evaluation check: `maxSum = max(maxSum, currentSum)` to handle trailing sub-array structures [00:08:24].
 * • Time Complexity: O(n) - Single scan pass across the array elements.
 * • Space Complexity: O(1) - Evaluates operations entirely in-place with minimal extra overhead.
 * • LOGIC BEHIND THIS APPROACH:
 *   Because all numbers are positive, extending a valid ascending sequence directly adds net positive value to our sum [00:01:18]. 
 *   When the ascending rule breaks, the current sub-array can no longer be legally extended. We record its sum, and immediately 
 *   use the current value as the starting foundation for a fresh tracking candidate [00:05:23].
 * 
 * ---
 * VISUAL DRY RUN (nums = [10, 20, 30, 10, 50, 80]):
 * Initial: currentSum = 10, maxSum = 10 [00:09:36]
 * i = 1: nums[1]=20 > nums[0]=10 -> currentSum = 10 + 20 = 30 [00:10:06].
 * i = 2: nums[2]=30 > nums[1]=20 -> currentSum = 30 + 30 = 60 [00:10:18].
 * i = 3: nums[3]=10 <= nums[2]=30 -> Break! maxSum = max(10, 60) = 60. Reset currentSum = 10 [00:10:29].
 * i = 4: nums[4]=50 > nums[3]=10 -> currentSum = 10 + 50 = 60 [00:11:08].
 * i = 5: nums[5]=80 > nums[4]=50 -> currentSum = 60 + 80 = 140 [00:11:20].
 * Loop End: Post-evaluation -> maxSum = max(60, 140) = 140 [00:11:38]. Final return = 140.
 */
public class MaximumAscendingSubarraySum {

    // APPROACH 1: Iterative Accumulation & Reset (Anchor Strategy)
    public static int maxAscendingSumOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxSum = nums[0];
        int currentSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                currentSum += nums[i];
            } else {
                maxSum = Math.max(maxSum, currentSum);
                currentSum = nums[i]; // Reset sequence tracker using current break component boundary
            }
        }
        
        // Final trailing check to capture sequences ending at the last index boundary
        return Math.max(maxSum, currentSum);
    }

    // APPROACH 2: Flagged Space In-Place Two-Pointer Window Strategy
    // Uses structural limits mapping window ranges via start and end indicators instead of checking immediate neighbors directly.
    public static int maxAscendingSumTwoPointer(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int n = nums.length;
        int maxSum = 0;
        int start = 0;
        
        while (start < n) {
            int runningSum = nums[start];
            int end = start + 1;
            
            while (end < n && nums[end] > nums[end - 1]) {
                runningSum += nums[end];
                end++;
            }
            
            maxSum = Math.max(maxSum, runningSum);
            start = end; // Forward pointer jumps over the fully scanned section
        }
        
        return maxSum;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Relies on stateful reduction logic wrapping primitive parameters in custom object state boxes. 
    // Triggers severe resource overhead from tracking transient reference instances instead of updating array elements in-place.
    public static int maxAscendingSumStream(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // index 0: global max sum, index 1: rolling current sequence sum
        int[] resultState = IntStream.range(1, nums.length)
                .boxed()
                .reduce(
                    new int[]{nums[0], nums[0]},
                    (state, idx) -> {
                        if (nums[idx] > nums[idx - 1]) {
                            state[1] += nums[idx];
                        } else {
                            state[0] = Math.max(state[0], state[1]);
                            state[1] = nums[idx];
                        }
                        return state;
                    },
                    (s1, s2) -> s1
                );

        return Math.max(resultState[0], resultState[1]);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Mixed Arrays) ---
        int[] test1 = {10, 20, 30, 5, 10, 50};
        int res1_1 = maxAscendingSumOptimal(test1);
        int res1_2 = maxAscendingSumTwoPointer(test1);
        int res1_3 = maxAscendingSumStream(test1);

        System.out.println("Test Case 1: [10, 20, 30, 5, 10, 50]");
        System.out.println("Approach 1 (Optimal Reset) Result: " + res1_1);
        System.out.println("Approach 2 (Two-Pointer Window)   Result: " + res1_2);
        System.out.println("Approach 3 (Stream API Pipeline)  Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 65 && res1_2 == 65 && res1_3 == 65 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {10, 20, 30, 10, 50, 80};
        int res2_1 = maxAscendingSumOptimal(test2);
        int res2_2 = maxAscendingSumTwoPointer(test2);
        int res2_3 = maxAscendingSumStream(test2);

        System.out.println("Test Case 2: [10, 20, 30, 10, 50, 80]");
        System.out.println("Approach 1 (Optimal Reset) Result: " + res2_1);
        System.out.println("Approach 2 (Two-Pointer Window)   Result: " + res2_2);
        System.out.println("Approach 3 (Stream API Pipeline)  Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 140 && res2_2 == 140 && res2_3 == 140 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Strictly Decreasing Constraints) ---
        int[] test3 = {12, 11, 10, 9, 8};
        int res3_1 = maxAscendingSumOptimal(test3);
        int res3_2 = maxAscendingSumTwoPointer(test3);
        int res3_3 = maxAscendingSumStream(test3);

        System.out.println("Test Case 3: [12, 11, 10, 9, 8]");
        System.out.println("Approach 1 (Optimal Reset) Result: " + res3_1);
        System.out.println("Approach 2 (Two-Pointer Window)   Result: " + res3_2);
        System.out.println("Approach 3 (Stream API Pipeline)  Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 == 12 && res3_2 == 12 && res3_3 == 12 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}