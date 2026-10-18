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
 * HACKERRANK QUESTION: K-diff Pairs in Financial Series
 * ============================================================================
 *
 * Problem Statement:
 * An algorithmic market surveillance system monitors an integer sequence of
 * transaction prices denoted by `nums`. The system flags unusual spread
 * activity by detecting unique price pairs that exhibit an exact absolute spread of k.
 *
 * Given an integer array nums and an integer k, return the number of UNIQUE
 * k-diff pairs in the array.
 *
 * A k-diff pair is an integer pair (nums[i], nums[j]) such that:
 *   1. 0 <= i, j < nums.length
 *   2. i != j
 *   3. |nums[i] - nums[j]| == k
 *
 * Notice that pair (x, y) and (y, x) are considered the exact same pair.
 * The answer must only count unique pairs.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the number of elements in nums.
 * - Next n lines: Each line contains an integer nums[i].
 * - Last line: An integer k, denoting the target difference.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - -10^7 <= nums[i] <= 10^7
 * - 0 <= k <= 10^7
 *
 * Example 1:
 * Input:
 *   5
 *   3
 *   1
 *   4
 *   1
 *   5
 *   2
 * Output:
 *   2
 * Explanation:
 * The 2-diff pairs are (1, 3) and (3, 5). Although 1 appears twice, the pair
 * (1, 3) is counted only once.
 *
 * Example 2:
 * Input:
 *   5
 *   1
 *   2
 *   3
 *   4
 *   5
 *   1
 * Output:
 *   4
 * Explanation:
 * The four 1-diff pairs are (1, 2), (2, 3), (3, 4), and (4, 5).
 *
 * Example 3:
 * Input:
 *   5
 *   1
 *   3
 *   1
 *   5
 *   4
 *   0
 * Output:
 *   1
 * Explanation:
 * The only 0-diff pair is (1, 1).
 *
 * ============================================================================
 * LOGIC & APPROACH (Frequency Map):
 * ============================================================================
 * 1. Corner Case:
 *    - If k < 0, an absolute difference |nums[i] - nums[j]| can never be negative.
 *      Return 0 immediately.
 *
 * 2. Frequency Mapping:
 *    Populate a HashMap storing the count of occurrences for each number in `nums`.
 *
 * 3. Case A: k == 0 (Zero Spread / Duplicate Search)
 *    - A pair (x, x) requires x to appear at least twice in the array.
 *    - Iterate over the entries in the frequency map: if count >= 2, increment result.
 *
 * 4. Case B: k > 0 (Positive Spread)
 *    - For each unique key x in the frequency map, check if (x + k) exists in the map.
 *    - Checking only (x + k) — rather than both (x + k) and (x - k) — guarantees
 *      that every unordered pair (x, x + k) is counted exactly once without
 *      requiring extra sets or deduplication checks.
 *
 * Complexity:
 * - Time: O(n) average time complexity for populating and querying the HashMap.
 * - Space: O(n) auxiliary space to store unique frequencies.
 *
 * ============================================================================
 * DRY RUN (nums = [3, 1, 4, 1, 5], k = 2):
 * ============================================================================
 * Frequency Map:
 *   1 -> 2
 *   3 -> 1
 *   4 -> 1
 *   5 -> 1
 *
 * Iterate over unique keys:
 * - Key = 1: Check (1 + 2) = 3. Is 3 in map? Yes -> pair (1, 3) found -> count = 1
 * - Key = 3: Check (3 + 2) = 5. Is 5 in map? Yes -> pair (3, 5) found -> count = 2
 * - Key = 4: Check (4 + 2) = 6. Is 6 in map? No  -> count = 2
 * - Key = 5: Check (5 + 2) = 7. Is 7 in map? No  -> count = 2
 *
 * Final Result: 2
 * ============================================================================
 */
public class SolutionKDiffPairs {

    public static int findPairs(List<Integer> nums, int k) {
        if (nums == null || nums.isEmpty() || k < 0) {
            return 0;
        }

        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        int count = 0;

        if (k == 0) {
            for (int occurrences : freqMap.values()) {
                if (occurrences >= 2) {
                    count++;
                }
            }
        } else {
            for (int key : freqMap.keySet()) {
                if (freqMap.containsKey(key + k)) {
                    count++;
                }
            }
        }

        return count;
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

        int result = findPairs(nums, k);
        System.out.println(result);

        bufferedReader.close();
    }
}