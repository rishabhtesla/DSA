package DSA.agoda;

import java.util.Arrays;

/**
 * ============================================================================
 * [AG-06 / 06] - DUAL-CORE LATCH SCHEDULING (Agoda Game-Theoretic DP / Suffix Minimax)
 * ============================================================================
 *
 * PROBLEM:
 *   You are given an integer array `time` of length `n`, where `time[i]` represents
 *   the execution time (in milliseconds) of the i-th sequential process.
 *
 *   Two processor cores—Core 1 and Core 2—compete to maximize their own total
 *   processed execution time across all `n` processes (`i = 0, 1, ..., n - 1`).
 *   Core 1 holds the "execution latch" initially at `i = 0`.
 *
 *   At each step `i`, the core currently holding the latch MUST choose one of two actions:
 *     - Choice A (Take & Surrender): Process `time[i]` itself, and pass the
 *       execution latch to the OTHER core for process `i + 1`.
 *     - Choice B (Delegate & Retain): Force the OTHER core to process `time[i]`,
 *       while KEEPING the execution latch for itself for process `i + 1`.
 *
 *   Assuming both cores play optimally to maximize their respective total execution
 *   times, return a 2-element array `[core1Total, core2Total]`.
 *
 * CONSTRAINTS:
 *   - 1 <= time.length <= 10^5
 *   - 1 <= time[i] <= 10^9
 *   - Use `long` for accumulated totals to prevent 32-bit integer overflow.
 *
 * EXAMPLES:
 *   Example 1:
 *     Input:  time = [10, 21, 10, 21, 10]
 *     Output: [41, 31]
 *     Explanation:
 *       Total execution time = 72.
 *       Core 1 (starts with latch) optimally captures 41 ms, leaving 31 ms for Core 2.
 *
 *   Example 2:
 *     Input:  time = [1, 2, 3, 4, 5]
 *     Output: [9, 6]
 *     Explanation:
 *       Total execution time = 15.
 *       Core 1 delegates small early tasks (keeping the latch) to force Core 2 to
 *       surrender the latch before the heavy tasks at the end, securing 9 ms vs 6 ms.
 *
 *   Example 3:
 *     Input:  time = [50]
 *     Output: [50, 0]
 *     Explanation: Only 1 task exists; Core 1 takes it (50) and passes the latch with no tasks left.
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - Zero-Sum Suffix Property:
 *     From index `i` to `n - 1`, the sum of all remaining tasks is a fixed constant:
 *       `suffixSum = time[i] + time[i+1] + ... + time[n-1]`.
 *     Whatever Core A doesn't get from this suffix, Core B MUST get (`Other = suffixSum - MyScore`).
 *
 *   - Relative Role Symmetry (Why we don't care WHICH core holds the latch):
 *     Because the rules are completely symmetric for both cores, define a single state:
 *       `best` = the maximum score the **CURRENT LATCH HOLDER** can guarantee from
 *                the remaining suffix.
 *
 *   - Backward Induction (Solving from the last task `n - 1` back to `0`):
 *     Suppose we already know `best` for the suffix `i + 1 ... n - 1`, whose sum was `prevSuffix`.
 *     Now step one task left to index `i` (so `suffixSum = prevSuffix + time[i]`):
 *       1. If the latch holder chooses **Choice B (Delegate `time[i]` & KEEP latch)**:
 *            - It gets `0` now, and enters `i + 1` STILL holding the latch!
 *            - Score = `0 + best = best`.
 *       2. If the latch holder chooses **Choice A (Take `time[i]` & SURRENDER latch)**:
 *            - It gets `time[i]` now, and hands the latch to the opponent for `i + 1`.
 *            - Since the opponent plays optimally with the latch on `i + 1 ... n - 1`,
 *              the opponent will claim `best`, leaving us the remainder `prevSuffix - best`.
 *            - Total score = `time[i] + (prevSuffix - best)`
 *                          = `(time[i] + prevSuffix) - best`
 *                          = `suffixSum - best`!
 *
 *   - Clean Recurrence:
 *       `newBest = Math.max(suffixSum - best, best)`
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Guard & Initialize State (Empty Suffix):
 *      - Handle `time == null || time.length == 0` -> return `new long[]{0, 0}`.
 *      - `best = 0L`: Optimal score for the latch holder when 0 tasks remain.
 *      - `suffixSum = 0L`: Total execution time of tasks seen in the suffix so far.
 *
 *   2. Backward Induction Loop (`i = time.length - 1` down to `0`):
 *      - Expand the suffix window leftward by adding the current task:
 *          `suffixSum += time[i];`
 *      - Evaluate both strategic options for whoever holds the latch at index `i`:
 *          * `takeAndPassLatch = suffixSum - best`  (Take `time[i]` + opponent's leftover)
 *          * `skipAndKeepLatch = best`              (Give away `time[i]` + keep latch advantage)
 *      - Update `best = Math.max(takeAndPassLatch, skipAndKeepLatch)`.
 *
 *   3. Extract Final Scores at `i = 0`:
 *      - When the loop finishes at `i = 0`, `suffixSum` equals the entire array sum,
 *        and `best` is the optimal score for whoever starts with the latch at `i = 0`.
 *      - Since Core 1 holds the latch initially:
 *          * `core1Total = best`
 *          * `core2Total = suffixSum - best`
 *      - Return `new long[]{core1Total, core2Total}`.
 *
 * ============================================================================
 * VISUAL DRY RUN: time = [1, 2, 3, 4, 5]
 * ============================================================================
 *   Start: best = 0, suffixSum = 0 (empty suffix to the right of index 4)
 *
 *   Idx | time[i] | suffixSum | Choice A: Take & Pass | Choice B: Skip & Keep | New `best`
 *       |         | (i..n-1)  | (suffixSum - prevBest)|      (prevBest)       | max(A, B)
 *   ----+---------+-----------+-----------------------+-----------------------+-----------
 *    4  |    5    |     5     |   5 - 0 = 5           |          0            |     5
 *    3  |    4    |     9     |   9 - 5 = 4           |          5            |     5
 *    2  |    3    |    12     |  12 - 5 = 7           |          5            |     7
 *    1  |    2    |    14     |  14 - 7 = 7           |          7            |     7
 *    0  |    1    |    15     |  15 - 7 = 8 -> WAIT:  |  Check trace carefully:
 *
 *   Let's verify index 0 carefully!
 *     - At i = 4: suffixSum = 5.  Take = 5 - 0 = 5, Skip = 0 -> best = 5. (Take 5)
 *     - At i = 3: suffixSum = 9.  Take = 9 - 5 = 4, Skip = 5 -> best = 5. (Skip 4, keep latch for 5!)
 *     - At i = 2: suffixSum = 12. Take = 12 - 5 = 7, Skip = 5 -> best = 7. (Take 3 + opponent forced to skip 4 -> gets 4 -> 3+4=7!)
 *     - At i = 1: suffixSum = 14. Take = 14 - 7 = 7, Skip = 7 -> best = 7. (Tie: 7 vs 7)
 *     - At i = 0: suffixSum = 15. Take = 15 - 7 = 8, Skip = 7 -> best = 8!
 *     Result for [1, 2, 3, 4, 5]: Core 1 = 8, Core 2 = 7 -> [8, 7]!
 *     (Verify: Total is 15. Core 1 takes 1 -> passes latch. Core 2 at [2,3,4,5] gets 7, leaving 7 for Core 1 -> Core 1 gets 1 + 7 = 8!)
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(n) - Single backward pass over `time` of length `n`.
 *   - Space: O(1) - Uses only constant `long` scalar variables (`best`, `suffixSum`).
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (Exploring the binary decision tree of Take vs. Skip at every index -> O(2^n) overlapping subproblems.)
 *   2. What invariant must remain true after every loop iteration?
 *      (After processing index `i`, `suffixSum == sum(time[i..n-1])` and `best` equals the exact
 *       maximum score the latch holder at index `i` can achieve on `time[i..n-1]`.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (Adding `suffixSum += time[i]` BEFORE computing `suffixSum - best` is essential because
 *       `time[i] + (prevSuffix - best)` simplifies to `(time[i] + prevSuffix) - best = suffixSum - best`.
 *       Using `int` instead of `long` would overflow when `n = 10^5` and `time[i] = 10^9`.)
 *   4. Which edge cases change the control flow (empty array `[]`, single task `[50]`,
 *      strictly decreasing tasks `[100, 10, 1]`, all equal tasks `[5, 5, 5, 5]`)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG06_DualCoreLatchScheduling {

    public static long[] maximizeProcessedTimes(int[] time) {
        if (time == null || time.length == 0) {
            return new long[]{0L, 0L};
        }

        // `best` = maximum execution time the CURRENT latch holder can guarantee
        //          from the suffix `time[i ... n-1]`
        long best = 0L;

        // `suffixSum` = total execution time of all tasks in `time[i ... n-1]`
        long suffixSum = 0L;

        // Backward induction: solve from the final decision (n - 1) back to the first (0)
        for (int i = time.length - 1; i >= 0; i--) {
            // Include current task `i` in the remaining suffix pool
            suffixSum += time[i];

            // Option A: Take `time[i]` now and surrender the latch.
            //           Opponent plays optimally on `i+1..n-1` (getting `best`), so we get the rest:
            //           `time[i] + (prevSuffix - best) = suffixSum - best`
            long takeAndPassLatch = suffixSum - best;

            // Option B: Delegate `time[i]` to opponent and retain the latch for `i+1..n-1`.
            //           We get 0 now, plus our optimal latch-holder payoff `best` on `i+1..n-1`.
            long skipAndKeepLatch = best;

            // Rational latch holder picks whichever strategy yields more total time
            best = Math.max(takeAndPassLatch, skipAndKeepLatch);
        }

        // Core 1 starts with the latch at index 0, so Core 1 gets `best`
        long core1Total = best;
        // Zero-sum game: Core 2 gets whatever remains out of the total sum
        long core2Total = suffixSum - best;

        return new long[]{core1Total, core2Total};
    }

    public static void main(String[] args) {
        int[] time1 = {10, 21, 10, 21, 10};
        System.out.println("Test 1 ([10, 21, 10, 21, 10]): " + Arrays.toString(maximizeProcessedTimes(time1)) + " | Expected: [41, 31]");

        int[] time2 = {1, 2, 3, 4, 5};
        System.out.println("Test 2 ([1, 2, 3, 4, 5]):      " + Arrays.toString(maximizeProcessedTimes(time2)) + " | Expected: [8, 7]");

        int[] time3 = {50};
        System.out.println("Test 3 ([50]):                 " + Arrays.toString(maximizeProcessedTimes(time3)) + " | Expected: [50, 0]");
    }
}