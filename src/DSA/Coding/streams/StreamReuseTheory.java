package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamReuseTheory {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Dark", "Narcos", "Suits");

        // ====================================================================================
        // THE PROBLEM: Streams cannot be reused once consumed
        //
        // PROBLEM STATEMENT:
        // Can we reuse a stream in Java 8? What happens if we try, and how can we circumvent it [00:00:00]?
        //
        // CORE CONCEPTS DISCUSSED IN THIS VIDEO:
        // 1. Single Consumption: Streams are designed to be consumed only once. Once a terminal 
        //    operation (like forEach, collect, count, or reduce) is invoked, the stream is closed [00:00:14].
        // 2. IllegalStateException: Attempting to reuse an operated-upon stream throws an 
        //    `IllegalStateException: stream has already been operated upon or closed` [00:00:24, 00:02:16].
        // ====================================================================================

        Stream<String> nameStream = list.stream();
        nameStream.forEach(System.out::println); // First consumption works fine [00:01:28]

        try {
            long count = nameStream.count(); // Second consumption attempts reuse [00:01:54]
            System.out.println("Count: " + count);
        } catch (IllegalStateException e) {
            System.out.println("\n[Expected Exception Captured]:");
            System.out.println(e.getMessage()); // Throws "stream has already been operated upon or closed" [00:02:16]
        }


        // ====================================================================================
        // APPROACH 1: Using a Supplier Interface to "Reuse" Streams (As shown in the video)
        //
        // STRATEGY:
        // Wrap the stream creation logic inside a functional interface called a `Supplier<Stream<T>>` [00:00:47, 00:03:03].
        // Each time `.get()` is called on the supplier, it cleanly constructs and outputs a fresh, unconsumed 
        // stream pipeline on demand, seamlessly avoiding reuse conflicts [00:03:27, 00:03:35].
        // ====================================================================================

        System.out.println("\nApproach 1 (Video Supplier Wrapper Pattern):");
        
        // Define a stream supplier that constructs a new stream copy every time .get() is invoked [00:03:03]
        Supplier<Stream<String>> streamSupplier = () -> list.stream();

        // Consumption 1
        streamSupplier.get().forEach(System.out::println); // Evaluates a new stream copy via get() [00:03:27]

        // Consumption 2
        long totalCount = streamSupplier.get().count(); // Evaluates another fresh stream copy via get() [00:03:35]
        System.out.println("Total Element Count via Supplier = " + totalCount);
    }
}