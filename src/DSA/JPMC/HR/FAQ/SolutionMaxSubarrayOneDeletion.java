package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Maximum Subarray Sum with At Most One Deletion
 * ============================================================================
 *
 * Problem Statement:
 * Given an integer array nums representing financial profit/loss records, return
 * the maximum sum of a non-empty subarray with at most one element deleted.
 *
 * You may choose to delete zero elements or delete exactly one element. After
 * deletion, the remaining subarray must not be empty.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, the size of the array nums.
 * - Next n lines: Each line contains an integer nums[i].
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 *
 * Example 1:
 * Input:
 *   4
 *   1
 *   -2
 *   0
 *   3
 * Output:
 *   4
 * Explanation: Choose [1, -2, 0, 3] and delete -2 to get [1, 0, 3] with sum 4.
 *
 * Example 2:
 * Input:
 *   3
 *   -1
 *   -2
 *   -3
 * Output:
 *   -1
 * Explanation: Deleting one negative still leaves a non-empty subarray; the maximum
 *              single non-empty subarray is [-1].
 *
 * ============================================================================
 * LOGIC & APPROACH (2-State Dynamic Programming / Kadane's Extension):
 * ============================================================================
 * 1. State Definition at index i:
 *    - `noDelete`: Maximum subarray sum ending at index i with 0 deletions.
 *      Standard Kadane's: noDelete = max(nums[i], noDelete + nums[i])
 *    - `oneDelete`: Maximum subarray sum ending at index i with exactly 1 deletion.
 *      Two possibilities to achieve this:
 *        a. We delete the CURRENT element nums[i]: sum is `prevNoDelete`.
 *        b. We already deleted an element earlier: sum is `oneDelete + nums[i]`.
 *      Therefore: oneDelete = max(prevNoDelete, oneDelete + nums[i])
 *
 * 2. Invariants:
 *    - Initialize `noDelete = nums[0]`, `oneDelete = 0`.
 *    - Global answer initialized to `nums[0]`.
 *
 * Complexity:
 * - Time: O(n) single pass
 * - Space: O(1) auxiliary variables
 *
 * ============================================================================
 * DRY RUN (nums = [1, -2, 0, 3]):
 * ============================================================================
 * Base state:
 * - noDelete = 1, oneDelete = 0, maxSoFar = 1
 *
 * i = 1 (val = -2):
 * - oneDelete = max(prevNoDelete=1, oneDelete + (-2) = -2) = 1
 * - noDelete = max(-2, 1 + (-2) = -1) = -1
 * - maxSoFar = max(1, 1, -1) = 1
 *
 * i = 2 (val = 0):
 * - oneDelete = max(prevNoDelete=-1, 1 + 0 = 1) = 1
 * - noDelete = max(0, -1 + 0 = -1) = 0
 * - maxSoFar = max(1, 1, 0) = 1
 *
 * i = 3 (val = 3):
 * - oneDelete = max(prevNoDelete=0, 1 + 3 = 4) = 4
 * - noDelete = max(3, 0 + 3 = 3) = 3
 * - maxSoFar = max(1, 4, 3) = 4
 *
 * Final Result: 4
 * ============================================================================
 */
public class SolutionMaxSubarrayOneDeletion {

    public static int maximumSum(List<Integer> nums) {
        if (nums == null || nums.isEmpty()) {
            return 0;
        }

        int noDelete = nums.get(0);
        int oneDelete = 0;
        int maxSoFar = nums.get(0);

        for (int i = 1; i < nums.size(); i++) {
            int val = nums.get(i);

            int prevNoDelete = noDelete;
            noDelete = Math.max(val, noDelete + val);

            if (i == 1) {
                oneDelete = prevNoDelete; // Only choice is to drop current element
            } else {
                oneDelete = Math.max(prevNoDelete, oneDelete + val);
            }

            maxSoFar = Math.max(maxSoFar, Math.max(noDelete, oneDelete));
        }

        return maxSoFar;
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

        int result = maximumSum(nums);
        System.out.println(result);

        bufferedReader.close();
    }
}