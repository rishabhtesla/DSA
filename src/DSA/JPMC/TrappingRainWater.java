package DSA.JPMC;

/**
 * PROBLEM: Trapping Rain Water
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
 * compute how much water it can trap after raining.
 * 
 * EXAMPLE:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * 
 * LOGIC:
 * Water trapped above any bar 'i' depends on: min(maxLeftHeight, maxRightHeight) - height[i].
 * Instead of pre-calculating left and right maxes in O(N) space, use Two Pointers.
 * Pointers 'left' and 'right' start at opposite ends. 
 * We track 'leftMax' and 'rightMax'. 
 * If leftMax < rightMax, the bottleneck is on the left. We can confidently calculate trapped water 
 * at 'left' based strictly on 'leftMax'. We add it to the total and increment 'left'.
 * Vice versa for the right side.
 * 
 * DRY RUN (Simplified): [0, 1, 0, 2]
 * - L=0, R=3. leftMax=0, rightMax=2.
 * - leftMax (0) < rightMax (2). Calculate L: max(0,0) - 0 = 0 water. L++.
 * - L=1 (val=1), R=3. leftMax=1, rightMax=2. L < R. Calculate L: max(1,1) - 1 = 0 water. L++.
 * - L=2 (val=0), R=3. leftMax=1, rightMax=2. L < R. Calculate L: 1 - 0 = +1 water. L++.
 * Result = 1.
 * 
 * COMPLEXITY: Time: O(N) (One pass). Space: O(1)
 */
/**
 * Learning supplement: per-index boundary scans are O(n^2); two pointers use
 * the lower known wall to compute water in O(n) time and O(1) space.
 * Examples: {@code [0,1,0,2,1,0,1,3,2,1,2,1] -> 6} and
 * {@code [4,2,0,3,2,5] -> 9}. Invariant: processed sides have their maximum
 * wall known, so the lower side's water is final. Ask why the lower wall is
 * sufficient.
 */
public class TrappingRainWater {
    public static int trap(int[] height) {
        if (height == null || height.length == 0) return 0;
        
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int waterTrapped = 0;
        
        while (left < right) {
            // Update maximums seen so far
            if (height[left] > leftMax) leftMax = height[left];
            if (height[right] > rightMax) rightMax = height[right];
            
            // The side with the lower max is the bottleneck, so we calculate water there
            if (leftMax < rightMax) {
                waterTrapped += Math.max(0, leftMax - height[left]);
                left++;
            } else {
                waterTrapped += Math.max(0, rightMax - height[right]);
                right--;
            }
        }
        return waterTrapped;
    }

    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Water trapped: " + trap(height)); // Output: 6
    }
}