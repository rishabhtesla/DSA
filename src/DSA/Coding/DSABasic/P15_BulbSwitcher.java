package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P15: BULB SWITCHER
 *
 * Question
 * After n rounds of toggling divisors, count bulbs left on.
 *
 * Example
 * n=3 -> 1.
 *
 * Constraints
 * 0 <= n <= 10^9.
 *
 * Approach used by edSlash
 * 1. Only bulbs with an odd divisor count remain on, exactly perfect squares.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 *   Bulb state visual for n = 3:
 *     bulb 1 is toggled on rounds 1,2,3 -> off
 *     bulb 2 is toggled on rounds 1,2 -> on
 *     bulb 3 is toggled on round 1 -> on
 *     one bulb remains on, so return 1
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P15_BulbSwitcher {
    public static int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }

    public static void main(String[] args) {
        System.out.println(bulbSwitch(3));
    }
}
