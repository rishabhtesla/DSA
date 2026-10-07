package DSA.Coding.streams; /**
 * PROBLEM STATEMENT:
 * Given an integer array containing duplicate values, write a Java 8 Stream 
 * program to compute the sum of only its unique elements [00:00:14].
 * * Example Input:  { 1, 2, 3, 4, 4, 3, 8, 8 }
 * Expected Sum:   18 (1 + 2 + 3 + 4 + 8)
 */
import java.util.Arrays;

public class UniqueElementsSum {
    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 4, 3, 8, 8 };

        int uniqueSum = Arrays.stream(arr)
                .distinct() // Filters out the duplicates [00:00:54]
                .sum();      // Terminal operation summing unique elements

        System.out.println("Sum of unique elements: " + uniqueSum);
    }
}