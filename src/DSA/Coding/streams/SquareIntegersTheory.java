package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SquareIntegersTheory {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(4, 5, 1, 7, 2, 9);

        // ====================================================================================
        // APPROACH 1: Using Stream.map() for Transformation (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of integers, convert it to a new list containing the squares of each element 
        // using Java 8 Streams [00:00:08].
        //
        // CORE CONCEPTS DISCUSSED IN THIS THEORY VIDEO:
        // 1. Transformation: `map()` changes the form of the elements (e.g., matching a string to its length) [00:01:18].
        // 2. Immutability: A new stream is created without mutating the original source collection [00:01:45].
        // 3. Conciseness: Avoids boilerplate external loop code blocks [00:02:00].
        // 4. Pipeline Processing: Enables chaining multiple complex data operations smoothly [00:02:21].
        // 5. Parallel Processing friendliness: Simplifies scaling up execution on large datasets [00:02:37].
        // ====================================================================================

        List<Integer> approach1Result = list.stream()
                .map(x -> x * x) // Map logic: Transforms the values into their squares [00:00:34]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Map Logic):");
        System.out.println(approach1Result);
        // Output: [16, 25, 1, 49, 4, 81]


        // ====================================================================================
        // APPROACH 2: Alternative using Primitive IntStream.map()
        //
        // PROBLEM STATEMENT:
        // Same transformation logic but explicitly focused on optimizing the mathematical operation.
        //
        // STRATEGY:
        // Downcast to a primitive IntStream using mapToInt to calculate calculations on unboxed values, 
        // then box back up to generate the final list container.
        // ====================================================================================

        List<Integer> approach2Result = list.stream()
                .mapToInt(Integer::intValue)
                .map(x -> x * x)
                .boxed()
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Primitive Math Optimization):");
        System.out.println(approach2Result);
        // Output: [16, 25, 1, 49, 4, 81]
    }
}