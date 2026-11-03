package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P11: REMOVE ELEMENT
 *
 * Question
 * Remove all occurrences of val in-place and return the new length.
 *
 * Example
 * [3,2,2,3], val=3 -> length 2, prefix [2,2].
 *
 * Constraints
 * 0 <= length <= 100; order of retained values need not change.
 *
 * Approach used by edSlash
 * 1. Write each non-target value at the next compact position.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 *   State table for [3,2,2,3], val = 3:
 *     index 0: 3 == val, skip; writeIndex = 0
 *     index 1: 2 != val, write nums[0] = 2; writeIndex = 1
 *     index 2: 2 != val, write nums[1] = 2; writeIndex = 2
 *     index 3: 3 == val, skip; final prefix = [2,2], length = 2
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P11_RemoveElement {
    public static int removeElement(int[] nums, int v) {
        int k = 0;
        for (int x : nums) if (x != v) nums[k++] = x;
        return k;
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 2, 3};
        System.out.println(removeElement(nums, 3));
    }
}
