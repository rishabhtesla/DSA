package DSA.ArraysandStrings;

/**
 * ============================================================================
 * [11 / 24] - H-INDEX (LeetCode 274)
 * ============================================================================
 *
 * 1. PROBLEM EXPLANATION:
 * ----------------------------------------------------------------------------
 * You are given an array of integers `citations`, where `citations[i]` represents
 * the number of times the i-th research paper has been cited.
 *
 * Your goal is to compute the researcher's "h-index".
 *
 * In simple terms, an h-index of `h` means:
 *   "The researcher has published at least `h` papers that have EACH been
 *    cited at least `h` times."
 *
 * We want the LARGEST possible value for `h`.
 *
 *
 * 2. CRUCIAL OBSERVATION ("THE AHA! MOMENT"):
 * ----------------------------------------------------------------------------
 * If a researcher published `n` papers in total:
 * - Can their h-index ever be n + 1? NO. Because they only wrote n papers.
 * - Therefore, 0 <= h <= n.
 * - Any paper with citations >= n can just be treated as having "n or more citations".
 *   Whether a paper has 5 citations or 5,000,000 citations, it counts toward the
 *   exact same threshold if n = 5.
 *
 * This allows us to use an array of size `n + 1` (a Bucket/Counting Sort)
 * instead of sorting the whole array in O(n log n).
 *
 *
 * 3. WALKTHROUGH CASES & EXAMPLES:
 * ----------------------------------------------------------------------------
 *
 * [Case 1: Standard Mixed Values]
 * Input: citations = [3, 0, 6, 1, 5] (Total papers n = 5)
 * Let's test possible values of h from highest (5) down to 0:
 * - Can h = 5? Need 5 papers with >= 5 citations. Papers: [6, 5] (only 2 papers) -> No.
 * - Can h = 4? Need 4 papers with >= 4 citations. Papers: [6, 5] (only 2 papers) -> No.
 * - Can h = 3? Need 3 papers with >= 3 citations. Papers: [3, 6, 5] (exactly 3 papers) -> YES!
 * Output: 3
 *
 * [Case 2: All Zero Citations (Researcher with no impact yet)]
 * Input: citations = [0, 0, 0] (Total papers n = 3)
 * - Can h = 3? Need 3 papers with >= 3 citations. (0 papers) -> No.
 * - Can h = 2? Need 2 papers with >= 2 citations. (0 papers) -> No.
 * - Can h = 1? Need 1 paper with >= 1 citation.   (0 papers) -> No.
 * - Can h = 0? Need 0 papers with >= 0 citations. -> YES.
 * Output: 0
 *
 * [Case 3: All Citations Extremely High]
 * Input: citations = [100, 200] (Total papers n = 2)
 * - Can h = 2? Need 2 papers with >= 2 citations. Both [100, 200] qualify! -> YES.
 * Output: 2
 * Note: Even though citations are 100+, h cannot exceed n (which is 2).
 *
 * [Case 4: Single Paper]
 * Input: citations = [10] (n = 1)
 * - Can h = 1? Need 1 paper with >= 1 citation. [10] qualifies! -> YES.
 * Output: 1
 *
 *
 * 4. ALGORITHM (Bucket Sort - O(n) Time, O(n) Space):
 * ----------------------------------------------------------------------------
 * 1. Create `buckets` of size `n + 1`.
 *    - `buckets[k]` stores the number of papers with EXACTLY `k` citations (for 0 <= k < n).
 *    - `buckets[n]` stores the number of papers with `n` OR MORE citations.
 * 2. Traverse backwards from `h = n` down to `0`, keeping a running sum of papers.
 * 3. The first `h` where `paperCount >= h` is guaranteed to be our answer.
 * ============================================================================
 */
public class P11_HIndex {

    public static int hIndex(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];

        // Step 1: Count how many papers fall into each citation bucket
        for (int c : citations) {
            if (c >= n) {
                buckets[n]++;
            } else {
                buckets[c]++;
            }
        }

        // Step 2: Accumulate papers from highest possible citation count down to 0
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
        // Test Case 1: Standard mixed
        int[] case1 = {3, 0, 6, 1, 5};
        System.out.println("Case 1 Output: " + hIndex(case1)); // Expected: 3

        // Test Case 2: All zeros
        int[] case2 = {0, 0, 0};
        System.out.println("Case 2 Output: " + hIndex(case2)); // Expected: 0

        // Test Case 3: Values exceeding length
        int[] case3 = {100, 200};
        System.out.println("Case 3 Output: " + hIndex(case3)); // Expected: 2

        // Test Case 4: Single element
        int[] case4 = {10};
        System.out.println("Case 4 Output: " + hIndex(case4)); // Expected: 1
    }
}