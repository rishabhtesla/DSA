package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * ============================================================================
 * HACKERRANK QUESTION 1: Character Reprogramming
 * ============================================================================
 *
 * Problem Statement:
 * An automated rover on a Cartesian grid moves based on a string of instructions
 * s containing characters:
 *   - 'U': move (x, y + 1)
 *   - 'D': move (x, y - 1)
 *   - 'L': move (x - 1, y)
 *   - 'R': move (x + 1, y)
 *
 * Starting from coordinate (0, 0), the rover executes all moves in order to
 * reach its destination (destX, destY).
 *
 * You want to optimize the rover's memory by deleting as many instructions as
 * possible such that if the remaining instructions are executed in order, the
 * rover STILL terminates at the exact same final position (destX, destY).
 *
 * Find the maximum number of instructions that can be deleted.
 *
 * Input Format for Custom Testing:
 * - Line 1: String s, denoting the instructions.
 *
 * Constraints:
 * - 1 <= s.length() <= 10^5
 * - s contains only 'U', 'D', 'L', 'R'
 *
 * Example:
 * Input:
 *   URDR
 * Output:
 *   2
 * Explanation:
 * - Net displacement: U(+1 Y), R(+1 X), D(-1 Y), R(+1 X) -> Final (2, 0).
 * - Minimum moves to reach (2, 0) from (0, 0) is 2 moves ('R', 'R').
 * - Deletions allowed = total moves (4) - minimum required (2) = 2.
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Count occurrences of each direction: U, D, L, R.
 * 2. Calculate net coordinate displacement from (0, 0):
 *    - netX = |R - L|
 *    - netY = |U - D|
 * 3. Any pair of opposing moves ('U' and 'D', or 'L' and 'R') cancel each
 *    other's contribution to the net displacement.
 * 4. The minimum number of steps strictly required to reach (destX, destY)
 *    is Manhattan distance = netX + netY.
 * 5. Maximum instructions to remove = total length - (netX + netY).
 *
 * Complexity:
 * - Time: O(n) single pass
 * - Space: O(1) auxiliary space
 *
 * ============================================================================
 * DRY RUN (s = "URDR"):
 * ============================================================================
 * Characters:
 * - 'U' -> count U = 1
 * - 'R' -> count R = 1
 * - 'D' -> count D = 1
 * - 'R' -> count R = 2
 *
 * Net calculations:
 * - netY = |1 - 1| = 0
 * - netX = |2 - 0| = 2
 * - minMoves = 0 + 2 = 2
 * - maxDeletions = 4 - 2 = 2
 *
 * Output: 2
 * ============================================================================
 */
public class SolutionCharacterReprogramming {

    public static int getMaxDeletions(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int u = 0, d = 0, l = 0, r = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == 'U') u++;
            else if (ch == 'D') d++;
            else if (ch == 'L') l++;
            else if (ch == 'R') r++;
        }

        int netX = Math.abs(r - l);
        int netY = Math.abs(u - d);
        int minRequired = netX + netY;

        return s.length() - minRequired;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferedReader.readLine();
        if (s != null) {
            System.out.println(getMaxDeletions(s.trim()));
        }
        bufferedReader.close();
    }
}