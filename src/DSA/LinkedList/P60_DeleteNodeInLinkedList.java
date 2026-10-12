package DSA.LinkedList;

/**
 * ============================================================================
 * [60 / 65] - DELETE NODE IN A LINKED LIST (LeetCode 237)
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Write a function to delete a node in a singly-linked list. You are given **only**
 * access to that node (you are NOT given the head pointer). The node to be deleted
 * is guaranteed **not to be the tail node**.
 *
 * ----------------------------------------------------------------------------
 * INTERVIEW INTUITION & EVOLUTION:
 * ----------------------------------------------------------------------------
 * 1. The Dilemma:
 *    - Normally, to delete a node in a singly-linked list, you need access to its predecessor
 *      to redirect the `.next` pointer around it. Without the head or previous node, how can we remove it?
 *
 * 2. Optimal In-Place Value Overwrite Approach (O(1) Time | O(1) Space):
 *    - **The Core Insight:** Since we cannot unlink the node from behind, we can make this node
 *      "become" the next node by copying its successor's value and bypassing the successor!
 *    - **Step 1:** Overwrite the target node's value with its successor's value: `node.val = node.next.val`.
 *    - **Step 2:** Delete the successor node by rewiring the pointer: `node.next = node.next.next`.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * - Time:  O(1) - Constant time operation involving a direct value copy and pointer reassignment.
 * - Space: O(1) - Zero allocations or extra auxiliary memory.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * - List: `4 -> 5 -> 1 -> 9`, target node to delete is `5`.
 * - Result: `4 -> 1 -> 9`
 *
 * VISUAL DRY RUN (for deleting node `5`):
 *   - Initial: `4 -> 5 -> 1 -> 9`
 *   - Copy successor value (`1`) into target node: `node.val = node.next.val` (List appears as `4 -> 1 -> 1 -> 9`).
 *   - Bypass successor node: `node.next = node.next.next`.
 *   - Final links: `4 -> 1 -> 9` (node `5` is effectively deleted).
 *
 * ----------------------------------------------------------------------------
 * CRITICAL THINKING CHECKPOINTS:
 * 1. Why does this solution require the constraint that the node is *not* the tail?
 *    (If the node were the tail, `node.next` would be `null`, making it impossible to access a successor
 *    value or shift pointers. LeetCode guarantees this constraint for this problem.)
 * 2. Does this approach genuinely delete the target node from memory?
 *    (Logically and structurally yes, the target node takes on the identity of its successor and the successor is bypassed.
 *    In languages like Java with garbage collection, the unlinked successor node is cleaned up automatically.)
 */
public class P60_DeleteNodeInLinkedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void deleteNode(ListNode node) {
        // Copy the value from the next node into the current node
        node.val = node.next.val;
        // Bypass the next node
        node.next = node.next.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n1 = new ListNode(1);
        ListNode n9 = new ListNode(9);
        head.next = n5;
        n5.next = n1;
        n1.next = n9;

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