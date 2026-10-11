package DSA.ArraysandStrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ============================================================================
 * [12 / 24] - INSERT DELETE GETRANDOM O(1) (LeetCode 380)
 * ============================================================================
 *
 * 1. PROBLEM STATEMENT:
 * ----------------------------------------------------------------------------
 * Design a data structure `RandomizedSet` supporting three operations:
 *   - insert(val):    Inserts `val` if not present. Returns true if added, false if already exists.
 *   - remove(val):    Removes `val` if present. Returns true if removed, false if not found.
 *   - getRandom():    Returns a random element from the current set.
 *                     Each element MUST have the exact same probability of being chosen: 1 / N.
 *
 * CONSTRAINT:
 *   All three operations must execute in AVERAGE O(1) time complexity.
 *
 *
 * 2. THE ARCHITECTURAL DILEMMA ("WHY A SINGLE STRUCTURE FAILS"):
 * ----------------------------------------------------------------------------
 * Attempt A: HashSet / HashMap alone
 *   - insert(val): O(1) average.
 *   - remove(val): O(1) average.
 *   - getRandom(): FAILS O(1). Hash buckets have gaps/collisions and no contiguous
 *     0-based index. To pick uniformly, you must traverse or convert to an array -> O(N).
 *
 * Attempt B: Dynamic Array (ArrayList) alone
 *   - getRandom(): O(1) via random index `rand.nextInt(list.size())`.
 *   - insert(val): O(1) amortized append.
 *   - remove(val): FAILS O(1). Deleting an element from the middle of an array
 *     requires shifting all subsequent elements to the left -> O(N).
 *
 *
 * 3. THE "AHA!" MOMENT: DUAL STRUCTURE + SWAP-AND-POP
 * ----------------------------------------------------------------------------
 * Combine both structures:
 *   1. `ArrayList<Integer> list`:
 *      Stores all distinct values contiguously so `getRandom()` is a trivial O(1) index lookup.
 *   2. `HashMap<Integer, Integer> valToIndex`:
 *      Maps `value -> its index in list`, giving O(1) lookup to check existence and position.
 *
 * THE "SWAP-AND-POP" TRICK FOR O(1) DELETION:
 *   Removing the last element of an ArrayList takes O(1) because zero elements need to shift!
 *   Therefore, to delete any arbitrary element at `index`:
 *     Step a: Grab the very last element in the list: `lastVal = list.get(list.size() - 1)`.
 *     Step b: Overwrite slot `index` with `lastVal`.
 *     Step c: Update `valToIndex` so `lastVal` points to its new home (`index`).
 *     Step d: Pop the duplicate trailing slot off the list: `list.remove(list.size() - 1)`.
 *     Step e: Delete the original target `val` from the map.
 *
 *
 * 4. DETAILED TRACE & EDGE CASES:
 * ----------------------------------------------------------------------------
 * Case 1: Standard Deletion (Element in the middle)
 *   State: list = [10, 20, 30, 40], map = {10:0, 20:1, 30:2, 40:3}
 *   Call:  remove(20)  (target index = 1, lastElement = 40)
 *     1. Move 40 to index 1: list becomes [10, 40, 30, 40]
 *     2. Update map: map.put(40, 1)
 *     3. Pop tail: list becomes [10, 40, 30]
 *     4. Remove 20 from map: map.remove(20)
 *   Result: list = [10, 40, 30], map = {10:0, 40:1, 30:2} in O(1) time.
 *
 * Case 2: Removing the Last Element (No actual swap needed)
 *   State: list = [10, 20, 30], map = {10:0, 20:1, 30:2}
 *   Call:  remove(30)  (target index = 2, lastElement = 30)
 *     1. list.set(2, 30) -> list stays [10, 20, 30]
 *     2. map.put(30, 2)
 *     3. list.remove(2)  -> list becomes [10, 20]
 *     4. map.remove(30)  -> 30 removed from map
 *   *Note: Because Step 1 and 2 write before Step 3 and 4 remove,
 *          removing the tail works naturally without extra if/else checks!*
 *
 * Case 3: Removing the Only Element (Size = 1)
 *   State: list = [5], map = {5:0}
 *   Call:  remove(5)
 *     - list.set(0, 5), map.put(5, 0)
 *     - list.remove(0) -> list becomes empty []
 *     - map.remove(5)  -> map becomes empty {}
 *
 *
 * 5. COMPLEXITY:
 * ----------------------------------------------------------------------------
 * - Time:
 *     - insert(val):  O(1) average (HashMap put + ArrayList append).
 *     - remove(val):  O(1) average (HashMap get/remove + ArrayList set/remove last).
 *     - getRandom():  O(1) (random integer generation + ArrayList index access).
 * - Space:
 *     - O(N) where N is the number of distinct elements stored across the list and map.
 */
public class P12_InsertDeleteGetRandomO1 {

    static class RandomizedSet {
        private final Map<Integer, Integer> valToIndex;
        private final List<Integer> list;
        private final Random rand;

        public RandomizedSet() {
            this.valToIndex = new HashMap<>();
            this.list = new ArrayList<>();
            this.rand = new Random();
        }

        public boolean insert(int val) {
            // Already present: cannot insert duplicates
            if (valToIndex.containsKey(val)) {
                return false;
            }

            // New element goes to the end of the list (index = current size)
            valToIndex.put(val, list.size());
            list.add(val);
            return true;
        }

        public boolean remove(int val) {
            // Not present: nothing to remove
            if (!valToIndex.containsKey(val)) {
                return false;
            }

            int index = valToIndex.get(val);
            int lastElement = list.get(list.size() - 1);

            // Step 1: Overwrite target slot with the last element
            list.set(index, lastElement);
            valToIndex.put(lastElement, index);

            // Step 2: Delete the trailing element and clear target from the map
            list.remove(list.size() - 1);
            valToIndex.remove(val);

            return true;
        }

        public int getRandom() {
            // Pick a uniform random index from [0, list.size() - 1]
            return list.get(rand.nextInt(list.size()));
        }
    }

    public static void main(String[] args) {
        RandomizedSet set = new RandomizedSet();
        System.out.println("Insert 1: " + set.insert(1));  // Expected: true
        System.out.println("Remove 2: " + set.remove(2));  // Expected: false
        System.out.println("Insert 2: " + set.insert(2));  // Expected: true
        System.out.println("Random:   " + set.getRandom()); // Expected: 1 or 2
        System.out.println("Remove 1: " + set.remove(1));  // Expected: true
        System.out.println("Random:   " + set.getRandom()); // Expected: 2
    }
}