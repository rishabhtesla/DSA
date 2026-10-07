package DSA.LinkedList;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * [63 / 65] - LRU CACHE (LeetCode 146)
 * ============================================================================
 * 
 * PROBLEM:
 *   Design a data structure that follows the constraints of a Least Recently Used
 *   (LRU) cache with get and put in average O(1) time complexity.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Hash Map + Doubly Linked List:
 *     - Hash Map provides O(1) node lookup by key.
 *     - Doubly Linked List provides O(1) insertion, deletion, and node relocation.
 *   - Setup:
 *     Dummy `head` (most recently used) and dummy `tail` (least recently used).
 *     - `get(key)`: If present, detach node and re-insert right after `head`.
 *     - `put(key, val)`: If key exists, update value and move to head. If new,
 *       insert after head. If capacity is exceeded, remove node right before `tail`.
 *
 * COMPLEXITY:
 *   - Time:  O(1) for both get and put.
 *   - Space: O(capacity) to store mappings and nodes.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *   2. What invariant must remain true after every loop iteration?
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *   4. Which edge cases change the control flow (empty input, one item, duplicates, or boundaries)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
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