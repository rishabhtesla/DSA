package DSA.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * [27 / 28] - INSERT INTERVAL (LeetCode 57)
 * ============================================================================
 * 
 * PROBLEM:
 *   You are given an array of non-overlapping intervals sorted in ascending order
 *   by start time, and a newInterval = [start, end].
 *   Insert newInterval into intervals such that intervals is still sorted and
 *   non-overlapping (merging if necessary).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Because the input is ALREADY sorted and non-overlapping, we do NOT need 
 *     O(n log n) sorting! We can solve this in a single O(n) pass.
 *   - The array splits cleanly into 3 chronological phases:
 *     Phase 1 (Before): All intervals that finish before `newInterval` starts:
 *                       `intervals[i][1] < newInterval[0]` -> Add directly.
 *     Phase 2 (Overlap): Intervals that overlap with `newInterval`:
 *                        `intervals[i][0] <= newInterval[1]` -> Merge together:
 *                        `newInterval[0] = Math.min(newInterval[0], intervals[i][0])`
 *                        `newInterval[1] = Math.max(newInterval[1], intervals[i][1])`
 *                        Then add merged `newInterval`.
 *     Phase 3 (After):  All remaining intervals that start after `newInterval` ends -> Add directly.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass through intervals.
 *   - Space: O(1) - Auxiliary space (ignoring the output list).
 *
 *
 * EXAMPLE:
 *   Insert newInterval=[4,8] into [[1,2],[3,5],[6,7],[8,10],[12,16]]; expected
 *   [[1,2],[3,10],[12,16]].
 *
 * VISUAL DRY RUN:
 *   [1,2] is before 4, emit it. [3,5] overlaps -> merge new=[3,8]; [6,7] -> [3,8];
 *   [8,10] touches/overlaps -> [3,10]. [12,16] is after, emit [3,10], then [12,16].
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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