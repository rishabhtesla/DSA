package DSA.Coding;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Counts occurrences of each value in a list.
 *
 * <p>Problem: for an input sequence, report the frequency of every value.
 * Examples: {@code [1,2,1,3,2] -> {1=2,2=2,3=1}} and
 * {@code [7,7,7] -> {7=3}}. Comparing every pair is O(n^2); a frequency map
 * performs one pass in expected O(n) time and O(n) space.</p>
 *
 * <p>The map is exact for the processed prefix: each value increments its
 * existing count or creates a count of one. In the main data, the second 20
 * changes its count from one to two and the third changes it to three.
 * Critical questions: should zero-frequency values appear, and does output
 * ordering matter?</p>
 */
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