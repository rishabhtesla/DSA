package DSA.Coding.DSABasic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PROBLEM STATEMENT:
 * Given an unsorted array of integers 'nums', return the length of the longest consecutive elements sequence [00:00:22].
 * You must write an algorithm that runs in O(n) time complexity [00:02:05].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: nums = [100, 4, 200, 1, 3, 2] [00:00:43]
 * - Process: The longest consecutive elements sequence is [1, 2, 3, 4]. Its length is 4 [00:00:52].
 * - Result: 4
 * 
 * Example 2: nums = [10, 120, 1, 9, 2, 3, 4, 5] (Simulated inside the video explanation [00:02:30, 00:17:03])
 * - Process:
 *   - Load all numbers into a `HashMap<Integer, Boolean>` initially paired with `false` [00:04:12, 00:17:11].
 *   - Check for cluster starting boundaries (anchors): A number starts a sequence if `num - 1` is completely 
 *     absent from our tracking catalog [00:05:39, 00:17:40].
 *   - 120, 1, and 9 qualify as sequence starting points [00:07:46, 00:18:40].
 *   - Anchor 1 expands continuously: 1 -> 2 -> 3 -> 4 -> 5, accumulating a streak length of 5 [00:08:41, 00:20:44].
 *   - Anchor 9 expands to 10, capturing a streak length of 2 [00:09:05, 00:21:18].
 *   - Max streak found across sequences is 5 [00:09:14, 00:21:46].
 * - Result: 5
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - HashMap Sequence Boundary Anchoring):
 * • Index Initialization: Sequential iterations scan individual keys populated directly from the associative map's unique keySet [00:10:52, 00:14:04].
 * • Condition Boundaries: If `map.containsKey(num - 1)` is false, flag the element as a cluster start anchor (`map.put(num, true)`) [00:06:29, 00:11:42].
 * • Operational Steps:
 *   1. Build a `HashMap<Integer, Boolean>` storing array keys matched initially to a default false state flag [00:09:35, 00:10:13].
 *   2. Identify sequences boundaries: if `num - 1` is missing, mark the key as a sequence starter [00:06:35, 00:11:50].
 *   3. If marked true, run an inner loop `while(map.containsKey(num + streakCount))` to extend the count [00:08:06, 00:14:44].
 *   4. Benchmark updates: `maxStreak = max(maxStreak, streakCount)` at each completion phase [00:08:45, 00:15:26].
 * • Time Complexity: O(n) - Although an inner loop exists, each element is visited at most twice across executions [00:02:05].
 * • Space Complexity: O(n) - Storage tracking mapping arrays over structural hashing maps [00:06:11].
 * • LOGIC BEHIND THIS APPROACH:
 *   Sorting a collection natively breaches the O(n) linear performance boundary by scaling at O(n log n) [00:02:10]. 
 *   An associative lookup allows us to check for neighbors in constant time O(1) [00:03:16]. By processing sequences 
 *   only from their absolute beginning (the anchor where `num - 1` does not exist), we prevent redundant sub-streak calculations, 
 *   keeping the total algorithm overhead linear [00:04:42].
 * 
 * ---
 * VISUAL DRY RUN (nums = [10, 120, 1, 9, 2, 3, 4, 5]):
 * Hashing Catalog State [00:17:11]: {10=false, 120=true, 1=true, 9=true, 2=false, 3=false, 4=false, 5=false}
 * Processing Loop (maxStreak = 0) [00:18:58]:
 * - key = 10 -> map.get(10) is false -> Skip internal tracking loops.
 * - key = 120 -> map.get(120) is true -> Loop: containsKey(121) is false. streak=1. maxStreak = max(0, 1) = 1 [00:19:42].
 * - key = 1 -> map.get(1) is true -> Loop updates: containsKey(2)=true, containsKey(3)=true, containsKey(4)=true, containsKey(5)=true, 
 *   containsKey(6)=false. streakCount reaches 5. maxStreak = max(1, 5) = 5 [00:20:44].
 * - key = 9 -> map.get(9) is true -> Loop updates: containsKey(10)=true, containsKey(11)=false. streakCount reaches 2. 
 *   maxStreak = max(5, 2) = 5 [00:21:28].
 * - Trailing keys (2, 3, 4, 5) evaluate to false, skipping execution loops [00:21:37].
 * Output returned = 5.
 */
public class LongestConsecutiveSequence {

    // APPROACH 1: HashMap Sequence Boundary Anchoring (Anchor Strategy)
    public static int longestConsecutiveOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0; // Guard clause for empty boundaries [00:13:45, 00:19:19]

        Map<Integer, Boolean> trackingMap = new HashMap<>();
        
        // Initial setup pass to catalog unique elements into the hash layer [00:09:35]
        for (int num : nums) {
            trackingMap.put(num, false);
        }

        // Boundary scanning pass to label valid cluster anchors [00:10:34]
        for (int key : trackingMap.keySet()) {
            if (!trackingMap.containsKey(key - 1)) {
                trackingMap.put(key, true); // Mark element as a verified sequence starter [00:06:35, 00:11:50]
            }
        }

        int maxStreak = 0;

        // Sequence expansion pass evaluating marked starting baselines [00:13:58]
        for (int key : trackingMap.keySet()) {
            if (trackingMap.get(key)) { // Process only active starting points [00:14:15]
                int currentStreak = 1;
                
                // Greedily count consecutive elements forward in constant time [00:14:44]
                while (trackingMap.containsKey(key + currentStreak)) {
                    currentStreak++;
                }
                
                maxStreak = Math.max(maxStreak, currentStreak); // Sync maximum metric [00:15:26]
            }
        }
        return maxStreak;
    }

    // APPROACH 2: HashSet Sequence Core Optimization
    // Streamlines Approach 1 by using a `HashSet` instead of a map. The membership test `!set.contains(num - 1)` 
    // provides clean anchor tracking with a smaller extra memory footprint.
    public static int longestConsecutiveSet(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        Set<Integer> uniqueSet = new HashSet<>();
        for (int num : nums) {
            uniqueSet.add(num);
        }
        
        int maxStreak = 0;
        
        for (int num : uniqueSet) {
            // Check if 'num' behaves as the true initial block anchor
            if (!uniqueSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                while (uniqueSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }
                
                maxStreak = Math.max(maxStreak, currentStreak);
            }
        }
        return maxStreak;
    }

    // APPROACH 3: Functional Streams API Pipeline Strategy
    // Runtime Trade-off Note: Collecting elements into sets and evaluating streams adds significant object boxing 
    // overhead. While the pipeline abstraction is clean, it relies on complete linear memory passes, making it less 
    // performant than primitive configurations.
    public static int longestConsecutiveStream(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> uniqueSet = Arrays.stream(nums)
                .boxed()
                .collect(Collectors.toSet());

        return uniqueSet.stream()
                .filter(num -> !uniqueSet.contains(num - 1)) // Filter out non-anchor items
                .mapToInt(anchor -> {
                    int streak = 1;
                    while (uniqueSet.contains(anchor + streak)) {
                        streak++;
                    }
                    return streak;
                })
                .max()
                .orElse(0);
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Standard Elements) ---
        int[] test1 = {100, 4, 200, 1, 3, 2};
        int res1_1 = longestConsecutiveOptimal(test1);
        int res1_2 = longestConsecutiveSet(test1);
        int res1_3 = longestConsecutiveStream(test1);

        System.out.println("Test Case 1: [100, 4, 200, 1, 3, 2]");
        System.out.println("Approach 1 (HashMap Anchor) Result: " + res1_1);
        System.out.println("Approach 2 (HashSet Lookup) Result: " + res1_2);
        System.out.println("Approach 3 (Stream Pipeline) Result: " + res1_3);
        System.out.println("Verification: " + (res1_1 == 4 && res1_2 == 4 && res1_3 == 4 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (From Video Board Explanation) ---
        int[] test2 = {10, 120, 1, 9, 2, 3, 4, 5};
        int res2_1 = longestConsecutiveOptimal(test2);
        int res2_2 = longestConsecutiveSet(test2);
        int res2_3 = longestConsecutiveStream(test2);

        System.out.println("Test Case 2: [10, 120, 1, 9, 2, 3, 4, 5]");
        System.out.println("Approach 1 (HashMap Anchor) Result: " + res2_1);
        System.out.println("Approach 2 (HashSet Lookup) Result: " + res2_2);
        System.out.println("Approach 3 (Stream Pipeline) Result: " + res2_3);
        System.out.println("Verification: " + (res2_1 == 5 && res2_2 == 5 && res2_3 == 5 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}