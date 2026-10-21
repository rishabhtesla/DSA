package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * [02 / 24] - REMOVE ELEMENT (LeetCode 27)
 *
 * PROBLEM:
 * Given an integer array `nums` and integer `val`, remove every occurrence of
 * val in-place. Return `k`, the number of remaining values. The first k
 * positions of nums must contain values different from val; their order may
 * be preserved, but the remaining positions do not matter.
 *
 * CONSTRAINTS:
 * - 0 <= nums.length <= 100
 * - 0 <= nums[i], val <= 50
 *
 * EXAMPLES:
 * - nums=[3,2,2,3], val=3 -> k=2, prefix=[2,2]
 * - nums=[0,1,2,2,3,0,4,2], val=2 -> k=5, prefix=[0,1,3,0,4]
 * - nums=[], val=1 -> k=0, prefix=[]
 *
 * BRUTE FORCE:
 * For each matching value, shift every later value one position left. Repeated
 * shifting can take O(n^2) time and uses O(1) extra space.
 *
 * MAIN LOGIC:
 * Scan with `read`. When nums[read] is valid, copy it to the next free
 * position `write`; when it equals val, skip it. Invariant: nums[0..write-1]
 * contains exactly the valid values seen so far, in their original order.
 * Time: O(n); extra space: O(1).
 *
 * VISUAL DRY RUN:
 * nums=[3,2,2,3], val=3; read=0, write=0
 * - read=0: 3 equals val -> skip; write=0; nums=[3,2,2,3]
 * - read=1: 2 is valid -> nums[0]=2; write=1; nums=[2,2,2,3]
 * - read=2: 2 is valid -> nums[1]=2; write=2; nums=[2,2,2,3]
 * - read=3: 3 equals val -> skip; write=2
 * Return k=2; only prefix [2,2] is relevant.
 *
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is it safe to overwrite an earlier position while read moves forward?
 * 2. What does write represent before and after copying a valid value?
 * 3. Why are values after index k-1 allowed to remain unchanged?
 */
public class P02_RemoveElement {

    public static int removeElement(int[] nums, int val) {
        int write = 0;

        for (int read = 0; read < nums.length; read++) {
            if (nums[read] != val) {
                // Preserve this valid value in the next position of the prefix.
                nums[write] = nums[read];
                write++;
            }
        }

        return write; // write is the count of valid elements k
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 2, 3};
        int k = removeElement(nums, 3);
        System.out.println("P02 Output: k = " + k + ", prefix = " + Arrays.toString(Arrays.copyOf(nums, k)));
        // Expected: k = 2, prefix = [2, 2]
    }
}