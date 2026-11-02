package DSA.Coding.DSABasic;

/*
 * LEETCODE 231: POWER OF TWO
 *
 * Question
 * Given an integer n, return true if it is a power of two. Otherwise, return
 * false.
 *
 * Examples
 *   n = 1    -> true
 *   n = 16   -> true
 *   n = 18   -> false
 *
 * Constraints
 *   -2^31 <= n <= 2^31 - 1
 *
 * Approach used by edSlash
 * 1. Zero is not a power of two, so return false for n = 0.
 * 2. While n is divisible by 2, divide it by 2.
 * 3. If the final value is 1, every factor was 2, so the original number was
 *    a power of two.
 * 4. Otherwise, the original number had another factor and is not a power of
 *    two.
 *
 * Dry run: n = 16
 *   Start: n = 16
 *   Divide by 2: n = 8
 *   Divide by 2: n = 4
 *   Divide by 2: n = 2
 *   Divide by 2: n = 1
 *   n == 1, so return true.
 *
 * Dry run: n = 18
 *   Divide by 2: n = 9
 *   9 is not divisible by 2 and is not 1.
 *   Return false.
 *
 * Complexity
 *   Time: O(log n), because n is divided by 2 in every iteration.
 *   Space: O(1).
 */
public class P02_PowerOfTwo {
    public static boolean isPowerOfTwo(int n) {
        if (n == 0) {
            return false;
        }

        while (n % 2 == 0) {
            n = n / 2;
        }

        if (n == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(16));
    }
}
