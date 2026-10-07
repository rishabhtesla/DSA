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