package DSA.Coding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Finds repeated strings with a set of already-seen values.
 *
 * <p>Problem: identify each distinct name that appears at least twice.
 * Examples: {@code [Java,Scala,Java] -> [Java]} and
 * {@code [A,B,C] -> []}. A nested comparison is O(n^2); inserting into a
 * hash set is expected O(n) time and O(n) space.</p>
 *
 * <p>When {@code seen.add(value)} is false, the value has appeared earlier and
 * is a duplicate. For the main input, Java and Scala are each inserted once
 * and then recognized on their second occurrence. The invariant is that
 * {@code seen} contains every distinct processed value. Consider whether the
 * input order or duplicate output order is part of the contract.</p>
 */
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