package DSA.Coding.streams;

import java.util.Arrays;
import java.util.Comparator;

public class SecondHighestLengthWord {
    public static void main(String[] args) {
        String sentence = "I am learning Java 8 streams API"; // Example sentence

        // APPROACH 1: Finding the Longest Word (using skip(0))
        String longestWord = Arrays.stream(sentence.split(" "))
                // Sort by length in descending order
                .sorted(Comparator.comparing(String::length).reversed()) 
                // Skip 0 elements, keeping the absolute maximum length word at index 0
                .skip(0) 
                .findFirst()
                .orElse(""); // Safely handles empty results instead of using naked .get()

        // Output: learning (length: 8)
        System.out.println("Longest word: " + longestWord); 


        // APPROACH 2: Finding the Second Longest Word (using skip(1))
        String secondLongestWord = Arrays.stream(sentence.split(" "))
                // Sort by length in descending order [00:00:35]
                .sorted(Comparator.comparing(String::length).reversed()) 
                // Skip the first element (the highest length word: "learning") [00:01:18]
                .skip(1) 
                // Retrieve the next element in the pipeline [00:01:24]
                .findFirst() 
                .orElse("");

        // Output: streams (length: 7) [00:02:07]
        System.out.println("Second longest word: " + secondLongestWord); 
        
        
        // BONUS: Dynamic 'Nth' Longest Word
        int n = 3; // To find the 3rd longest word ("streams" -> "learning" -> "Java"/"stream")
        String nthLongestWord = Arrays.stream(sentence.split(" "))
                .sorted(Comparator.comparing(String::length).reversed())
                .skip(n - 1) // Skips the first (n-1) elements to arrive at the nth element [00:02:20]
                .findFirst()
                .orElse("");
                
        System.out.println(n + "rd longest word: " + nthLongestWord);
    }
}