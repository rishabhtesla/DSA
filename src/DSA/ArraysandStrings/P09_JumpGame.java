package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [09 / 24] - JUMP GAME (LeetCode 55)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array nums where nums[i] represents your maximum jump length from index i,
 *   determine if you are able to reach the last index starting at index 0.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - DP approach: Index i is reachable if any reachable j < i can jump to i -> O(n^2).
 *   - Greedy Optimal (Furthest Reachable Index):
 *     Maintain `maxReach`, the furthest index currently accessible.
 *     At index i:
 *       1. If i > maxReach, index i is unreachable -> return false immediately.
 *       2. Update `maxReach = Math.max(maxReach, i + nums[i])`.
 *       3. If `maxReach >= n - 1`, target reached -> return true early.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single scan.
 *   - Space: O(1) - Single tracker variable.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P09_JumpGame {

    public static boolean canJump(int[] nums) {
        int maxReach = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) {
                return false; // Current index is beyond what was previously reachable
            }
            maxReach = Math.max(maxReach, i + nums[i]);
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 1, 1, 4};
        int[] nums2 = {3, 2, 1, 0, 4};
        System.out.println("P09 Output (Test 1): " + canJump(nums1)); // Expected: true
        System.out.println("P09 Output (Test 2): " + canJump(nums2)); // Expected: false
    }
}