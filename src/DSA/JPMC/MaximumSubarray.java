package DSA.JPMC;

/**
 * PROBLEM: Maximum Subarray
 * Given an integer array nums, find the contiguous subarray (containing at least one number)
 * which has the largest sum and return its sum.
 * 
 * EXAMPLE:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: The subarray [4,-1,2,1] has the largest sum = 6.
 * 
 * LOGIC (Kadane's Algorithm):
 * Keep a running sum of the current subarray.
 * If the current running sum drops below 0, it is actively dragging down any future numbers, 
 * so we discard it and reset the running sum to the current number.
 * At every step, we update our global maximum sum.
 * 
 * DRY RUN: nums = [-2, 1, -3, 4, -1, 2]
 * - i=0 (-2): currentSum = max(-2, -2) = -2. globalMax = -2
 * - i=1 (1):  currentSum = max(1, -2+1) = 1. globalMax = max(-2, 1) = 1
 * - i=2 (-3): currentSum = max(-3, 1-3) = -2. globalMax = max(1, -2) = 1
 * - i=3 (4):  currentSum = max(4, -2+4) = 4. globalMax = max(1, 4) = 4
 * - i=4 (-1): currentSum = max(-1, 4-1) = 3. globalMax = max(4, 3) = 4
 * - i=5 (2):  currentSum = max(2, 3+2) = 5. globalMax = max(4, 5) = 5
 * Result = 5.
 * 
 * COMPLEXITY: Time: O(N), Space: O(1)
 */
public class MaximumSubarray {
    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int currentSum = nums[0];
        int globalMax = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            // Should we start a new subarray at current element, 
            // or add the current element to the existing subarray?
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            
            // Update the maximum sum seen so far
            globalMax = Math.max(globalMax, currentSum);
        }
        
        return globalMax;
    }

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Maximum Subarray Sum: " + maxSubArray(nums)); // Output: 6
    }
}