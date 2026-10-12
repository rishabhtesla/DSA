package DSA.Intervals;

import java.util.Arrays;

/**
 * ============================================================================
 * [28 / 28] - MINIMUM NUMBER OF ARROWS TO BURST BALLOONS (LeetCode 452)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * There are spherical balloons taped to a flat wall. Each balloon is represented
 * by a 2D integer array `points` where `points[i] = [x_start, x_end]`.
 * An arrow shot vertically at position `x` bursts all balloons where `x_start <= x <= x_end`.
 * Find the minimum number of arrows required to burst all balloons.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Classic Interval Scheduling / Greedy Choice Approach:
 *    - **The Core Insight:** Sort the balloons by their **end coordinate** (`x_end`), NOT their start coordinate.
 *    - Why sort by end? The balloon that finishes earliest forces our hand: we *must* burst it at or before
 *      its end coordinate. To burst as many overlapping balloons as possible with that same single shot,
 *      we shoot greedily at the **very latest possible valid point**: `currentBalloon[1]`.
 *    - **Step 1:** Sort intervals by endpoint using `Integer.compare(a[1], b[1])`.
 *    - **Step 2:** Initialize `arrows = 1` and set our first `arrowPos = points[0][1]`.
 *    - **Step 3:** Iterate through subsequent balloons:
 *      - If `nextBalloon[0] <= arrowPos`, this balloon overlaps our current shot and bursts for free.
 *      - If `nextBalloon[0] > arrowPos`, it starts strictly after our shot position -> we need a new arrow.
 *        Increment `arrows++` and update `arrowPos = nextBalloon[1]`.
 *
 * *Interview Pro-Tip on Comparator Underflow/Overflow:*
 * Never use `(a, b) -> a[1] - b[1]`! If points contain extreme values like `-2147483648` and `2147483647`,
 * integer subtraction will overflow or underflow. Always use `Integer.compare(a[1], b[1])`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n log n) - Dominated by sorting the balloons array by endpoint.
 * - Space: O(log n) - Auxiliary space for the sorting recursion stack.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `points = [[10, 16], [2, 8], [1, 6], [7, 12]]` -> 2 (shots at x=6 and x=12)
 * - `points = [[1, 2], [3, 4], [5, 6], [7, 8]]`     -> 4
 *
 * VISUAL DRY RUN (for Test 1):
 *   - Sorted by endpoints: `[1, 6], [2, 8], [7, 12], [10, 16]`
 *   - Start with `arrowPos = 6` (bursts `[1, 6]` and `[2, 8]` since their starts `1` and `2` are `<= 6`).
 *   - Next balloon is `[7, 12]`: Its start (`7`) is `> 6`, so it misses our arrow!
 *     -> Shoot new arrow: `arrows = 2`, `arrowPos = 12` (bursts `[7, 12]` and `[10, 16]`).
 *   - Total arrows = 2.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is sorting by end coordinate superior to sorting by start coordinate for this problem?
 *    (Sorting by end coordinate guarantees we pick the earliest mandatory boundary constraint,
 *    allowing us to place our greedy shot at the maximum possible overlap horizon.)
 * 2. Why is `Integer.compare()` mandatory instead of subtraction inside custom comparators?
 *    (Subtraction causes integer overflow/underflow when comparing large negative and positive coordinate boundaries.)
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