package DSA.JPMC; /**
 * PROBLEM: 3Sum
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] 
 * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * Notice that the solution set must not contain duplicate triplets.
 * 
 * EXAMPLE:
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * 
 * LOGIC:
 * 1. Sort the array first. Sorting helps us use the Two Pointer technique and easily skip duplicates.
 * 2. Iterate through the array with a pointer 'i' (fixing one number).
 * 3. Use two pointers 'left' (i + 1) and 'right' (end of array) to find the other two numbers.
 * 4. If sum == 0, add to result. Move both pointers, skipping over duplicate values to ensure unique triplets.
 * 5. If sum < 0, we need a larger number, so move 'left' rightwards.
 * 6. If sum > 0, we need a smaller number, so move 'right' leftwards.
 * 
 * DRY RUN: nums = [-1, 0, 1, 2, -1, -4] -> Sorted: [-4, -1, -1, 0, 1, 2]
 * - i = 0 (val = -4): left at 1 (-1), right at 5 (2). Sum = -4 - 1 + 2 = -3 (< 0, left++)
 * - ... eventually i moves to 1 (val = -1): left at 2 (-1), right at 5 (2). Sum = -1 - 1 + 2 = 0. Found [-1,-1,2].
 * - skip duplicates, left moves to 3 (0), right to 4 (1). Sum = -1 + 0 + 1 = 0. Found [-1,0,1].
 * 
 * COMPLEXITY: Time: O(N^2) (Sorting is O(N log N), loops take O(N^2)). Space: O(1) or O(N) depending on sort algorithm.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Learning supplement: triple enumeration is O(n^3); sorting plus a fixed
 * index and two pointers is O(n^2) time. Examples:
 * {@code [-1,0,1,2,-1,-4] -> [[-1,-1,2],[-1,0,1]]} and
 * {@code [0,1,1] -> []}. Invariant: for fixed i, the pointers search every
 * pair for the required complement; duplicate skips make each triplet unique.
 * Ask why sorting makes pointer movement safe.
 */
public class ThreeSum {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // Step 1: Sort the array
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate values for 'i' to avoid duplicate triplets
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                    
                    // Skip duplicate values for 'left'
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    // Skip duplicate values for 'right'
                    while (left < right && nums[right] == nums[right + 1]) right--;
                    
                } else if (sum < 0) {
                    left++; // Need a larger sum
                } else {
                    right--; // Need a smaller sum
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println("Triplets: " + threeSum(nums)); 
        // Output: [[-1, -1, 2], [-1, 0, 1]]
    }
}