package DSA.Coding.streams;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstRepeatedCharacter {
    public static void main(String[] args) {
        String input = "hello world"; // Example input string [00:00:22]

        // ====================================================================================
        // PROBLEM STATEMENT:
        // Given a string, find the first repeated character using Java 8 Streams [00:00:22].
        // 
        // STRATEGY:
        // 1. Convert the string to a stream of characters [00:00:33].
        // 2. Group characters into a LinkedHashMap with their occurrence frequencies [00:01:17].
        //    Using a LinkedHashMap is vital to preserve the string's original encounter order [00:02:19].
        // 3. Convert the map entries back to a stream, filter for values greater than 1 [00:03:11],
        //    map to get the key (the character), and grab the first one [00:03:31, 00:03:44].
        // ====================================================================================

        Character firstRepeated = input.chars() // Instream of primitive character values [00:00:33]
                .mapToObj(c -> (char) c) // Box primitives to Character objects [00:00:46]
                .collect(Collectors.groupingBy(
                        Function.identity(),   // Key: The character itself [00:01:09]
                        LinkedHashMap::new,    // Map Factory: LinkedHashMap maintains the original insertion order [00:02:19]
                        Collectors.counting()  // Value: Counts character frequencies [00:01:17]
                )) // Returns a LinkedHashMap<Character, Long> [00:01:28]
                .entrySet()
                .stream() // Streams the entry sets of the collected map [00:03:03]
                .filter(entry -> entry.getValue() > 1) // Keeps characters appearing more than once (repeated) [00:03:11]
                .map(Map.Entry::getKey) // Extract the Character key from the filtered map entry rows [00:03:31]
                .findFirst() // Terminate the stream to fetch the first entry passing the criteria [00:03:44]
                .orElse(null); // Safe fallback extraction structure

        // Output: L (Both 'l' and 'o' are repeated, but 'l' occurs first in the string) [00:02:02, 00:03:55]
        System.out.println("First repeated character: " + firstRepeated);
    }
}