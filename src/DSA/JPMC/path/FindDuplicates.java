package DSA.JPMC.path; /**
 * PROBLEM: How to find duplicate strings in a list of strings?
 * 
 * EXAMPLE:
 * Input: ["apple", "banana", "apple", "orange", "banana", "grape"]
 * Output: ["apple", "banana"]
 * 
 * LOGIC:
 * A HashSet's `add()` method returns 'true' if the element is successfully added (it was new).
 * It returns 'false' if the element was ALREADY in the set.
 * We can use this property inside a Java 8 Stream `filter` operation:
 * If `set.add(item)` is false, we know it's a duplicate, so we let it pass through the filter 
 * and collect it into our results.
 * 
 * COMPLEXITY: Time: O(N), Space: O(N) for the Set.
 */
import java.util.*;
import java.util.stream.Collectors;

public class FindDuplicates {
    public static void main(String[] args) {
        List<String> fruits = Arrays.asList(
            "apple", "banana", "apple", "orange", "banana", "grape"
        );
        
        // Set to track elements we have already seen
        Set<String> seenElements = new HashSet<>();
        
        // Use Java 8 Streams to filter and collect duplicates
        Set<String> duplicates = fruits.stream()
            // .add() returns false if the element is already present
            // The '!' operator flips it to true, so duplicates pass the filter
            .filter(fruit -> !seenElements.add(fruit))
            .collect(Collectors.toSet());
            
        System.out.println("Original List: " + fruits);
        System.out.println("Duplicate Items: " + duplicates); 
        // Output: [apple, banana]
    }
}