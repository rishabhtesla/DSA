package DSA.BNP;

import java.util.*;

/**
 * ============================================================================
 * QUESTION 6: Target Indices After Sorting Array (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * You are given a 0-indexed integer array `nums` and a target element `target`.
 * A target index is an index `i` such that `nums[i] == target` after sorting
 * `nums` in non-decreasing order.
 * Return a list of the target indices of `nums` after sorting `nums` in
 * non-decreasing order. If there are no target indices, return an empty list.
 * The returned list must be sorted in increasing order.
 *
 * INPUT FORMAT:
 * - An integer array `nums`
 * - An integer `target`
 *
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10^5
 * - 1 <= nums[i], target <= 10^5
 *
 * SAMPLE INPUT 0:
 *   nums = [1, 2, 5, 2, 3], target = 2
 *
 * SAMPLE OUTPUT 0:
 *   [1, 2]
 *
 * EXPLANATION 0:
 *   After sorting, nums is [1, 2, 2, 3, 5].
 *   The indices where nums[i] == 2 are 1 and 2.
 *
 * SAMPLE INPUT 1:
 *   nums = [1, 2, 5, 2, 3], target = 4
 *
 * SAMPLE OUTPUT 1:
 *   []
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (nums = [1, 2, 5, 2, 3], target = 2):
 * ----------------------------------------------------------------------------
 * Optimal O(N) Counting Approach (No O(N log N) sorting needed!):
 * In a sorted array, all elements strictly LESS than `target` appear before
 * `target`, and all elements EQUAL to `target` appear consecutively right after.
 *
 * Single pass through nums = [1, 2, 5, 2, 3] with target = 2:
 * - num = 1: 1 < 2  -> lessCount = 1, equalCount = 0
 * - num = 2: 2 == 2 -> lessCount = 1, equalCount = 1
 * - num = 5: 5 > 2  -> lessCount = 1, equalCount = 1
 * - num = 2: 2 == 2 -> lessCount = 1, equalCount = 2
 * - num = 3: 3 > 2  -> lessCount = 1, equalCount = 2
 *
 * Result construction:
 * Since 1 element is smaller than 2, the first '2' lands at index 1 (lessCount).
 * Since there are 2 occurrences of '2' (equalCount = 2), they occupy indices:
 *   i = 0 -> lessCount + 0 = 1
 *   i = 1 -> lessCount + 1 = 2
 * Result = [1, 2].
 *
 * Time Complexity:  O(N) (Beats O(N log N) sorting on large hidden inputs)
 * Space Complexity: O(1) extra space (excluding output list)
 * ============================================================================
 */
public class TargetIndices {

    public static List<Integer> targetIndices(int[] nums, int target) {
        int lessCount = 0;  // Count of elements strictly smaller than target
        int equalCount = 0; // Count of elements equal to target

        for (int num : nums) {
            if (num < target) {
                lessCount++;
            } else if (num == target) {
                equalCount++;
            }
        }

        // Target elements will occupy indices from `lessCount` to `lessCount + equalCount - 1`
        List<Integer> result = new ArrayList<>(equalCount);
        for (int i = 0; i < equalCount; i++) {
            result.add(lessCount + i);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums0 = {1, 2, 5, 2, 3};
        System.out.println("Sample 0 Input:  nums = " + Arrays.toString(nums0) + ", target = 2");
        System.out.println("Sample 0 Output: " + targetIndices(nums0, 2));
        // Expected: [1, 2]

        System.out.println("\nSample 1 Input:  nums = " + Arrays.toString(nums0) + ", target = 4");
        System.out.println("Sample 1 Output: " + targetIndices(nums0, 4));
        // Expected: []
    }
}