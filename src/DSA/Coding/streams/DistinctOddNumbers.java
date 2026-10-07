package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DistinctOddNumbers {
    public static void main(String[] args) {
        // Input list of integers containing duplicates (e.g., 9 appears twice) [00:00:13]
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 10);

        // ====================================================================================
        // APPROACH 1: Using .filter(), .distinct(), and Collectors.toList() (As shown in video)
        //
        // PROBLEM STATEMENT:
        // Given a list of integers, find and return a list containing only the unique (distinct) 
        // odd numbers [00:00:07].
        //
        // STRATEGY:
        // 1. Filter elements where `x % 2 != 0` to isolate the odd numbers [00:00:41].
        // 2. Chain the `.distinct()` operation to remove duplicate values like the second '9' [00:01:29].
        // 3. Accumulate the final unique odd elements into a `List` [00:00:53].
        // ====================================================================================

        List<Integer> approach1Result = list.stream()
                .filter(x -> x % 2 != 0)  // Isolates odd integers [00:00:41]
                .distinct()              // Truncates repeating duplicates [00:01:29]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Filter + Distinct Logic):");
        System.out.println(approach1Result); 
        // Output: [1, 3, 5, 7, 9] [00:01:21]


        // ====================================================================================
        // APPROACH 2: Alternative using Collectors.toSet() (More Idiomatic for Uniqueness)
        //
        // PROBLEM STATEMENT:
        // Same as above, but optimized by leveraging the structural uniqueness properties of a Set 
        // instead of invoking a separate, stateful `.distinct()` operator step.
        //
        // STRATEGY:
        // Filter for odd numbers as usual, but collect directly using `Collectors.toSet()`. A `Set` 
        // inherently enforces uniqueness, automatically handling duplicate elimination during collection.
        // ====================================================================================

        Set<Integer> approach2Result = list.stream()
                .filter(x -> x % 2 != 0)
                .collect(Collectors.toSet()); // Automatically handles deduplication inside a Set container

        System.out.println("\nApproach 2 (Alternative Collectors.toSet Optimization):");
        System.out.println(approach2Result);
        // Output: [1, 3, 5, 7, 9] (Order depends on the underlying Set implementation)
    }
}