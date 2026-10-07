package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * PROBLEM STATEMENT:
 * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise) [00:00:23].
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. 
 * DO NOT allocate another 2D matrix to do the rotation [00:00:59, 00:01:18].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: matrix = [[1, 2, 3],
 *                      [4, 5, 6],
 *                      [7, 8, 9]] [00:02:24]
 * - Phase 1: Transpose Matrix (Swap along main diagonal where j starts from i) [00:02:06, 00:12:07]:
 *   - Swap (0,1) with (1,0): 2 <-> 4 [00:09:03]
 *   - Swap (0,2) with (2,0): 3 <-> 7 [00:09:08]
 *   - Swap (1,2) with (2,1): 6 <-> 8 [00:09:34]
 *   - Transposed Matrix: [[1, 4, 7],
 *                         [2, 5, 8],
 *                         [3, 6, 9]] [00:03:00, 00:03:15]
 * 
 * - Phase 2: Reverse Each Row (Two-pointer swap per row array) [00:02:18, 00:03:25, 00:19:28]:
 *   - Row 0: [1, 4, 7] -> [7, 4, 1] [00:03:31]
 *   - Row 1: [2, 5, 8] -> [8, 5, 2] [00:03:31]
 *   - Row 2: [3, 6, 9] -> [9, 6, 3] [00:03:31]
 * - Result: [[7, 4, 1],
 *            [8, 5, 2],
 *            [9, 6, 3]] [00:03:35]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - In-Place Transpose + Row-Wise Reversal):
 * • Index Initialization:
 *   - Dimension tracker `n = matrix.length` [00:11:36].
 *   - Outer loop 'i' iterates rows 0 to n - 1 [00:11:57].
 *   - Inner loop 'j' iterates columns starting at `j = i` up to n - 1 (upper triangle only) [00:12:07].
 * • Condition Boundaries:
 *   - Transpose swap loop: `j = i` prevents double swapping back to original layout [00:10:14, 00:12:07].
 *   - Row reverse loop: `start = 0, end = arr.length - 1` while `start <= end` [00:19:35, 00:19:44].
 * • Operational Steps:
 *   1. Step 1: Transpose matrix in-place. Loop `i` from 0 to `n-1`, `j` from `i` to `n-1` [00:12:07].
 *   2. Swap `matrix[i][j]` with `matrix[j][i]` using temporary variable `k` [00:12:22, 00:12:47].
 *   3. Step 2: Reverse each row array. Loop `i` from 0 to `n-1` [00:20:34].
 *   4. Pass row array `matrix[i]` to helper method `reverse()` [00:20:53, 00:20:58].
 *   5. Inside `reverse()`, swap elements at `start` and `end` pointers, moving inward (`start++`, `end--`) [00:19:56, 00:20:14].
 * • Time Complexity: O(n^2) - Transpose takes n^2/2 swaps, row reversals take n^2/2 swaps. Overall linear relative to total elements.
 * • Space Complexity: O(1) auxiliary space - All transformations modified strictly in-place [00:01:12].
 * • LOGIC BEHIND THIS APPROACH:
 *   Rotating a grid 90 degrees clockwise directly is mathematically equivalent to two simpler transformations [00:01:52]:
 *   1. Matrix Transposition (`matrix[i][j] <-> matrix[j][i]`): Flips matrix over main diagonal, turning rows into columns [00:02:37, 00:02:46].
 *   2. Row Reversal (horizontal flip): Flips each row horizontally, bringing elements into clockwise 90-degree position [00:03:25].
 *   Starting `j` from `i` in transposition restricts swapping to the upper triangle to avoid undoing swaps [00:10:14, 00:10:59].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: In-Place Transpose + Reverse - matrix = [[1, 2], [3, 4]]):
 * Input: [[1, 2], [3, 4]], n = 2
 * Phase 1: Transpose (j starts from i):
 * - i=0, j=0: swap matrix[0][0] with matrix[0][0] (1 <-> 1) -> [[1, 2], [3, 4]]
 * - i=0, j=1: swap matrix[0][1] with matrix[1][0] (2 <-> 3) -> [[1, 3], [2, 4]] [00:15:15]
 * - i=1, j=1: swap matrix[1][1] with matrix[1][1] (4 <-> 4) -> [[1, 3], [2, 4]]
 * Matrix after Transpose = [[1, 3], [2, 4]]
 * 
 * Phase 2: Reverse Each Row:
 * - Row 0 [1, 3]: start=0 (1), end=1 (3) -> swap -> [3, 1] [00:23:40]
 * - Row 1 [2, 4]: start=0 (2), end=1 (4) -> swap -> [4, 2]
 * Final Result = [[3, 1], [4, 2]].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Four-Corner Ring Swap - matrix = [[1, 2], [3, 4]], n = 2):
 * Ring i = 0 (outermost ring):
 * - top-left = matrix[0][0] (1)
 * - matrix[0][0] = matrix[1][0] (3)
 * - matrix[1][0] = matrix[1][1] (4)
 * - matrix[1][1] = matrix[0][1] (2)
 * - matrix[0][1] = temp (1)
 * Matrix = [[3, 1], [4, 2]].
 * Output = [[3, 1], [4, 2]].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream Matrix Transformation Pipeline - matrix = [[1, 2], [3, 4]]):
 * Transpose Pipeline: Map columns j into rows -> [[1, 3], [2, 4]]
 * Reverse Pipeline: Map each row -> reverse order -> [[3, 1], [4, 2]]
 * Copy back to source matrix in-place.
 * Output = [[3, 1], [4, 2]].
 */
public class RotateImage {

    // APPROACH 1: In-Place Transpose + Row-Wise Reversal (Anchor Strategy)
    public static void rotateOptimal(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;

        int n = matrix.length; // Grid dimension [00:11:36]

        // STEP 1: Transpose Matrix in-place [00:02:06, 00:03:57]
        for (int i = 0; i < n; i++) {
            // j starts from i to only process upper triangle [00:10:14, 00:12:07]
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];         // Save matrix[i][j] [00:12:22]
                matrix[i][j] = matrix[j][i];     // Assign swapped transpose element [00:12:29]
                matrix[j][i] = temp;             // Assign stored element [00:12:47]
            }
        }

        // STEP 2: Reverse each individual row array [00:02:18, 00:03:59]
        for (int i = 0; i < n; i++) {
            reverseRow(matrix[i]); // Pass single row array to helper [00:20:53, 00:20:58]
        }
    }

    // Helper method to reverse a 1D primitive array in-place using two-pointers [00:19:26]
    private static void reverseRow(int[] arr) {
        int start = 0;                   // Left pointer [00:19:35]
        int end = arr.length - 1;        // Right pointer [00:19:44]

        while (start <= end) {
            int temp = arr[start];       // Save left element [00:19:58]
            arr[start] = arr[end];       // Move right element to left [00:20:07]
            arr[end] = temp;             // Move saved left element to right [00:20:12]

            start++; // Advance left pointer [00:20:14]
            end--;   // Retreat right pointer [00:20:14]
        }
    }

    // APPROACH 2: Four-Corner Ring Rotation Strategy (Direct In-Place Rotation)
    // Rotates pixel positions directly layer-by-layer (ring by ring) from outside to inside, 
    // performing 4-way cyclical swaps without intermediate transposition.
    public static void rotateFourCorners(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;

        int n = matrix.length;

        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = matrix[i][j];

                // Move Bottom-Left to Top-Left
                matrix[i][j] = matrix[n - 1 - j][i];

                // Move Bottom-Right to Bottom-Left
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];

                // Move Top-Right to Bottom-Right
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];

                // Assign stored Top-Left to Top-Right
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Constructing rotated arrays via streams allocates secondary 
    // memory, which is copied back to the input array to fulfill in-place requirements.
    public static void rotateStream(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;

        int n = matrix.length;

        // Build 90-degree rotated copy via functional streams
        int[][] rotated = IntStream.range(0, n)
                .mapToObj(i -> IntStream.range(0, n)
                        .map(j -> matrix[n - 1 - j][i])
                        .toArray())
                .toArray(int[][]::new);

        // Copy rotated values back to input matrix in-place
        for (int i = 0; i < n; i++) {
            System.arraycopy(rotated[i], 0, matrix[i], 0, n);
        }
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (3x3 Matrix from Video Explanation) ---
        int[][] test1_1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int[][] test1_2 = cloneMatrix(test1_1);
        int[][] test1_3 = cloneMatrix(test1_1);

        rotateOptimal(test1_1);
        rotateFourCorners(test1_2);
        rotateStream(test1_3);

        System.out.println("Test Case 1: 3x3 Matrix");
        System.out.println("Approach 1 (Transpose+Reverse) Result: " + Arrays.deepToString(test1_1));
        System.out.println("Approach 2 (4-Corner Swap)    Result: " + Arrays.deepToString(test1_2));
        System.out.println("Approach 3 (Stream API)        Result: " + Arrays.deepToString(test1_3));
        boolean check1 = Arrays.deepEquals(test1_1, test1_2) && Arrays.deepEquals(test1_2, test1_3);
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (4x4 Matrix) ---
        int[][] test2_1 = {
            { 5,  1,  9, 11},
            { 2,  4,  8, 10},
            {13,  3,  6,  7},
            {15, 14, 12, 16}
        };
        int[][] test2_2 = cloneMatrix(test2_1);
        int[][] test2_3 = cloneMatrix(test2_1);

        rotateOptimal(test2_1);
        rotateFourCorners(test2_2);
        rotateStream(test2_3);

        System.out.println("Test Case 2: 4x4 Matrix");
        System.out.println("Approach 1 (Transpose+Reverse) Result: " + Arrays.deepToString(test2_1));
        System.out.println("Approach 2 (4-Corner Swap)    Result: " + Arrays.deepToString(test2_2));
        System.out.println("Approach 3 (Stream API)        Result: " + Arrays.deepToString(test2_3));
        boolean check2 = Arrays.deepEquals(test2_1, test2_2) && Arrays.deepEquals(test2_2, test2_3);
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }

    // Helper method to clone 2D matrix
    private static int[][] cloneMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }
}