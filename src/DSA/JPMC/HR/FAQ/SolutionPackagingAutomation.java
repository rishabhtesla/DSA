package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Packaging Automation / Maximum Final Element
 * ============================================================================
 *
 * Problem Statement:
 * An automated packaging and distribution facility arranges items in groups.
 * You are given an array of integers representing the number of items in each group.
 *
 * To optimize packaging, you must modify the groups so that they satisfy the
 * following operational conditions:
 *   1. The first group must contain exactly 1 item.
 *   2. The absolute difference between adjacent groups cannot exceed 1. That is:
 *      arr[i] - arr[i - 1] <= 1 for all 1 <= i < n.
 *
 * You are allowed to:
 *   - Rearrange the groups in any order.
 *   - Decrease the number of items in any group to any positive integer.
 *   - You CANNOT increase the number of items in any group.
 *
 * Return the maximum possible number of items in the final group (i.e., maximize arr[n - 1]).
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the size of the array items.
 * - Next n lines: Each line contains an integer items[i].
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - 1 <= items[i] <= 10^9
 *
 * Example:
 * Input:
 *   4
 *   3
 *   1
 *   3
 *   4
 * Output:
 *   4
 *
 * Explanation:
 * Initial array: [3, 1, 3, 4]
 * 1. Sort ascending: [1, 3, 3, 4]
 * 2. arr[0] is already 1.
 * 3. arr[1] is 3. Max allowed is arr[0] + 1 = 2 -> reduce to 2. Array: [1, 2, 3, 4]
 * 4. arr[2] is 3. Max allowed is arr[1] + 1 = 3 -> valid.
 * 5. arr[3] is 4. Max allowed is arr[2] + 1 = 4 -> valid.
 * The final element is 4.
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Sorting:
 *    Sort the array in ascending order. Placing smaller elements first allows
 *    larger elements later in the sequence to build up to their maximum possible
 *    ceiling.
 *
 * 2. Greedy Adjustment:
 *    - Force the first element `currentMax = 1`.
 *    - For every subsequent element `val` at index 1 to n - 1:
 *        - If `val > currentMax`, we can increment the staircase by 1:
 *          `currentMax = currentMax + 1`.
 *        - If `val <= currentMax`, the value cannot support a higher step, so
 *          `currentMax` remains unchanged.
 *    - Return `currentMax`.
 *
 * Complexity:
 * - Time: O(n log n) due to sorting.
 * - Space: O(1) auxiliary space (excluding input storage).
 *
 * ============================================================================
 * DRY RUN (items = [3, 1, 3, 4]):
 * ============================================================================
 * Sorted: [1, 3, 3, 4]
 * i = 0: val = 1 -> currentMax = 1
 * i = 1: val = 3 -> 3 > 1 -> currentMax = 1 + 1 = 2
 * i = 2: val = 3 -> 3 > 2 -> currentMax = 2 + 1 = 3
 * i = 3: val = 4 -> 4 > 3 -> currentMax = 3 + 1 = 4
 *
 * Final Result: 4
 * ============================================================================
 */
public class SolutionPackagingAutomation {

    public static int getMaxFinalElement(List<Integer> items) {
        if (items == null || items.isEmpty()) {
            return 0;
        }

        Collections.sort(items);

        int currentMax = 1;

        for (int i = 1; i < items.size(); i++) {
            if (items.get(i) > currentMax) {
                currentMax++;
            }
        }

        return currentMax;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Integer> items = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            items.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        int result = getMaxFinalElement(items);
        System.out.println(result);

        bufferedReader.close();
    }
}