package DSA.Coding.streams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InfiniteStreamsBestPractices {
    public static void main(String[] args) {

        // ====================================================================================
        // THE PROBLEM: Unbounded Infinite Streams (Pitfall)
        //
        // CORE CONCEPTS DISCUSSED:
        // 1. Missing Short-circuiting: Methods like `Stream.iterate` and `Stream.generate` create 
        //    infinite pipelines. Invoking a non-short-circuiting terminal operator (like `forEach`) 
        //    without a limit results in an infinite loop that will crash the application [00:01:19, 00:01:41].
        // 2. Performance Overhead: Boxing primitives into wrapper objects (`Integer`) within long loops 
        //    creates massive memory overhead [00:02:24].
        // ====================================================================================

        System.out.println("--- Pitfall Demonstration (Conceptual Only) ---");
        // Stream.iterate(1, x -> x + 1).forEach(System.out::println); 
        // WARNING: Running the line above creates a runaway infinite sequence loop! [00:01:19]


        // ====================================================================================
        // APPROACH 1: Bounded Primitive IntStream (As shown in the video)
        //
        // STRATEGY:
        // 1. Swap the object wrapper `Stream.iterate` out for primitive `IntStream.iterate` to eliminate 
        //    boxing/unboxing overhead for better performance and less memory stress [00:02:24].
        // 2. Explicitly bind the terminal bounds by inserting a `.limit(10)` intermediate operation 
        //    to safely intercept and stop the infinite generation cycle [00:01:34, 00:03:13].
        // ====================================================================================

        System.out.println("Approach 1 (Bounded Primitive IntStream.iterate):");
        IntStream.iterate(1, x -> x + 1) // Generates progressive numbers based on state pattern [00:00:34]
                .limit(10)               // Binds the infinite sequence safely to 10 elements [00:01:34]
                .forEach(System.out::println); // Prints elements 1 through 10 [00:03:13]


        // ====================================================================================
        // PROBLEM: Misusing `Stream.iterate` for Stateless Values (Pitfall)
        //
        // CONCEPT:
        // `Stream.iterate` requires each step to depend heavily on the previous element's computed values [00:04:41].
        // Using it to return stateless independent data like `Math.random()` ignores the seed parameter 
        // and is structurally incorrect [00:04:25].
        // ====================================================================================

        System.out.println("\n--- Incorrect Usage Example ---");
        Stream.iterate(1, x -> (int)(Math.random() * 100)) // Misuse: Does not rely on 'x' [00:04:25]
                .limit(5)
                .forEach(System.out::println);


        // ====================================================================================
        // APPROACH 2: `Stream.generate` for Stateless Data (As shown in the video)
        //
        // STRATEGY:
        // When generating unstructured, stateless sequence flows (e.g., random values, constant strings, 
        // UUID tokens), use `Stream.generate(Supplier)` instead of `iterate` [00:05:17]. 
        // It consumes a stateless supplier rather than updating an internal accumulator seed [00:05:30].
        // ====================================================================================

        System.out.println("\nApproach 2 (Correct Stateless Generation via Stream.generate):");
        Stream.generate(Math::random)    // Accepts a stateless dynamic supplier lambda [00:05:30]
                .limit(5)                // Bounds sequence loops safely
                .forEach(System.out::println);
    }
}