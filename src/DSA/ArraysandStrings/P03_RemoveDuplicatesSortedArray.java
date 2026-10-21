package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * [03 / 24] - REMOVE DUPLICATES FROM SORTED ARRAY (LeetCode 26)
 *
 * PROBLEM:
 * Given a sorted integer array nums, remove duplicates in-place so every
 * distinct value appears exactly once. Return k; the first k positions must
 * contain the unique values in sorted order.
 *
 * CONSTRAINTS:
 * - 0 <= nums.length <= 3 * 10^4
 * - -100 <= nums[i] <= 100
 * - nums is sorted in non-decreasing order.
 *
 * EXAMPLES:
 * - [1,1,2] -> k=2, prefix=[1,2]
 * - [0,0,1,1,1,2,2,3,3,4] -> k=5, prefix=[0,1,2,3,4]
 * - [] -> k=0, prefix=[]
 *
 * BRUTE FORCE:
 * Build a separate set/list of values not already seen. This is easy to
 * reason about but requires O(n) extra space; repeatedly removing from an
 * array can also take O(n^2) time because of shifting.
 *
 * MAIN LOGIC:
 * Sorted order makes duplicates adjacent. Keep `write` at the next position
 * for a new value. If nums[read] differs from nums[read-1], copy it to
 * nums[write]. Invariant: nums[0..write-1] contains exactly the unique values
 * encountered so far. Time: O(n); extra space: O(1).
 *
 * VISUAL DRY RUN:
 * nums=[0,0,1,1,1,2,2,3,3,4]; write=1; prefix starts [0]
 * - read=1: 0 equals previous 0 -> duplicate; write=1
 * - read=2: 1 differs -> nums[1]=1; write=2; prefix=[0,1]
 * - read=3: 1 equals previous -> skip; write=2
 * - read=4: 1 equals previous -> skip; write=2
 * - read=5: 2 differs -> nums[2]=2; write=3; prefix=[0,1,2]
 * - read=6: 2 equals previous -> skip
 * - read=7: 3 differs -> nums[3]=3; write=4; prefix=[0,1,2,3]
 * - read=8: 3 equals previous -> skip
 * - read=9: 4 differs -> nums[4]=4; write=5
 * Return k=5; valid prefix=[0,1,2,3,4].
 *
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does comparing with the previous sorted value detect every duplicate?
 * 2. Why can write never pass read?
 * 3. What changes if the input is not sorted?
 */
public class P03_RemoveDuplicatesSortedArray {

    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int write = 1;
        for (int read = 1; read < nums.length; read++) {
            if (nums[read] != nums[read - 1]) {
                nums[write] = nums[read];
                write++;
            }
        }

        return write;
    }

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int k = removeDuplicates(nums);
        System.out.println("P03 Output: k = " + k + ", prefix = " + Arrays.toString(Arrays.copyOf(nums, k)));
        // Expected: k = 5, prefix = [0, 1, 2, 3, 4]
    }
}