package DSA.Coding.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiplyMirrorElements {
    public static void main(String[] args) {
        int[] arr = { 4, 5, 1, 7, 2, 9 }; // Array size: 6 (Even size example) [00:02:06]

        // ====================================================================================
        // APPROACH 1: Using IntStream.range up to Array Length / 2 (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given an array of integers, write a Java 8 Stream program to multiply the first and 
        // last element, second and second-to-last element, and so on [00:00:08].
        //
        // STRATEGY: 
        // 1. Generate an IntStream index track from 0 to half of the array's length (arr.length / 2) [00:01:13].
        // 2. Map each index `x` to the product of `arr[x]` and its mirror index `arr[length - x - 1]` [00:01:22].
        // ====================================================================================

        List<Integer> approach1Result = IntStream.range(0, arr.length / 2) // Generates indexes 0, 1, 2 [00:02:32]
                .map(x -> arr[x] * arr[arr.length - x - 1]) // Multiplies matching pairs (e.g., index 0 * index 5) [00:03:13]
                .boxed()
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Split-Index Logic):");
        System.out.println(approach1Result); 
        // Output: [36, 10, 7] (4*9=36, 5*2=10, 1*7=7) [00:00:40]


        // ====================================================================================
        // APPROACH 2: Alternative Handling Odd-Length Arrays (Robust Modification)
        //
        // PROBLEM STATEMENT:
        // Same as above, but built to correctly handle an odd-sized array by carrying the middle 
        // element untouched (multiplying it by itself or leaving it alone) depending on custom logic.
        //
        // STRATEGY:
        // By taking the ceiling or using Math.ceil for the range loop, we ensure that if an array 
        // has 7 elements, the 4th element (index 3) is processed safely without an out-of-bounds error.
        // ====================================================================================

        int[] oddArr = { 4, 5, 1, 3, 7, 2, 9 }; // Array size: 7 (Odd size example)
        
        // Calculate dynamic range range limit: (length + 1) / 2 captures the true middle element
        int loopLimit = (oddArr.length + 1) / 2; 

        List<Integer> approach2Result = IntStream.range(0, loopLimit)
                .map(x -> oddArr[x] * oddArr[oddArr.length - x - 1])
                .boxed()
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Odd Array Compatibility Check):");
        System.out.println(approach2Result);
        // Output: [36, 10, 7, 9] (4*9=36, 5*2=10, 1*7=7, and middle 3*3=9)
    }
}