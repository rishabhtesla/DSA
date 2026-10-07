package DSA.Coding.DSABasic;

/**
 * PROBLEM STATEMENT:
 * Given a string 's' consisting of words and spaces, return the length of the last word in the string [00:00:10, 00:00:20].
 * A word is a maximal substring consisting of non-space characters only [00:00:36].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "Hello World" [00:00:26]
 * - Process: The last word is "World", which contains 5 characters [00:00:30].
 * - Result: 5
 * 
 * Example 2: s = "   fly me   to   the moon  " (Simulated inside the video explanation [00:01:00, 00:04:40])
 * - Process:
 *   - Start reading from the rightmost character (`i = s.length() - 1`) [00:01:28, 00:02:57].
 *   - Skip trailing whitespace spaces without counting (`count` remains 0) [00:01:42, 00:05:00].
 *   - Upon hitting 'n', increment `count` to 1. Count 'o', 'o', 'm' to reach `count = 4` [00:01:53, 00:05:26].
 *   - Upon hitting the space preceding "moon", check if `count > 0` [00:02:22, 00:05:54].
 *   - Since `count` (4) > 0, break the loop and return 4 [00:02:15, 00:06:01].
 * - Result: 4 [00:00:40]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Reverse Traversal Word Bounding):
 * • Index Initialization: Scanning variable 'i' iterates backward starting at `s.length() - 1` down to 0 [00:02:57, 00:03:07].
 * • Condition Boundaries: If character `ch != ' '`, increment `count++` [00:01:53, 00:03:30]. 
 *   If `ch == ' '` and `count > 0`, break loop immediately [00:02:22, 00:03:49].
 * • Operational Steps:
 *   1. Initialize tracking counter `count = 0` [00:01:32, 00:02:45].
 *   2. Iterate string from end to start character by character [00:02:57].
 *   3. If non-space character is found, increment counter [00:03:30].
 *   4. If space is encountered after counting word characters, break early [00:03:49, 00:03:59].
 *   5. Return accumulated `count` [00:04:04].
 * • Time Complexity: O(n) worst-case, O(k) average-case (where k is length of last word + trailing spaces).
 * • Space Complexity: O(1) - Constant space modifying state pointers in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   Scanning from left to right requires traversing the entire string regardless of length [00:01:15]. 
 *   Traversing in reverse processes trailing spaces first and hits the last word directly, allowing 
 *   early loop termination as soon as the word's boundary space is reached [00:01:28].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Reverse Traversal - s = "   fly me   to   the moon  "):
 * Initial: count = 0, i = 28 (length - 1) [00:04:55]
 * i = 28 (' '): space && count==0 -> skip [00:05:00].
 * i = 27 (' '): space && count==0 -> skip [00:05:12].
 * i = 26 ('n'): non-space -> count = 1 [00:05:26].
 * i = 25 ('o'): non-space -> count = 2 [00:05:39].
 * i = 24 ('o'): non-space -> count = 3 [00:05:43].
 * i = 23 ('m'): non-space -> count = 4 [00:05:47].
 * i = 22 (' '): space && count (4) > 0 -> Break loop! [00:05:54].
 * Output returned = 4 [00:06:06].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Trim & Split - s = "   fly me   to   the moon  "):
 * Step 1: s.trim() removes leading/trailing spaces -> "fly me   to   the moon"
 * Step 2: s.lastIndexOf(' ') finds index of space preceding "moon" -> index 18
 * Step 3: Trimmed string length (23) - space index (18) - 1 = 4
 * Output returned = 4.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Pipeline - s = "   fly me   to   the moon  "):
 * Step 1: Split string by whitespace regex `\s+` -> ["fly", "me", "to", "the", "moon"]
 * Step 2: Convert to Stream and select last element -> "moon"
 * Step 3: Evaluate length of "moon" -> 4
 * Output returned = 4.
 */
public class LengthOfLastWord {

    // APPROACH 1: Reverse Traversal Word Bounding (Anchor Strategy)
    public static int lengthOfLastWordOptimal(String s) {
        if (s == null || s.length() == 0) return 0;

        int count = 0;
        
        // Reverse traversal from end to start [00:02:57]
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            
            if (ch != ' ') {
                count++; // Accumulate length of active word [00:03:30]
            } else if (count > 0) {
                break; // Space found after word started -> terminate early [00:03:49, 00:03:59]
            }
        }
        
        return count;
    }

    // APPROACH 2: In-Built Trim & Last Index Lookup
    // Trims leading/trailing whitespace natively, then calculates the difference between total length 
    // and the index of the final space character.
    public static int lengthOfLastWordBuiltIn(String s) {
        if (s == null) return 0;
        
        String trimmed = s.trim();
        int lastSpaceIndex = trimmed.lastIndexOf(' ');
        
        return trimmed.length() - lastSpaceIndex - 1;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Splitting with regex `\s+` creates multiple string objects in memory, 
    // increasing memory footprint to O(n) compared to O(1) in-place reverse traversal.
    public static int lengthOfLastWordStream(String s) {
        if (s == null || s.trim().isEmpty()) return 0;

        String[] words = s.trim().split("\\s+");
        return words[words.length - 1].length();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Base String) ---
        String test1 = "Hello World";
        int res1_1 = lengthOfLastWordOptimal(test1);
        int res1_2 = lengthOfLastWordBuiltIn(test1);
        int res1_3 = lengthOfLastWordStream(test1);

        System.out.println("Test Case 1: \"Hello World\"");
        System.out.println("Approach 1 (Reverse Traversal) Result: " + res1_1);
        System.out.println("Approach 2 (Trim & Index)     Result: " + res1_2);
        System.out.println("Approach 3 (Stream Pipeline)   Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 5 && res1_2 == 5 && res1_3 == 5 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Explanation - Trailing Spaces) ---
        String test2 = "   fly me   to   the moon  ";
        int res2_1 = lengthOfLastWordOptimal(test2);
        int res2_2 = lengthOfLastWordBuiltIn(test2);
        int res2_3 = lengthOfLastWordStream(test2);

        System.out.println("Test Case 2: \"   fly me   to   the moon  \"");
        System.out.println("Approach 1 (Reverse Traversal) Result: " + res2_1);
        System.out.println("Approach 2 (Trim & Index)     Result: " + res2_2);
        System.out.println("Approach 3 (Stream Pipeline)   Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 4 && res2_2 == 4 && res2_3 == 4 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Single Word Without Spaces) ---
        String test3 = "luffy";
        int res3_1 = lengthOfLastWordOptimal(test3);
        int res3_2 = lengthOfLastWordBuiltIn(test3);
        int res3_3 = lengthOfLastWordStream(test3);

        System.out.println("Test Case 3: \"luffy\"");
        System.out.println("Approach 1 (Reverse Traversal) Result: " + res3_1);
        System.out.println("Approach 2 (Trim & Index)     Result: " + res3_2);
        System.out.println("Approach 3 (Stream Pipeline)   Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 == 5 && res3_2 == 5 && res3_3 == 5 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}