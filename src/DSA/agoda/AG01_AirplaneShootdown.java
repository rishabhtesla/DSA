package DSA.agoda;

import java.util.Arrays;

/**
 * ============================================================================
 * [AG-01 / 06] - AIRPLANE / DEFENSE SHOOTDOWN (Agoda OA Frequent / LC 1921)
 * ============================================================================
 * 
 * PROBLEM:
 *   You have n enemy airplanes approaching a defense base. You are given two arrays:
 *   dist[] where dist[i] is the initial distance of the ith plane, and speed[]
 *   where speed[i] is its constant speed.
 *   Each plane reaches the base in time t_i = dist[i] / speed[i] seconds.
 *   Your defense weapon can vaporize exactly 1 plane at t = 0, and recharge takes 
 *   exactly 1 second (so you can fire at t = 0, 1, 2, ...).
 *   Planes move continuously. If an airplane reaches the base at or before time t,
 *   you lose. Find the maximum number of planes you can eliminate before losing.
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