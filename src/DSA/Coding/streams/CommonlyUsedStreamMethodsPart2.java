package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonlyUsedStreamMethodsPart2 {
    public static void main(String[] args) {
        
        // ====================================================================================
        // CONTEXT & SUMMARY: Commonly Used Stream Methods (Part 2)
        // 
        // This video focuses on structural stream truncations, debugging, and terminal 
        // short-circuiting matching predicates using Java 8 Streams [00:00:00].
        // ====================================================================================

        List<String> baseNames = Arrays.asList("Emma", "John", "Sophia", "Oliver");

        // ------------------------------------------------------------------------------------
        // 1. limit(long maxSize) -> Truncates the stream size to look at a maximum index range [00:00:13].
        // ------------------------------------------------------------------------------------
        System.out.println("1. limit():");
        List<String> limited = baseNames.stream()
                .limit(2) // Truncates stream length to process only the first 2 names [00:00:45]
                .collect(Collectors.toList());
        System.out.println(limited); // Output: [Emma, John] [00:01:12]


        // ------------------------------------------------------------------------------------
        // 2. distinct() -> Pulls unique elements by processing equals() comparisons to strip duplicates [00:01:26].
        // ------------------------------------------------------------------------------------
        System.out.println("\n2. distinct():");
        List<String> duplicateNames = Arrays.asList("Emma", "John", "Emma", "Sophia"); // [00:01:45]
        List<String> unique = duplicateNames.stream()
                .distinct() // Eliminates duplicate occurrences [00:02:11]
                .collect(Collectors.toList());
        System.out.println(unique); // Output: [Emma, John, Sophia] [00:02:51]


        // ------------------------------------------------------------------------------------
        // 3. skip(long n) -> Discards the first n elements of the stream snapshot pipeline [00:03:04].
        // ------------------------------------------------------------------------------------
        System.out.println("\n3. skip():");
        List<String> skipped = baseNames.stream()
                .skip(1) // Skips past the index-0 item ("Emma") [00:03:29, 00:04:08]
                .collect(Collectors.toList());
        System.out.println(skipped); // Output: [John, Sophia, Oliver] [00:04:33]


        // ------------------------------------------------------------------------------------
        // 4. peek(Consumer) -> Performs a side-effect action without altering data; perfect for debugging [00:04:44].
        // ------------------------------------------------------------------------------------
        System.out.println("\n4. peek() (Logs items as they pass through):");
        List<String> peeked = baseNames.stream()
                .peek(name -> System.out.println("Inspecting payload element: " + name)) // Inspects stream state safely [00:04:55, 00:05:15]
                .map(String::toUpperCase)
                .collect(Collectors.toList());


        // ------------------------------------------------------------------------------------
        // 5. count() -> Terminal action aggregating total structural items currently tracked [00:06:30].
        // ------------------------------------------------------------------------------------
        System.out.println("\n5. count():");
        long structuralCount = baseNames.stream().count(); // Counts active elements [00:06:53]
        System.out.println("Total Element Count = " + structuralCount); // Output: 4 [00:07:20]


        // ------------------------------------------------------------------------------------
        // 6. allMatch(Predicate) -> Terminal short-circuit evaluating if ALL items fulfill a predicate [00:07:30].
        // ------------------------------------------------------------------------------------
        System.out.println("\n6. allMatch():");
        List<String> aNames = Arrays.asList("Amit", "Alice", "Bina"); // [00:08:03, 00:09:20]
        boolean allStartWithA = aNames.stream()
                .allMatch(name -> name.startsWith("A")); // [00:08:53]
        System.out.println("Do all names start with 'A'? " + allStartWithA); // Output: false ("Bina" fails condition) [00:09:33]


        // ------------------------------------------------------------------------------------
        // 7. anyMatch(Predicate) -> Terminal short-circuit checking if AT LEAST ONE item fulfills a predicate [00:09:49].
        // ------------------------------------------------------------------------------------
        System.out.println("\n7. anyMatch():");
        boolean anyStartWithA = aNames.stream()
                .anyMatch(name -> name.startsWith("A")); // Evaluates true if any entry fits criteria [00:10:58]
        System.out.println("Does at least one name start with 'A'? " + anyStartWithA); // Output: true


        // ------------------------------------------------------------------------------------
        // 8. noneMatch(Predicate) -> Terminal short-circuit validating if ZERO items fit a predicate [00:11:41].
        // ------------------------------------------------------------------------------------
        System.out.println("\n8. noneMatch():");
        List<String> randomNames = Arrays.asList("Victor", "Tim", "Pete"); // [00:11:20]
        boolean noneStartWithA = randomNames.stream()
                .noneMatch(name -> name.startsWith("A")); // Evaluates true because no elements match [00:12:19]
        System.out.println("Do zero names start with 'A'? " + noneStartWithA); // Output: true [00:12:53]
    }
}