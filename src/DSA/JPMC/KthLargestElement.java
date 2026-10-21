package DSA.JPMC; /**
 * PROBLEM: Kth Largest Element in an Array
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * 
 * EXAMPLE:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 * 
 * LOGIC:
 * We use a Min-Heap (PriorityQueue in Java) of size K.
 * As we iterate through the array, we add elements to the heap.
 * If the heap size exceeds K, we remove the top element (which is the MINIMUM element in the heap).
 * By constantly removing the smallest elements, what remains in the heap are the K largest elements.
 * The top of the heap will be exactly the Kth largest element.
 * 
 * DRY RUN: nums = [3,2,1,5,6,4], k = 2
 * - Add 3: Heap = [3]
 * - Add 2: Heap = [2, 3]
 * - Add 1: Heap = [1, 2, 3] -> Size > 2, poll(). Heap = [2, 3]
 * - Add 5: Heap = [2, 3, 5] -> Size > 2, poll(). Heap = [3, 5]
 * - Add 6: Heap = [3, 5, 6] -> Size > 2, poll(). Heap = [5, 6]
 * - Add 4: Heap = [4, 5, 6] -> Size > 2, poll(). Heap = [5, 6]
 * End of loop. The top of the heap is 5.
 * 
 * COMPLEXITY: Time: O(N log K) (Much faster than O(N log N) sorting for small K). Space: O(K)
 */
import java.util.PriorityQueue;

/**
 * Learning supplement: sorting costs O(n log n); a size-k min-heap costs
 * O(n log k) and O(k) auxiliary space. Examples:
 * {@code [3,2,1,5,6,4], k=2 -> 5} and {@code [3,2,3,1,2,4,5], k=4 -> 4}.
 * Invariant: the heap contains the k largest values seen, so its root is the
 * kth largest. During the scan a larger candidate replaces that root. Ask
 * how duplicate values affect rank.
 */
public class KthLargestElement {
    public static int findKthLargest(int[] nums, int k) {
        // By default, PriorityQueue is a Min-Heap in Java
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            minHeap.add(num);
            
            // If the heap grows larger than k, evict the smallest element
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // The root of the min-heap is the kth largest element overall
        return minHeap.peek();
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;
        System.out.println("Kth largest element: " + findKthLargest(nums, k)); // Output: 5
    }
}