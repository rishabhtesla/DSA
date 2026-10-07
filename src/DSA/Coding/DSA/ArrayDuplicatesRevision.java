package DSA.Coding.DSA;

import java.util.Arrays;

public class ArrayDuplicatesRevision {

    /*
    ================================================================================
    PROBLEM 1: Remove Duplicates from Sorted Array (At most 1 occurrence)
    
    APPROACH (Two Pointers):
    - Index 0 is always unique, so we start the placement pointer `j = 1`.
    - Loop pointer `i` starts from 1. If `nums[i] != nums[j - 1]`, it's a new unique element.
    - Place it at `nums[j]` and increment `j`.
    
    VISUAL DRY RUN EXAMPLES:
    ----------------------------------------------------------------------------
    Example 1: nums = [1, 1, 2]
    - Start: j = 1, i = 1
    - i = 1: nums[1] (1) == nums[j-1] (nums[0] = 1) -> Duplicate! Skip.
    - i = 2: nums[2] (2) != nums[j-1] (nums[0] = 1) -> Unique! 
             Assign nums[j] = nums[2] -> nums[1] = 2. Advance j to 2.
    - End of loop. Returns j = 2. Array state: [1, 2, 2] (Valid prefix: [1, 2])
    
    Example 2: nums = [0, 0, 1, 2]
    - Start: j = 1
    - i = 1: 0 == nums[0] (0) -> Skip
    - i = 2: 1 != nums[0] (0) -> nums[1] = 1, j becomes 2 -> Array: [0, 1, 1, 2]
    - i = 3: 2 != nums[1] (1) -> nums[2] = 2, j becomes 3 -> Array: [0, 1, 2, 2]
    - End of loop. Returns j = 3. Valid prefix: [0, 1, 2]
    ================================================================================
    */
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int j = 1; 
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[j - 1]) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j; 
    }

    /*
    ================================================================================
    PROBLEM 2: Remove Duplicates from Sorted Array II (At most 2 occurrences)
    
    APPROACH (Two Pointers):
    - The first two elements (indices 0 and 1) are always safe, so `j = 2`.
    - Loop pointer `i` starts from 2. We compare `nums[i]` with the element placed 
      two slots back: `nums[j - 2]`. If they don't match, it's safe to add.
    - Place it at `nums[j]` and increment `j`.
    
    VISUAL DRY RUN EXAMPLES:
    ----------------------------------------------------------------------------
    Example 1: nums = [1, 1, 1, 2, 2, 3]
    - Start: j = 2, i = 2
    - i = 2: nums[2] (1) == nums[j-2] (nums[0] = 1) -> 3rd duplicate! Skip.
    - i = 3: nums[3] (2) != nums[j-2] (nums[0] = 1) -> Safe! 
             Assign nums[j] = nums[3] -> nums[2] = 2. Advance j to 3. Array: [1, 1, 2, 2, 2, 3]
    - i = 4: nums[4] (2) != nums[j-2] (nums[1] = 1) -> Safe! (Only 2nd copy of '2' in target area)
             Assign nums[j] = nums[4] -> nums[3] = 2. Advance j to 4. Array: [1, 1, 2, 2, 2, 3]
    - i = 5: nums[5] (3) != nums[j-2] (nums[2] = 2) -> Safe!
             Assign nums[j] = nums[5] -> nums[4] = 3. Advance j to 5. Array: [1, 1, 2, 2, 3, 3]
    - End of loop. Returns j = 5. Valid prefix: [1, 1, 2, 2, 3]
    ================================================================================
    */
    public static int removeDuplicatesAtMostTwice(int[] nums) {
        if (nums == null) return 0;
        if (nums.length <= 2) return nums.length;
        
        int j = 2; 
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] !=  nums[j - 2]) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }

    // Helper method to visually print the results based on returned valid length 'k'
    private static void printResult(int[] original, int[] modified, int k) {
        System.out.println("Input Array   : " + Arrays.toString(original));
        System.out.println("Returned count: k = " + k);
        
        // Extract the valid part of the modified array up to length k
        int[] validPart = Arrays.copyOf(modified, k);
        System.out.println("Modified Array: " + Arrays.toString(modified));
        System.out.println("Valid Prefix  : " + Arrays.toString(validPart));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== PART 1: REMOVE DUPLICATES (AT MOST 1 OCCURRENCE) ===\n");

        // Case 1: Simple array
        int[] case1 = {1, 1, 2};
        int[] orig1 = case1.clone();
        int k1 = removeDuplicates(case1);
        printResult(orig1, case1, k1);

        // Case 2: Standard array with multiple duplicates
        int[] case2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int[] orig2 = case2.clone();
        int k2 = removeDuplicates(case2);
        printResult(orig2, case2, k2);

        // Case 3: Edge Case - Empty array
        int[] case3 = {};
        int[] orig3 = case3.clone();
        int k3 = removeDuplicates(case3);
        printResult(orig3, case3, k3);

        // Case 4: Edge Case - Single element
        int[] case4 = {5};
        int[] orig4 = case4.clone();
        int k4 = removeDuplicates(case4);
        printResult(orig4, case4, k4);


        System.out.println("\n=== PART 2: REMOVE DUPLICATES II (AT MOST 2 OCCURRENCES) ===\n");

        // Case 5: Standard array with triple duplicates
        int[] case5 = {1, 1, 1, 2, 2, 3};
        int[] orig5 = case5.clone();
        int k5 = removeDuplicatesAtMostTwice(case5);
        printResult(orig5, case5, k5);

        // Case 6: Highly duplicated array
        int[] case6 = {0, 0, 1, 1, 1, 1, 2, 3, 3, 3};
        int[] orig6 = case6.clone();
        int k6 = removeDuplicatesAtMostTwice(case6);
        printResult(orig6, case6, k6);

        // Case 7: Edge Case - Array size <= 2
        int[] case7 = {1, 1};
        int[] orig7 = case7.clone();
        int k7 = removeDuplicatesAtMostTwice(case7);
        printResult(orig7, case7, k7);
    }
}