package DSA.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * [34 / 34] - 3SUM (LeetCode 15)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]`
 * such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.
 * Notice that the solution set must not contain duplicate triplets.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach:
 *    - Use three nested loops to check all possible triplets, then use a HashSet to filter out duplicate configurations.
 *    - Inefficient: takes $O(n^3)$ time and leads to Time Limit Exceeded (TLE).
 *
 * 2. Optimal Sorting + Two-Pointer Convergence Approach (O(n^2) Time | O(log n) to O(n) Space):
 *    - **The Core Insight:** By sorting the array in ascending order, we can fix the first element `nums[i]`
 *      and reduce the problem into a classic **Two Sum II** search for the remaining two elements using inward-moving pointers.
 *    - **Step 1:** Sort `nums` ascending (`Arrays.sort(nums)`).
 *    - **Step 2:** Iterate through the array with index `i` up to `n - 3`:
 *      - **Pruning Check:** If `nums[i] > 0`, we can break early because three positive numbers can never sum to zero.
 *      - **Duplicate Skip:** If `i > 0 && nums[i] == nums[i - 1]`, skip the current iteration to avoid duplicate triplet sets.
 *    - **Step 3:** Run Two Sum II on the remaining subarray with `left = i + 1` and `right = n - 1` targeting `-nums[i]`.
 *      - If sum equals zero, record the triplet and advance both `left` and `right` while skipping duplicate values.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n^2) - Sorting takes $O(n \log n)$, and the nested two-pointer scan takes $O(n^2)$ total work.
 * - Space: O(\log n) to O(n) - Auxiliary space required for the sorting recursion stack.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `nums = [-1, 0, 1, 2, -1, -4]`
 * - Expected Triplets: `[[-1, -1, 2], [-1, 0, 1]]`
 *
 * VISUAL DRY RUN (for `[-1, 0, 1, 2, -1, -4]`):
 *   - Sorted array: `[-4, -1, -1, 0, 1, 2]`.
 *   - Fix `-4`: remaining sum targets cannot be met.
 *   - Fix first `-1` (at index 1): `left = 2` (`-1`), `right = 5` (`2`) -> sum = 0. Records `[-1, -1, 2]`.
 *     Advances pointers past duplicates. Next finds `[-1, 0, 1]`.
 *   - Skips the duplicate second `-1` at index 2. Returns final unique result list.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is sorting a prerequisite for this two-pointer approach?
 *    (Sorting ensures that elements are arranged monotonically, allowing us to safely increment `left`
 *    to increase the sum or decrement `right` to decrease it in linear time.)
 * 2. Why must we skip duplicate values for both the fixed outer element and the inner pointers?
 *    (To strictly satisfy the problem constraint that the solution set must not contain duplicate triplets
 *    (e.g., preventing the exact same combination from being appended multiple times).)
 */
public class P34_ThreeSum {

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) return result;

        // Step 1: Sort array ascending to enable two-pointer convergence and duplicate skipping
        Arrays.sort(nums);

        // Step 2: Fix the first element nums[i] one by one
        for (int i = 0; i < nums.length - 2; i++) {
            // Pruning: if the smallest fixed number is positive, sum can never equal zero
            if (nums[i] > 0) break;

            // Skip duplicate values for the first element to avoid duplicate triplets
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // Step 3: Run Two Sum II on the remainder of the array
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicate values for the second and third elements
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;
                    right--;
                } else if (sum < 0) {
                    left++; // Sum is too small; advance left pointer
                } else {
                    right--; // Sum is too large; decrement right pointer
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println("P34 Output: " + threeSum(nums));
        // Expected: [[-1, -1, 2], [-1, 0, 1]]
    }
}