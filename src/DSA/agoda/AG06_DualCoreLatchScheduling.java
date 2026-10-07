package DSA.agoda;

import java.util.Arrays;

/**
 * ============================================================================
 * [AG-06 / 06] - DUAL-CORE LATCH SCHEDULING (Agoda Game-Theoretic DP / Suffix Minimax)
 * ============================================================================
 * 
 * PROBLEM:
 *   There is a list of processes with execution times time[].
 *   Two processor cores (Core 1 and Core 2) compete to maximize their total processed time.
 *   Core 1 holds the execution latch initially.
 *   For each process i sequentially (from 0 to n - 1), the core currently holding the latch can:
 *     Choice A: Process task i and surrender the latch to the other core.
 *     Choice B: Delegate task i to the other core and RETAIN the latch for process i + 1.
 *   Both cores play optimally to maximize their own total execution time.
 *   Return the total processing time accumulated by [Core 1, Core 2].
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Game Theory / Backward Induction (Minimax DP):
 *     Because both cores want to maximize their share of the remaining suffix sum,
 *     we can solve this starting from the LAST task (index n - 1) backwards to 0.
 *   - Let:
 *     `suffixSum` = total sum of task times from index i to n - 1.
 *     `best` = the maximum payoff the core HOLDING the latch can achieve on tasks i+1...n-1.
 *   - At task i:
 *     - If the active core processes task i and surrenders the latch:
 *       It gets `time[i] + (remaining suffix sum - best) = suffixSum - best`.
 *     - If the active core gives task i away and keeps the latch:
 *       It gets `0 + best = best`.
 *     Therefore, the optimal payoff for the core with the latch at index i is:
 *     `newBest = max(suffixSum - best, best)`.
 *   - At the end of backward induction:
 *     Core 1 (holds latch initially) gets `best`.
 *     Core 2 gets `totalSum - best`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single backward linear scan.
 *   - Space: O(1) - Only two scalar long variables (handles large integer overflow).
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class AG06_DualCoreLatchScheduling {

    public static long[] maximizeProcessedTimes(int[] time) {
        long best = 0;      // Optimal score for latch holder on an empty suffix
        long suffixSum = 0;  // Running sum of tasks from i to n - 1

        // Backward induction from last process to first
        for (int i = time.length - 1; i >= 0; i--) {
            suffixSum += time[i];

            long takeAndPassLatch = suffixSum - best;
            long skipAndKeepLatch = best;

            best = Math.max(takeAndPassLatch, skipAndKeepLatch);
        }

        long core1Total = best;
        long core2Total = suffixSum - best;

        return new long[]{core1Total, core2Total};
    }

    public static void main(String[] args) {
        int[] time1 = {10, 21, 10, 21, 10};
        System.out.println("AG06 Output (Test 1): " + Arrays.toString(maximizeProcessedTimes(time1))); 
        // Expected: [41, 31]

        int[] time2 = {1, 2, 3, 4, 5};
        System.out.println("AG06 Output (Test 2): " + Arrays.toString(maximizeProcessedTimes(time2))); 
        // Expected: [9, 6]
    }
}