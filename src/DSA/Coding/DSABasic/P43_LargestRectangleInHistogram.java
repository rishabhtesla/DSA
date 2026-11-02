package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P43: LARGEST RECTANGLE IN HISTOGRAM
 *
 * Question
 * Find the largest rectangle area under adjacent histogram bars.
 *
 * Example
 * [2,1,5,6,2,3] -> 10.
 *
 * Constraints
 * 1 <= n <= 10^5; heights are non-negative.
 *
 * Approach used by edSlash
 * 1. A monotonic increasing stack identifies each bar's maximal left/right span when it is popped.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 * Example from main: [2,1,5,6,2,3] -> 10.
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
 *
 * Complexity
 * O(n log n) time and O(n) auxiliary space.
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
