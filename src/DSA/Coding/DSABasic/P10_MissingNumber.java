package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find the absent value from distinct numbers in [0,n].
 * EXAMPLE: [3,0,1] -> 2.
 * CONSTRAINTS: n values are distinct and lie in [0,n].
 * LOGIC / ALGORITHM:
 * 1. XOR every index and value; equal pairs cancel, leaving the missing number.
 * 2. Update the running result using the current value and retain only information that can affect later choices.
 * 3. Return the accumulated result after the final element.
 * INVARIANT: At each iteration, the processed prefix is already correct and only the remaining suffix can change the answer.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input [3,0,1] -> 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P10_MissingNumber {
    public static int missingNumber(int[] a) {
        int x = a.length;
        for (int i = 0; i < a.length; i++) x ^= i ^ a[i];
        return x;
    }

    public static void main(String[] args) {
        System.out.println(missingNumber(new int[]{3, 0, 1}));
    }
}
