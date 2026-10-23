package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Swap every adjacent pair in a linked list without changing values.
 * EXAMPLE: 1->2->3 -> 2->1->3.
 * CONSTRAINTS: 0 <= nodes <= 100; links may be rewired only.
 * LOGIC / ALGORITHM:
 * 1. Use a dummy predecessor and rotate each two-node block.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->2->3 -> 2->1->3.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P73_SwapNodesInPairs {
    public static ListNode swapPairs(ListNode h) {
        ListNode d = new ListNode(0, h);
        for (ListNode p = d; p.next != null && p.next.next != null; ) {
            ListNode a = p.next, b = a.next;
            a.next = b.next;
            b.next = a;
            p.next = b;
            p = a;
        }
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(swapPairs(new ListNode(1, new ListNode(2))).val);
    }
}
