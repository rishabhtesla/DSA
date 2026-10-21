package DSA.JPMC.path;

/**
 * PROBLEM: Check if a string is a palindrome.
 * 
 * LOGIC (Two Pointers):
 * Place one pointer at the start and one at the end of the string.
 * Compare characters at these pointers. If they match, move inwards.
 * If a mismatch occurs, it's not a palindrome. 
 * (Optional: ignore spaces/cases depending on exact constraints).
 * 
 * COMPLEXITY: Time: O(N), Space: O(1)
 */
/**
 * Learning supplement: normalize-and-reverse uses O(n) extra space; two
 * pointers compare from both ends in O(n) time and O(1) extra space.
 * Examples: {@code "A man, a plan, a canal: Panama" -> true} and
 * {@code "race a car" -> false}. Invariant: all compared normalized pairs
 * match. The main "Racecar" pointers meet after matching r/R, a/c, and e.
 * Ask whether punctuation and case should be ignored.
 */
public class ValidPalindrome {
    public static boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) return true;
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            // Compare characters ignoring case
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false; // Mismatch found
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Is 'Racecar' palindrome? " + isPalindrome("Racecar")); // true
        System.out.println("Is 'JPMorgan' palindrome? " + isPalindrome("JPMorgan")); // false
    }
}