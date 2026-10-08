package DSA.agoda;

import java.util.Arrays;

/**
 * ============================================================================
 * [AGODA HACKERRANK - 02] MINIMIZE OPERATIONS TO MAKE ARRAY ELEMENTS EQUAL
 * ============================================================================
 * 
 * SOURCE:
 *   Agoda HackerRank Online Assessment (Senior/Staff rounds).
 * 
 * PROBLEM:
 *   Given an array of integers nums, find the minimum number of operations required
 *   to make all array elements equal. In one operation, you can either increment
 *   or decrement any element by 1 (L1-norm distance).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Mean vs Median:
 *     Candidates often mistake this for minimizing squared differences (which uses the Mean).
 *     To minimize absolute deviations `sum(|nums[i] - k|)`, the optimal target `k` 
 *     is the MEDIAN of the array!
 *   - Why the Median?
 *     If you pick a target `k`, shifting `k` to the right increases the distance for all 
 *     elements to the left and decreases it for all elements to the right. 
 *     Balance is achieved strictly when count(left) == count(right), which is the median.
 *   - Two-Pointer Convergence without explicit median indexing:
 *     Sort array. For every pair `(nums[left], nums[right])`, the cost to bring both 
 *     to ANY common point between them is constant: `nums[right] - nums[left]`.
 *
 * COMPLEXITY:
 *   - Time:  O(n log n) - Dominated by sorting (or O(n) via Quickselect).
 *   - Space: O(1)       - In-place pointers.
 */
public class AG_HR02_MinimumOperationsEqualArray {

    public static long minOperations(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;

        Arrays.sort(nums);

        long operations = 0;
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            operations += (nums[right] - nums[left]);
            left++;
            right--;
        }

        return operations;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3};
        System.out.println("AG_HR02 Output (Test 1): " + minOperations(nums1)); 
        // Expected: 2 (Target median = 2; 1->2 (1 op), 3->2 (1 op))

        int[] nums2 = {1, 10, 2, 9};
        System.out.println("AG_HR02 Output (Test 2): " + minOperations(nums2)); 
        // Expected: 16 (Pairs: 9-2 = 7, 10-1 = 9 -> Total: 16)
    }
}