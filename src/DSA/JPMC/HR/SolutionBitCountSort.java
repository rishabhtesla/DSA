package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION 4: Bit Count Sort
 * ============================================================================
 *
 * Problem Statement:
 * Given an array of integers, sort the integers in ascending order based on the
 * number of set bits (1s) in their binary representations.
 *
 * If two or more integers have the same number of set bits, sort them in ascending
 * order based on their decimal value.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the size of the array nums.
 * - Next n lines: Each line contains an integer nums[i].
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - 1 <= nums[i] <= 10^9
 *
 * Example:
 * Input:
 *   5
 *   5
 *   3
 *   7
 *   10
 *   14
 * Output:
 *   3
 *   5
 *   10
 *   7
 *   14
 *
 * Explanation:
 * - Binary representations & set bit counts:
 *     3  -> binary 0011 -> 2 set bits
 *     5  -> binary 0101 -> 2 set bits
 *     10 -> binary 1010 -> 2 set bits
 *     7  -> binary 0111 -> 3 set bits
 *     14 -> binary 1110 -> 3 set bits
 * - Sorted primarily by bit count:
 *     Numbers with 2 set bits: [3, 5, 10] (already in ascending decimal order)
 *     Numbers with 3 set bits: [7, 14] (already in ascending decimal order)
 * - Resulting ordered array: [3, 5, 10, 7, 14]
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Set Bit Calculation:
 *    Use Java's built-in `Integer.bitCount(int i)`, which runs in O(1) time
 *    using hardware-accelerated popcount bitwise arithmetic.
 *
 * 2. Comparator Formulation:
 *    Define a 2-tier ordering rule:
 *      Tier 1: Compare bit count of a vs bit count of b:
 *              `Integer.compare(Integer.bitCount(a), Integer.bitCount(b))`
 *      Tier 2: If bit counts are identical, tiebreak using natural decimal order:
 *              `Integer.compare(a, b)`
 *
 * 3. Sorting Mechanism:
 *    Sort using `Collections.sort()` (TimSort implementation).
 *
 * Complexity:
 * - Time: O(n log n) comparisons, each taking O(1) time.
 * - Space: O(n) to store and re-order elements.
 *
 * ============================================================================
 * DRY RUN (nums = [5, 3, 7, 10, 14]):
 * ============================================================================
 * Computed Metadata:
 * - num = 5  -> bitCount = 2
 * - num = 3  -> bitCount = 2
 * - num = 7  -> bitCount = 3
 * - num = 10 -> bitCount = 2
 * - num = 14 -> bitCount = 3
 *
 * Comparator Comparisons:
 * - Compare 5 and 3: same bits (2 == 2), 3 < 5 -> [3, 5]
 * - Compare 10 and 5: same bits (2 == 2), 5 < 10 -> [3, 5, 10]
 * - Compare 7 and 10: bits(7)=3 > bits(10)=2 -> 10 precedes 7 -> [3, 5, 10, 7]
 * - Compare 14 and 7: same bits (3 == 3), 7 < 14 -> [3, 5, 10, 7, 14]
 *
 * Final Ordered List: [3, 5, 10, 7, 14]
 * ============================================================================
 */
public class SolutionBitCountSort {

    public static List<Integer> sortByBitCount(List<Integer> nums) {
        if (nums == null || nums.size() <= 1) {
            return nums;
        }

        Collections.sort(nums, (a, b) -> {
            int countA = Integer.bitCount(a);
            int countB = Integer.bitCount(b);

            if (countA != countB) {
                return Integer.compare(countA, countB);
            }
            return Integer.compare(a, b);
        });

        return nums;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Integer> nums = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            nums.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        List<Integer> result = sortByBitCount(nums);
        for (int val : result) {
            System.out.println(val);
        }

        bufferedReader.close();
    }
}