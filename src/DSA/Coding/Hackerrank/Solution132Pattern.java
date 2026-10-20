package DSA.Coding.Hackerrank;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ============================================================================
 * LEETCODE 456: 132 Pattern[cite: 1, 2]
 * ============================================================================
 * 
 * ----------------------------------------------------------------------------
 * 1. PROBLEM STATEMENT
 * ----------------------------------------------------------------------------
 * Given an array of n integers nums, a 132 pattern is a subsequence of three[cite: 1, 2]
 * integers nums[i], nums[j] and nums[k] such that:[cite: 1, 2]
 *         i < j < k  AND  nums[i] < nums[k] < nums[j][cite: 1, 2]
 * 
 * Return true if there is a 132 pattern in nums, otherwise return false[cite: 1, 2].
 * 
 * Note: Subsequence means indices i, j, k do not need to be contiguous[cite: 1, 2].
 * 
 * ----------------------------------------------------------------------------
 * 2. EXAMPLES
 * ----------------------------------------------------------------------------
 * Example 1:
 * Input: nums = [1, 2, 3, 4][cite: 1, 2]
 * Output: false[cite: 1, 2]
 * Explanation: There is no 132 pattern (it is strictly increasing)[cite: 1, 2].
 * 
 * Example 2:
 * Input: nums = [3, 1, 4, 2][cite: 1, 2]
 * Output: true[cite: 1, 2]
 * Explanation: The sequence contains [1, 4, 2] where 1 < 2 < 4 and indices 1 < 2 < 3[cite: 1, 2].
 * 
 * Example 3:
 * Input: nums = [-1, 3, 2, 0][cite: 1, 2]
 * Output: true[cite: 1, 2]
 * Explanation: There are three valid 132 patterns: [-1, 3, 2], [-1, 3, 0], and [-1, 2, 0][cite: 1, 2].
 * 
 * ----------------------------------------------------------------------------
 * 3. INTUITION & APPROACH (Monotonic Decreasing Stack from Right-to-Left)
 * ----------------------------------------------------------------------------
 * Goal: Find three values matching:
 *       nums[i]   <   nums[k]   <   nums[j]
 *       ('1')         ('2')         ('3')
 *       index i   <   index k   (where j < k)
 * 
 * Strategy:
 * 1. Scan from RIGHT to LEFT (k and j are evaluated before i).
 * 2. Maintain `nums[k]` (our '2' candidate) as the largest possible valid value 
 *    found so far to the right of our current pointer.
 * 3. Use a monotonic decreasing stack to store potential candidates for `nums[j]` ('3').
 * 4. While traversing backwards with current element as `nums[j]`:
 *    - If `nums[current] < nums[k]`:
 *      We immediately found a valid `nums[i]` ('1') because `nums[k]` ('2') 
 *      was already confirmed to have a larger `nums[j]` ('3') to its left! Return true.
 *    - While stack is not empty and `nums[current] > stack.peek()`:
 *      Current element is greater than the stack top, making current element a valid '3'
 *      and the popped stack element a valid '2' (`nums[k]`). 
 *      We greedily update `nums[k] = stack.pop()`.
 *    - Push `nums[current]` onto the stack (as a candidate for '3').
 * 
 * ----------------------------------------------------------------------------
 * 4. STEP-BY-STEP DRY RUN (Example 2: nums = [3, 1, 4, 2])[cite: 1, 2]
 * ----------------------------------------------------------------------------
 * Initial State:
 *   stack = []
 *   nums_k ('2') = -INFINITY
 * 
 * Step 1: index = 3, num = 2
 *   - num (2) < nums_k (-INF)? No.
 *   - Stack is empty.
 *   - Push 2 -> stack = [2]
 *   - nums_k remains -INF.
 * 
 * Step 2: index = 2, num = 4
 *   - num (4) < nums_k (-INF)? No.
 *   - num (4) > stack.peek() (2)? Yes:
 *       nums_k = stack.pop() = 2   <-- We found a valid '3' (4) and '2' (2) pair!
 *   - Push 4 -> stack = [4]
 *   - nums_k = 2
 * 
 * Step 3: index = 1, num = 1
 *   - num (1) < nums_k (2)? YES!
 *     -> 1 serves as nums[i] ('1')
 *     -> 4 is nums[j] ('3')
 *     -> 2 is nums[k] ('2')
 *     -> Condition met: 1 < 2 < 4 and indices 1 < 2 < 3.
 *   - Immediately return TRUE.
 * 
 * ----------------------------------------------------------------------------
 * 5. COMPLEXITY ANALYSIS
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N)
 *   Every element is pushed onto and popped from the stack at most once.
 * - Space Complexity: O(N)
 *   Stack stores at most N elements in the worst case (strictly increasing array).
 * ============================================================================
 */
public class Solution132Pattern {

    /**
     * Determines if a 132 pattern exists in the given array.
     *
     * @param nums Array of integers
     * @return true if any valid 132 pattern subsequence exists, otherwise false
     */
    public boolean find132pattern(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        // Holds candidates for nums[j] ('3') in monotonically decreasing order
        Deque<Integer> stack = new ArrayDeque<>();
        
        // Holds the largest valid nums[k] ('2') found so far
        int numsK = Integer.MIN_VALUE;

        // Traverse from right to left
        for (int i = nums.length - 1; i >= 0; i--) {
            // If current element is less than numsK, we found nums[i] ('1')
            if (nums[i] < numsK) {
                return true;
            }

            // If current element can act as nums[j] ('3') for elements in stack,
            // pop them and greedily take the largest popped value as nums[k] ('2')
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                numsK = stack.pop();
            }

            // Push current element as a potential nums[j] ('3')
            stack.push(nums[i]);
        }

        return false;
    }

    public static void main(String[] args) {
        Solution132Pattern solver = new Solution132Pattern();

        // Test 1: [1, 2, 3, 4] -> false[cite: 1, 2]
        int[] test1 = {1, 2, 3, 4}; //[cite: 1, 2]
        System.out.println("Test 1: " + solver.find132pattern(test1)); // Output: false[cite: 1, 2]

        // Test 2: [3, 1, 4, 2] -> true[cite: 1, 2]
        int[] test2 = {3, 1, 4, 2}; //[cite: 1, 2]
        System.out.println("Test 2: " + solver.find132pattern(test2)); // Output: true[cite: 1, 2]

        // Test 3: [-1, 3, 2, 0] -> true[cite: 1, 2]
        int[] test3 = {-1, 3, 2, 0}; //[cite: 1, 2]
        System.out.println("Test 3: " + solver.find132pattern(test3)); // Output: true[cite: 1, 2]
    }
}