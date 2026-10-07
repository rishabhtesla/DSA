package DSA.Coding;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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