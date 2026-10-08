package DSA.Intervals;

import java.util.Arrays;

/**
 * ============================================================================
 * [28 / 28] - MINIMUM NUMBER OF ARROWS TO BURST BALLOONS (LeetCode 452)
 * ============================================================================
 * 
 * PROBLEM:
 *   There are spherical balloons taped to a flat wall. Each balloon is represented
 *   by a 2D integer array points where points[i] = [x_start, x_end].
 *   An arrow shot vertically at x bursts all balloons where x_start <= x <= x_end.
 *   Find the minimum number of arrows required to burst all balloons.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Classic Interval Scheduling / Greedy Choice:
 *     Sort by END coordinate (`x_end`), NOT start coordinate.
 *   - Why sort by end?
 *     The balloon that finishes earliest forces our hand: we MUST burst it before
 *     or at its end coordinate. To burst as many other balloons as possible with
 *     that same arrow, shoot greedily at the very latest possible point: `currentBalloon[1]`.
 *   - For subsequent balloons:
 *     - If `nextBalloon[0] <= arrowPos`, this balloon overlaps our shot and bursts for free.
 *     - If `nextBalloon[0] > arrowPos`, it starts strictly after our shot -> we need a new arrow.
 *       Shoot at `nextBalloon[1]`.
 *   - Comparator Overflow Caveat:
 *     Do NOT use `(a, b) -> a[1] - b[1]`! If points contain values like `-2147483648` and `2147483647`,
 *     integer subtraction will underflow/overflow. Always use `Integer.compare(a[1], b[1])`.
 *
 * COMPLEXITY:
 *   - Time:  O(n log n) - Dominated by sorting balloons by endpoint.
 *   - Space: O(log n) - Sorting stack.
 *
 *
 * EXAMPLE:
 *   The first main points are [[10,16],[2,8],[1,6],[7,12]]; expected two arrows at x=6 and x=12.
 *
 * VISUAL DRY RUN:
 *   Sort by end: [1,6],[2,8],[7,12],[10,16]. Shoot at 6 (bursts first two); [7,12]
 *   starts after 6, so shoot again at 12 (also bursts [10,16]). arrows=2.
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P28_MinimumNumberOfArrowsToBurstBalloons {

    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) return 0;

        // Sort by end coordinate. Use Integer.compare to prevent arithmetic overflow!
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));

        int arrows = 1;
        int arrowPos = points[0][1];

        for (int i = 1; i < points.length; i++) {
            // If the current balloon starts after the last arrow position, shoot a new arrow
            if (points[i][0] > arrowPos) {
                arrows++;
                arrowPos = points[i][1];
            }
        }

        return arrows;
    }

    public static void main(String[] args) {
        int[][] points1 = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
        int[][] points2 = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        int[][] points3 = {{-2147483646, -2147483645}, {2147483646, 2147483647}};

        System.out.println("P28 Output (Test 1): " + findMinArrowShots(points1)); // Expected: 2 (at x=6, x=12)
        System.out.println("P28 Output (Test 2): " + findMinArrowShots(points2)); // Expected: 4
        System.out.println("P28 Output (Test 3): " + findMinArrowShots(points3)); // Expected: 2 (tests integer overflow)
    }
}