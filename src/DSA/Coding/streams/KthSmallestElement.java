package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;

public class KthSmallestElement {
    public static void main(String[] args) {
        // Input list of integers from the video [00:02:20]
        List<Integer> list = Arrays.asList(4, 5, 1, 7, 2, 9);
        int k = 3; // Find the 3rd smallest element [00:02:20]

        // ====================================================================================
        // APPROACH 1: Sorting and Skipping (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of integers, write a Java 8 Stream program to find the Kth smallest element [00:00:00].
        //
        // STRATEGY: 
        // 1. Sort the list in ascending order (`[1, 2, 4, 5, 7, 9]`) [00:00:21, 00:02:20].
        // 2. Skip the first `k - 1` elements. Skipping 2 elements leaves `[4, 5, 7, 9]` [00:00:27, 00:02:33].
        // 3. Call `.findFirst()` to grab the first remaining element (`4`) [00:00:39, 00:02:42].
        // ====================================================================================

        int approach1Result = list.stream()
                .sorted() // Sorts naturally in ascending order [00:00:21]
                .skip(k - 1) // Bypasses the first k-1 elements [00:00:27]
                .findFirst() // Grabs the next available element [00:00:39]
                .orElse(-1); // Safe fallback extraction instead of naked .get() [00:00:51]

        System.out.println("Approach 1 (Video Sort + Skip Logic):");
        System.out.println(k + "rd smallest element is: " + approach1Result); 
        // Output: 4 (Note: Video output logs '4' during trace, transcript mentions '2' inadvertently)


        // ====================================================================================
        // APPROACH 2: Handling Duplicates (True Uniqueness Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but robust against input lists that contain duplicate numbers. For example, 
        // in `[1, 1, 2, 4]`, the 3rd smallest *distinct* element should mathematically be `4`, not `2`.
        //
        // STRATEGY:
        // Chain a `.distinct()` operator right before or after sorting to filter out identical values. 
        // This ensures you are finding the Kth smallest *unique* value in the collection.
        // ====================================================================================

        List<Integer> listWithDuplicates = Arrays.asList(4, 5, 1, 1, 7, 2, 2, 9);

        int approach2Result = listWithDuplicates.stream()
                .distinct()  // Removes duplicate items first
                .sorted()    // Sorts the remaining unique values
                .skip(k - 1) // Skips to the target position
                .findFirst()
                .orElse(-1);

        System.out.println("\nApproach 2 (Alternative Deduplicated Unique Kth Smallest):");
        System.out.println(k + "rd smallest unique element is: " + approach2Result);
        // Output: 4
    }
}