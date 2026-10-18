package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Subarrays with Target XOR Checksum
 * ============================================================================
 *
 * Problem Statement:
 * A transaction audit system validates continuous streams of data packets.
 * Each packet has a 32-bit checksum integer value.
 *
 * Given an integer array nums and an integer k, find the total number of
 * continuous subarrays having bitwise XOR of all elements equal to k.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the length of nums.
 * - Next n lines: Each line contains an integer nums[i].
 * - Last line: An integer k, the target XOR.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - 0 <= nums[i], k <= 10^9
 *
 * Example:
 * Input:
 *   6
 *   4
 *   2
 *   2
 *   6
 *   4
 *   6
 * Output:
 *   4
 *
 * Explanation:
 * The subarrays having XOR sum equal to 6 are:
 * 1. [4, 2] from index 0 to 1 (4 ^ 2 = 6)
 * 2. [2, 2, 6] from index 1 to 3 (2 ^ 2 ^ 6 = 6)
 * 3. [6] at index 3
 * 4. [4, 2, 2, 6, 4] from index 0 to 4 (4 ^ 2 ^ 2 ^ 6 ^ 4 = 6)
 * Total count = 4.
 *
 * ============================================================================
 * LOGIC & APPROACH (Prefix XOR + Hash Map):
 * ============================================================================
 * 1. XOR Invariant:
 *    Let prefixXor[i] = nums[0] ^ nums[1] ^ ... ^ nums[i].
 *    The XOR of a subarray from index j to i is:
 *        subXor(j, i) = prefixXor[i] ^ prefixXor[j - 1]
 *    To satisfy subXor(j, i) == k:
 *        prefixXor[i] ^ prefixXor[j - 1] = k
 *    XORing both sides with prefixXor[j - 1] ^ k gives:
 *        prefixXor[j - 1] = prefixXor[i] ^ k
 *
 * 2. Frequency Hash Map:
 *    - Maintain running `currentPrefixXor`.
 *    - Initialize map with `{0: 1}` to account for subarrays starting at index 0.
 *    - For each element in nums:
 *        currentPrefixXor ^= val
 *        required = currentPrefixXor ^ k
 *        If map contains required, add its frequency to totalCount.
 *        Update currentPrefixXor in map.
 *
 * Complexity:
 * - Time: O(n) average
 * - Space: O(n) for frequency map
 *
 * ============================================================================
 * DRY RUN (nums = [4, 2, 2, 6, 4], k = 6):
 * ============================================================================
 * map = {0: 1}, currentXor = 0, count = 0
 *
 * i = 0: val = 4 -> currentXor = 4
 *        target = 4 ^ 6 = 2 (not in map)
 *        map = {0: 1, 4: 1}
 *
 * i = 1: val = 2 -> currentXor = 4 ^ 2 = 6
 *        target = 6 ^ 6 = 0 (exists with count 1) -> count = 1 [subarray: 4, 2]
 *        map = {0: 1, 4: 1, 6: 1}
 *
 * i = 2: val = 2 -> currentXor = 6 ^ 2 = 4
 *        target = 4 ^ 6 = 2 (not in map)
 *        map = {0: 1, 4: 2, 6: 1}
 *
 * i = 3: val = 6 -> currentXor = 4 ^ 6 = 2
 *        target = 2 ^ 6 = 4 (exists with count 2) -> count = 1 + 2 = 3
 *        map = {0: 1, 4: 2, 6: 1, 2: 1}
 *
 * i = 4: val = 4 -> currentXor = 2 ^ 4 = 6
 *        target = 6 ^ 6 = 0 (exists with count 1) -> count = 3 + 1 = 4
 *        map = {0: 1, 4: 2, 6: 2, 2: 1}
 *
 * Output: 4
 * ============================================================================
 */
public class SolutionSubarrayXOR {

    public static long countSubarraysWithXOR(List<Integer> nums, int k) {
        if (nums == null || nums.isEmpty()) {
            return 0L;
        }

        Map<Integer, Integer> xorFreq = new HashMap<>();
        xorFreq.put(0, 1);

        int currentPrefixXor = 0;
        long totalSubarrays = 0;

        for (int val : nums) {
            currentPrefixXor ^= val;
            int required = currentPrefixXor ^ k;

            if (xorFreq.containsKey(required)) {
                totalSubarrays += xorFreq.get(required);
            }

            xorFreq.put(currentPrefixXor, xorFreq.getOrDefault(currentPrefixXor, 0) + 1);
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

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        long result = countSubarraysWithXOR(nums, k);
        System.out.println(result);

        bufferedReader.close();
    }
}