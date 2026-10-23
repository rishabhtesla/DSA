package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return Tn where T0=0, T1=1, T2=1 and each term sums the previous three.
 * EXAMPLE: tribonacci(4) -> 4.
 * CONSTRAINTS: 0 <= n <= 37.
 * LOGIC / ALGORITHM:
 * 1. Maintain a rolling triple and shift it after each new sum.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input tribonacci(4) -> 4.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P04_NthTribonacciNumber {
    public static int tribonacci(int n) {
        int a = 0, b = 1, c = 1;
        for (int i = 0; i < n; i++) {
            int t = a + b + c;
            a = b;
            b = c;
            c = t;
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(tribonacci(4));
    }
}
