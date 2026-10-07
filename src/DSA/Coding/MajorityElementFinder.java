package DSA.Coding;

import java.util.Arrays;

/**
 * ============================================================================
 * FILE NAME: MajorityElementFinder.java
 * ============================================================================
 * * ============================================================================
 * PROBLEM STATEMENT: MAJORITY ELEMENT (n/2)
 * ============================================================================
 * Given an array 'nums' of size 'n', return the majority element.
 * The majority element is the element that appears MORE THAN ⌊n / 2⌋ times.
 * * You may assume that the majority element always exists in the array.
 * * Constraints & Requirements:
 * - Time Complexity: O(n) -> Must run in linear time.
 * - Space Complexity: O(1) -> Must use constant extra space.
 * * ============================================================================
 * LOGIC & APPROACH: BOYER-MOORE VOTING ALGORITHM
 * ============================================================================
 * The algorithm works on a "battle of elements" or "democratic voting" logic:
 * 1. We maintain a 'candidate' for the majority element and a 'count' of votes.
 * 2. As we iterate through the array:
 * - If 'count' drops to 0, we select the current element as our new 'candidate'.
 * - If the current element matches our 'candidate', we increment 'count' (+1).
 * - If it does not match, we decrement 'count' (-1).
 * * Why this works:
 * Since the true majority element appears more than half the time, even if all 
 * other elements band together to "vote it down", they won't have enough votes 
 * to cancel it out completely by the time we reach the end of the array.
 */
public class MajorityElementFinder {
    
    public int majorityElement(int[] nums) {
        // 'candidate' stores our current guess for the majority element
        int candidate = 0;
        
        // 'count' tracks the net votes the current candidate holds
        int count = 0;
        
        // Iterate through the array to find the dominant element
        for (int i = 0; i < nums.length; i++) {
            
            // If count becomes 0, the previous candidate's votes were completely
            // canceled out. We must pick the current element as the new candidate.
            if (count == 0) {
                candidate = nums[i];
            }
            
            // If the current element matches the candidate, it supports it (+1 vote).
            // Otherwise, it opposes it (-1 vote).
            if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        // The problem guarantees a majority element always exists, so the
        // surviving candidate at the end is guaranteed to be the correct answer.
        return candidate;
    }

    public static void main(String[] args) {
        MajorityElementFinder finder = new MajorityElementFinder();

        /*
         * ====================================================================
         * DETAILED STEP-BY-STEP EXAMPLE TRACING
         * ====================================================================
         * Array: [2, 2, 1, 1, 1, 2, 2]
         * Size (n): 7, Majority needs to appear > 7/2 (which means >= 4 times)
         * * [Initialization]: candidate = 0, count = 0
         * * - i = 0 (val = 2): count is 0 -> candidate becomes 2. 
         * val (2) == candidate (2) -> count becomes 1.
         * * - i = 1 (val = 2): val (2) == candidate (2) -> count becomes 2.
         * * - i = 2 (val = 1): val (1) != candidate (2) -> count drops to 1.
         * * - i = 3 (val = 1): val (1) != candidate (2) -> count drops to 0.
         * * - i = 4 (val = 1): count is 0 -> candidate switches to 1.
         * val (1) == candidate (1) -> count becomes 1.
         * * - i = 5 (val = 2): val (2) != candidate (1) -> count drops to 0.
         * * - i = 6 (val = 2): count is 0 -> candidate switches to 2.
         * val (2) == candidate (2) -> count becomes 1.
         * * [End of Loop]: candidate is 2. Final Output = 2.
         * ====================================================================
         */
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        
        System.out.println("--- Boyer-Moore Voting Algorithm ---");
        System.out.println("Input Array: " + Arrays.toString(nums));
        
        // Execute the algorithm
        int result = finder.majorityElement(nums);
        
        System.out.println("Calculated Majority Element: " + result);
    }
}