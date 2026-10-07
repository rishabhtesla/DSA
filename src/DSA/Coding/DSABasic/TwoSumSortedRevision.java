package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TwoSumSortedRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given a 1-indexed array of integers `numbers` that is already sorted in 
    non-decreasing order, find two numbers such that they add up to a specific 
    `target` number. Let these two numbers be `numbers[index1]` and `numbers[index2]` 
    where 1 <= index1 < index2 <= numbers.length.
    Return the indices of the two numbers, index1 and index2, added by one as an 
    integer array [index1, index2] of length 2.
    The tests are generated such that there is exactly one solution. You may not 
    use the same element twice. Your solution must use only constant extra space.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: numbers = [2, 7, 11, 15], target = 9 [00:01:16]
    - The elements 2 and 7 sum to 9. 
    - Their 0-based indices are 0 and 1.
    - Converting to 1-based indices: [0+1, 1+1] -> [1, 2].
    - Output: [1, 2]

    Example B: numbers = [2, 3, 4], target = 6 [00:01:43]
    - The elements 2 and 4 sum to 6.
    - 0-based positions: 0 and 2.
    - Converting to 1-based indices: [0+1, 2+1] -> [1, 3].
    - Output: [1, 3]

    APPROACH 1: Two-Pointer Convergence (As shown in video)
    - Set `start = 0` at the beginning and `end = numbers.length - 1` at the end [00:05:54].
    - While `start < end` [00:06:13]:
        1. Calculate the current combination: `sum = numbers[start] + numbers[end]` [00:06:48].
        2. If `sum == target`, target indices are found! Return `[start + 1, end + 1]` [00:07:08].
        3. If `sum > target`, the current sum value is too large. To decrease it, contract the 
           right boundary inward: `end--` [00:07:58].
        4. If `sum < target`, the sum value is too small. Since the array is sorted, we can 
           increase the value by advancing the left boundary outward: `start++` [00:08:21].
    - TIME COMPLEXITY: O(N) - Linear scan shifting boundaries inward.
    - SPACE COMPLEXITY: O(1) - Evaluated entirely using state pointers in-place.

    LOGIC BEHIND THIS APPROACH:
    ----------------------------------------------------------------------------
    1. Capitalizing on Order: A sorted collection provides directional certainty. When a sum is 
       too low, moving the left pointer rightward guarantees a value that is greater than or equal to 
       the previous value. Similarly, moving the right pointer leftward guarantees a lower value.
    2. Elimination of Pairs: Instead of checking every possible combination ($O(N^2)$), this approach 
       safely eliminates an entire row or column of potential pairs in a single step, ensuring a fast, 
       optimal search runtime.

    VISUAL DRY RUN (Two-Pointer Strategy for numbers = [5, 8, 13, 17, 20], target = 21):
    ----------------------------------------------------------------------------
    - Initial: start = 0 (5), end = 4 (20), ans = [_, _]              [00:10:57]
    - Cycle 1: sum = 5 + 20 = 25.
               25 > 21 (Too Large) -> contract right pointer: end = 3. [00:11:18]
    - Cycle 2: sum = 5 + 17 = 22.
               22 > 21 (Too Large) -> contract right pointer: end = 2. [00:11:43]
    - Cycle 3: sum = 5 + 13 = 18.
               18 < 21 (Too Small) -> advance left pointer: start = 1. [00:12:13]
    - Cycle 4: sum = 8 + 13 = 21.
               21 == 21 -> Match Found!                               [00:12:44]
    - Output adjustment: Return [start + 1, end + 1] -> [2, 3].       [00:12:50]

    ================================================================================
    */
    public static int[] twoSumTwoPointer(int[] numbers, int target) {
        int start = 0;
        int end = numbers.length - 1;

        while (start < end) {
            int sum = numbers[start] + numbers[end];
            
            if (sum == target) {
                return new int[]{start + 1, end + 1}; // Adjusting for 1-based indexing [00:07:18]
            } else if (sum > target) {
                end--; // Reduce right margin bounds [00:07:58]
            } else {
                start++; // Increase left margin bounds [00:08:21]
            }
        }
        return new int[]{-1, -1}; // Fallback matching condition safety placeholder [00:08:45]
    }

    /*
    ================================================================================
    APPROACH 2: Binary Search Scanning (Alternative Strategy)
    - Loop through each index `i`. Treat the remainder value `target - numbers[i]` 
      as the target item to find.
    - Use binary search on the subarray to the right of `i` (`i + 1` to `length - 1`) 
      to locate the complement.
    - TIME COMPLEXITY: O(N log N) - Binary searching $O(\log N)$ across N elements.
    - SPACE COMPLEXITY: O(1) - Constant pointer space allocation.
    ================================================================================
    */
    public static int[] twoSumBinarySearch(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int complement = target - numbers[i];
            int low = i + 1;
            int high = numbers.length - 1;
            
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (numbers[mid] == complement) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] > complement) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return new int[]{-1, -1};
    }

    /*
    ================================================================================
    APPROACH 3: Java Functional Streams Paradigm (Alternative Strategy)
    - We map an index sequence stream over the length bounds of the array.
    - For each index `i`, we look for a matching companion index `j` by running an 
      inner binary search on the remaining suffix range.
    - If a valid combination matches the criteria, we encapsulate it as a flat array stream.
    - TIME COMPLEXITY: O(N log N) - Matches standard binary search lookup over the array stream.
    - SPACE COMPLEXITY: O(1) - Handled using constant primitives inside functional pipeline wrappers.
    ================================================================================
    */
    public static int[] twoSumStream(int[] numbers, int target) {
        return IntStream.range(0, numbers.length)
                .boxed()
                .flatMap(i -> {
                    int complement = target - numbers[i];
                    // Perform binary search on the remaining suffix area inside a stream pipeline context
                    int idx = Arrays.binarySearch(numbers, i + 1, numbers.length, complement);
                    if (idx >= 0) {
                        return java.util.stream.Stream.of(new int[]{i + 1, idx + 1});
                    }
                    return java.util.stream.Stream.empty();
                })
                .findFirst()
                .orElse(new int[]{-1, -1});
    }

    private static void verifyAllApproaches(int[] numbers, int target, int[] expected) {
        System.out.println("Input Sorted Array: " + Arrays.toString(numbers) + " | Target: " + target);
        System.out.println("Expected Indices  : " + Arrays.toString(expected));
        System.out.println("1. Two-Pointer (Video): " + Arrays.toString(twoSumTwoPointer(numbers, target)));
        System.out.println("2. Binary Search Loop : " + Arrays.toString(twoSumBinarySearch(numbers, target)));
        System.out.println("3. Functional Streams : " + Arrays.toString(twoSumStream(numbers, target)));
        
        boolean passed = Arrays.equals(twoSumTwoPointer(numbers, target), expected) &&
                         Arrays.equals(twoSumBinarySearch(numbers, target), expected) &&
                         Arrays.equals(twoSumStream(numbers, target), expected);
                         
        System.out.println("Status                : " + (passed ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 167: TWO SUM II - INPUT ARRAY IS SORTED ===\n");

        // Case 1: Standard sample verification case [00:01:16]
        verifyAllApproaches(new int[]{2, 7, 11, 15}, 9, new int[]{1, 2});

        // Case 2: Mid-range value verification case [00:01:43]
        verifyAllApproaches(new int[]{2, 3, 4}, 6, new int[]{1, 3});

        // Case 3: In-depth simulation tracing sample from dry run logic [00:10:29]
        verifyAllApproaches(new int[]{5, 8, 13, 17, 20}, 21, new int[]{2, 3});

        // Case 4: Negative numbers boundary tracking [00:02:05]
        verifyAllApproaches(new int[]{-1, 0}, -1, new int[]{1, 2});
    }
}