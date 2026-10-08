package DSA.Hashmap;

/**
 * ============================================================================
 * [50 / 52] - HAPPY NUMBER (LeetCode 202)
 * ============================================================================
 * 
 * PROBLEM:
 *   A happy number is a number defined by the process:
 *   - Starting with any positive integer, replace the number by the sum of the
 *     squares of its digits.
 *   - Repeat the process until the number equals 1, or it loops endlessly in a cycle.
 *   Return true if n is a happy number, and false if not.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Detect Cycles without Extra Memory (Floyd's Tortoise and Hare):
 *     This sequence behaves like a linked list with next pointer = `sumOfSquares(n)`.
 *     Any non-happy number enters a fixed cycle (the well-known cycle includes 4).
 *   - Use `slow = getNext(n)` and `fast = getNext(getNext(n))`.
 *     When `slow == fast`:
 *     - If `slow == 1`, the number is happy!
 *     - Otherwise, it entered an infinite cycle -> return false.
 *
 * COMPLEXITY:
 *   - Time:  O(log n) - Number of digits drops rapidly; cycle detection takes few steps.
 *   - Space: O(1) - Pointers only; avoids `HashSet<Integer>`.
 *
 *
 * EXAMPLE:
 *   The first main number is 19; expected result is true.
 *
 * VISUAL DRY RUN:
 *   Replace n by squared digits: 19 -> 1^2+9^2=82 -> 68 -> 100 -> 1. The seen set
 *   never repeats before 1, so return true (the second main case 2 eventually cycles).
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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