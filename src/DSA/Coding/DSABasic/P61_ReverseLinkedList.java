package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P61: REVERSE LINKED LIST
 *
 * Question
 * Reverse the links of a singly linked list.
 *
 * Example
 * 1->2->null -> 2->1->null.
 *
 * Constraints
 * 0 <= nodes <= 5000; node values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Keep previous and current pointers; save next, point current backward, then advance.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 * Example from main: 1->2->null -> 2->1->null.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P61_ReverseLinkedList {
    public static ListNode reverseList(ListNode head) {
        ListNode p = null;
        while (head != null) {
            ListNode n = head.next;
            head.next = p;
            p = head;
            head = n;
        }
        return p;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2));
        System.out.println(reverseList(head).val);
    }
}
