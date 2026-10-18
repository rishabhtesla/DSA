package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * ============================================================================
 * HACKERRANK QUESTION 8: Balanced Audit Log Transactions
 * ============================================================================
 *
 * Problem Statement:
 * A transaction log records open and close audit checkpoints represented as
 * '(' for transaction start and ')' for transaction end.
 *
 * A log string is balanced if:
 * 1. It is the empty string, or
 * 2. It can be written as AB (A concatenated with B), where A and B are valid, or
 * 3. It can be written as (A), where A is valid.
 *
 * You are given a transaction log string s. In one move, you can insert a
 * '(' or ')' at any position.
 *
 * Return the minimum number of insertions required to make the log balanced.
 *
 * Input Format for Custom Testing:
 * - Line 1: String s.
 *
 * Constraints:
 * - 1 <= s.length() <= 10^5
 * - s consists only of '(' and ')'
 *
 * Example 1:
 * Input:
 *   ())
 * Output:
 *   1
 * Explanation: Insert '(' at index 0 to produce "()()".
 *
 * Example 2:
 * Input:
 *   (((
 * Output:
 *   3
 * Explanation: Insert three ')' at the end to produce "((()))".
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Balance Variable:
 *    Track `openCount` representing unmatched '(' observed so far.
 *    Track `insertionsNeeded` for unmatched ')' encountered without any prior '('.
 *
 * 2. Linear Scan:
 *    - For character '(':
 *        increment `openCount++`.
 *    - For character ')':
 *        - If `openCount > 0`, it matches an open bracket: decrement `openCount--`.
 *        - If `openCount == 0`, no open bracket exists to close this:
 *          we must insert a '(' before it: increment `insertionsNeeded++`.
 *
 * 3. Final Reconciliation:
 *    Any remaining unmatched '(' (`openCount`) will need corresponding ')'
 *    inserted at the end.
 *    Total insertions required = `insertionsNeeded + openCount`.
 *
 * Complexity:
 * - Time: O(n) single pass
 * - Space: O(1) auxiliary space (no stack required)
 *
 * ============================================================================
 * DRY RUN (s = "())"):
 * ============================================================================
 * Initial: openCount = 0, insertionsNeeded = 0
 *
 * i = 0: ch = '(' -> openCount = 1, insertionsNeeded = 0
 * i = 1: ch = ')' -> openCount > 0 -> matched! openCount = 0, insertionsNeeded = 0
 * i = 2: ch = ')' -> openCount == 0 -> unmatched close! insertionsNeeded = 1
 *
 * End of traversal:
 * Total required = insertionsNeeded (1) + openCount (0) = 1.
 * Output: 1
 * ============================================================================
 */
public class SolutionBalancedAuditLogs {

    public static int minInsertionsToBalance(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int openCount = 0;
        int insertionsNeeded = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                openCount++;
            } else if (ch == ')') {
                if (openCount > 0) {
                    openCount--;
                } else {
                    insertionsNeeded++;
                }
            }
        }

        return insertionsNeeded + openCount;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String line = bufferedReader.readLine();
        if (line != null) {
            System.out.println(minInsertionsToBalance(line.trim()));
        }

        bufferedReader.close();
    }
}