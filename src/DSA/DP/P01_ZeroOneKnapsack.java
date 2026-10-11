package DSA.DP;

/**
 * ============================================================================
 * [01 / 30] - 0/1 KNAPSACK (Classic Benchmark Problem)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given weights[] and values[] of N items, put these items in a knapsack of 
 * capacity W to get the maximum total value in the knapsack.
 * 
 * Constraint: You cannot break an item; you either pick it completely (1) 
 * or leave it (0). Each item can be picked at most ONCE.
 *
 *
 * 2. RECURRENCE & DECISION LOGIC:
 * ----------------------------------------------------------------------------
 * For each item 'i' (weight = w, value = v) and available capacity 'cap':
 * 
 * Choice 1: EXCLUDE item i
 *   - The maximum value remains whatever we could make without it: dp[i - 1][cap]
 *
 * Choice 2: INCLUDE item i (only valid if w <= cap)
 *   - We gain value 'v', and remaining capacity drops to (cap - w):
 *     v + dp[i - 1][cap - w]
 *
 * Recurrence:
 *   dp[i][cap] = Math.max(dp[i - 1][cap], v + dp[i - 1][cap - w])
 *
 *
 * 3. 1D SPACE OPTIMIZATION TRICK (REVERSE TRAVERSAL):
 * ----------------------------------------------------------------------------
 * Notice that dp[cap] depends on values from the PREVIOUS row at smaller indices:
 *   dp[i - 1][cap - w]
 * 
 * If we traverse 'cap' forwards (0 -> W), we would overwrite dp[cap - w] with 
 * an item already used in the current round (unbounded knapsack).
 * 
 * Traversing 'cap' BACKWARDS (W down to w) ensures that when we compute dp[cap],
 * dp[cap - w] still holds the value from the PREVIOUS iteration!
 *
 *
 * 4. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * Items:
 *   Item 0: wt = 1, val = 1
 *   Item 1: wt = 3, val = 4
 *   Item 2: wt = 4, val = 5
 *   Item 3: wt = 5, val = 7
 * Capacity W = 7
 *
 * Initial 1D DP: [0, 0, 0, 0, 0, 0, 0, 0]
 *
 * Pass 1: wt=1, val=1 (cap: 7 down to 1)
 *   dp = [0, 1, 1, 1, 1, 1, 1, 1]
 *
 * Pass 2: wt=3, val=4 (cap: 7 down to 3)
 *   cap=7: max(1, 4 + dp[4]) = max(1, 4+1) = 5
 *   cap=6: max(1, 4 + dp[3]) = max(1, 4+1) = 5
 *   cap=5: max(1, 4 + dp[2]) = max(1, 4+1) = 5
 *   cap=4: max(1, 4 + dp[1]) = max(1, 4+1) = 5
 *   cap=3: max(1, 4 + dp[0]) = max(1, 4+0) = 4
 *   dp = [0, 1, 1, 4, 5, 5, 5, 5]
 *
 * Pass 3: wt=4, val=5 (cap: 7 down to 4)
 *   cap=7: max(5, 5 + dp[3]) = max(5, 5+4) = 9  (Items {3, 4} -> wt 7, val 9)
 *   cap=6: max(5, 5 + dp[2]) = max(5, 5+1) = 6
 *   cap=5: max(5, 5 + dp[1]) = max(5, 5+1) = 6
 *   cap=4: max(5, 5 + dp[0]) = max(5, 5+0) = 5
 *   dp = [0, 1, 1, 4, 5, 6, 6, 9]
 *
 * Pass 4: wt=5, val=7 (cap: 7 down to 5)
 *   cap=7: max(9, 7 + dp[2]) = max(9, 7+1) = 9
 *   cap=6: max(6, 7 + dp[1]) = max(6, 7+1) = 8
 *   cap=5: max(6, 7 + dp[0]) = max(6, 7+0) = 7
 *   dp = [0, 1, 1, 4, 5, 7, 8, 9]
 *
 * Final Answer: dp[7] = 9
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N * W)
 * - Space Complexity: O(W) using 1D space optimization
 */
public class P01_ZeroOneKnapsack {

    public static int knapSackOptimized(int capacity, int[] weights, int[] values) {
        int n = weights.length;
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < n; i++) {
            int w = weights[i];
            int v = values[i];

            // Traverse backwards to prevent using the same item multiple times
            for (int cap = capacity; cap >= w; cap--) {
                dp[cap] = Math.max(dp[cap], v + dp[cap - w]);
            }
        }

        return dp[capacity];
    }

    public static void main(String[] args) {
        int capacity = 7;
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};

        int maxVal = knapSackOptimized(capacity, weights, values);
        System.out.println("Maximum Knapsack Value: " + maxVal); // Expected: 9
    }
}