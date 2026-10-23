package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: For each day, return days until a warmer temperature.
 * EXAMPLE: [73,74,75,71,69,72,76,73] -> [1,1,4,2,1,1,0,0].
 * CONSTRAINTS: 1 <= n <= 10^5; temperatures are 30..100.
 * LOGIC / ALGORITHM:
 * 1. Maintain decreasing temperatures' indices; a warmer day pops and resolves them.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [73,74,75,71,69,72,76,73] -> [1,1,4,2,1,1,0,0].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P42_DailyTemperatures {
    public static int[] dailyTemperatures(int[] a) {
        int[] r = new int[a.length];
        Deque<Integer> s = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            while (!s.isEmpty() && a[i] > a[s.peek()]) {
                int j = s.pop();
                r[j] = i - j;
            }
            s.push(i);
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
    }
}
