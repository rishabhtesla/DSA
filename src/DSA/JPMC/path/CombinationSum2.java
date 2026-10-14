package DSA.JPMC.path; /**
 * PROBLEM: Combination Sum II
 * Given a collection of candidate numbers (candidates) and a target number (target), 
 * find all unique combinations where the candidate numbers sum to target.
 * Each number in candidates may only be used ONCE in the combination.
 * 
 * EXAMPLE: Input: candidates = [10,1,2,7,6,1,5], target = 8
 * Output: [[1,1,6], [1,2,5], [1,7], [2,6]]
 * 
 * LOGIC (Backtracking):
 * 1. Sort the array. Sorting is CRITICAL to easily identify and skip duplicate elements.
 * 2. Use a recursive backtrack function that builds combinations.
 * 3. Loop through candidates starting from 'index'.
 * 4. Skip duplicates: if (i > index && arr[i] == arr[i-1]) continue;
 * 5. Add candidate to current list, subtract from target, recurse.
 * 6. "Backtrack" by removing the candidate after the recursive call returns.
 * 
 * COMPLEXITY: Time: O(2^N) worst-case. Space: O(N) for recursion stack.
 */
import java.util.*;

public class CombinationSum2 {
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(candidates); // Step 1: Sort to handle duplicates
        backtrack(candidates, target, 0, new ArrayList<>(), results);
        return results;
    }

    private static void backtrack(int[] arr, int remain, int start, List<Integer> current, List<List<Integer>> results) {
        if (remain < 0) return; // Exceeded target, abandon this path
        if (remain == 0) {
            results.add(new ArrayList<>(current)); // Valid combination found!
            return;
        }

        for (int i = start; i < arr.length; i++) {
            // Skip duplicates at the same depth level
            if (i > start && arr[i] == arr[i - 1]) continue;
            
            // Optimization: Since array is sorted, if arr[i] > remain, the rest will be too
            if (arr[i] > remain) break; 

            current.add(arr[i]); // Choose
            backtrack(arr, remain - arr[i], i + 1, current, results); // Explore (i+1 because we can't reuse)
            current.remove(current.size() - 1); // Un-choose (Backtrack)
        }
    }

    public static void main(String[] args) {
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        System.out.println("Combinations: " + combinationSum2(candidates, 8));
    }
}