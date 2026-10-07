package DSA.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * [34 / 34] - 3SUM (LeetCode 15)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
 *   such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 *   Notice that the solution set must not contain duplicate triplets.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Sorting + Two-Pointer Convergence:
 *     1. Sort `nums` ascending: `Arrays.sort(nums)`.
 *     2. Fix the first element `nums[i]` in a loop from `0` to `n - 3`.
 *        - If `nums[i] > 0`, break early (three positive numbers can never sum to 0).
 *        - If `i > 0 && nums[i] == nums[i - 1]`, skip to avoid duplicate triplets.
 *     3. Run Two Sum II on the remainder: `left = i + 1`, `right = n - 1`, target = `-nums[i]`.
 *        - If `nums[left] + nums[right] == -nums[i]`:
 *          Add triplet `[nums[i], nums[left], nums[right]]`.
 *          Advance `left` and `right`, skipping any identical numbers to prevent duplicate answers:
 *          `while (left < right && nums[left] == nums[left + 1]) left++;`
 *          `while (left < right && nums[right] == nums[right - 1]) right--;`
 *        - If sum < target: `left++`.
 *        - If sum > target: `right--`.
 *
 * COMPLEXITY:
 *   - Time:  O(n^2) - Sorting takes O(n log n). The nested two-pointer loop takes O(n^2).
 *   - Space: O(log n) to O(n) - Sorting recursion stack.
 */
public class P34_ThreeSum {

    public static List<List<Arrays>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) return (List) result;

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            // Prune: if first element is positive, sum can never be 0
            if (nums[i] > 0) break;

            // Skip duplicate values for the first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicate values for second and third elements
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return (List) result;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println("P34 Output: " + threeSum(nums));
        // Expected: [[-1, -1, 2], [-1, 0, 1]]
    }
}