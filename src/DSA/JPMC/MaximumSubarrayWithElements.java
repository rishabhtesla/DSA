package DSA.JPMC;

import java.util.Arrays;

/**
 * PROBLEM: Maximum Subarray (Returning both Sum and Array)
 * Given an integer array nums, find the contiguous subarray which has the 
 * largest sum, and return BOTH its sum and the actual subarray.
 * 
 * EXAMPLE:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: Sum = 6, Subarray = [4, -1, 2, 1]
 * 
 * LOGIC (Modified Kadane's Algorithm):
 * 1. Track indices: `tempStart` (start of the currently evaluated subarray), 
 *    `bestStart` (start of the historical best subarray), and `bestEnd` (end of historical best).
 * 2. If adding the current number to the `currentSum` results in a value LESS than the current 
 *    number itself, it means the old `currentSum` was negative. We discard the old subarray, 
 *    start fresh at the current index, and update `tempStart`.
 * 3. Whenever `currentSum` exceeds `globalMax`, we update `globalMax` AND lock in our 
 *    `bestStart` (which takes the value of `tempStart`) and `bestEnd` (current index `i`).
 * 4. Finally, use Arrays.copyOfRange() to extract the subarray using bestStart and bestEnd.
 * 
 * DRY RUN: nums = [-2, 1, -3, 4]
 * Init: currentSum=-2, globalMax=-2, tempStart=0, bestStart=0, bestEnd=0
 * - i=1 (val 1): 1 > -2+1. (Old sum dragged us down). 
 *                currentSum = 1, tempStart = 1. 
 *                currentSum(1) > globalMax(-2) -> globalMax=1, bestStart=1, bestEnd=1
 * - i=2 (val-3): -3 < 1-3. (Old sum is helping). 
 *                currentSum = -2. 
 *                No new globalMax.
 * - i=3 (val 4): 4 > -2+4. (Old sum dragged us down). 
 *                currentSum = 4, tempStart = 3. 
 *                currentSum(4) > globalMax(1) -> globalMax=4, bestStart=3, bestEnd=3
 * Result: Max=4, Array from index 3 to 3 -> [4]
 * 
 * COMPLEXITY: Time: O(N), Space: O(N) (To store the returned subarray slice)
 */
/**
 * Learning supplement: enumerate boundaries for O(n^2), or track Kadane
 * boundaries in O(n) time and O(1) auxiliary space. Examples:
 * {@code [4,-1,2,1] -> sum 6, values [4,-1,2,1]} and
 * {@code [-2,-1] -> sum -1, values [-1]}. Invariant: current start/sum is
 * the best suffix and best start/end is the best global range. Ask how ties
 * should be resolved.
 */
public class MaximumSubarrayWithElements {
    
    // Custom wrapper class to return multiple values cleanly
    public static class SubarrayResult {
        public int maxSum;
        public int[] subarray;

        public SubarrayResult(int maxSum, int[] subarray) {
            this.maxSum = maxSum;
            this.subarray = subarray;
        }
    }

    public static SubarrayResult maxSubArrayAndElements(int[] nums) {
        // Edge case handling
        if (nums == null || nums.length == 0) {
            return new SubarrayResult(0, new int[0]);
        }
        
        int currentSum = nums[0];
        int globalMax = nums[0];
        
        // Pointers for tracking the subarray boundaries
        int tempStart = 0; // Where the current positive streak started
        int bestStart = 0; // Where the global best streak started
        int bestEnd = 0;   // Where the global best streak ended
        
        for (int i = 1; i < nums.length; i++) {
            
            // If the current element ALONE is strictly greater than the accumulated sum + current element,
            // it means the accumulated sum was negative. Throw it away and start a new streak.
            if (nums[i] > currentSum + nums[i]) {
                currentSum = nums[i];
                tempStart = i; // Reset the starting pointer for the new streak
            } else {
                currentSum += nums[i]; // Extend the current streak
            }
            
            // If this current streak is the best we've seen so far, lock in the boundaries
            if (currentSum > globalMax) {
                globalMax = currentSum;
                bestStart = tempStart;
                bestEnd = i;
            }
        }
        
        // Extract the actual elements from the original array
        // Note: Arrays.copyOfRange's third parameter is EXCLUSIVE, so we do bestEnd + 1
        int[] optimalSubarray = Arrays.copyOfRange(nums, bestStart, bestEnd + 1);
        
        return new SubarrayResult(globalMax, optimalSubarray);
    }

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        
        SubarrayResult result = maxSubArrayAndElements(nums);
        
        System.out.println("Maximum Subarray Sum: " + result.maxSum);
        System.out.println("Actual Subarray: " + Arrays.toString(result.subarray)); 
        // Output: Maximum Subarray Sum: 6
        // Output: Actual Subarray: [4, -1, 2, 1]
    }
}