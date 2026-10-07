package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AvoidReusingStreams {
    public static void main(String[] args) {
        // Example input list of names matching the video context [00:00:17]
        List<String> names = Arrays.asList("Amit", "Adam", "Sam", "Peter");

        // ====================================================================================
        // THE PROBLEM: Attempting to reuse a stream throws IllegalStateException (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of names, find all names that start with 'A' and also print the count 
        // of names found. Demonstrate why streams cannot be reused after consumption [00:00:17].
        //
        // CORE CONCEPTS DISCUSSED:
        // 1. Single-use Lifecycle: A Java 8 stream can only be operated upon by a terminal operation 
        //    (e.g., forEach, count, collect) exactly once [00:00:40].
        // 2. Exception Trigger: Attempting to call another terminal function on the same stream instance 
        //    will fail with `IllegalStateException: stream has already been operated upon or closed` [00:00:49, 00:03:02].
        // ====================================================================================

        // Create initial stream filtered for names starting with "A"
        Stream<String> stream1 = names.stream().filter(name -> name.startsWith("A"));

        System.out.println("--- Bad Practice / Reused Stream Test ---");
        // First terminal consumption: Prints matching names
        stream1.forEach(System.out::println); // [00:01:37]

        try {
            // Second terminal consumption: Attempting to get count on the exact same stream [00:02:33]
            long count = stream1.count(); 
            System.out.println("Count: " + count);
        } catch (IllegalStateException e) {
            // Triggers: "stream has already been operated upon or closed" [00:03:02]
            System.out.println("[Exception Caught Successfully]: " + e.getMessage());
        }


        // ====================================================================================
        // APPROACH 1: Re-creating separate streams for each task (As shown in the video)
        //
        // STRATEGY:
        // Generate a completely fresh stream pipeline from the underlying list collection source 
        // whenever a new terminal operation needs to execute [00:03:14, 00:03:31].
        // ====================================================================================

        System.out.println("\n--- Approach 1 (Video Best Practice - Separate Stream Instantiation) ---");

        // Stream A: Focuses exclusively on filtering and printing names
        names.stream()
                .filter(name -> name.startsWith("A"))
                .forEach(System.out::println);

        // Stream B: A brand new stream instance created from the source to compute the aggregate count [00:03:31]
        long countFromFreshStream = names.stream()
                .filter(name -> name.startsWith("A"))
                .count();

        System.out.println("Count of names found: " + countFromFreshStream);
        // Output: Count of names found: 2


        // ====================================================================================
        // APPROACH 2: Single-Pipeline Collection Optimization (Alternative Best Practice)
        //
        // PROBLEM STATEMENT:
        // Same requirements, but optimized so you don't have to duplicate filtering rules or 
        // loop over the underlying list items multiple times, which wastes performance on large datasets.
        //
        // STRATEGY:
        // Process a single stream to filter entries, and collect matches into an intermediate 
        // sub-list layout. You can then print the sub-list items and query `.size()` directly 
        // from that list, executing the pipeline efficiently.
        // ====================================================================================

        System.out.println("\n--- Approach 2 (Alternative Single-Pass Optimization) ---");

        List<String> filteredNamesList = names.stream()
                .filter(name -> name.startsWith("A"))
                .toList(); // Intermediate collection step eliminates double streaming

        // Print names from the stored results
        filteredNamesList.forEach(System.out::println);

        // Fetch counts directly out of memory sizing variables
        System.out.println("Count of names found: " + filteredNamesList.size());
        // Output: Count of names found: 2
    }
}