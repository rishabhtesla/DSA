package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MaximumProductRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given the array of integers `nums`, you will choose two different indices `i` and `j` 
    of that array. Return the maximum value of `(nums[i]-1)*(nums[j]-1)`.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: nums = [3, 4, 5, 2] [00:00:47]
    - The two maximum values in the array are 5 and 4.
    - Applying the formula: (5 - 1) * (4 - 1) = 4 * 3 = 12.
    - Output: 12

    Example B: nums = [1, 5, 4, 5]
    - The two maximum values are 5 and 5 (two separate elements).
    - Applying the formula: (5 - 1) * (5 - 1) = 4 * 4 = 16.
    - Output: 16

    APPROACH 1: Single-Pass Max and Second Max (As shown in video)
    - Initialize two variables, `max` and `secondMax`, to `-1` (since elements are guaranteed 
      to be positive integers >= 1 based on constraints) [00:02:12].
    - Iterate through the array using index `i` from `0` to `nums.length - 1` [00:07:57].
    - Inside the loop:
        1. If the current element `nums[i]` is strictly greater than `max`, it overrides the top rank. 
           Just like in a race, if someone overtakes the leader, the previous leader drops down to 
           become the second place finisher [00:04:26]. Thus, `secondMax = max`, then `max = nums[i]` [00:08:15].
        2. Else, if `nums[i]` is not greater than `max` but is greater than `secondMax`, it simply 
           overrides the second slot: `secondMax = nums[i]` [00:08:45].
    - After the loop, return `(max - 1) * (secondMax - 1)` [00:09:07].

    LOGIC BEHIND THIS APPROACH:
    ----------------------------------------------------------------------------
    1. Maximizing a Product: To maximize a product of non-negative expressions like `(A-1)*(B-1)`, 
       we mathematically must select the two absolute largest available integers from the domain set.
    2. single-Pass Cascading Updates: Instead of finding the absolute maximum first and running a 
       second iteration to find the next best, we maintain both states concurrently. The conditional 
       `if-else` cascade handles the structural relationship between the elements perfectly.
    3. Avoiding Index Management Errors: By directly tracking values instead of index keys, we naturally 
       handle cases where duplicate values represent the top two slots (e.g., `[5, 5]`) without accidentally 
       using the exact same index entry twice.

    VISUAL DRY RUN (Single-Pass Max Strategy for nums = [3, 4, 5, 2]):
    ----------------------------------------------------------------------------
    - Initial: max = -1, secondMax = -1
    - i = 0 (nums[0] = 3): 
        3 > max (-1) -> true. Cascading update: secondMax = -1, max = 3.   [00:05:22]
    - i = 1 (nums[1] = 4): 
        4 > max (3)  -> true. Cascading update: secondMax = 3, max = 4.    [00:05:49]
    - i = 2 (nums[2] = 5): 
        5 > max (4)  -> true. Cascading update: secondMax = 4, max = 5.    [00:06:18]
    - i = 3 (nums[3] = 2): 
        2 > max (5)  -> false. Check secondMax: 2 > secondMax (4) -> false. [00:07:05]
    - Loop terminates. max = 5, secondMax = 4.
    - Evaluation: (5 - 1) * (4 - 1) = 4 * 3 = 12.                     [00:13:18]

    TIME COMPLEXITY: O(N) - Single pass traversal through the array.
    SPACE COMPLEXITY: O(1) - Evaluated using fixed boundary registers.
    ================================================================================
    */
    public static int maxProductSinglePass(int[] nums) {
        int max = -1;
        int secondMax = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max) {
                secondMax = max;
                max = nums[i];
            } else if (nums[i] > secondMax) {
                secondMax = nums[i];
            }
        }

        return (max - 1) * (secondMax - 1);
    }

    /*
    ================================================================================
    APPROACH 2: Array Sorting (Alternative Strategy)
    - Sort the entire array in ascending order. The two largest elements will naturally 
      reside at the last two indices: `nums[n - 1]` and `nums[n - 2]`.
    - TIME COMPLEXITY: O(N log N) - Dominated by the sorting routine.
    - SPACE COMPLEXITY: O(1) or O(N) depending on primitive sorting stack overhead.
    ================================================================================
    */
    public static int maxProductSorting(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return (nums[n - 1] - 1) * (nums[n - 2] - 1);
    }

    /*
    ================================================================================
    APPROACH 3: Min-Heap / PriorityQueue (Alternative Strategy)
    - Maintain a min-heap of max size 2. As we iterate through the array, push elements.
    - If the size exceeds 2, eject the smallest element. At the end, the heap contains 
      the two largest numbers.
    - Useful paradigm when finding top K elements in huge real-time streaming data lists.
    - TIME COMPLEXITY: O(N log 2) -> O(N)
    - SPACE COMPLEXITY: O(1) - Heap size bounded strictly at K = 2.
    ================================================================================
    */
    public static int maxProductMinHeap(int[] nums) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.add(num);
            if (minHeap.size() > 2) {
                minHeap.poll();
            }
        }
        int secondMax = minHeap.poll();
        int max = minHeap.poll();
        return (max - 1) * (secondMax - 1);
    }

    private static void verifyAllApproaches(int[] nums, int expected) {
        System.out.println("Input Array: " + Arrays.toString(nums));
        System.out.println("Expected   : " + expected);
        System.out.println("1. Video Single-Pass: " + maxProductSinglePass(nums));
        System.out.println("2. Sorting Method   : " + maxProductSorting(nums));
        System.out.println("3. Min-Heap Method  : " + maxProductMinHeap(nums));
        System.out.println("Status              : " + 
            (maxProductSinglePass(nums) == expected && 
             maxProductSorting(nums) == expected &&
             maxProductMinHeap(nums) == expected ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 1464: MAXIMUM PRODUCT OF TWO ELEMENTS ===\n");

        // Case 1: Standard video validation sample [00:00:47]
        verifyAllApproaches(new int[]{3, 4, 5, 2}, 12);

        // Case 2: Array elements containing matching duplicate maximums
        verifyAllApproaches(new int[]{1, 5, 4, 5}, 16);

        // Case 3: Minimum size constraint edge case
        verifyAllApproaches(new int[]{10, 2}, 9);
    }
}