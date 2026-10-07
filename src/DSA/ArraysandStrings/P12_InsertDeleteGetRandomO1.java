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
 * PROBLEM:
 *   Implement RandomizedSet with insert, remove, and getRandom operations,
 *   all running in average O(1) time complexity.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Hash Map: O(1) insert/remove, but lacks index-based uniform random access.
 *   - ArrayList: O(1) getRandom by index, but remove(val) takes O(n) due to shifting.
 *   - Dual Data Structure Synergy:
 *     1. Use `ArrayList` to store elements sequentially for O(1) random lookup.
 *     2. Use `HashMap<Integer, Integer>` mapping val -> index in the list.
 *     3. Swap-and-Pop Deletion:
 *        To delete an element in O(1) without shifting, overwrite its slot with
 *        the array's last element, update the map index of the last element,
 *        and pop the last element from the list.
 *
 * COMPLEXITY:
 *   - Time:  O(1) average for insert, remove, and getRandom.
 *   - Space: O(n) to store values and their index mappings.
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
            if (valToIndex.containsKey(val)) {
                return false;
            }
            valToIndex.put(val, list.size());
            list.add(val);
            return true;
        }

        public boolean remove(int val) {
            if (!valToIndex.containsKey(val)) {
                return false;
            }

            int index = valToIndex.get(val);
            int lastElement = list.get(list.size() - 1);

            // Swap: move last element into the deleted slot
            list.set(index, lastElement);
            valToIndex.put(lastElement, index);

            // Pop: drop the trailing element
            list.remove(list.size() - 1);
            valToIndex.remove(val);

            return true;
        }

        public int getRandom() {
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