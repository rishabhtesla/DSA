package DSA.twopointers;

import java.util.Arrays;

/**
 * ============================================================================
 * [32 / 34] - TWO SUM II - INPUT ARRAY IS SORTED (LeetCode 167)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given a 1-indexed array of integers numbers that is already sorted in non-decreasing
 *   order, find two numbers such that they add up to a specific target number.
 *   Return the 1-based indices [index1, index2].
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Because the array is SORTED, two pointers from opposite ends are monotonic:
 *     `left = 0`, `right = numbers.length - 1`.
 *     Let `sum = numbers[left] + numbers[right]`.
 *     - If `sum == target`: Return `[left + 1, right + 1]`.
 *     - If `sum < target`: The only way to increase the sum is to advance `left++`.
 *     - If `sum > target`: The only way to decrease the sum is to decrement `right--`.
 *   - Never misses the pair because each step rules out a candidate row/column.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Each step moves left or right; at most n iterations.
 *   - Space: O(1) - Constant auxiliary space.
 *
 *
 * EXAMPLE:
 *   The first main numbers are [2,7,11,15] and target=9; expected 1-indexed result [1,2].
 *
 * VISUAL DRY RUN:
 *   L=0,R=3 gives 2+15=17>9, decrement R to 2; 2+11=13>9, decrement R to 1;
 *   2+7=9, return [L+1,R+1]=[1,2].
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P32_TwoSumII {

    public static int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1}; // 1-indexed requirement
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        System.out.println("P32 Output: " + Arrays.toString(twoSum(numbers, target)));
        // Expected: [1, 2]
    }
}