package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnionOfLists {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(6, 7, 8, 9, 10);

        // ====================================================================================
        // APPROACH 1: Using Stream.concat() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given two lists of integers, find the union of these two lists by combining them 
        // into a single comprehensive list of integers using Java 8 Streams [00:00:00].
        //
        // STRATEGY: 
        // Use the native `Stream.concat(streamA, streamB)` utility operation to link two distinct 
        // lists smoothly sequentially into one unified stream tracking framework [00:00:26].
        // ====================================================================================

        List<Integer> approach1Result = Stream.concat(list1.stream(), list2.stream()) // Merges both pipelines [00:00:31]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Stream.concat Logic):");
        System.out.println(approach1Result); 
        // Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] [00:01:06]


        // ====================================================================================
        // APPROACH 2: Handling Duplicates (Mathematical Union Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but robust against overlapping list scenarios. In classical mathematics, 
        // a "Union" requires that duplicate values across sets be completely deduplicated, 
        // ensuring elements appearing in both arrays are only counted once.
        //
        // STRATEGY:
        // Concatenate streams as usual, but chain an intermediate `.distinct()` operation step 
        // to filter out repeating values before completing collection.
        // ====================================================================================

        List<Integer> duplicateList1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> duplicateList2 = Arrays.asList(4, 5, 6, 7, 8); // '4' and '5' overlap

        List<Integer> approach2Result = Stream.concat(duplicateList1.stream(), duplicateList2.stream())
                .distinct() // Enforces true set unique mathematical union constraints
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Deduplicated Unique Union):");
        System.out.println(approach2Result);
        // Output: [1, 2, 3, 4, 5, 6, 7, 8]
    }
}