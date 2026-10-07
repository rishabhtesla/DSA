package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared Definition for Singly-Linked List Node.
 */


/**
 * PROBLEM STATEMENT:
 * There is a singly-linked list head and we want to delete a node in it [00:00:53].
 * You are given the node to be deleted directly. You will NOT be given access to the head [00:01:10, 00:01:20].
 * All values of the linked list are unique, and the given node is NOT the last node [00:01:27, 00:01:30].
 * 
 * EXAMPLES & EXPLANATION:
 * Example 1: list = [4, 5, 1, 9], node to delete = Node(5) [00:01:48]
 * - Initial: 4 -> [5] -> 1 -> 9
 * - Step 1: Overwrite target node value with successor value (1):
 *   - node.val = node.next.val -> 4 -> [1] -> 1 -> 9 [00:04:08]
 * - Step 2: Re-link target node next pointer to skip duplicate successor node:
 *   - node.next = node.next.next -> 4 -> [1] ----> 9 [00:04:19]
 * - Resulting list values: [4, 1, 9] [00:01:55]
 * 
 * Example 2: list = [4, 6, 7, 3, 2], node to delete = Node(7) [00:02:22, 00:03:56]
 * - Initial: 4 -> 6 -> [7] -> 3 -> 2
 * - Overwrite target: node.val = 3 -> 4 -> 6 -> [3] -> 3 -> 2 [00:03:30]
 * - Bypass successor: node.next = node.next.next -> 4 -> 6 -> [3] ----> 2 [00:03:44]
 * - Resulting list values: [4, 6, 3, 2] [00:03:56]
 * 
 * ---
 * ANCHOR STRATEGY BREAKDOWN (Approach 1 - Successor Value Overwrite & Pointer Bypass):
 * • Edge Conditions & Guarantees:
 *   - Guaranteed `node != null` and `node.next != null` as per problem constraints [00:01:30].
 * • Operational Steps:
 *   1. Overwrite target node's value with next node's value: `node.val = node.next.val` [00:04:08].
 *   2. Link target node to successor's next node: `node.next = node.next.next` [00:04:19].
 * • Time Complexity: O(1) - Exactly two constant time operations executed [00:04:30].
 * • Space Complexity: O(1) auxiliary space - Performs modification directly in-place without memory allocation [00:04:30].
 * • LOGIC BEHIND THIS APPROACH:
 *   Because we lack access to the preceding node, we cannot change the pointer pointing TO the node [00:03:00, 00:03:14]. 
 *   Copying the successor value into the current node makes the current node indistinguishable from the successor node [00:03:30]. 
 *   Unlinking the actual successor node removes the duplicate node while maintaining correct overall list ordering [00:03:44, 00:05:12].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 1: In-Place Overwrite - list = [4, 5, 6], node = Node(5)):
 * Initial state: Node(4) -> Node(5) [target] -> Node(6) -> null [00:04:44]
 * 
 * Step 1: Copy value from successor (Node(6)) into target:
 * target.val = target.next.val (6)
 * State: Node(4) -> Node(6) [target] -> Node(6) -> null [00:04:58]
 * 
 * Step 2: Bypass target.next:
 * target.next = target.next.next (null)
 * State: Node(4) -> Node(6) [target] -> null [00:05:05]
 * 
 * List traversal from head: 4 -> 6 -> null.
 * Output = [4, 6] [00:05:17].
 * 
 * ---
 * VISUAL DRY RUN (APPROACH 2: Multi-Node Value Shift Loop Strategy):
 * While node.next != null:
 *   node.val = node.next.val
 *   if node.next.next == null -> node.next = null; break;
 *   node = node.next;
 * Continues copying values all the way down to tail node before severing tail.
 */
public class DeleteNodeInLinkedList {

    // APPROACH 1: Successor Value Overwrite & Pointer Bypass (Anchor Strategy - 2 Lines)
    public static void deleteNodeOptimal(ListNode node) {
        // Step 1: Overwrite target node's value with its successor's value [00:04:08]
        node.val = node.next.val;

        // Step 2: Skip the successor node by bridging pointers [00:04:19]
        node.next = node.next.next;
    }

    // APPROACH 2: Multi-Node Value Shift Loop Strategy
    // Shifts all remaining values leftwards down the line until the tail node, then drops tail.
    // Useful logic when pointer bypassing is constrained by garbage collection or references.
    public static void deleteNodeShiftAll(ListNode node) {
        ListNode curr = node;

        while (curr.next != null) {
            curr.val = curr.next.val; // Shift value left
            
            // If successor is tail, drop it and exit loop
            if (curr.next.next == null) {
                curr.next = null;
                break;
            }
            curr = curr.next;
        }
    }

    // Helper method to convert an array to a linked list
    private static ListNode buildList(int[] values) {
        if (values == null || values.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int v : values) {
            curr.next = new ListNode(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    // Helper method to locate node reference by value for testing
    private static ListNode findNode(ListNode head, int targetVal) {
        while (head != null) {
            if (head.val == targetVal) return head;
            head = head.next;
        }
        return null;
    }

    // Helper method to convert linked list to standard List for assertions
    private static List<Integer> toList(ListNode head) {
        List<Integer> result = new ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        return result;
    }

    // Comprehensive Verification Test Suite
    public static void main(String[] args) {
        System.out.println("================ RUNNING DSA TEST SUITE ================\n");

        // --- TEST CASE 1 (Delete Node 5 from [4, 5, 1, 9]) ---
        ListNode head1 = buildList(new int[]{4, 5, 1, 9});
        ListNode nodeToDelete1 = findNode(head1, 5);

        deleteNodeOptimal(nodeToDelete1);

        System.out.println("Test Case 1: Delete Node(5) from [4, 5, 1, 9]");
        System.out.println("Approach 1 (Optimal Overwrite) Result: " + toList(head1));
        boolean check1 = toList(head1).equals(Arrays.asList(4, 1, 9));
        System.out.println("Verification: " + (check1 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 2 (Delete Node 7 from [4, 6, 7, 3, 2] from Video Explanation) ---
        ListNode head2 = buildList(new int[]{4, 6, 7, 3, 2});
        ListNode nodeToDelete2 = findNode(head2, 7);

        deleteNodeOptimal(nodeToDelete2);

        System.out.println("Test Case 2: Delete Node(7) from [4, 6, 7, 3, 2]");
        System.out.println("Approach 1 (Optimal Overwrite) Result: " + toList(head2));
        boolean check2 = toList(head2).equals(Arrays.asList(4, 6, 3, 2));
        System.out.println("Verification: " + (check2 ? "PASSED" : "FAILED") + "\n");

        // --- TEST CASE 3 (Shift All Strategy Verification) ---
        ListNode head3 = buildList(new int[]{4, 5, 6});
        ListNode nodeToDelete3 = findNode(head3, 5);

        deleteNodeShiftAll(nodeToDelete3);

        System.out.println("Test Case 3: Shift All Strategy on Node(5) from [4, 5, 6]");
        System.out.println("Approach 2 (Shift All) Result: " + toList(head3));
        boolean check3 = toList(head3).equals(Arrays.asList(4, 6));
        System.out.println("Verification: " + (check3 ? "PASSED" : "FAILED") + "\n");

        System.out.println("========================================================");
    }
}