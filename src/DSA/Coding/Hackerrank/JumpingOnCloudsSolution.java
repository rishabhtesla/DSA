package DSA.Coding.Hackerrank;

import java.util.Scanner;

/**
 * ============================================================================
 * 1. PROBLEM STATEMENT (HackerRank: "Jumping on the Clouds")
 * ============================================================================
 * Emma is playing a mobile game with consecutively numbered clouds starting at 0.
 * Some clouds are safe cumulus clouds (marked as 0) and others are thunderheads 
 * that must be avoided (marked as 1).
 *
 * Rules:
 *   - Emma starts at cloud 0.
 *   - From index i, Emma can jump either to (i + 1) or (i + 2).
 *   - Emma must only land on safe clouds (c[target] == 0).
 *   - It is guaranteed that the game is always winnable.
 *
 * Goal:
 *   Determine the MINIMUM number of jumps Emma needs to reach the last cloud 
 *   (index c.length - 1).
 *
 * Example 1:
 *   c = [0, 0, 1, 0, 0, 1, 0] (length 7)
 *   Indices: 0 -> 1 -> 3 -> 4 -> 6
 *   Path: 
 *     - Jump 0 -> 1 (since index 2 is 1/unsafe)
 *     - Jump 1 -> 3 (index 3 is safe, 2 steps)
 *     - Jump 3 -> 4 (index 5 is 1/unsafe)
 *     - Jump 4 -> 6 (index 6 is safe, 2 steps)
 *   Total jumps = 4.
 *
 * Example 2:
 *   c = [0, 0, 0, 0, 1, 0] (length 6)
 *   Path:
 *     - Jump 0 -> 2 (2 steps)
 *     - Jump 2 -> 3 (index 4 is 1/unsafe, take 1 step)
 *     - Jump 3 -> 5 (2 steps)
 *   Total jumps = 3.
 *
 * Constraints:
 *   - 2 <= n <= 100
 *   - c[i] is either 0 or 1
 *   - c[0] = c[n - 1] = 0 (Start and end are always safe)
 *
 * ============================================================================
 * 2. LOGIC & GREEDY STRATEGY
 * ============================================================================
 * To MINIMIZE the total number of jumps, Emma should always be greedy:
 *   - Try to jump 2 steps (i + 2) whenever possible.
 *   - Only jump 1 step (i + 1) if jumping 2 steps would land on a thunderhead (c[i + 2] == 1) 
 *     or overshoot beyond the end of the array (i + 2 >= c.length).
 *
 * --- Why initialize count to -1? ---
 * In an iteration running `for (int i = 0; i < len; ...)`, when Emma reaches the 
 * last cloud (i == len - 1), the condition `i < len` is still true. The loop body 
 * executes once more and advances `i` past the end of the array, triggering one 
 * extraneous jump count. Setting `count = -1` ahead of time perfectly cancels out 
 * that extra +1 jump at the finish line.
 *
 * --- How the Code Compresses into a One-Liner ---
 * 1. Base Logic:
 *      if (i + 2 < len && c[i + 2] == 0) { i += 2; }
 *      else { i += 1; }
 *      count++;
 *
 * 2. Remove 'else':
 *      Every valid iteration moves by AT LEAST 1 step. 
 *      So, if the 2-step jump condition is met, advance `i++` inside the `if`,
 *      and perform another `i++` outside the `if` unconditionally.
 *
 * 3. Move updates into the for-loop increment section:
 *      for (int i = 0; i < len - 1; i++, count++) { ... }
 *      Because a for-loop allows multiple statements separated by commas in its 
 *      step clause, the body reduces to a single conditional ternary or `i++`.
 * ============================================================================
 */
public class JumpingOnCloudsSolution {

    /**
     * Version 1: Readable Standard Greedy Implementation
     * Time Complexity:  O(n) - Single pass through the clouds
     * Space Complexity: O(1) - In-place counters
     */
    public static int jumpingOnCloudsStandard(int[] c) {
        int jumps = 0;
        int i = 0;
        int len = c.length;

        while (i < len - 1) {
            // Greedy choice: If 2 steps ahead is within bounds and safe, take it
            if (i + 2 < len && c[i + 2] == 0) {
                i += 2;
            } else {
                i += 1;
            }
            jumps++;
        }
        

        return jumps;
    }

    /**
     * Version 2: JAVAAID's Compressed Idiomatic Java Loop
     * Time Complexity:  O(n)
     * Space Complexity: O(1)
     */
    public static int jumpingOnCloudsCompact(int[] c) {

            
        int count = -1;
        for (int i = 0; i < c.length; i++, count++) {
            if (i + 2 < c.length && c[i + 2] == 0) {
                i++; // Adds second step when 2-step jump is available
            }
        }
        return count;
    }

    /**
     * Version 3: The HackerRank One-Liner (as shown in the video)
     * Time Complexity:  O(n)
     * Space Complexity: O(1)
     */
    public static int jumpingOnCloudsOneLiner(int[] c) {
        int count = -1;
        for (int i = 0; i < c.length; i += (i + 2 < c.length && c[i + 2] == 0) ? 2 : 1, count++);
        return count;
    }

    // Driver method demonstrating test cases
    public static void main(String[] args) {
        // Test case 1: c = [0, 0, 1, 0, 0, 1, 0] -> expected 4
        int[] test1 = {0, 0, 1, 0, 0, 1, 0};
        System.out.println("Test Case 1 (Expected: 4):");
        System.out.println("  Standard : " + jumpingOnCloudsStandard(test1));
        System.out.println("  Compact  : " + jumpingOnCloudsCompact(test1));
        System.out.println("  One-Liner: " + jumpingOnCloudsOneLiner(test1));

        // Test case 2: c = [0, 0, 0, 0, 1, 0] -> expected 3
        int[] test2 = {0, 0, 0, 0, 1, 0};
        System.out.println("\nTest Case 2 (Expected: 3):");
        System.out.println("  Standard : " + jumpingOnCloudsStandard(test2));
        System.out.println("  Compact  : " + jumpingOnCloudsCompact(test2));
        System.out.println("  One-Liner: " + jumpingOnCloudsOneLiner(test2));

        // Interactive Testing
        System.out.println("\n--- Run Custom Test ---");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of clouds (n): ");
        if (sc.hasNextInt()) {
            int n = sc.nextInt();
            int[] clouds = new int[n];
            System.out.print("Enter " + n + " cloud values (0 or 1 separated by space): ");
            for (int i = 0; i < n; i++) {
                clouds[i] = sc.nextInt();
            }
            System.out.println("Minimum jumps needed: " + jumpingOnCloudsStandard(clouds));
        }
        sc.close();
    }
}