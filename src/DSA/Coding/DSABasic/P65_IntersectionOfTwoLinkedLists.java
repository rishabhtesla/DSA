package DSA.Coding.DSABasic;

import java.util.*;


/**
 * QUESTION: Find the node reference where two singly linked lists intersect.
 * EXAMPLE: A and B both enter shared node 7 -> return that node.
 * CONSTRAINTS: 0 <= each length <= 3*10^4; intersection is by reference, not value.
 * LOGIC / ALGORITHM:
 * 1. Advance two pointers; when one ends, restart it at the other head so path lengths equalize.
 * 2. Advance or relink pointers while preserving the already-correct portion and required relative order.
 * 3. Return the resulting head or value.
 * INVARIANT: The processed list portion is correctly linked; pointer gaps or stack entries represent exactly the nodes still to process.
 * <p>
 * COMPLEXITY: O(n) time and O(1) auxiliary space.
 * DRY RUN: Main input A and B both enter shared node 7 -> return that node.; follow the numbered steps while maintaining the invariant, producing the value printed by main.
 */
public class P65_IntersectionOfTwoLinkedLists {
    public static ListNode getIntersectionNode(ListNode a, ListNode b) {
        ListNode p = a, q = b;
        while (p != q) {
            p = p == null ? b : p.next;
            q = q == null ? a : q.next;
        }
        return p;
    }

    public static void main(String[] args) {
        ListNode x = new ListNode(7);
        System.out.println(getIntersectionNode(x, x).val);
    }
}
