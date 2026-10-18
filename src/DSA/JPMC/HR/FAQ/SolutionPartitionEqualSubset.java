package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Partition Equal Ledger Sum
 * ============================================================================
 *
 * Problem Statement:
 * Given an integer array nums representing financial liabilities, determine if
 * the array can be partitioned into two subsets such that the sum of elements
 * in both subsets is strictly equal.
 *
 * Constraints:
 * - 1 <= nums.length <= 200
 * - 1 <= nums[i] <= 100
 *
 * Logic:
 * 1. Total sum must be even; if totalSum % 2 != 0, return false.
 * 2. Target = totalSum / 2.
 * 3. 1D Boolean DP array of size (target + 1):
 *    dp[s] = dp[s] || dp[s - num], iterating backwards to prevent reuse.
 * ============================================================================
 */
public class SolutionPartitionEqualSubset {

    public static boolean canPartition(List<Integer> nums) {
        if (nums == null || nums.size() <= 1) return false;

        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        if (totalSum % 2 != 0) return false;

        int target = totalSum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int s = target; s >= num; s--) {
                dp[s] = dp[s] || dp[s - num];
            }
        }

        return dp[target];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) return;

        int n = Integer.parseInt(nLine.trim());
        List<Integer> nums = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            nums.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        System.out.println(canPartition(nums));
        bufferedReader.close();
    }
}