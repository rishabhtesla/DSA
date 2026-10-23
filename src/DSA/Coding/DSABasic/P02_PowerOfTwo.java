package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return whether n is an exact power of two.
 * EXAMPLE: 16 -> true; 18 -> false.
 * CONSTRAINTS: -2^31 <= n <= 2^31-1.
 * LOGIC / ALGORITHM:
 * 1. A positive power of two has exactly one set bit, so n & (n-1) must be zero.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 16 -> true; 18 -> false.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P02_PowerOfTwo {
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(16));
    }
}
