package DSA.Hashmap;

import java.util.HashSet;
import java.util.Set;

/**
 * ============================================================================
 * [52 / 52] - LONGEST CONSECUTIVE SEQUENCE (LeetCode 128)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an unsorted array of integers `nums`, return the length of the longest
 * consecutive elements sequence. You must write an algorithm that runs in `O(n)` time.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Sorting Approach (O(n log n) Time | O(1) or O(n) Space):
 *    - Sort the array and scan for consecutive runs.
 *    - Fails the strict interview requirement for an `O(n)` time complexity due to the sorting step.
 *
 * 2. Optimal Hash Set Sequence Root Check (O(n) Time | O(n) Space):
 *    - **The Core Insight:** To achieve `O(n)`, we cannot afford to search for sequence continuations blindly.
 *      Instead, we must only initiate a count when we are looking at the **start** of a sequence.
 *    - **Step 1:** Insert all elements into a `HashSet<Integer>` for `O(1)` average-time lookups.
 *    - **Step 2:** Iterate through each unique number `num` in the set. Ask: *"Can `num` be the start of a sequence?"*
 *      It can **only** be the start if `!set.contains(num - 1)` is true. If `num - 1` exists, skip it—it will
 *      be counted as part of a longer sequence originating earlier.
 *    - **Step 3:** When a true start is found, count consecutive numbers (`num + 1, num + 2, ...`)
 *      while they exist in the set, and track the maximum length (`longestStreak`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Although there is a `while` loop inside a `for` loop, each number is visited and processed
 *           in the inner loop at most once, leading to a linear overall time complexity.
 * - Space: O(n) - Hash set storing unique integer values from the array.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - `nums = [100, 4, 200, 1, 3, 2]` -> 4 (`[1, 2, 3, 4]`)
 * - `nums = [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]` -> 9 (`[0, 1, 2, 3, 4, 5, 6, 7, 8]`)
 *
 * VISUAL DRY RUN (for `[100, 4, 200, 1, 3, 2]`):
 *   - Set contains: `{100, 4, 200, 1, 3, 2}`
 *   - Check 100: `99` is absent -> Start found! Extends to `100` (streak = 1). `longestStreak = 1`.
 *   - Check 4: `3` exists in set -> Skip (not a sequence root).
 *   - Check 1: `0` is absent -> Start found! Extends: 1 -> 2 -> 3 -> 4 (streak = 4). `longestStreak = 4`.
 *   - Returns `4`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does checking `!set.contains(num - 1)` guarantee an overall `O(n)` time complexity?
 *    (It ensures that the inner `while` loop only runs when we are at the absolute beginning of a sequence.
 *    Numbers in the middle of a sequence are skipped instantly, preventing redundant nested iterations.)
 * 2. What edge cases are handled automatically?
 *    (Null or empty arrays return `0` instantly, and arrays with duplicate values or single elements evaluate cleanly.)
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