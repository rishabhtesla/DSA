package DSA.LinkedList;

/**
 * ============================================================================
 * [60 / 65] - DELETE NODE IN A LINKED LIST (LeetCode 237)
 * ============================================================================
 * 
 * PROBLEM:
 *   Write a function to delete a node in a singly-linked list. You are given ONLY
 *   access to that node (NOT given the head pointer). The node is guaranteed not to be the tail.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Without the preceding node, you cannot redirect pointers around the target.
 *   - Solution: Overwrite the target node with its next node's identity!
 *     1. Copy value: `node.val = node.next.val`
 *     2. Delete next node: `node.next = node.next.next`
 *
 * COMPLEXITY:
 *   - Time:  O(1) - Constant operation.
 *   - Space: O(1) - Zero allocations.
 */
public class P60_DeleteNodeInLinkedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n1 = new ListNode(1);
        ListNode n9 = new ListNode(9);
        head.next = n5; n5.next = n1; n1.next = n9;

        deleteNode(n5); // Delete node 5

        System.out.print("P60 Output: ");
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + (curr.next != null ? " -> " : ""));
            curr = curr.next;
        }
        System.out.println(); // Expected: 4 -> 1 -> 9
    }
}