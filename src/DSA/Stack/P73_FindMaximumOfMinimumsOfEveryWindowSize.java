package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * ============================================================================
 * [73 / 73] - MAXIMUM OF MINIMUMS OF EVERY WINDOW SIZE
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer array nums of size n, find the maximum of minimums for every
 *   window size from 1 to n.
 *   Result res[w] is the maximum among the minimums of all contiguous subarrays of size w.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Inverting the Question:
 *     Instead of asking "For each window size w, what is the minimum?", ask:
 *     "For each element `nums[i]`, what is the LARGEST window where it is the MINIMUM?"
 *   - Find Boundaries:
 *     Find `pse[i]` (Previous Smaller Element index) and `nse[i]` (Next Smaller Element index).
 *     Then `nums[i]` is the minimum in a window of max length `len = nse[i] - pse[i] - 1`.
 *   - Fill and Propagate:
 *     Place `nums[i]` into `ans[len] = Math.max(ans[len], nums[i])`.
 *     Observe: Any number that is a valid candidate for window size `k` is automatically
 *     a valid candidate for any window size `< k`.
 *     Therefore, propagate backwards: `ans[i] = Math.max(ans[i], ans[i + 1])`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Monotonic stack passes to compute PSE and NSE + linear fill.
 *   - Space: O(n) - Stack and boundary arrays.
 */
public class P73_FindMaximumOfMinimumsOfEveryWindowSize {

    public static int[] maxOfMin(int[] nums) {
        int n = nums.length;
        int[] pse = new int[n];
        int[] nse = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        // 1. Compute Previous Smaller Element indices
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }
            pse[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        // 2. Compute Next Smaller Element indices
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }
            nse[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // 3. ans[w] stores max of minimums for window of size w (1-indexed)
        int[] ans = new int[n + 1];
        Arrays.fill(ans, Integer.MIN_VALUE);

        for (int i = 0; i < n; i++) {
            int len = nse[i] - pse[i] - 1;
            ans[len] = Math.max(ans[len], nums[i]);
        }

        // 4. Fill gaps from right to left
        for (int i = n - 1; i >= 1; i--) {
            ans[i] = Math.max(ans[i], ans[i + 1]);
        }

        // Return 1-indexed results mapped to 0-indexed array of size n
        return Arrays.copyOfRange(ans, 1, n + 1);
    }

    public static void main(String[] args) {
        int[] nums = {10, 20, 50, 10};
        int[] result = maxOfMin(nums);
        System.out.println("P73 Output: " + Arrays.toString(result));
        // For window sizes [1, 2, 3, 4] -> Expected: [50, 20, 10, 10]
    }
}