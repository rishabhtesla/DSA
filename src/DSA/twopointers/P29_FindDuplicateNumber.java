package DSA.twopointers;

/**
 * ============================================================================
 * [29 / 34] - FIND THE DUPLICATE NUMBER (LeetCode 287)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of integers `nums` containing `n + 1` integers where each integer
 * is in the range `[1, n]` inclusive. There is only **one repeated number** in `nums`,
 * return this repeated number.
 * **Constraints:** You must not modify the array `nums` and must use only `O(1)` extra space.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Sorting or Hash Set Approach:
 *    - Sort the array and check adjacent elements, or use a HashSet to track visited numbers.
 *    - Valid, but either modifies the array (violating constraints) or uses $O(n)$ extra space (violating the $O(1)$ space rule).
 *
 * 2. Optimal Linked List Cycle Detection (Floyd's Tortoise and Hare) Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** Since array values are within `[1, n]` and indices are within `[0, n]`,
 *      we can view the array as a functional graph (linked list) where `index -> nums[index]`.
 *    - Because a value appears multiple times, multiple indices point to the same next node,
 *      creating a **cycle**. The entrance to this cycle is precisely the duplicate number!
 *    - **Phase 1 (Detect Cycle):** Initialize `slow` and `fast` pointers at `nums[0]`. Move `slow` by 1 step (`slow = nums[slow]`)
 *      and `fast` by 2 steps (`fast = nums[nums[fast]]`) until they intersect.
 *    - **Phase 2 (Find Cycle Entrance):** Reset `slow` back to `nums[0]`, keeping `fast` at the intersection point.
 *      Advance both pointers 1 step at a time (`slow = nums[slow]`, `fast = nums[fast]`).
 *      The node where they meet is the start of the cycle—the duplicate number.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Both Phase 1 and Phase 2 traverse at most a linear number of steps.
 * - Space: O(1) - Constant auxiliary pointer space; the input array is left entirely unmodified.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Array: `[1, 3, 4, 2, 2]`
 * - Expected Duplicate: `2`
 *
 * VISUAL DRY RUN (for `[1, 3, 4, 2, 2]`):
 *   - Treat values as pointers (`index -> nums[index]`): `0 -> 1`, `1 -> 3`, `3 -> 2`, `2 -> 4`, `4 -> 2`.
 *   - Phase 1: `slow` and `fast` traverse the sequence until they meet inside the cycle at value `2`.
 *   - Phase 2: Reset `slow` to `nums[0]` (value `1`). Advance both 1 step at a time. They meet at node `2`,
 *     which is the cycle entry and the duplicate number.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does modeling the array as a linked list graph guarantee a cycle?
 *    (Because every element's value is between `1` and `n`, every index points to a valid index within bounds,
 *    and since there is a duplicate value, two different indices point to the same target node, forming a merge and a loop.)
 * 2. Why does resetting `slow` to `nums[0]` and moving both at equal speeds pinpoint the cycle entrance?
 *    (Based on mathematical properties of cycle detection, the distance from the head of the list to the cycle entrance
 *    is equal to the distance from the intersection point to the cycle entrance within the loop.)
 */
public class P29_FindDuplicateNumber {

    public static int findDuplicate(int[] nums) {
        // Phase 1: Detect the intersection point inside the cycle using Tortoise and Hare
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Phase 2: Find the entrance to the cycle (the duplicate number)
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 3, 4, 2, 2};
        int[] nums2 = {3, 1, 3, 4, 2};
        System.out.println("P29 Output (Test 1): " + findDuplicate(nums1)); // Expected: 2
        System.out.println("P29 Output (Test 2): " + findDuplicate(nums2)); // Expected: 3
    }
}