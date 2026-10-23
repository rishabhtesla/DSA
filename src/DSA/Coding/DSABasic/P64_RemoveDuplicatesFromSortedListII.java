package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Remove every value that occurs more than once from a sorted list.
 * EXAMPLE: 1->1->2 -> 2.
 * CONSTRAINTS: 0 <= nodes <= 300; sorted nondecreasing.
 * LOGIC / ALGORITHM:
 * 1. Use a dummy predecessor; skip an entire equal-value run when a duplicate is found.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input 1->1->2 -> 2.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P64_RemoveDuplicatesFromSortedListII {
    public static ListNode deleteDuplicates(ListNode h) {
        ListNode d = new ListNode(0, h), p = d;
        while (h != null) {
            if (h.next != null && h.val == h.next.val) {
                int v = h.val;
                while (h != null && h.val == v) h = h.next;
                p.next = h;
            } else {
                p = h;
                h = h.next;
            }
        }
        return d.next;
    }

    public static void main(String[] args) {
        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1, new ListNode(2)))).val);
    }
}
