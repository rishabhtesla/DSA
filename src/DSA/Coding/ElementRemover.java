package DSA.Coding;

import java.util.Arrays;

/**
 * ============================================================================
 * FILE NAME: ElementRemover.java
 * ============================================================================
 * 1. PROBLEM STATEMENT
 * ============================================================================
 * Given an integer array 'nums' and an integer 'val', remove all occurrences 
 * of 'val' in 'nums' IN-PLACE. The order of the elements may be changed. 
 * Then, return the number of elements in 'nums' which are not equal to 'val'.
 * * Requirements:
 * - O(1) Extra Space: Do not allocate extra memory for another array.
 * - Return Value (k): The first 'k' elements of the modified array must hold 
 * the correct numbers. Whatever is left beyond index 'k' does not matter.
 * * ============================================================================
 * 2. LOGIC & APPROACH EXPLANATION
 * ============================================================================
 * We use a "Two-Pointer" approach to shift valid elements forward:
 * * Pointer 1: 'i' (The Scanner) -> Iterates through every element from 0 to N.
 * Pointer 2: 'index' (The Writer) -> Keeps track of where the next kept element goes.
 * * Action:
 * - If nums[i] matches 'val': It's a target element. Skip it.
 * - If nums[i] does NOT match 'val': Copy it down to nums[index] and increment 'index'.
 */
public class ElementRemover {
    
    public int removeElement(int[] nums, int val) {
        // 'index' tracks where to write the next valid element.
        // It also keeps an active count of the valid elements found.
        int index = 0; 
        
        // Loop 'i' scans the array from left to right
        for (int i = 0; i < nums.length; i++) {
            
            /*
             * IF-ELSE EXPLANATION:
             * Is the current element (nums[i]) a keeper?
             * * YES (nums[i] != val):
             * Copy the element from the scanner pointer 'i' over to the 
             * writer pointer 'index'. Then increment 'index' to point 
             * to the next empty slot.
             * * NO (nums[i] == val):
             * Do nothing. 'index' stands still. This bad element will 
             * eventually get overwritten when 'i' finds the next valid number.
             */
            if (nums[i] != val) {
                nums[index] = nums[i];
                index++; 
            }
        }
        
        // 'index' now naturally represents the total count of valid elements.
        return index;
    }

    public static void main(String[] args) {
        ElementRemover solution = new ElementRemover();

        /*
         * ====================================================================
         * DETAILED STEP-BY-STEP EXAMPLE TRACING
         * ====================================================================
         * Array: [3, 2, 2, 3], val = 3
         * * [Initialization]: index = 0
         * * - i = 0 (nums[0] = 3): nums[0] == val (3). Match found! Skip it. 
         * index remains 0.
         * * - i = 1 (nums[1] = 2): nums[1] != val (3). Keeper found! 
         * Copy nums[1] to nums[index] -> nums[0] becomes 2. 
         * increment index to 1. Array is now: [2, 2, 2, 3]
         * * - i = 2 (nums[2] = 2): nums[2] != val (3). Keeper found! 
         * Copy nums[2] to nums[index] -> nums[1] becomes 2. 
         * increment index to 2. Array is now: [2, 2, 2, 3]
         * * - i = 3 (nums[3] = 3): nums[3] == val (3). Match found! Skip it. 
         * index remains 2.
         * * [End of Loop]: index is 2. Valid block is nums[0] to nums[1] -> [2, 2]
         * ====================================================================
         */
        int[] nums = {3, 2, 2, 3};
        int val = 3;
        
        System.out.println("Original Array: " + Arrays.toString(nums));
        System.out.println("Value to remove: " + val);
        
        // Execute the solution
        int k = solution.removeElement(nums, val);
        
        System.out.println("\n--- Execution Results ---");
        System.out.println("Returned Length (k): " + k);
        
        // Slice the array to view only the validated section up to length 'k'
        System.out.println("Valid Elements (First " + k + " slots): " 
                           + Arrays.toString(Arrays.copyOfRange(nums, 0, k)));
                           
        System.out.println("Full Array State in Memory: " + Arrays.toString(nums));
    }
}