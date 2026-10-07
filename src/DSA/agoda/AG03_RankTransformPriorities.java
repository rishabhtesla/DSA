package DSA.agoda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [AG-03 / 06] - REASSIGN PRIORITIES / RANK TRANSFORM (Agoda OA Warm-up / LC 1331)
 * ============================================================================
 * 
 * PROBLEM:
 *   Given an array of integer priorities arr, replace each element with its rank.
 *   Rules:
 *   - Rank starts from 1.
 *   - The smaller the value, the smaller the rank.
 *   - If two elements are equal, they must receive the same rank.
 *   - Ranks must be strictly continuous (1, 2, 3, ...) without gaps.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Clone & Coordinate Compression:
 *     1. Copy `arr` into `sorted = arr.clone()`.
 *     2. Sort `sorted` ascending: `Arrays.sort(sorted)`.
 *     3. Iterate through `sorted` and place unique values into a `Map<Integer, Integer> rankMap`.
 *        Assign rank and increment rank ONLY when a novel key is encountered.
 *     4. Map the original array: `arr[i] = rankMap.get(arr[i])`.
 *
 * COMPLEXITY:
 *   - Time:  O(n log n) - Dominated by sorting the cloned array.
 *   - Space: O(n)       - Hash map and cloned auxiliary array.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 */
public class AG03_RankTransformPriorities {

    public static int[] arrayRankTransform(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];

        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1;

        for (int val : sorted) {
            if (!rankMap.containsKey(val)) {
                rankMap.put(val, rank++);
            }
        }

        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = rankMap.get(arr[i]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr1 = {40, 10, 20, 30};
        System.out.println("AG03 Output (Test 1): " + Arrays.toString(arrayRankTransform(arr1))); 
        // Expected: [4, 1, 2, 3]

        int[] arr2 = {100, 100, 100};
        System.out.println("AG03 Output (Test 2): " + Arrays.toString(arrayRankTransform(arr2))); 
        // Expected: [1, 1, 1]

        int[] arr3 = {37, 12, 98, 41, -20, 12};
        System.out.println("AG03 Output (Test 3): " + Arrays.toString(arrayRankTransform(arr3))); 
        // Expected: [3, 2, 5, 4, 1, 2]
    }
}