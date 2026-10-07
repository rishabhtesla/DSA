package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.stream.IntStream;

public class LargestNumberTwiceOthersRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    You are given an integer array `nums` where the largest integer is unique.
    Determine whether the largest element in the array is at least twice as much as 
    every other number in the array. If it is, return the index of the largest element, 
    or -1 if it is not.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: nums = [3, 6, 1, 0] [00:01:20]
    - The absolute largest element is 6, which resides at index 1 [00:01:26].
    - Check against others: 6 >= 2 * 3 (True), 6 >= 2 * 1 (True), 6 >= 2 * 0 (True) [00:01:35].
    - Since 6 is at least twice as large as all other elements, return its index.
    - Output: 1

    Example B: nums = [1, 2, 3, 4] [00:01:58]
    - The largest element is 4.
    - Check against 3: 2 * 3 = 6. Since 4 is not >= 6, the condition fails [00:02:14].
    - Output: -1

    APPROACH 1: Single-Pass Max & Second-Max Cascade (As shown in video)
    - Initialize `max = -1` and `secondMax = -1` (since elements are >= 0 per constraints) [00:05:21].
    - Track the index of the largest number via `maxIndex = -1` [00:09:57].
    - Traverse linearly through the array [00:10:10]:
        1. If `nums[i] > max`: the previous max drops down to become the new second max (`secondMax = max`), 
           and the current element assumes the top spot (`max = nums[i]`) [00:10:29]. Update `maxIndex = i` [00:10:41].
        2. Else if `nums[i] > secondMax`: update only the second slot (`secondMax = nums[i]`) [00:10:59].
    - Post-Loop Check: If `max >= 2 * secondMax`, return `maxIndex`, else return `-1` [00:11:18].
    - TIME COMPLEXITY: O(N) - Single pass traversal through the array.
    - SPACE COMPLEXITY: O(1) - Handled using constant primitives.

    LOGIC BEHIND THIS APPROACH:
    ----------------------------------------------------------------------------
    1. Sufficiency of Second Max: If the condition `max >= 2 * secondMax` holds true, no other 
       comparisons are mathematically necessary because `secondMax` bounds all other remaining values.
    2. Single Sweep Optimization: Maintaining tracking registers simultaneously allows us to read 
       the structure in a single pass, which avoids repetitive data scans.

    VISUAL DRY RUN (Video Strategy for nums = [3, 6, 1, 0]):
    ----------------------------------------------------------------------------
    - Initial: max = -1, secondMax = -1, maxIndex = -1
    - i = 0 (nums[0] = 3): 
        3 > max (-1) -> true. secondMax = -1, max = 3, maxIndex = 0.         [00:08:06]
    - i = 1 (nums[1] = 6): 
        6 > max (3)  -> true. secondMax = 3, max = 6, maxIndex = 1.         [00:08:29]
    - i = 2 (nums[2] = 1): 
        1 > max (6)  -> false. Check secondMax: 1 > secondMax (3) -> false.  [00:08:51]
    - i = 3 (nums[3] = 0): 
        0 > max (6)  -> false. Check secondMax: 0 > secondMax (3) -> false.  [00:09:08]
    - Loop Ends. max = 6, secondMax = 3, maxIndex = 1.
    - Post Check: 6 >= 2 * 3 (6 >= 6) -> True. Returns maxIndex = 1.        [00:11:26]

    ================================================================================
    */
    public static int dominantIndexVideo(int[] nums) {
        int max = -1;
        int secondMax = -1;
        int maxIndex = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max) {
                secondMax = max; // Previous leader drops to second place [00:10:29]
                max = nums[i];   // Update maximum [00:10:34]
                maxIndex = i;    // Save index [00:10:41]
            } else if (nums[i] > secondMax) {
                secondMax = nums[i]; // Update only second place [00:10:59]
            }
        }

        // Apply mathematical verification check [00:11:18]
        if (max >= 2 * secondMax) {
            return maxIndex;
        }
        return -1;
    }

    /*
    ================================================================================
    APPROACH 2: Two-Pass Scan Baseline
    - First scan: Identify the absolute maximum value and its corresponding index.
    - Second scan: Loop through all elements again. For any element at an index other 
      than the maximum index, verify if `max < 2 * nums[i]`. If it is, return -1.
    - TIME COMPLEXITY: O(N) - Two clean sequential loop sweeps.
    - SPACE COMPLEXITY: O(1) - Constant tracking properties.
    ================================================================================
    */
    public static int dominantIndexTwoPass(int[] nums) {
        int maxIndex = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (i != maxIndex && nums[maxIndex] < 2 * nums[i]) {
                return -1;
            }
        }
        return maxIndex;
    }

    /*
    ================================================================================
    APPROACH 3: Java Functional Streams Paradigm
    - We map an index sequence stream linearly across the array bounds using `IntStream.range`.
    - We identify the index matching the maximum element using a custom max comparator extraction.
    - We check if this maximum element dominates all other elements using an `allMatch` validation sweep.
    - TIME COMPLEXITY: O(N) - Linear performance pipeline resolution.
    - SPACE COMPLEXITY: O(1) - Primitive streaming footprint.
    ================================================================================
    */
    public static int dominantIndexStream(int[] nums) {
        // Find the index of the maximum element in the array
        int maxIndex = IntStream.range(0, nums.length)
                                .reduce((i, j) -> nums[i] > nums[j] ? i : j)
                                .orElse(-1);

        if (maxIndex == -1) return -1;
        int maxVal = nums[maxIndex];

        // Check if the maximum value is at least twice as large as every other element
        boolean satisfiesCondition = IntStream.range(0, nums.length)
                                              .filter(i -> i != maxIndex)
                                              .allMatch(i -> maxVal >= 2 * nums[i]);

        return satisfiesCondition ? maxIndex : -1;
    }

    private static void verifyAllApproaches(int[] nums, int expected) {
        System.out.println("Input Array : " + Arrays.toString(nums));
        System.out.println("Expected    : " + expected);
        System.out.println("1. Video Single-Pass: " + dominantIndexVideo(nums));
        System.out.println("2. Two-Pass Scan    : " + dominantIndexTwoPass(nums));
        System.out.println("3. Functional Stream: " + dominantIndexStream(nums));
        
        boolean passed = (dominantIndexVideo(nums) == expected) && 
                         (dominantIndexTwoPass(nums) == expected) && 
                         (dominantIndexStream(nums) == expected);
                         
        System.out.println("Status              : " + (passed ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 747: LARGEST NUMBER AT LEAST TWICE OF OTHERS ===\n");

        // Case 1: Standard sample verification case from video [00:01:20]
        verifyAllApproaches(new int[]{3, 6, 1, 0}, 1);

        // Case 2: Standard negative mismatch case from video [00:01:58]
        verifyAllApproaches(new int[]{1, 2, 3, 4}, -1);

        // Case 3: Simulation tracing verification case from dry run [00:12:30]
        verifyAllApproaches(new int[]{3, 6, 7, 4, 2, 1}, -1);

        // Case 4: Minimum length boundary constraint validation
        verifyAllApproaches(new int[]{1, 2}, 1);
    }
}