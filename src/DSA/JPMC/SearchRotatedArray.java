package DSA.JPMC;

/**
 * PROBLEM: Search in Rotated Sorted Array
 * There is an integer array nums sorted in ascending order (with distinct values).
 * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k.
 * Given the array nums after the possible rotation and an integer target, return the index of target 
 * if it is in nums, or -1 if it is not in nums. Must run in O(log n) time.
 * 
 * EXAMPLE:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * 
 * LOGIC:
 * Since it's O(log n), we must use Binary Search. 
 * Even if an array is rotated, dividing it in half guarantees that at least one half is perfectly sorted.
 * 1. Find the mid point.
 * 2. Check if the Left half is strictly sorted (nums[left] <= nums[mid]).
 *    - If it is, check if the target falls within the bounds of this sorted half.
 *    - If yes, eliminate the right half (right = mid - 1). Else, eliminate the left half.
 * 3. If the Left half isn't sorted, the Right half MUST be strictly sorted.
 *    - Check if target falls within the bounds of the Right half.
 *    - Eliminate accordingly.
 * 
 * COMPLEXITY: Time: O(log N), Space: O(1)
 */
public class SearchRotatedArray {
    
    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) return mid;
            
            // Left portion is sorted
            if (nums[left] <= nums[mid]) {
                // Target is in the left sorted portion
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1; // target is on the right side
                }
            } 
            // Right portion is sorted
            else {
                // Target is in the right sorted portion
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1; // target is on the left side
                }
            }
        }
        
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        System.out.println("Index of target: " + search(nums, target)); // Output: 4
    }
}