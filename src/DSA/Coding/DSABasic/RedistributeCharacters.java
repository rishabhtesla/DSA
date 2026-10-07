package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * PROBLEM STATEMENT:
 * You are given an array of strings 'words' (0-indexed) [00:00:23].
 * In one operation, pick two distinct indices i and j, where words[i] is a non-empty string, 
 * and move any character from words[i] to any position in words[j] [00:00:37, 00:00:48].
 * Return true if you can make every string in 'words' equal using any number of operations, 
 * and false otherwise [00:00:55, 00:01:05].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: words = ["abc", "aabc", "bc"] [00:00:28]
 * - Process: Move 'a' from words[1] ("aabc") to words[2] ("bc") -> words becomes ["abc", "abc", "abc"] [00:01:11].
 * - Result: true [00:01:17]
 * 
 * Example 2: words = ["ab", "a"] [00:01:21]
 * - Process: Total character counts: 'a' appears 2 times, 'b' appears 1 time.
 * - String count = 2. 'a' count (2) % 2 == 0 (can distribute 'a' to both strings) [00:03:09].
 * - 'b' count (1) % 2 == 1 != 0 (cannot distribute 'b' equally across 2 strings) [00:03:30].
 * - Result: false [00:01:35]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Frequency Array Divisibility Check):
 * • Index Initialization: Outer loop 'i' iterates through words array (0 to words.length - 1) [00:07:47].
 *   Inner loop 'j' iterates through characters of current word `words[i]` [00:08:34].
 * • Condition Boundaries:
 *   - Character index mapping: `index = ch - 'a'` (0 to 25) [00:09:07, 00:10:07].
 *   - Divisibility validation loop: `for (int count : freq)` -> if `count % words.length != 0`, return false [00:12:05, 00:12:28].
 * • Operational Steps:
 *   1. Create an integer frequency array of size 26 initialized to 0 [00:07:16].
 *   2. Traverse each word and increment the character count in `freq` array for each character [00:08:29, 00:10:40].
 *   3. Iterate through `freq` array: check if `freq[i] % words.length == 0` for all non-zero counts [00:12:11].
 *   4. If any character count is not divisible by `words.length`, return false [00:12:28].
 *   5. If all character counts are evenly divisible, return true [00:12:40].
 * • Time Complexity: O(N * M) - Where N is number of words and M is average length of a word [00:04:45].
 * • Space Complexity: O(1) auxiliary space - Fixed 26-element frequency array [00:07:24].
 * • LOGIC BEHIND THIS APPROACH:
 *   We can move characters freely between any strings [00:00:48]. Thus, character positions do not matter.
 *   To make all strings equal, every character must be distributed equally across all `N` strings (`words.length`) [00:02:27].
 *   Therefore, for every unique character, its total total count across all words must be a multiple of `N` (`count % N == 0`) [00:03:10, 00:05:05].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Frequency Array - words = ["aabb", "bba", "abcd"], N = 3):
 * Frequency Array Construction (size 26):
 * Process "aabb": 'a': 2, 'b': 2 [00:14:49]
 * Process "bba" : 'a': 2+1=3, 'b': 2+2=4 [00:16:03]
 * Process "abcd": 'a': 3+1=4, 'b': 4+1=5, 'c': 1, 'd': 1 [00:16:45]
 * Total Counts: 'a': 4, 'b': 5, 'c': 1, 'd': 1 [00:17:12]
 * Divisibility Verification (N = 3):
 * - Check 'a' (4): 4 % 3 = 1 != 0 -> Cannot divide 'a' evenly across 3 words! [00:17:20]
 * - Immediately return false [00:17:28].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: HashMap Frequency Counting - words = ["abc", "aabc", "bc"], N = 3):
 * HashMap Population:
 * - "abc"  -> {a:1, b:1, c:1}
 * - "aabc" -> {a:3, b:2, c:2}
 * - "bc"   -> {a:3, b:3, c:3}
 * Divisibility Check (N = 3):
 * - key 'a': count 3 % 3 == 0 -> OK
 * - key 'b': count 3 % 3 == 0 -> OK
 * - key 'c': count 3 % 3 == 0 -> OK
 * Return true.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Pipeline - words = ["ab", "a"], N = 2):
 * Stream Stage 1: FlatMap characters -> Stream['a', 'b', 'a']
 * Stream Stage 2: Group by character & count -> { 'a': 2, 'b': 1 }
 * Stream Stage 3: Validate all match (count % 2 == 0):
 *   - 'a': 2 % 2 == 0 -> true
 *   - 'b': 1 % 2 == 1 -> false!
 * Match Result = false.
 */
public class RedistributeCharacters {

    // APPROACH 1: Frequency Array Divisibility Check (Anchor Strategy)
    public static boolean makeEqualOptimal(String[] words) {
        if (words == null || words.length == 0) return true;

        // Fixed 26-element frequency array for lower case English alphabets [00:07:16]
        int[] frequency = new int[26];

        // Iterate through all strings in the words array [00:07:47]
        for (int i = 0; i < words.length; i++) {
            String word = words[i]; // Fetch current word [00:07:59]

            // Iterate through every character of the word [00:08:34]
            for (int j = 0; j < word.length(); j++) {
                char ch = word.charAt(j); // Extract character [00:08:58]
                int index = ch - 'a'; // Convert character to 0-25 array index [00:09:07]
                frequency[index] = frequency[index] + 1; // Increment character frequency [00:10:40]
            }
        }

        int totalWords = words.length; // Total target strings to balance [00:12:15]

        // Verify if every character count can be distributed evenly across all words [00:12:05]
        for (int i = 0; i < 26; i++) {
            if (frequency[i] % totalWords != 0) {
                return false; // Found a character that cannot be split equally [00:12:28]
            }
        }

        return true; // All characters distributed evenly [00:12:40]
    }

    // APPROACH 2: HashMap Frequency Counting Strategy
    // Uses Java HashMap to record character counts, dynamically handling character distributions.
    public static boolean makeEqualMap(String[] words) {
        if (words == null || words.length == 0) return true;

        Map<Character, Integer> charCounts = new HashMap<>();

        for (String word : words) {
            for (char ch : word.toCharArray()) {
                charCounts.put(ch, charCounts.getOrDefault(ch, 0) + 1);
            }
        }

        int n = words.length;
        for (int count : charCounts.values()) {
            if (count % n != 0) {
                return false;
            }
        }

        return true;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Flattens string characters using Stream flatMap operations. 
    // Boxing chars into Objects adds overhead compared to direct primitive array counting.
    public static boolean makeEqualStream(String[] words) {
        if (words == null || words.length == 0) return true;

        int n = words.length;

        return Arrays.stream(words)
                .flatMap(word -> word.chars().mapToObj(c -> (char) c))
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .values()
                .stream()
                .allMatch(count -> count % n == 0);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Valid Distribution) ---
        String[] test1 = {"abc", "aabc", "bc"};
        boolean res1_1 = makeEqualOptimal(test1);
        boolean res1_2 = makeEqualMap(test1);
        boolean res1_3 = makeEqualStream(test1);

        System.out.println("Test Case 1: [\"abc\", \"aabc\", \"bc\"]");
        System.out.println("Approach 1 (Freq Array)  Result: " + res1_1);
        System.out.println("Approach 2 (HashMap)     Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 && res1_2 && res1_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Invalid Distribution) ---
        String[] test2 = {"ab", "a"};
        boolean res2_1 = makeEqualOptimal(test2);
        boolean res2_2 = makeEqualMap(test2);
        boolean res2_3 = makeEqualStream(test2);

        System.out.println("Test Case 2: [\"ab\", \"a\"]");
        System.out.println("Approach 1 (Freq Array)  Result: " + res2_1);
        System.out.println("Approach 2 (HashMap)     Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res2_3);
        System.out.println("Verification: " + (!res2_1 && !res2_2 && !res2_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (From Video Explanation - All Equal Strings) ---
        String[] test3 = {"abc", "abc", "abc"};
        boolean res3_1 = makeEqualOptimal(test3);
        boolean res3_2 = makeEqualMap(test3);
        boolean res3_3 = makeEqualStream(test3);

        System.out.println("Test Case 3: [\"abc\", \"abc\", \"abc\"]");
        System.out.println("Approach 1 (Freq Array)  Result: " + res3_1);
        System.out.println("Approach 2 (HashMap)     Result: " + res3_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 && res3_2 && res3_3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}