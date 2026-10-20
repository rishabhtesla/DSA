package DSA.JPMC.path;

import java.util.*;

/**
 * ============================================================================
 * PROBLEM: Combination Sum II (LeetCode 40 / HackerRank Variant)
 * ============================================================================
 *
 * Given a collection of candidate numbers (candidates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sum to target.
 *
 * Constraints & Rules:
 * 1. Each number in candidates may only be used ONCE per combination.
 * 2. The solution set must NOT contain duplicate combinations.
 * 3. All numbers (including target) are positive integers.
 *
 * Example:
 *   Input:  candidates = [10, 1, 2, 7, 6, 1, 5], target = 8
 *   Output: [[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]]
 *
 * ============================================================================
 * CORE MECHANICS & WHY THIS LOGIC WORKS:
 * ============================================================================
 * 1. Sorting Step (Crucial):
 *    Sorting brings identical elements next to each other:
 *      Raw:    [10, 1, 2, 7, 6, 1, 5]
 *      Sorted: [1, 1, 2, 5, 6, 7, 10]
 *
 * 2. The Duplicate-Skipping Condition: `if (i > start && arr[i] == arr[i - 1]) continue;`
 *    - `start` is the index where this recursion frame began picking elements.
 *    - When `i == start`: We are picking the first copy of a duplicate value at
 *      this branch level. This is VALID because duplicate values can appear in
 *      different positions of the same combination (e.g., [1, 1, 6]).
 *    - When `i > start` and `arr[i] == arr[i - 1]`: We are picking a sibling
 *      alternative at the SAME tree level. Since `arr[i - 1]` already explored
 *      all combinations starting with this value at this position, picking `arr[i]`
 *      would produce exact clone combinations. We skip it!
 *
 * 3. Early Pruning: `if (arr[i] > remain) break;`
 *    Because `arr` is sorted, if the current element exceeds `remain`, every
 *    subsequent element `arr[i+1 ... n-1]` is guaranteed to exceed `remain` as well.
 *    We can immediately break the loop instead of making useless recursive calls.
 *
 * ============================================================================
 * STEP-BY-STEP DRY RUN:
 * Candidates (Sorted) = [1, 1, 2, 5, 6, 7, 10], Target = 8
 * ============================================================================
 *
 * Call 1: start=0, remain=8, current=[]
 * ----------------------------------------------------------------------------
 * - i = 0 (val = 1):
 *     current = [1], recurse(start=1, remain=7)
 *     - i = 1 (val = 1, i == start -> allowed):
 *         current = [1, 1], recurse(start=2, remain=6)
 *         - i = 2 (val = 2): current = [1, 1, 2], recurse(start=3, remain=4)
 *             - i = 3 (val = 5 > 4) -> break!
 *         - i = 3 (val = 5): current = [1, 1, 5], recurse(start=4, remain=1)
 *             - i = 4 (val = 6 > 1) -> break!
 *         - i = 4 (val = 6): current = [1, 1, 6], recurse(start=5, remain=0)
 *             -> MATCH FOUND! Save [1, 1, 6] to results.
 *         - i = 5 (val = 7 > 6) -> break!
 *     - i = 2 (val = 2):
 *         current = [1, 2], recurse(start=3, remain=5)
 *         - i = 3 (val = 5): current = [1, 2, 5], recurse(start=4, remain=0)
 *             -> MATCH FOUND! Save [1, 2, 5] to results.
 *         - i = 4 (val = 6 > 5) -> break!
 *     - i = 3 (val = 5): current = [1, 5], recurse(start=4, remain=2) -> i=4 (6>2) break!
 *     - i = 4 (val = 6): current = [1, 6], recurse(start=5, remain=1) -> i=5 (7>1) break!
 *     - i = 5 (val = 7): current = [1, 7], recurse(start=6, remain=0)
 *         -> MATCH FOUND! Save [1, 7] to results.
 *     - i = 6 (val = 10 > 7) -> break!
 *
 * - i = 1 (val = 1, i > start && arr[1] == arr[0] -> DUPLICATE AT LEVEL 0):
 *     -> SKIPPED! (Prevents duplicate [1, 2, 5], [1, 7], etc.)
 *
 * - i = 2 (val = 2):
 *     current = [2], recurse(start=3, remain=6)
 *     - i = 3 (val = 5): current = [2, 5], recurse(start=4, remain=1) -> break!
 *     - i = 4 (val = 6): current = [2, 6], recurse(start=5, remain=0)
 *         -> MATCH FOUND! Save [2, 6] to results.
 *     - i = 5 (val = 7 > 6) -> break!
 *
 * - i = 3 (val = 5):
 *     current = [5], recurse(start=4, remain=3)
 *     - i = 4 (val = 6 > 3) -> break!
 *
 * - i = 4 (val = 6):
 *     current = [6], recurse(start=5, remain=2)
 *     - i = 5 (val = 7 > 2) -> break!
 *
 * - i = 5 (val = 7):
 *     current = [7], recurse(start=6, remain=1)
 *     - i = 6 (val = 10 > 1) -> break!
 *
 * - i = 6 (val = 10 > 8):
 *     -> break!
 *
 * Final Result: [[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]]
 *
 * ============================================================================
 * COMPLEXITY:
 * ============================================================================
 * - Time Complexity:  O(2^N) in worst case (every element either included or excluded).
 *                     Sorting takes O(N log N). Early pruning makes practical runtime
 *                     far lower than theoretical upper bound.
 * - Space Complexity: O(N) auxiliary space for recursion stack and current list.
 * ============================================================================
 */
public class CombinationSum2 {

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return results;
        }

        // Step 1: Sort candidates to bring duplicates together and allow break optimization
        Arrays.sort(candidates);

        // Step 2: Trigger recursive backtracking
        backtrack(candidates, target, 0, new ArrayList<>(), results);

        return results;
    }

    private static void backtrack(int[] arr, int remain, int start, List<Integer> current, List<List<Integer>> results) {
        // Base Case: Remaining balance resolved to 0 -> combination discovered
        if (remain == 0) {
            results.add(new ArrayList<>(current)); // Deep copy current path
            return;
        }

        for (int i = start; i < arr.length; i++) {
            // Rule 1: Skip duplicate values at the same recursive decision level
            if (i > start && arr[i] == arr[i - 1]) {
                continue;
            }

            // Rule 2: Prune exploration - since arr is sorted, future elements are all > remain
            if (arr[i] > remain) {
                break;
            }

            // Standard Backtracking template: Choose -> Explore -> Un-choose
            current.add(arr[i]);
            backtrack(arr, remain - arr[i], i + 1, current, results); // i + 1 ensures elements are used at most once
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;

        List<List<Integer>> combinations = combinationSum2(candidates, target);

        System.out.println("Input Candidates: " + Arrays.toString(candidates));
        System.out.println("Target: " + target);
        System.out.println("Unique Combinations: " + combinations);
    }
}