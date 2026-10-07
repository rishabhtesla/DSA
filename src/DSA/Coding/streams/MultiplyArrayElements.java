package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MultiplyArrayElements {
    public static void main(String[] args) {
        // Setup base input array view from the video (using Integer wrappers instead of primitive int) [00:00:15]
        Integer[] arr = { 1, 2, 3, 4, 5 };
        
        // Note: Arrays.asList returns a fixed-size list structure. You can modify elements (set) 
        // but adding or removing elements throws an UnsupportedOperationException [00:00:48, 00:01:25].
        List<Integer> arrayList = Arrays.asList(arr);

        // ====================================================================================
        // APPROACH 1: Using Stream.reduce() returning an Optional (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given an array of integers, write a Java 8 Stream program to multiply all of its 
        // elements together sequentially [00:00:07].
        //
        // STRATEGY:
        // Use the terminal reduction method `.reduce((a, b) -> a * b)`. Because no initial identity 
        // value is supplied, this variation safely wraps the result inside an `Optional<Integer>` 
        // container to safely guard against processing empty lists [00:02:16, 00:03:06].
        // ====================================================================================

        Optional<Integer> approach1Result = arrayList.stream()
                .reduce((a, b) -> a * b); // Continually multiplies elements sequentially [00:02:45]

        System.out.println("Approach 1 (Video Optional Reduction Logic):");
        if (approach1Result.isPresent()) { // Safe validation guard pattern [00:03:30]
            System.out.println("Product = " + approach1Result.get()); // Extract using .get() [00:03:52]
        }
        // Output: Product = 120


        // ====================================================================================
        // APPROACH 2: Using reduce() with an Identity Element (Direct Value Alternative)
        //
        // PROBLEM STATEMENT:
        // Same multiplication goal, but structured to return a primitive/unwrap direct integer 
        // without processing or managing an intermediate `Optional` checking layer.
        //
        // STRATEGY:
        // Supply an initial default identity value of `1` into the `.reduce(1, (a, b) -> a * b)` 
        // parameters. Providing an identity value shifts the return signature directly to a plain 
        // integer type because an empty stream fallback defaults explicitly to the identity constant.
        // ====================================================================================

        int approach2Result = arrayList.stream()
                .reduce(1, (a, b) -> a * b); // Starts calculation with an identity basis of 1

        System.out.println("\nApproach 2 (Alternative Identity-driven Direct Reduction):");
        System.out.println("Product = " + approach2Result);
        // Output: Product = 120
    }
}