package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given 'n' non-negative integers representing an elevation map where the width of each bar is 1, 
 * compute how much water it can trap after raining [00:00:16].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
 * - Process: The elevation spikes form pockets that trap rain. Calculating bounds at each index yields 
 *   a total collection area of 6 units.
 * - Result: 6
 * 
 * Example 2: height = [4, 3, 0, 2, 1, 5] (Simulated inside the video explanation [00:00:46, 00:12:20])
 * - Process:
 *   - 'leftMax' caches historical peak walls from left: [4, 4, 4, 4, 4, 5] [00:12:31].
 *   - 'rightMax' caches historical peak walls from right: [5, 5, 5, 5, 5, 5] [00:13:15].
 *   - At each index, trapped water height is bounded by: `min(leftMax[i], rightMax[i]) - height[i]` [00:14:28].
 *   - Index 1 (height 3): min(4, 5) - 3 = 1 [00:17:28].
 *   - Index 2 (height 0): min(4, 5) - 0 = 4 [00:17:34].
 *   - Index 3 (height 2): min(4, 5) - 2 = 2 [00:17:42].
 *   - Index 4 (height 1): min(4, 5) - 1 = 3 [00:17:48].
 *   - Summing individual index pockets gives: 0 + 1 + 4 + 2 + 3 + 0 = 10 [00:18:03].
 * - Result: 10
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Two Pass Dynamic Auxiliary Lookup Arrays):
 * • Index Initialization: Single tracking index 'i' iterates from 0 up to height.length - 1 [00:14:48].
 * • Condition Boundaries: The left pass initializes `leftMax[0] = height[0]` [00:08:37], while the right 
 *   pass processes indices down from `n-2` to 0 [00:11:15].
 * • Operational Steps:
 *   1. Build `leftMax` scanning forward: `leftMax[i] = max(leftMax[i-1], height[i])` [00:08:54].
 *   2. Build `rightMax` scanning backward: `rightMax[i] = max(rightMax[i+1], height[i])` [00:11:29].
 *   3. Accumulate total area: `totalWater += min(leftMax[i], rightMax[i]) - height[i]` [00:14:58].
 * • Time Complexity: O(n) - Composed of three sequential linear loops.
 * • Space Complexity: O(n) - Employs two independent integer lookup arrays [00:05:23].
 * • LOGIC BEHIND THIS APPROACH:
 *   Water collection at any single coordinate behaves like a pocket formed between a left bounding wall and a 
 *   right bounding wall [00:01:19]. The capacity is strictly constrained by the shorter of these two boundaries [00:01:39]. 
 *   Caching these maximums beforehand removes the need for redundant scanning, reducing the execution time to linear [00:07:33].
 * 
 * ---
 * VISUAL DRY RUN (height = [4, 3, 0, 2, 1, 5]):
 * Array States:
 * leftMax  = [4, 4, 4, 4, 4, 5]
 * rightMax = [5, 5, 5, 5, 5, 5]
 * Accumulation (ans = 0) [00:17:04]:
 * i = 0: min(4, 5) - 4 = 0 -> ans = 0
 * i = 1: min(4, 5) - 3 = 1 -> ans = 0 + 1 = 1 [00:17:28]
 * i = 2: min(4, 5) - 0 = 4 -> ans = 1 + 4 = 5 [00:17:34]
 * i = 3: min(4, 5) - 2 = 2 -> ans = 5 + 2 = 7 [00:17:42]
 * i = 4: min(4, 5) - 1 = 3 -> ans = 7 + 3 = 10 [00:17:48]
 * i = 5: min(5, 5) - 5 = 0 -> ans = 10 + 0 = 10
 * Output returned = 10 [00:18:51].
 */
public class TrappingRainWater {

    // APPROACH 1: Two Pass Dynamic Auxiliary Lookup Arrays (Anchor Strategy)
    public static int trapOptimalArrays(int[] height) {
        if (height == null || height.length < 3) return 0;
        
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        
        // Populate left-to-right maximum constraints [00:08:37]
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        
        // Populate right-to-left maximum constraints [00:10:50]
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        
        // Process intersection constraints to map water storage accumulation [00:14:48]
        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            totalWater += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return totalWater;
    }

    // APPROACH 2: Space In-Place Two-Pointer Strategy
    // Condenses tracking to O(1) space by squeezing two converging pointers inward, tracking 
    // left and right heights concurrently to eliminate lookup arrays completely.
    public static int trapInPlaceTwoPointer(int[] height) {
        if (height == null || height.length < 3) return 0;
        
        int left = 0;
        int right = height.length - 1;
        int leftMaxBound = 0;
        int rightMaxBound = 0;
        int totalWater = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMaxBound) {
                    leftMaxBound = height[left];
                } else {
                    totalWater += leftMaxBound - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMaxBound) {
                    rightMaxBound = height[right];
                } else {
                    totalWater += rightMaxBound - height[right];
                }
                right--;
            }
        }
        return totalWater;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Relies on mapping array indexes through structural pipeline collection boundaries. 
    // This introduces performance delays due to primitive boxing layer processing, while remaining bound to O(n) space.
    public static int trapStream(int[] height) {
        if (height == null || height.length < 3) return 0;
        
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        
        leftMax[0] = height[0];
        IntStream.range(1, n).forEach(i -> leftMax[i] = Math.max(leftMax[i - 1], height[i]));
        
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        return IntStream.range(0, n)
                .map(i -> Math.min(leftMax[i], rightMax[i]) - height[i])
                .sum();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Leetcode Classic Structure) ---
        int[] test1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int res1_1 = trapOptimalArrays(test1);
        int res1_2 = trapInPlaceTwoPointer(test1);
        int res1_3 = trapStream(test1);

        System.out.println("Test Case 1: [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]");
        System.out.println("Approach 1 (Aux Arrays)   Result: " + res1_1);
        System.out.println("Approach 2 (Two Pointer)  Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 6 && res1_2 == 6 && res1_3 == 6 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {4, 3, 0, 2, 1, 5};
        int res2_1 = trapOptimalArrays(test2);
        int res2_2 = trapInPlaceTwoPointer(test2);
        int res2_3 = trapStream(test2);

        System.out.println("Test Case 2: [4, 3, 0, 2, 1, 5]");
        System.out.println("Approach 1 (Aux Arrays)   Result: " + res2_1);
        System.out.println("Approach 2 (Two Pointer)  Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 10 && res2_2 == 10 && res2_3 == 10 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}