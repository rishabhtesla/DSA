package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * HACKERRANK QUESTION 9: Subarray Transaction Sum Target
 * ============================================================================
 *
 * Problem Statement:
 * Given an array of integers transactionDeltas representing balance changes,
 * and an integer target, find the total number of continuous subarrays whose
 * elements sum up to target.
 *
 * A subarray is a contiguous non-empty sequence of elements within an array.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the length of transactionDeltas.
 * - Next n lines: Each line contains transactionDeltas[i].
 * - Last line: An integer target.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - -1000 <= transactionDeltas[i] <= 1000
 * - -10^7 <= target <= 10^7
 *
 * Example:
 * Input:
 *   3
 *   1
 *   1
 *   1
 *   2
 * Output:
 *   2
 * Explanation:
 * The subarrays summing to target 2 are:
 * - [1, 1] from index 0 to 1
 * - [1, 1] from index 1 to 2
 *
 * ============================================================================
 * LOGIC & APPROACH (Prefix Sum Hash Map):
 * ============================================================================
 * 1. Relation:
 *    Let prefixSum[i] = sum(arr[0...i]).
 *    The sum of any subarray from j to i is:
 *        sum(arr[j...i]) = prefixSum[i] - prefixSum[j - 1]
 *    To satisfy sum(arr[j...i]) == target:
 *        prefixSum[i] - prefixSum[j - 1] = target
 *        prefixSum[j - 1] = prefixSum[i] - target
 *
 * 2. Algorithm:
 *    - Maintain running `currentPrefixSum`.
 *    - Use a HashMap to store frequencies of prefix sums seen so far.
 *    - Seed map with `map.put(0L, 1)` to handle subarrays starting at index 0.
 *    - At each element:
 *        a. currentPrefixSum += num
 *        b. If map contains (currentPrefixSum - target), add its frequency to count.
 *        c. Put/update currentPrefixSum frequency in the map.
 *
 * Complexity:
 * - Time: O(n) average time using HashMap lookup.
 * - Space: O(n) to store prefix sum occurrences.
 *
 * ============================================================================
 * DRY RUN (arr = [1, -1, 1, 1], target = 2):
 * ============================================================================
 * map = {0: 1}, currentSum = 0, count = 0
 *
 * i = 0: val = 1
 *   currentSum = 1
 *   needed = 1 - 2 = -1 (not in map)
 *   map -> {0: 1, 1: 1}
 *
 * i = 1: val = -1
 *   currentSum = 0
 *   needed = 0 - 2 = -2 (not in map)
 *   map -> {0: 2, 1: 1}
 *
 * i = 2: val = 1
 *   currentSum = 1
 *   needed = 1 - 2 = -1 (not in map)
 *   map -> {0: 2, 1: 2}
 *
 * i = 3: val = 1
 *   currentSum = 2
 *   needed = 2 - 2 = 0
 *   0 is in map with count = 2 -> count += 2 (Subarrays: [-1, 1, 1] and [1, 1])
 *   map -> {0: 2, 1: 2, 2: 1}
 *
 * Total Count = 2
 * ============================================================================
 */
public class SolutionSubarraySumTarget {

    public static int subarraySum(List<Integer> nums, int target) {
        if (nums == null || nums.isEmpty()) {
            return 0;
        }

        Map<Long, Integer> prefixSumFreq = new HashMap<>();
        prefixSumFreq.put(0L, 1);

        long currentPrefixSum = 0;
        int totalSubarrays = 0;

        for (int num : nums) {
            currentPrefixSum += num;
            long complement = currentPrefixSum - target;

            if (prefixSumFreq.containsKey(complement)) {
                totalSubarrays += prefixSumFreq.get(complement);
            }

            prefixSumFreq.put(currentPrefixSum, prefixSumFreq.getOrDefault(currentPrefixSum, 0) + 1);
        }

        return totalSubarrays;
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

        int target = Integer.parseInt(bufferedReader.readLine().trim());

        int result = subarraySum(nums, target);
        System.out.println(result);

        bufferedReader.close();
    }
}