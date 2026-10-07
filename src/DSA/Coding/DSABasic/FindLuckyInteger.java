package DSA.Coding.DSABasic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * PROBLEM STATEMENT:
 * Given an array of integers 'arr', a lucky integer is an integer that has a frequency in the array 
 * equal to its value [00:00:17]. Return the largest lucky integer in the array. If there is no lucky 
 * integer, return -1 [00:00:30].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: arr = [2, 2, 3, 4] [00:00:42]
 * - Frequencies: 2 appears 2 times, 3 appears 1 time, 4 appears 1 time.
 * - Eligible Lucky Integers: 2 (value 2 equals frequency 2).
 * - Result: 2
 * 
 * Example 2: arr = [1, 2, 2, 3, 3, 3] (Simulated inside the video explanation [00:01:00])
 * - Frequencies: 1 appears 1 time, 2 appears 2 times, 3 appears 3 times.
 * - Eligible Lucky Integers: 1, 2, 3 (all values match their frequencies).
 * - Max lucky integer is 3.
 * - Result: 3
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - HashMap Frequency Mapping):
 * • Index Initialization: Iterate over the array elements using a typical loop variable or an enhanced for-loop [00:03:26].
 * • Condition Boundaries: Scan the map entry key-set. If `map.get(key) == key`, update `ans = max(ans, key)` [00:05:43].
 * • Operational Steps:
 *   1. Initialize `HashMap<Integer, Integer>` to track mapping metrics [00:03:06].
 *   2. Populate counts by evaluating `map.put(num, map.getOrDefault(num, 0) + 1)` [00:04:12].
 *   3. Iterate over the `keySet()` to locate matches where element value == recorded count [00:04:56].
 *   4. Filter and capture `ans = Math.max(ans, key)`. Return `ans` (defaulted to -1) [00:04:46].
 * • Time Complexity: O(n) - Single pass to count occurrences, and a bound-capped pass over keys.
 * • Space Complexity: O(n) - Auxiliary space used by the tracking collection structure.
 * • LOGIC BEHIND THIS APPROACH:
 *   When computing arbitrary object occurrences within unordered arrays, associative containers match dynamic allocations perfectly [00:01:54]. 
 *   The unique-key mapping constraint naturally aligns elements against their aggregate totals, allowing simple linear retrieval [00:02:16].
 * 
 * ---
 * VISUAL DRY RUN (arr = [1, 2, 2, 3, 4, 6, 3]):
 * Map Construction Pass:
 * Element 1 -> Map: {1=1}
 * Element 2 -> Map: {1=1, 2=1}
 * Element 2 -> Map: {1=1, 2=2}
 * Element 3 -> Map: {1=1, 2=2, 3=1}
 * Element 4 -> Map: {1=1, 2=2, 3=1, 4=1}
 * Element 6 -> Map: {1=1, 2=2, 3=1, 4=1, 6=1}
 * Element 3 -> Map: {1=1, 2=2, 3=2, 4=1, 6=1} [00:08:29]
 * Evaluation Pass (ans = -1):
 * Key 1: Count=1 -> 1==1 -> ans = max(-1, 1) = 1 [00:08:59].
 * Key 2: Count=2 -> 2==2 -> ans = max(1, 2) = 2 [00:09:14].
 * Key 3: Count=2 -> 3!=2 -> Skip.
 * Key 4: Count=1 -> 4!=1 -> Skip.
 * Key 6: Count=1 -> 6!=1 -> Skip.
 * Output returned = 2 [00:09:48].
 */
public class FindLuckyInteger {

    // APPROACH 1: HashMap Frequency Mapping (Anchor Strategy)
    public static int findLuckyHashMap(int[] arr) {
        if (arr == null || arr.length == 0) return -1;

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : arr) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int maxLucky = -1;
        for (int key : frequencyMap.keySet()) {
            if (frequencyMap.get(key) == key) {
                maxLucky = Math.max(maxLucky, key);
            }
        }
        return maxLucky;
    }

    // APPROACH 2: Constant Boundary Fixed Array Bucketing
    // Given constraints where elements usually sit inside fixed boundaries (e.g., 1 <= arr[i] <= 500), 
    // an explicit primitive tracking bucket removes hashing costs completely to keep space utilization pure.
    public static int findLuckyBucketArray(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        
        int[] buckets = new int[501]; // Assuming default LeetCode range bounds: 1 to 500
        for (int num : arr) {
            if (num >= 1 && num <= 500) {
                buckets[num]++;
            }
        }

        // Iterate backwards to return the largest index directly without checking all max states
        for (int i = 500; i >= 1; i--) {
            if (buckets[i] == i) {
                return i;
            }
        }
        return -1;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Utilizing dynamic classification abstractions (Collectors.groupingBy) 
    // boxes all primitive parameters into boxed equivalents, incurring a linear memory performance drop.
    public static int findLuckyStream(int[] arr) {
        if (arr == null || arr.length == 0) return -1;

        return Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(num -> num, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() == entry.getValue().intValue())
                .mapToInt(Map.Entry::getKey)
                .max()
                .orElse(-1);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 ---
        int[] test1 = {2, 2, 3, 4};
        int res1_1 = findLuckyHashMap(test1);
        int res1_2 = findLuckyBucketArray(test1);
        int res1_3 = findLuckyStream(test1);

        System.out.println("Test Case 1: [2, 2, 3, 4]");
        System.out.println("Approach 1 (HashMap)    Result: " + res1_1);
        System.out.println("Approach 2 (Bucket Arr) Result: " + res1_2);
        System.out.println("Approach 3 (Stream API) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 2 && res1_2 == 2 && res1_3 == 2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {1, 2, 2, 3, 4, 6, 3};
        int res2_1 = findLuckyHashMap(test2);
        int res2_2 = findLuckyBucketArray(test2);
        int res2_3 = findLuckyStream(test2);

        System.out.println("Test Case 2: [1, 2, 2, 3, 4, 6, 3]");
        System.out.println("Approach 1 (HashMap)    Result: " + res2_1);
        System.out.println("Approach 2 (Bucket Arr) Result: " + res2_2);
        System.out.println("Approach 3 (Stream API) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 2 && res2_2 == 2 && res2_3 == 2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Negative Instance) ---
        int[] test3 = {2, 3, 4};
        int res3_1 = findLuckyHashMap(test3);
        int res3_2 = findLuckyBucketArray(test3);
        int res3_3 = findLuckyStream(test3);

        System.out.println("Test Case 3: [2, 3, 4]");
        System.out.println("Approach 1 (HashMap)    Result: " + res3_1);
        System.out.println("Approach 2 (Bucket Arr) Result: " + res3_2);
        System.out.println("Approach 3 (Stream API) Result: " + res3_3);
        System.out.println("Verification: " + (res3_1 == -1 && res3_2 == -1 && res3_3 == -1 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}