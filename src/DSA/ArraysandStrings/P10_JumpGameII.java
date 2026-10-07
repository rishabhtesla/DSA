package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [10 / 24] - JUMP GAME II (LeetCode 45)
 * ============================================================================
 * 
 * PROBLEM:
 *   Return the minimum number of jumps to reach index n - 1.
 *   You are guaranteed that you can reach the end.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Think of this as an implicit BFS on 1D intervals (jump frontiers):
 *     - `currentEnd`: The right boundary of the current jump level.
 *     - `farthest`: The furthest index reachable with one extra jump from this level.
 *   - Iterate from index 0 up to n - 2 (we do not jump once we reach or pass n - 1):
 *     - Update `farthest = Math.max(farthest, i + nums[i])`.
 *     - When `i == currentEnd`, we've exhausted our current jump -> increment `jumps`,
 *       set `currentEnd = farthest`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Linear pass through the array.
 *   - Space: O(1) - Constant variables.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P10_JumpGameII {

    public static int jump(int[] nums) {
        if (nums.length <= 1) return 0;

        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            // Exhausted the range of the current jump level
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;

                if (currentEnd >= nums.length - 1) {
                    break;
                }
            }
        }

        return jumps;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println("P10 Output: " + jump(nums));
        // Expected: 2 (0 -> 1 -> 4)
    }
}