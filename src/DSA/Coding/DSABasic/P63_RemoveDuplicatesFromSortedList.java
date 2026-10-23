package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Collapse repeated values in a sorted linked list to one node.
 * EXAMPLE: 1->1->2 -> 1->2.
 * CONSTRAINTS: 0 <= nodes <= 300; sorted nondecreasing.
 * LOGIC / ALGORITHM:
 * 1. Compare each node with its next; bypass the next node when values match.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->1->2 -> 1->2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P63_RemoveDuplicatesFromSortedList {
    public static ListNode deleteDuplicates(ListNode h) {
        for (ListNode p = h; p != null && p.next != null; )
            if (p.val == p.next.val) p.next = p.next.next;
            else p = p.next;
        return h;
    }

    public static void main(String[] args) {
        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1))).val);
    }
}
