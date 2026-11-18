package DSA.agoda;

/**
 * ============================================================================
 * [AGODA HACKERRANK - 01] LEXICOGRAPHICALLY SMALLEST TASK QUEUE
 * ============================================================================
 *
 * SOURCE:
 *   Agoda HackerRank OA (Reported on LeetCode Discuss & DesiQnA).
 *
 * PROBLEM:
 *   You are given a string `taskQueue` of length `n` consisting only of the
 *   characters `'1'`, `'2'`, and `'3'`, representing task priority levels:
 *     - `'1'` : Low priority
 *     - `'2'` : Medium priority
 *     - `'3'` : High priority
 *
 *   You may perform the following adjacent swap operations any number of times:
 *     1. Swap adjacent `'1'` and `'2'` (i.e., `"12" <-> "21"`).
 *     2. Swap adjacent `'2'` and `'3'` (i.e., `"23" <-> "32"`).
 *
 *   Notice: You CANNOT swap adjacent `'1'` and `'3'` (`"13"` and `"31"` are locked).
 *   Return the lexicographically smallest task queue string obtainable.
 *
 * CONSTRAINTS:
 *   - 1 <= taskQueue.length() <= 10^5
 *   - `taskQueue` consists solely of characters `'1'`, `'2'`, and `'3'`.
 *
 * EXAMPLES:
 *   Example 1:
 *     Input:  taskQueue = "312"
 *     Output: "123"
 *     Explanation: Wait—let's check! In "312", '3' is before '1', and '1' CANNOT
 *                  cross '3'! Swapping '1' and '2' gives "321", then '3' and '2'
 *                  gives "231"! So "312" -> "231" (NOT "123"!).
 *
 *   Example 2:
 *     Input:  taskQueue = "2312"
 *     Output: "2231"
 *     Explanation: Both '2's can slide past '3' to the front, while '1' remains
 *                  trapped behind '3'.
 *
 *   Example 3:
 *     Input:  taskQueue = "1323321"
 *     Output: "1223331"
 *     Explanation: The leading '1' stays first; both '2's move directly in front
 *                  of the first '3'; the trailing '1' stays trapped behind the '3's.
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - The "Ghost / Fluid Bridge" Property of `'2'`:
 *       * `'2'` can swap with `'1'` AND `'2'` can swap with `'3'`. That means
 *         every `'2'` can freely glide to ANY position in the string!
 *       * Conversely, `'1'` and `'3'` can NEVER pass through each other—not even
 *         with a `'2'` between them (`"321" -> "231"` or `"312"`, `'3'` always
 *         stays to the left of `'1'`).
 *       * Therefore, the subsequence formed by removing all `'2'`s (consisting solely
 *         of `'1'`s and `'3'`s) is **100% IMMUTABLE**.
 *
 *   - Where should all the `'2'`s go to make the string lexicographically smallest?
 *       * Putting `'2'` BEFORE `'1'` (`"21"` vs `"12"`) makes the string WORSE (`'2' > '1'`).
 *       * Putting `'2'` BEFORE `'3'` (`"23"` vs `"32"`) makes the string BETTER (`'2' < '3'`).
 *       * Therefore, every single `'2'` wants to sit:
 *           1. AFTER all leading `'1'`s (so `'1'`s stay at the very front), AND
 *           2. IMMEDIATELY BEFORE the very first `'3'` (to push the first `'3'` as
 *              far right as possible!).
 *       * If there is NO `'3'` in the string at all (only `'1'`s and `'2'`s), all
 *         `'2'`s must go at the very end after every `'1'`.
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Guard Edge Case:
 *      - If `taskQueue == null || taskQueue.isEmpty()`, return `""`.
 *
 *   2. Separate Flexible `'2'`s from the Immutable `'1'`/`'3'` Skeleton:
 *      - Initialize `count2 = 0` and `StringBuilder nonTwos = new StringBuilder()`.
 *      - Scan `taskQueue` from left to right:
 *          * If `c == '2'`, increment `count2++` (pluck `'2'` out of the queue).
 *          * Otherwise (`'1'` or `'3'`), append `c` to `nonTwos` (preserving exact
 *            relative order of all `'1'`s and `'3'`s).
 *
 *   3. Locate the First `'3'` in `nonTwos`:
 *      - Scan `nonTwos` from `i = 0` to `nonTwos.length() - 1` to find the first
 *        index `firstThreeIdx` where `nonTwos.charAt(i) == '3'` (default `-1` if none).
 *      - Notice: Every character in `nonTwos` before `firstThreeIdx` is guaranteed
 *        to be `'1'`!
 *
 *   4. Reconstruct the Optimal String:
 *      - Case A (`firstThreeIdx == -1`, no `'3'` exists):
 *          * Append all of `nonTwos` (all `'1'`s), then append `count2` copies of `'2'`.
 *      - Case B (`firstThreeIdx != -1`, at least one `'3'` exists):
 *          * Append `nonTwos.substring(0, firstThreeIdx)` (all leading `'1'`s).
 *          * Dump all `count2` copies of `'2'` right here!
 *          * Append `nonTwos.substring(firstThreeIdx)` (the first `'3'` and everything after it).
 *
 * ============================================================================
 * VISUAL DRY RUN: taskQueue = "1323321"
 * ============================================================================
 *   Step 1: Separate '2's from immutable '1'/'3' skeleton
 *     Input:    1  3  2  3  3  2  1
 *     nonTwos:  1  3     3  3     1   -> "13331"  (Locked relative order!)
 *     count2:         1        1      -> 2 twos plucked out
 *
 *   Step 2: Find first '3' in `nonTwos` ("13331")
 *     Index 0: '1'
 *     Index 1: '3'  <-- firstThreeIdx = 1!
 *
 *   Step 3: Assemble Result
 *     1. Prefix before first '3'  : nonTwos[0..1) = "1"
 *     2. Dump all `count2` twos   : "22"
 *     3. Suffix from first '3' on : nonTwos[1..5) = "3331"
 *     Concatenated Result         : "1" + "22" + "3331" = "1223331"
 *
 *   Let's also trace `taskQueue = "312"`:
 *     - `nonTwos` = `"31"`, `count2` = `1`
 *     - `firstThreeIdx` = `0` (`'3'` is at index 0)
 *     - Assemble: `""` + `"2"` + `"31"` = `"231"`!
 *       (Actual swap sequence: `"312" -> "321" -> "231"`. `'1'` can never jump left of `'3'`!)
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(n) - One pass to partition, one scan to find first `'3'`, and one
 *                   pass to build the result string (`O(n) + O(n) + O(n) = O(n)`).
 *   - Space: O(n) - `StringBuilder` buffers `nonTwos` and `result` of length at most `n`.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (BFS/DFS over all reachable string states via adjacent swaps -> exponential `O(n!)` states.)
 *   2. What invariant must remain true after every loop iteration?
 *      (Across any valid sequence of swaps, the relative order of all `'1'`s and `'3'`s never changes.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (Dumping `'2'`s BEFORE the first `'3'` rather than at index `0` is crucial: for `"1132"`,
 *       putting `'2'` at index `0` gives `"2113"`, whereas putting `'2'` right before `'3'` gives `"1123"`.)
 *   4. Which edge cases change the control flow (no `'3'`s at all e.g. `"2121" -> "1122"`,
 *      no `'1'`s at all e.g. `"3232" -> "2233"`, all `'2'`s `"222"`, already optimal `"112233"`)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_HR01_LexicographicallySmallestTaskQueue {

    public static String getSmallestQueue(String taskQueue) {
        if (taskQueue == null || taskQueue.isEmpty()) {
            return "";
        }

        // Step 1: Pluck out all '2's (fluid elements) while locking the relative order of '1's and '3's
        int count2 = 0;
        StringBuilder nonTwos = new StringBuilder();

        for (int i = 0; i < taskQueue.length(); i++) {
            char c = taskQueue.charAt(i);
            if (c == '2') {
                count2++;
            } else {
                nonTwos.append(c);
            }
        }

        // Step 2: Locate the first '3' in the immutable '1'/'3' skeleton
        // Every character before `firstThreeIdx` is guaranteed to be '1'
        int firstThreeIdx = -1;
        for (int i = 0; i < nonTwos.length(); i++) {
            if (nonTwos.charAt(i) == '3') {
                firstThreeIdx = i;
                break;
            }
        }

        // Step 3: Reconstruct the lexicographically smallest string
        StringBuilder result = new StringBuilder(taskQueue.length());

        if (firstThreeIdx == -1) {
            // Case A: No '3' exists in the queue -> place all '1's first, then all '2's at the end
            result.append(nonTwos);
            for (int i = 0; i < count2; i++) {
                result.append('2');
            }
        } else {
            // Case B: Place leading '1's first, dump ALL '2's right before the first '3',
            //         then append the first '3' and everything after it
            result.append(nonTwos, 0, firstThreeIdx);
            for (int i = 0; i < count2; i++) {
                result.append('2');
            }
            result.append(nonTwos, firstThreeIdx, nonTwos.length());
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test 1 (\"312\"):     " + getSmallestQueue("312")     + " | Expected: \"231\"");
        System.out.println("Test 2 (\"2312\"):    " + getSmallestQueue("2312")    + " | Expected: \"2231\"");
        System.out.println("Test 3 (\"1323321\"): " + getSmallestQueue("1323321") + " | Expected: \"1223331\"");
        System.out.println("Test 4 (\"2121\"):    " + getSmallestQueue("2121")    + " | Expected: \"1122\"");
    }
}