package DSA.Coding.DSABasic;

import java.util.Arrays;

/**
 * PROBLEM STATEMENT:
 * Given an integer array 'nums' sorted in non-decreasing order, return the maximum between the number 
 * of positive integers and the number of negative integers [00:00:22, 00:00:30]. 
 * Note that 0 is neither positive nor negative.
 * You must attempt to solve the problem in O(log n) time complexity [00:02:10].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [-2, -1, -1, 1, 2, 3] [00:00:46]
 * - Process: Count of negative integers [-2, -1, -1] is 3. Count of positive integers [1, 2, 3] is 3.
 * - Result: 3
 * 
 * Example 2: nums = [-3, -2, -1, 0, 0, 1, 2] (Simulated inside the video explanation [00:04:00, 00:19:14])
 * - Process:
 *   - To find the count of negative numbers: Use binary search to find the index of the last negative number [00:03:26]. 
 *     Last negative number is at index 2 (-1). Count = 2 + 1 = 3 [00:04:10, 00:20:45].
 *   - To find the count of positive numbers: Use binary search to find the index of the first positive number [00:03:41]. 
 *     First positive number is at index 5 (1). Count = length - 5 = 7 - 5 = 2 [00:04:36, 00:22:51].
 *   - Max(3, 2) = 3 [00:05:12, 00:23:49].
 * - Result: 3
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Double Binary Search Boundaries):
 * • Index Initialization: Run two separate binary searches. Both search bounds 'start' begin at 0 and 'end' at nums.length - 1 [00:09:44, 00:15:28].
 * • Condition Boundaries: Loops execute while `start <= end` [00:10:04, 00:15:55].
 * • Operational Steps:
 *   1. Last Negative Search: If `nums[mid] < 0`, cache `ans = mid` and search the right partition via `start = mid + 1` [00:10:29, 00:11:04]. 
 *      Otherwise, search left via `end = mid - 1` [00:10:50].
 *   2. First Positive Search: If `nums[mid] > 0`, cache `ans = mid` and search the left partition via `end = mid - 1` [00:16:16, 00:16:28]. 
 *      Otherwise, search right via `start = mid + 1` [00:16:41].
 *   3. Net count of negative elements is `lastNegativeIndex + 1` [00:04:10, 00:17:24].
 *   4. Net count of positive elements is `nums.length - firstPositiveIndex` [00:04:36, 00:17:47].
 * • Time Complexity: O(log n) - Composed of two isolated O(log n) boundary checks [00:02:10].
 * • Space Complexity: O(1) - Executed in-place with minimal index tracking markers.
 * • LOGIC BEHIND THIS APPROACH:
 *   Since the array is pre-sorted, negative numbers, zeros, and positive numbers are perfectly grouped [00:00:22, 00:05:17]. 
 *   Instead of scanning linearly, we can determine the counts by locating the transitions between negative-to-zero 
 *   and zero-to-positive using binary search [00:03:26, 00:03:41].
 * 
 * ---
 * VISUAL DRY RUN (nums = [-3, -2, -1, 0, 0, 1, 2]):
 * [Last Negative Search] (Initial: start=0, end=6, ans=-1) [00:19:14]
 * Step 1: mid = 3 (val=0)  -> 0 >= 0. Search left: end = 3 - 1 = 2 [00:20:15].
 * Step 2: mid = 1 (val=-2) -> -2 < 0. Cache ans=1. Search right: start = 1 + 1 = 2 [00:19:53].
 * Step 3: mid = 2 (val=-1) -> -1 < 0. Cache ans=2. Search right: start = 2 + 1 = 3 [00:19:53].
 * Terminated (start > end). Count of negatives = lastNegativeIndex + 1 = 2 + 1 = 3 [00:20:45].
 * [First Positive Search] (Initial: start=0, end=6, ans=7) [00:21:05]
 * Step 1: mid = 3 (val=0)  -> 0 <= 0. Search right: start = 3 + 1 = 4 [00:22:38].
 * Step 2: mid = 5 (val=1)  -> 1 > 0.  Cache ans=5. Search left: end = 5 - 1 = 4 [00:22:15].
 * Step 3: mid = 4 (val=0)  -> 0 <= 0. Search right: start = 4 + 1 = 5 [00:22:38].
 * Terminated (start > end). Count of positives = length - firstPositiveIndex = 7 - 5 = 2 [00:22:51].
 * Final Result = max(3, 2) = 3 [00:23:49].
 */
public class MaximumCountPositiveNegative {

    // APPROACH 1: Double Binary Search Boundaries (Anchor Strategy)
    public static int maximumCountOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int lastNegativeIndex = findLastNegative(nums);
        int firstPositiveIndex = findFirstPositive(nums);
        
        int negativeCount = lastNegativeIndex + 1;
        int positiveCount = nums.length - firstPositiveIndex;
        
        return Math.max(negativeCount, positiveCount);
    }
    
    private static int findLastNegative(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        int ans = -1; // Default fallback index implying zero negatives [00:09:59]
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < 0) {
                ans = mid;
                start = mid + 1; // Greedily push right for trailing negatives [00:11:04]
            } else {
                end = mid - 1;
            }
        }
        return ans;
    }
    
    private static int findFirstPositive(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        int ans = nums.length; // Default fallback index implying zero positives [00:11:59]
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > 0) {
                ans = mid;
                end = mid - 1; // Greedily pull left for leading positives [00:16:28]
            } else {
                start = mid + 1;
            }
        }
        return ans;
    }

    // APPROACH 2: Single-Pass Linear Scanning (Structural Design Alternative)
    // Runs an O(n) scan, matching standard array structures. Used as a baseline variant [00:01:49].
    public static int maximumCountLinear(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int positiveCount = 0;
        int negativeCount = 0;
        
        for (int num : nums) {
            if (num > 0) positiveCount++;
            else if (num < 0) negativeCount++;
        }
        
        return Math.max(positiveCount, negativeCount);
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Relies on filtering twice over the stream layer. 
    // This transitions execution from log(n) back down to a dual linear scan O(n), creating boxing overhead.
    public static int maximumCountStream(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        long negativeCount = Arrays.stream(nums).filter(num -> num < 0).count();
        long positiveCount = Arrays.stream(nums).filter(num -> num > 0).count();

        return Math.max((int) negativeCount, (int) positiveCount);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Symmetric Counts) ---
        int[] test1 = {-2, -1, -1, 1, 2, 3};
        int res1_1 = maximumCountOptimal(test1);
        int res1_2 = maximumCountLinear(test1);
        int res1_3 = maximumCountStream(test1);

        System.out.println("Test Case 1: [-2, -1, -1, 1, 2, 3]");
        System.out.println("Approach 1 (Double BS)  Result: " + res1_1);
        System.out.println("Approach 2 (Linear)     Result: " + res1_2);
        System.out.println("Approach 3 (Stream API) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 3 && res1_2 == 3 && res1_3 == 3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation - Zero Pockets) ---
        int[] test2 = {-3, -2, -1, 0, 0, 1, 2};
        int res2_1 = maximumCountOptimal(test2);
        int res2_2 = maximumCountLinear(test2);
        int res2_3 = maximumCountStream(test2);

        System.out.println("Test Case 2: [-3, -2, -1, 0, 0, 1, 2]");
        System.out.println("Approach 1 (Double BS)  Result: " + res2_1);
        System.out.println("Approach 2 (Linear)     Result: " + res2_2);
        System.out.println("Approach 3 (Stream API) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 3 && res2_2 == 3 && res2_3 == 3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}