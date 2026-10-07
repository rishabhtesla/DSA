package DSA.Coding.Hackerrank;

import java.util.Scanner;

/**
 * ============================================================================
 * 1. PROBLEM STATEMENT (HackerRank: "Utopian Tree")
 * ============================================================================
 * The Utopian Tree goes through 2 growth cycles each year:
 *   - Each Spring, it doubles in height (height = height * 2).
 *   - Each Summer, its height increases by 1 meter (height = height + 1).
 *
 * A sapling is planted at the onset of spring with an initial height of 1 meter.
 * Given an integer 'n' representing the number of growth cycles that have passed, 
 * find and print the height of the tree.
 *
 * Example Growth Progression:
 *   - Cycle 0 (Planting):           Height = 1
 *   - Cycle 1 (Spring - Doubles):   Height = 1 * 2 = 2
 *   - Cycle 2 (Summer - +1):        Height = 2 + 1 = 3
 *   - Cycle 3 (Spring - Doubles):   Height = 3 * 2 = 6
 *   - Cycle 4 (Summer - +1):        Height = 6 + 1 = 7
 *   - Cycle 5 (Spring - Doubles):   Height = 7 * 2 = 14
 *
 * Input Format:
 *   - First line: T (number of test cases)
 *   - Next T lines: Integer n (number of cycles for each test case)
 *
 * Constraints:
 *   - 1 <= T <= 10
 *   - 0 <= n <= 60
 *
 * ============================================================================
 * 2. LOGIC & MATHEMATICAL DERIVATION
 * ============================================================================
 *
 * --- APPROACH 1: Simulation (O(n) Time, O(1) Space) ---
 *   Start with height = 1.
 *   Iterate from cycle 1 to n:
 *     - If cycle is odd (Spring): height *= 2
 *     - If cycle is even (Summer): height += 1
 *
 * --- APPROACH 2: Bitwise / Mathematical One-Liner (O(1) Time, O(1) Space) ---
 *   Notice the pattern of heights at even cycles (n = 0, 2, 4, 6...):
 *     n = 0  -> 1  = 2^1 - 1  (binary: 0001)
 *     n = 2  -> 3  = 2^2 - 1  (binary: 0011)
 *     n = 4  -> 7  = 2^3 - 1  (binary: 0111)
 *     n = 6  -> 15 = 2^4 - 1  (binary: 1111)
 *
 *   General formula for even n:
 *     height = 2^((n / 2) + 1) - 1
 *     Using bit shift: (1 << ((n >> 1) + 1)) - 1
 *
 *   Notice what happens when n is odd (n = 1, 3, 5...):
 *     The tree doubles after the preceding even cycle:
 *     n = 1  -> 2  = (2^2 - 1) - 1 = 3 - 1
 *     n = 3  -> 6  = (2^3 - 1) - 1 = 7 - 1
 *     n = 5  -> 14 = (2^4 - 1) - 1 = 15 - 1
 *
 *   Unified One-Liner Formula:
 *     If we evaluate (1 << ((n >> 1) + 1)) - 1:
 *       - When n is even: this exact value is the answer.
 *       - When n is odd:  subtract 1 from the power of two expansion, or
 *         shift by ((n + 1) / 2) and adjust:
 *
 *     Formula: ((1 << ((n >> 1) + 1)) - 1) - (n & 1)
 *     Or alternatively:
 *       n is even: ~(~1 << (n / 2))
 *       unified:   (1 << ((n + 2) / 2)) - 1 - (n % 2)
 * ============================================================================
 */
public class UtopianTreeSolution {

    /**
     * Approach 1: Iterative Simulation
     * Time Complexity:  O(n)
     * Space Complexity: O(1)
     */
    public static int utopianTreeIterative(int n) {
        int height = 1;
        for (int cycle = 1; cycle <= n; cycle++) {
            if (cycle % 2 != 0) {
                // Spring: tree doubles
                height *= 2;
            } else {
                // Summer: tree grows by 1
                height += 1;
            }
        }
        return height;
    }

    /**
     * Approach 2: Optimal Bitwise One-Liner
     * Time Complexity:  O(1)
     * Space Complexity: O(1)
     */
    public static int utopianTreeOptimal(int n) {
        // (1 << ((n >> 1) + 1)) computes 2^(floor(n/2) + 1)
        // Subtract 1 gives the all-ones bitmask for even cycles
        // Subtract (n & 1) accounts for odd cycles where spring just doubled
        return ((1 << ((n >> 1) + 1)) - 1) - (n & 1);
    }

    // Driver method demonstrating test cases
    public static void main(String[] args) {
        int[] testCases = {0, 1, 2, 3, 4, 5, 6};

        System.out.println("Cycle | Iterative | Bitwise (O(1))");
        System.out.println("----------------------------------");
        for (int n : testCases) {
            int ansIterative = utopianTreeIterative(n);
            int ansOptimal = utopianTreeOptimal(n);
            System.out.printf("  %-4d|    %-7d|    %-7d%n", n, ansIterative, ansOptimal);
        }

        // Custom console testing
        System.out.println("\n--- Test with custom input ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of cycles (n): ");
        if (scanner.hasNextInt()) {
            int customN = scanner.nextInt();
            System.out.println("Height after " + customN + " cycles: " + utopianTreeOptimal(customN));
        }
        scanner.close();
    }
}