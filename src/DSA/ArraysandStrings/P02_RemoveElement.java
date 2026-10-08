package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 - ============================================================================
 - [02 / 24] - REMOVE ELEMENT (LeetCode 27)
 - ============================================================================
 -
 - PROBLEM:
 -   Given an integer array nums and an integer val, remove all occurrences of val
 -   in-place. Return the number of elements (k) remaining. The first k elements
 -   must hold the non-val values.
 *
 - INTERVIEW INTUITION & "AHA!" MOMENT:
 - Two-Pointer Reader/Writer pattern.
 - 'write' marks the index where the next valid element belongs.
 - 'read' iterates through every element.
 - Whenever nums[read] != val, write it into nums[write] and advance write.
 - Any element matching val is simply skipped by 'read'.
 *
 - COMPLEXITY:
 - Time:  O(n) - Single pass through nums.
 - Space: O(1) - Constant auxiliary space.
 *
 - EXAMPLE:
 -   Input:  nums = [3, 2, 2, 3], val = 3
 -   Output: k = 2, and the first k values are [2, 2]
 *
 - VISUAL DRY RUN:
 -   `write` starts at 0 because the first position is available for valid values.
 -   `read` checks each value against `val`.
 *
 -   Initial: read = 0, write = 0
 -            nums = [3, 2, 2, 3]
 *
 - read = 0: nums[0] = 3, val = 3
 -     Match val -> skip it. write remains 0.
 *
 - read = 1: nums[1] = 2, val = 3
 -     Different from val -> nums[write] = nums[1], so nums[0] = 2.
 -     nums = [2, 2, 2, 3], write = 1
 *
 - read = 2: nums[2] = 2, val = 3
 -     Different from val -> nums[write] = nums[2], so nums[1] = 2.
 -     nums = [2, 2, 2, 3], write = 2
 *
 - read = 3: nums[3] = 3, val = 3
 -     Match val -> skip it. write remains 2.
 *
 -   The loop ends. Return write = 2.
 -   Only the first two positions matter: [2, 2].
 *
 - CRITICAL THINKING CHECKPOINTS:
 - Before coding, what would the brute-force solution do, and where does it repeat work?
 -   2. What invariant must remain true after every loop iteration?
 -   3. Why is each pointer/state update safe, and what counterexample would break it?
 -   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 -   5. Can you derive the time and extra-space complexity without looking at the answer?
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