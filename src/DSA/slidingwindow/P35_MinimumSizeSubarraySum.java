package DSA.slidingwindow;

/**
 * ============================================================================
 * [35 / 38] - MINIMUM SIZE SUBARRAY SUM (LeetCode 209)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array of positive integers nums and a positive integer target, return
 *   the minimal length of a subarray whose sum is greater than or equal to target.
 *   If there is no such subarray, return 0 instead.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Variable-Size Sliding Window (Shrinkable Window):
 *     Because all numbers in nums are POSITIVE, adding elements monotonically increases
 *     the window sum, and removing elements from the left monotonically decreases it.
 *   - Algorithm:
 *     1. Expand window to the right by adding nums[right] to running sum.
 *     2. As long as `sum >= target`, update `minLen = Math.min(minLen, right - left + 1)`.
 *        Then shrink the window from the left (`sum -= nums[left++]`) to find the
 *        tightest valid subarray.
 *     3. Each element is added once and removed at most once -> strict O(n) total operations.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Both left and right pointers traverse the array at most once.
 *   - Space: O(1) - Constant auxiliary space.
 */
public class P35_MinimumSizeSubarraySum {

    public static int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];

            // Contract window from the left while current sum meets or exceeds target
            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                currentSum -= nums[left];
                left++;
            }
        }

        return (minLength == Integer.MAX_VALUE) ? 0 : minLength;
    }

    public static void main(String[] args) {
        int target1 = 7;
        int[] nums1 = {2, 3, 1, 2, 4, 3};
        System.out.println("P35 Output (Test 1): " + minSubArrayLen(target1, nums1)); // Expected: 2 ([4, 3])

        int target2 = 4;
        int[] nums2 = {1, 4, 4};
        System.out.println("P35 Output (Test 2): " + minSubArrayLen(target2, nums2)); // Expected: 1 ([4])

        int target3 = 11;
        int[] nums3 = {1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println("P35 Output (Test 3): " + minSubArrayLen(target3, nums3)); // Expected: 0
    }
}