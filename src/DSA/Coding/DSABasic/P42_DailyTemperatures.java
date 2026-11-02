package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P42: DAILY TEMPERATURES
 *
 * Question
 * For each day, return days until a warmer temperature.
 *
 * Example
 * [73,74,75,71,69,72,76,73] -> [1,1,4,2,1,1,0,0].
 *
 * Constraints
 * 1 <= n <= 10^5; temperatures are 30..100.
 *
 * Approach used by edSlash
 * 1. Maintain decreasing temperatures' indices; a warmer day pops and resolves them.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 * Example from main: [73,74,75,71,69,72,76,73] -> [1,1,4,2,1,1,0,0].
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
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
