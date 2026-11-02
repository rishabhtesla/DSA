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
 * Example from main: 1->2 -> node 2.
 * 1. Initialize the pointers described in the approach.
 * 2. Save the next node before changing a link or moving a pointer.
 * 3. Update the links and advance until the required node or the end is reached.
 * 4. Return the resulting head or answer.
 *
 * Complexity
 * O(n) time and O(1) auxiliary space.
 */
public class P70_MiddleOfTheLinkedList {
    public static ListNode middleNode(ListNode h) {
        ListNode s = h, f = h;
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
