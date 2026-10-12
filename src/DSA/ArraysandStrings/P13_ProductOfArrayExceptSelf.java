package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [13 / 24] - PRODUCT OF ARRAY EXCEPT SELF (LeetCode 238)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an integer array nums, return an array answer such that answer[i] is
 * equal to the product of all elements of nums except nums[i].
 * You must solve it in O(n) time complexity and WITHOUT using the division operation.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force O(n^2):
 *    Nested loops to calculate the product for every index. Too slow.
 *
 * 2. Division Approach O(n):
 *    Find the total product of all elements and divide by nums[i].
 *    -> FAILS completely if the array contains zeros (division by zero / loss of zero counts).
 *
 * 3. Two-Array Prefix & Suffix Products O(n) Time | O(n) Space:
 *    - Left array: stores running products from left to right.
 *    - Right array: stores running products from right to left.
 *    - answer[i] = left[i - 1] * right[i + 1].
 *    -> Meets time requirement, but uses extra space.
 *
 * 4. THE "AHA!" MOMENT (Optimal O(n) Time | O(1) Extra Space):
 *    - We can reuse the output array (`res`) to store the prefix products directly.
 *    - Instead of a full right array, use a single running scalar variable
 *      (`suffixProduct`) traversing backwards from right to left, multiplying
 *      into `res[i]` on the fly.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Exactly two linear passes over the array.
 * - Space: O(1) - Auxiliary space (the output array does not count toward space complexity).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE: nums = [1, 2, 3, 4] -> [24, 12, 8, 6]
 *
 * VISUAL DRY RUN:
 *   Initial Array: [1, 2, 3, 4]
 *
 *   Step 1: Prefix Pass (Left-to-Right)
 *   - Builds `res` to hold products of elements strictly to the left.
 *   - res[0] = 1 (nothing to the left)
 *   - res[1] = res[0] * nums[0] = 1 * 1 = 1
 *   - res[2] = res[1] * nums[1] = 1 * 2 = 2
 *   - res[3] = res[2] * nums[2] = 2 * 3 = 6
 *   - Result after Pass 1: res = [1, 1, 2, 6]
 *
 *   Step 2: Suffix Pass (Right-to-Left with `suffixProduct = 1`)
 *   - i = 3: res[3] *= 1 -> 6 * 1 = 6  | suffixProduct updates to: 1 * 4 = 4
 *   - i = 2: res[2] *= 4 -> 2 * 4 = 8  | suffixProduct updates to: 4 * 3 = 12
 *   - i = 1: res[1] *= 12 -> 1 * 12 = 12 | suffixProduct updates to: 12 * 2 = 24
 *   - i = 0: res[0] *= 24 -> 1 * 24 = 24 | suffixProduct updates to: 24 * 1 = 24
 *   - Final Result: [24, 12, 8, 6]
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does the prefix pass start at index 1 instead of 0?
 *    (Because element at index 0 has no elements to its left, so its left product is default 1.)
 * 2. Why does the suffix pass update `res[i]` *before* updating `suffixProduct`?
 *    (Because `suffixProduct` must represent the product of elements strictly *to the right* of index i, excluding nums[i] itself.)
 * 3. How does this algorithm handle arrays with zeros? (e.g., [1, 0, 3])
 *    (Naturally handles zeros without division errors because it relies strictly on multiplication.)
 */
public class P13_ProductOfArrayExceptSelf {

    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        // Pass 1: res[i] stores product of all elements to the left of index i
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }

        // Pass 2: Multiply by product of all elements to the right of index i
        int suffixProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            res[i] *= suffixProduct;
            suffixProduct *= nums[i];
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println("P13 Output: " + Arrays.toString(productExceptSelf(nums)));
        // Expected: [24, 12, 8, 6]
    }
}