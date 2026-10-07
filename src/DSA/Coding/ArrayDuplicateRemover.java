package DSA.Coding;

import java.util.Arrays;

/**
 * ============================================================================
 * FILE NAME: ArrayDuplicateRemover.java
 * ============================================================================
 * * ============================================================================
 * PART 1: REMOVE DUPLICATES FROM SORTED ARRAY (MAX 1 OCCURRENCE)
 * ============================================================================
 * PROBLEM STATEMENT:
 * Given an integer array 'nums' sorted in non-decreasing order, remove the 
 * duplicates IN-PLACE such that each unique element appears only ONCE.
 * Return the number of unique elements (k).
 * * LOGIC EXPLANATION (Two-Pointer):
 * Since the array is sorted, duplicates are always next to each other.
 * - 'i' (Scanner Pointer) travels across the array.
 * - 'index' (Write Pointer) marks where the next unique element should be placed.
 * - Action: If nums[i] is different from the last unique element we saved 
 * (nums[index - 1]), it means we found a new unique number. We write it down.
 */

/**
 * ============================================================================
 * PART 2: REMOVE DUPLICATES FROM SORTED ARRAY II (MAX 2 OCCURRENCES)
 * ============================================================================
 * PROBLEM STATEMENT:
 * Given an integer array 'nums' sorted in non-decreasing order, remove some 
 * duplicates IN-PLACE such that each unique element appears AT MOST TWICE.
 * Return the number of valid elements (k).
 * * LOGIC EXPLANATION (Generalized Two-Pointer):
 * We can allow an element to be copied if it doesn't match the element 
 * TWO positions behind our write pointer (nums[index - 2]). 
 * - The first two elements (indices 0 and 1) are always allowed to stay because 
 * an element cannot appear more than twice if we haven't even filled two slots.
 * - For any element beyond that, we check: Is nums[i] != nums[index - 2]? 
 * If it's different, it's safe to keep.
 */
public class ArrayDuplicateRemover {

    // --- PART 1: Max 1 Occurrence Allowed ---
    public int removeDuplicatesPart1(int[] nums) {
        if (nums.length == 0) return 0;

        // The first element is always unique, so we start writing at index 1
        int index = 1;

        for (int i = 1; i < nums.length; i++) {
            /* * Compare the current scanned element (nums[i]) with the
             * last unique element we locked in (nums[index - 1]).
             */
            if (nums[i] != nums[index - 1]) {
                nums[index] = nums[i]; // Store the new unique element
                index++;               // Move write pointer forward
            }
        }
        return index; // Return total unique elements
    }

    // --- PART 2: Max 2 Occurrences Allowed ---
    public int removeDuplicatesPart2(int[] nums) {
        if (nums.length <= 2) return nums.length;

        // The first two slots can be filled unconditionally
        int index = 2;

        for (int i = 2; i < nums.length; i++) {
            /* * Look back two positions from where we are about to write (nums[index - 2]).
             * If the scanned element (nums[i]) is different, it means we haven't
             * exceeded our quota of 2 allowed duplicates for this number yet.
             */
            if (nums[i] != nums[index - 2]) {
                nums[index] = nums[i]; // Store the valid element
                index++;               // Move write pointer forward
            }
        }
        return index; // Return total valid elements
    }

    public static void main(String[] args) {
        ArrayDuplicateRemover remover = new ArrayDuplicateRemover();

        /*
         * ====================================================================
         * DETAILED STEP-BY-STEP EXAMPLE TRACING (PART 1)
         * ====================================================================
         * Array: [1, 1, 2]
         * * [Initialization]: index = 1 (Since index 0 is already unique)
         * * - i = 1 (nums[1] = 1): nums[1] == nums[index - 1] (nums[0] which is 1).
         * It's a duplicate! Skip it. index remains 1.
         * * - i = 2 (nums[2] = 2): nums[2] != nums[index - 1] (nums[0] which is 1).
         * New unique found! Copy nums[2] to nums[index] -> nums[1] becomes 2.
         * Increment index to 2. Array is now: [1, 2, 2]
         * * [End of Loop]: index is 2. Unique section is [1, 2].
         * ====================================================================
         * * ====================================================================
         * DETAILED STEP-BY-STEP EXAMPLE TRACING (PART 2)
         * ====================================================================
         * Array: [1, 1, 1, 2]
         * * [Initialization]: index = 2 (First two positions are granted unconditionally)
         * * - i = 2 (nums[2] = 1): Check if nums[2] != nums[index - 2] (nums[0] which is 1).
         * Since 1 == 1, it means we already have 2 copies of '1' locked in. Skip it!
         * index remains 2.
         * * - i = 3 (nums[3] = 2): Check if nums[3] != nums[index - 2] (nums[0] which is 1).
         * Since 2 != 1, this is valid to write! 
         * Copy nums[3] to nums[index] -> nums[2] becomes 2.
         * Increment index to 3. Array is now: [1, 1, 2, 2]
         * * [End of Loop]: index is 3. Valid modified section is [1, 1, 2].
         * ====================================================================
         */

        // ==========================================
        // TEST CASE FOR PART 1 (Unique Elements)
        // ==========================================
        int[] nums1 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println("--- PART 1: Max 1 Duplicate Allowed ---");
        System.out.println("Original Array: " + Arrays.toString(nums1));

        int k1 = remover.removeDuplicatesPart1(nums1);

        System.out.println("Returned Length (k): " + k1);
        System.out.println("Modified Section: " + Arrays.toString(Arrays.copyOfRange(nums1, 0, k1)));
        System.out.println("Full Memory State: " + Arrays.toString(nums1));

        System.out.println("\n--------------------------------------------------\n");

        // ==========================================
        // TEST CASE FOR PART 2 (Max 2 of Each Allowed)
        // ==========================================
        int[] nums2 = {1, 1, 1, 2, 2, 3};
        System.out.println("--- PART 2: Max 2 Duplicates Allowed ---");
        System.out.println("Original Array: " + Arrays.toString(nums2));

        int k2 = remover.removeDuplicatesPart2(nums2);

        System.out.println("Returned Length (k): " + k2);
        System.out.println("Modified Section: " + Arrays.toString(Arrays.copyOfRange(nums2, 0, k2)));
        System.out.println("Full Memory State: " + Arrays.toString(nums2));
    }
}