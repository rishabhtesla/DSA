package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION 7: Maximize Array Value After Prefix Redistribution
 * ============================================================================
 *
 * Problem Statement:
 * You are given a 0-indexed integer array nums of length n.
 *
 * In one operation, you must:
 * 1. Choose an integer i such that 1 <= i < n and nums[i] > 0.
 * 2. Decrease nums[i] by 1.
 * 3. Increase nums[i - 1] by 1.
 *
 * You can perform this operation any number of times.
 *
 * Return the minimum possible maximum integer in nums after performing any
 * number of operations.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the length of nums.
 * - Next n lines: Each line contains an integer nums[i].
 *
 * Constraints:
 * - 2 <= n <= 10^5
 * - 0 <= nums[i] <= 10^9
 *
 * Example:
 * Input:
 *   4
 *   3
 *   7
 *   1
 *   6
 * Output:
 *   5
 *
 * Explanation:
 * - One optimal sequence of operations:
 *   1. Choose i = 1, decrease nums[1] and increase nums[0]: [4, 6, 1, 6]
 *   2. Choose i = 1, decrease nums[1] and increase nums[0]: [5, 5, 1, 6]
 *   3. Choose i = 3, decrease nums[3] and increase nums[2]: [5, 5, 2, 5]
 * - The maximum element is now 5. It cannot be reduced further.
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Monotonicity:
 *    Notice operations can only move values to the left (from i to i - 1), never
 *    to the right. If it is possible to achieve a maximum value M, it is also
 *    possible to achieve any target > M. This monotonic property enables
 *    Binary Search on the Answer.
 *
 * 2. Verification Function (canAchieve(target)):
 *    Iterate from right to left (index n - 1 down to 1):
 *    - If nums[i] + excess > target, the spillover must be pushed to index i - 1:
 *        excess = (nums[i] + excess) - target
 *    - Else, excess is absorbed, so excess = 0.
 *    - Finally, check if (nums[0] + excess) <= target.
 *
 * 3. Search Space:
 *    - low = 0
 *    - high = max(nums[i])
 *
 * Complexity:
 * - Time: O(n * log(max_val))
 * - Space: O(1) auxiliary space
 *
 * ============================================================================
 * DRY RUN (nums = [3, 7, 1, 6], Target = 5):
 * ============================================================================
 * i = 3: val = 6 | excess = 0 -> total = 6. 6 > 5 -> push excess = 1 to i = 2.
 * i = 2: val = 1 | excess = 1 -> total = 2. 2 <= 5 -> excess absorbed (excess = 0).
 * i = 1: val = 7 | excess = 0 -> total = 7. 7 > 5 -> push excess = 2 to i = 0.
 * i = 0: val = 3 | excess = 2 -> total = 5. 5 <= 5 -> Valid!
 *
 * Result for target 5: feasible.
 * Binary search converges to 5.
 * ============================================================================
 */
public class SolutionMaximizeArrayValue {

    public static int minimizeArrayValue(List<Integer> nums) {
        long low = 0;
        long high = 0;

        for (int num : nums) {
            high = Math.max(high, num);
        }

        long ans = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (isPossible(nums, mid)) {
                ans = mid;
                high = mid - 1; // Try to minimize further
            } else {
                low = mid + 1;  // Target mid is too small
            }
        }

        return (int) ans;
    }

    private static boolean isPossible(List<Integer> nums, long target) {
        long excess = 0;
        for (int i = nums.size() - 1; i > 0; i--) {
            long total = nums.get(i) + excess;
            if (total > target) {
                excess = total - target;
            } else {
                excess = 0;
            }
        }
        return (nums.get(0) + excess) <= target;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Integer> nums = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            nums.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        int result = minimizeArrayValue(nums);
        System.out.println(result);

        bufferedReader.close();
    }
}