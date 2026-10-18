package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * HACKERRANK QUESTION 10: High-Throughput LRU Cache
 * ============================================================================
 *
 * Problem Statement:
 * Design a data structure that follows the constraints of a Least Recently
 * Used (LRU) cache for caching market quotation feeds.
 *
 * Implement the LRUCache class:
 * - LRUCache(int capacity): Initialize the LRU cache with positive size capacity.
 * - int get(int key): Return the value of the key if the key exists, otherwise
 *   return -1.
 * - void put(int key, int value): Update the value of the key if the key exists.
 *   Otherwise, add the key-value pair to the cache. If the number of keys exceeds
 *   the capacity from this operation, evict the least recently used key.
 *
 * The functions get and put must each run in O(1) average time complexity.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer capacity.
 * - Line 2: An integer q, the number of queries.
 * - Next q lines: A query formatted as:
 *     - "GET key"
 *     - "PUT key value"
 *
 * Constraints:
 * - 1 <= capacity <= 3000
 * - 0 <= key <= 10^4
 * - 0 <= value <= 10^5
 * - At most 2 * 10^5 calls will be made to get and put.
 *
 * Example:
 * Input:
 *   2
 *   6
 *   PUT 1 1
 *   PUT 2 2
 *   GET 1
 *   PUT 3 3
 *   GET 2
 *   GET 3
 * Output:
 *   1
 *   -1
 *   3
 *
 * ============================================================================
 * LOGIC & APPROACH:
 * ============================================================================
 * 1. Data Structure Choice:
 *    - A standard HashMap provides O(1) key-to-node lookup.
 *    - A custom Doubly Linked List with dummy `head` and `tail` nodes provides
 *      O(1) insertion, deletion, and relocation without shifting elements.
 *
 * 2. Recency Invariant:
 *    - Most recently used node sits immediately after dummy `head`.
 *    - Least recently used node sits immediately before dummy `tail`.
 *
 * 3. Operations:
 *    - get(key):
 *        If key not found -> return -1.
 *        If found -> detach node, insert at head (mark active), return value.
 *    - put(key, value):
 *        If key exists -> update value, move to head.
 *        If new key:
 *          If size == capacity -> remove tail.prev from map and linked list.
 *          Create newNode, put in map, attach at head.
 *
 * Complexity:
 * - Time: O(1) for both get and put operations.
 * - Space: O(capacity) to store nodes and hash entries.
 *
 * ============================================================================
 * DRY RUN (Capacity = 2):
 * ============================================================================
 * PUT 1 1 -> Cache: [1:1]
 * PUT 2 2 -> Cache: [2:2, 1:1] (size = 2)
 * GET 1   -> Found! Move 1 to head -> Cache: [1:1, 2:2] -> Returns 1
 * PUT 3 3 -> Capacity full! Evict tail.prev (2:2).
 *            Insert 3:3 at head -> Cache: [3:3, 1:1]
 * GET 2   -> Not found in map -> Returns -1
 * GET 3   -> Found! Move 3 to head -> Cache: [3:3, 1:1] -> Returns 3
 * ============================================================================
 */
public class SolutionLRUCache {

    static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head;
    private final Node tail;

    public SolutionLRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    private void addFirst(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addFirst(node);
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
            return;
        }

        if (map.size() >= capacity) {
            Node lru = tail.prev;
            removeNode(lru);
            map.remove(lru.key);
        }

        Node newNode = new Node(key, value);
        map.put(key, newNode);
        addFirst(newNode);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String capLine = bufferedReader.readLine();
        if (capLine == null || capLine.trim().isEmpty()) {
            return;
        }

        int capacity = Integer.parseInt(capLine.trim());
        SolutionLRUCache cache = new SolutionLRUCache(capacity);

        int q = Integer.parseInt(bufferedReader.readLine().trim());
        for (int i = 0; i < q; i++) {
            String[] tokens = bufferedReader.readLine().trim().split("\\s+");
            if (tokens[0].equalsIgnoreCase("GET")) {
                int key = Integer.parseInt(tokens[1]);
                System.out.println(cache.get(key));
            } else if (tokens[0].equalsIgnoreCase("PUT")) {
                int key = Integer.parseInt(tokens[1]);
                int val = Integer.parseInt(tokens[2]);
                cache.put(key, val);
            }
        }

        bufferedReader.close();
    }
}