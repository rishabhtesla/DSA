package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [13 / 24] - PRODUCT OF ARRAY EXCEPT SELF (LeetCode 238)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer array nums, return an array answer such that answer[i] is
 *   equal to the product of all elements of nums except nums[i].
 *   You must solve it in O(n) without using the division operation.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Naive with division: Product of all elements divided by nums[i] -> fails on zeros.
 *   - Prefix & Suffix Products:
 *     For any index i: answer[i] = (product of all left) * (product of all right).
 *   - Two-Array approach: Left product array and right product array -> O(n) extra space.
 *   - Aha! Moment (O(1) Auxiliary Space):
 *     1. Use the output array `res` to store running prefix products from left to right.
 *     2. Traverse backwards from right to left with a single running scalar `suffixProduct`,
 *        multiplying `res[i]` by `suffixProduct` on the fly.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Two passes over the array.
 *   - Space: O(1) - Auxiliary space (the output array does not count toward space complexity).
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