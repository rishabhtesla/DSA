package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION 6: Minimum Start Value
 * ============================================================================
 *
 * Problem Statement:
 * Given an array of integers nums, you start with an initial positive integer
 * startValue.
 *
 * In each iteration, you calculate the step-by-step running sum of startValue
 * plus elements in nums (from left to right).
 *
 * Return the minimum positive integer value of startValue such that the step-by-step
 * running sum is NEVER less than 1 at any point during traversal.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the size of nums.
 * - Next n lines: Each line contains an integer nums[i].
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - -1000 <= nums[i] <= 1000
 *
 * Example:
 * Input:
 *   5
 *   -3
 *   2
 *   -3
 *   4
 *   2
 * Output:
 *   5
 *
 * Explanation:
 * If startValue = 4:
 *   (4 - 3) = 1
 *   (1 + 2) = 3
 *   (3 - 3) = 0  <-- Fails condition! Running sum dropped below 1.
 *
 * If startValue = 5:
 *   (5 - 3) = 2
 *   (2 + 2) = 4
 *   (4 - 3) = 1
 *   (1 + 4) = 5
 *   (5 + 2) = 7
 * Running sum is strictly >= 1 at every step. Therefore, 5 is the minimum startValue.
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Mathematical Condition:
 *    Let P[t] = sum(nums[0...t]) be the prefix sum at step t.
 *    The running sum at step t is:
 *        runningSum[t] = startValue + P[t]
 *
 * 2. Bound Inequality:
 *    We require runningSum[t] >= 1 for all t:
 *        startValue + P[t] >= 1
 *        startValue >= 1 - P[t]
 *
 * 3. Minimal Integer Solution:
 *    To satisfy this for all t, startValue must satisfy:
 *        startValue >= 1 - min(P[t]) for all t
 *    Because startValue must also be positive (startValue >= 1),
 *    if min(P[t]) >= 0, the answer is 1.
 *    Otherwise, startValue = 1 - min(P[t]).
 *
 * Complexity:
 * - Time: O(n) single pass
 * - Space: O(1) auxiliary space
 *
 * ============================================================================
 * DRY RUN (nums = [-3, 2, -3, 4, 2]):
 * ============================================================================
 * Initial: currentSum = 0, minPrefixSum = 0
 *
 * i = 0: num = -3 -> currentSum = -3 | minPrefixSum = min(0, -3) = -3
 * i = 1: num =  2 -> currentSum = -1 | minPrefixSum = min(-3, -1) = -3
 * i = 2: num = -3 -> currentSum = -4 | minPrefixSum = min(-3, -4) = -4
 * i = 3: num =  4 -> currentSum =  0 | minPrefixSum = min(-4, 0) = -4
 * i = 4: num =  2 -> currentSum =  2 | minPrefixSum = min(-4, 2) = -4
 *
 * Result = 1 - minPrefixSum = 1 - (-4) = 5
 * Output: 5
 * ============================================================================
 */
public class SolutionMinimumStartValue {

    public static int minStartValue(List<Integer> nums) {
        if (nums == null || nums.isEmpty()) {
            return 1;
        }

        int currentSum = 0;
        int minPrefixSum = 0;

        for (int num : nums) {
            currentSum += num;
            if (currentSum < minPrefixSum) {
                minPrefixSum = currentSum;
            }
        }

        return 1 - minPrefixSum;
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

        int result = minStartValue(nums);
        System.out.println(result);

        bufferedReader.close();
    }
}