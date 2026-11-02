package DSA.Coding.DSABasic;

/*
 * LEETCODE 509: FIBONACCI NUMBER
 *
 * Question
 * The Fibonacci sequence is defined as:
 *   F(0) = 0
 *   F(1) = 1
 *   F(n) = F(n - 1) + F(n - 2), for n > 1
 *
 * Given n, calculate F(n).
 *
 * Examples
 *   n = 2    -> 1
 *   n = 4    -> 3
 *   n = 6    -> 8
 *
 * Constraints
 *   0 <= n <= 30
 *
 * Approach used by edSlash
 * 1. Return 0 or 1 directly for the two base cases.
 * 2. Keep the last two values in ft and st.
 * 3. Calculate the next value as ft + st.
 * 4. Move st into ft and the new value into st.
 * 5. Repeat until the required position is reached.
 *
 * Dry run: n = 6
 *   Start: ft = 0, st = 1
 *   i = 1: tt = 1, ft = 1, st = 1
 *   i = 2: tt = 2, ft = 1, st = 2
 *   i = 3: tt = 3, ft = 2, st = 3
 *   i = 4: tt = 5, ft = 3, st = 5
 *   i = 5: tt = 8, ft = 5, st = 8
 *   i = 6: tt = 13, ft = 8, st = 13
 *   Return ft = 8.
 *
 * Complexity
 *   Time: O(n).
 *   Space: O(1).
 */
public class P03_FibonacciNumber {
    public static int fib(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        int ft = 0;
        int st = 1;

        for (int i = 1; i <= n; i++) {
            int tt = ft + st;
            ft = st;
            st = tt;
        }

        return ft;
    }

    public static void main(String[] args) {
        System.out.println(fib(6));
    }
}
