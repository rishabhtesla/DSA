package DSA.JPMC; /**
 * PROBLEM: Longest Substring Without Repeating Characters
 * Given a string s, find the length of the longest substring without repeating characters.
 * 
 * EXAMPLE:
 * Input: s = "abcabcbb"
 * Output: 3 (The answer is "abc")
 * 
 * LOGIC:
 * Use a Sliding Window approach with a HashSet (or boolean array).
 * Maintain two pointers, 'left' and 'right', both starting at 0.
 * Expand the window by moving 'right' and adding characters to the Set.
 * If the character at 'right' is ALREADY in the Set, we have a duplicate.
 * We must shrink the window by removing the character at 'left', moving 'left' forward 
 * until the duplicate is removed from the Set.
 * Update the max length at each valid step.
 * 
 * DRY RUN: s = "abca"
 * - right = 0 ('a'): Add 'a' to set. Set = [a]. Max = 1.
 * - right = 1 ('b'): Add 'b' to set. Set = [a,b]. Max = 2.
 * - right = 2 ('c'): Add 'c' to set. Set = [a,b,c]. Max = 3.
 * - right = 3 ('a'): 'a' is already in set! 
 *     -> Remove s.charAt(left) ('a') from set, left++. Set = [b,c].
 *     -> Now add new 'a'. Set = [b,c,a]. Max = max(3, 3) = 3.
 * 
 * COMPLEXITY: Time: O(N) (Each char visited at most twice). Space: O(min(M, N)) for the Set.
 */
import java.util.HashSet;
import java.util.Set;

/**
 * Learning supplement: enumerating all substrings is O(n^2); the last-seen
 * index window is O(n) time and O(n) space. Examples:
 * {@code "abcabcbb" -> 3} and {@code "bbbbb" -> 1}. Invariant: the active
 * window has no duplicate character. For the main scan, a repeated character
 * moves left just beyond its previous index. Ask why left never moves back.
 */
public class LongestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        int left = 0;
        int right = 0;
        int maxLength = 0;
        Set<Character> window = new HashSet<>();
        
        while (right < s.length()) {
            char currentChar = s.charAt(right);
            
            // If duplicate found, shrink window from the left
            while (window.contains(currentChar)) {
                window.remove(s.charAt(left));
                left++;
            }
            
            // Add current character and calculate length
            window.add(currentChar);
            maxLength = Math.max(maxLength, right - left + 1);
            right++;
        }
        
        return maxLength;
    }

    public static void main(String[] args) {
        String s = "abcabcbb";
        System.out.println("Longest substring length: " + lengthOfLongestSubstring(s)); 
        // Output: 3
    }
}