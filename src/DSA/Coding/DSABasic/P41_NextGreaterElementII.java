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
 *   Circular next-greater visual for [1,2,1]:
 *     scan indices twice: [0,1,2,0,1,2]
 *     1 at index 0 sees 2 -> answer 2
 *     2 has no greater value -> -1
 *     1 at index 2 wraps around and sees 2 -> answer 2
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P41_NextGreaterElementII {
    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] r = new int[n];
        Arrays.fill(r, -1);
        Deque<Integer> s = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            while (!s.isEmpty() && nums[s.peek()] < nums[i % n]) r[s.pop()] = nums[i % n];
            if (i < n) s.push(i);
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGreaterElements(new int[]{1, 2, 1})));
    }
}
