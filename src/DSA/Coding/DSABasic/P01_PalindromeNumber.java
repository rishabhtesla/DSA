package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Determine whether an integer reads identically forwards and backwards.
 * EXAMPLE: 121 -> true; -121 -> false because the minus sign is not mirrored.
 * CONSTRAINTS: -2^31 <= x <= 2^31-1; negative numbers are not palindromes.
 * LOGIC / ALGORITHM:
 * 1. Reject negatives, then repeatedly take the last digit to build the reversed number and compare it with x.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 121 -> true; -121 -> false because the minus sign is not mirrored.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P01_PalindromeNumber {
    public static boolean isPalindrome(int x) {
        if (x < 0) return false;
        int y = x, r = 0;
        while (y > 0) {
            r = r * 10 + y % 10;
            y /= 10;
        }
        return x == r;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(121));
    }
}
