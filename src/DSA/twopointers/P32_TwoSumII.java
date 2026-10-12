package DSA.twopointers;

import java.util.Arrays;

/**
 * ============================================================================
 * [32 / 34] - TWO SUM II - INPUT ARRAY IS SORTED (LeetCode 167)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a 1-indexed array of integers `numbers` that is already sorted in non-decreasing
 * order, find two numbers such that they add up to a specific target number.
 * Return the 1-based indices `[index1, index2]` of the two numbers.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach:
 *    - Use nested loops to check all possible pairs of numbers.
 *    - Inefficient: takes $O(n^2)$ time, ignoring the critical sorted property of the array.
 *
 * 2. Optimal Opposite-Ends Two Pointers Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** Because the array is **sorted**, we can place two pointers at opposite ends
 *      (`left = 0`, `right = numbers.length - 1`) and adjust them monotonically based on the current sum:
 *      - If `sum == target`: Return the 1-based indices `[left + 1, right + 1]`.
 *      - If `sum < target`: The sum is too small. The only way to increase it is to advance the `left` pointer (`left++`).
 *      - If `sum > target`: The sum is too large. The only way to decrease it is to decrement the `right` pointer (`right--`).
 *    - This guarantees that no potential candidate pairs are missed, running in a single linear pass.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Each step moves either `left` or `right` inward; at most $n$ total iterations.
 * - Space: O(1) - Constant auxiliary pointer space.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `numbers = [2, 7, 11, 15]`, `target = 9`
 * - Expected 1-Indexed Result: `[1, 2]`
 *
 * VISUAL DRY RUN (for `[2, 7, 11, 15]`, `target = 9`):
 *   - `left = 0` (2), `right = 3` (15) -> sum = 17 > 9. Decrement `right` to 2.
 *   - `left = 0` (2), `right = 2` (11) -> sum = 13 > 9. Decrement `right` to 1.
 *   - `left = 0` (2), `right = 1` (7) -> sum = 9 == target. Returns `[1, 2]`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does moving `left` increase the sum and moving `right` decrease it?
 *    (Because the array is sorted in non-decreasing order, moving indices inward accesses larger values
 *    from the left and smaller values from the right respectively.)
 * 2. Are there any edge cases with duplicate values in the array?
 *    (No, the problem guarantees a unique solution exists, and the monotonic nature of the two pointers
 *    will always land directly on the correct pair.)
 */
public class P32_TwoSumII {

    public static int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1}; // Return 1-indexed positions as required
            } else if (sum < target) {
                left++; // Sum is too small; increase left pointer
            } else {
                right--; // Sum is too large; decrease right pointer
            }
        }

        return new int[]{-1, -1}; // Fallback (though a valid solution is guaranteed by constraints)
    }

    public static void main(String[] args) {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        System.out.println("P32 Output: " + Arrays.toString(twoSum(numbers, target)));
        // Expected: [1, 2]
    }
}