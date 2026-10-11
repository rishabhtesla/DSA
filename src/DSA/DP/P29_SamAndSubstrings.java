package DSA.DP;

/**
 * ============================================================================
 * [29 / 30] - SAM AND SUB-STRINGS (HackerRank Benchmark DP)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Samantha and Sam are playing a numbers game. Given an integer as a string `s`, 
 * find the sum of all substrings of `s` when interpreted as integers.
 * Because the sum can be huge, return the result modulo 10^9 + 7.
 *
 * Example:
 *   s = "123"
 *   Substrings: "1", "2", "3", "12", "23", "123"
 *   Sum = 1 + 2 + 3 + 12 + 23 + 123 = 164.
 *
 *
 * 2. RECURRENCE & DIGIT SHIFT LOGIC:
 * ----------------------------------------------------------------------------
 * Let `endingSum` be the sum of all substrings ending strictly at index `i`.
 *
 * When moving from index (i - 1) to index (i) with digit `d`:
 *   1. Every substring that ended at index (i - 1) gets extended by appending `d`.
 *      In place value, appending `d` shifts the previous number by 10 and adds `d`.
 *      Contribution = endingSum * 10.
 *
 *   2. The digit `d` is added to all (i + 1) substrings ending at index `i`:
 *      (e.g., at i=2: "123", "23", and "3" all get digit 3 in their ones place).
 *      Contribution = d * (i + 1).
 *
 * Transition:
 *   endingSum_curr = (endingSum_prev * 10 + digit * (i + 1)) % MOD
 *   totalSum = (totalSum + endingSum_curr) % MOD
 *
 * Space Optimization:
 *   Only ONE scalar variable is needed for `endingSum`. Space is O(1)!
 *
 *
 * 3. DRY RUN TRACE:
 * ----------------------------------------------------------------------------
 * s = "123", MOD = 10^9 + 7
 *
 * i = 0, digit = 1:
 *   endingSum = (0 * 10 + 1 * (0 + 1)) = 1  (Substrings: "1")
 *   totalSum = 0 + 1 = 1
 *
 * i = 1, digit = 2:
 *   endingSum = (1 * 10 + 2 * (1 + 1)) = 10 + 4 = 14  (Substrings: "12" + "2" = 14)
 *   totalSum = 1 + 14 = 15
 *
 * i = 2, digit = 3:
 *   endingSum = (14 * 10 + 3 * (2 + 1)) = 140 + 9 = 149  (Substrings: "123" + "23" + "3" = 149)
 *   totalSum = 15 + 149 = 164
 *
 * Result: 164
 *
 *
 * 4. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N) - Single linear pass over string of length N.
 * - Space Complexity: O(1) - Constant variables.
 */
public class P29_SamAndSubstrings {

    public static int substrings(String s) {
        long MOD = 1_000_000_007L;
        long totalSum = 0;
        long endingSum = 0;

        for (int i = 0; i < s.length(); i++) {
            int digit = s.charAt(i) - '0';

            // Shift all previous substrings ending at (i-1) by x10, plus digit * count
            endingSum = (endingSum * 10 + (long) digit * (i + 1)) % MOD;
            totalSum = (totalSum + endingSum) % MOD;
        }

        return (int) totalSum;
    }

    public static void main(String[] args) {
        System.out.println("P29 Sam & Substrings '16':  " + substrings("16"));  // Expected: 23 (1 + 6 + 16)
        System.out.println("P29 Sam & Substrings '123': " + substrings("123")); // Expected: 164
    }
}