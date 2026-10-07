package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ContainsDistinctValues {
    public static void main(String[] args) {
        int[] arr = { 5, 0, 1, 0, 8 }; // Example input array (contains duplicate 0) [00:00:28]

        // Setup base list from primitives using boxed() [00:00:44]
        List<Integer> list = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toList());

        // ====================================================================================
        // APPROACH 1: Map Frequency & Short-circuiting with noneMatch (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given an array of integers, return true if all its elements are distinct (unique), 
        // and false if any element is repeated [00:00:11].
        //
        // STRATEGY:
        // 1. Create a character/integer frequency map using groupingBy and counting() [00:01:32].
        // 2. Extract only the counts/frequencies via .values() [00:03:16].
        // 3. Open a stream over the counts and use .noneMatch(count -> count > 1) [00:03:54].
        //    noneMatch returns true if NO count is greater than 1, proving all elements are unique.
        // ====================================================================================

        boolean approach1Result = list.stream()
                .collect(Collectors.groupingBy(
                        x -> x, // Map key: element itself [00:01:58]
                        Collectors.counting() // Map value: frequency count [00:02:16]
                ))
                .values() // Extract collection of counts [00:03:16]
                .stream() 
                .noneMatch(count -> count > 1); // Check if no frequency exceeds 1 [00:03:54]

        System.out.println("Approach 1 (Video Frequency + noneMatch Logic):");
        System.out.println(approach1Result); 
        // Output: false (due to duplicate 0) [00:04:46]


        // ====================================================================================
        // APPROACH 2: Alternative using Count Comparison (More Optimal Stream Pattern)
        //
        // PROBLEM STATEMENT:
        // Same as above, but evaluated efficiently without the memory overhead of constructing an intermediate map.
        //
        // STRATEGY:
        // 1. Find the total count of elements in the stream pipeline.
        // 2. Find the count of elements after passing through a .distinct() filter.
        // 3. Compare both counts. If the original count equals the distinct count, then every element 
        //    is unique (distinct), returning true. Otherwise, duplicates exist, returning false.
        // ====================================================================================

        boolean approach2Result = list.stream().distinct().count() == list.size();

        System.out.println("\nApproach 2 (Alternative Count vs Distinct Comparison):");
        System.out.println(approach2Result);
        // Output: false
    }
}