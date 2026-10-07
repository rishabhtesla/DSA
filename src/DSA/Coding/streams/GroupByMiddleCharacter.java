package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupByMiddleCharacter {
    public static void main(String[] args) {
        String[] arr = { "ewe", "kwk", "jhj", "aha", "j" }; // Example input string array [00:00:24]

        // ====================================================================================
        // APPROACH 1: Hardcoded Substring Tracking for 3-Letter Words (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given an array of strings, group the strings based on their middle character using Java 8 Streams [00:00:00].
        //
        // STRATEGY:
        // Use a static `substring(1, 2)` invocation on each string elements. This assumes 3-character strings,
        // where index 1 is strictly the center character [00:01:18, 00:01:53].
        // ====================================================================================

        Map<String, List<String>> approach1Result = Stream.of(arr) // Creates a stream from the array [00:00:53]
                .collect(Collectors.groupingBy(
                        str -> str.substring(1, 2), // Extracts the middle character assuming string length is 3 [00:01:18]
                        Collectors.toList()
                ));

        System.out.println("Approach 1 (Video Hardcoded Substring Logic):");
        System.out.println(approach1Result);
        // Output: {h=[jhj, aha], w=[ewe, kwk]} (Throws error on "j" if processed, assuming 3 chars) [00:00:31]


        // ====================================================================================
        // APPROACH 2: Dynamic Midpoint Calculation (Robust External Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but generalized to support strings of varying lengths dynamically 
        // (e.g., handles "j", "ewe", or longer strings perfectly without IndexOutOfBoundsException).
        //
        // STRATEGY:
        // For each string, calculate the midpoint dynamically using `length() / 2`. 
        // Extract that single targeted character as a `String` key using `String.valueOf(str.charAt(mid))`.
        // ====================================================================================

        Map<String, List<String>> approach2Result = Arrays.stream(arr)
                .collect(Collectors.groupingBy(
                        str -> {
                            if (str == null || str.isEmpty()) return "";
                            int mid = str.length() / 2; // Dynamically computes middle index (e.g., 3/2 = 1, 1/2 = 0)
                            return String.valueOf(str.charAt(mid)); // Safely grabs character at mid index
                        },
                        Collectors.toList()
                ));

        System.out.println("\nApproach 2 (Alternative Dynamic Length Logic):");
        System.out.println(approach2Result);
        // Output: {j=[j], h=[jhj, aha], w=[ewe, kwk]}
    }
}