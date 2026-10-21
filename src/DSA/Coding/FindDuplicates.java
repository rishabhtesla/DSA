package DSA.Coding;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Finds values that occur more than once in a list.
 *
 * <p>Problem: return the distinct repeated values. Examples:
 * {@code [1,2,3,2] -> [2]}; {@code [4,4,5,4] -> [4]}.
 * Rechecking the entire list for every element is O(n^2); the existing
 * frequency-based stream is straightforward but also repeats work, while a
 * single map/set scan is expected O(n) time and O(n) space.</p>
 *
 * <p>Dry run for the main list: 1, 2, and 3 are eventually seen again, while
 * 4, 5, 6, and 7 are seen once. The invariant is that membership/frequency
 * checks describe the entire processed prefix. Ask whether duplicates should
 * be emitted once and whether their first-seen order must be retained.</p>
 */
public class FindDuplicates {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 2, 3, 6, 7, 3);

        // Filter elements that appear more than once
        Set<Integer> duplicates = numbers.stream()
                .filter(num -> Collections.frequency(numbers, num) > 1)
                .collect(Collectors.toSet());

        System.out.println("Duplicate elements: " + duplicates); 
        // Output: [2, 3]
    }
}