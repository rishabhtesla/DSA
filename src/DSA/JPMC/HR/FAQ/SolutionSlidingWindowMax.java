package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Maximum Volume in Moving Window
 * ============================================================================
 *
 * Problem Statement:
 * A trading surveillance engine tracks trade volumes over time in an array `volumes`.
 * Given an integer window size k, calculate the peak trade volume inside each
 * contiguous sliding window of size k moving from left to right.
 *
 * Return an array of size (n - k + 1) containing the maximum volume for each window.
 * An O(n) solution is expected.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, the size of volumes.
 * - Next n lines: Each line contains an integer volumes[i].
 * - Last line: An integer k, the window size.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - 1 <= k <= n
 * - -10^4 <= volumes[i] <= 10^4
 *
 * Example:
 * Input:
 *   8
 *   1
 *   3
 *   -1
 *   -3
 *   5
 *   3
 *   6
 *   7
 *   3
 * Output:
 *   3 3 5 5 6 7
 *
 * ============================================================================
 * LOGIC & APPROACH (Monotonic Decreasing Deque):
 * ============================================================================
 * 1. Monotonic Deque Invariant:
 *    Maintain an `ArrayDeque<Integer>` storing indices such that the values
 *    represented by those indices are strictly in decreasing order.
 *
 * 2. Window Traversal:
 *    At each step i:
 *      a. Evict Stale Indices:
 *         If deque.peekFirst() <= i - k, the index is outside the window of size k;
 *         remove it using pollFirst().
 *      b. Maintain Monotonicity:
 *         While deque is not empty and volumes[deque.peekLast()] <= volumes[i],
 *         pollLast() because those smaller elements can never be the maximum
 *         within this window or any future window that contains index i.
 *      c. Push Current Index:
 *         deque.offerLast(i).
 *      d. Record Window Maximum:
 *         Once i >= k - 1, the front of the deque (`deque.peekFirst()`) holds
 *         the index of the maximum element for window ending at i.
 *
 * Complexity:
 * - Time: O(n) — each element index is added and removed from the deque at most once.
 * - Space: O(k) for the monotonic deque.
 *
 * ============================================================================
 * DRY RUN (volumes = [1, 3, -1, -3, 5], k = 3):
 * ============================================================================
 * i = 0: val = 1  | deque = [0 (val 1)]
 * i = 1: val = 3  | 3 > 1 -> pop index 0 | deque = [1 (val 3)]
 * i = 2: val = -1 | -1 < 3 -> append     | deque = [1 (val 3), 2 (val -1)]
 *        i >= 2 -> Window [1, 3, -1] Max = volumes[1] = 3
 * i = 3: val = -3 | -3 < -1 -> append    | deque = [1, 2, 3]
 *        i >= 2 -> Window [3, -1, -3] Max = volumes[1] = 3
 * i = 4: val = 5  | stale check: 1 <= 4-3=1 -> pop index 1!
 *        5 > -3, 5 > -1 -> pop indices 3 and 2!
 *        deque = [4 (val 5)]
 *        i >= 2 -> Window [-1, -3, 5] Max = volumes[4] = 5
 *
 * Result: [3, 3, 5]
 * ============================================================================
 */
public class SolutionSlidingWindowMax {

    public static List<Integer> maxSlidingWindow(List<Integer> volumes, int k) {
        if (volumes == null || volumes.isEmpty() || k <= 0) {
            return new ArrayList<>();
        }

        int n = volumes.size();
        List<Integer> result = new ArrayList<>(n - k + 1);
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Remove indices outside the current sliding window
            if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // Remove smaller elements as they are dominated by volumes[i]
            int currentVal = volumes.get(i);
            while (!deque.isEmpty() && volumes.get(deque.peekLast()) <= currentVal) {
                deque.pollLast();
            }

            deque.offerLast(i);

            // Record maximum once window reaches size k
            if (i >= k - 1) {
                result.add(volumes.get(deque.peekFirst()));
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Integer> volumes = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            volumes.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> result = maxSlidingWindow(volumes, k);
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + (i == result.size() - 1 ? "" : " "));
        }
        System.out.println();

        bufferedReader.close();
    }
}