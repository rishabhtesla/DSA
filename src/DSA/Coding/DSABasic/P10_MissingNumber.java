package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P10: MISSING NUMBER
 *
 * Question
 * Find the absent value from distinct numbers in [0,n].
 *
 * Example
 * [3,0,1] -> 2.
 *
 * Constraints
 * n values are distinct and lie in [0,n].
 *
 * Approach used by edSlash
 * 1. XOR every index and value; equal pairs cancel, leaving the missing number.
 * 2. Scan the input while keeping the smallest amount of state needed to make the next decision.
 * 3. Update the result immediately when the current value proves a better answer or completes a valid condition.
 *
 * Dry run
 * Example from main: [3,0,1] -> 2.
 *   Visual XOR state table:
 *
 *   Step | index i | nums[i] | x before | calculation        | x after
 *   -----|---------|---------|----------|--------------------|--------
 *     0  |    -    |    -    |    3     | start with n = 3  |   3
 *     1  |    0    |    3    |    3     | 3 ^ 0 ^ 3          |   0
 *     2  |    1    |    0    |    0     | 0 ^ 1 ^ 0          |   1
 *     3  |    2    |    1    |    1     | 1 ^ 2 ^ 1          |   2
 *
 *   Matching values cancel because x ^ x = 0. The value left is 2,
 *   so return 2.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P10_MissingNumber {
    public static int missingNumber(int[] nums) {
        int x = nums.length;
        for (int i = 0; i < nums.length; i++) x ^= i ^ nums[i];
        return x;
    }

    public static void main(String[] args) {
        System.out.println(missingNumber(new int[]{3, 0, 1}));
    }
}
