package DSA.Coding.streams;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RearrangeNumbers {
    public static void main(String[] args) {
        int[] arr = { 172, 3, 2, 5, 4 }; // Example input array [00:01:22]

        // APPROACH 1: Ascending Order (To find the Lowest Value Combination) [00:01:15]
        List<Integer> ascendingResult = Arrays.stream(arr)
                // Convert primitive int values to Integer objects [00:00:46]
                .mapToObj(Integer::valueOf) 
                // Natural sorting order (smallest to largest) [00:00:58]
                .sorted() 
                .collect(Collectors.toList());

        System.out.println("Ascending Order (Lowest): " + ascendingResult);
        // Output: [2, 3, 4, 5, 172] [00:01:38]


        // APPROACH 2: Descending Order (To find the Highest Value Combination) [00:01:44]
        List<Integer> descendingResult = Arrays.stream(arr)
                // Convert primitive int values to Integer objects
                .mapToObj(Integer::valueOf) 
                // Sort with reverse order comparator (largest to smallest) [00:01:51]
                .sorted(Collections.reverseOrder()) 
                .collect(Collectors.toList());

        System.out.println("Descending Order (Highest): " + descendingResult);
        // Output: [172, 5, 4, 3, 2] [00:02:04]
    }
}