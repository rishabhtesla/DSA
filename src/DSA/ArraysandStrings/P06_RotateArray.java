package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [06 / 24] - ROTATE ARRAY BY K STEPS (LeetCode 189)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer array nums, rotate the array to the right by k steps in-place.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Normalize k: k = k % n (rotating by n steps yields the original array).
 *   - Reversal Algorithm:
 *     Rotating right by k means the last k elements move to the front, and the
 *     first (n - k) elements shift to the back.
 *     Example: [1, 2, 3, 4, 5, 6, 7], k = 3
 *     1. Reverse entire array:             [7, 6, 5, 4, 3, 2, 1]
 *     2. Reverse first k elements (0..k-1): [5, 6, 7, 4, 3, 2, 1]
 *     3. Reverse remaining (k..n-1):        [5, 6, 7, 1, 2, 3, 4]  -> Result!
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Reversing takes linear time total across 3 passes.
 *   - Space: O(1) - In-place element swaps.
 *
 *
 * EXAMPLE:
 *   Input: nums = [1, 2, 3, 4, 5, 6, 7], k = 3
 *   Output: [5, 6, 7, 1, 2, 3, 4]
 *
 * VISUAL DRY RUN:
 *   - Reverse the whole array:
 *     [1,2,3,4,5,6,7] -> [7,6,5,4,3,2,1]
 *   - Reverse the first k=3 values:
 *     [7,6,5,4,3,2,1] -> [5,6,7,4,3,2,1]
 *   - Reverse the remaining values from index 3:
 *     [5,6,7,4,3,2,1] -> [5,6,7,1,2,3,4]
 *   The last three original values are now at the front, which is a right
 *   rotation by three positions.
 *
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P06_RotateArray {

    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;

        // Step 1: Reverse the whole array
        reverse(nums, 0, n - 1);
        // Step 2: Reverse first k elements
        reverse(nums, 0, k - 1);
        // Step 3: Reverse remaining n - k elements
        reverse(nums, k, n - 1);
    }

    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        rotate(nums, 3);
        System.out.println("P06 Output: " + Arrays.toString(nums));
        // Expected: [5, 6, 7, 1, 2, 3, 4]
    }
}