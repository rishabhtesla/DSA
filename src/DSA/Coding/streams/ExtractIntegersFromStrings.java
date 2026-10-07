package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExtractIntegersFromStrings {
    public static void main(String[] args) {
        List<String> inputList = Arrays.asList("1", "2", "3", "apple", "4", "5", "banana", "6");

        // ====================================================================================
        // APPROACH 1: Using Regex Matching (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of strings, extract only the valid integers and return them as a list of Integers [00:00:08].
        //
        // STRATEGY:
        // Filter out strings that do not match a fully numeric regex sequence ("\\d+").
        // Once filtered, map the valid numeric strings using Integer::valueOf into Integer objects [00:01:15, 00:01:37].
        // ====================================================================================

        List<Integer> approach1Result = inputList.stream()
                // Regex matches a sequence of one or more digits [00:01:15]
                .filter(str -> str.matches("\\d+")) 
                // Parses the filtered string token to an Integer object [00:01:37]
                .map(Integer::valueOf) 
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Regex Logic):");
        System.out.println(approach1Result); 
        // Output: [1, 2, 3, 4, 5, 6] [00:00:25]


        // ====================================================================================
        // APPROACH 2: Alternative using Exception-Safe Parsing Try-Catch
        //
        // PROBLEM STATEMENT:
        // Same as above, but robust against edge cases where strings might contain large numbers 
        // exceeding integer limits or custom formats where regex might pass but parsing fails.
        //
        // STRATEGY:
        // Instead of matching strings upfront via regex, we map them directly inside a flatMap pipeline.
        // We attempt Integer.parseInt inside a helper method. If it succeeds, it yields a single-element stream.
        // If it throws a NumberFormatException, it catches it gracefully and yields an empty stream, ignoring the value.
        // ====================================================================================

        List<Integer> approach2Result = inputList.stream()
                .flatMap(str -> {
                    try {
                        return java.util.stream.Stream.of(Integer.parseInt(str));
                    } catch (NumberFormatException e) {
                        return java.util.stream.Stream.empty(); // Safely drops non-integer strings
                    }
                })
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Exception-Safe Logic):");
        System.out.println(approach2Result);
        // Output: [1, 2, 3, 4, 5, 6]
    }
}