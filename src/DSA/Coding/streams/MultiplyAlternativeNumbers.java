package DSA.Coding.streams;

import java.util.stream.IntStream;

public class MultiplyAlternativeNumbers {
    public static void main(String[] args) {
        // Input array as defined in the video [00:00:08]
        // Indices:   0  1  2  3  4  5  6
        int[] arr = { 4, 5, 1, 7, 2, 9, 2 }; 
        
        // Final Stream pipeline execution to reduce alternative elements to their product
        int result = IntStream.range(0, arr.length) 
            /*
             * BREAKDOWN 1: IntStream.range(0, arr.length)
             * Generates a sequential stream of primitive int values representing indices.
             * For this array, it yields indices from 0 up to 6 (exclusive of 7) [00:00:31].
             */
            
            .filter(i -> i % 2 == 0) 
            /*
             * BREAKDOWN 2: .filter(i -> i % 2 == 0)
             * Keeps only the even index positions (0, 2, 4, 6) which isolates the alternate numbers.
             * By selecting even indices, we capture elements: arr[0]=4, arr[2]=1, arr[4]=2, arr[6]=2 [00:00:54].
             * (Note: Changing this to 'i % 2 != 0' would target odd indices instead [00:02:01]).
             */
            
            .map(i -> arr[i]) 
            /*
             * BREAKDOWN 3: .map(i -> arr[i])
             * Maps the isolated index stream back to the actual values residing inside the array [00:01:13].
             * The stream transforms from indices [0, 2, 4, 6] -> values [4, 1, 2, 2].
             */
            
            .reduce(1, (a, b) -> a * b); 
            /*
             * BREAKDOWN 4: .reduce(1, (a, b) -> a * b)
             * Performs a reduction operation to multiply the stream elements together [00:01:24].
             * '1' serves as the starting identity element for multiplication.
             * Sequence: 1 * 4 = 4 -> 4 * 1 = 4 -> 4 * 2 = 8 -> 8 * 2 = 16.
             */

        // Expected output is 16 (4 * 1 * 2 * 2) [00:01:47]
        System.out.println("Product of alternative numbers: " + result); 
    }
}