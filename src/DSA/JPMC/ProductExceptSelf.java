package DSA.JPMC; /**
 * PROBLEM: Product of Array Except Self
 * Given an integer array nums, return an array answer such that answer[i] is equal 
 * to the product of all the elements of nums except nums[i].
 * YOU MUST WRITE AN ALGORITHM THAT RUNS IN O(N) TIME AND WITHOUT USING THE DIVISION OPERATION.
 * 
 * EXAMPLE:
 * Input: nums = [1, 2, 3, 4]
 * Output: [24, 12, 8, 6]
 * 
 * LOGIC:
 * Since we can't use division, the product except self at index 'i' is exactly:
 * (Product of all elements to the LEFT of i) * (Product of all elements to the RIGHT of i).
 * We can do this in two passes:
 * 1. Calculate left products and store them in the answer array.
 * 2. Calculate right products on the fly and multiply them with the left products.
 * 
 * DRY RUN: nums = [1, 2, 3, 4]
 * Pass 1 (Left Products):
 * - ans[0] = 1 (nothing to the left)
 * - ans[1] = ans[0] * nums[0] = 1 * 1 = 1
 * - ans[2] = ans[1] * nums[1] = 1 * 2 = 2
 * - ans[3] = ans[2] * nums[2] = 2 * 3 = 6
 * ans = [1, 1, 2, 6]
 * 
 * Pass 2 (Right Products): (rightMultiplier starts at 1)
 * - i=3: ans[3] = ans[3] * right (6*1=6). right = right * nums[3] (1*4=4)
 * - i=2: ans[2] = ans[2] * right (2*4=8). right = right * nums[2] (4*3=12)
 * - i=1: ans[1] = ans[1] * right (1*12=12). right = right * nums[1] (12*2=24)
 * - i=0: ans[0] = ans[0] * right (1*24=24). right = right * nums[0] (24*1=24)
 * Final ans = [24, 12, 8, 6]
 * 
 * COMPLEXITY: Time: O(N), Space: O(1) (excluding output array)
 */
import java.util.Arrays;

/**
 * Learning supplement: division is unsafe with zeroes; prefix/suffix products
 * run in O(n) time and O(1) extra space beyond output. Examples:
 * {@code [1,2,3,4] -> [24,12,8,6]} and
 * {@code [-1,1,0,-3,3] -> [0,0,9,0,0]}. Invariant: after the first pass each
 * slot contains its left product; the reverse pass multiplies its right
 * product. Ask why two zeroes make every result zero.
 */
public class ProductExceptSelf {
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // Step 1: Calculate left products
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        
        // Step 2: Calculate right products and multiply
        int rightMultiplier = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] = result[i] * rightMultiplier;
            rightMultiplier *= nums[i]; // Update for the next iteration
        }
        
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println("Product array: " + Arrays.toString(productExceptSelf(nums))); 
        // Output: [24, 12, 8, 6]
    }
}