package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterWordsByVowelCount {
    public static void main(String[] args) {
        String sentence = "I am learning streams API in Java"; // Example sentence
        int targetVowelCount = 2; // Target number of vowels [00:00:15]

        // Single pipeline approach collecting to a List as demonstrated across the playlist
        List<String> result = Arrays.stream(sentence.split(" "))
                .filter(word -> {
                    // Regex breakdown [00:00:56]:
                    // [^aeiouAEIOU] matches any character that is NOT a vowel (case-insensitive).
                    // replaceAll replaces those non-vowel characters with an empty string "".
                    String vowelsOnly = word.replaceAll("[^aeiouAEIOU]", "");
                    
                    // If "streams" -> "ea" -> length is 2 [00:01:27]
                    return vowelsOnly.length() == targetVowelCount; 
                })
                .collect(Collectors.toList());

        // Output: [streams, API, Java] (Each contains exactly 2 vowels) [00:00:22]
        System.out.println("Words with exactly " + targetVowelCount + " vowels: " + result);
    }
}