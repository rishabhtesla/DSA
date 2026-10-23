package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the k values with highest frequencies.
 * EXAMPLE: [1,1,1,2,2,3], k=2 -> [1,2].
 * CONSTRAINTS: 1 <= n <= 10^5; answer is unique; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Count with a map, sort distinct keys by descending frequency, and take k.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n log n) time and O(n) auxiliary space.
 * DRY RUN: Main input [1,1,1,2,2,3], k=2 -> [1,2].; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P36_TopKFrequentElements {
    public static int[] topKFrequent(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.put(x, m.getOrDefault(x, 0) + 1);
        List<Integer> l = new ArrayList<>(m.keySet());
        l.sort((x, y) -> m.get(y) - m.get(x));
        return l.subList(0, k).stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));
    }
}
