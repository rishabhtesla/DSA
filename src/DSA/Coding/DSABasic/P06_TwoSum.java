package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P06: TWO SUM
 *
 * Question
 * Find two distinct indices whose values add to target.
 *
 * Example
 * [2,7,11,15], 9 -> [0,1].
 *
 * Constraints
 * 2 <= n <= 10^4; exactly one answer; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Store value-to-index complements while scanning once.
 * 2. For each value, check whether its complement target - value was seen earlier.
 * 3. If the complement exists, return its saved index and the current index; otherwise save the current value.
 *
 * Dry run
 * Example from main: [2,7,11,15], 9 -> [0,1].
 * 1. At index 0, value 2 needs 7; 7 is not in the map, so save 2 -> 0.
 * 2. At index 1, value 7 needs 2; 2 is in the map at index 0.
 * 3. Return [0, 1] immediately because values 2 and 7 add to 9.
 *
 * Complexity
 * O(n) time and O(n) auxiliary space for the value-to-index map.
 */
public class P06_TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int index = 0; index < nums.length; index++) {
            if (indexMap.containsKey(target - nums[index])) return new int[]{indexMap.get(target - nums[index]), index};
            indexMap.put(nums[index], index);
        }
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
    }
}
