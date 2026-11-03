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
 *   Visual state table:
 *
 *   Step | numBottles before | totalBottles before | add to total | next numBottles
 *   -----|-------------------|---------------------|--------------|----------------
 *     0  |         9         |          0          |      9       |       3
 *     1  |         3         |          9          |      3       |       1
 *   Stop because 1 < numExchange (3).
 *   Final answer = totalBottles 12 + remaining numBottles 1 = 13.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P05_WaterBottles {
    public static int numWaterBottles(int numBottles, int numExchange) {
        int totalBottles = 0;
        while (numBottles >= numExchange) {
            totalBottles += numBottles;
            numBottles = numBottles / numExchange + numBottles % numExchange;
        }
        return totalBottles + numBottles;
    }

    public static void main(String[] args) {
        System.out.println(numWaterBottles(9, 3));
    }
}
