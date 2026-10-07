package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateAverage {
    public static void main(String[] args) {
        // Example input list of integers from the video [00:00:08]
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        // ====================================================================================
        // APPROACH 1: Using mapToDouble() and average() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of integers, calculate the mathematical average of all the numbers [00:00:00].
        //
        // STRATEGY:
        // 1. Convert the object stream to a primitive `DoubleStream` using `mapToDouble(Integer::doubleValue)` [00:00:22, 00:01:56].
        // 2. Call the primitive terminal method `.average()`, which returns an `OptionalDouble` [00:02:15].
        // 3. Extract the primitive double using `.orElse(0.0)` instead of a raw `.getAsDouble()` to avoid exceptions on empty lists [00:02:25].
        // ====================================================================================

        double approach1Result = list.stream()
                .mapToDouble(Integer::doubleValue) // Converted to primitive DoubleStream to gain math operations [00:00:34]
                .average()                         // Terminal calculation that aggregates average [00:02:15]
                .orElse(0.0);                      // Safe value extraction standard fallback logic

        System.out.println("Approach 1 (Video mapToDouble Logic):");
        System.out.println("Average = " + approach1Result); 
        // Output: 3.0 [00:02:35]


        // ====================================================================================
        // APPROACH 2: Alternative using Collectors.averagingDouble() (Single-pipeline Collector)
        //
        // PROBLEM STATEMENT:
        // Same as above, but achieved natively using the Collectors utility framework in a clean single-stage collection step.
        //
        // STRATEGY:
        // Instead of manually shifting into a primitive stream step, pass `Collectors.averagingDouble(Integer::doubleValue)` 
        // straight inside the `.collect()` method. It automatically aggregates sum and count variables behind the scenes.
        // ====================================================================================

        double approach2Result = list.stream()
                .collect(Collectors.averagingDouble(Integer::doubleValue)); // Directly extracts standard wrapper double average

        System.out.println("\nApproach 2 (Alternative averagingDouble Collector):");
        System.out.println("Average = " + approach2Result);
        // Output: 3.0
    }
}