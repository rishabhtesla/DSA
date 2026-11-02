package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P41: NEXT GREATER ELEMENT II
 *
 * Question
 * Find circular next-greater values for every array element.
 *
 * Example
 * [1,2,1] -> [2,-1,2].
 *
 * Constraints
 * 1 <= n <= 10^4; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Scan indices twice; stack stores unresolved indices from the first pass.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 * Example from main: [1,2,1] -> [2,-1,2].
 * 1. Read each character or value from left to right.
 * 2. Push unresolved values or state onto the stack.
 * 3. When the current value resolves the top entry, pop and update the answer.
 * 4. Process any remaining stack entries before returning.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
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
