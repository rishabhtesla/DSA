package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PROBLEM STATEMENT:
 * Given an array of characters 'chars', compress it using the following algorithm:
 * Begin with an empty string s. For each group of consecutive repeating characters in chars:
 * - If the group's length is 1, append the character to s [00:01:45].
 * - Otherwise, append the character followed by the group's length [00:00:44].
 * The compressed string s should not be returned separately, but instead, be stored in the input character array chars [00:01:00]. 
 * Note that group lengths that are 10 or longer will be split into multiple characters in chars [00:02:14].
 * You must write an algorithm that uses only constant extra space [00:02:26].
 * Return the new length of the array [00:01:19].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: chars = ["a","a","b","b","c","c","c"] [00:00:30]
 * - Compressed groups: "a" appears 2 times -> "a2", "b" appears 2 times -> "b2", "c" appears 3 times -> "c3" [00:00:44].
 * - In-place update: chars becomes ["a","2","b","2","c","3", ...] [00:01:00].
 * - Return length: 6 [00:01:19].
 * 
 * Example 2: chars = ["a","b","b","b","c","d","d"] (Simulated inside the video explanation [00:11:13])
 * - Process:
 *   - 'a' count = 1 -> Append "a" (no number appended for count 1) [00:01:45, 00:13:29].
 *   - 'b' count = 3 -> Append "b3" [00:01:58, 00:13:30].
 *   - 'c' count = 1 -> Append "c" [00:13:31].
 *   - 'd' count = 2 -> Append "d2" [00:13:32].
 *   - Overwrite original array with "ab3cd2" and return new length = 6 [00:14:15, 00:14:22].
 * - Result: 6
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - StringBuilder Grouping & In-Place Array Overwrite):
 * • Index Initialization: Pointer 'i' starts at index 1 and scans up to chars.length - 1 [00:07:57].
 * • Condition Boundaries:
 *   - If `chars[i] != chars[i - 1]`, a character transition occurs [00:08:08].
 *   - If `count > 1`, append count to string buffer before resetting `count = 1` [00:08:29, 00:09:00].
 * • Operational Steps:
 *   1. Initialize `count = 1` and a `StringBuilder` buffer populated with `chars[0]` [00:07:15, 00:07:39].
 *   2. Iterate from index 1 to the end of array [00:07:57].
 *   3. If character matches previous, increment `count++` [00:08:23].
 *   4. If character changes, append current `count` (if > 1), append new character, reset `count = 1` [00:08:34, 00:09:00].
 *   5. After loop ends, handle remaining count for trailing group [00:09:12].
 *   6. Write back buffered characters to original `chars` array and return buffer length [00:09:30, 00:10:08].
 * • Time Complexity: O(n) - Single pass to build compressed representation, O(k) write-back pass.
 * • Space Complexity: O(n) auxiliary space for StringBuilder buffer [00:07:22].
 * • LOGIC BEHIND THIS APPROACH:
 *   To group consecutive matching characters, we compare adjacent elements [00:03:43]. 
 *   Tracking run-length count allows appending exact repetition metrics [00:04:23]. 
 *   Using a intermediate string buffer ensures clean handling of multi-digit count representations (e.g. 12 -> '1', '2') 
 *   before writing results back to the source array [00:02:14, 00:08:43].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: StringBuilder Grouping - chars = ['a','a','b','b','b','c','d','d']):
 * Initial: SB = "a", count = 1 [00:11:25]
 * i=1 ('a'): chars[1] == chars[0] ('a'=='a') -> count = 2 [00:11:48]
 * i=2 ('b'): chars[2] != chars[1] ('b'!='a') -> count (2) > 1 -> SB.append(2) -> "a2" [00:12:04]
 *            SB.append('b') -> "a2b", count reset to 1 [00:12:09]
 * i=3 ('b'): chars[3] == chars[2] ('b'=='b') -> count = 2 [00:12:21]
 * i=4 ('b'): chars[4] == chars[3] ('b'=='b') -> count = 3 [00:12:29]
 * i=5 ('c'): chars[5] != chars[4] ('c'!='b') -> count (3) > 1 -> SB.append(3) -> "a2b3" [00:12:35]
 *            SB.append('c') -> "a2b3c", count reset to 1 [00:12:40]
 * i=6 ('d'): chars[6] != chars[5] ('d'!='c') -> count (1) !> 1 -> skip count [00:12:54]
 *            SB.append('d') -> "a2b3cd", count reset to 1 [00:13:03]
 * i=7 ('d'): chars[7] == chars[6] ('d'=='d') -> count = 2 [00:13:15]
 * Post-Loop: count (2) > 1 -> SB.append(2) -> "a2b3cd2" [00:13:24]
 * Overwrite chars array with "a2b3cd2" -> ['a','2','b','3','c','d','2',...] [00:14:15]
 * Return SB.length() = 7 [00:14:22].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Two-Pointer Strict In-Place - chars = ['a','a','b','b','c','c','c']):
 * Pointers: write = 0, read = 0
 * Run 1 ('a'): anchor = 0. Advance read until chars[read] != 'a' -> read = 2.
 *              chars[write++] = 'a' (write = 1).
 *              len = 2 - 0 = 2. Append '2' -> chars[write++] = '2' (write = 2).
 * Run 2 ('b'): anchor = 2. Advance read until chars[read] != 'b' -> read = 4.
 *              chars[write++] = 'b' (write = 3).
 *              len = 4 - 2 = 2. Append '2' -> chars[write++] = '2' (write = 4).
 * Run 3 ('c'): anchor = 4. Advance read until chars[read] != 'c' -> read = 7.
 *              chars[write++] = 'c' (write = 5).
 *              len = 7 - 4 = 3. Append '3' -> chars[write++] = '3' (write = 6).
 * Output = write pointer = 6.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Grouping Pipeline - chars = ['a','a','b','b','c','c','c']):
 * Step 1: IntStream.range over indices to group matching contiguous blocks.
 * Step 2: Form runs: [("a", 2), ("b", 2), ("c", 3)]
 * Step 3: Map to compressed string: "a2b3c3"
 * Step 4: Write to array and return length = 6.
 */
public class StringCompression {

    // APPROACH 1: StringBuilder Grouping & In-Place Array Overwrite (Anchor Strategy)
    public static int compressOptimal(char[] chars) {
        if (chars == null || chars.length == 0) return 0;

        int count = 1; // Group frequency tracker [00:07:15]
        StringBuilder sb = new StringBuilder();
        sb.append(chars[0]); // Append initial character [00:07:39]

        // Single pass scanning for adjacent character transitions [00:07:57]
        for (int i = 1; i < chars.length; i++) {
            if (chars[i - 1] != chars[i]) {
                if (count > 1) {
                    sb.append(count); // Append occurrence count if > 1 [00:08:34]
                }
                sb.append(chars[i]); // Append new character group leader [00:08:55]
                count = 1; // Reset run counter [00:09:00]
            } else {
                count++; // Increment matching character group count [00:08:23]
            }
        }

        // Process trailing group count after loop completes [00:09:12]
        if (count > 1) {
            sb.append(count);
        }

        // Write compressed buffer back to original array [00:09:30, 00:09:55]
        for (int i = 0; i < sb.length(); i++) {
            chars[i] = sb.charAt(i);
        }

        return sb.length(); // Return new compressed array length [00:10:08]
    }

    // APPROACH 2: Strict O(1) Memory Two-Pointer In-Place Strategy
    // Uses read and write pointers to achieve true O(1) auxiliary space without intermediate String representations.
    public static int compressTwoPointer(char[] chars) {
        if (chars == null || chars.length == 0) return 0;

        int write = 0; // Pointer for writing compressed output in-place
        int read = 0;  // Pointer for reading input groups

        while (read < chars.length) {
            char currentChar = chars[read];
            int groupStart = read;

            // Find end of current character group
            while (read < chars.length && chars[read] == currentChar) {
                read++;
            }

            // Write character to array
            chars[write++] = currentChar;

            int groupLength = read - groupStart;
            // Write count digits if count > 1
            if (groupLength > 1) {
                for (char digit : String.valueOf(groupLength).toCharArray()) {
                    chars[write++] = digit;
                }
            }
        }

        return write;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Simulating contiguous character group reduction using functional streams 
    // involves heavy object creation, but offers a declarative processing model.
    public static int compressStream(char[] chars) {
        if (chars == null || chars.length == 0) return 0;

        List<String> runs = new ArrayList<>();
        int i = 0;

        while (i < chars.length) {
            final char current = chars[i];
            int start = i;
            while (i < chars.length && chars[i] == current) {
                i++;
            }
            int count = i - start;
            runs.add(String.valueOf(current) + (count > 1 ? count : ""));
        }

        String compressed = runs.stream().collect(Collectors.joining());

        for (int j = 0; j < compressed.length(); j++) {
            chars[j] = compressed.charAt(j);
        }

        return compressed.length();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Grouping) ---
        char[] test1_1 = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        char[] test1_2 = test1_1.clone();
        char[] test1_3 = test1_1.clone();

        int res1_1 = compressOptimal(test1_1);
        int res1_2 = compressTwoPointer(test1_2);
        int res1_3 = compressStream(test1_3);

        System.out.println("Test Case 1: ['a','a','b','b','c','c','c']");
        System.out.println("Approach 1 (StringBuilder) Result length: " + res1_1);
        System.out.println("Approach 2 (Two Pointer)   Result length: " + res1_2);
        System.out.println("Approach 3 (Stream API)    Result length: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 6 && res1_2 == 6 && res1_3 == 6 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        char[] test2_1 = {'a', 'a', 'b', 'b', 'b', 'c', 'd', 'd'};
        char[] test2_2 = test2_1.clone();
        char[] test2_3 = test2_1.clone();

        int res2_1 = compressOptimal(test2_1);
        int res2_2 = compressTwoPointer(test2_2);
        int res2_3 = compressStream(test2_3);

        System.out.println("Test Case 2: ['a','a','b','b','b','c','d','d']");
        System.out.println("Approach 1 (StringBuilder) Result length: " + res2_1);
        System.out.println("Approach 2 (Two Pointer)   Result length: " + res2_2);
        System.out.println("Approach 3 (Stream API)    Result length: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 7 && res2_2 == 7 && res2_3 == 7 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Single Character Groups) ---
        char[] test3_1 = {'a'};
        char[] test3_2 = test3_1.clone();
        char[] test3_3 = test3_1.clone();

        int res3_1 = compressOptimal(test3_1);
        int res3_2 = compressTwoPointer(test3_2);
        int res3_3 = compressStream(test3_3);

        System.out.println("Test Case 3: ['a']");
        System.out.println("Approach 1 (StringBuilder) Result length: " + res3_1);
        System.out.println("Approach 2 (Two Pointer)   Result length: " + res3_2);
        System.out.println("Approach 3 (Stream API)    Result length: " + res3_3);
        System.out.println("Verification: " + (res3_1 == 1 && res3_2 == 1 && res3_3 == 1 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}