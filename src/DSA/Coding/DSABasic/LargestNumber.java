package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * PROBLEM STATEMENT:
 * Given a list of non-negative integers 'nums', arrange them such that they form the largest number and return it.
 * Since the result may be very large, you need to return a string instead of an integer [00:00:23, 00:00:35].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [10, 2] [00:00:40]
 * - Combinations possible: "102" or "210" [00:00:55].
 * - Comparing both string combinations, "210" is larger [00:01:03].
 * - Result: "210"
 * 
 * Example 2: nums = [3, 30, 34, 5, 9] (Simulated inside the video explanation [00:01:19, 00:09:43])
 * - Process:
 *   - Convert individual numeric primitives to explicit String objects [00:02:49].
 *   - Evaluate pairs using customized tracking rules: `(s1, s2) -> (s1 + s2).compareTo(s2 + s1)` [00:08:35].
 *   - To compare "3" and "30": "330" vs "303" -> "3" is sorted as larger than "30" [00:05:59].
 *   - Sorting all items descending yields: ["9", "5", "34", "3", "30"] [00:10:15].
 *   - Concatenating everything results in "9534330" [00:10:33].
 * - Result: "9534330"
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Lexicographical Custom Compounding Comparator):
 * • Index Initialization: Create an auxiliary string array matching `nums.length` dimensions [00:07:15].
 * • Condition Boundaries: Sort elements using custom sorting metrics [00:08:29]. 
 *   If the maximum combined string starts with "0", return "0" immediately to bypass duplicate zeros [00:12:08].
 * • Operational Steps:
 *   1. Map integer array items directly to matching string representations [00:07:49].
 *   2. Call `Arrays.sort(array, (s1, s2) -> (s2 + s1).compareTo(s1 + s2))` to arrange elements descending [00:08:35].
 *   3. If the leading element after sorting is "0", handle the edge case by returning "0" [00:12:29].
 *   4. Concatenate strings sequentially using a dynamic `StringBuilder` [00:11:03].
 * • Time Complexity: O(n log n) - Governed entirely by sorting string permutations [00:01:35].
 * • Space Complexity: O(n) - Auxiliary storage required for individual string buffer copies [00:02:55].
 * • LOGIC BEHIND THIS APPROACH:
 *   Standard value comparison fails when sorting elements to create the largest concatenated number (e.g., 9 beats 34, but 34 beats 3) [00:01:30]. 
 *   To guarantee a globally optimal configuration, look at how pairs concatenate. If string combination `s2 + s1` is larger than `s1 + s2`, 
 *   it means `s2` must come before `s1` in the final output [00:05:59].
 * 
 * ---
 * VISUAL DRY RUN (nums = [3, 30, 34, 5, 9]):
 * String Conversion [00:02:49]: ["3", "30", "34", "5", "9"]
 * Sorting Evaluations [00:08:35]:
 * - Compare "3" and "30": "3" + "30" = "330", "30" + "3" = "303". "330" > "303" -> ["3", "30"]
 * - Compare "34" and "3": "34" + "3" = "343", "3" + "34" = "334". "343" > "334" -> ["34", "3"]
 * Complete Sorted Order (Descending Order): ["9", "5", "34", "3", "30"] [00:10:15]
 * Edge Check: leading element is "9" != "0", skip edge filter [00:12:29].
 * Accumulate String Builder: "9" -> "95" -> "9534" -> "95343" -> "9534330" [00:11:29].
 * Final returned String = "9534330".
 */
public class LargestNumber {

    // APPROACH 1: Lexicographical Custom Compounding Comparator (Anchor Strategy)
    public static String largestNumberOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return "";

        // Convert the input integers to string array format [00:07:15]
        String[] stringArray = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            stringArray[i] = String.valueOf(nums[i]); // In-place string typecast assignment [00:07:49]
        }

        // Sort the strings using a custom descending combination rule [00:08:29]
        Arrays.sort(stringArray, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String combination1 = s1 + s2;
                String combination2 = s2 + s1;
                // Reverse standard comparison to arrange elements in descending order
                return combination2.compareTo(combination1);
            }
        });

        // Edge case: if the largest element is "0", the entire number is zero [00:12:08]
        if (stringArray[0].equals("0")) {
            return "0";
        }

        // Combine sorted components sequentially [00:11:03]
        StringBuilder largestNumBuffer = new StringBuilder();
        for (String str : stringArray) {
            largestNumBuffer.append(str);
        }

        return largestNumBuffer.toString();
    }

    // APPROACH 2: QuickSort In-Place Custom Priority Strategy
    // Uses an in-place QuickSort implementation with custom string concatenation logic to sort the primitive 
    // numbers directly, avoiding the memory overhead of creating a separate String array.
    public static String largestNumberQuickSort(int[] nums) {
        if (nums == null || nums.length == 0) return "";
        
        performCustomQuickSort(nums, 0, nums.length - 1);
        
        if (nums[0] == 0) return "0";
        
        StringBuilder largestNumBuffer = new StringBuilder();
        for (int num : nums) {
            largestNumBuffer.append(num);
        }
        return largestNumBuffer.toString();
    }

    private static void performCustomQuickSort(int[] nums, int low, int high) {
        if (low >= high) return;
        int pivotIndex = partition(nums, low, high);
        performCustomQuickSort(nums, low, pivotIndex - 1);
        performCustomQuickSort(nums, pivotIndex + 1, high);
    }

    private static int partition(int[] nums, int low, int high) {
        int pivot = nums[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (shouldComeFirst(nums[j], pivot)) {
                i++;
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        int temp = nums[i + 1];
        nums[i + 1] = nums[high];
        nums[high] = temp;
        return i + 1;
    }

    private static boolean shouldComeFirst(int num1, int num2) {
        String comb1 = String.valueOf(num1) + String.valueOf(num2);
        String comb2 = String.valueOf(num2) + String.valueOf(num1);
        return comb1.compareTo(comb2) > 0; // Descending placement choice
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Stream mappings wrap data transformations cleanly inside single pipeline boundaries. 
    // This abstract layout adds execution overhead due to object allocation cascades and intermediate terminal allocations.
    public static String largestNumberStream(int[] nums) {
        if (nums == null || nums.length == 0) return "";

        String result = Arrays.stream(nums)
                .mapToObj(String::valueOf)
                .sorted((s1, s2) -> (s2 + s1).compareTo(s1 + s2))
                .collect(Collectors.joining());

        return result.startsWith("0") ? "0" : result;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Base Array) ---
        int[] test1 = {10, 2};
        int[] t1_1 = test1.clone();
        int[] t1_2 = test1.clone();
        int[] t1_3 = test1.clone();

        String res1_1 = largestNumberOptimal(t1_1);
        String res1_2 = largestNumberQuickSort(t1_2);
        String res1_3 = largestNumberStream(t1_3);

        System.out.println("Test Case 1: [10, 2]");
        System.out.println("Approach 1 (Custom Sort)  Result: " + res1_1);
        System.out.println("Approach 2 (QuickSort)    Result: " + res1_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res1_3);
        System.out.println("Verification: " + (res1_1.equals("210") && res1_2.equals("210") && res1_3.equals("210") ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {3, 30, 34, 5, 9};
        int[] t2_1 = test2.clone();
        int[] t2_2 = test2.clone();
        int[] t2_3 = test2.clone();

        String res2_1 = largestNumberOptimal(t2_1);
        String res2_2 = largestNumberQuickSort(t2_2);
        String res2_3 = largestNumberStream(t2_3);

        System.out.println("Test Case 2: [3, 30, 34, 5, 9]");
        System.out.println("Approach 1 (Custom Sort)  Result: " + res2_1);
        System.out.println("Approach 2 (QuickSort)    Result: " + res2_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res2_3);
        System.out.println("Verification: " + (res2_1.equals("9534330") && res2_2.equals("9534330") && res2_3.equals("9534330") ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Zero Bound Edge Constraints) ---
        int[] test3 = {0, 0, 0};
        int[] t3_1 = test3.clone();
        int[] t3_2 = test3.clone();
        int[] t3_3 = test3.clone();

        String res3_1 = largestNumberOptimal(t3_1);
        String res3_2 = largestNumberQuickSort(t3_2);
        String res3_3 = largestNumberStream(t3_3);

        System.out.println("Test Case 3: [0, 0, 0]");
        System.out.println("Approach 1 (Custom Sort)  Result: " + res3_1);
        System.out.println("Approach 2 (QuickSort)    Result: " + res3_2);
        System.out.println("Approach 3 (Stream API)   Result: " + res3_3);
        System.out.println("Verification: " + (res3_1.equals("0") && res3_2.equals("0") && res3_3.equals("0") ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}