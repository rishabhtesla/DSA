package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Minimum Execution Servers / Meeting Rooms II
 * ============================================================================
 *
 * Problem Statement:
 * A distributed computation engine processes background reconciliation jobs.
 * Each job has a scheduled start time and end time: [startTime, endTime].
 *
 * If a job ends at time T, that same server can immediately begin processing another
 * job that starts at time T.
 *
 * Given an array of job time intervals, determine the minimum number of servers
 * required to execute all jobs concurrently without any scheduling conflicts.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, the number of jobs.
 * - Next n lines: Two space-separated integers startTime and endTime.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - 0 <= startTime < endTime <= 10^9
 *
 * Example:
 * Input:
 *   3
 *   0 30
 *   5 10
 *   15 20
 * Output:
 *   2
 *
 * Explanation:
 * - Job 1: [0, 30] runs on Server 1.
 * - Job 2: [5, 10] overlaps with Job 1 -> needs Server 2.
 * - Job 3: [15, 20] begins after Job 2 finishes (10 <= 15) -> reuses Server 2.
 * Total servers needed = 2.
 *
 * ============================================================================
 * LOGIC & APPROACH (Sweep-Line Event Sorting):
 * ============================================================================
 * 1. Event Modeling:
 *    - Each start time represents an incoming demand of +1 server.
 *    - Each end time represents an expiring demand of -1 server.
 *
 * 2. Tie-Breaking Rule:
 *    If a job starts at the exact same moment another job ends (start == end),
 *    the departing job must release its server BEFORE the arriving job claims it.
 *    Therefore, sort end events before start events on identical timestamps.
 *
 * 3. Sweep-Line Accumulator:
 *    Iterate through sorted events while tracking `currentActiveServers` and
 *    updating `peakServers = max(peakServers, currentActiveServers)`.
 *
 * Complexity:
 * - Time: O(n log n) to sort 2n events.
 * - Space: O(n) to store events.
 *
 * ============================================================================
 * DRY RUN (Intervals = [[0, 30], [5, 10], [15, 20]]):
 * ============================================================================
 * Events: (0, +1), (30, -1), (5, +1), (10, -1), (15, +1), (20, -1)
 * Sorted:
 * 1. Time 0:  Start (+1) -> active = 1, peak = 1
 * 2. Time 5:  Start (+1) -> active = 2, peak = 2
 * 3. Time 10: End   (-1) -> active = 1, peak = 2
 * 4. Time 15: Start (+1) -> active = 2, peak = 2
 * 5. Time 20: End   (-1) -> active = 1, peak = 2
 * 6. Time 30: End   (-1) -> active = 0, peak = 2
 *
 * Final Result: 2
 * ============================================================================
 */
public class SolutionMinServersNeeded {

    static class Event implements Comparable<Event> {
        int time;
        int delta; // +1 for job start, -1 for job end

        Event(int time, int delta) {
            this.time = time;
            this.delta = delta;
        }

        @Override
        public int compareTo(Event other) {
            if (this.time != other.time) {
                return Integer.compare(this.time, other.time);
            }
            // End events (-1) must be processed before start events (+1) on ties
            return Integer.compare(this.delta, other.delta);
        }
    }

    public static int minMeetingRooms(List<int[]> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return 0;
        }

        List<Event> events = new ArrayList<>(intervals.size() * 2);

        for (int[] interval : intervals) {
            events.add(new Event(interval[0], 1));  // Server claimed
            events.add(new Event(interval[1], -1)); // Server released
        }

        Collections.sort(events);

        int currentActive = 0;
        int peakRequired = 0;

        for (Event event : events) {
            currentActive += event.delta;
            if (currentActive > peakRequired) {
                peakRequired = currentActive;
            }
        }

        return peakRequired;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<int[]> intervals = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            String[] parts = bufferedReader.readLine().trim().split("\\s+");
            intervals.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
        }

        int result = minMeetingRooms(intervals);
        System.out.println(result);

        bufferedReader.close();
    }
}