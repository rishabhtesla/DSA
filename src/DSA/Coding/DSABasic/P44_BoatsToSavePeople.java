package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Use boats carrying at most two people to minimize boat count.
 * EXAMPLE: [3,2,2,1], limit=3 -> 3.
 * CONSTRAINTS: 1 <= n <= 5*10^4; each weight <= limit.
 * LOGIC / ALGORITHM:
 * 1. Sort weights; pair lightest with heaviest when they fit, otherwise send heaviest alone.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [3,2,2,1], limit=3 -> 3.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P44_BoatsToSavePeople {
    public static int numRescueBoats(int[] a, int lim) {
        Arrays.sort(a);
        int l = 0, r = a.length - 1, c = 0;
        while (l <= r) {
            if (a[l] + a[r] <= lim) l++;
            r--;
            c++;
        }
        return c;
    }

    public static void main(String[] args) {
        System.out.println(numRescueBoats(new int[]{3, 2, 2, 1}, 3));
    }
}
