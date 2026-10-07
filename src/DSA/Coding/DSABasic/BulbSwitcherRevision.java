package DSA.Coding.DSABasic;

import java.util.stream.IntStream;

public class BulbSwitcherRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    There are `n` bulbs that are initially off. You first turn on all the bulbs, then 
    you turn off every second bulb. On the third round, you toggle every third bulb 
    (turning on if it's off or turning off if it's on). For the i-th round, you toggle 
    every i bulb. For the n-th round, you toggle only the last bulb.
    Return the number of bulbs that are on after n rounds.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: n = 3 [00:01:20]
    - Initial Status: [OFF, OFF, OFF]
    - Round 1 (Factor 1): Toggle all -> [ON, ON, ON]                  [00:01:24]
    - Round 2 (Factor 2): Toggle every 2nd -> [ON, OFF, ON]            [00:01:41]
    - Round 3 (Factor 3): Toggle every 3rd -> [ON, OFF, OFF]           [00:01:53]
    - Only 1 bulb stays ON (the 1st bulb).
    - Output: 1

    Example B: n = 15 [00:02:08]
    - Following all toggle sweeps, the bulbs that remain ON are positions: 1, 4, and 9 [00:05:17].
    - Notice that 1, 4, and 9 are all perfect squares: 1^2, 2^2, 3^2 [00:05:37].
    - Output: 3

    APPROACH 1: Iterative Perfect Square Tracking Loop (As shown in video)
    - Initialize `count = 0` and base root index pointer `i = 1` [00:09:34].
    - Run a loop checking if `i * i <= n` [00:09:42].
    - As long as the square is within range, increment `count` and move `i++` [00:09:59].
    - Returns `count`, which represents the absolute number of perfect squares within `n`.
    - TIME COMPLEXITY: O(sqrt(N)) - The loop runs exactly up to the square root boundary of N.
    - SPACE COMPLEXITY: O(1) - Constant state registers.

    LOGIC BEHIND THIS APPROACH:
    ----------------------------------------------------------------------------
    1. Factor Pair Invariant: Regular numbers have an even number of unique factors because 
       they map cleanly in pairs (e.g., 6 has 1*6 and 2*3). Toggling an even number of times 
       leaves the bulb in its original OFF state.
    2. Odd Factor Overlap: Perfect squares contain an odd number of unique factors because one 
       factor maps directly to itself (e.g., 9 has 1*9 and 3*3). Toggling an odd number of times 
       leaves the bulb in the ON state.
    3. Mathematical Simplification: Counting bulbs that remain ON simplifies to finding the 
       highest integer whose square is less than or equal to `n`.

    VISUAL DRY RUN (Video Strategy for n = 15):
    ----------------------------------------------------------------------------
    - Initial: count = 0, i = 1
    - Loop 1: i * i = 1 * 1 = 1 <= 15 -> count = 1, i updates to 2
    - Loop 2: i * i = 2 * 2 = 4 <= 15 -> count = 2, i updates to 3
    - Loop 3: i * i = 3 * 3 = 9 <= 15 -> count = 3, i updates to 4
    - Loop 4: i * i = 4 * 4 = 16 <= 15 -> False. Loop breaks.        [00:10:07]
    - Final Output: count = 3                                         [00:10:10]

    ================================================================================
    */
    public static int bulbSwitchVideo(int n) {
        int count = 0;
        int i = 1;

        // Trace perfect squares sequentially until out of bounds [00:09:42]
        while (i * i <= n) {
            count++;
            i++;
        }

        return count;
    }

    /*
    ================================================================================
    APPROACH 2: Optimized Direct Square Root Math Formula
    - Since counting perfect squares up to `n` matches the floor of the square root 
      of `n`, we can compute it instantly using standard library definitions.
    - TIME COMPLEXITY: O(1) - Calculated in a single native processor step.
    - SPACE COMPLEXITY: O(1) - Pure math abstraction.
    ================================================================================
    */
    public static int bulbSwitchMath(int n) {
        return (int) Math.sqrt(n);
    }

    /*
    ================================================================================
    APPROACH 3: Java Functional Streams Paradigm
    - We build an index sequence stream mapping potential factor roots from `1` up to `n`.
    - We filter for indices whose square is less than or equal to `n`, counting the 
      valid entries.
    - TIME COMPLEXITY: O(sqrt(N)) - Evaluated across matching bounds.
    - SPACE COMPLEXITY: O(1) - No array allocations needed.
    ================================================================================
    */
    public static long bulbSwitchStream(int n) {
        return IntStream.rangeClosed(1, n)
                        .takeWhile(i -> (long) i * i <= n)
                        .count();
    }

    private static void verifyAllApproaches(int n, int expected) {
        System.out.println("Input Bulb Count (n): " + n);
        System.out.println("Expected Bulb ON     : " + expected);
        System.out.println("1. Video Iterative   : " + bulbSwitchVideo(n));
        System.out.println("2. O(1) Math Formula : " + bulbSwitchMath(n));
        System.out.println("3. Functional Stream : " + bulbSwitchStream(n));
        
        boolean passed = (bulbSwitchVideo(n) == expected) && 
                         (bulbSwitchMath(n) == expected) && 
                         (bulbSwitchStream(n) == expected);
                         
        System.out.println("Status               : " + (passed ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 319: BULB SWITCHER ===\n");

        // Case 1: Video problem introduction sample [00:01:20]
        verifyAllApproaches(3, 1);

        // Case 2: In-depth verification tracing case from video logic [00:02:08]
        verifyAllApproaches(15, 3);

        // Case 3: Zero element boundary edge case
        verifyAllApproaches(0, 0);

        // Case 4: Perfect square matching exact bounds
        verifyAllApproaches(9, 3);
    }
}