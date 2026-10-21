package DSA.JPMC; /**
 * PROBLEM: LRU Cache (Least Recently Used)
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * Implement the LRUCache class:
 * - LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * - int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * - void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair.
 *   If the number of keys exceeds the capacity, evict the least recently used key.
 * Both operations must be O(1) time complexity.
 * 
 * LOGIC:
 * We need O(1) access (HashMap) and O(1) deletion/insertion at ends (Doubly Linked List).
 * - HashMap maps Key -> Node in the Linked List.
 * - The Doubly Linked List keeps track of usage. 
 *   - Most Recently Used (MRU) goes right after a dummy HEAD.
 *   - Least Recently Used (LRU) goes right before a dummy TAIL.
 * Every time a node is read or written, we remove it from its current position and move it to HEAD.
 * If capacity is exceeded, we remove the node right before TAIL and delete it from the Map.
 * 
 * COMPLEXITY: Time: O(1) for get and put, Space: O(capacity)
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Learning supplement: a map alone cannot track recency and a list alone
 * cannot locate keys in O(1); combining a map with a doubly linked list gives
 * O(1) get/put and O(capacity) space. Examples: capacity 2, put(1,10),
 * put(2,20), get(1)->10, put(3,30), get(2)->-1; capacity 1, put(1,1),
 * put(2,2) evicts 1. Invariant: list order is MRU to LRU and every map entry
 * points to its list node. Ask why both get and update move a node to the
 * front.
 */
public class LRUCache {
    
    // Internal class for Doubly Linked List nodes
    class Node {
        int key;
        int value;
        Node prev;
        Node next;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head;
    private final Node tail;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Dummy head and tail to avoid null checks during insertion/deletion
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }
    
    // Move a node to the front (right after head) -> marks it as MRU
    private void addNodeToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
    
    // Remove a node from the linked list
    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) return -1;
        
        Node node = cache.get(key);
        // It was accessed, so it becomes the Most Recently Used.
        removeNode(node);
        addNodeToFront(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node existingNode = cache.get(key);
            existingNode.value = value;
            removeNode(existingNode);
            addNodeToFront(existingNode);
        } else {
            if (cache.size() >= capacity) {
                // Cache is full, remove the LRU node (node just before tail)
                Node lruNode = tail.prev;
                removeNode(lruNode);
                cache.remove(lruNode.key);
            }
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNodeToFront(newNode);
        }
    }
    
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2); // Capacity is 2
        cache.put(1, 10);
        cache.put(2, 20);
        System.out.println("Get 1: " + cache.get(1)); // Returns 10 (1 is now MRU)
        cache.put(3, 30); // Evicts key 2 (LRU)
        System.out.println("Get 2: " + cache.get(2)); // Returns -1 (not found)
    }
}