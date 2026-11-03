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
 *   Temperature stack visual for [73,74,75,71,69,72,76,73]:
 *     74 resolves 73 -> 1 day
 *     75 resolves 74 -> 1 day
 *     72 resolves 71 and 69 -> 2 and 1 days
 *     76 resolves all remaining lower temperatures; result = [1,1,4,2,1,1,0,0]
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P42_DailyTemperatures {
    public static int[] dailyTemperatures(int[] nums) {
        int[] r = new int[nums.length];
        Deque<Integer> s = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            while (!s.isEmpty() && nums[i] > nums[s.peek()]) {
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
