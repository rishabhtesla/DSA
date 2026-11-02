package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P28: FIND TARGET INDICES AFTER SORTING ARRAY
 *
 * Question
 * Return indices containing target after sorting the array.
 *
 * Example
 * [1,2,5,2,3], target 2 -> [1,2].
 *
 * Constraints
 * 1 <= n <= 100; values are 1..100.
 *
 * Approach used by edSlash
 * 1. Sort, then collect every index equal to target.
 * 2. Use the sorted or rotated structure to discard the part of the search space that cannot contain the answer.
 * 3. Move the left and right boundaries until the answer is found or the search interval becomes empty.
 *
 * Dry run
 * Example from main: [1,2,5,2,3], target 2 -> [1,2].
 * 1. Start with the complete valid search interval.
 * 2. Inspect the middle position and decide which half can still contain the answer.
 * 3. Discard the other half and repeat until the answer is found or the interval is empty.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P28_FindTargetIndicesAfterSortingArray {
    public static List<Integer> targetIndices(int[] nums, int target) {
        Arrays.sort(nums);
        List<Integer> targetIndices = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) if (nums[i] == target) targetIndices.add(i);
        return targetIndices;
    }

    public static void main(String[] args) {
        System.out.println(targetIndices(new int[]{1, 2, 5, 2, 3}, 2));
    }
}
