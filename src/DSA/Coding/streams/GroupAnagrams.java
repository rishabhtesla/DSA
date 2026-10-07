package DSA.Coding.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GroupAnagrams {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("pan", "nap", "Pat", "tap", "team", "meet", "tree");

        Collection<List<String>> groupedAnagrams = list.stream()
            .collect(Collectors.groupingBy(
                word -> Arrays.stream(word.toLowerCase().split(""))
                              .sorted()
                              .collect(Collectors.joining())
            ))
            .values(); // Extracting values to match the collection-based result

        // Print the final result
        System.out.println(groupedAnagrams);
    }
}