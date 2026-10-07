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
 */
public class P02_RemoveElement {

    public static int removeElement(int[] nums, int val) {
        int write = 0;

        for (int read = 0; read < nums.length; read++) {
            if (nums[read] != val) {
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