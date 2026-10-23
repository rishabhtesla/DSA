package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Reverse links from position left through right, preserving the rest.
 * EXAMPLE: 1->2, left=1,right=2 -> 2->1.
 * CONSTRAINTS: 1 <= left <= right <= n <= 500.
 * LOGIC / ALGORITHM:
 * 1. Repeatedly remove the node after the reversed segment head and insert it at the front.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->2, left=1,right=2 -> 2->1.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P74_ReverseLinkedListII {
    public static ListNode reverseBetween(ListNode h, int l, int r) {
        ListNode d = new ListNode(0, h), p = d;
        for (int i = 1; i < l; i++) p = p.next;
        ListNode q = p.next;
        for (int i = 0; i < r - l; i++) {
            ListNode x = q.next;
            q.next = x.next;
            x.next = p.next;
            p.next = x;
        }
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(reverseBetween(new ListNode(1, new ListNode(2)), 1, 2).val);
    }
}
