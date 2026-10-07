package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given an integer array 'nums', find the subarray with the largest sum and return its sum [00:00:16].
 * A subarray is a contiguous part of an array [00:00:34].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4] [00:01:06]
 * - Process: The contiguous subarray [4, -1, 2, 1] has the largest sum = 6 [00:01:22].
 * - Result: 6
 * 
 * Example 2: nums = [7, -3, 8, 6, -10, -1, 4, 7, 2] (Simulated inside the video explanation [00:10:37])
 * - Process:
 *   - 'currentSum' and 'maxSum' both initialize to nums[0] = 7 [00:10:58].
 *   - index 1 (-3): 7 + (-3) = 4 > -3. currentSum updates to 4 [00:11:46].
 *   - index 2 (8): 4 + 8 = 12 > 8. currentSum updates to 12. maxSum locks in 12 [00:12:10].
 *   - index 3 (6): 12 + 6 = 18 > 6. currentSum updates to 18. maxSum locks in 18 [00:12:34].
 *   - Continuing this sequential accumulation pattern across trailing positive values brings the complete net 
 *     sub-array total to 20 at the final index [00:14:39].
 * - Result: 20
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Kadane's Algorithm):
 * • Index Initialization: Scanning variable 'i' iterates from 1 to nums.length - 1 [00:15:09]. 
 *   Variables 'currentSum' and 'maxSum' initialize to nums[0] [00:14:58].
 * • Condition Boundaries: If `currentSum + nums[i] > nums[i]`, append the element to the existing running subarray. 
 *   Otherwise, discard historical metrics and start a fresh subarray at index 'i' [00:15:30].
 * • Operational Steps:
 *   1. Initialize trackers `currentSum = nums[0]` and `maxSum = nums[0]` [00:14:58].
 *   2. Iterate from index 1 to the end [00:15:09].
 *   3. Update running accumulation state greedily: `currentSum = Math.max(nums[i], currentSum + nums[i])` [00:15:30].
 *   4. Sync global benchmarks at each progression: `maxSum = Math.max(maxSum, currentSum)` [00:16:19].
 * • Time Complexity: O(n) - Single sequential pass through the array dataset [00:04:19].
 * • Space Complexity: O(1) - Constant tracking registers updated completely in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   Kadane's algorithm leverages dynamic programming concepts to evaluate sequential array extensions [00:04:34]. At any given index, 
 *   the local choice is to either expand the existing running sub-array chain or drop it to launch a brand new sub-array segment. 
 *   If the historical sum drops below zero, it acts as a structural drag, making it mathematically ideal to reset the baseline [00:08:15].
 * 
 * ---
 * VISUAL DRY RUN (nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]):
 * Initial: currentSum = -2, maxSum = -2 [00:17:28]
 * i = 1: nums[1]=1.  (-2 + 1 = -1) < 1 -> Reset! currentSum = 1.  maxSum = max(-2, 1) = 1 [00:17:57].
 * i = 2: nums[2]=-3. (1 + -3 = -2) > -3 -> Append! currentSum = -2. maxSum = max(1, -2) = 1 [00:18:23].
 * i = 3: nums[3]=4.  (-2 + 4 = 2) < 4 -> Reset! currentSum = 4.  maxSum = max(1, 4) = 4 [00:19:04].
 * i = 4: nums[4]=-1. (4 + -1 = 3) > -1 -> Append! currentSum = 3.  maxSum = max(4, 3) = 4 [00:19:28].
 * i = 5: nums[5]=2.  (3 + 2 = 5) > 2  -> Append! currentSum = 5.  maxSum = max(4, 5) = 5 [00:19:47].
 * i = 6: nums[6]=1.  (5 + 1 = 6) > 1  -> Append! currentSum = 6.  maxSum = max(5, 6) = 6 [00:20:10].
 * i = 7: nums[7]=-5. (6 + -5 = 1) > -5 -> Append! currentSum = 1.  maxSum = max(6, 1) = 6 [00:20:28].
 * i = 8: nums[8]=4.  (1 + 4 = 5) > 4  -> Append! currentSum = 5.  maxSum = max(6, 5) = 6 [00:20:55].
 * Final returned maxSum = 6.
 */
public class MaximumSubarray {

    // APPROACH 1: Kadane's Algorithm (Anchor Strategy)
    public static int maxSubArrayKadane(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int currentSum = nums[0];
        int maxSum = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            // Greedily choose to either extend the current chain or start anew from this index
            if (currentSum + nums[i] > nums[i]) {
                currentSum += nums[i];
            } else {
                currentSum = nums[i];
            }
            
            // Track the absolute maximum found across all subarray evaluation scopes
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }

    // APPROACH 2: Divide and Conquer Paradigm Variant
    // Recursively breaks the array down to solve the left, right, and cross-boundary subregions.
    // Demonstrates an alternative O(n log n) structural sorting design pattern.
    public static int maxSubArrayDivideAndConquer(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return findMaxSubArray(nums, 0, nums.length - 1);
    }

    private static int findMaxSubArray(int[] nums, int low, int high) {
        if (low == high) return nums[low];
        
        int mid = low + (high - low) / 2;
        
        int leftMax = findMaxSubArray(nums, low, mid);
        int rightMax = findMaxSubArray(nums, mid + 1, high);
        int crossMax = findCrossMaximum(nums, low, mid, high);
        
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    private static int findCrossMaximum(int[] nums, int low, int mid, int high) {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= low; i--) {
            sum += nums[i];
            if (sum > leftSum) leftSum = sum;
        }
        
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= high; i++) {
            sum += nums[i];
            if (sum > rightSum) rightSum = sum;
        }
        
        return leftSum + rightSum;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Implementing reduction layers over state mutations introduces massive primitive boxing 
    // overhead, raising runtime complexity markers while transitioning space allocations to linear O(n) configurations.
    public static int maxSubArrayStream(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // Index 0: global maximum subarray sum, Index 1: running current subarray sum
        int[] resultState = IntStream.range(1, nums.length)
                .boxed()
                .reduce(
                    new int[]{nums[0], nums[0]},
                    (state, idx) -> {
                        state[1] = Math.max(nums[idx], state[1] + nums[idx]);
                        state[0] = Math.max(state[0], state[1]);
                        return state;
                    },
                    (s1, s2) -> s1
                );

        return resultState[0];
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Mixed Array) ---
        int[] test1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int res1_1 = maxSubArrayKadane(test1);
        int res1_2 = maxSubArrayDivideAndConquer(test1);
        int res1_3 = maxSubArrayStream(test1);

        System.out.println("Test Case 1: [-2, 1, -3, 4, -1, 2, 1, -5, 4]");
        System.out.println("Approach 1 (Kadane)      Result: " + res1_1);
        System.out.println("Approach 2 (Div & Conq)  Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)  Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 6 && res1_2 == 6 && res1_3 == 6 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {7, -3, 8, 6, -10, -1, 4, 7, 2};
        int res2_1 = maxSubArrayKadane(test2);
        int res2_2 = maxSubArrayDivideAndConquer(test2);
        int res2_3 = maxSubArrayStream(test2);

        System.out.println("Test Case 2: [7, -3, 8, 6, -10, -1, 4, 7, 2]");
        System.out.println("Approach 1 (Kadane)      Result: " + res2_1);
        System.out.println("Approach 2 (Div & Conq)  Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)  Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 20 && res2_2 == 20 && res2_3 == 20 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}