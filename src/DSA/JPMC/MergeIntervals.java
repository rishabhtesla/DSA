package DSA.JPMC; /**
 * PROBLEM: Merge Intervals
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, 
 * and return an array of the non-overlapping intervals that cover all the intervals in the input.
 * 
 * EXAMPLE:
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
 * 
 * LOGIC:
 * 1. Sort the intervals based on their starting times.
 * 2. Create a 'current' interval and add it to a result list.
 * 3. Iterate through the remaining intervals.
 * 4. If the next interval's start time is <= the 'current' interval's end time, they overlap.
 *    Merge them by updating the 'current' interval's end time to the max of both end times.
 * 5. If they don't overlap, update the 'current' interval to this new interval and add to result.
 * 
 * DRY RUN: [[1,3], [2,6], [8,10], [15,18]]
 * - cur = [1,3]. Next is [2,6]. Since 2 <= 3, they overlap. cur end becomes max(3, 6) = 6. cur = [1,6].
 * - Next is [8,10]. Since 8 > 6, no overlap. Add cur [1,6] to result, new cur = [8,10].
 * - Next is [15,18]. Since 15 > 10, no overlap. Add cur [8,10] to result, new cur = [15,18].
 * - Loop ends. Add final cur [15,18] to result.
 * 
 * COMPLEXITY: Time: O(N log N) for sorting. Space: O(N) to store the result.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Learning supplement: repeated pairwise merging can be O(n^2); sorting by
 * start then scanning is O(n log n) plus output space. Examples:
 * {@code [[1,3],[2,6],[8,10]] -> [[1,6],[8,10]]} and
 * {@code [[1,4],[4,5]] -> [[1,5]]}. Invariant: the result prefix is sorted
 * and fully merged. In the main scan [2,6] extends [1,3], then [8,10] starts
 * a new range. Ask whether touching endpoints overlap.
 */
public class MergeIntervals {
    public static int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) return intervals;
        
        // Sort by ascending starting point
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<int[]> result = new ArrayList<>();
        int[] currentInterval = intervals[0];
        result.add(currentInterval);
        
        for (int[] interval : intervals) {
            int currentEnd = currentInterval[1];
            int nextBegin = interval[0];
            int nextEnd = interval[1];
            
            if (currentEnd >= nextBegin) {
                // Overlapping intervals, update the end if needed
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                // Disjoint intervals, add the new interval to the list
                currentInterval = interval;
                result.add(currentInterval);
            }
        }
        
        // Convert List back to 2D array
        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] merged = merge(intervals);
        
        System.out.print("Merged Intervals: ");
        for (int[] interval : merged) {
            System.out.print(Arrays.toString(interval) + " ");
        }
        // Output: Merged Intervals: [1, 6] [8, 10] [15, 18] 
    }
}