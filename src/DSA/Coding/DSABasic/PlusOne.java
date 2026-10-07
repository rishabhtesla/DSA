package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PROBLEM STATEMENT:
 * You are given a large integer represented as an integer array 'digits', where each digits[i] is the i-th 
 * digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. 
 * The large integer does not contain any leading 0's [00:00:22, 00:02:19].
 * Increment the large integer by one and return the resulting array of digits [00:00:16, 00:00:39].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: digits = [1, 2, 3] [00:00:30]
 * - Process: The array represents 123. Incrementing by one results in 124 [00:00:39].
 * - Result: [1, 2, 4] [00:00:46]
 * 
 * Example 2: digits = [9, 9, 9] (Simulated inside the video explanation [00:04:13, 00:12:34])
 * - Process:
 *   - The least significant digit is 9. 9 + 1 = 10, so it resets to 0 and carries over 1 [00:04:18].
 *   - The next digit is also 9, which resets to 0 due to the carry-over [00:04:22].
 *   - The most significant digit is 9, which resets to 0 as well [00:04:26].
 *   - Since all digits have cascaded into 0, the overall structural boundary expands. A new array of size `n + 1` 
 *     is allocated, initializing the leading slot to 1 [00:04:34, 00:09:24].
 * - Result: [1, 0, 0, 0] [00:13:21]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Reverse Cascade Addition):
 * • Index Initialization: Scanning index variable 'i' initializes at the trailing slot `digits.length - 1` [00:05:06, 00:06:44].
 * • Condition Boundaries: The loop travels backward while `i >= 0` to simulate right-to-left addition carry behaviors [00:06:51].
 * • Operational Steps:
 *   1. Evaluate the digit at index 'i'. If it is strictly less than 9, increment `digits[i]++` and return the array immediately [00:05:25, 00:07:13].
 *   2. If the digit equals 9, overwrite `digits[i] = 0` and allow the loop to step backward to simulate carry-over propagation [00:06:18, 00:08:40].
 *   3. If the loop completes without early termination, all digits were 9s. Create a new array of size `n + 1`, set index 0 to 1, and return it [00:09:24].
 * • Time Complexity: O(n) - Single backward pass through the array.
 * • Space Complexity: O(1) - Modifies the array in-place, except for the all-9s worst-case which uses O(n) space [00:04:34].
 * • LOGIC BEHIND THIS APPROACH:
 *   When adding 1 to a number, a change only propagates past a digit if it triggers a carry-over [00:03:51]. 
 *   In base-10 arithmetic, only a 9 rolls over into a 0 and passes a carry of 1 to its left neighbor [00:03:05]. 
 *   As soon as a non-9 digit is encountered, it absorbs the carry completely, allowing us to terminate the loop early [00:03:56, 00:07:54].
 * 
 * ---
 * VISUAL DRY RUN (digits = [9, 9, 9]):
 * Initial: digits = [9, 9, 9], length n = 3 [00:12:43]
 * Pass 1: i = 2 -> digits[2] == 9 -> Overwrite digits[2] = 0. Carry propagates left [00:05:59, 00:12:58].
 * Pass 2: i = 1 -> digits[1] == 9 -> Overwrite digits[1] = 0. Carry propagates left [00:13:04].
 * Pass 3: i = 0 -> digits[0] == 9 -> Overwrite digits[0] = 0. Loop terminates [00:13:08].
 * Post-Loop Allocation: All components are evaluated. Create new array `result` of size 4 [00:09:24].
 * Set `result[0] = 1`. Trailing elements default to 0 natively. Output: [1, 0, 0, 0] [00:13:21].
 */
public class PlusOne {

    // APPROACH 1: Reverse Cascade Addition (Anchor Strategy)
    public static int[] plusOneOptimal(int[] digits) {
        if (digits == null || digits.length == 0) return new int[]{1};
        
        int n = digits.length;
        
        // Loop backward from the least significant digit toward the most significant digit [00:06:44]
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits; // Early termination if no carry rolls over [00:05:25, 00:07:29]
            }
            // Overwrite 9 with 0 to let the carry cascade to the left [00:06:18, 00:08:40]
            digits[i] = 0;
        }
        
        // Handle the edge case where all digits were 9s (e.g., [9, 9, 9] -> [1, 0, 0, 0]) [00:04:13, 00:09:24]
        int[] result = new int[n + 1];
        result[0] = 1; // Leading element becomes 1, trailing components default to 0 [00:09:33, 00:13:17]
        return result;
    }

    // APPROACH 2: Explicit Boolean Carry Flag Tracking
    // Simulates an adder circuit with an explicit carry tracker variable. 
    // This serves as an alternative structural design pattern for backward additions.
    public static int[] plusOneWithCarryFlag(int[] digits) {
        if (digits == null || digits.length == 0) return new int[]{1};
        
        int n = digits.length;
        int carry = 1; // Initialize with 1 to represent the addition step
        
        for (int i = n - 1; i >= 0 && carry > 0; i--) {
            int total = digits[i] + carry;
            digits[i] = total % 10;
            carry = total / 10;
        }
        
        if (carry > 0) {
            int[] result = new int[n + 1];
            result[0] = carry;
            System.arraycopy(digits, 0, result, 1, n);
            return result;
        }
        
        return digits;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Forcing stream allocations over stateful collections introduces large 
    // memory overhead due to intermediate object boxing, transforming an O(1) space optimization into O(n).
    public static int[] plusOneStream(int[] digits) {
        if (digits == null || digits.length == 0) return new int[]{1};

        List<Integer> digitList = Arrays.stream(digits).boxed().collect(Collectors.toList());
        int carry = 1;
        
        for (int i = digitList.size() - 1; i >= 0; i--) {
            int total = digitList.get(i) + carry;
            digitList.set(i, total % 10);
            carry = total / 10;
        }
        
        if (carry > 0) {
            digitList.add(0, carry);
        }
        
        return digitList.stream().mapToInt(Integer::intValue).toArray();
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Base Increment) ---
        int[] test1_base = {1, 2, 3};
        int[] t1_1 = test1_base.clone();
        int[] t1_2 = test1_base.clone();
        int[] t1_3 = test1_base.clone();

        int[] res1_1 = plusOneOptimal(t1_1);
        int[] res1_2 = plusOneWithCarryFlag(t1_2);
        int[] res1_3 = plusOneStream(t1_3);

        System.out.println("Test Case 1: [1, 2, 3]");
        System.out.println("Approach 1 (Reverse Cascade) Result: " + Arrays.toString(res1_1));
        System.out.println("Approach 2 (Carry Flag)     Result: " + Arrays.toString(res1_2));
        System.out.println("Approach 3 (Stream Pipeline) Result: " + Arrays.toString(res1_3));
        boolean check1 = Arrays.equals(res1_1, new int[]{1, 2, 4});
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation - All 9s Boundary) ---
        int[] test2_base = {9, 9, 9};
        int[] t2_1 = test2_base.clone();
        int[] t2_2 = test2_base.clone();
        int[] t2_3 = test2_base.clone();

        int[] res2_1 = plusOneOptimal(t2_1);
        int[] res2_2 = plusOneWithCarryFlag(t2_2);
        int[] res2_3 = plusOneStream(t2_3);

        System.out.println("Test Case 2: [9, 9, 9]");
        System.out.println("Approach 1 (Reverse Cascade) Result: " + Arrays.toString(res2_1));
        System.out.println("Approach 2 (Carry Flag)     Result: " + Arrays.toString(res2_2));
        System.out.println("Approach 3 (Stream Pipeline) Result: " + Arrays.toString(res2_3));
        boolean check2 = Arrays.equals(res2_1, new int[]{1, 0, 0, 0});
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Partial Cascading Carry Over) ---
        int[] test3_base = {4, 5, 2, 9};
        int[] t3_1 = test3_base.clone();
        int[] t3_2 = test3_base.clone();
        int[] t3_3 = test3_base.clone();

        int[] res3_1 = plusOneOptimal(t3_1);
        int[] res3_2 = plusOneWithCarryFlag(t3_2);
        int[] res3_3 = plusOneStream(t3_3);

        System.out.println("Test Case 3: [4, 5, 2, 9]");
        System.out.println("Approach 1 (Reverse Cascade) Result: " + Arrays.toString(res3_1));
        System.out.println("Approach 2 (Carry Flag)     Result: " + Arrays.toString(res3_2));
        System.out.println("Approach 3 (Stream Pipeline) Result: " + Arrays.toString(res3_3));
        boolean check3 = Arrays.equals(res3_1, new int[]{4, 5, 3, 0});
        System.out.println("Verification: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}