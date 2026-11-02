package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P05: WATER BOTTLES
 *
 * Question
 * Compute how many bottles can be drunk by exchanging empty bottles.
 *
 * Example
 * 9 bottles, exchange 3 -> 13 bottles drunk.
 *
 * Constraints
 * 1 <= numBottles, numExchange <= 100.
 *
 * Approach used by edSlash
 * 1. Drink every current bottle, then turn empties into full bottles using quotient and retain the remainder.
 * 2. Add the bottles currently available to the total, because every one of them can be drunk.
 * 3. Repeat the exchange while enough empty bottles remain, then add the final bottles that cannot be exchanged.
 *
 * Dry run
 * Example from main: 9 bottles, exchange 3 -> 13 bottles drunk.
 * 1. Start with 9 full bottles, so the total is 9 and 9 empty bottles are available.
 * 2. Exchange the 9 empty bottles for 3 full bottles and drink them; the total is now 12.
 * 3. Those 3 new empties exchange for 1 more bottle, so the method returns 13 after drinking it.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
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
