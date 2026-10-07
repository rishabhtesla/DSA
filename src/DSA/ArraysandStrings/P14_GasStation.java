package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [14 / 24] - GAS STATION (LeetCode 134)
 * ============================================================================
 * 
 * PROBLEM:
 *   There are n gas stations along a circular route. gas[i] is gas available at i,
 *   cost[i] is gas needed to travel to (i + 1). Return the starting gas station's
 *   index if you can travel clockwise around the circuit once, or -1 if impossible.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Global Condition: If sum(gas) < sum(cost), it is mathematically impossible
 *     to complete a circuit from any station -> return -1 immediately.
 *   - Greedy Pruning:
 *     Suppose we start at station A and run out of gas before station B (tank < 0).
 *     Can any station between A and B be a valid starting point?
 *     NO. Any station between A and B already contributed a non-negative or neutral
 *     amount of gas to reach that point. If starting with a surplus from A failed,
 *     starting empty from any station in between will fail even earlier.
 *   - Therefore, whenever `currentTank < 0`, reset `startStation = i + 1` and `currentTank = 0`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass.
 *   - Space: O(1) - Constant tracking variables.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P14_GasStation {

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int totalTank = 0;
        int currentTank = 0;
        int startStation = 0;

        for (int i = 0; i < gas.length; i++) {
            int net = gas[i] - cost[i];
            totalTank += net;
            currentTank += net;

            // If we run out of fuel, no station from startStation to i can be the start
            if (currentTank < 0) {
                startStation = i + 1;
                currentTank = 0;
            }
        }

        return (totalTank >= 0) ? startStation : -1;
    }

    public static void main(String[] args) {
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        System.out.println("P14 Output: " + canCompleteCircuit(gas, cost));
        // Expected: 3 (Station index 3)
    }
}