package DSA.Coding;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SortMapValues {
    public static void main(String[] args) {
        // Sample unsorted map
        Map<String, Integer> budget = new HashMap<>();
        budget.put("clothes", 120);
        budget.put("grocery", 150);
        budget.put("transportation", 70);
        budget.put("rent", 1150);
        budget.put("miscellaneous", 45);

        System.out.println("Original Map: " + budget);

        // Java 8 Stream approach to sort by value
        Map<String, Integer> sortedByValue = budget.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()) // Sorts by value ascending
                .collect(Collectors.toMap(
                        Map.Entry::getKey, 
                        Map.Entry::getValue, 
                        (oldValue, newValue) -> oldValue, // Merge function (not needed here but required by syntax)
                        LinkedHashMap::new // Maintains insertion order
                ));
        Map<String, Integer> sortedByValueDesc = budget.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder())) // Reverse order
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        System.out.println("Sorted Map (Descending): " + sortedByValueDesc);
        System.out.println("Sorted Map (Ascending): " + sortedByValue);
    }
}