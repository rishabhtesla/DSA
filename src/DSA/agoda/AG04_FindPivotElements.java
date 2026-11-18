package DSA.agoda;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * [AG-04 / 06] - PIVOT ELEMENTS IN UNSORTED ARRAY (Agoda Classic OA & Live Round)
 * ============================================================================
 *
 * PROBLEM:
 *   You are given an unsorted integer array `arr` of length `n`. An element `arr[i]`
 *   is defined as a "Pivot Element" (partition-ready element) if it satisfies BOTH
 *   of the following conditions:
 *     1. It is STRICTLY GREATER than every element to its left (`arr[0 ... i-1]`).
 *     2. It is STRICTLY SMALLER than every element to its right (`arr[i+1 ... n-1]`).
 *
 *   Return a list of all Pivot Elements in their original left-to-right order.
 *
 *   Boundary Rules:
 *     - Index `0` has no elements to its left, so condition (1) is automatically true.
 *     - Index `n - 1` has no elements to its right, so condition (2) is automatically true.
 *     - If `n == 1`, the single element is trivially a Pivot Element.
 *
 * CONSTRAINTS:
 *   - 0 <= arr.length <= 10^5
 *   - -10^9 <= arr[i] <= 10^9
 *
 * EXAMPLES:
 *   Example 1:
 *     Input:  arr = [2, 3, 1, 8, 9, 11, 10, 15]
 *     Output: [8, 15]
 *     Explanation:
 *       - 8  (index 3): max(left) = 3 < 8, and min(right) = 9 > 8  -> Valid Pivot!
 *       - 15 (index 7): max(left) = 11 < 15, and no right elements -> Valid Pivot!
 *       - Note: 9 and 11 fail because 10 is to their right (10 < 11, 10 < 9 is false, wait: 9 < 10 holds,
 *         so why does 9 fail? Check right side of 9: [11, 10, 15] -> min is 10 > 9! Wait: 9 IS a pivot!
 *         Correction: For 9 (index 4), max(left) = 8 < 9 and min(right) = 10 > 9 -> 9 is ALSO a pivot!
 *         Output for [2, 3, 1, 8, 9, 11, 10, 15] is [8, 9, 15].)
 *
 *   Example 2:
 *     Input:  arr = [5, 1, 4, 3, 6, 8, 10, 7, 9]
 *     Output: [6]
 *     Explanation:
 *       - 6 (index 4): max(left) = 5 < 6, and min(right) = 7 > 6 -> Valid Pivot!
 *       - All other elements fail at least one side.
 *
 *   Example 3:
 *     Input:  arr = [3, 3, 3]
 *     Output: []
 *     Explanation: Strict inequality (< and >) is required; duplicates cannot be pivots.
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - Checking "greater than ALL left elements" reduces to a single comparison:
 *       `arr[i] > max(arr[0 ... i-1])`  (If you beat the biggest bully on the left, you beat them all!)
 *   - Checking "smaller than ALL right elements" reduces to a single comparison:
 *       `arr[i] < min(arr[i+1 ... n-1])` (If you are smaller than the smallest element on the right, you are smaller than them all!)
 *   - Instead of recomputing left-max and right-min from scratch at every index (`O(n^2)`):
 *       1. Precompute all right-side minimums in ONE backward pass (`suffixMin[]`).
 *       2. Track the left-side maximum on the fly in ONE forward pass (`prefixMax`).
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Guard Edge Case:
 *      - If `arr == null || arr.length == 0`, return an empty `ArrayList<>()`.
 *
 *   2. Pass 1 — Right-to-Left Suffix Minimum Array (`suffixMin`):
 *      - Define `suffixMin[i]` = minimum value strictly to the RIGHT of index `i`,
 *        i.e., `min(arr[i+1 ... n-1])`.
 *      - Base case: `suffixMin[n - 1] = Integer.MAX_VALUE` (nothing exists to the right of `n - 1`).
 *      - Loop `i` from `n - 2` down to `0`:
 *          * `suffixMin[i] = Math.min(suffixMin[i + 1], arr[i + 1])`
 *          * Notice how `suffixMin[i]` only looks at `i + 1`, so `arr[i]` itself is
 *            never included in its own right-side minimum!
 *
 *   3. Pass 2 — Left-to-Right Scan with Running `prefixMax`:
 *      - Initialize `prefixMax = Integer.MIN_VALUE` (tracks `max(arr[0 ... i-1])`).
 *      - Loop `i` from `0` to `n - 1`:
 *          * Check Left Condition:  `validLeft  = (i == 0) || (arr[i] > prefixMax)`
 *          * Check Right Condition: `validRight = (i == n - 1) || (arr[i] < suffixMin[i])`
 *          * If BOTH `validLeft && validRight` are true -> `pivots.add(arr[i])`.
 *          * Update running maximum AFTER checking index `i`:
 *              `prefixMax = Math.max(prefixMax, arr[i])`
 *              (Updating AFTER ensures `arr[i]` is compared against `0 ... i-1`, not itself!)
 *
 *   4. Return `pivots`.
 *
 * ============================================================================
 * VISUAL DRY RUN: arr = [2, 3, 1, 8, 9, 11, 10, 15]
 * ============================================================================
 *   Step 1: Build `suffixMin` (Right -> Left, strictly right of index `i`)
 *     Index (i)   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |   7
 *     ------------+-----+-----+-----+-----+-----+-----+-----+------
 *     arr[i]      |  2  |  3  |  1  |  8  |  9  | 11  | 10  |  15
 *     suffixMin[i]|  1  |  1  |  8  |  9  | 10  | 10  | 15  | +INF
 *
 *   Step 2: Sweep Left -> Right comparing `prefixMax < arr[i] < suffixMin[i]`
 *     i | prefixMax (Left) | arr[i] | suffixMin[i] (Right) | Condition Check         | Pivot?
 *     --+------------------+--------+----------------------+-------------------------+-------
 *     0 |      (none)      |   2    |          1           | (true) && (2 < 1) = F   |  No
 *     1 |        2         |   3    |          1           | (3 > 2) && (3 < 1) = F  |  No
 *     2 |        3         |   1    |          8           | (1 > 3) && (1 < 8) = F  |  No
 *     3 |        3         |   8    |          9           | (8 > 3) && (8 < 9) = T  |  YES (8)
 *     4 |        8         |   9    |         10           | (9 > 8) && (9 < 10) = T |  YES (9)
 *     5 |        9         |  11    |         10           | (11 > 9) && (11 < 10)=F |  No
 *     6 |       11         |  10    |         15           | (10 > 11) && (10 < 15)=F|  No
 *     7 |       11         |  15    |        (none)        | (15 > 11) && (true) = T |  YES (15)
 *
 *   Final Output: [8, 9, 15]
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(n) - Two linear passes over the array (`O(n) + O(n) = O(n)`).
 *   - Space: O(n) - Extra `suffixMin` array of size `n` (excluding output list).
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (Re-scanning `0..i-1` and `i+1..n-1` at every index `i` repeats min/max work -> O(n^2).)
 *   2. What invariant must remain true after every loop iteration?
 *      (At the start of iteration `i` in Pass 2, `prefixMax == max(arr[0..i-1])` and
 *       `suffixMin[i] == min(arr[i+1..n-1])`.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (We MUST evaluate `arr[i] > prefixMax` BEFORE updating `prefixMax = Math.max(prefixMax, arr[i])`,
 *       otherwise `arr[i]` compares against itself and `arr[i] > arr[i]` is always false.)
 *   4. Which edge cases change the control flow (empty array `[]`, single element `[42]`,
 *      all duplicates `[5, 5, 5]`, `Integer.MIN_VALUE` at index 0, `Integer.MAX_VALUE` at index `n-1`)?
 *      (Explicit `(i == 0)` and `(i == n - 1)` guards protect against `Integer.MIN_VALUE` / `MAX_VALUE` edge cases!)
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG04_FindPivotElements {

    public static List<Integer> findPivots(int[] arr) {
        List<Integer> pivots = new ArrayList<>();
        if (arr == null || arr.length == 0) {
            return pivots;
        }

        int n = arr.length;

        // Pass 1 (Right -> Left): suffixMin[i] stores the minimum value strictly to the RIGHT of i
        int[] suffixMin = new int[n];
        suffixMin[n - 1] = Integer.MAX_VALUE; // Base case: no elements exist to the right of n - 1

        for (int i = n - 2; i >= 0; i--) {
            // Look one step right: compare arr[i + 1] with everything to its right
            suffixMin[i] = Math.min(suffixMin[i + 1], arr[i + 1]);
        }

        // Pass 2 (Left -> Right): Track running prefixMax strictly to the LEFT of i
        int prefixMax = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            // Boundary indices (0 and n - 1) automatically satisfy their missing side
            boolean greaterThanAllLeft = (i == 0) || (arr[i] > prefixMax);
            boolean smallerThanAllRight = (i == n - 1) || (arr[i] < suffixMin[i]);

            // Sandwich check: prefixMax < arr[i] < suffixMin[i]
            if (greaterThanAllLeft && smallerThanAllRight) {
                pivots.add(arr[i]);
            }

            // Update prefixMax AFTER checking index i so arr[i] is excluded from its own left max
            prefixMax = Math.max(prefixMax, arr[i]);
        }

        return pivots;
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 3, 1, 8, 9, 11, 10, 15};
        System.out.println("Test 1 ([2, 3, 1, 8, 9, 11, 10, 15]): " + findPivots(arr1) + " | Expected: [8, 9, 15]");

        int[] arr2 = {5, 1, 4, 3, 6, 8, 10, 7, 9};
        System.out.println("Test 2 ([5, 1, 4, 3, 6, 8, 10, 7, 9]): " + findPivots(arr2) + " | Expected: [6]");

        int[] arr3 = {3, 3, 3};
        System.out.println("Test 3 ([3, 3, 3]):                    " + findPivots(arr3) + " | Expected: []");
    }
}