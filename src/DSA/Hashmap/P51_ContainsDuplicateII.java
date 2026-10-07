package DSA.Hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [51 / 52] - CONTAINS DUPLICATE II (LeetCode 219)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an integer array nums and an integer k, return true if there are two
 *   distinct indices i and j in the array such that nums[i] == nums[j] and
 *   abs(i - j) <= k.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Track Last Seen Index:
 *     Map each number to its most recent index `map.put(nums[i], i)`.
 *     When encountering `nums[i]` again:
 *     Check if `i - map.get(nums[i]) <= k`. If true, return true immediately!
 *     Always update `map.put(nums[i], i)` with the freshest index to maximize
 *     future proximity chances.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single linear pass.
 *   - Space: O(min(n, k)) - Map stores unique values.
 */
public class P51_ContainsDuplicateII {

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if (i - map.get(nums[i]) <= k) {
                    return true;
                }
            }
            map.put(nums[i], i);
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 1};
        int[] nums2 = {1, 0, 1, 1};
        int[] nums3 = {1, 2, 3, 1, 2, 3};

        System.out.println("P51 Output (Test 1): " + containsNearbyDuplicate(nums1, 3)); // Expected: true
        System.out.println("P51 Output (Test 2): " + containsNearbyDuplicate(nums2, 1)); // Expected: true
        System.out.println("P51 Output (Test 3): " + containsNearbyDuplicate(nums3, 2)); // Expected: false
    }
}