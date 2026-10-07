package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RemoveNonNumericCharacters {
    public static void main(String[] args) {
        // Example input list containing alphanumeric strings from the video [00:00:15]
        List<String> list = Arrays.asList("abc123", "a1b2c3", "123abc");

        // ====================================================================================
        // APPROACH 1: Pre-compiled Regex Pattern Matching (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of strings, write a Java 8 Stream program to remove all non-numeric 
        // characters from each string, leaving only the digits [00:00:00].
        //
        // STRATEGY:
        // 1. Compile a regex pattern `[^0-9]` (or `\\D`) which matches any character that is 
        //    NOT a number [00:00:50].
        // 2. Map over each string, apply the matcher, and call `replaceAll("")` to substitute 
        //    all matched non-digit characters with an empty string, effectively removing them [00:01:25, 00:01:55].
        // ====================================================================================

        Pattern pattern = Pattern.compile("[^0-9]"); // Matches any character that is NOT a digit [00:00:50]

        List<String> approach1Result = list.stream()
                .map(str -> pattern.matcher(str).replaceAll("")) // Replaces non-numeric matching entries with empty string [00:01:25]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Pattern Matcher Logic):");
        System.out.println(approach1Result); 
        // Output: [123, 123, 123] [00:00:21]


        // ====================================================================================
        // APPROACH 2: Clean Inline String replaceAll API (Simplified Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but written in a cleaner, more concise syntax style without needing 
        // an explicit external `Pattern` instance structure.
        //
        // STRATEGY:
        // Use the native `String.replaceAll(regex, replacement)` directly inside the map lambda.
        // The regex `\\D` represents any non-digit character shortcut in Java regex formatting.
        // ====================================================================================

        List<String> approach2Result = list.stream()
                .map(str -> str.replaceAll("\\D", "")) // \\D matches any character that is NOT a digit
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Inline String replaceAll):");
        System.out.println(approach2Result);
        // Output: [123, 123, 123]
    }
}