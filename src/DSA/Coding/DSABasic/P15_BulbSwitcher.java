package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: After n rounds of toggling divisors, count bulbs left on.
 * EXAMPLE: n=3 -> 1.
 * CONSTRAINTS: 0 <= n <= 10^9.
 * LOGIC / ALGORITHM:
 * 1. Only bulbs with an odd divisor count remain on, exactly perfect squares.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input n=3 -> 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P15_BulbSwitcher {
    public static int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }

    public static void main(String[] args) {
        System.out.println(bulbSwitch(3));
    }
}
