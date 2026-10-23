package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Choose two vertical lines that hold the greatest water area.
 * EXAMPLE: [1,8,6,2,5,4,8,3,7] -> 49.
 * CONSTRAINTS: 2 <= n <= 10^5; heights are non-negative.
 * LOGIC / ALGORITHM:
 * 1. Start at both ends; discard the shorter wall because moving the taller wall cannot improve the limiting height.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,8,6,2,5,4,8,3,7] -> 49.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P17_ContainerWithMostWater {
    public static int maxArea(int[] a) {
        int l = 0, r = a.length - 1, ans = 0;
        while (l < r) {
            ans = Math.max(ans, Math.min(a[l], a[r]) * (r - l));
            if (a[l] < a[r]) l++;
            else r--;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }
}
