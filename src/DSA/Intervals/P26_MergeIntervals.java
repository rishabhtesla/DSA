package DSA.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * [26 / 28] - MERGE INTERVALS (LeetCode 56)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of intervals where `intervals[i] = [start_i, end_i]`, merge all
 * overlapping intervals, and return an array of the non-overlapping intervals
 * that cover all the intervals in the input.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Brute Force Approach:
 *    - Compare every interval against every other interval to detect overlaps,
 *      which requires multiple passes and complex cascading adjustments. (Inefficient: O(n^2)).
 *
 * 2. Optimal Sorting & Tracking Approach (O(n log n) Time | O(log n) to O(n) Space):
 *    - **The Core Insight:** Once intervals are sorted by their **start times** (`start_0 <= start_1 <= ... <= start_n`),
 *      any overlapping intervals will be adjacent to each other.
 *    - Two adjacent intervals `A = [start_A, end_A]` and `B = [start_B, end_B]` overlap
 *      if and only if: `start_B <= end_A`.
 *    - **Step 1:** Sort intervals by start time using `Arrays.sort()`.
 *    - **Step 2:** Maintain a `currentInterval` reference (initialized to the first interval).
 *    - **Step 3:** Iterate through the remaining intervals:
 *      - If `nextStart <= currentEnd`, an overlap occurs -> expand the end point: `currentInterval[1] = Math.max(currentEnd, nextEnd)`.
 *      - Otherwise, no overlap -> update `currentInterval` to the new interval and add it to our `merged` list.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n log n) - Dominated by the sorting step of the intervals array.
 * - Space: O(log n) to O(n) - Auxiliary space required for sorting recursion stack / output list conversion.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `intervals = [[1, 3], [2, 6], [8, 10], [15, 18]]` -> `[[1, 6], [8, 10], [15, 18]]`
 *
 * VISUAL DRY RUN (for `[[1, 3], [2, 6], [8, 10], [15, 18]]`):
 *   - Sorted (already sorted by start times).
 *   - Start with `currentInterval = [1, 3]`.
 *   - Next is `[2, 6]`: Since `2 <= 3` (overlap!), merge by updating end: `currentInterval[1] = Math.max(3, 6) = 6` (`[1, 6]`).
 *   - Next is `[8, 10]`: Since `8 > 6` (no overlap), add `[1, 6]` to results and shift active interval to `[8, 10]`.
 *   - Next is `[15, 18]`: Since `15 > 10` (no overlap), add `[8, 10]` to results and shift active interval to `[15, 18]`.
 *   - Loop ends, adds final interval `[15, 18]`. Returns `[[1, 6], [8, 10], [15, 18]]`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is sorting by start time critical for this algorithm?
 *    (It guarantees that potential overlapping intervals are positioned contiguously,
 *    allowing us to process them in a single linear sweep from left to right.)
 * 2. Why do we use `Math.max(currentEnd, nextEnd)` when merging overlapping intervals?
 *    (Because one interval could be entirely nested inside another (e.g., `[1, 5]` and `[2, 4]`).
 *    Taking the max ensures we don't accidentally truncate a longer outer interval with a shorter nested one.)
 */
public class P26_MergeIntervals {

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Sort by start times
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();
        int[] currentInterval = intervals[0];
        merged.add(currentInterval);

        for (int i = 1; i < intervals.length; i++) {
            int currentEnd = currentInterval[1];
            int nextStart = intervals[i][0];
            int nextEnd = intervals[i][1];

            // Overlap detected: merge by extending the end point
            if (nextStart <= currentEnd) {
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                // Disjoint interval: push to list and shift active interval
                currentInterval = intervals[i];
                merged.add(currentInterval);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] result = merge(intervals);
        System.out.print("P26 Output: ");
        for (int[] interval : result) {
            System.out.print(Arrays.toString(interval) + " ");
        }
        System.out.println();
        // Expected: [1, 6] [8, 10] [15, 18]
    }
}