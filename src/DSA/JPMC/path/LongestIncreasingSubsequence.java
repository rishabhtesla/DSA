package DSA.JPMC.path; /**
 * PROBLEM: Longest Increasing Subsequence (LIS)
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 * 
 * EXAMPLE: Input: nums = [10,9,2,5,3,7,101,18] -> Output: 4 ([2,3,7,101])
 * 
 * LOGIC (DP Approach):
 * Create a DP array where dp[i] represents the length of the LIS ending at index 'i'.
 * Initialize all dp values to 1 (every number is a subsequence of length 1 by itself).
 * For every number 'i', look back at all previous numbers 'j'.
 * If nums[i] > nums[j], it means nums[i] can extend the subsequence ending at 'j'.
 * So, dp[i] = max(dp[i], dp[j] + 1).
 * The answer is the maximum value in the DP array.
 * 
 * COMPLEXITY: Time: O(N^2), Space: O(N) 
 * (Note: An O(N log N) solution exists using Binary Search, but DP is the standard expected answer).
 */
import java.util.Arrays;

/**
 * Learning supplement: enumerating subsequences is exponential; O(n^2) DP
 * stores the best sequence ending at each index (the implementation's
 * approach). Examples: {@code [10,9,2,5,3,7,101,18] -> 4} and
 * {@code [7,7,7] -> 1}. Invariant: dp[i] is the longest increasing
 * subsequence ending at i. For the main input, [2,3,7,101] reaches length 4.
 * Ask how the O(n log n) tails method changes the invariant.
 */
public class LongestIncreasingSubsequence {
    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1); // Base case: min length is 1
        
        int maxLength = 1;
        
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // If current number is greater than previous, we can extend the sequence
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]); // Keep track of global max
        }
        
        return maxLength;
    }

    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("LIS length: " + lengthOfLIS(nums)); // 4
    }
}