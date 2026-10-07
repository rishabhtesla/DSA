package DSA.Intervals;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * [25 / 28] - SUMMARY RANGES (LeetCode 228)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a sorted unique integer array nums, return the smallest sorted list of
 *   ranges that cover all the numbers in the array exactly.
 *   Each range [a,b] should be formatted as:
 *     - "a->b" if a != b
 *     - "a"    if a == b
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Two-Pointer / Sliding Window (Anchor & Explorer):
 *     1. Set an anchor `start = nums[i]`.
 *     2. Look ahead to find consecutive elements: `nums[i + 1] == nums[i] + 1`.
 *        Note on Integer Overflow: Avoid `nums[i + 1] - nums[i] == 1` because subtraction 
 *        can overflow for `Integer.MAX_VALUE` and negative numbers. Use `nums[i] + 1 == nums[i + 1]`.
 *     3. When the consecutive chain breaks:
 *        - If `start == nums[i]`, append `"start"`.
 *        - Else append `"start->nums[i]"`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass where inner loop advances index `i`.
 *   - Space: O(1) - Auxiliary space (ignoring the output list).
 */
public class P25_SummaryRanges {

    public static List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        int i = 0;
        int n = nums.length;

        while (i < n) {
            int start = nums[i];

            // Scan consecutive numbers
            while (i + 1 < n && nums[i] + 1 == nums[i + 1]) {
                i++;
            }

            if (start == nums[i]) {
                result.add(String.valueOf(start));
            } else {
                result.add(start + "->" + nums[i]);
            }

            i++; // Advance to the start of the next range
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {0, 1, 2, 4, 5, 7};
        int[] nums2 = {0, 2, 3, 4, 6, 8, 9};
        System.out.println("P25 Output (Test 1): " + summaryRanges(nums1)); 
        // Expected: ["0->2", "4->5", "7"]
        System.out.println("P25 Output (Test 2): " + summaryRanges(nums2)); 
        // Expected: ["0", "2->4", "6", "8->9"]
    }
}