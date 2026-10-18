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
 * HACKERRANK QUESTION 12: Top K Frequent Security Identifiers
 * ============================================================================
 *
 * Problem Statement:
 * Given an integer array idList representing transaction stock security IDs and
 * an integer k, return the k most frequent elements.
 *
 * You may return the answer in any order. Solutions with time complexity better
 * than O(n log n) are expected.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the length of idList.
 * - Next n lines: Each line contains an integer idList[i].
 * - Last line: An integer k.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - -10^4 <= idList[i] <= 10^4
 * - k is in the range [1, the number of unique elements in the array].
 * - It is guaranteed that the answer is unique.
 *
 * Example:
 * Input:
 *   6
 *   1
 *   1
 *   1
 *   2
 *   2
 *   3
 *   2
 * Output:
 *   1
 *   2
 * Explanation:
 * 1 appears 3 times, 2 appears 2 times, 3 appears 1 time.
 * The 2 most frequent elements are 1 and 2.
 *
 * ============================================================================
 * LOGIC & APPROACH (Bucket Sort for O(n) Runtime):
 * ============================================================================
 * 1. Count Frequencies:
 *    Map each unique number to its occurrence frequency using a HashMap.
 *
 * 2. Bucket Formulation:
 *    Create an array of lists `buckets` of size n + 1, where index `f` stores
 *    all elements that appeared exactly `f` times.
 *    The maximum possible frequency of any element is n.
 *
 * 3. Extraction:
 *    Iterate backwards from bucket index n down to 1.
 *    Collect elements until exactly k elements have been accumulated.
 *
 * Complexity:
 * - Time: O(n) linear time — O(n) for frequency count, O(n) for bucket population,
 *   and O(n) worst-case to harvest top k elements.
 * - Space: O(n) auxiliary space for the map and bucket lists.
 *
 * ============================================================================
 * DRY RUN (idList = [1, 1, 1, 2, 2, 3], k = 2):
 * ============================================================================
 * Frequency Map: {1: 3, 2: 2, 3: 1}
 * n = 6
 * Buckets array of size 7:
 * - bucket[0] = []
 * - bucket[1] = [3]
 * - bucket[2] = [2]
 * - bucket[3] = [1]
 * - bucket[4..6] = []
 *
 * Reverse Collection:
 * - Scan bucket[6] -> empty
 * - Scan bucket[5] -> empty
 * - Scan bucket[4] -> empty
 * - Scan bucket[3] -> contains [1] -> result.add(1), kRemaining = 1
 * - Scan bucket[2] -> contains [2] -> result.add(2), kRemaining = 0 -> STOP!
 *
 * Result: [1, 2]
 * ============================================================================
 */
public class SolutionTopKFrequent {

    public static List<Integer> topKFrequent(List<Integer> nums, int k) {
        if (nums == null || nums.isEmpty() || k <= 0) {
            return new ArrayList<>();
        }

        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new ArrayList[nums.size() + 1];

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(entry.getKey());
        }

        List<Integer> result = new ArrayList<>(k);

        for (int i = buckets.length - 1; i >= 0 && result.size() < k; i--) {
            if (buckets[i] != null) {
                for (int val : buckets[i]) {
                    result.add(val);
                    if (result.size() == k) {
                        break;
                    }
                }
            }
        }

        return result;
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

        List<Integer> result = topKFrequent(nums, k);
        for (int val : result) {
            System.out.println(val);
        }

        bufferedReader.close();
    }
}