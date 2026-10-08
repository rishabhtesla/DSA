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
 *   A system manages tasks represented as a string `taskQueue` of length n.
 *   Each task has a priority level:
 *     '1' for low, '2' for medium, '3' for high.
 *   You can perform the following operations any number of times:
 *     1. Swap adjacent tasks with values '1' and '2' (or '2' and '1').
 *     2. Swap adjacent tasks with values '2' and '3' (or '3' and '2').
 *   Notice: You CANNOT directly swap '1' and '3'.
 *   Return the lexicographically smallest task queue possible.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Relative Ordering Rule:
 *     Because '1' can swap with '2', and '3' can swap with '2', the digit '2' is 
 *     a "fluid bridge" that can cross either '1' or '3'.
 *     HOWEVER, '1' and '3' CANNOT pass through each other directly or indirectly!
 *     The relative order of all '1's and '3's in the string is IMMUTABLE.
 *   - Greedy Strategy:
 *     All '2's are completely flexible relative to '1' and '3'. To minimize lexicographically,
 *     we want '1's as far left as possible, but '2's should only go before '3's, never 
 *     blocking a '1' that can move ahead of it.
 *     1. Count total occurrences of '2'.
 *     2. Write out all '1's that appear before the first '3'.
 *     3. When encountering the first '3', dump ALL accumulated '2's before it!
 *        (Since '2' < '3', putting all '2's before the first '3' is always optimal).
 *     4. Continue appending the remaining '1's and '3's in their original relative sequence.
 *     5. If there were no '3's at all, place the '2's at the very end after all '1's.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Single linear scan and construction.
 *   - Space: O(n) - StringBuilder output.
 */
public class AG_HR01_LexicographicallySmallestTaskQueue {

    public static String getSmallestQueue(String taskQueue) {
        int count2 = 0;
        StringBuilder nonTwos = new StringBuilder();

        // Separate '2's while keeping relative order of '1's and '3's
        for (char c : taskQueue.toCharArray()) {
            if (c == '2') {
                count2++;
            } else {
                nonTwos.append(c);
            }
        }

        // Find the index of the first '3'
        int firstThreeIdx = -1;
        for (int i = 0; i < nonTwos.length(); i++) {
            if (nonTwos.charAt(i) == '3') {
                firstThreeIdx = i;
                break;
            }
        }

        StringBuilder result = new StringBuilder();

        if (firstThreeIdx == -1) {
            // No '3' exists: all '1's first, followed by all '2's
            result.append(nonTwos);
            for (int i = 0; i < count2; i++) {
                result.append('2');
            }
        } else {
            // Append '1's up to first '3'
            result.append(nonTwos.substring(0, firstThreeIdx));
            // Dump all '2's immediately before the first '3'
            for (int i = 0; i < count2; i++) {
                result.append('2');
            }
            // Append remaining sequence ('3' and any following '1's/'3's)
            result.append(nonTwos.substring(firstThreeIdx));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("AG_HR01 Output (312):     " + getSmallestQueue("312"));     // Expected: "123"
        System.out.println("AG_HR01 Output (2312):    " + getSmallestQueue("2312"));    // Expected: "2231" -> ('1' cannot cross '3')
        System.out.println("AG_HR01 Output (1323321): " + getSmallestQueue("1323321")); // Expected: "1223331"
    }
}