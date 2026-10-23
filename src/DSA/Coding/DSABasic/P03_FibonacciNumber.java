package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the nth Fibonacci number with F(0)=0 and F(1)=1.
 * EXAMPLE: fib(6) -> 8.
 * CONSTRAINTS: 0 <= n <= 30.
 * LOGIC / ALGORITHM:
 * 1. Keep the last two values and repeatedly replace them with their sum.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input fib(6) -> 8.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P03_FibonacciNumber {
    public static int fib(int n) {
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            int t = a + b;
            a = b;
            b = t;
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(fib(6));
    }
}
