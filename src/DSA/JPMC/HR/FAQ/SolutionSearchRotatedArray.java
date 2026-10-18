package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Search in Rotated Sorted Array
 * ============================================================================
 *
 * Problem Statement:
 * An ordered list of historical transaction timestamps `nums` sorted in strictly
 * ascending order was rotated at an unknown pivot index k (1 <= k < nums.length).
 *
 * For example, [0, 1, 2, 4, 5, 6, 7] rotated at pivot index 3 becomes:
 * [4, 5, 6, 7, 0, 1, 2].
 *
 * Given the rotated array `nums` of unique integers and an integer `target`,
 * return the index of target if it is in nums, or -1 if it is not in nums.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the length of nums.
 * - Next n lines: Each line contains an integer nums[i].
 * - Last line: An integer target.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - -10^9 <= nums[i], target <= 10^9
 * - All values of nums are unique.
 * - nums is guaranteed to be rotated at some pivot.
 *
 * Example 1:
 * Input:
 *   7
 *   4
 *   5
 *   6
 *   7
 *   0
 *   1
 *   2
 *   0
 * Output:
 *   4
 *
 * Example 2:
 * Input:
 *   7
 *   4
 *   5
 *   6
 *   7
 *   0
 *   1
 *   2
 *   3
 * Output:
 *   -1
 *
 * ============================================================================
 * LOGIC & APPROACH (Modified Binary Search with Sorted Half Invariant):
 * ============================================================================
 * 1. Key Invariant:
 *    For any split at midpoint `mid`, at least one of the two halves
 *    [low...mid] or [mid...high] is GUARANTEED to be normally sorted.
 *
 * 2. Decision Logic:
 *    - Calculate `mid = low + (high - low) / 2`.
 *    - If nums[mid] == target -> return mid.
 *
 *    - Case A: Left half [low...mid] is sorted (nums[low] <= nums[mid]):
 *        - Check if target falls strictly within the left boundary:
 *          (nums[low] <= target && target < nums[mid])
 *          If yes: high = mid - 1
 *          Else:   low = mid + 1
 *
 *    - Case B: Right half [mid...high] is sorted (nums[low] > nums[mid]):
 *        - Check if target falls strictly within the right boundary:
 *          (nums[mid] < target && target <= nums[high])
 *          If yes: low = mid + 1
 *          Else:   high = mid - 1
 *
 * Complexity:
 * - Time: O(log n) strictly eliminates half the search space per iteration.
 * - Space: O(1) auxiliary space.
 *
 * ============================================================================
 * DRY RUN (nums = [4, 5, 6, 7, 0, 1, 2], target = 0):
 * ============================================================================
 * low = 0, high = 6
 *
 * Iteration 1:
 * - mid = 3, nums[mid] = 7 != 0
 * - nums[low] (4) <= nums[mid] (7) -> Left half [4, 5, 6, 7] is sorted.
 * - Is target in [4..7)? 4 <= 0 < 7 is false.
 * - Target must lie in right half: low = mid + 1 = 4.
 *
 * Iteration 2:
 * - low = 4, high = 6
 * - mid = 5, nums[mid] = 1 != 0
 * - nums[low] (0) <= nums[mid] (1) -> Left half [0, 1] is sorted.
 * - Is target in [0..1)? 0 <= 0 < 1 is true!
 * - Target is in left half: high = mid - 1 = 4.
 *
 * Iteration 3:
 * - low = 4, high = 4
 * - mid = 4, nums[mid] = 0 == target (0) -> Target found at index 4!
 *
 * Final Result: 4
 * ============================================================================
 */
public class SolutionSearchRotatedArray {

    public static int search(List<Integer> nums, int target) {
        if (nums == null || nums.isEmpty()) {
            return -1;
        }

        int low = 0;
        int high = nums.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midVal = nums.get(mid);

            if (midVal == target) {
                return mid;
            }

            // Check if left half is sorted
            if (nums.get(low) <= midVal) {
                if (nums.get(low) <= target && target < midVal) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            // Otherwise, right half is sorted
            else {
                if (midVal < target && target <= nums.get(high)) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Integer> nums = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            nums.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        int target = Integer.parseInt(bufferedReader.readLine().trim());

        int result = search(nums, target);
        System.out.println(result);

        bufferedReader.close();
    }
}