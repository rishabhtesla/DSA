package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Reverse the links of a singly linked list.
 * EXAMPLE: 1->2->null -> 2->1->null.
 * CONSTRAINTS: 0 <= nodes <= 5000; node values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Keep previous and current pointers; save next, point current backward, then advance.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->2->null -> 2->1->null.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P61_ReverseLinkedList {
    public static ListNode reverseList(ListNode h) {
        ListNode p = null;
        while (h != null) {
            ListNode n = h.next;
            h.next = p;
            p = h;
            h = n;
        }
        return p;
    }

    public static void main(String[] args) {
        ListNode h = new ListNode(1, new ListNode(2));
        System.out.println(reverseList(h).val);
    }
}
