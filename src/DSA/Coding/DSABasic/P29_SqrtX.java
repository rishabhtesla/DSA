package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return floor(sqrt(x)) without using a square-root library.
 * EXAMPLE: x=8 -> 2.
 * CONSTRAINTS: 0 <= x <= 2^31-1.
 * LOGIC / ALGORITHM:
 * 1. Binary-search the greatest integer whose square is at most x, using long multiplication.
 * 2. Use the comparison or feasibility test to discard the half that cannot contain a valid answer.
 * 3. Return the remaining boundary or found index.
 * INVARIANT: The current search interval contains every possible answer; discarded elements cannot satisfy the ordering or feasibility condition.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input x=8 -> 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P29_SqrtX {
    public static int mySqrt(int x) {
        long l = 0, r = x;
        while (l <= r) {
            long m = (l + r) / 2;
            if (m * m <= x) l = m + 1;
            else r = m - 1;
        }
        return (int) r;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(8));
    }
}
