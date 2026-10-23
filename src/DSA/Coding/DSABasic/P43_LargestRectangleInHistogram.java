package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find the largest rectangle area under adjacent histogram bars.
 * EXAMPLE: [2,1,5,6,2,3] -> 10.
 * CONSTRAINTS: 1 <= n <= 10^5; heights are non-negative.
 * LOGIC / ALGORITHM:
 * 1. A monotonic increasing stack identifies each bar's maximal left/right span when it is popped.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n log n) time and O(n) auxiliary space.
 * DRY RUN: Main input [2,1,5,6,2,3] -> 10.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P43_LargestRectangleInHistogram {
    public static int largestRectangleArea(int[] a) {
        Deque<Integer> s = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i <= a.length; i++) {
            int h = i == a.length ? 0 : a[i];
            while (!s.isEmpty() && h < a[s.peek()]) {
                int ht = a[s.pop()], w = s.isEmpty() ? i : i - s.peek() - 1;
                ans = Math.max(ans, ht * w);
            }
            s.push(i);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    }
}
