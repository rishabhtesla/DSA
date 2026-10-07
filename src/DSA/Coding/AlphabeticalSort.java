package DSA.Coding;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AlphabeticalSort {
    public static void main(String[] args) {
        // Input list of string elements from the video [00:00:10]
        List<String> str = Arrays.asList("Zudio", "Puma", "Addidas", "MAC", "H&M");

        // ====================================================================================
        // APPROACH 1: Using Standard .sorted() Natural Order (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of strings, write a Java 8 Stream program to sort the elements in 
        // natural alphabetical order [00:00:10].
        //
        // STRATEGY:
        // By invoking the standard parameterless `.sorted()` method, the stream sorts the strings 
        // in their natural case-sensitive Lexicographical/ASCII order [00:00:53].
        // ====================================================================================

        List<String> approach1Result = str.stream()
                .sorted() // Default natural sorting arrangement pattern [00:00:53]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Natural Sorted Order):");
        System.out.println(approach1Result); 
        // Output: [Addidas, H&M, MAC, Puma, Zudio] [00:00:10]


        // ====================================================================================
        // APPROACH 2: Case-Insensitive Alphabetical Sorting (Robust Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but robust against variations in upper/lowercase mixes. If names like 
        // "adidas" started with a lowercase 'a', case-sensitive natural sort would push it to the 
        // end because lowercase ASCII values are higher than uppercase values.
        //
        // STRATEGY:
        // Inject a custom case-insensitive comparator (`String.CASE_INSENSITIVE_ORDER`) into the 
        // `.sorted(...)` step to achieve a pure user-friendly alphabetical sort.
        // ====================================================================================

        List<String> mixedCaseList = Arrays.asList("zudio", "Puma", "Addidas", "mac", "H&M");

        List<String> approach2Result = mixedCaseList.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER) // Custom case-insensitive comparison
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Case-Insensitive Sort):");
        System.out.println(approach2Result);
        // Output: [Addidas, H&M, mac, Puma, zudio]
    }
}