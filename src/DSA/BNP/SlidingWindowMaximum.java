package DSA.BNP;

import java.util.*;

/**
 * ============================================================================
 * QUESTION 3: Sliding Window Maximum (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * You are given an array of integers `nums`, and there is a sliding window of
 * size `k` which is moving from the very left of the array to the very right.
 * You can only see the `k` numbers in the window. Each time the sliding window
 * moves right by one position, return the max sliding window.
 *
 * INPUT FORMAT:
 * - An integer array `nums` of length n
 * - An integer `k` representing the window size
 *
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - 1 <= k <= nums.length
 *
 * SAMPLE INPUT 0:
 *   nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
 *
 * SAMPLE OUTPUT 0:
 *   [3, 3, 5, 5, 6, 7]
 *
 * EXPLANATION 0:
 *   Window position                Max
 *   ---------------               -----
 *   [1  3  -1] -3  5  3  6  7       3
 *    1 [3  -1  -3] 5  3  6  7       3
 *    1  3 [-1  -3  5] 3  6  7       5
 *    1  3  -1 [-3  5  3] 6  7       5
 *    1  3  -1  -3 [5  3  6] 7       6
 *    1  3  -1  -3  5 [3  6  7]      7
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3):
 * ----------------------------------------------------------------------------
 * Use a Monotonic Decreasing Deque storing INDICES of useful elements.
 * - Front of deque (peekFirst) is ALWAYS the index of the current window max.
 * - Before adding index `i`, pop any index from the back whose value is < nums[i]
 *   (because that smaller value can never be the max while nums[i] is in window).
 *
 * i = 0 (val = 1):  deque=[] -> offer(0) -> deque=[0(1)]
 * i = 1 (val = 3):  3 > nums[0](1) -> popBack(0) -> offer(1) -> deque=[1(3)]
 * i = 2 (val = -1): -1 < nums[1](3) -> offer(2) -> deque=[1(3), 2(-1)]
 *                   i >= 2 -> record nums[1] = 3.  Result = [3]
 * i = 3 (val = -3): -3 < nums[2](-1) -> offer(3) -> deque=[1(3), 2(-1), 3(-3)]
 *                   i >= 2 -> record nums[1] = 3.  Result = [3, 3]
 * i = 4 (val = 5):  Index 1 < (4 - 3 + 1 = 2) -> out of window, popFront(1).
 *                   5 > nums[3](-3) & nums[2](-1) -> popBack(3), popBack(2).
 *                   offer(4) -> deque=[4(5)].
 *                   i >= 2 -> record nums[4] = 5.  Result = [3, 3, 5]
 * i = 5 (val = 3):  3 < nums[4](5) -> offer(5) -> deque=[4(5), 5(3)]
 *                   i >= 2 -> record nums[4] = 5.  Result = [3, 3, 5, 5]
 * i = 6 (val = 6):  6 > nums[5](3) & nums[4](5) -> popBack(5), popBack(4).
 *                   offer(6) -> deque=[6(6)].
 *                   i >= 2 -> record nums[6] = 6.  Result = [3, 3, 5, 5, 6]
 * i = 7 (val = 7):  7 > nums[6](6) -> popBack(6) -> offer(7) -> deque=[7(7)].
 *                   i >= 2 -> record nums[7] = 7.  Result = [3, 3, 5, 5, 6, 7]
 *
 * Time Complexity:  O(N) - each index is pushed and popped at most once
 * Space Complexity: O(K) - deque holds at most k indices
 * ============================================================================
 */
public class SlidingWindowMaximum {

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // Stores indices in decreasing value order

        for (int i = 0; i < n; i++) {
            // 1. Remove front index if it has slid outside the window [i - k + 1, i]
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // 2. Remove all back indices whose values are smaller than current nums[i]
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // 3. Push current index onto the back of the deque
            deque.offerLast(i);

            // 4. Once first full window of size k is formed (i >= k - 1), record max
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println("Sample 0 Input:  nums = " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("Sample 0 Output: " + Arrays.toString(maxSlidingWindow(nums, k)));
        // Expected: [3, 3, 5, 5, 6, 7]
    }
}