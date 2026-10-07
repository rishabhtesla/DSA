package DSA.Coding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FindDuplicatesEfficient {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Java", "Scala", "Python", "Java", "C++", "Scala");

        Set<String> seen = new HashSet<>();

        // If seen.add(name) returns false, it means it's a duplicate.
        // !seen.add(name) turns that false into true, letting it pass through the filter.
        Set<String> duplicates = names.stream()
                .filter(name -> !seen.add(name))
                .collect(Collectors.toSet());

        System.out.println("Duplicate names: " + duplicates); 
        // Output: [Java, Scala]
    }
}