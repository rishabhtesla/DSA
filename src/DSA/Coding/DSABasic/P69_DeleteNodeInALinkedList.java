package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Delete a node when only that node reference is provided, not the head.
 * EXAMPLE: 1->2, delete node 1 -> 2.
 * CONSTRAINTS: Node is not the tail; list has at least two nodes.
 * LOGIC / ALGORITHM:
 * 1. Copy the successor's value into the node, then bypass the successor.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->2, delete node 1 -> 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P69_DeleteNodeInALinkedList {
    public static void deleteNode(ListNode n) {
        n.val = n.next.val;
        n.next = n.next.next;
    }

    public static void main(String[] args) {
        ListNode h = new ListNode(1, new ListNode(2));
        deleteNode(h);
        System.out.println(h.val);
    }
}
