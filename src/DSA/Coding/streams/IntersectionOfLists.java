package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IntersectionOfLists {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5); // Example input list 1 [00:00:15]
        List<Integer> list2 = Arrays.asList(3, 5, 6, 7);    // Example input list 2 [00:00:15]

        // ====================================================================================
        // APPROACH 1: Using Stream.filter() and List::contains (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given two lists of integers, find the intersection by extracting only the elements 
        // that are common to both lists using Java 8 Streams [00:00:08].
        //
        // STRATEGY:
        // 1. Open a stream over `list1` [00:00:38].
        // 2. Pass a predicate method reference `list2::contains` inside `.filter()` [00:00:46, 00:01:00].
        //    This effectively evaluates whether each element of list1 is also present in list2 [00:01:07].
        // ====================================================================================

        List<Integer> approach1Result = list1.stream()
                .filter(list2::contains) // Evaluates presence matches against list2 entries [00:01:00]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video filter + contains Logic):");
        System.out.println(approach1Result); 
        // Output: [3, 5] [00:00:25]


        // ====================================================================================
        // APPROACH 2: Optimized O(N + M) Lookup using Set (Highly Performant Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but optimized for large data workloads. Calling `list2.contains(x)` inside 
        // a stream loop runs in O(N * M) time complexity because list lookups require sequential scans.
        //
        // STRATEGY:
        // Convert `list2` into a hash-based `Set` framework beforehand. Hash lookup properties reduce 
        // `contains()` evaluation down to constant O(1) time complexity, resulting in a significantly 
        // faster overall runtime execution of O(N + M).
        // ====================================================================================

        Set<Integer> set2 = Set.copyOf(list2); // Creates an unmodifiable high-speed lookup HashSet container

        List<Integer> approach2Result = list1.stream()
                .filter(set2::contains) // constant time O(1) checking efficiency
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative O(N + M) Set Lookup Optimization):");
        System.out.println(approach2Result);
        // Output: [3, 5]
    }
}