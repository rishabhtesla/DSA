package DSA.agoda;

import java.util.Arrays;

/**
 * ============================================================================
 * [AGODA HACKERRANK - 02] MINIMIZE OPERATIONS TO MAKE ARRAY ELEMENTS EQUAL (LC 462)
 * ============================================================================
 *
 * SOURCE:
 *   Agoda HackerRank Online Assessment (Senior/Staff rounds).
 *
 * PROBLEM:
 *   You are given an integer array `nums` of length `n`. In a single operation,
 *   you may increment or decrement any single element by `1` (`nums[i] += 1` or
 *   `nums[i] -= 1`).
 *
 *   Return the minimum total number of operations required to make all elements
 *   in `nums` equal (i.e., minimize `sum(|nums[i] - target|)`).
 *
 * CONSTRAINTS:
 *   - 1 <= nums.length <= 10^5
 *   - -10^9 <= nums[i] <= 10^9
 *   - Return type is `long` because the sum of differences can exceed `Integer.MAX_VALUE`.
 *
 * EXAMPLES:
 *   Example 1:
 *     Input:  nums = [1, 2, 3]
 *     Output: 2
 *     Explanation: Optimal target is median = 2.
 *                  1 -> 2 (1 op), 2 -> 2 (0 ops), 3 -> 2 (1 op) => Total = 2.
 *
 *   Example 2:
 *     Input:  nums = [1, 10, 2, 9]
 *     Output: 16
 *     Explanation: Sorted array is [1, 2, 9, 10]. Any target `k` in `[2, 9]` yields
 *                  optimal cost 16. E.g., for `k = 5`: |1-5| + |2-5| + |9-5| + |10-5|
 *                  = 4 + 3 + 4 + 5 = 16.
 *
 *   Example 3:
 *     Input:  nums = [7, 7, 7]
 *     Output: 0
 *     Explanation: All elements are already equal.
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - Mean vs. Median Trap:
 *       * The **Mean** minimizes squared L2 distance: `sum((nums[i] - k)^2)`.
 *       * The **Median** minimizes absolute L1 distance: `sum(|nums[i] - k|)`.
 *       * Why? Imagine placing target `k` on a number line. Moving `k` right by 1 unit
 *         adds `+1` cost to every element on its left, and subtracts `-1` cost from
 *         every element on its right. Cost stops decreasing only when `count(left) == count(right)`
 *         — which is the exact definition of the **Median**!
 *
 *   - Two-Pointer "Pairwise Range" Trick (No explicit median needed!):
 *       * Once sorted (`a_0 <= a_1 <= ... <= a_{n-1}`), pair the outermost elements:
 *         `(nums[left], nums[right])`.
 *       * For ANY target `k` trapped between `nums[left]` and `nums[right]`
 *         (`nums[left] <= k <= nums[right]`):
 *             `(k - nums[left]) + (nums[right] - k) = nums[right] - nums[left]`!
 *       * Notice `k` cancels out completely! As long as `k` sits inside the innermost
 *         pair (the median), every outer pair `(left, right)` contributes a fixed,
 *         unavoidable distance of `nums[right] - nums[left]`.
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Guard Trivial Cases:
 *      - If `nums == null || nums.length <= 1`, 0 operations are needed -> return `0L`.
 *
 *   2. Sort the Array Ascending:
 *      - Call `Arrays.sort(nums)` (`O(n log n)`) so smallest and largest elements
 *        sit at opposite ends.
 *
 *   3. Two-Pointer Inward Squeeze (`left` & `right`):
 *      - Initialize `operations = 0L`, `left = 0`, `right = nums.length - 1`.
 *      - While `left < right`:
 *          * Cast to `long` BEFORE subtracting (`(long) nums[right] - nums[left]`)
 *            to avoid 32-bit signed integer overflow when `nums[right] = 10^9`
 *            and `nums[left] = -10^9`!
 *          * Add the pair's gap to `operations`.
 *          * Move both pointers inward: `left++`, `right--`.
 *      - Note: If `n` is odd, the single middle element (`left == right`) is the
 *        exact median itself, so its distance to the target is `0` and the loop
 *        naturally stops without adding anything.
 *
 *   4. Return `operations`.
 *
 * ============================================================================
 * VISUAL DRY RUN: nums = [1, 10, 2, 9]
 * ============================================================================
 *   Step 1: Sort `nums`
 *     Sorted: [ 1,  2,  9, 10 ]
 *     Index:    0   1   2   3
 *
 *   Step 2: Converge Two Pointers (`left` -> <- `right`)
 *     Iter | left | right | nums[left] | nums[right] | Pair Cost (R - L) | Running `operations`
 *     -----+------+-------+------------+-------------+-------------------+---------------------
 *      1   |  0   |   3   |     1      |     10      |  10 - 1 = 9       |         9
 *      2   |  1   |   2   |     2      |      9      |   9 - 2 = 7       |     9 + 7 = 16
 *      -   |  2   |   1   | (left >= right -> terminate loop)             |   Result = 16
 *
 *   Geometric Number-Line Visualization:
 *     1 --------[ 2 ====== (target k) ====== 9 ]-------- 10
 *     |<----------------- 10 - 1 = 9 ------------------->|  (Outer pair)
 *                 |<------- 9 - 2 = 7 -------->|            (Inner pair)
 *     Total steps = 9 + 7 = 16 (identical for ANY k between 2 and 9!)
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(n log n) - Dominated by `Arrays.sort(nums)`; the two-pointer scan is O(n).
 *                         (Can be improved to O(n) expected time using Quickselect to find the median.)
 *   - Space: O(1)       - Only scalar pointers/accumulators (`O(log n)` stack space used internally by Dual-Pivot Quicksort).
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (Testing every integer `k` from `min(nums)` to `max(nums)` and summing `|nums[i] - k|` -> O(range * n).)
 *   2. What invariant must remain true after every loop iteration?
 *      (All already-processed outer pairs `[0..left-1]` and `[right+1..n-1]` enclose the remaining
 *       inner subarray `[left..right]`, so any median chosen inside `[left..right]` satisfies every outer pair.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (CRITICAL OVERFLOW BUG: `(nums[right] - nums[left])` without casting `(long)` first overflows `int`
 *       if `nums = [-1_000_000_000, 1_000_000_000]`, producing a negative number!)
 *   4. Which edge cases change the control flow (empty/1-element array, odd vs. even length `n`,
 *      negative numbers, extreme `Integer.MIN_VALUE` / `Integer.MAX_VALUE` pairs, all identical elements)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_HR02_MinimumOperationsEqualArray {

    public static long minOperations(int[] nums) {
        // Guard against null, empty, or single-element arrays (already equal -> 0 ops)
        if (nums == null || nums.length <= 1) {
            return 0L;
        }

        // Step 1: Sort ascending so extreme values are paired at opposite ends
        Arrays.sort(nums);

        long operations = 0L;
        int left = 0;
        int right = nums.length - 1;

        // Step 2: Squeeze inward from both ends toward the median
        // Each pair (nums[left], nums[right]) contributes a fixed cost of (nums[right] - nums[left])
        while (left < right) {
            // Cast to long BEFORE subtraction to prevent 32-bit int overflow on large gaps
            operations += (long) nums[right] - nums[left];
            left++;
            right--;
        }

        return operations;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3};
        System.out.println("Test 1 ([1, 2, 3]):         " + minOperations(nums1) + " | Expected: 2");

        int[] nums2 = {1, 10, 2, 9};
        System.out.println("Test 2 ([1, 10, 2, 9]):     " + minOperations(nums2) + " | Expected: 16");

        int[] nums3 = {-1_000_000_000, 1_000_000_000};
        System.out.println("Test 3 ([-10^9, 10^9]):     " + minOperations(nums3) + " | Expected: 2000000000");
    }
}