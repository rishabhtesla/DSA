package DSA.Coding.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DistinctNumbersStartingWithOne {
    public static void main(String[] args) {
        // Setup initial primitive input array matching the video scenario [00:00:14]
        int[] array = {12, 114, 11, 12, 121, 56, 78, 1643, 99, 11};

        // ====================================================================================
        // APPROACH 1: Using IntStream, boxed(), and Lambda Comparator (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a primitive int array, print all distinct numbers that start with the digit "1" 
        // sorted in descending order [00:00:08].
        //
        // STRATEGY:
        // 1. Convert the primitive array into an `IntStream` using `Arrays.stream(array)` [00:01:00].
        // 2. Eliminate duplicates using `.distinct()` [00:01:23].
        // 3. Temporarily convert the primitive `int` to a `String` using `String.valueOf(x)` inside 
        //    a `.filter()` operation to safely invoke `.startsWith("1")` [00:02:12, 00:02:28].
        // 4. Box the primitive stream into an object stream using `.boxed()` (`Stream<Integer>`) 
        //    because custom sorting and object collection requires objects rather than primitives [00:03:07, 00:03:40].
        // 5. Sort in reverse descending order using the custom comparison calculation rule `(a, b) -> b - a` [00:03:21].
        // 6. Terminate into a standard generic list using `.collect(Collectors.toList())` [00:03:55].
        // ====================================================================================

        List<Integer> approach1Result = Arrays.stream(array)
                .distinct()                                         // Removes duplicate values [00:01:23]
                .filter(x -> String.valueOf(x).startsWith("1"))      // Filters for numbers starting with "1" [00:02:28]
                .boxed()                                            // Converts IntStream to Stream<Integer> [00:03:40]
                .sorted((a, b) -> b - a)                            // Descending order calculation logic [00:03:21]
                .collect(Collectors.toList());                      // Gathers results [00:03:55]

        System.out.println("Approach 1 (Video IntStream to Boxed Descending Logic):");
        System.out.println(approach1Result);
        // Expected Output: [1643, 121, 114, 12, 11] [00:00:23]


        // ====================================================================================
        // APPROACH 2: Idiomatic Comparator Strategy (Alternative Best Practice)
        //
        // PROBLEM STATEMENT:
        // Same as above, but replacing the subtraction lambda `(a, b) -> b - a` with Java's pre-built 
        // `Comparator.reverseOrder()`. 
        //
        // STRATEGY:
        // Using `b - a` can cause hidden numeric underflow/overflow bugs if numbers are extreme 
        // values near `Integer.MAX_VALUE` or `Integer.MIN_VALUE`. Utilizing `Comparator.reverseOrder()` 
        // completely circumvents integer range overflow risks and scales robustly.
        // ====================================================================================

        List<Integer> approach2Result = Arrays.stream(array)
                .distinct()
                .filter(x -> String.valueOf(x).startsWith("1"))
                .boxed()
                .sorted(Comparator.reverseOrder())                  // Idiomatic and overflow-safe descending sorting
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Overflow-Safe Comparator.reverseOrder()):");
        System.out.println(approach2Result);
    }
}