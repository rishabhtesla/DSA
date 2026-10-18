package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * HACKERRANK QUESTION 11: Trapping Rain Water
 * ============================================================================
 *
 * Problem Statement:
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it can trap after raining.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the number of elevation bars.
 * - Next n lines: Each line contains an integer height[i].
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - 0 <= height[i] <= 10^5
 *
 * Example:
 * Input:
 *   12
 *   0
 *   1
 *   0
 *   2
 *   1
 *   0
 *   1
 *   3
 *   2
 *   1
 *   2
 *   1
 * Output:
 *   6
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Principle:
 *    Water trapped at any index i is:
 *        trapped[i] = max(0, min(maxLeft[i], maxRight[i]) - height[i])
 *
 * 2. Two-Pointer Optimization:
 *    - Place `left = 0` and `right = n - 1`.
 *    - Maintain `leftMax = 0` and `rightMax = 0`.
 *    - If `height[left] <= height[right]`:
 *        - The bottleneck is guaranteed to be on the left because leftMax is
 *          strictly less than or equal to whatever barrier exists on the right.
 *        - If height[left] >= leftMax: update leftMax = height[left].
 *        - Else: water trapped at left = leftMax - height[left].
 *        - left++
 *    - Else (`height[left] > height[right]`):
 *        - The bottleneck is bounded by rightMax.
 *        - If height[right] >= rightMax: update rightMax = height[right].
 *        - Else: water trapped at right = rightMax - height[right].
 *        - right--
 *
 * Complexity:
 * - Time: O(n) single pass
 * - Space: O(1) auxiliary space
 *
 * ============================================================================
 * DRY RUN (height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]):
 * ============================================================================
 * left=0, right=11, leftMax=0, rightMax=0, water=0
 * - h[0]=0 <= h[11]=1: leftMax=0, water+=0, left->1
 * - h[1]=1 <= h[11]=1: leftMax=1, water+=0, left->2
 * - h[2]=0 <= h[11]=1: h[2] < leftMax(1) -> water += (1 - 0) = 1, left->3
 * - h[3]=2 >  h[11]=1: right side bottleneck:
 *     - rightMax=1, water+=0, right->10
 * - h[3]=2 <= h[10]=2:
 *     - leftMax=max(1, 2)=2, water+=0, left->4
 * Traversal proceeds until left and right meet, accumulating:
 * index 2 (1) + index 4 (1) + index 5 (2) + index 6 (1) + index 9 (1) = 6 units.
 *
 * Final Result: 6
 * ============================================================================
 */
public class SolutionTrappingRainWater {

    public static long trap(List<Integer> height) {
        if (height == null || height.size() <= 2) {
            return 0L;
        }

        int left = 0;
        int right = height.size() - 1;
        long leftMax = 0;
        long rightMax = 0;
        long trappedWater = 0;

        while (left < right) {
            if (height.get(left) <= height.get(right)) {
                if (height.get(left) >= leftMax) {
                    leftMax = height.get(left);
                } else {
                    trappedWater += (leftMax - height.get(left));
                }
                left++;
            } else {
                if (height.get(right) >= rightMax) {
                    rightMax = height.get(right);
                } else {
                    trappedWater += (rightMax - height.get(right));
                }
                right--;
            }
        }

        return trappedWater;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        List<Integer> height = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            height.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }

        long result = trap(height);
        System.out.println(result);

        bufferedReader.close();
    }
}