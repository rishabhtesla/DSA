package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Return the middle node, choosing the second middle for even length.
 * EXAMPLE: 1->2 -> node 2.
 * CONSTRAINTS: 1 <= nodes <= 100; values fit 32-bit integers.
 * LOGIC / ALGORITHM:
 * 1. Move slow one step and fast two; slow reaches the middle when fast ends.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->2 -> node 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
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
