package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given an array of integers `nums` and an integer `target`, return indices of the 
    two numbers such that they add up to `target`. You may assume that each input 
    would have exactly one solution, and you may not use the same element twice.
    
    APPROACH (One-Pass Hash Map):
    - Create a HashMap to store values as keys and their indices as values.
    - Traverse the array. For each number `nums[i]`, calculate the `complement` needed 
      to hit the target: `complement = target - nums[i]`.
    - If the map already contains this `complement`, we've found a pair! Return the 
      index stored in the map along with the current index `i`.
    - Otherwise, store `nums[i]` and its index `i` in the map and continue.
    
    VISUAL DRY RUN EXAMPLES:
    ----------------------------------------------------------------------------
    Example 1: nums = [2, 7, 11, 15], target = 9
    - Initial: Map = {}
    - i = 0: nums[0] = 2. complement = 9 - 2 = 7. 
             Is 7 in Map? No. Map.put(2, 0) -> Map = {2: 0}
    - i = 1: nums[1] = 7. complement = 9 - 7 = 2. 
             Is 2 in Map? Yes! Map contains 2 at index 0.
    - End: Return indices [0, 1].
    
    Example 2: nums = [3, 2, 4], target = 6
    - Initial: Map = {}
    - i = 0: nums[0] = 3. comp = 6 - 3 = 3. In Map? No. Map.put(3, 0) -> Map = {3: 0}
    - i = 1: nums[1] = 2. comp = 6 - 2 = 4. In Map? No. Map.put(2, 1) -> Map = {3: 0, 2: 1}
    - i = 2: nums[2] = 4. comp = 6 - 4 = 2. In Map? Yes! Map contains 2 at index 1.
    - End: Return indices [1, 2].
    
    TIME COMPLEXITY: O(N) - We traverse the list containing N elements only once. Lookups in the hash table cost O(1).
    SPACE COMPLEXITY: O(N) - The extra space required depends on the number of items stored in the hash table, which stores at most N elements.
    ================================================================================
    */
    public static int[] twoSum(int[] nums, int target) {
        // Fallback edge cases
        if (nums == null || nums.length < 2) {
            return new int[]{-1, -1};
        }
        
        // HashMap to store: Key = Element value, Value = Element index
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            // Check if the required complement has already been seen
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            
            // If not found, track the current element and its index
            map.put(nums[i], i);
        }
        
        // Return fallback indices if no valid pair exists
        return new int[]{-1, -1};
    }

    // Helper method to output results gracefully in the terminal
    private static void printResult(int[] nums, int target, int[] result) {
        System.out.println("Input Array   : " + Arrays.toString(nums));
        System.out.println("Target Value  : " + target);
        System.out.println("Result Indices: " + Arrays.toString(result));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE #1: TWO SUM ===\n");

        // Case 1: Standard positive sorted input
        int[] case1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] res1 = twoSum(case1, target1);
        printResult(case1, target1, res1);

        // Case 2: Unsorted array with duplicate values
        int[] case2 = {3, 2, 4};
        int target2 = 6;
        int[] res2 = twoSum(case2, target2);
        printResult(case2, target2, res2);

        // Case 3: Negative numbers included
        int[] case3 = {-3, 4, 3, 90};
        int target3 = 0;
        int[] res3 = twoSum(case3, target3);
        printResult(case3, target3, res3);

        // Case 4: Edge Case - Minimum valid constraints
        int[] case4 = {5, 5};
        int target4 = 10;
        int[] res4 = twoSum(case4, target4);
        printResult(case4, target4, res4);
    }
}