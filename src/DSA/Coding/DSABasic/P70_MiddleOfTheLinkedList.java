package DSA.Coding.DSABasic;

import java.util.*;


/*
 * DSA BASIC P70: MIDDLE OF THE LINKED LIST
 *
 * Question
 * Return the middle node, choosing the second middle for even length.
 *
 * Example
 * 1->2 -> node 2.
 *
 * Constraints
 * 1 <= nodes <= 100; values fit 32-bit integers.
 *
 * Approach used by edSlash
 * 1. Move slow one step and fast two; slow reaches the middle when fast ends.
 * 2. Use pointers to represent the already-processed part and the nodes that still need to be visited.
 * 3. Save the next node before changing links, then advance the pointers while preserving the list structure.
 *
 * Dry run
 *   Slow/fast visual for 1->2:
 *     slow starts at 1 and fast starts at 1
 *     slow moves to 2 while fast reaches the end
 *     return slow, node 2
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P70_MiddleOfTheLinkedList {
    public static ListNode middleNode(ListNode head) {
        ListNode s = head, f = head;
        while (f != null && f.next != null) {
            s = s.next;
            f = f.next.next;
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println(middleNode(new ListNode(1, new ListNode(2))).val);
    }
}
