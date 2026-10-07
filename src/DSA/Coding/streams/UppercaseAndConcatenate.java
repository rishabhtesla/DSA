package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UppercaseAndConcatenate {
    public static void main(String[] args) {
        // Example input list from the video context [00:00:15]
        List<String> alphabets = Arrays.asList("a", "b", "c", "d");

        // ====================================================================================
        // APPROACH 1: Using Stream.map(), reduce(), and orElse() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of strings, convert all strings to uppercase and then concatenate them 
        // into a single string separated by spaces [00:00:00].
        //
        // STRATEGY:
        // 1. Transform each string element to uppercase using `.map(String::toUpperCase)` [00:00:48].
        // 2. Perform a terminal accumulation using `.reduce((s1, s2) -> s1 + " " + s2)` [00:01:04, 00:01:20].
        //    This repeatedly acts on each string pair to join them until a single result remains [00:01:54].
        // 3. Since this reduction variant returns an `Optional`, append `.orElse("")` to handle 
        //    empty stream edge cases safely by returning a default empty string string [00:01:35, 00:02:12].
        // ====================================================================================

        String approach1Result = alphabets.stream()
                .map(String::toUpperCase) // Transforms string elements to uppercase [00:00:48]
                .reduce((s1, s2) -> s1 + " " + s2) // Concatenates elements with a space separator [00:01:20]
                .orElse(""); // Safe default validation handle for empty stream structures [00:01:35]

        System.out.println("Approach 1 (Video Map + Reduce Logic):");
        System.out.println("Result: \"" + approach1Result + "\""); 
        // Output: Result: "A B C D" [00:02:29]


        // ====================================================================================
        // APPROACH 2: Using Collectors.joining() (Idiomatic/Efficient Alternative)
        //
        // PROBLEM STATEMENT:
        // Same transformation goal, but optimized to avoid intermediate immutable String copies. 
        // Successive string concatenation inside a `.reduce()` step creates multiple short-lived 
        // String objects in memory, which can hurt performance on large datasets.
        //
        // STRATEGY:
        // Leverage `Collectors.joining(" ")` downstream inside the `.collect()` stage. Under the 
        // hood, it uses an optimized mutable `StringBuilder` pipeline to append characters efficiently 
        // and inherently returns a plain String without wrapping or requiring an `Optional` unboxing step.
        // ====================================================================================

        String approach2Result = alphabets.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(" ")); // Highly efficient mutable joining optimization

        System.out.println("\nApproach 2 (Alternative Collectors.joining Optimization):");
        System.out.println("Result: \"" + approach2Result + "\"");
        // Output: Result: "A B C D"
    }
}