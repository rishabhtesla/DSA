package DSA.JPMC; /**
 * PROBLEM: Top K Frequent Elements
 * Given an integer array nums and an integer k, return the k most frequent elements.
 * You may return the answer in any order. The algorithm must be strictly better than O(N log N).
 * 
 * EXAMPLE:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * 
 * LOGIC:
 * 1. Build a frequency map using a HashMap. (Number -> Count).
 * 2. Create a Min-Heap (PriorityQueue in Java) configured to order elements by their frequency.
 * 3. Add numbers to the heap. If the heap size exceeds 'k', remove the top element 
 *    (which is the one with the LEAST frequency in the current heap).
 * 4. At the end, the heap will contain exactly the 'k' elements with the HIGHEST frequencies.
 * 
 * DRY RUN: nums = [1,1,1,2,2,3], k = 2
 * Frequencies: {1:3, 2:2, 3:1}
 * - Add 1: Heap = [1]
 * - Add 2: Heap = [2, 1] (Ordered by frequency ascending)
 * - Add 3: Heap = [3, 2, 1]. Size > 2, so poll(). '3' (lowest freq) is removed. Heap = [2, 1].
 * Result array: [1, 2]
 * 
 * COMPLEXITY: Time: O(N log K) (Inserting into heap of size K takes log K). Space: O(N) for Map and Heap.
 */
import java.util.*;

public class TopKFrequent {
    public static int[] topKFrequent(int[] nums, int k) {
        // Step 1: Build the frequency map
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int n : nums) {
            frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
        }
        
        // Step 2: Build a Min-Heap based on the frequencies
        // n1 and n2 are keys. We compare their values (frequencies).
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (n1, n2) -> frequencyMap.get(n1) - frequencyMap.get(n2)
        );
        
        // Step 3: Keep the heap size exactly equal to K
        for (int num : frequencyMap.keySet()) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict the least frequent element
            }
        }
        
        // Step 4: Extract the results
        int[] topK = new int[k];
        for (int i = 0; i < k; i++) {
            topK[i] = minHeap.poll();
        }
        
        return topK;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println("Top K Frequent: " + Arrays.toString(topKFrequent(nums, k))); // Output: [2, 1]
    }
}