package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P40: NEXT GREATER ELEMENT I
 *
 * Question
 * For each query value, find its first greater value to the right in nums2.
 *
 * Example
 * nums1=[4,1,2], nums2=[1,3,4,2] -> [-1,3,-1].
 *
 * Constraints
 * nums2 values are distinct; nums1 is a subset.
 *
 * Approach used by edSlash
 * 1. A decreasing monotonic stack resolves values when a larger value arrives, then map queries.
 * 2. Process the input from left to right and keep unresolved values in the stack until a later value resolves them.
 * 3. Remove or preserve stack entries according to the ordering or balance rule required by the problem.
 *
 * Dry run
 *   Next-greater mapping for nums2 = [1,3,4,2]:
 *     read 1, then 3 resolves 1 -> next greater is 3
 *     read 4 resolves 3 -> next greater is 4
 *     4 and 2 have no greater value to their right -> -1
 *     nums1 [4,1,2] maps to [-1,3,-1]
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P40_NextGreaterElementI {
    public static int[] nextGreaterElement(int[] findNums, int[] nums) {
        Map<Integer, Integer> m = new HashMap<>();
        Deque<Integer> s = new ArrayDeque<>();
        for (int x : nums) {
            while (!s.isEmpty() && s.peek() < x) m.put(s.pop(), x);
            s.push(x);
        }
        int[] r = new int[findNums.length];
        for (int i = 0; i < findNums.length; i++) r[i] = m.getOrDefault(findNums[i], -1);
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));
    }
}
