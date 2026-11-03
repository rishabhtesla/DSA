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
 *   Pointer visual for 1->2->null:
 *     current=1: save next=2, point 1 to null
 *     current=2: save next=null, point 2 to 1
 *     current=null: stop; new head is 2 -> 1 -> null
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
