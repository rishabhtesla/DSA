package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION 5: Merge Overlapping Intervals
 * ============================================================================
 *
 * Problem Statement:
 * Given a collection of trading intervals where intervals[i] = [start_i, end_i],
 * merge all overlapping intervals and return an array of non-overlapping intervals
 * that cover all intervals in the input.
 *
 * Intervals touching at boundaries (e.g., [1, 4] and [4, 6]) are considered
 * overlapping and must be merged into [1, 6].
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, the number of intervals.
 * - Next n lines: Each line contains two space-separated integers representing
 *   start_i and end_i.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - 0 <= start_i <= end_i <= 10^9
 *
 * Example:
 * Input:
 *   4
 *   1 3
 *   2 6
 *   8 10
 *   15 18
 * Output:
 *   1 6
 *   8 10
 *   15 18
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Sorting:
 *    Sort all intervals based on their start time in ascending order.
 *    If two intervals have the same start time, their end times do not affect
 *    correctness since we take Math.max(current.end, next.end).
 *
 * 2. Linear Scan & Merge:
 *    - Initialize the merged list with the first interval as `current`.
 *    - Iterate through the remaining intervals:
 *        a. If next.start <= current.end:
 *           Overlapping occurs. Extend current interval:
 *             current.end = Math.max(current.end, next.end)
 *        b. Else (next.start > current.end):
 *           No overlap. Push `next` into the merged list and set `current = next`.
 *
 * Complexity:
 * - Time: O(n log n) dominated by sorting.
 * - Space: O(n) to store the merged intervals.
 *
 * ============================================================================
 * DRY RUN (intervals = [[1, 3], [2, 6], [8, 10], [15, 18]]):
 * ============================================================================
 * Sorted intervals: [[1, 3], [2, 6], [8, 10], [15, 18]]
 * Initial merged: [[1, 3]], current = [1, 3]
 *
 * Iteration 1: next = [2, 6]
 * - next.start (2) <= current.end (3)? Yes (Overlap detected)
 * - current.end = max(3, 6) = 6
 * - State: [[1, 6]]
 *
 * Iteration 2: next = [8, 10]
 * - next.start (8) <= current.end (6)? No (Disjoint interval)
 * - Append [8, 10] to merged
 * - current = [8, 10]
 * - State: [[1, 6], [8, 10]]
 *
 * Iteration 3: next = [15, 18]
 * - next.start (15) <= current.end (10)? No (Disjoint interval)
 * - Append [15, 18] to merged
 * - current = [15, 18]
 * - State: [[1, 6], [8, 10], [15, 18]]
 *
 * Output:
 * 1 6
 * 8 10
 * 15 18
 * ============================================================================
 */
public class SolutionMergeIntervals {

    public static class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1) {
            return intervals;
        }

        Collections.sort(intervals, Comparator.comparingInt(a -> a.start));

        List<Interval> merged = new ArrayList<>();
        Interval current = intervals.get(0);
        merged.add(current);

        for (int i = 1; i < intervals.size(); i++) {
            Interval next = intervals.get(i);
            if (next.start <= current.end) {
                current.end = Math.max(current.end, next.end);
            } else {
                current = next;
                merged.add(current);
            }
        }

        return merged;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Interval> intervals = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            String[] parts = bufferedReader.readLine().trim().split("\\s+");
            intervals.add(new Interval(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }

        List<Interval> result = merge(intervals);
        for (Interval inv : result) {
            System.out.println(inv.start + " " + inv.end);
        }

        bufferedReader.close();
    }
}