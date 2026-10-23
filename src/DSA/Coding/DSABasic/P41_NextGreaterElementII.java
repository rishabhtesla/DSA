package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find circular next-greater values for every array element.
 * EXAMPLE: [1,2,1] -> [2,-1,2].
 * CONSTRAINTS: 1 <= n <= 10^4; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Scan indices twice; stack stores unresolved indices from the first pass.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [1,2,1] -> [2,-1,2].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P41_NextGreaterElementII {
    public static int[] nextGreaterElements(int[] a) {
        int n = a.length;
        int[] r = new int[n];
        Arrays.fill(r, -1);
        Deque<Integer> s = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            while (!s.isEmpty() && a[s.peek()] < a[i % n]) r[s.pop()] = a[i % n];
            if (i < n) s.push(i);
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGreaterElements(new int[]{1, 2, 1})));
    }
}
