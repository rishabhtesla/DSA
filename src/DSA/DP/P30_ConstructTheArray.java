package DSA.DP;

/**
 * ============================================================================
 * [30 / 30] - CONSTRUCT THE ARRAY (HackerRank Hard DP Problem)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Your goal is to find the number of ways to construct an array of length `n` such that:
 *   1. 1 <= array[i] <= k for all 0 <= i < n
 *   2. array[i] != array[i - 1] for all adjacent elements
 *   3. array[0] == 1
 *   4. array[n - 1] == x
 * Return the answer modulo 10^9 + 7.
 *
 * Constraints:
 *   3 <= n <= 10^5, 2 <= k <= 10^5, 1 <= x <= k
 *
 *
 * 2. SYMMETRY & DUAL-STATE DP:
 * ----------------------------------------------------------------------------
 * Notice that any number other than 1 is symmetric!
 * At each index `i`, we only care whether the element placed is `1` or `NOT 1`:
 *
 * State 0: `same` = number of valid prefixes ending at `1` at index `i`.
 * State 1: `diff` = number of valid prefixes ending at a specific number `v != 1` at index `i`.
 *
 * Transition from index (i - 1) to (i):
 *   To place `1` at index `i`:
 *     The previous element CANNOT be 1. It must be one of the (k - 1) other numbers.
 *     nextSame = ((k - 1) * diff) % MOD
 *
 *   To place a specific number `v != 1` at index `i`:
 *     The previous element could be `1` (1 choice: same),
 *     OR the previous element could be any other non-1 number EXCEPT `v` ((k - 2) choices: (k - 2) * diff).
 *     nextDiff = (same + (k - 2) * diff) % MOD
 *
 * Base Case (i = 0):
 *   Array starts with 1:
 *   same = 1
 *   diff = 0
 *
 * Final Result:
 *   If x == 1: return same
 *   If x != 1: return diff
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * n = 4, k = 3, x = 2
 * Start (i = 0): same = 1, diff = 0
 *
 * i = 1:
 *   nextSame = (3 - 1) * 0 = 0
 *   nextDiff = 1 + (3 - 2) * 0 = 1
 *   same = 0, diff = 1
 *
 * i = 2:
 *   nextSame = 2 * 1 = 2
 *   nextDiff = 0 + 1 * 1 = 1
 *   same = 2, diff = 1
 *
 * i = 3 (Target length n = 4):
 *   nextSame = 2 * 1 = 2
 *   nextDiff = 2 + 1 * 1 = 3
 *   same = 2, diff = 3
 *
 * Since x = 2 (which is != 1), answer is `diff` = 3.
 * (Valid arrays: [1, 2, 1, 2], [1, 2, 3, 2], [1, 3, 1, 2] -> Exactly 3 ways).
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n) - Single loop up to n.
 * - Space Complexity: O(1) - Two rolling state variables.
 */
public class P30_ConstructTheArray {

    public static long countArray(int n, int k, int x) {
        long MOD = 1_000_000_007L;

        long same = 1; // Ending at 1 at index 0
        long diff = 0; // Ending at non-1 at index 0

        for (int i = 1; i < n; i++) {
            long nextSame = ((long) (k - 1) * diff) % MOD;
            long nextDiff = (same + (long) (k - 2) * diff) % MOD;

            same = nextSame;
            diff = nextDiff;
        }

        return (x == 1) ? same : diff;
    }

    public static void main(String[] args) {
        System.out.println("P30 Ways (n=4, k=3, x=2): " + countArray(4, 3, 2)); // Expected: 3
        System.out.println("P30 Ways (n=5, k=2, x=1): " + countArray(5, 2, 1)); // Expected: 1 ([1, 2, 1, 2, 1])
    }
}