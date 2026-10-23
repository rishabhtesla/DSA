package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Compute rainwater trapped between elevation bars.
 * EXAMPLE: [0,1,0,2,1,0,1,3,2,1,2,1] -> 6.
 * CONSTRAINTS: 0 <= n <= 2*10^4; heights are non-negative.
 * LOGIC / ALGORITHM:
 * 1. Two pointers maintain left/right maximum walls and add water on the side with the lower current wall.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [0,1,0,2,1,0,1,3,2,1,2,1] -> 6.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P18_TrappingRainWater {
    public static int trap(int[] a) {
        int l = 0, r = a.length - 1, lm = 0, rm = 0, ans = 0;
        while (l < r) {
            if (a[l] < a[r]) {
                lm = Math.max(lm, a[l]);
                ans += lm - a[l++];
            } else {
                rm = Math.max(rm, a[r]);
                ans += rm - a[r--];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    }
}
