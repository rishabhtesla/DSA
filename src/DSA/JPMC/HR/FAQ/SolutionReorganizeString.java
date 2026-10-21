package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Reorganize Payload String
 * ============================================================================
 *
 * Problem Statement:
 * Given a string s representing event identifiers, rearrange the characters of s
 * so that no two adjacent characters are identical.
 *
 * Return any valid rearranged string. If no valid rearrangement exists, return
 * an empty string "".
 *
 * Input Format for Custom Testing:
 * - Line 1: String s.
 *
 * Constraints:
 * - 1 <= s.length() <= 10^5
 * - s consists of lowercase English letters.
 *
 * Example 1:
 * Input:
 *   aab
 * Output:
 *   aba
 *
 * Example 2:
 * Input:
 *   aaab
 * Output:
 *   "" (Impossible because 'a' occurs 3 times, which exceeds ceil(4 / 2) = 2)
 *
 * ============================================================================
 * LOGIC & APPROACH (Greedy Priority Queue):
 * ============================================================================
 * 1. Pigeonhole Feasibility Check:
 *    Let maxFreq be the count of the most frequent character.
 *    If maxFreq > (n + 1) / 2, adjacent collisions are mathematically unavoidable.
 *    Return "" immediately.
 *
 * 2. Max-Heap Strategy:
 *    - Insert all character frequency entries into a Max-Heap based on count.
 *    - In each step, extract the two characters with the highest remaining
 *      frequencies: `first` and `second`.
 *    - Append `first` then `second` to the result.
 *    - Decrement their counts. If any count remains > 0, reinsert into heap.
 *    - If 1 character remains at the end, append it.
 *
 * Complexity:
 * - Time: O(n log 26) = O(n)
 * - Space: O(1) auxiliary space (alphabet size <= 26)
 * ============================================================================
 */
/**
 * Learning supplement: brute-force permutations are factorial; a max-heap
 * greedily placing two most frequent characters is O(n log alphabet) time.
 * Examples: {@code "aab" -> "aba"} and {@code "aaab" -> ""}. Invariant:
 * no adjacent output characters match and heap counts describe unplaced
 * characters. The main aab trace places a, then b, then a. Ask why
 * maxFrequency > ceil(n/2) proves impossibility.
 */
public class SolutionReorganizeString {

    static class CharFreq {
        char ch;
        int count;

        CharFreq(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }

    public static String reorganizeString(String s) {
        if (s == null || s.length() <= 1) {
            return s == null ? "" : s;
        }

        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int maxAllowed = (s.length() + 1) / 2;
        PriorityQueue<CharFreq> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b.count, a.count));

        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                if (freq[i] > maxAllowed) {
                    return "";
                }
                maxHeap.offer(new CharFreq((char) ('a' + i), freq[i]));
            }
        }

        StringBuilder sb = new StringBuilder();

        while (maxHeap.size() >= 2) {
            CharFreq first = maxHeap.poll();
            CharFreq second = maxHeap.poll();

            sb.append(first.ch);
            sb.append(second.ch);

            first.count--;
            second.count--;

            if (first.count > 0) maxHeap.offer(first);
            if (second.count > 0) maxHeap.offer(second);
        }

        if (!maxHeap.isEmpty()) {
            sb.append(maxHeap.poll().ch);
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferedReader.readLine();
        if (s != null) {
            System.out.println(reorganizeString(s.trim()));
        }
        bufferedReader.close();
    }
}