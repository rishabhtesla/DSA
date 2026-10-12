package DSA.twopointers;

/**
 * ============================================================================
 * [30 / 34] - VALID PALINDROME (LeetCode 125)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase
 * letters and removing all non-alphanumeric characters, it reads the same forward
 * and backward. Alphanumeric characters include letters and numbers.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / String Filtering Approach:
 *    - Strip all non-alphanumeric characters into a new string, convert to lowercase, and check if it equals its reverse.
 *    - Valid, but allocates $O(n)$ extra memory for intermediate string copies and reversed arrays.
 *
 * 2. Optimal In-Place Two Pointers Approach (O(n) Time | O(1) Space):
 *    - **The Core Insight:** We can evaluate palindromes directly on the original string without extra memory
 *      by using inward-moving `left` and `right` pointers that bypass non-alphanumeric characters on the fly.
 *    - **Step 1:** Initialize `left = 0` and `right = s.length() - 1`.
 *    - **Step 2:** Advance `left` forward until it points to a valid letter or digit (`Character.isLetterOrDigit()`).
 *    - **Step 3:** Decrement `right` backward until it points to a valid letter or digit.
 *    - **Step 4:** Compare both characters case-insensitively. If they mismatch, return `false` immediately.
 *    - **Step 5:** Continue until the pointers cross (`left >= right`).
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(n) - Single pass moving inward from both ends of the string.
 * - Space: O(1) - Constant auxiliary pointer space with zero string or array allocations.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - String: `"A man, a plan, a canal: Panama"`
 * - Expected Result: `true`
 *
 * VISUAL DRY RUN (for `"race a car"`):
 *   - `left = 0` ('r'), `right = 9` ('r') -> match.
 *   - `left = 1` ('a'), `right = 8` ('a') -> match.
 *   - `left = 2` ('c'), `right = 7` ('r') -> mismatch ('c' != 'r'). Returns `false`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why must the inner `while` loops include the `left < right` boundary check?
 *    (To prevent the pointers from crossing or going out of bounds when encountering sequences
 *    composed entirely of non-alphanumeric characters or punctuation marks.)
 * 2. Why is this in-place two-pointer approach preferred in production systems?
 *    (It eliminates garbage collection overhead and memory allocation spikes for very large input strings.)
 */
public class P30_ValidPalindrome {

    public static boolean isPalindrome(String s) {
        if (s == null) return false;

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric characters from the left boundary
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            // Skip non-alphanumeric characters from the right boundary
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Perform case-insensitive character comparison
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