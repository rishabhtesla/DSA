package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P14: PRODUCT OF ARRAY EXCEPT SELF
 *
 * Question
 * Return each position's product of all other values without division.
 *
 * Example
 * [1,2,3,4] -> [24,12,8,6].
 *
 * Constraints
 * 2 <= n <= 10^5; products fit 32-bit output; division is forbidden.
 *
 * Approach used by edSlash
 * 1. First store products left of each index, then multiply by a right-to-left suffix product.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 *   Prefix/suffix visual for [1,2,3,4]:
 *     prefix products before each index: [1,1,2,6]
 *     suffix products after each index:  [24,12,4,1]
 *     multiply matching positions -> [24,12,8,6]
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P14_ProductOfArrayExceptSelf {
    public static int[] productExceptSelf(int[] nums) {
        int[] r = new int[nums.length];
        Arrays.fill(r, 1);
        int p = 1;
        for (int i = 0; i < nums.length; i++) {
            r[i] = p;
            p *= nums[i];
        }
        p = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            r[i] *= p;
            p *= nums[i];
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(productExceptSelf(new int[]{1, 2, 3, 4})));
    }
}
