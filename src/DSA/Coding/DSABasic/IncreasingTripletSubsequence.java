package DSA.Coding.DSABasic;

import java.util.Arrays;

/**
 * PROBLEM STATEMENT:
 * Given an integer array 'nums', return true if there exists a triple of indices (i, j, k) 
 * such that i < j < k and nums[i] < nums[j] < nums[k]. If no such indices exist, return false.
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [1, 2, 3, 4, 5]
 * - Process: The numbers are strictly ascending. Indices (0, 1, 2) correspond to values 1 < 2 < 3.
 * - Result: true
 * 
 * Example 2: nums = [8, 4, 3, 5, 7] (Simulated inside the video explanation [00:09:47])
 * - Process: 
 *   - 'first' initializes to +Infinity, tracks the lowest values: 8 -> 4 -> 3 [00:11:48].
 *   - 5 arrives: 5 > 'first' (3), so it shifts down and updates 'second' = 5 [00:12:35].
 *   - 7 arrives: 7 > 'first' (3) and 7 > 'second' (5). It falls to the 'else' branch [00:13:14].
 *   - All three variables ('first', 'second', 'third') become resolved as 3 < 5 < 7.
 * - Result: true
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Two Variable State Preservation):
 * • Index Initialization: Single loop index 'i' goes from 0 to nums.length - 1 [00:08:04]. 
 *   Two state boundaries, 'first' and 'second', are assigned Integer.MAX_VALUE [00:07:20].
 * • Condition Boundaries: If an element is smaller or equal to 'first', it replaces 'first'. 
 *   Else if it is smaller or equal to 'second', it replaces 'second'. Otherwise, it is strictly greater than both, completing the triplet.
 * • Operational Steps:
 *   1. Iterate over array elements [00:08:04].
 *   2. Update 'first' if element <= first [00:08:23].
 *   3. Update 'second' if element <= second [00:08:52].
 *   4. Return true if an element is greater than both [00:09:16].
 * • Time Complexity: O(n) - Single linear scanning loop.
 * • Space Complexity: O(1) - Uses constant extra space primitives.
 * • LOGIC BEHIND THIS APPROACH:
 *   By dynamically minimizing 'first' and 'second', we maximize the potential window for a valid 'third' value to fall 
 *   beyond them [00:11:26]. Even if a smaller 'first' is encountered later without updating 'second', 'second' still stores 
 *   a valid threshold historical state linked implicitly to an older, larger 'first', ensuring sequence correctness.
 * 
 * ---
 * VISUAL DRY RUN (nums = [8, 4, 3, 5, 7]):
 * Initial: first = INF, second = INF, third = INF [00:09:47]
 * i = 0: element = 8 -> 8 <= INF -> first = 8.
 * i = 1: element = 4 -> 4 <= 8   -> first = 4 [00:11:19].
 * i = 2: element = 3 -> 3 <= 4   -> first = 3 [00:11:48].
 * i = 3: element = 5 -> 5 > 3, 5 <= INF -> second = 5 [00:12:35].
 * i = 4: element = 7 -> 7 > 3, 7 > 5   -> Hits else branch. third = 7 [00:13:14]. Return true!
 */
public class IncreasingTripletSubsequence {

    // APPROACH 1: Two Variable State Preservation (Anchor Strategy)
    public static boolean increasingTripletOptimal(int[] nums) {
        if (nums == null || nums.length < 3) return false;
        
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num <= first) {
                first = num; 
            } else if (num <= second) {
                second = num; 
            } else {
                // If an element is greater than both 'first' and 'second', a valid triplet exists
                return true;
            }
        }
        return false;
    }

    // APPROACH 2: Dynamic Suffix/Prefix Extreme Tracking (Min-Max Auxiliary Arrays)
    // Alternative structural design mapping historical left-min and right-max states to resolve tracking conditions.
    public static boolean increasingTripletAuxiliaryArrays(int[] nums) {
        if (nums == null || nums.length < 3) return false;
        
        int n = nums.length;
        int[] minLeft = new int[n];
        int[] maxRight = new int[n];
        
        minLeft[0] = nums[0];
        for (int i = 1; i < n; i++) {
            minLeft[i] = Math.min(minLeft[i - 1], nums[i]);
        }
        
        maxRight[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], nums[i]);
        }
        
        for (int j = 1; j < n - 1; j++) {
            if (nums[j] > minLeft[j] && nums[j] < maxRight[j]) {
                return true;
            }
        }
        return false;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Using structural streams drops performance to O(n) space and extra complexity layering.
    // Maps the atomic reductions to verify state changes inside an custom accumulator array wrapper object.
    public static boolean increasingTripletStream(int[] nums) {
        if (nums == null || nums.length < 3) return false;

        // Index 0: first, Index 1: second, Index 2: found flag (1=true, 0=false)
        int[] resultState = Arrays.stream(nums)
                .boxed()
                .reduce(
                    new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 0},
                    (state, current) -> {
                        if (state[2] == 1) return state; // Shortcut if found
                        if (current <= state[0]) {
                            state[0] = current;
                        } else if (current <= state[1]) {
                            state[1] = current;
                        } else {
                            state[2] = 1; 
                        }
                        return state;
                    },
                    (s1, s2) -> s1 // Standard dummy combiner for sequential flows
                );

        return resultState[2] == 1;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Strictly Increasing) ---
        int[] test1 = {1, 2, 3, 4, 5};
        boolean res1_1 = increasingTripletOptimal(test1);
        boolean res1_2 = increasingTripletAuxiliaryArrays(test1);
        boolean res1_3 = increasingTripletStream(test1);

        System.out.println("Test Case 1: [1, 2, 3, 4, 5]");
        System.out.println("Approach 1 (Two Variable) Result: " + res1_1);
        System.out.println("Approach 2 (Aux Arrays)   Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 && res1_2 && res1_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {8, 4, 3, 5, 7};
        boolean res2_1 = increasingTripletOptimal(test2);
        boolean res2_2 = increasingTripletAuxiliaryArrays(test2);
        boolean res2_3 = increasingTripletStream(test2);

        System.out.println("Test Case 2: [8, 4, 3, 5, 7]");
        System.out.println("Approach 1 (Two Variable) Result: " + res2_1);
        System.out.println("Approach 2 (Aux Arrays)   Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 && res2_2 && res2_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Negative Case - Decreasing) ---
        int[] test3 = {5, 4, 3, 2, 1};
        boolean res3_1 = increasingTripletOptimal(test3);
        boolean res3_2 = increasingTripletAuxiliaryArrays(test3);
        boolean res3_3 = increasingTripletStream(test3);

        System.out.println("Test Case 3: [5, 4, 3, 2, 1]");
        System.out.println("Approach 1 (Two Variable) Result: " + res3_1);
        System.out.println("Approach 2 (Aux Arrays)   Result: " + res3_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res3_3);
        System.out.println("Verification: " + (!res3_1 && !res3_2 && !res3_3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}