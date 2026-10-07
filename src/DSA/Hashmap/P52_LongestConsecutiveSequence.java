package DSA.Hashmap;

import java.util.HashSet;
import java.util.Set;

/**
 * ============================================================================
 * [52 / 52] - LONGEST CONSECUTIVE SEQUENCE (LeetCode 128)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an unsorted array of integers nums, return the length of the longest
 *   consecutive elements sequence. You must write an algorithm that runs in O(n) time.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Sorting takes O(n log n), which violates the strict O(n) requirement.
 *   - Hash Set Sequence Root Check:
 *     1. Insert all elements into a `HashSet<Integer>`.
 *     2. Iterate through each number `num` in the set:
 *        Ask: "Can `num` be the START of a consecutive sequence?"
 *        It can ONLY be the start if `set.contains(num - 1)` is FALSE!
 *        If `num - 1` exists, skip it—it will be counted as part of a longer sequence
 *        originating earlier.
 *     3. When a start is found, count consecutive numbers `num + 1, num + 2, ...`
 *        while they exist in the set.
 *     Every element is visited at most twice (once in outer iteration, once in inner while).
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Linear lookups; each number enters the inner loop at most once.
 *   - Space: O(n) - Hash set storing unique values.
 */
public class P52_LongestConsecutiveSequence {

    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longestStreak = 0;

        for (int num : set) {
            // Only initiate a count if 'num' is the start of a sequence
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }

    public static void main(String[] args) {
        int[] nums1 = {100, 4, 200, 1, 3, 2};
        int[] nums2 = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};

        System.out.println("P52 Output (Test 1): " + longestConsecutive(nums1)); // Expected: 4 ([1, 2, 3, 4])
        System.out.println("P52 Output (Test 2): " + longestConsecutive(nums2)); // Expected: 9 ([0, 1, 2, 3, 4, 5, 6, 7, 8])
    }
}