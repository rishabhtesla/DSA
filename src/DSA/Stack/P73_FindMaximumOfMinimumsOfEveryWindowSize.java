package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * ============================================================================
 * [73 / 73] - MAXIMUM OF MINIMUMS OF EVERY WINDOW SIZE
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an integer array `nums` of size `n`, find the maximum of minimums for every
 * window size from `1` to `n`.
 * Result `res[w]` is the maximum among the minimums of all contiguous subarrays of size `w` (1-indexed).
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Brute Force Approach:
 *    - Generate all possible contiguous windows of size `w`, find the minimum of each, and take the maximum.
 *    - Inefficient: takes $O(n^3)$ or $O(n^2)$ time, which will result in TLE.
 *
 * 2. Optimal Monotonic Stack & Backward Propagation Approach (O(n) Time | O(n) Space):
 *    - **The Core Insight (Inverting the Question):** Instead of asking "For each window size `w`, what is the minimum?",
 *      ask: *"For each element `nums[i]`, what is the LARGEST window length where it acts as the absolute minimum?"*
 *    - **Step 1:** Compute the Previous Smaller Element index (`pse[i]`) and Next Smaller Element index (`nse[i]`) for each bar
 *      using monotonic increasing stacks.
 *    - **Step 2:** The maximal window length where `nums[i]` is the minimum is `len = nse[i] - pse[i] - 1`.
 *      Store the candidate into `ans[len] = Math.max(ans[len], nums[i])`.
 *    - **Step 3 (Backward Propagation):** Observe that any number that is a valid minimum for a window size `k`
 *      is automatically a valid candidate for any smaller window size `< k`. Propagate values backwards:
 *      `ans[i] = Math.max(ans[i], ans[i + 1])`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Two linear monotonic stack passes to compute boundaries, followed by linear filling and propagation.
 * - Space: O(n) - Auxiliary storage for stacks, boundary index arrays (`pse`, `nse`), and answer tracking arrays.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - `nums = [10, 20, 50, 10]`
 * - Expected Maximum of Minimums for window sizes `[1, 2, 3, 4]`: `[50, 20, 10, 10]`
 *
 * VISUAL DRY RUN (for `[10, 20, 50, 10]`):
 *   - Monotonic stack spans give each element's maximal valid window:
 *     - `50` spans window size 1 (candidate `50`).
 *     - `20` spans window size 2 (candidate `20`).
 *     - Rightmost `10` spans window size 4 (candidate `10`).
 *   - Filling and propagating backwards fills missing window gaps, yielding `[50, 20, 10, 10]`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is backward propagation (`ans[i] = Math.max(ans[i], ans[i + 1])`) logically sound?
 *    (If a subarray of size `k` has a minimum value `v`, any smaller subarray nested inside it
 *    can potentially feature `v` or an even larger minimum, meaning larger window capabilities cascade downward.)
 * 2. Why do we map results using `Arrays.copyOfRange(ans, 1, n + 1)`?
 *    (Because window sizes are 1-indexed (from `1` to `n`), offsetting the 0th index makes boundary mapping
 *    cleaner before returning the final 0-indexed result array.)
 */
public class P73_FindMaximumOfMinimumsOfEveryWindowSize {

    public static int[] maxOfMin(int[] nums) {
        int n = nums.length;
        int[] pse = new int[n];
        int[] nse = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        // Step 1: Compute Previous Smaller Element (PSE) indices
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }
            pse[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        // Step 2: Compute Next Smaller Element (NSE) indices
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }
            nse[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // Step 3: ans[w] stores the max of minimums for a window of size w (1-indexed)
        int[] ans = new int[n + 1];
        Arrays.fill(ans, Integer.MIN_VALUE);

        for (int i = 0; i < n; i++) {
            int len = nse[i] - pse[i] - 1;
            ans[len] = Math.max(ans[len], nums[i]);
        }

        // Step 4: Fill gaps from right to left (cascade larger window minimums downward)
        for (int i = n - 1; i >= 1; i--) {
            ans[i] = Math.max(ans[i], ans[i + 1]);
        }

        // Return 1-indexed results mapped to a 0-indexed array of size n
        return Arrays.copyOfRange(ans, 1, n + 1);
    }

    public static void main(String[] args) {
        int[] nums = {10, 20, 50, 10};
        int[] result = maxOfMin(nums);
        System.out.println("P73 Output: " + Arrays.toString(result));
        // Expected for window sizes [1, 2, 3, 4]: [50, 20, 10, 10]
    }
}