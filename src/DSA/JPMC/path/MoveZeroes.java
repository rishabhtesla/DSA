package DSA.JPMC.path; /**
 * PROBLEM: Move all zeroes to the end of an array.
 * Given an integer array nums, move all 0's to the end of it while maintaining the 
 * relative order of the non-zero elements. Must be done in-place without making a copy.
 * 
 * EXAMPLE:
 * Input: nums = [0, 1, 0, 3, 12]
 * Output: [1, 3, 12, 0, 0]
 * 
 * LOGIC:
 * Use a pointer (`insertPosition`) to keep track of where the next non-zero element should go.
 * Iterate through the array. Whenever we find a non-zero element, we place it at `insertPosition` 
 * and increment `insertPosition`.
 * After the loop, all non-zero elements are in their correct relative order at the front.
 * Finally, fill the remaining indices from `insertPosition` to the end of the array with 0s.
 * 
 * DRY RUN: nums = [0, 1, 0, 3, 12]
 * - i=0 (val 0): Do nothing. (insertPosition = 0)
 * - i=1 (val 1): Place 1 at insertPosition(0). nums=[1,1,0,3,12]. insertPosition becomes 1.
 * - i=2 (val 0): Do nothing. (insertPosition = 1)
 * - i=3 (val 3): Place 3 at insertPosition(1). nums=[1,3,0,3,12]. insertPosition becomes 2.
 * - i=4 (val 12): Place 12 at insertPosition(2). nums=[1,3,12,3,12]. insertPosition becomes 3.
 * - End of loop. Now fill from index 3 to 4 with 0s -> [1,3,12,0,0].
 * 
 * COMPLEXITY: Time: O(N), Space: O(1)
 */
import java.util.Arrays;

public class MoveZeroes {
    public static void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) return;

        int insertPosition = 0;

        // Step 1: Shift all non-zero elements to the front of the array
        for (int num : nums) {
            if (num != 0) {
                nums[insertPosition] = num;
                insertPosition++;
            }
        }

        // Step 2: Fill the remaining spaces with zeroes
        while (insertPosition < nums.length) {
            nums[insertPosition] = 0;
            insertPosition++;
        }
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);
        System.out.println("Result: " + Arrays.toString(nums)); 
        // Output: [1, 3, 12, 0, 0]
    }
}