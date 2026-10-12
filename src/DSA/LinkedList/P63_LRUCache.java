package DSA.LinkedList;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [63 / 65] - LRU CACHE (LeetCode 146)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache
 * supporting `get` and `put` operations in **average O(1) time complexity**.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. Naive / Array or Queue Approach:
 *    - Use an array, list, or queue to maintain usage order.
 *    - Inefficient: searches or deletions take O(n) time, violating the strict O(1) requirement.
 *
 * 2. Optimal Hash Map + Doubly Linked List Approach (O(1) Time | O(capacity) Space):
 *    - **The Core Insight:** We need O(1) random access (provided by a Hash Map) combined with
 *      O(1) insertion, deletion, and relocation (provided by a Doubly Linked List).
 *    - **Map Structure:** Maps each `key` directly to its corresponding `Node` reference.
 *    - **List Structure:** Maintain dummy `head` (most recently used marker) and dummy `tail` (least recently used marker).
 *    - `get(key)`: If present, extract the node, detach it, and re-insert it right after `head`.
 *    - `put(key, val)`: If the key exists, update its value and move it to the head. If new, insert it right after `head`.
 *      If capacity is exceeded, evict the node right before `tail` (`tail.prev`) from both the list and the map.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(1) average time complexity for both `get` and `put` operations.
 * - Space: O(capacity) to store the mapping entries and doubly linked list nodes up to the specified limit.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - Capacity = 2
 * - `put(1, 1)`, `put(2, 2)`, `get(1)` -> returns 1
 * - `put(3, 3)` -> evicts key `2`
 * - `get(2)` -> returns `-1` (not found)
 * - `put(4, 4)` -> evicts key `1`
 * - `get(1)` -> `-1`, `get(3)` -> `3`, `get(4)` -> `4`
 *
 * VISUAL DRY RUN:
 *   - Order after puts: `[1, 2]`. `get(1)` relocates 1 to the front -> `[2, 1]`.
 *   - `put(3, 3)` exceeds capacity: evicts LRU node (`2`) -> list becomes `[1, 3]`.
 *   - `put(4, 4)` exceeds capacity: evicts LRU node (`1`) -> list becomes `[3, 4]`.
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why use a *doubly* linked list instead of a singly linked list?
 *    (A doubly linked list allows O(1) removal of any node since each node has direct access to its predecessor (`prev`),
 *    whereas a singly linked list would require an O(n) traversal to find the previous node before unlinking.)
 * 2. What purpose do the dummy `head` and `tail` nodes serve?
 *    (They act as boundary anchors that eliminate edge cases when inserting or removing nodes at the very ends of the list,
 *    ensuring that `prev` and `next` pointer assignments never encounter unexpected `null` references.)
 */
public class P63_LRUCache {

    static class LRUCache {
        static class Node {
            int key, val;
            Node prev, next;
            Node(int k, int v) { key = k; val = v; }
        }

        private final int capacity;
        private final Map<Integer, Node> map;
        private final Node head;
        private final Node tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!map.containsKey(key)) return -1;
            Node node = map.get(key);
            remove(node);
            insertAtHead(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.val = value;
                remove(node);
                insertAtHead(node);
            } else {
                if (map.size() == capacity) {
                    Node lru = tail.prev;
                    remove(lru);
                    map.remove(lru.key);
                }
                Node newNode = new Node(key, value);
                insertAtHead(newNode);
                map.put(key, newNode);
            }
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void insertAtHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
    }

    public static void main(String[] args) {
        LRUCache lru = new LRUCache(2);
        lru.put(1, 1);
        lru.put(2, 2);
        System.out.println("Get 1: " + lru.get(1)); // Expected: 1
        lru.put(3, 3);                             // Evicts key 2
        System.out.println("Get 2: " + lru.get(2)); // Expected: -1
        lru.put(4, 4);                             // Evicts key 1
        System.out.println("Get 1: " + lru.get(1)); // Expected: -1
        System.out.println("Get 3: " + lru.get(3)); // Expected: 3
        System.out.println("Get 4: " + lru.get(4)); // Expected: 4
    }
}