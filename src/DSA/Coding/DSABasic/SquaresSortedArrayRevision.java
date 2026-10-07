package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.LinkedList;

public class SquaresSortedArrayRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given an integer array `nums` sorted in non-decreasing order, return an array of 
    the squares of each number sorted in non-decreasing order.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: nums = [-4, -1, 0, 3, 10] [00:00:59]
    - Squaring each element yields: [16, 1, 0, 9, 100].
    - Sorting these values gives the final valid output: [0, 1, 9, 16, 100].
    - Output: [0, 1, 9, 16, 100]

    Example B: nums = [-7, -3, 2, 3, 11]
    - Squaring each element yields: [49, 9, 4, 9, 121].
    - Sorting these values gives the final valid output: [4, 9, 9, 49, 121].
    - Output: [4, 9, 9, 49, 121]

    LOGIC BEHIND THIS APPROACH:
    ----------------------------------------------------------------------------
    1. Maximum Square Location: Because the initial input is already sorted, the largest 
       absolute values (and thus, the largest squares) can only reside at the extreme outer 
       edges—either far left (highly negative values) or far right (highly positive values) [00:04:34].
    2. Backwards Array Compaction: Since the outer edges hold the largest elements, we can 
       populate our new result array from right to left (from highest index `length - 1` down to `0`) [00:08:08].
    3. Two-Pointer Convergence: We place a `start` pointer at index 0 and an `end` pointer at 
       the last index [00:04:00]. In each cycle, we square both values and compare them. The larger 
       square gets placed at the current back index of the result array (`pointer`), and its 
       respective tracking index steps inward (`start++` or `end--`) [00:09:04].
    4. Loop Termination Invariant: Using an inclusive check (`start <= end`) ensures that even when both 
       pointers meet at the middle element, it is correctly squared and captured [00:06:36].

    VISUAL DRY RUN (Simulation Strategy for nums = [-7, -4, 3, 0, 1, 2]):
    ----------------------------------------------------------------------------
    - Initial: start = 0, end = 5, pointer = 5, ans = [_, _, _, _, _, _]
    - Loop 1 (start = 0, end = 5):
        startSq = (-7)^2 = 49;  endSq = (2)^2 = 4
        startSq (49) > endSq (4) -> ans[5] = 49                      [00:12:08]
        start shifts to 1;  pointer becomes 4                        [00:12:15]
    - Loop 2 (start = 1, end = 5):
        startSq = (-4)^2 = 16;  endSq = (2)^2 = 4
        startSq (16) > endSq (4) -> ans[4] = 16                      [00:12:52]
        start shifts to 2;  pointer becomes 3                        [00:12:58]
    - Loop 3 (start = 2, end = 5):
        startSq = (3)^2 = 9;    endSq = (2)^2 = 4
        startSq (9) > endSq (4) -> ans[3] = 9                        [00:13:24]
        start shifts to 3;  pointer becomes 2                        [00:13:35]
    - Loop 4 (start = 3, end = 5):
        startSq = (0)^2 = 0;    endSq = (2)^2 = 4
        endSq (4) > startSq (0) -> ans[2] = 4                        [00:14:04]
        end shifts to 4;    pointer becomes 1                        [00:14:11]
    - Loop 5 (start = 3, end = 4):
        startSq = (0)^2 = 0;    endSq = (1)^2 = 1
        endSq (1) > startSq (0) -> ans[1] = 1                        [00:14:38]
        end shifts to 3;    pointer becomes 0                        [00:14:45]
    - Loop 6 (start = 3, end = 3): Meeting point index overlap.
        startSq = 0; endSq = 0 -> ans[0] = 0                         [00:15:12]
        end shifts to 2;    pointer becomes -1.                      [00:15:17]
    - Terminate: start (3) > end (2) -> breaks outer condition loop. [00:15:34]

    TIME COMPLEXITY: O(N) - Linear single-pass comparison loop over the items.
    SPACE COMPLEXITY: O(N) - Output array space needed to contain squared results.
    ================================================================================
    */
    public static int[] sortedSquaresTwoPointer(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        
        int start = 0;
        int end = n - 1;
        int pointer = n - 1; // Tracks the next available insertion index from the back [00:08:08]

        // Inclusive check to ensure the middle item is also processed [00:08:20]
        while (start <= end) {
            int startSquare = nums[start] * nums[start];
            int endSquare = nums[end] * nums[end];

            if (startSquare > endSquare) {
                ans[pointer] = startSquare;
                start++; // Move left boundary inward [00:09:19]
            } else {
                ans[pointer] = endSquare;
                end--; // Move right boundary inward [00:09:40]
            }
            pointer--; // Step result pointer backward [00:09:50]
        }
        
        return ans;
    }

    /*
    ================================================================================
    APPROACH 2: Deque-Based Dynamic Collection Insertion (Alternative Strategy)
    - Instead of populating a fixed primitive array backwards, we use a double-ended 
      queue (`LinkedList`).
    - We compare the outer boundaries just like before, but we insert the larger value 
      at the *front* of the queue using `addFirst()`.
    - This creates a clean, left-to-right insertion sequence.
    - TIME COMPLEXITY: O(N) - O(1) prepend inserts over N operations.
    - SPACE COMPLEXITY: O(N) - Required queue storage size.
    ================================================================================
    */
    public static int[] sortedSquaresDeque(int[] nums) {
        LinkedList<Integer> list = new LinkedList<>();
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int startSquare = nums[start] * nums[start];
            int MathSquare = nums[end] * nums[end];

            if (startSquare > MathSquare) {
                list.addFirst(startSquare);
                start++;
            } else {
                list.addFirst(MathSquare);
                end--;
            }
        }

        // Convert the collection back into a primitive integer array
        return list.stream().mapToInt(i -> i).toArray();
    }

    private static void verifyBothApproaches(int[] nums, int[] expected) {
        System.out.println("Input Array  : " + Arrays.toString(nums));
        System.out.println("Expected     : " + Arrays.toString(expected));
        System.out.println("1. Video Two-Pointer : " + Arrays.toString(sortedSquaresTwoPointer(nums)));
        System.out.println("2. Deque Collection  : " + Arrays.toString(sortedSquaresDeque(nums)));
        System.out.println("Status               : " + 
            (Arrays.equals(sortedSquaresTwoPointer(nums), expected) && 
             Arrays.equals(sortedSquaresDeque(nums), expected) ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 977: SQUARES OF A SORTED ARRAY ===\n");

        // Case 1: First sample case from the video tutorial [00:00:59]
        verifyBothApproaches(new int[]{-4, -1, 0, 3, 10}, new int[]{0, 1, 9, 16, 100});

        // Case 2: In-depth verification case from dry run logic [00:10:47]
        verifyBothApproaches(new int[]{-7, -4, 3, 0, 1, 2}, new int[]{0, 1, 4, 9, 16, 49});

        // Case 3: Edge Case - Array containing only negative values
        verifyBothApproaches(new int[]{-5, -3, -1}, new int[]{1, 9, 25});

        // Case 4: Edge Case - Single element boundary limit
        verifyBothApproaches(new int[]{-2}, new int[]{4});
    }
}