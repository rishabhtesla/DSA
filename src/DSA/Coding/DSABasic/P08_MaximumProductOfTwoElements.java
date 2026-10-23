package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Maximize (nums[i]-1)(nums[j]-1) over two distinct elements.
 * EXAMPLE: [3,4,5,2] -> 12 from 5 and 4.
 * CONSTRAINTS: 2 <= n <= 500; 1 <= nums[i] <= 1000.
 * LOGIC / ALGORITHM:
 * 1. Track the largest and second-largest values in one pass.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [3,4,5,2] -> 12 from 5 and 4.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P08_MaximumProductOfTwoElements {
    public static int maxProduct(int[] a) {
        int x = -1, y = -1;
        for (int v : a) {
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
