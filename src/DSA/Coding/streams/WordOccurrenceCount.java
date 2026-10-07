package DSA.Coding.streams;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordOccurrenceCount {
    public static void main(String[] args) {
        String sentence = "Java is a programming language and Java is fun"; // Example sentence containing duplicates ("Java", "is") [00:00:08]

        // APPROACH 1: Using Function.identity() (Standard way shown in video)
        Map<String, Long> wordCountMap1 = Arrays.stream(sentence.split(" "))
                .collect(Collectors.groupingBy(
                        Function.identity(), // Key: The word itself [00:00:49]
                        Collectors.counting() // Value: The absolute count of that word [00:01:05]
                ));

        System.out.println("Output using Function.identity():");
        System.out.println(wordCountMap1); // Output: {Java=2, is=2, a=1, programming=1, language=1, and=1, fun=1} [00:00:15]


        // APPROACH 2: Using a Lambda Expression (Equivalent alternative variant)
        Map<String, Long> wordCountMap2 = Arrays.stream(sentence.split(" "))
                .collect(Collectors.groupingBy(
                        word -> word,        // Key: Explicit lambda returning the input string element [00:02:04]
                        Collectors.counting() // Value: Count of occurrences
                ));

        System.out.println("\nOutput using Lambda Expression:");
        System.out.println(wordCountMap2);
    }
}