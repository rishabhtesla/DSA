package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeparateEvenOdd {
    public static void main(String[] args) {
        int[] rawArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        // Step 1: Convert primitive int[] to List<Integer> using boxed() [00:00:33]
        List<Integer> list = Arrays.stream(rawArray)
                .boxed()
                .collect(Collectors.toList());

        
        // APPROACH 1: Grouping by criteria resulting in a Map<Boolean, List<Integer>> [00:01:28]
        // Key 'true' holds even numbers, key 'false' holds odd numbers [00:02:38]
        Map<Boolean, List<Integer>> groupedMap = list.stream()
                .collect(Collectors.groupingBy(num -> num % 2 == 0)); // Uses a Function [00:05:00]

        System.out.println("Approach 1 Map: " + groupedMap);
        // Output: {false=[1, 3, 5, 7, 9], true=[2, 4, 6, 8]}


        // APPROACH 2: Extracting only the List values (Flattening out the Map keys) [00:03:00]
        List<List<Integer>> separatedLists = list.stream()
                .collect(Collectors.groupingBy(num -> num % 2 == 0))
                .entrySet()
                .stream()
                .map(Map.Entry::getValue) // Bypasses the Boolean keys, taking only the values [00:03:33]
                .collect(Collectors.toList());

        System.out.println("Approach 2 Flattened List: " + separatedLists);
        // Output: [[1, 3, 5, 7, 9], [2, 4, 6, 8]]


        // APPROACH 3: Using Collectors.partitioningBy(...) [00:04:34]
        // A specialized variation of groupingBy tailored for Boolean operations
        Map<Boolean, List<Integer>> partitionedMap = list.stream()
                .collect(Collectors.partitioningBy(num -> num % 2 == 0)); // Uses a Predicate [00:05:00]

        System.out.println("Approach 3 Partitioned Map: " + partitionedMap);
        // Output: {false=[1, 3, 5, 7, 9], true=[2, 4, 6, 8]}
    }
}