package DSA.Coding.DSABasic;

import java.util.stream.Collectors;

/**
 * PROBLEM STATEMENT:
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and 
 * removing all non-alphanumeric characters, it reads the same forward and backward [00:00:52, 00:01:14]. 
 * Alphanumeric characters include letters and numbers [00:00:57].
 * Given a string 's', return true if it is a palindrome, or false otherwise [00:01:19].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "A man, a plan, a canal: Panama" [00:01:49]
 * - Process:
 *   - Remove non-alphanumeric characters (commas, spaces, colons) and lowercase all characters [00:02:14].
 *   - Filtered string: "amanaplanacanalpanama" [00:02:23].
 *   - Check forward and backward: reads identical in both directions [00:02:30].
 * - Result: true
 * 
 * Example 2: s = "race a car" [00:01:25]
 * - Process:
 *   - Filtered string: "raceacar" [00:01:30].
 *   - Compare ends: 'r'=='r', 'a'=='a', 'c'=='c', 'e'!='a' -> mismatch at center [00:01:42].
 * - Result: false
 * 
 * Example 3: s = " "
 * - Process: Empty or whitespace-only string becomes "" after filtering. An empty string reads same forward/backward [00:02:41].
 * - Result: true
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Filtered StringBuilder & Two-Pointer Verification):
 * • Index Initialization:
 *   - Pre-pass loop 'i' iterates from 0 to s.length() - 1 [00:04:46].
 *   - Two-pointer verification setup: `start = 0`, `end = sb.length() - 1` [00:11:02].
 * • Condition Boundaries:
 *   - Lowercase letter ('a'-'z'): append directly [00:05:06, 00:05:24].
 *   - Uppercase letter ('A'-'Z'): convert using `(char)(ch - 'A' + 'a')` and append [00:06:35, 00:07:43].
 *   - Numeric digit ('0'-'9'): append directly [00:09:22, 00:09:34].
 *   - Pointer check loop: `while (start < end)` [00:11:29].
 * • Operational Steps:
 *   1. Handle base edge case: if `s.length() <= 1`, return true immediately [00:03:00, 00:03:12].
 *   2. Iterate string and build lowercased alphanumeric `StringBuilder` [00:04:16, 00:04:46].
 *   3. Perform two-pointer comparison from both ends toward center [00:10:10, 00:11:29].
 *   4. If characters mismatch at `start` and `end`, return false [00:12:12].
 *   5. Advance `start++` and `end--` until pointers cross [00:12:59].
 *   6. Return true if all paired checks pass [00:12:23].
 * • Time Complexity: O(n) - Linear scan to filter string + linear two-pointer check pass [00:04:46].
 * • Space Complexity: O(n) auxiliary space - Space used to construct filtered StringBuilder buffer [00:04:16].
 * • LOGIC BEHIND THIS APPROACH:
 *   Filtering first isolates pure alphanumeric components, removing formatting noise [00:01:08, 00:03:42]. 
 *   Two-pointer validation directly tests palindromic symmetry by checking matching characters from opposing ends [00:10:10].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: StringBuilder & Two-Pointer - s = "R1a,2D:r"):
 * Filtering Pass:
 * i=0 ('R'): Uppercase -> 'R'-'A'+'a' = 'r'. sb = "r" [00:15:28]
 * i=1 ('1'): Digit -> sb = "r1" [00:15:38]
 * i=2 ('a'): Lowercase -> sb = "r1a" [00:15:43]
 * i=3 (','): Symbol -> skip [00:16:12]
 * i=4 ('2'): Digit -> sb = "r1a2" [00:15:49]
 * i=5 ('D'): Uppercase -> 'd'. sb = "r1a2d" [00:16:03]
 * i=6 (':'): Symbol -> skip [00:16:08]
 * i=7 ('r'): Lowercase -> sb = "r1a2dr" [00:16:17]
 * 
 * Two-Pointer Pass (sb = "r1a2dr", start=0, end=5):
 * Step 1: sb[0] ('r') == sb[5] ('r') -> start++ (1), end-- (4) [00:16:36]
 * Step 2: sb[1] ('1') != sb[4] ('d') -> Mismatch! Return false [00:16:50].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Strict In-Place Two-Pointer - s = "A man, a plan..."):
 * Pointers: left = 0 ('A'), right = 29 ('a')
 * Step 1: Character.isLetterOrDigit('A') & ('a') -> compare lowercase('A')=='a' -> match. left++ (1), right-- (28)
 * Step 2: s[left]=' ' (non-alphanumeric) -> left++ until valid char ('m')
 * Step 3: s[right]='m' -> compare 'm'=='m' -> match.
 * Continues without extra memory allocations until left >= right.
 * Output = true.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Pipeline - s = "race a car"):
 * Step 1: Filter alphanumeric chars -> Stream['r','a','c','e','a','c','a','r']
 * Step 2: Convert to String -> "raceacar"
 * Step 3: Compare with reverse("raceacar") -> "racaecar" != "raceacar"
 * Output = false.
 */
public class ValidPalindrome {

    // APPROACH 1: Filtered StringBuilder & Two-Pointer Verification (Anchor Strategy)
    public static boolean isPalindromeOptimal(String s) {
        if (s == null) return false;
        
        // Single-character or empty strings are valid palindromes [00:03:00]
        if (s.length() <= 1) {
            return true;
        }

        StringBuilder sb = new StringBuilder();

        // Process characters and convert to lowercased alphanumeric buffer [00:04:46]
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Lowercase character check [00:05:06]
            if (ch >= 'a' && ch <= 'z') {
                sb.append(ch);
            } 
            // Uppercase character conversion check [00:06:35]
            else if (ch >= 'A' && ch <= 'Z') {
                char lowerCh = (char) (ch - 'A' + 'a'); // Manual ASCII shift to lowercase [00:07:43]
                sb.append(lowerCh);
            } 
            // Numeric digit check [00:09:22]
            else if (ch >= '0' && ch <= '9') {
                sb.append(ch);
            }
            // All non-alphanumeric symbols/spaces are skipped naturally [00:09:41]
        }

        // Two-pointer symmetry validation pass [00:11:02]
        int start = 0;
        int end = sb.length() - 1;

        while (start < end) {
            char startChar = sb.charAt(start);
            char endChar = sb.charAt(end);

            if (startChar != endChar) {
                return false; // Character mismatch breaking symmetry [00:12:12]
            }

            start++; // Move left pointer forward [00:12:59]
            end--;   // Move right pointer backward [00:12:59]
        }

        return true; // Symmetric balance verified [00:12:23]
    }

    // APPROACH 2: Strict O(1) Space In-Place Two-Pointer Strategy
    // Avoids creating an intermediate StringBuilder buffer by filtering and skipping 
    // invalid characters directly during the two-pointer traversal.
    public static boolean isPalindromeInPlace(String s) {
        if (s == null) return false;

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            // Skip non-alphanumeric characters from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare case-insensitively
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Filtering characters into a stream and comparing against its reverse 
    // creates multiple String objects, adding GC pressure compared to direct pointer manipulation.
    public static boolean isPalindromeStream(String s) {
        if (s == null) return false;

        String filtered = s.chars()
                .filter(Character::isLetterOrDigit)
                .mapToObj(ch -> String.valueOf((char) Character.toLowerCase(ch)))
                .collect(Collectors.joining());

        String reversed = new StringBuilder(filtered).reverse().toString();

        return filtered.equals(reversed);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Valid Palindrome Phrase) ---
        String test1 = "A man, a plan, a canal: Panama";
        boolean res1_1 = isPalindromeOptimal(test1);
        boolean res1_2 = isPalindromeInPlace(test1);
        boolean res1_3 = isPalindromeStream(test1);

        System.out.println("Test Case 1: \"A man, a plan, a canal: Panama\"");
        System.out.println("Approach 1 (StringBuilder) Result: " + res1_1);
        System.out.println("Approach 2 (In-Place)      Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 && res1_2 && res1_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Invalid Palindrome) ---
        String test2 = "race a car";
        boolean res2_1 = isPalindromeOptimal(test2);
        boolean res2_2 = isPalindromeInPlace(test2);
        boolean res2_3 = isPalindromeStream(test2);

        System.out.println("Test Case 2: \"race a car\"");
        System.out.println("Approach 1 (StringBuilder) Result: " + res2_1);
        System.out.println("Approach 2 (In-Place)      Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res2_3);
        System.out.println("Verification: " + (!res2_1 && !res2_2 && !res2_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (From Video Board Explanation - Numbers & Mismatch) ---
        String test3 = "R1a,2D:r";
        boolean res3_1 = isPalindromeOptimal(test3);
        boolean res3_2 = isPalindromeInPlace(test3);
        boolean res3_3 = isPalindromeStream(test3);

        System.out.println("Test Case 3: \"R1a,2D:r\"");
        System.out.println("Approach 1 (StringBuilder) Result: " + res3_1);
        System.out.println("Approach 2 (In-Place)      Result: " + res3_2);
        System.out.println("Approach 3 (Stream API)    Result: " + res3_3);
        System.out.println("Verification: " + (!res3_1 && !res3_2 && !res3_3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}