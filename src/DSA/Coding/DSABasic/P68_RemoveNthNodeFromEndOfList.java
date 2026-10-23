package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Remove the nth node from the end and return the head.
 * EXAMPLE: 1->2, n=1 -> 1.
 * CONSTRAINTS: 1 <= nodes <= 30; 1 <= n <= nodes.
 * LOGIC / ALGORITHM:
 * 1. Keep fast n nodes ahead of slow; when fast reaches tail, slow precedes the target.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->2, n=1 -> 1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P68_RemoveNthNodeFromEndOfList {
    public static ListNode removeNthFromEnd(ListNode h, int n) {
        ListNode d = new ListNode(0, h), p = d;
        for (int i = 0; i < n; i++) p = p.next;
        ListNode q = d;
        while (p.next != null) {
            p = p.next;
            q = q.next;
        }
        q.next = q.next.next;
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(removeNthFromEnd(new ListNode(1, new ListNode(2)), 1).val);
    }
}
