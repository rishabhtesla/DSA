package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [03 / 24] - REMOVE DUPLICATES FROM SORTED ARRAY (LeetCode 26)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer array nums sorted in non-decreasing order, remove duplicates
 *   in-place such that each unique element appears only once. Return k.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Since the array is sorted, duplicates are strictly consecutive.
 *   - The 0th element is always unique by definition. Start write pointer at index 1.
 *   - Iterate read from index 1 to n - 1.
 *   - If nums[read] != nums[read - 1], we transitioned to a brand new unique number.
 *     Copy it to nums[write] and advance write.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single linear scan.
 *   - Space: O(1) - In-place array update.
 *
 * EXAMPLE:
 *   Input:  nums = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
 *   Output: k = 5, and the first k values are [0, 1, 2, 3, 4]
 *
 * VISUAL DRY RUN:
 *   `write` starts at 1 because nums[0] = 0 is already the first unique value.
 *   `read` checks each next value against the value immediately before it.
 *
 *   Initial: read = 1, write = 1
 *            nums = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
 *
 *   - read = 1: nums[1] = 0, previous = 0
 *     Duplicate -> skip it. write remains 1.
 *
 *   - read = 2: nums[2] = 1, previous = 0
 *     New value -> nums[write] = nums[2], so nums[1] = 1.
 *     nums = [0, 1, 1, 1, 1, 2, 2, 3, 3, 4], write = 2
 *
 *   - read = 3: nums[3] = 1, previous = 1
 *     Duplicate -> skip it. write remains 2.
 *
 *   - read = 4: nums[4] = 1, previous = 1
 *     Duplicate -> skip it. write remains 2.
 *
 *   - read = 5: nums[5] = 2, previous = 1
 *     New value -> nums[2] = 2.
 *     nums = [0, 1, 2, 1, 1, 2, 2, 3, 3, 4], write = 3
 *
 *   - read = 6: nums[6] = 2, previous = 2
 *     Duplicate -> skip it. write remains 3.
 *
 *   - read = 7: nums[7] = 3, previous = 2
 *     New value -> nums[3] = 3.
 *     nums = [0, 1, 2, 3, 1, 2, 2, 3, 3, 4], write = 4
 *
 *   - read = 8: nums[8] = 3, previous = 3
 *     Duplicate -> skip it. write remains 4.
 *
 *   - read = 9: nums[9] = 4, previous = 3
 *     New value -> nums[4] = 4.
 *     nums = [0, 1, 2, 3, 4, 2, 2, 3, 3, 4], write = 5
 *
 *   The loop ends. Return write = 5.
 *   Only the first five positions matter: [0, 1, 2, 3, 4].
 *
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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