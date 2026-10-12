package DSA.Intervals;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * [25 / 28] - SUMMARY RANGES (LeetCode 228)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a sorted unique integer array `nums`, return the smallest sorted list of
 * ranges that cover all the numbers in the array exactly.
 * Each range `[a, b]` should be formatted as:
 * - `"a->b"` if `a != b`
 * - `"a"`    if `a == b`
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Two-Pointer / Sliding Window (Anchor & Explorer) Approach:
 *    - **Step 1:** Set an anchor `start = nums[i]` at the beginning of each potential range.
 *    - **Step 2:** Look ahead to find consecutive elements using the condition `nums[i] + 1 == nums[i + 1]`.
 *      *Interview Pro-Tip on Integer Overflow:* Avoid writing `nums[i + 1] - nums[i] == 1`, because subtraction
 *      can overflow for extreme values like `Integer.MAX_VALUE` or negative numbers. Always use addition (`nums[i] + 1 == nums[i + 1]`).
 *    - **Step 3:** When the consecutive chain breaks, format and append the range:
 *      - If `start == nums[i]`, append single value string `"start"`.
 *      - Else, append range string `"start->nums[i]"`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass where the inner lookahead loop advances index `i` through each element at most once.
 * - Space: O(1) - Constant auxiliary memory space (ignoring the output result list).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `nums = [0, 1, 2, 4, 5, 7]`         -> `["0->2", "4->5", "7"]`
 * - `nums = [0, 2, 3, 4, 6, 8, 9]`     -> `["0", "2->4", "6", "8->9"]`
 *
 * VISUAL DRY RUN (for `[0, 1, 2, 4, 5, 7]`):
 *   - Start range at `0`. Lookahead finds `1` and `2` are consecutive, so `i` advances to index `2`.
 *     Since `start (0) != nums[i] (2)`, emits `"0->2"`.
 *   - Next range starts at `4`. Lookahead finds `5` is consecutive, advances `i` to index `4`. Emits `"4->5"`.
 *   - Next range starts at `7`. No successor exists (`7 + 1 != ...`), emits single value `"7"`.
 *   - Returns `["0->2", "4->5", "7"]`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is addition preferred over subtraction when checking for consecutive numbers?
 *    (Addition (`nums[i] + 1 == nums[i + 1]`) avoids potential integer overflow errors that can occur when subtracting negative and positive bounds near `Integer.MIN_VALUE` or `Integer.MAX_VALUE`.)
 * 2. What edge cases are handled automatically?
 *    (Empty arrays return an empty list immediately, and single-element arrays correctly bypass the lookahead loop and format as a single string.)
 */
public class P25_SummaryRanges {

    public static List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        int i = 0;
        int n = nums.length;

        while (i < n) {
            int start = nums[i];

            // Scan consecutive numbers using addition to prevent integer overflow
            while (i + 1 < n && nums[i] + 1 == nums[i + 1]) {
                i++;
            }

            // Append the formatted range based on whether it's a single element or a span
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