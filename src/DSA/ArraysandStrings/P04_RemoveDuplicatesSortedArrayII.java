package DSA.ArraysandStrings;

import java.util.Arrays;

/**
 * ============================================================================
 * [04 / 24] - REMOVE DUPLICATES FROM SORTED ARRAY II (LeetCode 80)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a sorted integer array, remove duplicates in-place such that each
 *   unique element appears at most TWICE. Return k.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Universal pattern for "at most K duplicates":
 *     When deciding whether `nums[read]` can be accepted, compare it against the
 *     element placed `k` positions behind the write pointer: `nums[write - 2]`.
 *   - If `nums[read] == nums[write - 2]`, accepting it would make it the 3rd duplicate.
 *   - If `nums[read] != nums[write - 2]`, it is safe to keep.
 *   - Works cleanly without tracking explicit frequencies or edge cases.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass starting from index 2.
 *   - Space: O(1) - Constant auxiliary space.
 *
 *
 * EXAMPLE:
 *   Input:  nums = [1, 1, 1, 2, 2, 3]
 *   Output: k = 5, prefix = [1, 1, 2, 2, 3]
 *
 * VISUAL DRY RUN:
 *   The first two values are always allowed, so write = 2.
 *   From read = 2 onward, compare the candidate with nums[write - 2].
 *   If they are equal, the candidate would be a third copy.
 *
 *   - read=2, candidate=1, nums[write-2]=nums[0]=1
 *     Third 1 -> skip. write=2, valid prefix=[1,1].
 *   - read=3, candidate=2, nums[write-2]=nums[0]=1
 *     Safe -> nums[2]=2. write=3, prefix=[1,1,2].
 *   - read=4, candidate=2, nums[write-2]=nums[1]=1
 *     Safe -> nums[3]=2. write=4, prefix=[1,1,2,2].
 *   - read=5, candidate=3, nums[write-2]=nums[2]=2
 *     Safe -> nums[4]=3. write=5, prefix=[1,1,2,2,3].
 *
 *   Return write = 5. Only the first five positions are part of the answer.
 *
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P04_RemoveDuplicatesSortedArrayII {

    public static int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;

        int write = 2;
        for (int read = 2; read < nums.length; read++) {
            // Compare candidate against the element 2 slots back in our valid window
            if (nums[read] != nums[write - 2]) {
                nums[write] = nums[read];
                write++;
            }
        }

        return write;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = removeDuplicates(nums);
        System.out.println("P04 Output: k = " + k + ", prefix = " + Arrays.toString(Arrays.copyOf(nums, k)));
        // Expected: k = 5, prefix = [1, 1, 2, 2, 3]
    }
}