package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given an integer array 'height' of length 'n', where each element represents the height of a vertical 
 * wall at index 'i'. Find two lines that together with the x-axis form a container, such that the 
 * container contains the most water. Return the maximum amount of water a container can store.
 * Notice that you may not slant the container.
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
 * - Process: The two lines that can trap the maximum amount of water are at index 1 (height 8) 
 *   and index 8 (height 7). 
 *   - The width between them is: 8 - 1 = 7.
 *   - The effective limiting height is: min(8, 7) = 7.
 *   - Area = 7 * 7 = 49.
 * - Result: 49
 * 
 * Example 2: height = [4, 7, 3, 8, 1] (Simulated inside the video explanation [00:15:22])
 * - Process: Starting pointers at index 0 (height 4) and index 4 (height 1). 
 *   - Move inner boundaries towards each other. The highest structural match forms between index 1 (height 7) 
 *     and index 3 (height 8).
 *   - Width = 3 - 1 = 2.
 *   - Limiting height = min(7, 8) = 7.
 *   - Area = 7 * 2 = 14.
 * - Result: 14
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Optimal Two Pointers):
 * • Index Initialization: Left pointer 'start' begins at 0, Right pointer 'end' begins at height.length - 1 [00:10:47].
 * • Condition Boundaries: Loop runs while 'start < end' [00:11:10]. Pointers collapse until they meet.
 * • Operational Steps:
 *   1. Compute current width: `end - start` [00:11:51].
 *   2. Find the limiting constraint: `min(height[start], height[end])` [00:11:27].
 *   3. Calculate area: `height * width` and update `maxCapacity` [00:12:07].
 *   4. Greedily move the pointer pointing to the smaller height to search for a larger baseline constraint [00:12:43].
 * • Time Complexity: O(n) - Single pass through the array.
 * • Space Complexity: O(1) - Evaluated completely in-place using simple pointers.
 * • LOGIC BEHIND THIS APPROACH:
 *   The capacity is strictly bounded by the shorter wall. Shrinking the width guarantees a potential decrease in 
 *   volume unless the limiting height increases. Thus, we discard the shorter wall pointer by incrementing/decrementing 
 *   it, preserving the possibility of expanding the vertical height limit.
 * 
 * ---
 * VISUAL DRY RUN (height = [4, 7, 3, 8, 1]):
 * Initial: maxCapacity = 0, start = 0, end = 4 [00:15:22]
 * Step 1: start=0 (4), end=4 (1) -> Min H=1, W=(4-0)=4 -> Area=4.  maxCapacity = 4.  Since height[end](1) < height[start](4), end--.
 * Step 2: start=0 (4), end=3 (8) -> Min H=4, W=(3-0)=3 -> Area=12. maxCapacity = 12. Since height[start](4) < height[end](8), start++.
 * Step 3: start=1 (7), end=3 (8) -> Min H=7, W=(3-1)=2 -> Area=14. maxCapacity = 14. Since height[start](7) < height[end](8), start++.
 * Step 4: start=2 (3), end=3 (8) -> Min H=3, W=(3-2)=1 -> Area=3.  maxCapacity = 14. Since height[start](3) < height[end](8), start++.
 * Loop terminates because start == end (3 == 3) [00:18:49]. Final maxCapacity = 14.
 */
public class ContainerWithMostWater {

    // APPROACH 1: Optimal Two Pointers (Anchor Strategy)
    public static int maxAreaTwoPointer(int[] height) {
        if (height == null || height.length < 2) return 0;
        
        int start = 0;
        int end = height.length - 1;
        int maxCapacity = 0;
        
        while (start < end) {
            int currentHeight = Math.min(height[start], height[end]);
            int width = end - start;
            int currentCapacity = currentHeight * width;
            
            maxCapacity = Math.max(maxCapacity, currentCapacity);
            
            if (height[start] < height[end]) {
                start++;
            } else {
                end--;
            }
        }
        return maxCapacity;
    }

    // APPROACH 2: Space In-Place Skip Optimization Strategy
    // Bypasses localized redundant steps by shifting pointers past any heights that are strictly smaller than or 
    // equal to the current calculated bounding boundaries.
    public static int maxAreaOptimizedSkip(int[] height) {
        if (height == null || height.length < 2) return 0;
        
        int start = 0;
        int end = height.length - 1;
        int maxCapacity = 0;
        
        while (start < end) {
            int leftWall = height[start];
            int rightWall = height[end];
            int currentHeight = Math.min(leftWall, rightWall);
            
            maxCapacity = Math.max(maxCapacity, currentHeight * (end - start));
            
            if (leftWall < rightWall) {
                while (start < end && height[start] <= leftWall) {
                    start++;
                }
            } else {
                while (start < end && height[end] <= rightWall) {
                    end--;
                }
            }
        }
        return maxCapacity;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Simulating indices variations requires flatMapping index permutations over an IntStream. 
    // This transitions execution from linear O(n) to quadratic O(n^2) space/time layers due to processing combinations and boxing conversions.
    public static int maxAreaStream(int[] height) {
        if (height == null || height.length < 2) return 0;

        return IntStream.range(0, height.length)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, height.length)
                        .mapToObj(j -> Math.min(height[i], height[j]) * (j - i)))
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (From Leetcode Classic) ---
        int[] test1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int res1_1 = maxAreaTwoPointer(test1);
        int res1_2 = maxAreaOptimizedSkip(test1);
        int res1_3 = maxAreaStream(test1);

        System.out.println("Test Case 1: [1, 8, 6, 2, 5, 4, 8, 3, 7]");
        System.out.println("Approach 1 (Two Pointer)  Result: " + res1_1);
        System.out.println("Approach 2 (Skip Opt)     Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 49 && res1_2 == 49 && res1_3 == 49 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {4, 7, 3, 8, 1};
        int res2_1 = maxAreaTwoPointer(test2);
        int res2_2 = maxAreaOptimizedSkip(test2);
        int res2_3 = maxAreaStream(test2);

        System.out.println("Test Case 2: [4, 7, 3, 8, 1]");
        System.out.println("Approach 1 (Two Pointer)  Result: " + res2_1);
        System.out.println("Approach 2 (Skip Opt)     Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 14 && res2_2 == 14 && res2_3 == 14 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}