package DSA.Coding.streams;

import java.util.*;
import java.util.stream.Collectors;

public class GroupNumbersByRange {
    public static void main(String[] args) {
        int[] arr = {2, 3, 10, 14, 20, 24, 40, 44, 50, 54};

        // ====================================================================================
        // APPROACH 1: Using Integer Math Truncation (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given an array of integers, group the numbers by the range bucket they belong to 
        // (e.g., 0-9 bucket grouped under key 0, 10-19 bucket grouped under key 10, etc.) [00:00:08].
        //
        // STRATEGY:
        // Leverage integer truncation division. For example, 14 / 10 = 1. 
        // Then multiply by 10 to get the floor boundary key: 1 * 10 = 10 [00:01:46].
        // A LinkedHashMap is used to keep the output buckets in their encounter order [00:05:48].
        // ====================================================================================

        List<Integer> list = Arrays.stream(arr)
                .boxed() // Converts primitive IntStream to Stream<Integer> [00:02:43]
                .collect(Collectors.toList());

        Map<Integer, List<Integer>> approach1Result = list.stream()
                .collect(Collectors.groupingBy(
                        num -> (num / 10) * 10, // Key Classifier logic: (num / 10) * 10 [00:03:47]
                        LinkedHashMap::new,     // Map Factory: Preserves range insertion order [00:05:48]
                        Collectors.toList()     // Downstream collector to accumulate numbers [00:03:59]
                ));

        System.out.println("Approach 1 (Video Logic):");
        System.out.println(approach1Result);
        // Output: {0=[2, 3], 10=[10, 14], 20=[24, 20], 40=[40, 44], 50=[50, 54]} [00:05:24]


        // ====================================================================================
        // APPROACH 2: Alternative using TreeMap (Sorted Custom Range Keys)
        //
        // PROBLEM STATEMENT:
        // Same as above, but dynamic, sorted, and explicitly mapping to descriptive string ranges 
        // (like "0-9", "10-19") instead of single floor integer multipliers.
        //
        // STRATEGY:
        // Instead of hardcoding a raw math formula, we calculate the bucket ranges explicitly and 
        // collect them into a TreeMap. TreeMap guarantees that keys are sorted numerically/alphabetically 
        // regardless of the element input order in the source array.
        // ====================================================================================

        Map<String, List<Integer>> approach2Result = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(
                        num -> {
                            int floorRange = (num / 10) * 10;
                            int ceilingRange = floorRange + 9;
                            return floorRange + "-" + ceilingRange; // Dynamic string key (e.g., "10-19")
                        },
                        TreeMap::new, // Map Factory: Automatically keeps range keys sorted naturally
                        Collectors.toList()
                ));

        System.out.println("\nApproach 2 (Alternative Descriptive Sorted Ranges):");
        System.out.println(approach2Result);
        // Output: {"0-9"=[2, 3], "10-19"=[10, 14], "20-29"=[20, 24], "40-49"=[40, 44], "50-59"=[50, 54]}
    }
}