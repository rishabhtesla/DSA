package DSA.Coding.DSABasic;

import java.util.Arrays;

/**
 * PROBLEM STATEMENT:
 * The power of the string is the maximum length of a non-empty substring that contains 
 * only one unique character [00:00:21].
 * Given a string 's', return the power of 's' [00:00:27].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: s = "leetcode" [00:00:30]
 * - Process: Substring "ee" contains only 'e' and has length 2. Other single-character substrings have length 1 [00:00:45].
 * - Result: 2
 * 
 * Example 2: s = "abbcccddddeeeeedcba" [00:00:55]
 * - Process: Substring "eeeee" contains only 'e' and has maximum consecutive length 5 [00:01:05].
 * - Result: 5
 * 
 * Example 3: s = "aabbcccdd" (Simulated inside the video explanation [00:01:28, 00:07:47])
 * - Process:
 *   - Initialize max = 1, count = 1 [00:01:40, 00:05:47].
 *   - Compare adjacent characters starting at index 1 (`current = s.charAt(i)`, `previous = s.charAt(i-1)`) [00:02:25, 00:06:08].
 *   - i=1 ('a' == 'a'): count++ -> count = 2 [00:02:48, 00:06:35].
 *   - i=2 ('b' != 'a'): update max = Math.max(1, 2) = 2, reset count = 1 [00:03:54, 00:06:54].
 *   - i=3 ('b' == 'b'): count++ -> count = 2 [00:04:20].
 *   - i=4 ('c' != 'b'): update max = Math.max(2, 2) = 2, reset count = 1 [00:04:35].
 *   - i=5 ('c' == 'c'): count++ -> count = 2 [00:05:11].
 *   - i=6 ('c' == 'c'): count++ -> count = 3 [00:05:22].
 *   - Loop ends. Final edge sync: max = Math.max(2, 3) = 3 [00:05:34, 00:07:06].
 * - Result: 3 [00:01:10, 00:09:34]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Adjacent Character Single-Pass Tracking):
 * • Index Initialization: Pointer 'i' starts at index 1 and increments to s.length() - 1 [00:02:25, 00:05:55].
 * • Condition Boundaries:
 *   - If `current == previous` (`s.charAt(i) == s.charAt(i-1)`), increment `count++` [00:02:48, 00:06:35].
 *   - Else, update `max = Math.max(max, count)` and reset `count = 1` [00:02:54, 00:06:54].
 * • Operational Steps:
 *   1. Initialize `max = 1` and `count = 1` (since minimum length of non-empty string is 1) [00:01:40, 00:05:47].
 *   2. Traverse string from index 1 to `s.length() - 1` [00:05:55].
 *   3. Extract `current = s.charAt(i)` and `previous = s.charAt(i - 1)` [00:06:08, 00:06:17].
 *   4. Increment `count` if characters match; else update `max` and reset `count = 1` [00:06:35, 00:06:54].
 *   5. Perform final update `max = Math.max(max, count)` after loop ends to catch trailing streak [00:05:34, 00:07:06].
 *   6. Return `max` [00:07:15].
 * • Time Complexity: O(n) - Single pass through the string [00:05:55].
 * • Space Complexity: O(1) - Uses primitive counters without dynamic heap allocations [00:01:40].
 * • LOGIC BEHIND THIS APPROACH:
 *   The problem asks for the longest contiguous block of a single repeated character [00:00:21]. 
 *   Checking adjacent character pairs `(i - 1, i)` identifies when a run continues versus when it ends [00:02:29]. 
 *   Updating the running maximum on transition and at the end of string guarantees optimal single-pass resolution [00:03:54, 00:05:34].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Adjacent Character - s = "aabbccc"):
 * Initial: max = 1, count = 1 [00:08:08]
 * i=1 ('a' == 'a'): count = 2 [00:08:14]
 * i=2 ('b' != 'a'): max = Math.max(1, 2) = 2, count reset to 1 [00:08:45]
 * i=3 ('b' == 'b'): count = 2 [00:09:01]
 * i=4 ('b' == 'b'): count = 3 [00:09:25]
 * Loop End.
 * Final Sync: max = Math.max(2, 3) = 3 [00:09:29]
 * Output returned = 3 [00:09:34].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Dynamic Two-Pointer Anchor Window - s = "leetcode"):
 * Pointers: left = 0, right = 0, maxLen = 1
 * right=0 ('l'): left=0, len = 0 - 0 + 1 = 1, maxLen = 1
 * right=1 ('e'): left=1 (reset left because 'e' != 'l'), len = 1, maxLen = 1
 * right=2 ('e'): left=1 ('e' == 'e'), len = 2 - 1 + 1 = 2, maxLen = 2
 * right=3 ('t'): left=3 ('t' != 'e'), len = 1, maxLen = 2
 * ...
 * Output = 2.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Substring Split Pipeline - s = "abbccc"):
 * Stream Stage 1: Group adjacent equal characters using regex boundary split `(?<=(.))(?!\1)`.
 * Stream Stage 2: Tokens -> ["a", "bb", "ccc"]
 * Stream Stage 3: Map to lengths -> [1, 2, 3]
 * Stream Stage 4: Find max -> 3.
 * Output = 3.
 */
public class ConsecutiveCharacters {

    // APPROACH 1: Adjacent Character Single-Pass Tracking (Anchor Strategy)
    public static int maxPowerOptimal(String s) {
        if (s == null || s.length() == 0) return 0;

        int max = 1;   // Maximum run length recorded [00:05:47]
        int count = 1; // Current run length count [00:05:47]

        // Compare each character with the preceding character [00:05:55]
        for (int i = 1; i < s.length(); i++) {
            char current = s.charAt(i);     // Current character [00:06:08]
            char previous = s.charAt(i - 1); // Previous character [00:06:17]

            if (current == previous) {
                count++; // Streak continues [00:06:35]
            } else {
                max = Math.max(max, count); // Update peak record [00:06:54]
                count = 1;                  // Reset streak counter [00:06:59]
            }
        }

        // Final boundary sync to handle trailing character streak [00:05:34, 00:07:06]
        max = Math.max(max, count);

        return max; // Return highest streak count [00:07:15]
    }

    // APPROACH 2: Dynamic Two-Pointer Anchor Window Strategy
    // Uses a left pointer to anchor the start of a character group and a right pointer to scan ahead, 
    // updating max length whenever a new character is hit.
    public static int maxPowerTwoPointer(String s) {
        if (s == null || s.length() == 0) return 0;

        int maxLen = 1;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            if (s.charAt(right) != s.charAt(left)) {
                left = right; // Advance anchor to new character
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Splitting the string using regex lookarounds creates intermediate 
    // String objects and incurs regex engine parsing overhead, but provides a functional layout.
    public static int maxPowerStream(String s) {
        if (s == null || s.length() == 0) return 0;

        // Split string wherever a character differs from its predecessor
        return Arrays.stream(s.split("(?<=(.))(?!\\1)"))
                .mapToInt(String::length)
                .max()
                .orElse(1);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Substring) ---
        String test1 = "leetcode";
        int res1_1 = maxPowerOptimal(test1);
        int res1_2 = maxPowerTwoPointer(test1);
        int res1_3 = maxPowerStream(test1);

        System.out.println("Test Case 1: \"leetcode\"");
        System.out.println("Approach 1 (Adjacent Pass) Result: " + res1_1);
        System.out.println("Approach 2 (Two Pointer)   Result: " + res1_2);
        System.out.println("Approach 3 (Stream Regex)  Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 2 && res1_2 == 2 && res1_3 == 2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Long Streak) ---
        String test2 = "abbcccddddeeeeedcba";
        int res2_1 = maxPowerOptimal(test2);
        int res2_2 = maxPowerTwoPointer(test2);
        int res2_3 = maxPowerStream(test2);

        System.out.println("Test Case 2: \"abbcccddddeeeeedcba\"");
        System.out.println("Approach 1 (Adjacent Pass) Result: " + res2_1);
        System.out.println("Approach 2 (Two Pointer)   Result: " + res2_2);
        System.out.println("Approach 3 (Stream Regex)  Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 5 && res2_2 == 5 && res2_3 == 5 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (From Video Explanation) ---
        String test3 = "aabbccc";
        int res3_1 = maxPowerOptimal(test3);
        int res3_2 = maxPowerTwoPointer(test3);
        int res3_3 = maxPowerStream(test3);

        System.out.println("Test Case 3: \"aabbccc\"");
        System.out.println("Approach 1 (Adjacent Pass) Result: " + res3_1);
        System.out.println("Approach 2 (Two Pointer)   Result: " + res3_2);
        System.out.println("Approach 3 (Stream Regex)  Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 == 3 && res3_2 == 3 && res3_3 == 3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}