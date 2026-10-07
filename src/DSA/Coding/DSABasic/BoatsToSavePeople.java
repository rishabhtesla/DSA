package DSA.Coding.DSABasic;

import java.util.Arrays;

/**
 * PROBLEM STATEMENT:
 * You are given an array 'people' where people[i] is the weight of the i-th person, and an infinite 
 * number of boats where each boat can carry a maximum weight of 'limit' [00:00:18, 00:00:30]. 
 * Each boat carries at most TWO people at the same time, provided the sum of the weight of those 
 * people is at most 'limit' [00:00:46, 00:00:52].
 * Return the minimum number of boats required to carry every given person [00:01:04].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: people = [1, 2], limit = 3 [00:01:21]
 * - Process: Both persons (weights 1 and 2) can share a single boat because 1 + 2 = 3 <= 3 [00:01:28].
 * - Result: 1
 * 
 * Example 2: people = [2, 3, 4, 6, 9, 9, 10, 11], limit = 11 (Simulated inside the video explanation [00:03:52, 00:11:59])
 * - Process:
 *   - Sort array: [2, 3, 4, 6, 9, 9, 10, 11] [00:04:10, 00:11:59].
 *   - start = 0 (val 2), end = 7 (val 11): 2 + 11 = 13 > 11. Heavy person 11 takes a solo boat. end-- (6), boats = 1 [00:05:36, 00:12:43].
 *   - start = 0 (val 2), end = 6 (val 10): 2 + 10 = 12 > 11. Heavy person 10 takes a solo boat. end-- (5), boats = 2 [00:06:07, 00:13:05].
 *   - start = 0 (val 2), end = 5 (val 9) : 2 + 9 = 11 <= 11. Pair (2, 9) shares a boat! start++ (1), end-- (4), boats = 3 [00:06:29, 00:13:45].
 *   - start = 1 (val 3), end = 4 (val 9) : 3 + 9 = 12 > 11. Heavy person 9 takes a solo boat. end-- (3), boats = 4 [00:06:53, 00:13:58].
 *   - start = 1 (val 3), end = 3 (val 6) : 3 + 6 = 9 <= 11. Pair (3, 6) shares a boat! start++ (2), end-- (2), boats = 5 [00:07:35, 00:14:18].
 *   - start = 2 (val 4), end = 2 (val 4) : 4 + 4 = 8 <= 11. Single remaining person takes a boat. start++ (3), end-- (1), boats = 6 [00:07:49, 00:14:33].
 *   - Loop terminates (start > end).
 * - Result: 6 boats [00:08:14, 00:15:03]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Two Pointer Greedy Matching):
 * • Index Initialization: Sort the array first [00:10:52]. Pointer 'start' begins at index 0, 'end' begins at people.length - 1 [00:08:59].
 * • Condition Boundaries: Loop drives forward while `start <= end` [00:09:22].
 * • Operational Steps:
 *   1. Check if the lightest available person (`people[start]`) can pair with the heaviest (`people[end]`):
 *      `if (people[start] + people[end] <= limit)` [00:09:30].
 *   2. If valid, increment `start++` as both persons share the boat [00:10:03].
 *   3. Always decrement `end--` (the heaviest person leaves in this boat regardless of pairing) [00:10:19].
 *   4. Increment total boat count `boatsCount++` on every loop iteration [00:10:25].
 * • Time Complexity: O(n log n) - Governed by initial quicksort pass; two-pointer scan takes O(n) [00:04:45].
 * • Space Complexity: O(1) - Evaluated strictly in-place with minimal pointer registers.
 * • LOGIC BEHIND THIS APPROACH:
 *   To minimize the total number of boats, we must maximize the number of paired boats [00:05:23]. 
 *   The most constrained element is always the heaviest remaining person (`people[end]`). The best candidate to pair 
 *   with the heaviest person is the lightest remaining person (`people[start]`) [00:07:05]. If even the lightest person 
 *   cannot fit alongside the heaviest person, no one else can, so the heaviest person must sail alone [00:07:14, 00:13:28].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Two Pointer - people = [2, 3, 4, 6, 9, 9, 10, 11], limit = 11):
 * Initial: start = 0 (val 2), end = 7 (val 11), boatsCount = 0 [00:12:26]
 * Iteration 1: 2 + 11 = 13 > 11 -> Pair fails. end-- (6). boatsCount = 1 [00:12:47].
 * Iteration 2: 2 + 10 = 12 > 11 -> Pair fails. end-- (5). boatsCount = 2 [00:13:15].
 * Iteration 3: 2 + 9  = 11 <= 11 -> Pair matches! start++ (1), end-- (4). boatsCount = 3 [00:13:45].
 * Iteration 4: 3 + 9  = 12 > 11 -> Pair fails. end-- (3). boatsCount = 4 [00:13:58].
 * Iteration 5: 3 + 6  = 9  <= 11 -> Pair matches! start++ (2), end-- (2). boatsCount = 5 [00:14:18].
 * Iteration 6: start == end (4 + 4 = 8 <= 11) -> Last person takes boat. start++ (3), end-- (1). boatsCount = 6 [00:14:33].
 * Loop End (start > end). Return boatsCount = 6 [00:15:03].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Counting / Bucket Array - people = [3, 5, 3, 4], limit = 5):
 * Frequency Array Setup: count = [0, 0, 0, 2, 1, 1] (indices 0..5, weights 3:2, 4:1, 5:1)
 * Initial Pointers: start = 1, end = 5, boatsCount = 0
 * Iteration 1:
 *   - Advance start to first active weight: start = 3 (count[3] = 2)
 *   - Retain end at highest active weight: end = 5 (count[5] = 1)
 *   - Check: 3 + 5 = 8 > 5 -> Pair fails.
 *   - Action: count[5]-- (becomes 0), boatsCount = 1
 * Iteration 2:
 *   - start stays at 3. Decrement end to next active weight: end = 4 (count[4] = 1)
 *   - Check: 3 + 4 = 7 > 5 -> Pair fails.
 *   - Action: count[4]-- (becomes 0), boatsCount = 2
 * Iteration 3:
 *   - start stays at 3. Decrement end: end = 3 (count[3] = 2)
 *   - Check: 3 + 3 = 6 > 5 -> Pair fails.
 *   - Action: count[3]-- (becomes 1), boatsCount = 3
 * Iteration 4:
 *   - start = 3, end = 3 (count[3] = 1)
 *   - Check: 3 + 3 = 6 > 5 -> Pair fails.
 *   - Action: count[3]-- (becomes 0), boatsCount = 4
 * Loop End (all count entries zeroed out, start > end). Return boatsCount = 4.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Pipeline - people = [1, 2], limit = 3):
 * Stream Stage: Arrays.stream([1, 2]).sorted().toArray() -> sortedPeople = [1, 2]
 * Initial Pointers: start = 0 (val 1), end = 1 (val 2), boatsCount = 0
 * Iteration 1:
 *   - Check: sortedPeople[0] + sortedPeople[1] = 1 + 2 = 3 <= 3 -> Pair matches!
 *   - Action: start++ (1), end-- (0), boatsCount = 1
 * Loop End (start (1) > end (0)). Return boatsCount = 1.
 */
public class BoatsToSavePeople {

    // APPROACH 1: Two Pointer Greedy Matching (Anchor Strategy)
    public static int numRescueBoatsOptimal(int[] people, int limit) {
        if (people == null || people.length == 0) return 0;

        // Sort weights in non-decreasing order [00:10:52]
        Arrays.sort(people);

        int start = 0;
        int end = people.length - 1;
        int boatsCount = 0;

        while (start <= end) {
            // Check if lightest person can share the boat with the heaviest person [00:09:30]
            if (people[start] + people[end] <= limit) {
                start++; // Lightest person successfully boarded [00:10:03]
            }
            // The heaviest person always gets a boat (either paired or solo) [00:10:19]
            end--;
            boatsCount++; // Record boat dispatch [00:10:25]
        }

        return boatsCount;
    }

    // APPROACH 2: Bucket / Counting Array Strategy (O(n) Non-Sorting Optimization)
    // Avoids O(n log n) sorting by taking advantage of bounded weight values (`people[i] <= limit`). 
    // Uses a frequency array to count weight distributions and pairs items in linear O(n) time.
    public static int numRescueBoatsCounting(int[] people, int limit) {
        if (people == null || people.length == 0) return 0;

        int[] count = new int[limit + 1];
        for (int p : people) {
            count[p]++;
        }

        int start = 1;
        int end = limit;
        int boatsCount = 0;

        while (start <= end) {
            // Find next available lightest weight
            while (start <= end && count[start] <= 0) {
                start++;
            }
            // Find next available heaviest weight
            while (start <= end && count[end] <= 0) {
                end--;
            }

            if (start > end) break;

            // If lightest + heaviest fit together
            if (start + end <= limit) {
                count[start]--;
            }
            // Heaviest leaves regardless
            count[end]--;
            boatsCount++;
        }

        return boatsCount;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Boxing primitives into objects for stream-based pipeline execution 
    // introduces significant memory overhead and allocation lag, making it noticeably slower than 
    // primitive in-place two-pointer evaluation.
    public static int numRescueBoatsStream(int[] people, int limit) {
        if (people == null || people.length == 0) return 0;

        // Sort via primitive stream and convert to array
        int[] sortedPeople = Arrays.stream(people).sorted().toArray();

        int start = 0;
        int end = sortedPeople.length - 1;
        int boatsCount = 0;

        while (start <= end) {
            if (sortedPeople[start] + sortedPeople[end] <= limit) {
                start++;
            }
            end--;
            boatsCount++;
        }

        return boatsCount;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Simple Match) ---
        int[] test1 = {1, 2};
        int limit1 = 3;
        int res1_1 = numRescueBoatsOptimal(test1.clone(), limit1);
        int res1_2 = numRescueBoatsCounting(test1.clone(), limit1);
        int res1_3 = numRescueBoatsStream(test1.clone(), limit1);

        System.out.println("Test Case 1: [1, 2], limit = 3");
        System.out.println("Approach 1 (Two Pointer) Result: " + res1_1);
        System.out.println("Approach 2 (Counting)    Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)  Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 1 && res1_2 == 1 && res1_3 == 1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {2, 3, 4, 6, 9, 9, 10, 11};
        int limit2 = 11;
        int res2_1 = numRescueBoatsOptimal(test2.clone(), limit2);
        int res2_2 = numRescueBoatsCounting(test2.clone(), limit2);
        int res2_3 = numRescueBoatsStream(test2.clone(), limit2);

        System.out.println("Test Case 2: [2, 3, 4, 6, 9, 9, 10, 11], limit = 11");
        System.out.println("Approach 1 (Two Pointer) Result: " + res2_1);
        System.out.println("Approach 2 (Counting)    Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)  Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 6 && res2_2 == 6 && res2_3 == 6 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (All Solo Boats) ---
        int[] test3 = {3, 5, 3, 4};
        int limit3 = 5;
        int res3_1 = numRescueBoatsOptimal(test3.clone(), limit3);
        int res3_2 = numRescueBoatsCounting(test3.clone(), limit3);
        int res3_3 = numRescueBoatsStream(test3.clone(), limit3);

        System.out.println("Test Case 3: [3, 5, 3, 4], limit = 5");
        System.out.println("Approach 1 (Two Pointer) Result: " + res3_1);
        System.out.println("Approach 2 (Counting)    Result: " + res3_2);
        System.out.println("Approach 3 (Stream API)  Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 == 4 && res3_2 == 4 && res3_3 == 4 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}