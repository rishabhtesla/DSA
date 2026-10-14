package DSA.JPMC.path.theory;

import java.util.*;
import java.util.stream.Collectors;

public class MapSortingDemo {
    public static void main(String[] args) {
        Map<String, Integer> unsortedMap = new HashMap<>();
        unsortedMap.put("Zebra", 50);
        unsortedMap.put("Apple", 20);
        unsortedMap.put("Mango", 90);

        // --- 1. SORT BY KEYS ---
        // TreeMap automatically sorts by keys in natural alphabetical order.
        Map<String, Integer> sortedByKey = new TreeMap<>(unsortedMap);
        System.out.println("Sorted by Key: " + sortedByKey); 
        // Output: {Apple=20, Mango=90, Zebra=50}

        // --- 2. SORT BY VALUES (Using Java 8 Streams) ---
        Map<String, Integer> sortedByValue = unsortedMap.entrySet()
            .stream()
            // Compare map entries by their Value
            .sorted(Map.Entry.comparingByValue())
            // Collect back into a LinkedHashMap to preserve the sorted insertion order
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (oldValue, newValue) -> oldValue, // Merge function (not needed here but required by API)
                LinkedHashMap::new                // Map factory
            ));
            
        System.out.println("Sorted by Value: " + sortedByValue);
        // Output: {Apple=20, Zebra=50, Mango=90}
    }
}