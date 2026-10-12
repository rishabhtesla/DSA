package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [14 / 24] - GAS STATION (LeetCode 134)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * There are n gas stations along a circular route, where the amount of gas at
 * station `i` is `gas[i]`. You have a car with an unlimited gas tank and it costs
 * `cost[i]` of gas to travel from station `i` to its next station `(i + 1)`.
 * You begin the journey with an empty tank at one of the gas stations.
 *
 * Return the starting gas station's index if you can travel around the circuit
 * once in the clockwise direction, otherwise return -1.
 * Note: If there exists a solution, it is guaranteed to be unique.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force O(n^2):
 *    - Try starting from every single station and simulate the full loop.
 *    - Too slow and redundant.
 *
 * 2. Global Feasibility Condition:
 *    - If the total sum of gas is less than the total sum of cost (`sum(gas) < sum(cost)`),
 *      it is mathematically impossible to complete a circuit -> return -1 immediately.
 *
 * 3. The Greedy "Aha!" Insight (O(n) Single Pass):
 *    - Suppose we start at station `A` and run out of gas before reaching station `B` (i.e., `currentTank < 0`).
 *    - Can *any* station between `A` and `B` be a valid starting point?
 *    - **NO!** Any station between `A` and `B` already contributed a non-negative or neutral
 *      amount of gas to reach that failure point. If starting with accumulated surplus from `A`
 *      still failed, starting with zero gas from an intermediate station will fail even earlier.
 *    - Therefore, whenever `currentTank < 0`, we can safely discard all stations up to `i`,
 *      reset our `startStation` to `i + 1`, and reset `currentTank = 0`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single linear scan through the arrays.
 * - Space: O(1) - Constant tracking variables (`totalTank`, `currentTank`, `startStation`).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE: gas = [1, 2, 3, 4, 5], cost = [3, 4, 5, 1, 2] -> Output: 3
 *
 * VISUAL DRY RUN:
 *   Net gain array (`gas[i] - cost[i]`): `[-2, -2, -2, 3, 3]`
 *
 *   - i = 0: net = -2 | totalTank = -2, currentTank = -2
 *     -> currentTank < 0! Reset: startStation = 1, currentTank = 0.
 *   - i = 1: net = -2 | totalTank = -4, currentTank = -2
 *     -> currentTank < 0! Reset: startStation = 2, currentTank = 0.
 *   - i = 2: net = -2 | totalTank = -6, currentTank = -2
 *     -> currentTank < 0! Reset: startStation = 3, currentTank = 0.
 *   - i = 3: net =  3 | totalTank = -3, currentTank = 3  (Valid start so far)
 *   - i = 4: net =  3 | totalTank =  0, currentTank = 6  (Maintains surplus)
 *
 *   Final Check: totalTank (0) >= 0, so return startStation (3).
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is checking `totalTank >= 0` at the end sufficient to guarantee a valid start exists?
 *    (Because if total gas >= total cost, a solution is guaranteed to exist. By resetting `startStation`
 *    whenever the tank drops below zero, the greedy choice guarantees we find that unique starting point.)
 * 2. What happens if `gas` and `cost` arrays are of length 1?
 *    (Handled seamlessly: if `gas[0] >= cost[0]`, net >= 0, returns index 0; else returns -1.)
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