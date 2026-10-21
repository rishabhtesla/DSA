package DSA.JPMC.path;

/**
 * PROBLEM: Longest Common Prefix
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 * 
 * EXAMPLE:
 * Input: strs = ["flower", "flow", "flight"]
 * Output: "fl"
 * 
 * LOGIC (Horizontal Scanning):
 * 1. Take the first string as the initial "prefix" (e.g., "flower").
 * 2. Compare it with the next string ("flow").
 * 3. Does "flow" start with "flower"? No. 
 *    Trim the prefix by 1 character from the end (becomes "flowe").
 *    Keep trimming until the string starts with the prefix (prefix becomes "flow").
 * 4. Move to the next string ("flight"). Keep trimming "flow" until it matches ("fl").
 * 
 * DRY RUN: ["flower", "flow", "flight"]
 * - prefix = "flower"
 * - Check "flow". Does "flow".startsWith("flower")? No.
 *   - Trim prefix -> "flowe" -> "flow". Now it matches!
 * - Check "flight". Does "flight".startsWith("flow")? No.
 *   - Trim prefix -> "flo" -> "fl". Now it matches!
 * - Loop ends. Result = "fl".
 * 
 * COMPLEXITY: Time: O(S) where S is the sum of all characters in all strings. Space: O(1).
 */
/**
 * Learning supplement: comparing every character of every pair is wasteful;
 * shrinking a candidate prefix across the strings is O(total input length)
 * time and O(1) extra space. Examples: {@code ["flower","flow","flight"] ->
 * "fl"} and {@code ["dog","racecar"] -> ""}. Invariant: prefix is common to
 * every processed string. Dry run shrinks "flower" to "flow", then "fl".
 * Ask what the empty array and an empty string should return.
 */
public class LongestCommonPrefix {
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        
        // Start by assuming the first word is the complete prefix
        String prefix = strs[0];
        
        for (int i = 1; i < strs.length; i++) {
            // While the current string DOES NOT start with the prefix
            // String.indexOf() returns 0 if it is a prefix
            while (strs[i].indexOf(prefix) != 0) {
                
                // Chop off the last character of the prefix
                prefix = prefix.substring(0, prefix.length() - 1);
                
                // If we chopped it down to nothing, there is no common prefix at all
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }

    public static void main(String[] args) {
        String[] strs = {"flower", "flow", "flight"};
        System.out.println("Longest Common Prefix: " + longestCommonPrefix(strs)); 
        // Output: "fl"
    }
}