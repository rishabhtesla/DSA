package DSA.twopointers;

/**
 * ============================================================================
 * [30 / 34] - VALID PALINDROME (LeetCode 125)
 * ============================================================================
 * 
 * PROBLEM:
 *   A phrase is a palindrome if, after converting all uppercase letters into lowercase
 *   letters and removing all non-alphanumeric characters, it reads the same forward
 *   and backward. Alphanumeric characters include letters and numbers.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Naive: Strip non-alphanumeric characters and reverse string -> O(n) memory allocation.
 *   - In-place Two Pointers (Left & Right):
 *     1. Set `left = 0`, `right = s.length() - 1`.
 *     2. Advance `left` until pointing to an alphanumeric char: `Character.isLetterOrDigit()`.
 *     3. Decrement `right` until pointing to an alphanumeric char.
 *     4. Compare `Character.toLowerCase(s.charAt(left))` and `Character.toLowerCase(s.charAt(right))`.
 *        If unequal, return false immediately.
 *     5. Continue until `left >= right`.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single pass inwards.
 *   - Space: O(1) - Two pointer variables, no new strings created.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P30_ValidPalindrome {

    public static boolean isPalindrome(String s) {
        if (s == null) return false;

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            // Skip non-alphanumeric from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Case-insensitive character comparison
            char cl = Character.toLowerCase(s.charAt(left));
            char cr = Character.toLowerCase(s.charAt(right));

            if (cl != cr) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    public static void main(String[] args) {
        String s1 = "A man, a plan, a canal: Panama";
        String s2 = "race a car";
        System.out.println("P30 Output (Test 1): " + isPalindrome(s1)); // Expected: true
        System.out.println("P30 Output (Test 2): " + isPalindrome(s2)); // Expected: false
    }
}