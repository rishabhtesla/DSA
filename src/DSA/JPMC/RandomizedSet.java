package DSA.JPMC; /**
 * PROBLEM: Insert Delete GetRandom O(1)
 * Implement the RandomizedSet class:
 * - insert(val): Inserts an item val into the set if not present. O(1) time.
 * - remove(val): Removes an item val from the set if present. O(1) time.
 * - getRandom(): Returns a random element from the current set. Each element must 
 *   have the same probability of being returned. O(1) time.
 * 
 * LOGIC:
 * 1. For getRandom() to be O(1), we must use an ArrayList (so we can access by index).
 * 2. For insert() and remove() to be O(1), we need a HashMap to track the values and their indexes.
 * 3. The trick to O(1) remove in an ArrayList:
 *    If we delete an element in the middle, the list shifts (which is O(N)).
 *    To fix this, when removing, we take the LAST element in the ArrayList and overwrite 
 *    the element we want to delete. Then we just delete the last element (which is O(1)).
 * 
 * DRY RUN: Insert 1, Insert 2. ArrayList: [1, 2]. Map: {1:0, 2:1}.
 * - Remove 1: 
 *   Find index of 1 in Map -> 0.
 *   Find last element in ArrayList -> 2.
 *   Swap 2 into index 0. ArrayList becomes [2, 2].
 *   Update Map for 2: Map: {1:0, 2:0}.
 *   Remove last element from ArrayList: [2].
 *   Remove 1 from Map: {2:0}.
 * 
 * COMPLEXITY: Time: O(1) average for all operations. Space: O(N).
 */
import java.util.*;

/**
 * Learning supplement: list plus value-to-index map gives expected O(1)
 * insert/remove/random and O(n) space. Examples: insert(1)->true,
 * remove(2)->false, insert(2)->true; after remove(1), random can only return
 * 2. Invariant: each list index agrees with its map entry. Middle deletion
 * swaps the last value into the removed slot and updates its index. Ask why
 * one random call cannot statistically prove uniformity.
 */
public class RandomizedSet {
    private List<Integer> list;
    private Map<Integer, Integer> map;
    private Random random;

    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        // Add to list and record its index in the map
        map.put(val, list.size());
        list.add(val);
        return true;
    }
    
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        // Trick: Move the last element to the place of the element to delete
        int indexToDelete = map.get(val);
        int lastElement = list.get(list.size() - 1);
        
        list.set(indexToDelete, lastElement); // Overwrite deleted element
        map.put(lastElement, indexToDelete);  // Update the map for the moved element
        
        // Remove the last element
        list.remove(list.size() - 1);
        map.remove(val);
        
        return true;
    }
    
    public int getRandom() {
        // Pick a random index from 0 to list.size() - 1
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static void main(String[] args) {
        RandomizedSet randomSet = new RandomizedSet();
        System.out.println("Insert 1: " + randomSet.insert(1)); // true
        System.out.println("Insert 2: " + randomSet.insert(2)); // true
        System.out.println("Remove 1: " + randomSet.remove(1)); // true
        System.out.println("Insert 2 again: " + randomSet.insert(2)); // false
        System.out.println("Get Random: " + randomSet.getRandom()); // 2
    }
}