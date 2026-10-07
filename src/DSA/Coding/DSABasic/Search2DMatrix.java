package DSA.Coding.DSABasic;

import java.util.Arrays;

/**
 * PROBLEM STATEMENT:
 * You are given an m x n integer matrix 'matrix' with the following two properties:
 * 1. Each row is sorted in non-decreasing order [00:00:36].
 * 2. The first integer of each row is greater than the last integer of the previous row [00:00:42].
 * Given an integer 'target', return true if target is in matrix or false otherwise [00:01:11].
 * You must write a solution in O(log(m * n)) time complexity [00:01:21].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: matrix = [[1,  3,  5,  7],
 *                      [10, 11, 16, 20],
 *                      [23, 30, 34, 60]], target = 3 [00:00:50, 00:11:57]
 * - Start search at Top-Right Corner (row = 0, col = 3, val = 7) [00:02:18, 00:03:10]:
 *   - 7 > 3 (target is smaller): Move Left -> col-- (2, val = 5) [00:05:35, 00:10:39, 00:12:17].
 *   - 5 > 3 (target is smaller): Move Left -> col-- (1, val = 3) [00:12:49].
 *   - 3 == 3 (target found!): Return true [00:07:29, 00:10:08, 00:13:04].
 * - Result: true
 * 
 * Example 2: matrix = [[1,  3,  5,  7],
 *                      [10, 11, 16, 20],
 *                      [23, 30, 34, 60]], target = 13
 * - Top-Right (0, 3) = 7 < 13 -> Move Down -> row++ (1, 3, val = 20).
 * - (1, 3) = 20 > 13 -> Move Left -> col-- (1, 2, val = 16).
 * - (1, 2) = 16 > 13 -> Move Left -> col-- (1, 1, val = 11).
 * - (1, 1) = 11 < 13 -> Move Down -> row++ (2, 1, val = 30).
 * - (2, 1) = 30 > 13 -> Move Left -> col-- (2, 0, val = 23).
 * - (2, 0) = 23 > 13 -> Move Left -> col-- (2, -1, out of bounds!).
 * - Result: false
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Top-Right Corner Staircase Binary Search):
 * • Index Initialization:
 *   - Pointer `row = 0` (top row) [00:08:49].
 *   - Pointer `col = matrix[0].length - 1` (rightmost column) [00:08:57].
 * • Condition Boundaries:
 *   - Loop runs while `row < matrix.length` AND `col >= 0` [00:09:07, 00:09:24].
 *   - Found match: `if (matrix[row][col] == target)` -> return true [00:09:58, 00:10:08].
 *   - Current element > target: move left (`col--`) [00:10:20, 00:10:39].
 *   - Current element < target: move down (`row++`) [00:10:46, 00:10:50].
 * • Operational Steps:
 *   1. Initialize `row = 0` and `col = cols - 1` [00:08:49, 00:08:57].
 *   2. Evaluate cell `matrix[row][col]` inside boundary while loop [00:09:07].
 *   3. If value matches target, return true [00:10:08].
 *   4. If cell > target, decrement `col--` (eliminates current column) [00:10:39].
 *   5. If cell < target, increment `row++` (eliminates current row) [00:10:50].
 *   6. Return false if loop terminates (pointer out of matrix grid boundaries) [00:11:17].
 * • Time Complexity: O(m + n) - Staircase step traversal checks at most m rows + n columns [00:02:44].
 * • Space Complexity: O(1) auxiliary space - Uses primitive scalar pointer coordinates.
 * • LOGIC BEHIND THIS APPROACH:
 *   The top-right corner `(0, n-1)` serves as a natural decision pivot [00:02:18, 00:03:36]. 
 *   It represents the LARGEST element in row 0 and SMALLEST element in column `n-1` [00:03:36]. 
 *   Comparing against target allows instant elimination of either an entire row or an entire column [00:03:46, 00:04:17].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: Staircase Top-Right - matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3):
 * Initial: row = 0, col = 3 (matrix[0][3] = 7) [00:08:49, 00:12:05]
 * Step 1: 7 > 3 (target is smaller) -> col-- -> col = 2 (matrix[0][2] = 5) [00:12:17]
 * Step 2: 5 > 3 (target is smaller) -> col-- -> col = 1 (matrix[0][1] = 3) [00:12:49]
 * Step 3: 3 == 3 -> Target Match Found! Return true [00:13:04].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Flattened 1D Binary Search - target = 16, m=3, n=4):
 * Low = 0, High = 3*4 - 1 = 11
 * Step 1: Mid = (0 + 11)/2 = 5 -> Row = 5/4 = 1, Col = 5%4 = 1 -> matrix[1][1] = 11 < 16 -> Low = 6
 * Step 2: Mid = (6 + 11)/2 = 8 -> Row = 8/4 = 2, Col = 8%4 = 0 -> matrix[2][0] = 23 > 16 -> High = 7
 * Step 3: Mid = (6 + 7)/2  = 6 -> Row = 6/4 = 1, Col = 6%4 = 2 -> matrix[1][2] = 16 == 16 -> Found!
 * Return true.
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 3: Stream FlatMap Search Pipeline - target = 3):
 * Stream Stage 1: Arrays.stream(matrix).flatMapToInt(Arrays::stream) -> Stream[1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]
 * Stream Stage 2: anyMatch(x -> x == 3) -> true.
 * Output = true.
 */
public class Search2DMatrix {

    // APPROACH 1: Top-Right Corner Staircase Binary Search (Anchor Strategy)
    public static boolean searchMatrixOptimal(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

        int row = 0;                          // Start at top row [00:08:49]
        int col = matrix[0].length - 1;      // Start at rightmost column [00:08:57]

        // Staircase traversal loop bounded by grid dimensions [00:09:07]
        while (row < matrix.length && col >= 0) {
            
            // Match found [00:09:58]
            if (matrix[row][col] == target) {
                return true; // Target exists in matrix [00:10:08]
            } 
            // Target is smaller -> eliminate current rightmost column [00:10:20]
            else if (matrix[row][col] > target) {
                col--; // Move left [00:10:39]
            } 
            // Target is larger -> eliminate current top row [00:10:46]
            else {
                row++; // Move down [00:10:50]
            }
        }

        return false; // Target not present in matrix [00:11:17]
    }

    // APPROACH 2: Flattened 1D Index Binary Search (Strict O(log(m * n)) Standard)
    // Treats the 2D matrix as a contiguous 1D array of length (m * n).
    // Calculates 2D coordinates dynamically using `row = mid / n` and `col = mid % n`.
    public static boolean searchMatrixFlattenedBS(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

        int m = matrix.length;
        int n = matrix[0].length;

        int low = 0;
        int high = m * n - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midVal = matrix[mid / n][mid % n]; // Map 1D index to 2D matrix cell

            if (midVal == target) {
                return true;
            } else if (midVal < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return false;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Flattens matrix into an IntStream pipeline. 
    // Suffers from O(m * n) linear time complexity, but offers declarative readability.
    public static boolean searchMatrixStream(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

        return Arrays.stream(matrix)
                .flatMapToInt(Arrays::stream)
                .anyMatch(val -> val == target);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        int[][] testMatrix = {
            {1,  3,  5,  7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };

        // --- TEST CASE 1 (Target Present) ---
        int target1 = 3;
        boolean res1_1 = searchMatrixOptimal(testMatrix, target1);
        boolean res1_2 = searchMatrixFlattenedBS(testMatrix, target1);
        boolean res1_3 = searchMatrixStream(testMatrix, target1);

        System.out.println("Test Case 1: Target = 3");
        System.out.println("Approach 1 (Top-Right Search) Result: " + res1_1);
        System.out.println("Approach 2 (1D Flattened BS) Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)       Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 && res1_2 && res1_3 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Target Absent) ---
        int target2 = 13;
        boolean res2_1 = searchMatrixOptimal(testMatrix, target2);
        boolean res2_2 = searchMatrixFlattenedBS(testMatrix, target2);
        boolean res2_3 = searchMatrixStream(testMatrix, target2);

        System.out.println("Test Case 2: Target = 13");
        System.out.println("Approach 1 (Top-Right Search) Result: " + res2_1);
        System.out.println("Approach 2 (1D Flattened BS) Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)       Result: " + res2_3);
        System.out.println("Verification: " + (!res2_1 && !res2_2 && !res2_3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}