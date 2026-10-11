package DSA.DP;

import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * [15 / 30] - SHERLOCK AND COST (HackerRank Benchmark DP Problem)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an array B of N integers, construct an array A of N integers such that:
 *   1 <= A[i] <= B[i] for all 0 <= i < N
 *
 * Goal: Maximize the total sum of absolute differences between adjacent elements:
 *   S = sum of |A[i] - A[i - 1]| for i from 1 to N - 1
 *
 *
 * 2. THE MATHEMATICAL "EXTREMES" AHA! MOMENT:
 * ----------------------------------------------------------------------------
 * To maximize |A[i] - A[i - 1]|, you will NEVER pick an intermediate value 
 * strictly between 1 and B[i].
 * 
 * Convexity Property:
 *   The absolute difference |x - y| is a convex function. Its maximum on any 
 *   interval [1, B[i]] always occurs at the boundaries:
 *   A[i] is strictly either 1 OR B[i].
 *
 *
 * 3. TWO-STATE TRANSITION AT EACH STEP:
 * ----------------------------------------------------------------------------
 * At each index `i`, we maintain two values:
 *   - `low`:  maximum sum up to index `i` if we choose A[i] = 1
 *   - `high`: maximum sum up to index `i` if we choose A[i] = B[i]
 *
 * Transitions from (i - 1) to (i):
 *   If A[i] = 1:
 *     nextLow = max(
 *       low  + |1 - 1|,        // Previous was 1
 *       high + |1 - B[i - 1]|  // Previous was B[i - 1]
 *     )
 *
 *   If A[i] = B[i]:
 *     nextHigh = max(
 *       low  + |B[i] - 1|,        // Previous was 1
 *       high + |B[i] - B[i - 1]|  // Previous was B[i - 1]
 *     )
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * B = [10, 1, 10, 1, 10]
 *
 * Index 0:
 *   low = 0, high = 0
 *
 * Index 1 (B[1] = 1, prevB = 10):
 *   nextLow  = max(0 + |1 - 1|, 0 + |1 - 10|) = max(0, 9) = 9
 *   nextHigh = max(0 + |1 - 1|, 0 + |1 - 10|) = max(0, 9) = 9
 *   low = 9, high = 9
 *
 * Index 2 (B[2] = 10, prevB = 1):
 *   nextLow  = max(9 + |1 - 1|, 9 + |1 - 1|) = 9
 *   nextHigh = max(9 + |10 - 1|, 9 + |10 - 1|) = 9 + 9 = 18
 *   low = 9, high = 18
 *
 * Index 3 (B[3] = 1, prevB = 10):
 *   nextLow  = max(9 + |1 - 1|, 18 + |1 - 10|) = max(9, 18 + 9) = 27
 *   nextHigh = max(9 + |1 - 1|, 18 + |1 - 10|) = 27
 *   low = 27, high = 27
 *
 * Index 4 (B[4] = 10, prevB = 1):
 *   nextLow  = max(27, 27) = 27
 *   nextHigh = max(27 + |10 - 1|, 27 + |10 - 1|) = 27 + 9 = 36
 *   low = 27, high = 36
 *
 * Result: max(low, high) = 36
 * Constructed optimal A: [10, 1, 10, 1, 10]
 * |1-10| + |10-1| + |1-10| + |10-1| = 9 + 9 + 9 + 9 = 36.
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n) - Single pass over array B.
 * - Space Complexity: O(1) - Constant variables.
 */
public class P15_SherlockAndCost {

    public static int cost(List<Integer> B) {
        int n = B.size();
        if (n <= 1) return 0;

        int low = 0;  // Max sum if A[i - 1] was chosen as 1
        int high = 0; // Max sum if A[i - 1] was chosen as B[i - 1]

        for (int i = 1; i < n; i++) {
            int prevB = B.get(i - 1);
            int currB = B.get(i);

            // Choice 1: Pick A[i] = 1
            int nextLow = Math.max(
                low + Math.abs(1 - 1),
                high + Math.abs(1 - prevB)
            );

            // Choice 2: Pick A[i] = currB
            int nextHigh = Math.max(
                low + Math.abs(currB - 1),
                high + Math.abs(currB - prevB)
            );

            low = nextLow;
            high = nextHigh;
        }

        return Math.max(low, high);
    }

    public static void main(String[] args) {
        List<Integer> B1 = Arrays.asList(10, 1, 10, 1, 10);
        System.out.println("P15 Sherlock Cost [10, 1, 10, 1, 10]: " + cost(B1)); // Expected: 36

        List<Integer> B2 = Arrays.asList(4, 7, 9);
        System.out.println("P15 Sherlock Cost [4, 7, 9]:          " + cost(B2)); // Expected: 12 (A = [4, 1, 9] -> |1-4| + |9-1| = 3 + 8 = 11, or A = [1, 7, 1] -> 6 + 6 = 12)
    }
}