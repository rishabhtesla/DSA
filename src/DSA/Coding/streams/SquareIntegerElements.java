package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SquareIntegerElements {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(4, 5, 1, 7, 2, 9); // Example input list

        // ====================================================================================
        // APPROACH 1: Using Stream.map() for Transformation (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of integers, convert it to a new list containing the squares of each element 
        // using Java 8 Streams [00:00:08].
        //
        // STRATEGY:
        // Invoke the `.map(x -> x * x)` operation [00:00:26]. `map()` is an intermediate operation 
        // that transforms elements while preserving stream immutability (the original list remains unchanged) [00:01:18, 00:01:45].
        // ====================================================================================

        List<Integer> approach1Result = list.stream()
                .map(x -> x * x) // Map logic: Multiplies each number by itself to compute the square [00:00:34]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video map Transformation):");
        System.out.println(approach1Result); 
        // Output: [16, 25, 1, 49, 4, 81]


        // ====================================================================================
        // APPROACH 2: Alternative using mapToInt() (Performance Optimized)
        //
        // PROBLEM STATEMENT:
        // Same as above, but optimized for compute-heavy workloads or mathematical calculations.
        //
        // STRATEGY:
        // Instead of processing object streams (`Stream<Integer>`), map values down to a primitive 
        // `IntStream` via `mapToInt`. This executes the squaring math directly on raw primitives, 
        // bypassing the wrapper object unboxing/autoboxing overhead.
        // ====================================================================================

        List<Integer> approach2Result = list.stream()
                .mapToInt(Integer::intValue) // Unbox objects to primitives
                .map(x -> x * x)            // Square operation directly on primitives
                .boxed()                     // Box back to Integer to collect into a List
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative mapToInt Optimization):");
        System.out.println(approach2Result);
        // Output: [16, 25, 1, 49, 4, 81]
    }
}