package DSA.Coding;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DuplicateCount {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 20, 30, 20, 40, 10, 20, 50);

        // Group by element and count occurrences
        Map<Integer, Long> duplicateCountMap = numbers.stream()
                .collect(Collectors.groupingBy(num -> num, Collectors.counting())) // Returns Map<Integer, Long>
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1) // Keep only elements that appear > 1 time
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("Duplicates and their counts: " + duplicateCountMap);
        // Output: {10=2, 20=3}
    }
}