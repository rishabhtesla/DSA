package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByFirstCharacterCount {
    public static void main(String[] args) {
        // Example input list containing words from the video context [00:00:42, 00:00:51]
        List<String> list = Arrays.asList("apple", "apricot", "avocado", "banana", "blueberry", "cherry");

        // ====================================================================================
        // APPROACH 1: Using charAt(0) and Collectors.counting() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of strings, group them based on their first character and return a map 
        // with the frequency count of strings belonging to each character bucket [00:00:00].
        //
        // STRATEGY:
        // 1. Convert the input list to a stream [00:01:05].
        // 2. Use `Collectors.groupingBy` with `str -> str.charAt(0)` to set the classification key 
        //    as the primitive character at index 0 [00:01:15, 00:01:27].
        // 3. Pass `Collectors.counting()` as the downstream collector to aggregate total occurrences [00:01:33].
        // ====================================================================================

        Map<Character, Long> approach1Result = list.stream()
                .collect(Collectors.groupingBy(
                        str -> str.charAt(0),    // Extract the character at the 0th index as the Map key [00:01:27]
                        Collectors.counting()    // Secondary collector computes the frequency counts [00:01:33]
                ));

        System.out.println("Approach 1 (Video charAt(0) Grouping Logic):");
        System.out.println(approach1Result); 
        // Output format: {a=3, b=2, c=1} [00:00:42, 00:00:51]


        // ====================================================================================
        // APPROACH 2: Handling Empty Strings Safely (Robust External Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but built safely to prevent `StringIndexOutOfBoundsException`. If the input 
        // list contains an empty string `""`, invoking `.charAt(0)` directly will crash the pipeline.
        //
        // STRATEGY:
        // Filter out empty or null strings using an explicit `.filter()` validation stage before 
        // attempting to extract indices, or conditionally map to a fallback category value.
        // ====================================================================================

        List<String> listWithEmptyStrings = Arrays.asList("apple", "", "banana", null, "blueberry");

        Map<Character, Long> approach2Result = listWithEmptyStrings.stream()
                .filter(str -> str != null && !str.isEmpty()) // Skips nulls and empty strings safely
                .collect(Collectors.groupingBy(
                        str -> str.charAt(0),
                        Collectors.counting()
                ));

        System.out.println("\nApproach 2 (Alternative Safe Empty/Null String Filtering):");
        System.out.println(approach2Result);
        // Output: {a=1, b=2}
    }
}