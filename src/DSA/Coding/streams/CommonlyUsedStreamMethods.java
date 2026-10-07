package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonlyUsedStreamMethods {
    public static void main(String[] args) {
        
        // ====================================================================================
        // CONTEXT & SUMMARY: Commonly Used Stream Methods (Part 1)
        // 
        // This video functions as an all-in-one comprehensive revision blueprint highlighting the 
        // mechanics and implementations of core intermediate and terminal Java 8 Stream API methods [00:00:13].
        // ====================================================================================

        List<String> names = Arrays.asList("Sam", "Amit", "Adam", "Peter");

        // ------------------------------------------------------------------------------------
        // 1. stream() -> Converts a traditional Collection into a Stream pipeline sequence [00:00:28].
        // ------------------------------------------------------------------------------------
        System.out.println("1. stream():");
        names.stream().forEach(System.out::println);


        // ------------------------------------------------------------------------------------
        // 2. filter(Predicate) -> Evaluates a Boolean state filter on elements [00:01:19].
        // Example: Extracts only names that start with the character "A" [00:01:32].
        // ------------------------------------------------------------------------------------
        System.out.println("\n2. filter():");
        List<String> filtered = names.stream()
                .filter(name -> name.startsWith("A")) // [00:01:58]
                .collect(Collectors.toList());
        System.out.println(filtered); // Output: [Amit, Adam] [00:02:15]


        // ------------------------------------------------------------------------------------
        // 3. map(Function) -> Performs a 1-to-1 conversion, transforming elements [00:02:27].
        // Example: Maps each String to its corresponding integer length property [00:02:52].
        // ------------------------------------------------------------------------------------
        System.out.println("\n3. map():");
        List<Integer> lengths = names.stream()
                .map(String::length) // [00:03:16]
                .collect(Collectors.toList());
        System.out.println(lengths); // Output: [3, 4, 4, 5] [00:03:53]


        // ------------------------------------------------------------------------------------
        // 4. flatMap(Function) -> Flattens structural multi-layer collections (1-to-many) [00:04:03].
        // Example: Merges a nested List of Lists down into a uniform top-level stream [00:04:20].
        // ------------------------------------------------------------------------------------
        System.out.println("\n4. flatMap():");
        List<List<String>> nestedNames = Arrays.asList(
                Arrays.asList("Sam", "Amit", "Adam"),
                Arrays.asList("Mike") // Nested element [00:04:52]
        );
        List<String> flattened = nestedNames.stream()
                .flatMap(List::stream) // Flattens nested arrays into a single sequential list [00:06:22]
                .collect(Collectors.toList());
        System.out.println(flattened); // Output: [Sam, Amit, Adam, Mike] [00:06:48]


        // ------------------------------------------------------------------------------------
        // 5. forEach(Consumer) -> Terminal loop that performs actions on elements directly [00:07:14].
        // ------------------------------------------------------------------------------------
        System.out.println("\n5. forEach():");
        names.stream().forEach(name -> System.out.print(name + " ")); // [00:07:57]
        System.out.println();


        // ------------------------------------------------------------------------------------
        // 6. collect(Collector) -> Terminal action aggregating elements into data structures [00:08:23].
        // ------------------------------------------------------------------------------------
        System.out.println("\n6. collect():");
        List<String> collectedList = names.stream().collect(Collectors.toList()); // [00:08:45]
        System.out.println(collectedList);


        // ------------------------------------------------------------------------------------
        // 7. sorted() -> Re-orders the internal components following natural alphabetical layout [00:09:07].
        // ------------------------------------------------------------------------------------
        System.out.println("\n7. sorted():");
        List<String> sortedNames = names.stream()
                .sorted() // [00:09:40]
                .collect(Collectors.toList());
        System.out.println(sortedNames); // Output: [Adam, Amit, Peter, Sam] [00:11:14]
    }
}