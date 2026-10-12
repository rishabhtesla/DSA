package DSA.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * [27 / 28] - INSERT INTERVAL (LeetCode 57)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * You are given an array of non-overlapping intervals sorted in ascending order
 * by start time, and a `newInterval = [start, end]`.
 * Insert `newInterval` into `intervals` such that the resulting array is still sorted
 * and non-overlapping (merging if necessary).
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Append & Sort Approach (O(n log n) Time):
 *    - Append the new interval to the array and re-sort all intervals.
 *    - Inefficient because it completely ignores the fact that the input array is **already sorted**.
 *
 * 2. Optimal Three-Phase Linear Scan Approach (O(n) Time | O(n) Space):
 *    - **The Core Insight:** Since the input is already sorted, we can partition the process
 *      cleanly into **3 chronological phases** without any sorting overhead:
 *    - **Phase 1 (Before):** Add all intervals that finish strictly before `newInterval` starts
 *      (`intervals[i][1] < newInterval[0]`) directly to the result.
 *    - **Phase 2 (Overlap):** Merge all overlapping intervals with `newInterval`
 *      (`intervals[i][0] <= newInterval[1]`) by updating bounds:
 *      - `newInterval[0] = Math.min(newInterval[0], intervals[i][0])`
 *      - `newInterval[1] = Math.max(newInterval[1], intervals[i][1])`
 *      Once all overlaps are absorbed, add the final merged `newInterval`.
 *    - **Phase 3 (After):** Add all remaining intervals that start after `newInterval` ends.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear pass visiting each interval at most once.
 * - Space: O(n) - Auxiliary space required to store the resulting list of intervals.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE: `intervals = [[1, 2], [3, 5], [6, 7], [8, 10], [12, 16]]`, `newInterval = [4, 8]`
 *
 * VISUAL DRY RUN:
 *   - Phase 1: `[1, 2]` finishes before 4 starts -> Added to result.
 *   - Phase 2: `[3, 5]` overlaps (`3 <= 8`) -> `newInterval` expands to `[3, 8]`.
 *              `[6, 7]` overlaps (`6 <= 8`) -> `newInterval` expands to `[3, 8]`.
 *              `[8, 10]` overlaps (`8 <= 8` boundary touch) -> `newInterval` expands to `[3, 10]`.
 *              Merged `[3, 10]` added to result.
 *   - Phase 3: `[12, 16]` starts after 10 -> Added directly.
 *   - Returns `[[1, 2], [3, 10], [12, 16]]`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is this solution more optimal than standard interval merging?
 *    (It leverages the pre-sorted invariant of the input array, dropping the time complexity from O(n log n) down to O(n).)
 * 2. How does the condition `intervals[i][0] <= newInterval[1]` correctly capture all overlaps?
 *    (It checks whether an existing interval's start time occurs on or before the new interval's end time,
 *    which, combined with Phase 1 filtering, guarantees a complete overlap intersection.)
 */
public class P27_InsertInterval {

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // Phase 1: Add all intervals ending before newInterval starts
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2: Merge all overlapping intervals into newInterval
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);

        // Phase 3: Add all remaining intervals that start after newInterval ends
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        int[][] intervals = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval = {4, 8};
        int[][] result = insert(intervals, newInterval);

        System.out.print("P27 Output: ");
        for (int[] interval : result) {
            System.out.print(Arrays.toString(interval) + " ");
        }
        System.out.println();
        // Expected: [1, 2] [3, 10] [12, 16]
    }
}