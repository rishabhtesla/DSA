package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P29: SQRT X
 *
 * Question
 * Return floor(sqrt(x)) without using a square-root library.
 *
 * Example
 * x=8 -> 2.
 *
 * Constraints
 * 0 <= x <= 2^31-1.
 *
 * Approach used by edSlash
 * 1. Binary-search the greatest integer whose square is at most x, using long multiplication.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: x=8 -> 2.
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P29_SqrtX {
    public static int mySqrt(int x) {
        long start = 0, end = x;
        while (start <= end) {
            long mid = (start + end) / 2;
            if (mid * mid <= x) start = mid + 1;
            else end = mid - 1;
        }
        return (int) end;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(8));
    }
}
