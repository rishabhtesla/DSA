package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: For each query value, find its first greater value to the right in nums2.
 * EXAMPLE: nums1=[4,1,2], nums2=[1,3,4,2] -> [-1,3,-1].
 * CONSTRAINTS: nums2 values are distinct; nums1 is a subset.
 * LOGIC / ALGORITHM:
 * 1. A decreasing monotonic stack resolves values when a larger value arrives, then map queries.
 * 2. When the current item resolves smaller pending candidates, pop them; push the current item as unresolved.
 * 3. Assign the default answer to candidates that never find a partner, then return the result.
 * INVARIANT: The stack contains exactly unresolved candidates in monotonic order; every popped item has just found its required neighbor.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input nums1=[4,1,2], nums2=[1,3,4,2] -> [-1,3,-1].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P40_NextGreaterElementI {
    public static int[] nextGreaterElement(int[] a, int[] b) {
        Map<Integer, Integer> m = new HashMap<>();
        Deque<Integer> s = new ArrayDeque<>();
        for (int x : b) {
            while (!s.isEmpty() && s.peek() < x) m.put(s.pop(), x);
            s.push(x);
        }
        int[] r = new int[a.length];
        for (int i = 0; i < a.length; i++) r[i] = m.getOrDefault(a[i], -1);
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));
    }
}
