package DSA.JPMC.path; /**
 * PROBLEM: Given an array, remove all odd numbers, multiply each remaining number 
 * by a constant, and return the sum using Java Streams.
 * 
 * EXAMPLE: Array: [1, 2, 3, 4, 5], Constant: 10
 * Even numbers: [2, 4]. Multiplied: [20, 40]. Sum: 60.
 * 
 * LOGIC:
 * Arrays.stream() -> .filter(num % 2 == 0) -> .map(num * constant) -> .sum()
 */
import java.util.Arrays;

public class StreamOperations {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6};
        int constant = 10;

        // Declarative Java 8 Streams pipeline
        long sum = Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)      // Keep only even numbers
                .map(n -> n * constant)       // Multiply by constant
                .sum();                       // Aggregate sum

        System.out.println("Resulting Sum: " + sum); // Output: 120 (20+40+60)
    }
}