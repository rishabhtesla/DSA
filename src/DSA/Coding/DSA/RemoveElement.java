package DSA.Coding.DSA; /**
 * =================================================================================================
 * QUICK SUMMARY
 * =================================================================================================
 * Given an array and a target value 'val', remove all instances of 'val' from the array in-place.
 * Shift all the valid remaining elements to the front of the array and return the count of 
 * these valid elements. The elements beyond this count do not matter.
 * 
 * =================================================================================================
 * FULL PROBLEM STATEMENT (LeetCode 27: Remove Element)
 * =================================================================================================
 * Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. 
 * The order of the elements may be changed. Then return the number of elements in nums which are 
 * not equal to val.
 * 
 * Consider the number of elements in nums which are not equal to val be k, to get accepted, 
 * you need to do the following things:
 * 1. Change the array nums such that the first k elements of nums contain the elements which are 
 *    not equal to val. The remaining elements of nums are not important as well as the size of nums.
 * 2. Return k.
 * 
 * -------------------------------------------------------------------------------------------------
 * OFFICIAL LEETCODE EXAMPLES:
 * -------------------------------------------------------------------------------------------------
 * Example 1:
 * Input: nums = [3,2,2,3], val = 3
 * Output: 2, nums = [2,2,_,_]
 * Explanation: Your function should return k = 2, with the first two elements of nums being 2.
 * 
 * Example 2:
 * Input: nums = [0,1,2,2,3,0,4,2], val = 2
 * Output: 5, nums = [0,1,4,0,3,_,_,_]
 * =================================================================================================
 * 
 * =================================================================================================
 * APPROACH OVERVIEW & COGNITIVE LOGIC
 * =================================================================================================
 * 1. Data Structure Used:
 *    - Two-pointer approach on a single Array. O(1) space complexity, O(N) time complexity.
 * 
 * 2. The Logic Behind the Approach:
 *    - We keep a slow-moving tracking pointer (`index`) initialized at 0. This pointer acts as the 
 *      "write head". It explicitly points to the slot where the next valid element belongs.
 *    - We use a fast pointer (`i`) inside a loop to inspect every element from index 0 to the end.
 *    - If the fast pointer `nums[i]` finds an element that matches `val`, we ignore it completely.
 *    - If it finds a valid element (`nums[i] != val`), we copy it forward into `nums[index]` and 
 *      increment `index` so it's ready for the next valid item.
 * =================================================================================================
 */

import java.util.Arrays;

public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        // --- POINTER INITIALIZATION ---
        // 'index' acts as the write-pointer. It stores the position where the next valid element 
        // should go. At the end of the loop, it perfectly represents the total count of valid elements.
        int index = 0; 

        // --- CORE SCANNING LOGIC ---
        // Fast pointer 'i' sweeps across the entire physical array layout.
        for (int i = 0; i < nums.length; i++) {
            
            // CRITICAL CHECK: We only take action if the element is NOT the target value we want to destroy.
            if (nums[i] != val) {
                
                // Copy the valid element forward to the current available write index slot.
                nums[index] = nums[i];
                
                // Advance the write pointer forward by one slot.
                index++;
            }
            // Note: If nums[i] == val, the code skips the block, effectively dropping/ignoring the element.
        }
        
        // Return the final value of index, which acts as the logical length 'k' of the cleaned array.
        return index; 
    }

    public static void main(String[] args) {
        RemoveElement solver = new RemoveElement();

        System.out.println("--- 2. REMOVE ELEMENT EDGE CASES ---\n");

        // EDGE CASE A: The array is entirely empty
        // Explanation: The for-loop boundary evaluation `i < nums.length` returns false right away.
        // The algorithm skips the loop execution completely and returns the original index value: 0.
        int[] nums_A = {};
        int val_A = 5;
        
        System.out.println("Edge Case A: Array is completely empty.");
        System.out.println("  INPUT  -> nums: " + Arrays.toString(nums_A) + ", val: " + val_A);
        int k_A = solver.removeElement(nums_A, val_A);
        System.out.println("  OUTPUT -> k: " + k_A + ", Array state: " + Arrays.toString(nums_A) + "\n");

        // EDGE CASE B: Every single element in the array matches 'val'
        // Explanation: The statement `nums[i] != val` evaluates to false on every cycle. 
        // The code block inside the condition never runs; index stays fixed at 0.
        int[] nums_B = {2, 2, 2, 2};
        int val_B = 2;
        
        System.out.println("Edge Case B: Array contains ONLY the target value.");
        System.out.println("  INPUT  -> nums: " + Arrays.toString(nums_B) + ", val: " + val_B);
        int k_B = solver.removeElement(nums_B, val_B);
        System.out.println("  OUTPUT -> k: " + k_B + ", Array state: " + Arrays.toString(nums_B) + "\n");

        // EDGE CASE C: No elements in the array match 'val'
        // Explanation: The statement `nums[i] != val` evaluates to true on every single item. 
        // Elements are rewritten to their own original locations, and the returned k matches the full length.
        int[] nums_C = {1, 2, 3, 4};
        int val_C = 9; 
        
        System.out.println("Edge Case C: The target value does not exist in the array.");
        System.out.println("  INPUT  -> nums: " + Arrays.toString(nums_C) + ", val: " + val_C);
        int k_C = solver.removeElement(nums_C, val_C);
        System.out.println("  OUTPUT -> k: " + k_C + ", Array state: " + Arrays.toString(nums_C) + "\n");
        System.out.println("  \n");


        int[] nums_D = {0,1,2,2,3,0,4,2};
        int val_D = 2;
        int k_D = solver.removeElement(nums_D, val_D);
        System.out.println("  OUTPUT -> k: " + k_D+ ", Array state: " + Arrays.toString(nums_D) + "\n");


    }
}