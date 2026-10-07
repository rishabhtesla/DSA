package DSA.Coding.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoveZerosToBeginning {
    public static void main(String[] args) {
        int[] rawArray = { 5, 0, 1, 0, 8, 0 }; // Example input array [00:00:16]

        // Setup base list from primitives using boxed() [00:00:41]
        List<Integer> list = Arrays.stream(rawArray)
                .boxed()
                .collect(Collectors.toList());

        // ====================================================================================
        // APPROACH 1: Separate Filtering and Collection Merging (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given an integer array, write a Java 8 Stream program to move all zeros to the 
        // beginning of the array while preserving the relative order of the non-zero elements [00:00:09].
        //
        // STRATEGY:
        // 1. Create a stream filtered explicitly for `x == 0` [00:02:10].
        // 2. Create another stream filtered explicitly for `x != 0` [00:02:22].
        // 3. Instantiate a target master list and add all elements from the zero collection, 
        //    followed by all elements from the non-zero collection [00:02:45].
        // ====================================================================================

        List<Integer> zeros = list.stream().filter(x -> x == 0).collect(Collectors.toList());
        List<Integer> nonZeros = list.stream().filter(x -> x != 0).collect(Collectors.toList());

        List<Integer> approach1Result = new ArrayList<>();
        approach1Result.addAll(zeros);     // Adds [0, 0, 0] [00:02:56]
        approach1Result.addAll(nonZeros);  // Adds [5, 1, 8]
        
        System.out.println("Approach 1 (Separate Filters Logic):");
        System.out.println(approach1Result); 
        // Output: [0, 0, 0, 5, 1, 8] [00:00:22]


        // ====================================================================================
        // APPROACH 2: Using Collectors.partitioningBy and FlatMap (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Same as above, but achieved in a more advanced single-pass grouping collector approach.
        //
        // STRATEGY:
        // 1. Partition the numbers using the predicate `x != 0`. The map keys evaluate to Boolean [00:03:56].
        //    Key `false` will contain the zeros, and Key `true` will contain the non-zeros [00:05:03].
        // 2. Extract `.values()` which provides a collection layout of lists in that specific map 
        //    Boolean sorting order (`false` first, then `true`), effectively placing zeros out front [00:05:28].
        // 3. Use `.flatMap(List::stream)` to flatten the structure from `List<List<Integer>>` 
        //    into a single continuous `List<Integer>` result [00:06:17, 00:06:47].
        // ====================================================================================

        List<Integer> approach2Result = list.stream()
                .collect(Collectors.partitioningBy(x -> x != 0)) // Splits stream into a Map<Boolean, List<Integer>> [00:03:56]
                .values() // Grabs the list values. Since false comes before true, zeros come first! [00:05:28]
                .stream()
                .flatMap(List::stream) // Flattens List<List<Integer>> into a unified Stream<Integer> [00:06:17]
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Partitioning By & FlatMap Logic):");
        System.out.println(approach2Result);
        // Output: [0, 0, 0, 5, 1, 8] [00:09:11]


        // ====================================================================================
        // APPROACH 3: Alternative using Custom Comparator Sorting (Optimal External Approach)
        //
        // PROBLEM STATEMENT:
        // Same as above, but fully optimized using standard sorting without multiple lists or splitting.
        //
        // STRATEGY:
        // Pass a custom comparator to the `.sorted()` stream operation. We compare values based on 
        // whether they are zero. By treating a zero condition as "smaller" than non-zero elements, 
        // the sorting algorithm pushes all zeros to the left side while leaving the non-zero sequence order intact.
        // ====================================================================================

        List<Integer> approach3Result = list.stream()
                .sorted((a, b) -> {
                    if (a == 0 && b != 0) return -1; // Move a (zero) to the left
                    if (a != 0 && b == 0) return 1;  // Move b (zero) to the left
                    return 0;                        // Keep original order for everything else
                })
                .collect(Collectors.toList());

        System.out.println("\nApproach 3 (Alternative Custom Sort Comparator):");
        System.out.println(approach3Result);
        // Output: [0, 0, 0, 5, 1, 8]
    }
}