package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Compute how many bottles can be drunk by exchanging empty bottles.
 * EXAMPLE: 9 bottles, exchange 3 -> 13 bottles drunk.
 * CONSTRAINTS: 1 <= numBottles, numExchange <= 100.
 * LOGIC / ALGORITHM:
 * 1. Drink every current bottle, then turn empties into full bottles using quotient and retain the remainder.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 9 bottles, exchange 3 -> 13 bottles drunk.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P05_WaterBottles {
    public static int numWaterBottles(int n, int e) {
        int ans = 0;
        while (n >= e) {
            ans += n;
            n = n / e + n % e;
        }
        return ans + n;
    }

    public static void main(String[] args) {
        System.out.println(numWaterBottles(9, 3));
    }
}
