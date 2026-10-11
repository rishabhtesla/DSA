package DSA.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * [19 / 30] - RUSSIAN DOLL ENVELOPES (LeetCode 354)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * You are given a 2D array of integers `envelopes` where `envelopes[i] = [w_i, h_i]` 
 * represents the width and the height of an envelope.
 * One envelope can fit into another if and only if BOTH width and height of one 
 * envelope are strictly greater than the other envelope:
 *   w1 < w2 AND h1 < h2
 * Return the maximum number of envelopes you can Russian doll (nest inside each other).
 *
 *
 * 2. THE 2D SORTING "AHA!" REDUCTION:
 * ----------------------------------------------------------------------------
 * If we sort envelopes purely by width ascending, we can satisfy the width condition.
 * But what happens if two envelopes have the EXACT SAME width?
 *   e.g., [3, 4] and [3, 5]. We CANNOT nest one inside the other because w1 == w2.
 *
 * The Clever Sorting Rule:
 *   1. Sort `width` in ASCENDING order.
 *   2. If widths are equal, sort `height` in DESCENDING order!
 *
 * Why Descending Height for Equal Width?
 *   - Suppose envelopes are: [3, 4] and [3, 5].
 *   - Sorting height descending gives: [3, 5] followed by [3, 4].
 *   - When we run 1D LIS on heights, 4 cannot come after 5 to extend the subsequence.
 *     Thus, at most ONE envelope of width 3 can ever be chosen!
 *
 * This reduces the entire 2D problem to:
 *   "Find the 1D LIS on the heights array in O(n log n) time!"
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * envelopes = [[5,4], [6,4], [6,7], [2,3]]
 *
 * Step 1: Sort by width asc, then height desc:
 *   [2, 3]
 *   [5, 4]
 *   [6, 7]  <- width 6 has heights 7, 4. Sorted descending: 7 first, then 4!
 *   [6, 4]
 *
 * Heights array: [3, 4, 7, 4]
 *
 * Step 2: 1D LIS on heights:
 *   x = 3: tails = [3]
 *   x = 4: tails = [3, 4]
 *   x = 7: tails = [3, 4, 7]
 *   x = 4: replace 7 with 4 -> tails = [3, 4, 4] -> binary search replaces 4 with 4
 *
 * Final tails size: 3 (Envelopes: [2,3] -> [5,4] -> [6,7])
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n log n) - O(n log n) for sorting + O(n log n) for LIS.
 * - Space Complexity: O(n) - Auxiliary list for tails.
 */
public class P19_RussianDollEnvelopes {

    public static int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) return 0;

        // Custom Sort: width asc, height desc on ties
        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] == b[0]) {
                return Integer.compare(b[1], a[1]); // Height descending
            }
            return Integer.compare(a[0], b[0]);     // Width ascending
        });

        // 1D LIS on the heights using Patience Sorting
        List<Integer> tails = new ArrayList<>();

        for (int[] env : envelopes) {
            int h = env[1];
            int idx = Collections.binarySearch(tails, h);

            if (idx < 0) {
                idx = -(idx + 1);
            }

            if (idx == tails.size()) {
                tails.add(h);
            } else {
                tails.set(idx, h);
            }
        }

        return tails.size();
    }

    public static void main(String[] args) {
        int[][] env1 = {{5, 4}, {6, 4}, {6, 7}, {2, 3}};
        System.out.println("P19 Max Envelopes: " + maxEnvelopes(env1)); // Expected: 3

        int[][] env2 = {{1, 1}, {1, 1}, {1, 1}};
        System.out.println("P19 Identical Envelopes: " + maxEnvelopes(env2)); // Expected: 1
    }
}