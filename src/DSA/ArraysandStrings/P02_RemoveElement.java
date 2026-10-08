package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [02 / 24] - REMOVE ELEMENT (LeetCode 27)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer array nums and an integer val, remove all occurrences of val
 *   in-place. Return the number of elements (k) remaining. The first k elements
 *   must hold the non-val values.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Two-Pointer Reader/Writer pattern.
 *   - 'write' marks the index where the next valid element belongs.
 *   - 'read' iterates through every element.
 *   - Whenever nums[read] != val, write it into nums[write] and advance write.
 *   - Any element matching val is simply skipped by 'read'.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass through nums.
 *   - Space: O(1) - Constant auxiliary space.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 *
 * DRY RUN:
 *   nums = [3, 2, 2, 3], val = 3
 *
 *   `read` examines every element. `write` marks the next position where a
 *   value different from `val` should be stored. Therefore, `write` never
 *   moves ahead of `read`, so copying is always safe.
 *
 *   Step | read/value | write before | Decision             | Array after step | write after
 *   -----+------------+--------------+----------------------+------------------+------------
 *     1  | 0 / 3      |      0       | Skip: value == 3    | [3, 2, 2, 3]    |     0
 *     2  | 1 / 2      |      0       | Copy 2 to index 0   | [2, 2, 2, 3]    |     1
 *     3  | 2 / 2      |      1       | Copy 2 to index 1   | [2, 2, 2, 3]    |     2
 *     4  | 3 / 3      |      2       | Skip: value == 3    | [2, 2, 2, 3]    |     2
 *
 *   The method returns `write = 2`, so only the first two positions matter:
 *   prefix = [2, 2]. Values after index `write - 1` are irrelevant.
 *
 *   Loop invariant:
 *   Before reading index `read`, the prefix nums[0..write-1] contains exactly
 *   the non-`val` values seen so far, in their original relative order.
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