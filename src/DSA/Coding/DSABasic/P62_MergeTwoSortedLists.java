package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Merge two sorted linked lists into one sorted list.
 * EXAMPLE: 1->2 and 1->3 -> 1->1->2->3.
 * CONSTRAINTS: 0 <= total nodes <= 50; lists are sorted.
 * LOGIC / ALGORITHM:
 * 1. Use a dummy tail and repeatedly link the smaller head.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->2 and 1->3 -> 1->1->2->3.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P62_MergeTwoSortedLists {
    public static ListNode mergeTwoLists(ListNode a, ListNode b) {
        ListNode d = new ListNode(), p = d;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                p.next = a;
                a = a.next;
            } else {
                p.next = b;
                b = b.next;
            }
            p = p.next;
        }
        p.next = a == null ? b : a;
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(mergeTwoLists(new ListNode(1), new ListNode(2)).val);
    }
}
