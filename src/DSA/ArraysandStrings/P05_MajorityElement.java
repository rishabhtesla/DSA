package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [05 / 24] - MAJORITY ELEMENT (LeetCode 169)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array of size n, return the element that appears > floor(n / 2) times.
 *   The majority element is guaranteed to exist.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Hash Map approach: O(n) time, O(n) space.
 *   - Sorting: O(n log n) time, return nums[n / 2].
 *   - Boyer-Moore Voting Algorithm: O(n) time, O(1) space:
 *     Think of elements voting against one another. If an element appears more
 *     than half the time, even if every other element cancels out one vote of this
 *     candidate, the true majority element will still have a positive count at the end.
 *     1. If count drops to 0, pick the current element as new candidate.
 *     2. If num == candidate, increment count; otherwise decrement count.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - One pass.
 *   - Space: O(1) - Two primitive variables.
 *
 *
 * EXAMPLE:
 *   Input: nums = [2, 2, 1, 1, 1, 2, 2]
 *   Output: 2
 *
 * VISUAL DRY RUN:
 *   candidate = -, count = 0
 *   - num=2: count=0, choose candidate=2; same -> count=1
 *   - num=2: same as candidate; count=2
 *   - num=1: different; count=1
 *   - num=1: different; count=0
 *   - num=1: count=0, choose candidate=1; same -> count=1
 *   - num=2: different; count=0
 *   - num=2: count=0, choose candidate=2; same -> count=1
 *   Final candidate = 2. The majority guarantee makes this candidate correct.
 *
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P05_MajorityElement {

    public static int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("P05 Output: " + majorityElement(nums));
        // Expected: 2
    }
}