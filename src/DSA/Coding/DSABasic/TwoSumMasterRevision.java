package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumMasterRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given an array of integers `nums` and an integer `target`, return indices of the 
    two numbers such that they add up to `target`. You may assume that each input 
    would have exactly one solution, and you may not use the same element twice.
    You can return the answer in any order.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: nums = [2, 7, 11, 15], target = 9 [00:00:46]
    - We check pairs to see which two values equal 9.
    - index 0 (2) + index 1 (7) = 9. 
    - Output: [0, 1]

    Example B: nums = [3, 2, 4], target = 6 [00:01:40]
    - Pair pairs: (3+2=5), (3+4=7), (2+4=6).
    - index 1 (2) + index 2 (4) = 6. 
    - Output: [1, 2]

    APPROACH 1: Brute Force Nested Loop (As shown in video)
    - Set an outer pointer `i` from `0` to `nums.length - 1` [00:03:41].
    - Set an inner pointer `j` from `i + 1` to `nums.length` to check all unique pairs [00:03:59].
    - If `nums[i] + nums[j] == target`, save `i` and `j` in a new 2-element array and return it [00:04:25].
    - TIME COMPLEXITY: O(N^2) - Two loops scanning all pair permutations.
    - SPACE COMPLEXITY: O(1) - Only allocating the static return array.

    VISUAL DRY RUN (Brute Force Strategy for nums = [3, 2, 4], target = 6):
    ----------------------------------------------------------------------------
    - i = 0 (nums[i] = 3):
        - j = 1 (nums[j] = 2): 3 + 2 = 5 != 6 -> Continue
        - j = 2 (nums[j] = 4): 3 + 4 = 7 != 6 -> Continue
    - i = 1 (nums[i] = 2):
        - j = 2 (nums[j] = 4): 2 + 4 = 6 == 6 -> Target Found!       [00:07:21]
    - Returns: [1, 2]

    APPROACH 2: One-Pass Hash Map (Optimized)
    - Instead of checking backwards dynamically with loops, track elements in a HashMap.
    - Key = value of the element, Value = index of the element.
    - For each element, look for its needed `complement` (`target - nums[i]`) inside the map.
    - If found, return `[map.get(complement), i]`. Otherwise, record current state and advance.
    - TIME COMPLEXITY: O(N) - Single loop, constant hash dictionary search operations.
    - SPACE COMPLEXITY: O(N) - Map stores key mappings proportionally to N.
    ================================================================================
    */

    // Approach 1: Nested Loop Simulation (Video Implementation)
    public static int[] twoSumBruteForce(int[] nums, int target) {
        int[] answer = new int[2];
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    answer[0] = i;
                    answer[1] = j;
                    return answer;
                }
            }
        }
        return answer;
    }

    // Approach 2: One-Pass Hash Map (Optimized Alternative Strategy)
    public static int[] twoSumHashMap(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        return new int[] {-1, -1};
    }

    private static void verifyBothApproaches(int[] nums, int target) {
        System.out.println("Input Array: " + Arrays.toString(nums) + " | Target: " + target);
        System.out.println("1. Video Brute Force: " + Arrays.toString(twoSumBruteForce(nums, target)));
        System.out.println("2. Optimized HashMap: " + Arrays.toString(twoSumHashMap(nums, target)));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 1: TWO SUM ===\n");

        // Case 1: First sample case from the video 
        verifyBothApproaches(new int[]{2, 7, 11, 15}, 9);

        // Case 2: Second sample dry run case from the video [00:01:40]
        verifyBothApproaches(new int[]{3, 2, 4}, 6);

        // Case 3: Edge Case - Minimum valid bounds with matching values
        verifyBothApproaches(new int[]{3, 3}, 6);
    }
}