package DSA.Coding.streams;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FirstNonRepeatedCharacter {
    public static void main(String[] args) {
        String input = "hello world"; // Example input string [00:00:16]

        // ====================================================================================
        // APPROACH 1: Using indexOf() and lastIndexOf() matching
        //
        // PROBLEM STATEMENT:
        // Given a string, find the first non-repeated character using a single stream pipeline [00:00:06].
        //
        // STRATEGY: 
        // For a unique character, its first occurrence index and last occurrence index in the string 
        // will be identical (e.g., for 'h', both indices are 0). For a repeated character like 'l', 
        // its first occurrence is at index 2 and its last occurrence is at index 9 [00:01:14, 00:03:22].
        // ====================================================================================
        
        String result1 = Arrays.stream(input.split("")) // Split string into individual character strings [00:00:28]
                .filter(ch -> input.indexOf(ch) == input.lastIndexOf(ch)) // Keep characters whose first and last index match [00:00:40]
                .findFirst() // Grab the very first matching character that passes the filter [00:00:55]
                .orElse(""); // Safe fallback extraction instead of using a naked .get()

        System.out.println("Approach 1 Result: " + result1); // Output: h [00:01:28]


        // ====================================================================================
        // APPROACH 2: Using LinkedHashMap Frequency Count Collection
        //
        // PROBLEM STATEMENT:
        // Given a string, find the first non-repeated character by explicitly tracking character 
        // frequencies while preserving the original string insertion order [00:03:32].
        //
        // STRATEGY:
        // Group characters using a LinkedHashMap to preserve the structural arrangement order [00:05:49].
        // Then, stream the entry map rows, filter for a value count of 1, and grab the first key [00:06:43, 00:07:35].
        // ====================================================================================

        Character result2 = input.chars() // IntStream of character code points [00:03:51]
                .mapToObj(c -> (char) c) // Box the primitives to Character objects [00:04:01]
                .collect(Collectors.groupingBy(
                        Function -> Function, // Key: The character itself [00:04:20]
                        LinkedHashMap::new,   // Map Factory: LinkedHashMap ensures entry insertion order is preserved [00:05:49]
                        Collectors.counting() // Value: Count total character frequency occurrences [00:04:40]
                ))
                .entrySet()
                .stream() // Open a new stream on the map entry set rows [00:06:43]
                .filter(entry -> entry.getValue() == 1) // Filter map rows for characters that appear exactly once [00:06:50]
                .map(Map.Entry::getKey) // Transform the row map item back to the isolated Character key [00:07:22]
                .findFirst() // Terminate to retrieve the first unique entry [00:07:35]
                .orElse(null);

        System.out.println("Approach 2 Result: " + result2); // Output: h [00:07:53]
    }
}