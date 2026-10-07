package DSA.Coding.DSABasic;

import java.util.Arrays;

public class RemoveElementMasterRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given an integer array `nums` and an integer `val`, remove all occurrences of `val` 
    in-place. The order of the elements may be changed. Then return the number of 
    elements in `nums` which are not equal to `val`.
    
    Consider the number of elements in `nums` which are not equal to `val` be `k`, 
    to get accepted, you need to do the following things:
    1. Change the array `nums` such that the first `k` elements of `nums` contain the 
       elements which are not equal to `val`. The remaining elements of `nums` are 
       not important as well as the size of `nums`.
    2. Return `k`.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: nums = [3, 2, 2, 3], val = 3 [00:01:52]
    - We want to remove all 3s.
    - The valid elements are [2, 2].
    - Output: k = 2, nums = [2, 2, _, _]

    Example B: nums = [0, 1, 2, 2, 3, 0, 4, 2], val = 2 [00:02:24]
    - Remove all 2s. 
    - Valid elements left over: 0, 1, 3, 0, 4 (Count = 5).
    - Output: k = 5, nums = [0, 1, 3, 0, 4, _, _, _] (Order can vary)

    APPROACH 1: Two-Pointer Fast & Slow Placement (As shown in video)
    - Initialize a slow pointer `pointer = 0` to track the next insertion index for a non-val element [00:08:29].
    - Iterate through the array with a fast pointer `i` from `0` to `nums.length - 1` [00:08:35].
    - Inside the loop:
        - If `nums[i] != val`, it means `nums[i]` is a valid element that belongs in the front [00:08:51].
        - We copy it to the current insertion slot: `nums[pointer] = nums[i]` [00:08:58].
        - Then, we increment `pointer` to prepare for the next valid element [00:09:06].
    - When the loop terminates, `pointer` will equal `k`, the exact number of valid elements.

    LOGIC BEHIND THIS APPROACH:
    ----------------------------------------------------------------------------
    1. In-Place Compaction: Rather than allocating an auxiliary array (which wastes memory), 
       we use the same array to store the result. The slow pointer (`pointer`) builds the 
       compacted array prefix sequentially, while the fast pointer (`i`) filters out the elements to remove.
    2. Preservation of Valid Elements: By copying elements *only* when `nums[i] != val`, 
       the values matching `val` are naturally overwritten as the slow pointer advances.
    3. Traversal Invariant: Since `pointer` is always less than or equal to `i`, we never overwrite 
       an element that the fast pointer hasn't checked yet.

    VISUAL DRY RUN (Simulation Strategy for nums = [3, 2, 2, 3], val = 3):
    ----------------------------------------------------------------------------
    - Initial: pointer = 0, i = 0
    - i = 0 (nums[0] = 3): 3 != 3 -> False. Skip copy. `pointer` remains 0.
    - i = 1 (nums[1] = 2): 2 != 3 -> True. 
                           nums[pointer] = nums[1] -> nums[0] = 2.
                           pointer increments to 1. Array: [2, 2, 2, 3]
    - i = 2 (nums[2] = 2): 2 != 3 -> True. 
                           nums[pointer] = nums[2] -> nums[1] = 2.
                           pointer increments to 2. Array: [2, 2, 2, 3]
    - i = 3 (nums[3] = 3): 3 != 3 -> False. Skip copy. `pointer` remains 2.
    - Loop ends. Return `pointer` = 2. Valid prefix: [2, 2]

    TIME COMPLEXITY: O(N) - Single linear scan through the array.
    SPACE COMPLEXITY: O(1) - Constant auxiliary space, modifying the array in-place.
    ================================================================================
    */
    public static int removeElementPlacement(int[] nums, int val) {
        int pointer = 0; // Tracks placement index for valid numbers [00:08:29]

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) { // If the element is valid [00:08:51]
                nums[pointer] = nums[i]; // Move it to the front index [00:08:58]
                pointer++; // Step placement pointer forward [00:09:06]
            }
        }
        return pointer; // Matches count of non-val numbers [00:09:12]
    }

    /*
    ================================================================================
    APPROACH 2: Two-Pointer Swap from Opposite Ends (Alternative Strategy)
    - Set two pointers: `left = 0` and `right = nums.length - 1`.
    - When `nums[left] == val`, we overwrite it with the element from `nums[right]` 
      and decrement `right` (effectively tossing the bad element to the back).
    - We do *not* increment `left` immediately after a swap because the new element 
      brought from the back might also equal `val` and needs to be verified.
    - This minimizes the number of array writes when target elements to remove are rare.
    - TIME COMPLEXITY: O(N)
    - SPACE COMPLEXITY: O(1)
    ================================================================================
    */
    public static int removeElementSwap(int[] nums, int val) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            if (nums[left] == val) {
                nums[left] = nums[right];
                right--; // Contract boundaries from right side
            } else {
                left++; // Safely advance left side
            }
        }
        return left;
    }

    private static void verifyBothApproaches(int[] nums, int val, int expectedCount) {
        int[] copy1 = nums.clone();
        int[] copy2 = nums.clone();

        int k1 = removeElementPlacement(copy1, val);
        int[] validPrefix1 = Arrays.copyOf(copy1, k1);
        Arrays.sort(validPrefix1); // Sort only for uniform test checking

        int k2 = removeElementSwap(copy2, val);
        int[] validPrefix2 = Arrays.copyOf(copy2, k2);
        Arrays.sort(validPrefix2);

        System.out.println("Input Array: " + Arrays.toString(nums) + " | Value to Remove: " + val);
        System.out.println("Expected count k: " + expectedCount);
        System.out.println("1. Video Placement approach: k = " + k1 + ", Valid elements: " + Arrays.toString(validPrefix1));
        System.out.println("2. Opposite Swap approach   : k = " + k2 + ", Valid elements: " + Arrays.toString(validPrefix2));
        System.out.println("Status                      : " + (k1 == expectedCount && k2 == expectedCount ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 27: REMOVE ELEMENT ===\n");

        // Case 1: First sample case from the video [00:01:52]
        verifyBothApproaches(new int[]{3, 2, 2, 3}, 3, 2);

        // Case 2: Second sample dry run case from the video [00:02:24]
        verifyBothApproaches(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2, 5);

        // Case 3: Edge Case - Array contains only elements to remove
        verifyBothApproaches(new int[]{2, 2, 2}, 2, 0);

        // Case 4: Edge Case - Empty array bounds
        verifyBothApproaches(new int[]{}, 5, 0);
    }
}