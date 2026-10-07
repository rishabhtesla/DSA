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