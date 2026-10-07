package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;

public class SumOfElements {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(4, 5, 1, 7, 2, 9); // Example input list

        // ====================================================================================
        // APPROACH 1: Using mapToInt() and sum() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of integers, find the sum of all elements using Java 8 Streams [00:00:14].
        //
        // STRATEGY:
        // Convert the object stream (`Stream<Integer>`) to a primitive `IntStream` using `mapToInt` [00:00:38].
        // This avoids the overhead of autoboxing and unboxing wrapper types, making it highly 
        // performant and memory-efficient for arithmetic operations [00:02:14, 00:02:22].
        // ====================================================================================

        int approach1Result = list.stream()
                .mapToInt(Integer::intValue) // Unboxes Integer objects to primitive ints [00:01:15]
                .sum(); // Invokes the optimized primitive summation collector [00:01:31]

        System.out.println("Approach 1 (Video mapToInt Logic):");
        System.out.println("Sum = " + approach1Result); // Output: 28


        // ====================================================================================
        // APPROACH 2: Alternative using reduce()
        //
        // PROBLEM STATEMENT:
        // Same as above, but achieved using standard functional reduction metrics without unboxing 
        // down to a primitive stream.
        //
        // STRATEGY:
        // Accumulate elements iteratively via a binary accumulator function. 
        // The identity element is 0, and the collector maps combinations to `(a, b) -> a + b` or `Integer::sum`.
        // ====================================================================================

        int approach2Result = list.stream()
                .reduce(0, Integer::sum); // Identity accumulator reduction fallback

        System.out.println("\nApproach 2 (Alternative reduce Logic):");
        System.out.println("Sum = " + approach2Result); // Output: 28
    }
}