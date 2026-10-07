package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * PROBLEM STATEMENT:
 * Given an integer array 'nums' and an integer 'k', return the k-th largest element in the array [00:00:54].
 * Note that it is the k-th largest element in the sorted order, not the k-th distinct element [00:01:05].
 * Can you solve it without sorting? [00:01:11].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [3, 2, 1, 5, 6, 4], k = 2 [00:01:16]
 * - Process: The 2nd largest element inside the sorted arrangement is 5 [00:01:28].
 * - Result: 5
 * 
 * Example 2: nums = [7, 6, 3, 5, 4, 8, 9, 10, 1, 2], k = 4 (Simulated inside the video explanation [00:10:29])
 * - Process:
 *   - Initialize a Min-PriorityQueue (Min-Heap) and insert the first k = 4 elements: [7, 6, 3, 5] [00:11:03].
 *   - The head (peak) of the heap represents the current smallest (weakest) element, which is 3 [00:11:10].
 *   - Element 4 arrives: 4 > 3 -> Evict 3, insert 4. Heap becomes [7, 6, 5, 4] with peak = 4 [00:11:22].
 *   - Element 8 arrives: 8 > 4 -> Evict 4, insert 8. Heap becomes [7, 6, 5, 8] with peak = 5 [00:11:48].
 *   - Element 9 arrives: 9 > 5 -> Evict 5, insert 9. Heap becomes [7, 6, 9, 8] with peak = 6 [00:12:09].
 *   - Element 10 arrives: 10 > 6 -> Evict 6, insert 10. Heap becomes [7, 10, 9, 8] with peak = 7 [00:12:27].
 *   - Elements 1 and 2 arrive: Both are smaller than the peak (7), so they are strictly ignored [00:12:41, 00:12:50].
 *   - The final peak of the min-heap holds the k-th largest value, which is 7 [00:13:05].
 * - Result: 7
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Bounded Min-Heap / PriorityQueue):
 * • Index Initialization: Single loop index variable 'i' iterates from 0 up to nums.length - 1 [00:08:46].
 * • Condition Boundaries: If the heap size is strictly less than 'k', add elements unconditionally [00:09:04]. 
 *   Otherwise, evaluate elements against the heap's peak value [00:09:19].
 * • Operational Steps:
 *   1. Initialize a `PriorityQueue<Integer>` acting natively as a Min-Heap [00:08:23].
 *   2. Push items into the heap until its structural capacity reaches exactly 'k' components [00:09:08].
 *   3. For subsequent items, if `nums[i] > minHeap.peek()`, pop the peak element and insert `nums[i]` [00:09:29, 00:09:43].
 *   4. After processing terminates, return `minHeap.peek()` as the final answer [00:09:59].
 * • Time Complexity: O(n log k) - Maintains a constrained heap size bounded tightly to 'k' items.
 * • Space Complexity: O(k) - Limits auxiliary memory retention strictly to 'k' internal allocations [00:06:18].
 * • LOGIC BEHIND THIS APPROACH:
 *   By buffering up to 'k' entries inside a Min-Heap, the root always stores the absolute minimum element 
 *   within our prospective target window [00:04:37]. When a larger candidate arrives, it displaces the smaller 
 *   root element, ensuring the heap continuously retains the 'k' largest elements encountered [00:07:46].
 * 
 * ---
 * VISUAL DRY RUN (nums = [7, 6, 3, 5, 4, 8, 9, 10, 1, 2], k = 4):
 * Heap State Sequence:
 * Initializing (Size < 4): Push 7 -> [7] | Push 6 -> [6, 7] | Push 3 -> [3, 6, 7] | Push 5 -> [3, 5, 6, 7] (Peak = 3) [00:11:03].
 * Scan Phase (Size == 4):
 * i = 4: nums[4]=4 > 3 -> Poll 3, Add 4. Heap = [4, 5, 6, 7] (Peak = 4) [00:11:22].
 * i = 5: nums[5]=8 > 4 -> Poll 4, Add 8. Heap = [5, 6, 7, 8] (Peak = 5) [00:11:48].
 * i = 6: nums[6]=9 > 5 -> Poll 5, Add 9. Heap = [6, 7, 8, 9] (Peak = 6) [00:12:09].
 * i = 7: nums[7]=10 > 6 -> Poll 6, Add 10. Heap = [7, 8, 9, 10] (Peak = 7) [00:12:27].
 * i = 8: nums[8]=1 <= 7 -> Skip execution [00:12:41].
 * i = 9: nums[9]=2 <= 7 -> Skip execution [00:12:50].
 * Loop End. Return minHeap.peek() = 7 [00:13:05].
 */
public class KthLargestElement {

    // APPROACH 1: Bounded Min-Heap / PriorityQueue (Anchor Strategy)
    public static int findKthLargestHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) return -1;

        // Java PriorityQueue behaves naturally as a Min-Heap layout [00:08:23]
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            // Keep stuffing items until the heap matches our targeted window threshold [00:09:04]
            if (minHeap.size() < k) {
                minHeap.add(num);
            } 
            // If incoming element is greater than the weakest player, perform substitution [00:09:29]
            else if (num > minHeap.peek()) {
                minHeap.poll(); // Evict current minimum barrier [00:09:43]
                minHeap.add(num); // Insert stronger element candidate [00:09:50]
            }
        }

        // Root node captures the k-th largest boundary element [00:09:59]
        return minHeap.peek();
    }

    // APPROACH 2: In-Place Sorting Strategy
    // Sorts the primitive array natively using standard Dual-Pivot Quicksort.
    // Provides a straightforward alternative design pattern with O(n log n) time and O(1) extra space.
    public static int findKthLargestSort(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) return -1;
        
        Arrays.sort(nums);
        
        // Return element mapped back from the trailing partition end [00:02:17]
        return nums[nums.length - k];
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Forcing stream collections to reverse-sort box objects degrades performance,
    // introducing substantial auxiliary memory tracking structures instead of processing arrays in-place.
    public static int findKthLargestStream(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) return -1;

        return Arrays.stream(nums)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .skip(k - 1)
                .findFirst()
                .orElse(-1);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Classic Setup) ---
        int[] test1 = {3, 2, 1, 5, 6, 4};
        int res1_1 = findKthLargestHeap(test1, 2);
        int res1_2 = findKthLargestSort(test1.clone(), 2);
        int res1_3 = findKthLargestStream(test1, 2);

        System.out.println("Test Case 1: [3, 2, 1, 5, 6, 4], k = 2");
        System.out.println("Approach 1 (Bounded Heap) Result: " + res1_1);
        System.out.println("Approach 2 (In-Place Sort) Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 5 && res1_2 == 5 && res1_3 == 5 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation - Zero Pockets) ---
        int[] test2 = {7, 6, 3, 5, 4, 8, 9, 10, 1, 2};
        int res2_1 = findKthLargestHeap(test2, 4);
        int res2_2 = findKthLargestSort(test2.clone(), 4);
        int res2_3 = findKthLargestStream(test2, 4);

        System.out.println("Test Case 2: [7, 6, 3, 5, 4, 8, 9, 10, 1, 2], k = 4");
        System.out.println("Approach 1 (Bounded Heap) Result: " + res2_1);
        System.out.println("Approach 2 (In-Place Sort) Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 7 && res2_2 == 7 && res2_3 == 7 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}