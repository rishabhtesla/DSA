package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * Given a positive integer 'n', generate an n x n matrix filled with elements from 1 to n^2 
 * in spiral order [00:00:22, 00:00:36].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: n = 3 [00:01:06]
 * - Matrix size: 3 x 3, elements 1 to 3^2 = 9 [00:01:11].
 * - Fill outer boundary in clockwise order [00:01:44]:
 *   - Top row (left -> right): 1, 2, 3 [00:05:37]
 *   - Right column (top -> bottom): 4, 5 [00:07:20]
 *   - Bottom row (right -> left): 6, 7 [00:08:25]
 *   - Left column (bottom -> top): 8 [00:09:18]
 * - Shrink boundary and fill remaining inner cell [00:09:46, 00:12:10]:
 *   - Center cell: 9
 * - Result: [[1, 2, 3],
 *            [8, 9, 4],
 *            [7, 6, 5]]
 * 
 * Example 2: n = 1
 * - Matrix size: 1 x 1, elements 1 to 1^2 = 1.
 * - Result: [[1]]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Four Boundary Pointers with Wall Shrinking):
 * • Index Initialization:
 *   - Define boundaries: `minRow = 0`, `maxRow = n - 1`, `minCol = 0`, `maxCol = n - 1` [00:03:19, 00:14:56].
 *   - Main counter `count = 1` increments up to `n * n` [00:13:52, 00:13:59].
 * • Condition Boundaries:
 *   - Outer loop drives forward while `count <= n * n` [00:13:59].
 *   - Top wall loop: `for (c = minCol; c <= maxCol; c++)` with `minRow` fixed [00:04:30, 00:16:28].
 *   - Right wall loop: `for (r = minRow + 1; r <= maxRow; r++)` with `maxCol` fixed [00:06:15, 00:18:05].
 *   - Bottom wall loop: `for (c = maxCol - 1; c >= minCol; c--)` with `maxRow` fixed [00:07:50, 00:20:14].
 *   - Left wall loop: `for (r = maxRow - 1; r >= minRow + 1; r--)` with `minCol` fixed [00:08:46, 00:22:09].
 *   - Shrink boundary walls inward after each 4-wall cycle: `minCol++`, `maxCol--`, `minRow++`, `maxRow--` [00:09:46, 00:22:50].
 * • Operational Steps:
 *   1. Instantiate `n x n` result array `arr` and counters `count = 1` [00:13:37].
 *   2. Setup 4 tracking boundaries: `minRow`, `maxRow`, `minCol`, `maxCol` [00:14:56].
 *   3. Execute 4 direction loops inside outer `while (count <= n * n)` loop [00:13:59]:
 *      - Fill top row left-to-right [00:16:28].
 *      - Fill right col top-to-bottom [00:18:05].
 *      - Fill bottom row right-to-left [00:20:14].
 *      - Fill left col bottom-to-top [00:22:09].
 *   4. Shrink boundaries inward [00:22:50].
 *   5. Return `arr` when matrix is completely filled [00:23:38].
 * • Time Complexity: O(n^2) - Every single cell in the n x n matrix is visited and written to exactly once.
 * • Space Complexity: O(1) auxiliary space (excluding output matrix) - Uses fixed scalar boundary pointers.
 * • LOGIC BEHIND THIS APPROACH:
 *   Matrix traversal cannot be handled in a single continuous loop because directions change at corners [00:02:12, 00:02:34]. 
 *   Decomposing spiral fill into 4 distinct wall iterations (top, right, bottom, left) maintains clear index control [00:02:47]. 
 *   Shrinking row/col boundaries after each full layer step advances processing seamlessly to the next nested inner sub-matrix [00:09:46, 00:12:10].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Four Boundary Pointers - n = 3, n*n = 9):
 * Initial: count = 1, minRow = 0, maxRow = 2, minCol = 0, maxCol = 2 [00:25:57]
 * 
 * Layer 1 (Outer Ring):
 * Loop 1 (Top: minRow = 0, c from 0 to 2) [00:16:28]:
 *   arr[0][0] = 1, arr[0][1] = 2, arr[0][2] = 3. count becomes 4.
 * Loop 2 (Right: maxCol = 2, r from 1 to 2) [00:18:05]:
 *   arr[1][2] = 4, arr[2][2] = 5. count becomes 6.
 * Loop 3 (Bottom: maxRow = 2, c from 1 down to 0) [00:20:14]:
 *   arr[2][1] = 6, arr[2][0] = 7. count becomes 8.
 * Loop 4 (Left: minCol = 0, r from 1 down to 1) [00:22:09]:
 *   arr[1][0] = 8. count becomes 9.
 * 
 * Shrink Boundaries [00:22:50]: minCol = 1, maxCol = 1, minRow = 1, maxRow = 1.
 * 
 * Layer 2 (Center Cell):
 * Loop 1 (Top: minRow = 1, c from 1 to 1):
 *   arr[1][1] = 9. count becomes 10.
 * Outer while loop terminates (count 10 > 9) [00:31:55].
 * Result = [[1, 2, 3], [8, 9, 4], [7, 6, 5]].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Direction Vectors Strategy - n = 2):
 * Directions: dr = [0, 1, 0, -1], dc = [1, 0, -1, 0] (Right, Down, Left, Up)
 * Matrix: 2x2. Fill 1 to 4:
 * r=0, c=0, d=0: arr[0][0] = 1 -> next (0,1)
 * r=0, c=1, d=0: arr[0][1] = 2 -> next (0,2) out of bounds -> turn d=1 (Down)
 * r=1, c=1, d=1: arr[1][1] = 3 -> next (2,1) out of bounds -> turn d=2 (Left)
 * r=1, c=0, d=2: arr[1][0] = 4 -> complete.
 * Output = [[1, 2], [4, 3]].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Functional Stream Grid Generator - n = 2):
 * Precompute coordinate to spiral index mapping:
 * IntStream 1 to 4 mapped into 2D array grid by simulate-walk collector.
 * Output = [[1, 2], [4, 3]].
 */
public class SpiralMatrixII {

    // APPROACH 1: Four Boundary Pointers with Wall Shrinking (Anchor Strategy)
    public static int[][] generateMatrixOptimal(int n) {
        if (n <= 0) return new int[0][0];

        int[][] arr = new int[n][n]; // Allocate n x n matrix [00:13:37]
        int count = 1;               // Number counter starts at 1 [00:13:52]

        // Initialize 4 matrix boundaries [00:14:56]
        int minRow = 0;
        int maxRow = n - 1;
        int minCol = 0;
        int maxCol = n - 1;

        // Loop until n*n values are filled [00:13:59]
        while (count <= n * n) {
            
            // Wall 1: Top row (left to right) [00:16:28]
            for (int c = minCol; c <= maxCol; c++) {
                arr[minRow][c] = count++;
            }

            // Wall 2: Right column (top + 1 to bottom) [00:18:05]
            for (int r = minRow + 1; r <= maxRow; r++) {
                arr[r][maxCol] = count++;
            }

            // Wall 3: Bottom row (right - 1 down to left) [00:20:14]
            for (int c = maxCol - 1; c >= minCol; c--) {
                arr[maxRow][c] = count++;
            }

            // Wall 4: Left column (bottom - 1 up to top + 1) [00:22:09]
            for (int r = maxRow - 1; r >= minRow + 1; r--) {
                arr[r][minCol] = count++;
            }

            // Shrink all boundaries inward for next spiral layer [00:22:50]
            minCol++;
            maxCol--;
            minRow++;
            maxRow--;
        }

        return arr; // Return spiral populated matrix [00:23:38]
    }

    // APPROACH 2: Direction Vectors Simulation Strategy
    // Moves step-by-step using direction offsets (Right, Down, Left, Up).
    // Turns clockwise whenever moving out of bounds or into an already visited non-zero cell.
    public static int[][] generateMatrixDirectionVectors(int n) {
        if (n <= 0) return new int[0][0];

        int[][] matrix = new int[n][n];
        
        // Direction vectors: Right, Down, Left, Up
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        
        int r = 0, c = 0, dir = 0;

        for (int val = 1; val <= n * n; val++) {
            matrix[r][c] = val;

            // Calculate next cell coordinates
            int nextR = r + dr[dir];
            int nextC = c + dc[dir];

            // Turn clockwise if next cell is out of bounds or already filled
            if (nextR < 0 || nextR >= n || nextC < 0 || nextC >= n || matrix[nextR][nextC] != 0) {
                dir = (dir + 1) % 4; // Turn clockwise
                nextR = r + dr[dir];
                nextC = c + dc[dir];
            }

            r = nextR;
            c = nextC;
        }

        return matrix;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Uses Stream sequence generation to populate pre-allocated matrix. 
    // Boxing values adds minor allocation overhead, but showcases clean functional iteration.
    public static int[][] generateMatrixStream(int n) {
        if (n <= 0) return new int[0][0];

        int[][] result = new int[n][n];
        int minRow = 0, maxRow = n - 1, minCol = 0, maxCol = n - 1;

        // Functional stream generating values 1 to n^2 in order
        Iterator<Integer> numbers = IntStream.rangeClosed(1, n * n).boxed().iterator();

        while (numbers.hasNext()) {
            for (int c = minCol; c <= maxCol && numbers.hasNext(); c++) {
                result[minRow][c] = numbers.next();
            }
            for (int r = minRow + 1; r <= maxRow && numbers.hasNext(); r++) {
                result[r][maxCol] = numbers.next();
            }
            for (int c = maxCol - 1; c >= minCol && numbers.hasNext(); c--) {
                result[maxRow][c] = numbers.next();
            }
            for (int r = maxRow - 1; r >= minRow + 1 && numbers.hasNext(); r--) {
                result[r][minCol] = numbers.next();
            }

            minRow++;
            maxRow--;
            minCol++;
            maxCol--;
        }

        return result;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (n = 3) ---
        int n1 = 3;
        int[][] res1_1 = generateMatrixOptimal(n1);
        int[][] res1_2 = generateMatrixDirectionVectors(n1);
        int[][] res1_3 = generateMatrixStream(n1);

        System.out.println("Test Case 1: n = 3");
        System.out.println("Approach 1 (Boundary Wall) Result: " + Arrays.deepToString(res1_1));
        System.out.println("Approach 2 (Dir Vector)    Result: " + Arrays.deepToString(res1_2));
        System.out.println("Approach 3 (Stream API)    Result: " + Arrays.deepToString(res1_3));
        boolean check1 = Arrays.deepEquals(res1_1, res1_2) && Arrays.deepEquals(res1_2, res1_3);
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (n = 1) ---
        int n2 = 1;
        int[][] res2_1 = generateMatrixOptimal(n2);
        int[][] res2_2 = generateMatrixDirectionVectors(n2);
        int[][] res2_3 = generateMatrixStream(n2);

        System.out.println("Test Case 2: n = 1");
        System.out.println("Approach 1 (Boundary Wall) Result: " + Arrays.deepToString(res2_1));
        System.out.println("Approach 2 (Dir Vector)    Result: " + Arrays.deepToString(res2_2));
        System.out.println("Approach 3 (Stream API)    Result: " + Arrays.deepToString(res2_3));
        boolean check2 = Arrays.deepEquals(res2_1, res2_2) && Arrays.deepEquals(res2_2, res2_3);
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (n = 4) ---
        int n3 = 4;
        int[][] res3_1 = generateMatrixOptimal(n3);
        int[][] res3_2 = generateMatrixDirectionVectors(n3);
        int[][] res3_3 = generateMatrixStream(n3);

        System.out.println("Test Case 3: n = 4");
        System.out.println("Approach 1 (Boundary Wall) Result: " + Arrays.deepToString(res3_1));
        System.out.println("Approach 2 (Dir Vector)    Result: " + Arrays.deepToString(res3_2));
        System.out.println("Approach 3 (Stream API)    Result: " + Arrays.deepToString(res3_3));
        boolean check3 = Arrays.deepEquals(res3_1, res3_2) && Arrays.deepEquals(res3_2, res3_3);
        System.out.println("Verification: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}