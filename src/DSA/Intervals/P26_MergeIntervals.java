package DSA.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * [26 / 28] - MERGE INTERVALS (LeetCode 56)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array of intervals where intervals[i] = [start_i, end_i], merge all
 *   overlapping intervals, and return an array of the non-overlapping intervals
 *   that cover all the intervals in the input.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Sorting by Start Time is Key:
 *     Once intervals are sorted by start time: `start_0 <= start_1 <= ... <= start_n`.
 *     Two adjacent intervals `A = [start_A, end_A]` and `B = [start_B, end_B]` overlap 
 *     if and only if: `start_B <= end_A`.
 *   - Strategy:
 *     1. Sort intervals by start time.
 *     2. Add the first interval to `merged`.
 *     3. For each subsequent interval `curr`:
 *        - If `curr[0] <= last[1]`, they overlap -> expand `last[1] = Math.max(last[1], curr[1])`.
 *        - Otherwise, no overlap -> add `curr` to `merged`.
 *
 * COMPLEXITY:
 *   - Time:  O(n log n) - Dominated by sorting the intervals.
 *   - Space: O(log n) to O(n) - Sorting recursion stack / output list.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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