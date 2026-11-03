package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P36: TOP K FREQUENT ELEMENTS
 *
 * Question
 * Return the k values with highest frequencies.
 *
 * Example
 * [1,1,1,2,2,3], k=2 -> [1,2].
 *
 * Constraints
 * 1 <= n <= 10^5; answer is unique; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Count with a map, sort distinct keys by descending frequency, and take k.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 *   Frequency visual for [1,1,1,2,2,3], k = 2:
 *     frequencies: 1 -> 3, 2 -> 2, 3 -> 1
 *     sort values by frequency descending -> [1,2,3]
 *     take the first 2 values -> [1,2]
 *
 * Complexity
 * O(n log n) time and O(n) auxiliary space.
 */
public class P36_TopKFrequentElements {
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : nums) m.put(x, m.getOrDefault(x, 0) + 1);
        List<Integer> l = new ArrayList<>(m.keySet());
        l.sort((x, y) -> m.get(y) - m.get(x));
        return l.subList(0, k).stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));
    }
}
