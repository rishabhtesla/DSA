package DSA.Coding.streams;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MiddleCharacterStream {
    public static void main(String[] args) {
        String oddString = "education"; // Length 9 (Odd) -> Expects "a"
        String evenString = "travel";   // Length 6 (Even) -> Expects "av"

        // ====================================================================================
        // APPROACH 1: Using IntStream.range(), mapToObj(), and a Custom StringBuilder Collector
        //
        // PROBLEM STATEMENT:
        // Find and print the middle character(s) of a given string using the Java 8 Stream API. 
        // If the string length is odd, return the single center character. If it's even, return 
        // the two middle characters.
        //
        // FIXED METHOD:
        // Swapped the transcript typo `.mapToObject()` out for the structurally correct `.mapToObj()`.
        // This resolves the stream compilation flow and satisfies the StringBuilder method references.
        // ====================================================================================

        System.out.println("Approach 1 (Video IntStream Index + Fixed mapToObj Collector Logic):");
        System.out.println("Middle of 'education': " + getMiddleCharsVideoApproach(oddString)); // Output: a
        System.out.println("Middle of 'travel':    " + getMiddleCharsVideoApproach(evenString)); // Output: av


        // ====================================================================================
        // APPROACH 2: Idiomatic & Clean Pipeline Alternative (Optimal Substring Alternative)
        //
        // STRATEGY:
        // Pre-compute the exact indices directly using basic arithmetic formulas, then map the 
        // computed bounds into a clean `IntStream.range(start, end)` pipeline and transform them 
        // via `Collectors.joining()` to optimize readability.
        // ====================================================================================

        System.out.println("\nApproach 2 (Alternative Pre-computed Range + Collectors.joining):");
        System.out.println("Middle of 'education': " + getMiddleCharsOptimized(oddString));
        System.out.println("Middle of 'travel':    " + getMiddleCharsOptimized(evenString));
    }

    // Fixed implementation using the proper .mapToObj method call
    private static String getMiddleCharsVideoApproach(String str) {
        if (str == null || str.isEmpty()) return "";

        int len = str.length();
        int mid = len / 2;

        return IntStream.range(0, len)
                .filter(x -> len % 2 == 0
                        ? (x == mid || x == mid - 1)  // Handles two midpoints if even
                        : (x == mid)                  // Handles single midpoint if odd
                )
                .mapToObj(str::charAt) // FIXED: Replaced invalid mapToObject with mapToObj
                .collect(
                        StringBuilder::new,                 // Supplier: Instantiates container state
                        StringBuilder::append,              // Accumulator: Appends character elements linearly
                        StringBuilder::append               // Combiner: Merges parallel threads safely
                )
                .toString();
    }

    // Standard idiomatic alternative using pre-computed math limits
    private static String getMiddleCharsOptimized(String str) {
        if (str == null || str.isEmpty()) return "";

        int len = str.length();
        int mid = len / 2;

        // Compute structural offsets explicitly up front
        int start = (len % 2 == 0) ? mid - 1 : mid;
        int end = mid + 1; // upper bound is exclusive in IntStream.range()

        return IntStream.range(start, end)
                .mapToObj(i -> String.valueOf(str.charAt(i)))
                .collect(Collectors.joining());
    }
}