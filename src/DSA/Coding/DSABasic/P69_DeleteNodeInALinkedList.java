package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P69: DELETE NODE IN A LINKED LIST
 *
 * Question
 * Delete a node when only that node reference is provided, not the head.
 *
 * Example
 * 1->2, delete node 1 -> 2.
 *
 * Constraints
 * Node is not the tail; list has at least two nodes.
 *
 * Approach used by edSlash
 * 1. Copy the successor's value into the node, then bypass the successor.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 *   In-place delete visual for 1->2:
 *     copy the next node's value 2 into the node to delete
 *     bypass the next node
 *     remaining list is 2
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P69_DeleteNodeInALinkedList {
    public static void deleteNode(ListNode n) {
        n.val = n.next.val;
        n.next = n.next.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2));
        deleteNode(head);
        System.out.println(head.val);
    }
}
