package DSA.twopointers;

/**
 * ============================================================================
 * [29 / 34] - FIND THE DUPLICATE NUMBER (LeetCode 287)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array of integers nums containing n + 1 integers where each integer
 *   is in the range [1, n] inclusive. There is only one repeated number in nums,
 *   return this repeated number.
 *   Constraints: You must not modify the array nums and must use only O(1) extra space.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Model as a Linked List with a Cycle:
 *     Since array values are in [1, n] and indices are in [0, n], we can view
 *     the array as a linked list where `index -> nums[index]`.
 *     Because a value appears multiple times, multiple indices point to the same next node.
 *     This creates a cycle in the functional graph. The entrance to the cycle is the duplicate!
 *   - Floyd's Cycle Detection (Tortoise and Hare):
 *     Phase 1: Detect cycle. `slow = nums[slow]`, `fast = nums[nums[fast]]`.
 *              They are guaranteed to meet inside the loop.
 *     Phase 2: Find cycle entrance. Reset `slow = nums[0]`, keep `fast` at intersection point.
 *              Move both at 1 step per turn: `slow = nums[slow]`, `fast = nums[fast]`.
 *              The node where they meet is the start of the cycle (the duplicate number).
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Both phases traverse at most linear steps.
 *   - Space: O(1) - Pointers only. Array is untouched.
 */
public class P29_FindDuplicateNumber {

    public static int findDuplicate(int[] nums) {
        // Phase 1: Detect intersection point inside the cycle
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Phase 2: Find the entrance to the cycle
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