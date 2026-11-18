package DSA.agoda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [AG-03 / 06] - REASSIGN PRIORITIES / RANK TRANSFORM (Agoda OA Warm-up / LC 1331)
 * ============================================================================
 *
 * PROBLEM:
 *   You are given an integer array `arr` of length `n` representing raw task or
 *   booking priorities. Your goal is to compress and reassign each priority to its
 *   relative rank in a new integer array `result` of the same length.
 *
 *   Rules:
 *     1. Ranks are 1-indexed (the smallest unique value receives rank 1).
 *     2. Smaller values strictly receive smaller ranks (`if a < b, rank(a) < rank(b)`).
 *     3. Duplicate values receive the exact same rank (`if a == b, rank(a) == rank(b)`).
 *     4. Ranks must be strictly contiguous (1, 2, 3, ..., k) with no gaps, where `k`
 *        is the number of distinct elements in `arr`.
 *
 * CONSTRAINTS:
 *   - 0 <= arr.length <= 10^5
 *   - -10^9 <= arr[i] <= 10^9
 *
 * EXAMPLES:
 *   Example 1:
 *     Input:  arr = [40, 10, 20, 30]
 *     Output: [4, 1, 2, 3]
 *     Explanation: 10 is smallest (rank 1), 20 is 2nd (rank 2), 30 is 3rd (rank 3),
 *                  40 is largest (rank 4).
 *
 *   Example 2:
 *     Input:  arr = [100, 100, 100]
 *     Output: [1, 1, 1]
 *     Explanation: All elements are equal, so all share rank 1.
 *
 *   Example 3:
 *     Input:  arr = [37, 12, 98, 41, -20, 12]
 *     Output: [3, 2, 5, 4, 1, 2]
 *     Explanation: Sorted unique values: -20 -> 1, 12 -> 2, 37 -> 3, 41 -> 4, 98 -> 5.
 *                  Notice there is NO gap after duplicate 12 (37 gets rank 3, not 4).
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - This pattern is called "Coordinate Compression".
 *   - We need two conflicting things at once:
 *       1. Values sorted in ascending order (to know relative rank).
 *       2. The original index positions preserved (to return the result in order).
 *   - Instead of sorting `(value, originalIndex)` pairs, we separate the concerns:
 *       1. Clone and sort a copy of the array to discover the monotonic rank order.
 *       2. Store `value -> rank` in a HashMap (ignoring duplicates so rank stays dense).
 *       3. Walk the untouched original array and translate each value via O(1) lookup.
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Guard Edge Case:
 *      - If `arr == null` or `arr.length == 0`, return an empty array `new int[0]`.
 *
 *   2. Clone & Sort (Discover Relative Order):
 *      - Create `sorted = arr.clone()` so we don't destroy the original element order.
 *      - Sort `sorted` in ascending order using `Arrays.sort(sorted)`.
 *
 *   3. Build Dense Rank Dictionary (`rankMap`):
 *      - Initialize `rank = 1` and an empty `HashMap<Integer, Integer> rankMap`.
 *      - Iterate `val` from left to right across `sorted`:
 *          * If `!rankMap.containsKey(val)` (first time seeing this number):
 *              - Assign `rankMap.put(val, rank)` and post-increment `rank++`.
 *          * If `rankMap.containsKey(val)` (duplicate number):
 *              - Skip it! Neither `rankMap` nor `rank` changes, guaranteeing
 *                equal elements share a rank and no rank numbers are skipped.
 *
 *   4. Reconstruct Output Array:
 *      - Allocate `result = new int[arr.length]`.
 *      - Loop `i` from `0` to `arr.length - 1`:
 *          * Set `result[i] = rankMap.get(arr[i])`.
 *
 * ============================================================================
 * VISUAL DRY RUN: arr = [37, 12, 98, 41, -20, 12]
 * ============================================================================
 *   Phase 1: Clone & Sort
 *     arr    = [ 37, 12, 98, 41, -20, 12 ]  (Kept intact for final lookup)
 *     sorted = [ -20, 12, 12, 37, 41, 98 ]
 *
 *   Phase 2: Sweep `sorted` to build `rankMap` (rank starts at 1)
 *     Index | val | Already in map? | Action                | rankMap State
 *     ------+-----+-----------------+-----------------------+-----------------------------
 *       0   | -20 | No              | Put (-20 -> 1), r = 2 | {-20:1}
 *       1   |  12 | No              | Put ( 12 -> 2), r = 3 | {-20:1, 12:2}
 *       2   |  12 | YES (Duplicate) | Skip! `r` stays 3     | {-20:1, 12:2}
 *       3   |  37 | No              | Put ( 37 -> 3), r = 4 | {-20:1, 12:2, 37:3}
 *       4   |  41 | No              | Put ( 41 -> 4), r = 5 | {-20:1, 12:2, 37:3, 41:4}
 *       5   |  98 | No              | Put ( 98 -> 5), r = 6 | {-20:1, 12:2, 37:3, 41:4, 98:5}
 *
 *   Phase 3: Map original `arr` -> `result`
 *     i = 0: arr[0] =  37  ->  rankMap.get(37)  = 3
 *     i = 1: arr[1] =  12  ->  rankMap.get(12)  = 2
 *     i = 2: arr[2] =  98  ->  rankMap.get(98)  = 5
 *     i = 3: arr[3] =  41  ->  rankMap.get(41)  = 4
 *     i = 4: arr[4] = -20  ->  rankMap.get(-20) = 1
 *     i = 5: arr[5] =  12  ->  rankMap.get(12)  = 2
 *     Final Result = [3, 2, 5, 4, 1, 2]
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(n log n) - `Arrays.sort` takes O(n log n); building the map and
 *                         mapping the result take O(n) each.
 *   - Space: O(n)       - `sorted` copy takes O(n) and `rankMap` holds at most `n` entries.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (For every `arr[i]`, scanning the whole array to count how many distinct values are smaller -> O(n^2).)
 *   2. What invariant must remain true after every loop iteration?
 *      (In Phase 2, `rank - 1` always equals the exact number of unique values processed so far.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (Using `putIfAbsent` or checking `!containsKey` before `rank++` is critical; incrementing `rank`
 *       unconditionally on duplicates would leave gaps like `[1, 2, 4]` instead of `[1, 2, 3]`.)
 *   4. Which edge cases change the control flow (empty array `[]`, single element `[5]`,
 *      all duplicates `[7, 7, 7]`, negative numbers, `Integer.MIN_VALUE` / `Integer.MAX_VALUE`)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG03_RankTransformPriorities {

    public static int[] arrayRankTransform(int[] arr) {
        // Guard against null or empty input
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        // Step 1: Clone original array so we can sort without losing index positions
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        // Step 2: Assign contiguous 1-based ranks to unique sorted values
        Map<Integer, Integer> rankMap = new HashMap<>();
        int nextRank = 1;

        for (int val : sorted) {
            // Only assign and increment rank the FIRST time a number appears
            if (!rankMap.containsKey(val)) {
                rankMap.put(val, nextRank);
                nextRank++;
            }
        }

        // Step 3: Translate each element in the original array to its compressed rank
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = rankMap.get(arr[i]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr1 = {40, 10, 20, 30};
        System.out.println("Test 1 ([40, 10, 20, 30]):         " + Arrays.toString(arrayRankTransform(arr1)) + " | Expected: [4, 1, 2, 3]");

        int[] arr2 = {100, 100, 100};
        System.out.println("Test 2 ([100, 100, 100]):          " + Arrays.toString(arrayRankTransform(arr2)) + " | Expected: [1, 1, 1]");

        int[] arr3 = {37, 12, 98, 41, -20, 12};
        System.out.println("Test 3 ([37, 12, 98, 41, -20, 12]):" + Arrays.toString(arrayRankTransform(arr3)) + " | Expected: [3, 2, 5, 4, 1, 2]");
    }
}