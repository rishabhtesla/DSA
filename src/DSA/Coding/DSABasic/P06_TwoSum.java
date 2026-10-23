package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find two distinct indices whose values add to target.
 * EXAMPLE: [2,7,11,15], 9 -> [0,1].
 * CONSTRAINTS: 2 <= n <= 10^4; exactly one answer; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Store value-to-index complements while scanning once.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [2,7,11,15], 9 -> [0,1].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P06_TwoSum {
    public static int[] twoSum(int[] a, int t) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (m.containsKey(t - a[i])) return new int[]{m.get(t - a[i]), i};
            m.put(a[i], i);
        }
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
    }
}
