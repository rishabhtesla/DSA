package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ProductExceptSelfRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given an integer array `nums`, return an array `answer` such that `answer[i]` 
    is equal to the product of all the elements of `nums` except `nums[i]`.
    The product of any prefix or suffix of `nums` is guaranteed to fit in a 32-bit integer.
    You must write an algorithm that runs in O(n) time and without using the division operation.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: nums = [1, 2, 3, 4] [00:01:08]
    - Position 0: 2 * 3 * 4 = 24
    - Position 1: 1 * 3 * 4 = 12
    - Position 2: 1 * 2 * 4 = 8
    - Position 3: 1 * 2 * 3 = 6
    - Output: [24, 12, 8, 6]

    APPROACH 1: Auxiliary Suffix Accumulator Array (As shown in video)
    - Pre-allocate a `right` suffix summary lookup array of size `n` [00:06:59].
    - Run a loop backwards from `n - 1` down to `0` [00:07:34]. Maintain a running `product` variable.
    - Set `right[i] = product`, then accumulate `product *= nums[i]` [00:08:07].
    - Establish a rolling tracker `left = 1` [00:11:29].
    - Loop forward from `0` up to `n - 2`:
        1. Compute cell output: `answer[i] = left * right[i + 1]` [00:11:56].
        2. Shift state: `left *= nums[i]` [00:12:34].
    - Handle the edge constraint final cell boundary manually: `answer[n - 1] = left` [00:16:58].
    - TIME COMPLEXITY: O(N) - Two clean isolated sequential scans.
    - SPACE COMPLEXITY: O(N) - Allocated tracking array size dimensions.

    LOGIC BEHIND THIS APPROACH:
    ----------------------------------------------------------------------------
    1. Product Splitting: The total product excluding an element `nums[i]` is simply the product of 
       everything to its left multiplied by the product of everything to its right.
    2. Decoupled Processing: By tracking the rights array backwards first, we effectively save the future values. 
       Then during the forward pass, we dynamic-build lefts while pulling down rights instantaneously in O(1) time.

    VISUAL DRY RUN (Video Strategy for nums = [1, 2, 3, 4]):
    ----------------------------------------------------------------------------
    - Backwards Right Generation Loop [00:08:48]:
        i = 3: product = 1 * 4 = 4   -> right[3] = 4
        i = 2: product = 4 * 3 = 12  -> right[2] = 12
        i = 1: product = 12 * 2 = 24 -> right[1] = 24
        i = 0: product = 24 * 1 = 24 -> right[0] = 24
    - Forward Merge Pass Loop [00:13:22]:
        i = 0: ans[0] = left (1) * right[1] (24) = 24. left becomes 1 * nums[0] = 1.
        i = 1: ans[1] = left (1) * right[2] (12) = 12. left becomes 1 * nums[1] = 2.
        i = 2: ans[2] = left (2) * right[3] (4)  = 8.  left becomes 2 * nums[2] = 6.
    - Manual Terminal Edge Assignment: ans[3] = left (6) = 6 [00:16:58].
    - Output: [24, 12, 8, 6]

    ================================================================================
    */
    public static int[] productExceptSelfVideo(int[] nums) {
        int n = nums.length;
        int[] right = new int[n];
        int product = 1;

        // Build suffix product lookup array moving backwards [00:07:34]
        for (int i = n - 1; i >= 0; i--) {
            product *= nums[i];
            right[i] = product;
        }

        int[] answer = new int[n];
        int left = 1;

        // Map rolling intersections forward [00:11:34]
        for (int i = 0; i < n - 1; i++) {
            answer[i] = left * right[i + 1];
            left *= nums[i];
        }

        // Handle last element overflow buffer explicitly [00:16:58]
        answer[n - 1] = left;
        return answer;
    }

    /*
    ================================================================================
    APPROACH 2: Optimized Space In-Place Transformation
    - Eliminates the auxiliary array entirely by reusing the final `answer` array 
      to hold the prefix products first.
    - Loop forward: `answer[i] = prefix`, then `prefix *= nums[i]`.
    - Loop backward: `answer[i] *= suffix`, then `suffix *= nums[i]`.
    - TIME COMPLEXITY: O(N) - Space optimized scan profiles.
    - SPACE COMPLEXITY: O(1) - Modifying output layout cleanly (ignoring output space).
    1 2 3 4
    
    
    1 2 6 24
   
    
    ================================================================================
    */
    public static int[] productExceptSelfOptimized(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        
        // Forward pass: calculate prefix products directly in output array
        int prefix = 1;
        for (int i = 0; i < n; i++) {
            answer[i] = prefix;
            prefix *= nums[i];
        }
        
        // Backward pass: multiply by suffix product on the fly
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }
        
        return answer;
    }

    /*
    ================================================================================
    APPROACH 3: Java Functional Streams Paradigm
    - We map an index collection stream linearly from `0` up to `nums.length`.
    - To eliminate nesting complexity ($O(N^2)$), we compute the net array aggregate products 
      and zero frequencies ahead of time, resolving edge calculation mappings in O(1) time.
    - TIME COMPLEXITY: O(N)
    - SPACE COMPLEXITY: O(1)
    ================================================================================
    */
    public static int[] productExceptSelfStream(int[] nums) {
        // Calculate complete product excluding zeros, and track zero occurrences
        long zeroCount = Arrays.stream(nums).filter(x -> x == 0).count();
        int totalProductWithoutZero = Arrays.stream(nums).filter(x -> x != 0).reduce(1, (a, b) -> a * b);

        return IntStream.range(0, nums.length)
                .map(i -> {
                    if (zeroCount > 1) return 0; // Multiple zeros turn everything to 0
                    if (zeroCount == 1) {
                        return (nums[i] == 0) ? totalProductWithoutZero : 0;
                    }
                    // No zeros in the array: multiply out in O(1) using division mapping logic
                    // (This works mathematically here since no zeros exist to cause errors)
                    return totalProductWithoutZero / nums[i];
                })
                .toArray();
    }

    private static void verifyAllApproaches(int[] nums, int[] expected) {
        System.out.println("Input Array  : " + Arrays.toString(nums));
        System.out.println("Expected     : " + Arrays.toString(expected));
        System.out.println("1. Video Right-Array  : " + Arrays.toString(productExceptSelfVideo(nums)));
        System.out.println("2. In-Place Space Opt : " + Arrays.toString(productExceptSelfOptimized(nums)));
        System.out.println("3. Functional Streams : " + Arrays.toString(productExceptSelfStream(nums)));
        
        boolean passed = Arrays.equals(productExceptSelfVideo(nums), expected) &&
                         Arrays.equals(productExceptSelfOptimized(nums), expected) &&
                         Arrays.equals(productExceptSelfStream(nums), expected);
                         
        System.out.println("Status                : " + (passed ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 238: PRODUCT OF ARRAY EXCEPT SELF ===\n");

        // Case 1: Basic validation sequence from video tutorial [00:01:08]
        verifyAllApproaches(new int[]{1, 2, 3, 4}, new int[]{24, 12, 8, 6});

        // Case 2: Array containing a single zero element [00:03:30]
        verifyAllApproaches(new int[]{-1, 1, 0, -3, 3}, new int[]{0, 0, 9, 0, 0});

        // Case 3: Multiple zeros killing calculation domains
        verifyAllApproaches(new int[]{0, 4, 0}, new int[]{0, 0, 0});
    }
}