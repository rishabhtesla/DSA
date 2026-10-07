package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [11 / 24] - H-INDEX (LeetCode 274)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given citations[], return the researcher's h-index: the maximum h such that
 *   at least h papers have at least h citations each.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Sorting: Sort descending, find where citations[i] < i + 1 -> O(n log n).
 *   - Bucket / Counting Sort: O(n)
 *     Notice h cannot exceed n (the total number of papers). Any citation >= n
 *     can be pooled into a single bucket `n`.
 *     1. Allocate bucket array of size n + 1.
 *     2. Count papers: if citations >= n, increment bucket[n]; else bucket[citations].
 *     3. Traverse backwards from n down to 0, accumulating paper counts.
 *        The first h where accumulated papers >= h is the maximum valid h-index.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Two linear passes (one to bucket, one to aggregate).
 *   - Space: O(n) - Size n + 1 bucket array.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class P11_HIndex {

    public static int hIndex(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];

        for (int c : citations) {
            if (c >= n) {
                buckets[n]++;
            } else {
                buckets[c]++;
            }
        }

        int paperCount = 0;
        for (int h = n; h >= 0; h--) {
            paperCount += buckets[h];
            if (paperCount >= h) {
                return h;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        int[] citations = {3, 0, 6, 1, 5};
        System.out.println("P11 Output: " + hIndex(citations));
        // Expected: 3
    }
}