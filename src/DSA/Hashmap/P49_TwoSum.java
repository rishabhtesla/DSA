package DSA.Hashmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [49 / 52] - TWO SUM (LeetCode 1)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array of integers nums and an integer target, return indices of the
 *   two numbers such that they add up to target. Each input has exactly one solution,
 *   and you may not use the same element twice.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Naive: Nested loops -> O(n^2).
 *   - One-Pass Hash Map (Complement Lookup):
 *     As you iterate across index `i` with value `nums[i]`:
 *     - Compute required complement: `complement = target - nums[i]`.
 *     - If `map.containsKey(complement)`, you found the pair -> return `[map.get(complement), i]`.
 *     - Otherwise, store `map.put(nums[i], i)`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass with O(1) average lookup per item.
 *   - Space: O(n) - Hash map storing up to n elements.
 *
 *
 * EXAMPLE:
 *   The first main nums=[2,7,11,15], target=9; expected zero-based indices are [0,1].
 *
 * VISUAL DRY RUN:
 *   i=0 value 2, complement 7 absent -> map={2:0}; i=1 value 7, complement 2 found
 *   at index 0 -> return [0,1].
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P49_TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }

            map.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println("P49 Output: " + Arrays.toString(twoSum(nums, target)));
        // Expected: [0, 1]
    }
}