package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * PROBLEM STATEMENT:
 * You are given an array of integers 'stones' where stones[i] is the weight of the i-th stone [00:00:16].
 * We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together [00:00:20].
 * Suppose the heaviest two stones have weights x and y with x <= y [00:00:41]. The result of this smash is:
 * - If x == y, both stones are completely destroyed [00:00:54].
 * - If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x [00:01:00].
 * At the end of the game, there is at most one stone left [00:01:06]. Return the weight of the last remaining stone. 
 * If there are no stones left, return 0 [00:01:15].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: stones = [2, 7, 4, 1, 8, 1] [00:01:30]
 * - Process: 
 *   - Smash 8 and 7 -> result 1. Array becomes [2, 4, 1, 1, 1] [00:01:38].
 *   - Smash 4 and 2 -> result 2. Array becomes [2, 1, 1, 1] [00:01:51].
 *   - Smash 2 and 1 -> result 1. Array becomes [1, 1, 1] [00:01:55].
 *   - Smash 1 and 1 -> result 0 (both destroyed). Array becomes [1] [00:01:59].
 * - Result: 1 [00:02:05]
 * 
 * Example 2: stones = [8, 6, 7, 3] (Simulated inside the video explanation [00:09:59])
 * - Process:
 *   - Load elements into a Max-Heap (PriorityQueue with Collections.reverseOrder()) [00:06:20].
 *   - Turn 1: Poll 8 and 7 -> diff = 8 - 7 = 1. Add 1 back to heap [00:10:23]. Heap: [6, 3, 1].
 *   - Turn 2: Poll 6 and 3 -> diff = 6 - 3 = 3. Add 3 back to heap [00:10:37]. Heap: [3, 1].
 *   - Turn 3: Poll 3 and 1 -> diff = 3 - 1 = 2. Add 2 back to heap [00:10:52]. Heap: [2].
 *   - Loop terminates since heap size is now 1 (not > 1) [00:11:01].
 * - Result: 2 [00:11:10]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Max-Heap PriorityQueue):
 * • Index Initialization: No explicit array index pointer is used; state mutations are entirely managed 
 *   via sequential polling on a priority container [00:06:46].
 * • Condition Boundaries: Processing driving loops run dynamically while `maxHeap.size() > 1` [00:07:36].
 * • Operational Steps:
 *   1. Initialize a `PriorityQueue<Integer>` configured with `Collections.reverseOrder()` to function as a Max-Heap [00:05:59].
 *   2. Push all elements of the primitive array directly into the heap structure [00:06:46].
 *   3. Inside the loop, extract the two largest elements using `.poll()` [00:07:50, 00:07:59].
 *   4. Compute the difference. If `stone1 - stone2 > 0`, insert the difference back into the max-heap [00:08:15].
 *   5. Post-loop check: if the heap is empty, return 0 [00:09:04]; otherwise, return the final top item [00:09:08].
 * • Time Complexity: O(n log n) - Extracting or inserting items requires logarithmic O(log n) time per step [00:05:35].
 * • Space Complexity: O(n) - Auxiliary space needed to allocate the underlying tree network structure [00:06:11].
 * • LOGIC BEHIND THIS APPROACH:
 *   Sorting the array repeatedly after each smash is highly inefficient, leading to an O(n^2 log n) complexity [00:03:51]. 
 *   A Max-Heap provides constant-time O(1) access to the largest elements and updates in logarithmic time O(log n) [00:05:29]. 
 *   This ensures that the two heaviest stones are always processed first throughout all smashing rounds [00:04:35].
 * 
 * ---
 * VISUAL DRY RUN (stones = [8, 6, 7, 3]):
 * Initial: heap = [8, 7, 6, 3] [00:09:59]
 * Turn 1: max = poll() -> 8. secondMax = poll() -> 7 [00:07:50, 00:07:59].
 *         diff = 8 - 7 = 1. Since 1 != 0, push(1) [00:08:15]. Heap = [6, 3, 1] [00:10:23].
 * Turn 2: max = poll() -> 6. secondMax = poll() -> 3.
 *         diff = 6 - 3 = 3. Since 3 != 0, push(3). Heap = [3, 1] [00:10:37].
 * Turn 3: max = poll() -> 3. secondMax = poll() -> 1.
 *         diff = 3 - 1 = 2. Since 2 != 0, push(2). Heap = [2] [00:10:52].
 * Loop End: maxHeap.size() == 1. Check empty boundary: false [00:11:01].
 * Return maxHeap.poll() = 2 [00:11:10].
 */
public class LastStoneWeight {

    // APPROACH 1: Max-Heap PriorityQueue (Anchor Strategy)
    public static int lastStoneWeightOptimal(int[] stones) {
        if (stones == null || stones.length == 0) return 0;
        
        // Instantiate Java PriorityQueue explicitly configured with reverse order comparator flags [00:05:59]
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        // Bulk push entries directly to establish tree nodes [00:06:46]
        for (int stone : stones) {
            maxHeap.add(stone);
        }
        
        // Smashing loop runs until less than 2 elements remain [00:07:36]
        while (maxHeap.size() > 1) {
            int absoluteMax = maxHeap.poll(); // Pull out heaviest element [00:07:50]
            int secondaryMax = maxHeap.poll(); // Pull out second heaviest element [00:07:59]
            
            // Calculate structural residual weight boundaries
            if (absoluteMax != secondaryMax) {
                maxHeap.add(absoluteMax - secondaryMax); // Re-insert residual fragment back to heap [00:08:34]
            }
        }
        
        // Handle empty boundary edge checks safely [00:09:04]
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }

    // APPROACH 2: In-Place Sorting Iteration Strategy
    // Avoids separate heap structure allocations by utilizing primitive in-place library sorts. 
    // Triggers severe recurring execution costs because sorting calls run sequentially inside the loop.
    public static int lastStoneWeightSorting(int[] stones) {
        if (stones == null || stones.length == 0) return 0;
        
        int validLength = stones.length;
        
        while (validLength > 1) {
            Arrays.sort(stones, 0, validLength);
            
            int stone1 = stones[validLength - 1];
            int stone2 = stones[validLength - 2];
            
            if (stone1 == stone2) {
                validLength -= 2;
            } else {
                stones[validLength - 2] = stone1 - stone2;
                validLength -= 1;
            }
        }
        
        return validLength == 0 ? 0 : stones[0];
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Forcing stream allocations inside reduction layers triggers boxing cycles.
    // This dramatically impacts time efficiency markers compared to optimal primitive in-place heaps.
    public static int lastStoneWeightStream(int[] stones) {
        if (stones == null || stones.length == 0) return 0;

        PriorityQueue<Integer> maxHeap = Arrays.stream(stones)
                .boxed()
                .collect(Collectors.toCollection(() -> new PriorityQueue<>(Collections.reverseOrder())));

        while (maxHeap.size() > 1) {
            int s1 = maxHeap.poll();
            int s2 = maxHeap.poll();
            if (s1 != s2) {
                maxHeap.add(s1 - s2);
            }
        }

        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Classic Setup) ---
        int[] test1 = {2, 7, 4, 1, 8, 1};
        int res1_1 = lastStoneWeightOptimal(test1.clone());
        int res1_2 = lastStoneWeightSorting(test1.clone());
        int res1_3 = lastStoneWeightStream(test1.clone());

        System.out.println("Test Case 1: [2, 7, 4, 1, 8, 1]");
        System.out.println("Approach 1 (Max Heap)    Result: " + res1_1);
        System.out.println("Approach 2 (In-Place S)  Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)  Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 1 && res1_2 == 1 && res1_3 == 1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {8, 6, 7, 3};
        int res2_1 = lastStoneWeightOptimal(test2.clone());
        int res2_2 = lastStoneWeightSorting(test2.clone());
        int res2_3 = lastStoneWeightStream(test2.clone());

        System.out.println("Test Case 2: [8, 6, 7, 3]");
        System.out.println("Approach 1 (Max Heap)    Result: " + res2_1);
        System.out.println("Approach 2 (In-Place S)  Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)  Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 2 && res2_2 == 2 && res2_3 == 2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}