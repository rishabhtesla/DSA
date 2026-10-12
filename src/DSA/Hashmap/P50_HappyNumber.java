package DSA.Hashmap;

/**
 * ============================================================================
 * [50 / 52] - HAPPY NUMBER (LeetCode 202)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * A happy number is a number defined by the following process:
 * Starting with any positive integer, replace the number by the sum of the squares of its digits.
 * Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle
 * that does not include 1.
 * Return `true` if `n` is a happy number, and `false` if not.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / HashSet Approach:
 *    - Use a `HashSet<Integer>` to track numbers we've already seen to detect cycles.
 *    - Valid, but takes O(log n) extra space for storing visited elements in memory.
 *
 * 2. Optimal Cycle Detection via Floyd's Tortoise and Hare (O(log n) Time | O(1) Space):
 *    - **The Core Insight:** This sequence behaves mathematically like a linked list with a
 *      `next` pointer defined by `getNext(n)` (sum of squared digits).
 *    - Any non-happy number will eventually enter a fixed cycle (the well-known cycle includes 4).
 *    - Instead of a `HashSet`, we use two pointers: `slow` (advances by 1 step: `getNext(slow)`)
 *      and `fast` (advances by 2 steps: `getNext(getNext(fast))`).
 *    - **Termination:**
 *      - If `fast == 1`, we reached 1, so the number is happy!
 *      - If `slow == fast` (and not 1), they have met inside an infinite cycle, meaning it is not a happy number.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(log n) - The number of digits drops rapidly, and cycle detection resolves within a few steps.
 * - Space: O(1) - Uses only two integer pointers (`slow` and `fast`), avoiding extra heap memory for a set.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLES:
 * - 19 -> `true`  (19 -> 82 -> 68 -> 100 -> 1)
 * - 2  -> `false` (Enters an endless cycle)
 *
 * VISUAL DRY RUN (for `n = 19`):
 *   - `slow = 19`, `fast = getNext(19) = 82`
 *   - Loop iteration 1:
 *     - `slow` becomes `getNext(19) = 82`
 *     - `fast` becomes `getNext(getNext(82)) = getNext(68) = 100`
 *   - Loop iteration 2:
 *     - `slow` becomes `getNext(82) = 68`
 *     - `fast` becomes `getNext(getNext(100)) = getNext(1) = 1`
 *   - Loop condition check (`fast != 1 && slow != fast`) evaluates to false because `fast == 1`.
 *   - Returns `fast == 1` -> `true`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why is Floyd's Cycle-Finding Algorithm superior to a `HashSet` solution here?
 *    (It drops auxiliary space complexity from O(log n) down to O(1) constant space.)
 * 2. What guarantees that non-happy numbers will always result in a cycle rather than growing infinitely?
 *    (Mathematical bounding proves that numbers above a certain threshold will always have their sum of squared digits shrink.)
 */
public class P50_HappyNumber {

    private static int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    public static boolean isHappy(int n) {
        int slow = n;
        int fast = getNext(n);

        while (fast != 1 && slow != fast) {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        }

        return fast == 1;
    }

    public static void main(String[] args) {
        System.out.println("P50 Output (19): " + isHappy(19)); // Expected: true
        System.out.println("P50 Output (2):  " + isHappy(2));  // Expected: false
    }
}