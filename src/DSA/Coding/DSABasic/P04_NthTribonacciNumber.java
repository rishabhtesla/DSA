package DSA.Coding.DSABasic;

/*
 * LEETCODE 1137: N-TH TRIBONACCI NUMBER
 *
 * Question
 * The Tribonacci sequence is defined as:
 *   T(0) = 0
 *   T(1) = 1
 *   T(2) = 1
 *   T(n) = T(n - 1) + T(n - 2) + T(n - 3), for n > 2
 *
 * Given n, return T(n).
 *
 * Examples
 *   n = 3    -> 2
 *   n = 4    -> 4
 *   n = 25   -> 1389537
 *
 * Constraints
 *   0 <= n <= 37
 *
 * Approach used by edSlash
 * 1. Return the base values for n = 0, n = 1, and n = 2.
 * 2. Keep the previous three values in ft, st, and tt.
 * 3. Calculate the next value as ft + st + tt.
 * 4. Shift the three variables one position to the right.
 * 5. Repeat until the required position is reached.
 *
 * Dry run: n = 4
 *   Start: ft = 0, st = 1, tt = 1
 *   i = 1: fot = 2, ft = 1, st = 1, tt = 2
 *   i = 2: fot = 4, ft = 1, st = 2, tt = 4
 *   i = 3: fot = 7, ft = 2, st = 4, tt = 7
 *   i = 4: fot = 13, ft = 4, st = 7, tt = 13
 *   Return ft = 4.
 *
 * Complexity
 *   Time: O(n).
 *   Space: O(1).
 */
public class P04_NthTribonacciNumber {
    public static int tribonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1 || n == 2) {
            return 1;
        }

        int ft = 0;
        int st = 1;
        int tt = 1;

        for (int i = 1; i <= n; i++) {
            int fot = ft + st + tt;
            ft = st;
            st = tt;
            tt = fot;
        }

        return ft;
    }

    public static void main(String[] args) {
        System.out.println(tribonacci(4));
    }
}
