package DSA.agoda;

import java.util.Arrays;

/**
 * ============================================================================
 * [AG-01 / 06] - AIRPLANE / DEFENSE SHOOTDOWN (Agoda OA Frequent / LC 1921)
 * ============================================================================
 *
 /*
 * PROBLEM: Defense Grid
 *
 * You are operating an automated defense turret protecting a strategic military base.
 * Radar has detected n incoming enemy aircraft, each flying directly toward the base
 * at a constant speed.
 *
 * You are given two 0-indexed integer arrays of length n:
 *   - dist[i]  : initial distance (in km) of the i-th aircraft from the base.
 *   - speed[i] : constant speed (in km/s) of the i-th aircraft.
 *
 * The turret is fully charged at t = 0 seconds and can vaporize at most 1 aircraft
 * per shot. Once fired, the weapon requires exactly 1 second to recharge, meaning
 * you can fire at timestamps t = 0, 1, 2, ..., n - 1.
 *
 * Aircraft move continuously. If any remaining aircraft reaches the base (distance <= 0)
 * at the exact moment you are ready to fire—or at any time prior—the base is breached
 * and you lose immediately.
 *
 * Return the maximum number of aircraft you can eliminate before losing (or n if all
 * aircraft can be eliminated).
 *
 * CONSTRAINTS:
 *   - 1 <= n <= 10^5
 *   - 1 <= dist[i], speed[i] <= 10^5
 *
 * EXAMPLES:
 *
 *   Example 1:
 *     Input:  dist = [1, 3, 4], speed = [1, 1, 1]
 *     Output: 3
 *     Explanation: Arrival times are [1, 3, 4]. Eliminate plane 0 at t = 0,
 *                  plane 1 at t = 1, and plane 2 at t = 2.
 *
 *   Example 2:
 *     Input:  dist = [1, 1, 2, 3], speed = [1, 1, 1, 1]
 *     Output: 1
 *     Explanation: Arrival times are [1, 1, 2, 3]. Eliminate plane 0 at t = 0.
 *                  At t = 1, plane 1 reaches the base at the exact moment you
 *                  are ready to fire, causing a loss.
 *
 *   Example 3:
 *     Input:  dist = [3, 2, 4], speed = [5, 3, 2]
 *     Output: 1
 *     Explanation: Arrival times are [0.6, 0.67, 2.0]. Eliminate one plane at t = 0;
 *                  the other fast plane breaches the base before t = 1.
 *
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - The bottleneck is NOT how far a plane starts, nor how fast it moves individually,
 *     but its ARRIVAL TIME AT BASE:
 *     arrival_time[i] = dist[i] / (double) speed[i].
 *   - Greedy Choice Property:
 *     To survive as long as possible, you MUST eliminate whichever airplane arrives
 *     earliest (Earliest Deadline First).
 *   - Sort arrival times ascending.
 *   - At minute t (where t = 0, 1, 2, ...):
 *     If arrivalTimes[t] <= t, that plane has arrived before or precisely when you 
 *     can shoot it -> Game over, return t.
 *     Otherwise, you shoot it down and proceed to minute t + 1.
 *
 * COMPLEXITY:
 *   - Time:  O(n log n) - Dominated by sorting arrival times (or O(n) via bucket sort).
 *   - Space: O(n)       - Array storing arrival times.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
/**
 * VISUAL DRY RUN: for dist=[1,3,4], speed=[1,1,1], arrival times are
 * [1,3,4]. At t=0, 1>0 so shoot plane 1; at t=1, 3>1 so shoot plane 3;
 * at t=2, 4>2 so shoot plane 4 and return 3. The invariant is that all
 * earlier sorted deadlines have already been handled.
 */
public class AG01_AirplaneShootdown {

    public static int maxPlanesShot(int[] dist, int[] speed) {
        int n = dist.length;
        double[] arrivalTimes = new double[n];

        for (int i = 0; i < n; i++) {
            arrivalTimes[i] = (double) dist[i] / speed[i];
        }

        // Greedy strategy: always eliminate the closest-to-landing plane
        Arrays.sort(arrivalTimes);

        for (int t = 0; t < n; t++) {
            // If the plane arrives at or before minute t, we lose
            if (arrivalTimes[t] <= t) {
                return t;
            }
        }

        return n; // All planes eliminated
    }

    public static void main(String[] args) {
        int[] dist1 = {1, 3, 4};
        int[] speed1 = {1, 1, 1};
        System.out.println("AG01 Output (Test 1): " + maxPlanesShot(dist1, speed1)); // Expected: 3

        int[] dist2 = {1, 1, 2, 3};
        int[] speed2 = {1, 1, 1, 1};
        System.out.println("AG01 Output (Test 2): " + maxPlanesShot(dist2, speed2)); // Expected: 1 (second plane hits at t=1)

        int[] dist3 = {3, 2, 4};
        int[] speed3 = {5, 3, 2};
        System.out.println("AG01 Output (Test 3): " + maxPlanesShot(dist3, speed3)); // Expected: 1
    }
}