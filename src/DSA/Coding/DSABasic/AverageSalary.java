package DSA.Coding.DSABasic;

import java.util.Arrays;

/**
 * PROBLEM STATEMENT:
 * Given an array of unique integers 'salary' where salary[i] is the salary of the i-th employee, 
 * return the average salary of employees excluding the minimum and maximum salary [00:00:18]. 
 * Answers within 10^-5 of the actual answer will be accepted [00:01:15].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: salary = [4000, 3000, 1000, 2000] [00:00:30]
 * - Minimum salary is 1000, Maximum salary is 4000 [00:00:47].
 * - Remaining elements after excluding min and max: [3000, 2000].
 * - Sum of remaining elements = 3000 + 2000 = 5000 [00:01:00].
 * - Average = 5000 / 2 = 2500.0 [00:01:08].
 * - Result: 2500.0
 * 
 * Example 2: salary = [400, 200, 100, 300, 600] (Simulated inside the video explanation [00:08:35])
 * - Process:
 *   - Initialize 'maxSalary', 'minSalary', and 'totalSum' to salary[0] = 400 [00:08:55].
 *   - Iterating through the array updates the values to: maxSalary = 600, minSalary = 100, totalSum = 1600 [00:10:50].
 *   - Exclude extremes from aggregate: filteredSum = 1600 - 100 - 600 = 900 [00:11:21].
 *   - The count of elements to divide by is `length - 2` = 5 - 2 = 3 [00:11:35].
 *   - Average = 900.0 / 3 = 300.0 [00:11:55].
 * - Result: 300.0
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Extreme Accumulation Pass):
 * • Index Initialization: Scanning variable 'i' iterates sequentially from index 1 to salary.length - 1 [00:05:29].
 * • Condition Boundaries: Primitives track the absolute global extremes (`minSalary`, `maxSalary`) alongside the overall mathematical sum [00:02:10].
 * • Operational Steps:
 *   1. Initialize `minSalary`, `maxSalary`, and `totalSum` using the first baseline element salary[0] [00:05:04].
 *   2. Iterate from index 1 forward, comparing and updating extremes while adding values to `totalSum` [00:05:37].
 *   3. Exclude the absolute extremes: `filteredSum = totalSum - minSalary - maxSalary` [00:07:10].
 *   4. Cast values to precision floats (`double`) and divide by `salary.length - 2` [00:07:22, 00:07:56].
 * • Time Complexity: O(n) - Resolves all state tracks inside a single linear array-scanning pass.
 * • Space Complexity: O(1) - Primitive tracking monitors are modified purely in-place.
 * • LOGIC BEHIND THIS APPROACH:
 *   Calculating the average requires finding the aggregate sum and dividing it by the net count of contributing elements [00:04:22]. 
 *   Instead of sorting the data, which would increase time complexity to O(n log n), tracking the absolute 
 *   minimum and maximum values allows us to exclude them from the sum at the end, maintaining linear efficiency [00:01:42].
 * 
 * ---
 * VISUAL DRY RUN (salary = [400, 200, 100, 300, 600]):
 * Initial: maxSalary = 400, minSalary = 400, totalSum = 400 [00:08:55]
 * i = 1 (val 200): 200 < minSalary -> minSalary = 200. totalSum = 400 + 200 = 600 [00:09:11].
 * i = 2 (val 100): 100 < minSalary -> minSalary = 100. totalSum = 600 + 100 = 700 [00:09:40].
 * i = 3 (val 300): No change to extremes. totalSum = 700 + 300 = 1000 [00:10:09].
 * i = 4 (val 600): 600 > maxSalary -> maxSalary = 600. totalSum = 1000 + 600 = 1600 [00:10:37].
 * Loop Termination. 
 * filteredSum = 1600 - 100 - 600 = 900 [00:11:21].
 * validElementsCount = 5 - 2 = 3 [00:11:35].
 * Return 900.0 / 3 = 300.0 [00:11:55].
 */
public class AverageSalary {

    // APPROACH 1: Extreme Accumulation Pass (Anchor Strategy)
    public static double averageOptimal(int[] salary) {
        if (salary == null || salary.length <= 2) return 0.0;
        
        int maxSalary = salary[0];
        int minSalary = salary[0];
        int totalSum = salary[0];
        
        // Accumulate entire numeric sum while updating extreme bounds concurrently [00:05:04]
        for (int i = 1; i < salary.length; i++) {
            if (salary[i] > maxSalary) {
                maxSalary = salary[i];
            }
            if (salary[i] < minSalary) {
                minSalary = salary[i];
            }
            totalSum += salary[i];
        }
        
        // Isolate sub-partition boundaries by pulling out extreme variables [00:07:10]
        int filteredSum = totalSum - minSalary - maxSalary;
        int validCount = salary.length - 2;
        
        // Explicitly typecast variables to double precision primitives to map fraction divisions safely [00:07:56]
        return (double) filteredSum / validCount;
    }

    // APPROACH 2: Structural Sorting Modification Strategy
    // Sorts the array using the native library's quicksort algorithm. 
    // This allows us to skip the first and last indices to find the average, resulting in an O(n log n) pattern.
    public static double averageSortingVariant(int[] salary) {
        if (salary == null || salary.length <= 2) return 0.0;
        
        Arrays.sort(salary);
        
        int totalSum = 0;
        // Step clear over indices 0 and length-1 directly
        for (int i = 1; i < salary.length - 1; i++) {
            totalSum += salary[i];
        }
        
        return (double) totalSum / (salary.length - 2);
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Relies on terminal collectors that perform linear mapping scans over stream pipelines. 
    // This introduces object-boxing overhead that impacts performance compared to in-place operations.
    public static double averageStream(int[] salary) {
        if (salary == null || salary.length <= 2) return 0.0;

        int maxSalary = Arrays.stream(salary).max().orElse(0);
        int minSalary = Arrays.stream(salary).min().orElse(0);

        return Arrays.stream(salary)
                .filter(s -> s != maxSalary && s != minSalary)
                .average()
                .orElse(0.0);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Base Elements) ---
        int[] test1 = {4000, 3000, 1000, 2000};
        double res1_1 = averageOptimal(test1);
        double res1_2 = averageSortingVariant(test1.clone());
        double res1_3 = averageStream(test1);

        System.out.println("Test Case 1: [4000, 3000, 1000, 2000]");
        System.out.println("Approach 1 (Optimal Pass) Result: " + res1_1);
        System.out.println("Approach 2 (Sort Variant) Result: " + res1_2);
        System.out.println("Approach 3 (Stream Layers) Result: " + res1_3);
        System.out.println("Verification: " + (Math.abs(res1_1 - 2500.0) < 1e-5 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {400, 200, 100, 300, 600};
        double res2_1 = averageOptimal(test2);
        double res2_2 = averageSortingVariant(test2.clone());
        double res2_3 = averageStream(test2);

        System.out.println("Test Case 2: [400, 200, 100, 300, 600]");
        System.out.println("Approach 1 (Optimal Pass) Result: " + res2_1);
        System.out.println("Approach 2 (Sort Variant) Result: " + res2_2);
        System.out.println("Approach 3 (Stream Layers) Result: " + res2_3);
        System.out.println("Verification: " + (Math.abs(res2_1 - 300.0) < 1e-5 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}