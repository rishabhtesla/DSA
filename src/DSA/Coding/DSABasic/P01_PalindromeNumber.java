package DSA.Coding.DSABasic;

/*
 * LEETCODE 9: PALINDROME NUMBER
 *
 * Question
 * Given an integer x, return true if x is a palindrome. Otherwise, return
 * false. A palindrome reads the same from left to right and right to left.
 *
 * Examples
 *   x = 121    -> true
 *   x = -121   -> false
 *   x = 10     -> false
 *
 * Constraints
 *   -2^31 <= x <= 2^31 - 1
 *
 * Approach used by edSlash
 * 1. A negative number cannot be a palindrome because its minus sign cannot
 *    appear at the end of the number.
 * 2. Store the original number in n.
 * 3. Repeatedly take the last digit with n % 10.
 * 4. Add that digit to the end of revNum.
 * 5. Remove the last digit from n with integer division by 10.
 * 6. Compare the reversed number with the original number.
 *
 * Dry run: x = 121
 *   Start: n = 121, revNum = 0
 *   Step 1: digit = 1, revNum = 1,   n = 12
 *   Step 2: digit = 2, revNum = 12,  n = 1
 *   Step 3: digit = 1, revNum = 121, n = 0
 *   revNum == x, so return true.
 *
 * Complexity
 *   Time: O(log10(x)), because each digit is processed once.
 *   Space: O(1).
 */
public class P01_PalindromeNumber {
    public static boolean isPalindrome(int x) {
    if (x < 0) {
        return false;
    }

    int n = x;
    int revNum = 0;

    while (n > 0) {
        int digit = n % 10;
        revNum = revNum * 10 + digit;
        n = n / 10;
    }

    if (revNum == x) {
        return true;
    } else {
        return false;
    }
    }

    public static void main(String[] args) {
    System.out.println(isPalindrome(121));
    }
}
