package DSA.Coding.streams;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CharacterOccurrenceCount {
    public static void main(String[] args) {
        String word = "communication"; // Example input word containing repeating characters ('c', 'o', 'm', 'n', 'i')

        // APPROACH 1: Using Function.identity() (Standard syntax shown in the video)
        Map<String, Long> charCountMap1 = Arrays.stream(word.split("")) // Split by empty quotes to isolate characters [00:00:26]
                .collect(Collectors.groupingBy(
                        Function.identity(),  // Key: Evaluates to the character string itself [00:00:59]
                        Collectors.counting()  // Value: Computes the occurrence frequency count [00:01:13]
                ));

        System.out.println("Output using Function.identity():");
        System.out.println(charCountMap1); // Output format: {c=2, o=2, m=2, u=1, n=2, i=2, a=1, t=1} [00:01:51]


        // APPROACH 2: Using an Explicit Lambda Expression (Alternative equivalent variation)
        Map<String, Long> charCountMap2 = Arrays.stream(word.split(""))
                .collect(Collectors.groupingBy(
                        c -> c,               // Key: Simple inline lambda returning the character element exactly [00:02:00]
                        Collectors.counting()  // Value: Computes the occurrence frequency count
                ));

        System.out.println("\nOutput using Lambda Expression:");
        System.out.println(charCountMap2);
    }
}