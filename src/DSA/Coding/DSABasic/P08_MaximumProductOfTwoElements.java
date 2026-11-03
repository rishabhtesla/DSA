package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P08: MAXIMUM PRODUCT OF TWO ELEMENTS
 *
 * Question
 * Maximize (nums[i]-1)(nums[j]-1) over two distinct elements.
 *
 * Example
 * [3,4,5,2] -> 12 from 5 and 4.
 *
 * Constraints
 * 2 <= n <= 500; 1 <= nums[i] <= 1000.
 *
 * Approach used by edSlash
 * 1. Track the largest and second-largest values in one pass.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [3,4,5,2] -> 12 from 5 and 4.
 *   Visual state table:
 *
 *   Value | largest x | second-largest y | action
 *   ------|-----------|------------------|--------------------
 *      3  |     3     |        -1        | x = 3
 *      4  |     4     |         3        | x = 4, y = 3
 *      5  |     5     |         4        | x = 5, y = 4
 *      2  |     5     |         4        | no change
 *
 *   Final calculation: (5 - 1) * (4 - 1) = 4 * 3 = 12.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P08_MaximumProductOfTwoElements {
    public static int maxProduct(int[] nums) {
        int x = -1, y = -1;
        for (int v : nums) {
            if (v > x) {
                y = x;
                x = v;
            } else if (v > y) y = v;
        }
        return (x - 1) * (y - 1);
    }

    public static void main(String[] args) {
        System.out.println(maxProduct(new int[]{3, 4, 5, 2}));
    }
}
