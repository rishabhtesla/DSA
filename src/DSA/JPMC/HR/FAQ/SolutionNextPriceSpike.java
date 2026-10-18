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
 * HACKERRANK QUESTION: Days Until Next Price Spike
 * ============================================================================
 *
 * Problem Statement:
 * Given an array of integers prices representing daily closing asset prices,
 * return an array answer such that answer[i] is the number of days you have to
 * wait after the i-th day to get a price strictly greater than prices[i].
 *
 * If there is no future day for which prices[j] > prices[i], set answer[i] = 0.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the number of days.
 * - Next n lines: Each line contains prices[i].
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - 30 <= prices[i] <= 1000
 *
 * Example:
 * Input:
 *   8
 *   73
 *   74
 *   75
 *   71
 *   69
 *   72
 *   76
 *   73
 * Output:
 *   1 1 4 2 1 1 0 0
 *
 * ============================================================================
 * LOGIC & APPROACH (Monotonic Decreasing Stack):
 * ============================================================================
 * 1. Principle:
 *    Instead of looking forward from each element in O(n^2), maintain a stack
 *    storing indices of previous days that are still awaiting a higher price.
 *
 * 2. Traversal:
 *    At each day i with price = prices[i]:
 *      - While stack is not empty and prices[i] > prices[stack.peek()]:
 *          prevIndex = stack.pop()
 *          answer[prevIndex] = i - prevIndex
 *      - Push index i onto stack.
 *
 * 3. Default Values:
 *    Any indices left in the stack at the end have no warmer day ahead,
 *    and remain initialized to 0.
 *
 * Complexity:
 * - Time: O(n) — each index is pushed and popped at most once.
 * - Space: O(n) for the stack.
 *
 * ============================================================================
 * DRY RUN (prices = [73, 74, 75, 71, 69, 72]):
 * ============================================================================
 * i = 0 (73): stack = [0]
 * i = 1 (74): 74 > 73 -> pop 0, ans[0] = 1 - 0 = 1 | stack = [1]
 * i = 2 (75): 75 > 74 -> pop 1, ans[1] = 2 - 1 = 1 | stack = [2]
 * i = 3 (71): 71 < 75 -> stack = [2, 3]
 * i = 4 (69): 69 < 71 -> stack = [2, 3, 4]
 * i = 5 (72):
 *   - 72 > 69 -> pop 4, ans[4] = 5 - 4 = 1
 *   - 72 > 71 -> pop 3, ans[3] = 5 - 3 = 2
 *   - 72 < 75 -> stop popping
 *   - stack = [2, 5]
 * ============================================================================
 */
public class SolutionNextPriceSpike {

    public static List<Integer> dailyTemperatures(List<Integer> prices) {
        if (prices == null || prices.isEmpty()) {
            return new ArrayList<>();
        }

        int n = prices.size();
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            int currentPrice = prices.get(i);

            while (!stack.isEmpty() && currentPrice > prices.get(stack.peek())) {
                int prevDay = stack.pop();
                result[prevDay] = i - prevDay;
            }

            stack.push(i);
        }

        List<Integer> answer = new ArrayList<>(n);
        for (int days : result) {
            answer.add(days);
        }

        return answer;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Integer> prices = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            prices.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        List<Integer> result = dailyTemperatures(prices);
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + (i == result.size() - 1 ? "" : " "));
        }
        System.out.println();

        bufferedReader.close();
    }
}