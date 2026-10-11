package DSA.DP;

/**
 * ============================================================================
 * [17 / 30] - MAXIMUM PRODUCT SUBARRAY (LeetCode 152)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Given an integer array `nums`, find a contiguous non-empty subarray that has 
 * the largest product, and return the product.
 *
 *
 * 2. THE SIGN-FLIP "AHA!" MOMENT:
 * ----------------------------------------------------------------------------
 * Multiplication behaves differently than addition:
 *   - Multiplying two large positive numbers produces a larger positive number.
 *   - Multiplying a large negative number by ANOTHER negative number flips the sign 
 *     and yields a LARGE POSITIVE number!
 *
 * Therefore, a minimum (most negative) product up to index `i - 1` can suddenly become 
 * the MAXIMUM product at index `i` if `nums[i]` is negative.
 *
 * Dual State Tracking:
 *   At each element `nums[i]`, we must maintain:
 *     1. `curMax`: maximum product ending at index `i`.
 *     2. `curMin`: minimum product ending at index `i`.
 *
 * Transitions:
 *   If `nums[i] < 0`, multiplying by `nums[i]` swaps maximum and minimum potentials.
 *   We can swap `curMax` and `curMin` before calculating:
 *     curMax = Math.max(nums[i], curMax * nums[i])
 *     curMin = Math.min(nums[i], curMin * nums[i])
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * nums = [2, 3, -2, 4, -1]
 * Initial: curMax = 2, curMin = 2, ans = 2
 *
 * i=1 (val = 3):
 *   curMax = max(3, 2 * 3) = 6
 *   curMin = min(3, 2 * 3) = 3
 *   ans = max(2, 6) = 6
 *
 * i=2 (val = -2) -> Negative! Swap curMax and curMin (curMax=3, curMin=6):
 *   curMax = max(-2, 3 * -2) = -2
 *   curMin = min(-2, 6 * -2) = -12
 *   ans = max(6, -2) = 6
 *
 * i=3 (val = 4):
 *   curMax = max(4, -2 * 4) = 4
 *   curMin = min(4, -12 * 4) = -48
 *   ans = max(6, 4) = 6
 *
 * i=4 (val = -1) -> Negative! Swap curMax and curMin (curMax=-48, curMin=4):
 *   curMax = max(-1, -48 * -1) = 48  <-- Negative times negative flips to max!
 *   curMin = min(-1, 4 * -1) = -4
 *   ans = max(6, 48) = 48
 *
 * Result: 48 (Subarray: [2, 3, -2, 4, -1])
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(n) - Single pass.
 * - Space Complexity: O(1) - Three scalar variables.
 */
public class P17_MaximumProductSubarray {

    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int curMax = nums[0];
        int curMin = nums[0];
        int ans = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];

            // When multiplied by a negative number, max becomes min and min becomes max
            if (num < 0) {
                int temp = curMax;
                curMax = curMin;
                curMin = temp;
            }

            curMax = Math.max(num, curMax * num);
            curMin = Math.min(num, curMin * num);

            ans = Math.max(ans, curMax);
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 3, -2, 4};
        System.out.println("P17 Max Product [2, 3, -2, 4]:     " + maxProduct(nums1)); // Expected: 6

        int[] nums2 = {-2, 0, -1};
        System.out.println("P17 Max Product [-2, 0, -1]:       " + maxProduct(nums2)); // Expected: 0

        int[] nums3 = {2, 3, -2, 4, -1};
        System.out.println("P17 Max Product [2, 3, -2, 4, -1]: " + maxProduct(nums3)); // Expected: 48
    }
}