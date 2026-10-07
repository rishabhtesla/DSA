package DSA.Coding.streams;

import java.util.Arrays;
import java.util.Comparator;

public class SecondHighestWordLength {
    public static void main(String[] args) {
        String sentence = "I am learning Java 8 streams API"; // Example sentence

        // APPROACH 1: Get the exact length of the second longest word (As shown in video)
        int secondHighestLength = Arrays.stream(sentence.split(" "))
                // Map each word to its integer length value [00:00:31]
                .map(String::length)
                // Sort the lengths in descending order [00:00:38]
                .sorted(Comparator.reverseOrder())
                // Skip the maximum length element (8) to target the second highest [00:01:38]
                .skip(1)
                // Retrieve the next remaining value in the pipeline [00:00:55]
                .findFirst()
                // Safely extract the primitive int value from the Optional [00:01:02]
                .orElse(0); 

        // Output: 7 (Matches the length of "streams") [00:01:20]
        System.out.println("Second highest word length: " + secondHighestLength);


        // APPROACH 2: Alternative using mapToInt (More optimal memory footprint)
        // This avoids boxing to Integer objects and works directly with primitive IntStream
        int secondHighestLengthOptimal = Arrays.stream(sentence.split(" "))
                .mapToInt(String::length)
                .boxed() // Boxed to apply object-based Comparator.reverseOrder()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElse(0);

        System.out.println("Second highest word length (Optimal): " + secondHighestLengthOptimal);
    }
}