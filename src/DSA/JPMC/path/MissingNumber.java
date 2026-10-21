package DSA.JPMC.path;

/**
 * PROBLEM: Missing Number
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, 
 * find the one that is missing from the array.
 * 
 * EXAMPLE: Input: [3, 0, 1] -> Output: 2 (Array size is 3, so expected numbers are 0,1,2,3)
 * 
 * LOGIC:
 * Use the mathematical formula for the sum of the first N numbers: Sum = N * (N + 1) / 2
 * Calculate the expected sum using the length of the array.
 * Calculate the actual sum of the array.
 * The difference between Expected Sum and Actual Sum is the missing number!
 * 
 * COMPLEXITY: Time: O(N), Space: O(1)
 */
/**
 * Learning supplement: sorting costs O(n log n) and a boolean table costs
 * O(n) space; XOR or arithmetic finds the missing value in O(n) time/O(1)
 * space. Examples: {@code [3,0,1] -> 2} and {@code [0,1] -> 2}. Invariant:
 * XOR cancels every present value with its expected counterpart. For the main
 * input, 0^1^2^3 combined with 3^0^1 leaves 2. Ask why XOR avoids overflow.
 */
public class MissingNumber {
    public static int missingNumber(int[] nums) {
        int n = nums.length;
        
        // Expected sum of numbers from 0 to N
        int expectedSum = n * (n + 1) / 2;
        
        // Actual sum of the elements in the array
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }

    public static void main(String[] args) {
        System.out.println("Missing number: " + missingNumber(new int[]{3, 0, 1})); // 2
    }
}