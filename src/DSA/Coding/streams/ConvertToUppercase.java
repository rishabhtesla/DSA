package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ConvertToUppercase {
    public static void main(String[] args) {
        // Example input list of string elements from the video [00:00:22]
        List<String> list = Arrays.asList("dark", "narcos", "suits");

        // ====================================================================================
        // APPROACH 1: Using Stream.map() and String::toUpperCase (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of strings, write a Java 8 Stream program to transform all string elements 
        // to uppercase [00:00:16].
        //
        // STRATEGY:
        // Pass the method reference `String::toUpperCase` into the intermediate `.map()` function 
        // to map and process every string element seamlessly [00:00:48, 00:01:05].
        // ====================================================================================

        List<String> approach1Result = list.stream()
                .map(String::toUpperCase) // Transforms each item to uppercase using method reference [00:01:05]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video String::toUpperCase Logic):");
        System.out.println(approach1Result); 
        // Output: [DARK, NARCOS, SUITS] [00:02:15]


        // ====================================================================================
        // APPROACH 2: Alternative using Locale-Specific Uppercase Processing (Video Concept)
        //
        // PROBLEM STATEMENT:
        // Same as above, but robust for globalization frameworks. Standard formatting can yield 
        // surprising results when applied to certain scripts (like Turkish or Azerbaijani where the 
        // lowercase 'i' converts to a dotted uppercase 'İ' rather than a standard 'I').
        //
        // STRATEGY:
        // Leverage the overloaded `.toUpperCase(Locale)` variation inside the map lambda method 
        // to guarantee safe cultural/regional resource string translation standards [00:01:19, 00:01:34].
        // ====================================================================================

        List<String> approach2Result = list.stream()
                .map(str -> str.toUpperCase(Locale.ENGLISH)) // Safe explicitly-defined locale conversion
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Locale-Specific Logic):");
        System.out.println(approach2Result);
        // Output: [DARK, NARCOS, SUITS]
    }
}