package DSA.Coding.streams;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcatenateTwoStreams {
    public static void main(String[] args) {
        // Creating the initial streams using the convenient static factory method Stream.of() [00:00:19, 00:00:28]
        Stream<String> stream1 = Stream.of("Dark", "Narcos");
        Stream<String> stream2 = Stream.of("Suits", "Friends");

        // ====================================================================================
        // APPROACH 1: Using Stream.concat() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given two already initialized streams, concatenate them into a single comprehensive 
        // stream structure using Java 8 Streams [00:00:12].
        //
        // STRATEGY:
        // Use the static utility method `Stream.concat(a, b)`. This appends the second stream's 
        // elements directly after the first stream's elements sequentially [00:00:50, 00:01:06].
        //
        // NOTE: Terminal operations consume the stream, meaning it cannot be reused [00:01:16, 00:01:44].
        // ====================================================================================

        Stream<String> concatenatedStream = Stream.concat(stream1, stream2); // Merges pipelines sequentially [00:00:50]

        System.out.println("Approach 1 (Video Stream.concat terminal execution):");
        concatenatedStream.forEach(System.out::println); // Consumes stream via method reference loop [00:01:24]
        /* Output:
           Dark
           Narcos
           Suits
           Friends
        */


        // ====================================================================================
        // APPROACH 2: Concatenating Multiple (> 2) Streams via Stream.flatMap (Alternative Pattern)
        //
        // PROBLEM STATEMENT:
        // How do you cleanly concatenate three or more streams without nesting successive 
        // `Stream.concat(Stream.concat(s1, s2), s3)` method statements together awkwardly?
        //
        // STRATEGY:
        // Group all the target streams into an overarching `Stream.of(s1, s2, s3)` pipeline layer, 
        // then apply `.flatMap(java.util.function.Function.identity())` to flatten them cleanly 
        // into a single sequential execution track.
        // ====================================================================================

        Stream<String> s1 = Stream.of("Java");
        Stream<String> s2 = Stream.of("Python");
        Stream<String> s3 = Stream.of("Go", "Rust");

        System.out.println("\nApproach 2 (Alternative Multiple-Stream FlatMap Flattening):");
        Stream.of(s1, s2, s3)
                .flatMap(stream -> stream) // Equivalent to Function.identity(), flattens all inner streams
                .collect(Collectors.toList())
                .forEach(System.out::println);
        /* Output:
           Java
           Python
           Go
           Rust
        */
    }
}