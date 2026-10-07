package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FindStringsContainingOnlyDigits {
    public static void main(String[] args) {
        // Example input list containing mixed string types from the video [00:00:16]
        List<String> list = Arrays.asList("123", "abc", "45", "a1b2");

        // ====================================================================================
        // APPROACH 1: Using String.matches() with character class regex (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of strings, write a Java 8 Stream program to find and filter out only the 
        // strings that contain strictly numeric digits [00:00:06].
        //
        // STRATEGY: 
        // Filter the stream by checking if each string matches the regular expression `^[0-9]+$`. 
        // The `+` quantifier handles matching scenarios with one or more continuous digits [00:01:11, 00:01:20].
        // ====================================================================================

        List<String> approach1Result = list.stream()
                .filter(str -> str.matches("[0-9]+")) // Keeps strings matching only numeric classes [00:01:11]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Character Class Regex Logic):");
        System.out.println(approach1Result); 
        // Output: [123, 45] [00:00:23]


        // ====================================================================================
        // APPROACH 2: Using the `\\d` Regex Digit Shortcut (Mentioned as variant in the video)
        //
        // PROBLEM STATEMENT:
        // Same as above, but substituting the explicit `[0-9]` character class for the native shortcut token syntax.
        //
        // STRATEGY:
        // Use `str.matches("\\d+")` inside the filter. In Java regular expressions, `\\d` represents 
        // a digit character shortcut, acting as a cleaner programmatic variation [00:01:58].
        // ====================================================================================

        List<String> approach2Result = list.stream()
                .filter(str -> str.matches("\\d+")) // Short-hand regex matching digits [00:01:58]
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Digit Token Shortcut):");
        System.out.println(approach2Result);
        // Output: [123, 45]


        // ====================================================================================
        // APPROACH 3: Character Code Point All-Match Rule (Robust External Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but achieved natively without compiling a regex matching system for performance metrics.
        //
        // STRATEGY:
        // For each string element, verify its emptiness, open up an underlying primitive `.chars()` 
        // int stream of its code points, and apply `.allMatch(Character::isDigit)`.
        // ====================================================================================

        List<String> approach3Result = list.stream()
                .filter(str -> !str.isEmpty() && str.chars().allMatch(Character::isDigit)) // Custom structural validation
                .collect(Collectors.toList());

        System.out.println("\nApproach 3 (Alternative Code Point Check - Non-Regex):");
        System.out.println(approach3Result);
        // Output: [123, 45]
    }
}